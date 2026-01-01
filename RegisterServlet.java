package com.pfms.controller;

import com.pfms.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String plainPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        
        if (plainPassword == null || confirmPassword == null) {
            response.sendRedirect("register.html?error=Missing+Fields");
            return;
        }

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword); // Store the HASH, not the plain text

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("index.html?register=success");
            } else {
                response.sendRedirect("register.html?error=Registration+failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.html?error=Email+already+exists");
        }
    }
}