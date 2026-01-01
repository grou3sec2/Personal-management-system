package com.pfms.controller;

import com.google.gson.Gson;
import com.pfms.dao.TransactionDAO;
import com.pfms.model.Transaction;
import com.pfms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/transactions/*")
public class TransactionServlet extends HttpServlet {

    private final TransactionDAO dao = new TransactionDAO();
    private final Gson gson = new Gson();

    // GET: Fetch all transactions
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<Transaction> transactions = dao.findByUser(user.getId());
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("user", user);
        responseData.put("transactions", transactions);
        resp.getWriter().write(gson.toJson(responseData));
    }

    // POST: Save new transaction
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Transaction t = gson.fromJson(req.getReader(), Transaction.class);
            
            // Server-side defaults and validation
            if (t.getCategoryId() == null) t.setCategoryId(1);
            t.setUserId(user.getId());
            t.setTransactionDate(new Date());

            if (t.getAmount() == null || t.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid amount\"}");
                return;
            }

            boolean saved = dao.save(t);
            if (saved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\":\"Saved\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE: Remove specific transaction
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int transactionId = Integer.parseInt(pathInfo.substring(1));
            boolean success = dao.delete(transactionId, user.getId());

            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\":\"Deleted\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}