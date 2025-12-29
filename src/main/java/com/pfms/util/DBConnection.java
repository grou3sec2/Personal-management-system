package com.pfms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    // Update 'personal_finance_db' with your actual MySQL database name
    private static final String URL = "jdbc:mysql://localhost:3306/personal_finance_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Modern MySQL driver class name
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connected successfully");
           
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public static void main(String[] args){
        Connection c = getConnection();
    }
}

