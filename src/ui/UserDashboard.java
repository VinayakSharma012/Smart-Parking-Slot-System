package ui;

import utils.Session;
import model.User;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    public UserDashboard() {
        setTitle("Smart Parking - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        User u = Session.getInstance().getUser();
        JLabel heading = new JLabel("Welcome, " + (u!=null?u.getName():"User"));
        heading.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(heading, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Available Slots", new ParkingSlotPanel());
        tabs.addTab("Book Slot", new BookingPanel());
        tabs.addTab("My Bookings", new BookingHistoryPanel());
        tabs.addTab("Payment", new PaymentPanel());
        add(tabs, BorderLayout.CENTER);

        JButton logout = new JButton("Logout");
        JButton backBtn = new JButton("Back to Login");
        logout.addActionListener(e -> {
            Session.getInstance().clear();
            new LoginFrame().setVisible(true);
            dispose();
        });
        backBtn.addActionListener(e -> {
            Session.getInstance().clear();
            new LoginFrame().setVisible(true);
            dispose();
        });
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(backBtn);
        south.add(logout);
        add(south, BorderLayout.SOUTH);

        pack(); setLocationRelativeTo(null);
    }
}
