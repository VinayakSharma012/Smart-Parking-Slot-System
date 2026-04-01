package ui;

import dao.ParkingAreaDAO;
import dao.ParkingSlotDAO;
import dao.UserDAO;
import model.ParkingArea;
import model.ParkingSlot;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private ParkingAreaDAO areaDAO = new ParkingAreaDAO();
    private ParkingSlotDAO slotDAO = new ParkingSlotDAO();
    private UserDAO userDAO = new UserDAO();

    public AdminDashboard() {
        setTitle("Smart Parking - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        JButton backBtn = new JButton("Back to Login");
        logoutBtn.addActionListener(e -> {
            utils.Session.getInstance().clear();
            new LoginFrame().setVisible(true);
            dispose();
        });
        backBtn.addActionListener(e -> {
            utils.Session.getInstance().clear();
            new LoginFrame().setVisible(true);
            dispose();
        });
        header.add(backBtn);
        header.add(logoutBtn);
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Manage Areas", createManageAreasPanel());
        tabs.addTab("Manage Slots", createManageSlotsPanel());
        tabs.addTab("All Bookings", new JPanel());
        tabs.addTab("User Management", createUserManagementPanel());

        add(tabs, BorderLayout.CENTER);
        pack(); setLocationRelativeTo(null);
    }

    private JPanel createManageAreasPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Area ID","Location","Total Slots"},0){ public boolean isCellEditable(int r,int c){return false;} };
        JTable table = new JTable(model);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField locField = new JTextField(20); JSpinner slots = new JSpinner(new SpinnerNumberModel(5,1,100,1));
        JButton addBtn = new JButton("Add Area"); 
        JButton delBtn = new JButton("Delete Area");
        JButton resetBtn = new JButton("Reset Form");
        form.add(new JLabel("Location:")); 
        form.add(locField); 
        form.add(new JLabel("Slots:")); 
        form.add(slots); 
        form.add(addBtn); 
        form.add(delBtn);
        form.add(resetBtn);
        p.add(form, BorderLayout.SOUTH);

        Runnable reload = () -> {
            model.setRowCount(0);
            List<ParkingArea> areas = areaDAO.getAllAreas();
            for (ParkingArea a : areas) model.addRow(new Object[]{a.getAreaId(), a.getLocation(), a.getTotalSlots()});
        };
        reload.run();

        addBtn.addActionListener(e -> {
            String loc = locField.getText().trim(); int ts = (Integer) slots.getValue();
            if (loc.isEmpty()) return;
            ParkingArea a = new ParkingArea(); a.setLocation(loc); a.setTotalSlots(ts);
            areaDAO.addArea(a); reload.run();
            locField.setText("");
            slots.setValue(5);
        });

        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r < 0) return;
            int aid = (Integer) model.getValueAt(r,0);
            areaDAO.deleteArea(aid); reload.run();
        });
        
        resetBtn.addActionListener(e -> {
            locField.setText("");
            slots.setValue(5);
            table.clearSelection();
        });

        return p;
    }

    private JPanel createManageSlotsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Slot ID","Area ID","Slot Number","Status"},0){ public boolean isCellEditable(int r,int c){return false;} };
        JTable table = new JTable(model);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton toggle = new JButton("Toggle Status");
        JButton resetBtn = new JButton("Clear Selection");
        bottom.add(toggle);
        bottom.add(resetBtn);
        p.add(bottom, BorderLayout.SOUTH);
        
        Runnable reload = () -> {
            model.setRowCount(0);
            List<ParkingSlot> slots = slotDAO.getAllSlots();
            for (ParkingSlot s : slots) model.addRow(new Object[]{s.getSlotId(), s.getAreaId(), s.getSlotNumber(), s.getStatus()});
        };
        reload.run();

        toggle.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r<0) return;
            int sid = (Integer) model.getValueAt(r,0);
            String st = (String) model.getValueAt(r,3);
            slotDAO.updateSlotStatus(sid, "Free".equalsIgnoreCase(st)?"Occupied":"Free");
            reload.run();
        });
        
        resetBtn.addActionListener(e -> {
            table.clearSelection();
            model.setRowCount(0);
        });
        
        return p;
    }

    private JPanel createUserManagementPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"User ID","Name","Email","Phone","Role"},0){ public boolean isCellEditable(int r,int c){return false;} };
        JTable table = new JTable(model);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton delBtn = new JButton("Delete User");
        JButton refreshBtn = new JButton("Refresh");
        JButton resetBtn = new JButton("Clear Selection");
        south.add(refreshBtn);
        south.add(delBtn);
        south.add(resetBtn);
        p.add(south, BorderLayout.SOUTH);

        Runnable reload = () -> {
            model.setRowCount(0);
            for (User u : userDAO.getAllUsers()) model.addRow(new Object[]{u.getUserId(), u.getName(), u.getEmail(), u.getPhone(), u.getRole()});
        };
        reload.run();

        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r<0) return;
            int uid = (Integer) model.getValueAt(r,0);
            userDAO.deleteUser(uid); reload.run();
        });
        
        refreshBtn.addActionListener(e -> reload.run());
        
        resetBtn.addActionListener(e -> {
            table.clearSelection();
            model.setRowCount(0);
        });
        
        return p;
    }
}
