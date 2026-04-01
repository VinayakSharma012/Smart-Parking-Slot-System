package dao;

import db.DBConnection;
import model.ParkingSlot;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ParkingSlotDAO {
    public List<ParkingSlot> getSlotsByArea(int areaId) {
        List<ParkingSlot> list = new ArrayList<>();
        String sql = "SELECT slot_id,area_id,slot_number,status FROM Parking_Slot WHERE area_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, areaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ParkingSlot s = new ParkingSlot(rs.getInt("slot_id"), rs.getInt("area_id"), rs.getString("slot_number"), rs.getString("status"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading slots: " + e.getMessage());
        }
        return list;
    }

    public List<ParkingSlot> getFreeSlotsByArea(int areaId) {
        List<ParkingSlot> list = new ArrayList<>();
        String sql = "SELECT slot_id,area_id,slot_number,status FROM Parking_Slot WHERE area_id = ? AND status = 'Free'";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, areaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ParkingSlot s = new ParkingSlot(rs.getInt("slot_id"), rs.getInt("area_id"), rs.getString("slot_number"), rs.getString("status"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading free slots: " + e.getMessage());
        }
        return list;
    }

    public boolean updateSlotStatus(int slotId, String status) {
        String sql = "UPDATE Parking_Slot SET status = ? WHERE slot_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, slotId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating slot: " + e.getMessage());
            return false;
        }
    }

    public List<ParkingSlot> getAllSlots() {
        List<ParkingSlot> list = new ArrayList<>();
        String sql = "SELECT slot_id,area_id,slot_number,status FROM Parking_Slot";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ParkingSlot s = new ParkingSlot(rs.getInt("slot_id"), rs.getInt("area_id"), rs.getString("slot_number"), rs.getString("status"));
                list.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading slots: " + e.getMessage());
        }
        return list;
    }
}
