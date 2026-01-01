package com.pfms.controller;

import com.google.gson.Gson;
import com.pfms.dao.TransactionDAO;
import com.pfms.dao.CategoryDAO;
import com.pfms.model.Transaction;
import com.pfms.model.Category;
import com.pfms.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/dashboard")
public class DashboardServlet extends HttpServlet {

    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Not logged in\"}");
            return;
        }

        List<Transaction> transactions = transactionDAO.findByUser(user.getId());
        List<Category> categories = categoryDAO.findAll();

        // Calculate totals
        double totalIncome = transactions.stream()
                .filter(t -> "INCOME".equals(t.getTransactionType()))
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> "EXPENSE".equals(t.getTransactionType()))
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        double net = totalIncome - totalExpense;

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("transactions", transactions);
        data.put("categories", categories);
        data.put("totalIncome", totalIncome);
        data.put("totalExpense", totalExpense);
        data.put("net", net);

        resp.getWriter().write(gson.toJson(data));
    }
}