package dao;

import db.DBConnection;
import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class BookingDAO {
    public int addBooking(Booking b) {
        String sql = "INSERT INTO Booking (user_id,slot_id) VALUES (?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getUserId());
            ps.setInt(2, b.getSlotId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding booking: " + e.getMessage());
        }
        return -1;
    }

    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT booking_id,user_id,slot_id,booking_time,exit_time FROM Booking WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("booking_id"));
                    b.setUserId(rs.getInt("user_id"));
                    b.setSlotId(rs.getInt("slot_id"));
                    Timestamp bt = rs.getTimestamp("booking_time");
                    if (bt != null) b.setBookingTime(new Date(bt.getTime()));
                    Timestamp et = rs.getTimestamp("exit_time");
                    if (et != null) b.setExitTime(new Date(et.getTime()));
                    list.add(b);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading bookings: " + e.getMessage());
        }
        return list;
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT booking_id,user_id,slot_id,booking_time,exit_time FROM Booking";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setSlotId(rs.getInt("slot_id"));
                Timestamp bt = rs.getTimestamp("booking_time");
                if (bt != null) b.setBookingTime(new Date(bt.getTime()));
                Timestamp et = rs.getTimestamp("exit_time");
                if (et != null) b.setExitTime(new Date(et.getTime()));
                list.add(b);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading bookings: " + e.getMessage());
        }
        return list;
    }

    public boolean updateExitTime(int bookingId) {
        String sql = "UPDATE Booking SET exit_time = NOW() WHERE booking_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating exit time: " + e.getMessage());
            return false;
        }
    }

    public Booking getBookingById(int bookingId) {
        String sql = "SELECT booking_id,user_id,slot_id,booking_time,exit_time FROM Booking WHERE booking_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("booking_id"));
                    b.setUserId(rs.getInt("user_id"));
                    b.setSlotId(rs.getInt("slot_id"));
                    Timestamp bt = rs.getTimestamp("booking_time");
                    if (bt != null) b.setBookingTime(new Date(bt.getTime()));
                    Timestamp et = rs.getTimestamp("exit_time");
                    if (et != null) b.setExitTime(new Date(et.getTime()));
                    return b;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching booking: " + e.getMessage());
        }
        return null;
    }
}
