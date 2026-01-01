package com.pfms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve the current session, do not create a new one if it doesn't exist
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // This is the key line: it wipes all session data (user object, etc.)
            session.invalidate();
        }
        
        // Redirect back to login page
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}