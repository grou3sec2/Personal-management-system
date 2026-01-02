package com.pfms.controller;

import com.pfms.dao.UserDAO;
import com.pfms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.validateUser(email, password);

        if (user != null) {
            // true creates a new session if none exists
            HttpSession session = request.getSession(true); 
            session.setAttribute("user", user);
            
            // Redirect using Context Path to avoid 404s
            response.sendRedirect(request.getContextPath() + "/home.html");
        } else {
            // Redirect back with error parameter
            response.sendRedirect(request.getContextPath() + "/index.html?error=invalid");
        }
    }
}