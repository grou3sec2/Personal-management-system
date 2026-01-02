package com.pfms.dao;

import com.pfms.model.User;
import com.pfms.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt; // Import BCrypt
import java.sql.*;

public class UserDAO {
    
    public User validateUser(String email, String plainPassword) {
        // We only filter by email in the SQL query
        String sql = "SELECT id, full_name, email, password FROM users WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the hashed password stored in the database
                    String storedHash = rs.getString("password");

                    // Use BCrypt to compare the plain text input with the stored hash
                    if (BCrypt.checkpw(plainPassword, storedHash)) {
                        // If it matches, create and return the User object
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setFull_name(rs.getString("full_name")); 
                        user.setEmail(rs.getString("email"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return null if user not found or password doesn't match
        return null; 
    }
}