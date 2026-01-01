package com.pfms.dao;

import com.pfms.model.Transaction;
import com.pfms.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean save(Transaction t) {
        String sql = "INSERT INTO transactions(user_id, category_id, amount, transaction_date, description, transaction_type) VALUES (?,?,?,?,?,?)";
        // Using try-with-resources here ensures the connection closes automatically
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getUserId());
            ps.setInt(2, t.getCategoryId());
            ps.setBigDecimal(3, t.getAmount());
            ps.setDate(4, new java.sql.Date(t.getTransactionDate().getTime()));
            ps.setString(5, t.getDescription());
            ps.setString(6, t.getTransactionType());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> findByUser(int userId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setTransactionDate(rs.getDate("transaction_date"));
                t.setDescription(rs.getString("description"));
                t.setTransactionType(rs.getString("transaction_type"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(int transactionId, int userId) {
        String sql = "DELETE FROM transactions WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transactionId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}