package dao;

import db.DBConnection;
import model.ParkingArea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ParkingAreaDAO {
    public List<ParkingArea> getAllAreas() {
        List<ParkingArea> list = new ArrayList<>();
        String sql = "SELECT area_id,location,total_slots FROM Parking_Area";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ParkingArea a = new ParkingArea(rs.getInt("area_id"), rs.getString("location"), rs.getInt("total_slots"));
                list.add(a);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading areas: " + e.getMessage());
        }
        return list;
    }

    public int addArea(ParkingArea area) {
        String sql = "INSERT INTO Parking_Area (location,total_slots) VALUES (?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, area.getLocation());
            ps.setInt(2, area.getTotalSlots());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int areaId = rs.getInt(1);
                        // auto generate slots
                        String ssql = "INSERT INTO Parking_Slot (area_id,slot_number,status) VALUES (?,?,?)";
                        try (PreparedStatement sps = conn.prepareStatement(ssql)) {
                            for (int i = 1; i <= area.getTotalSlots(); i++) {
                                sps.setInt(1, areaId);
                                sps.setString(2, String.valueOf(i));
                                sps.setString(3, "Free");
                                sps.addBatch();
                            }
                            sps.executeBatch();
                        }
                        return areaId;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding area: " + e.getMessage());
        }
        return -1;
    }

    public boolean deleteArea(int areaId) {
        String sql = "DELETE FROM Parking_Area WHERE area_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, areaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting area: " + e.getMessage());
            return false;
        }
    }
}
