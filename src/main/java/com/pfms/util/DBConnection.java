package com.pfms.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    // Member 1(Jeremiah): 
    private static final String URL = "jdbc:mysql://localhost:3306/personal_finance_db";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_LOCAL_PASSWORD_HERE"; //use your local database and password

    public static Connection getConnection() {
        
        return null; 
    }
}