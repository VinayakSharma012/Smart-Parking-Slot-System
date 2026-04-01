package dao;

import db.DBConnection;
import model.Payment;

import java.sql.*;
import java.util.Date;
import javax.swing.JOptionPane;

public class PaymentDAO {
    public int addPayment(Payment p) {
        String sql = "INSERT INTO Payment (booking_id,amount,payment_status) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getBookingId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getPaymentStatus());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding payment: " + e.getMessage());
        }
        return -1;
    }

    public boolean updatePayment(int bookingId, double amount, String status) {
        String sql = "UPDATE Payment SET amount = ?, payment_status = ?, payment_time = NOW() WHERE booking_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, status);
            ps.setInt(3, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating payment: " + e.getMessage());
            return false;
        }
    }

    public Payment getPaymentByBookingId(int bookingId) {
        String sql = "SELECT payment_id,booking_id,amount,payment_status,payment_time FROM Payment WHERE booking_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Payment p = new Payment();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setBookingId(rs.getInt("booking_id"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setPaymentStatus(rs.getString("payment_status"));
                    Timestamp t = rs.getTimestamp("payment_time");
                    if (t != null) p.setPaymentTime(new Date(t.getTime()));
                    return p;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching payment: " + e.getMessage());
        }
        return null;
    }
}
