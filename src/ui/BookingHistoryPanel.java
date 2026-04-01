package ui;

import dao.BookingDAO;
import dao.PaymentDAO;
import model.Booking;
import model.Payment;
import utils.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookingHistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private BookingDAO bookingDAO = new BookingDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    public BookingHistoryPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("Refresh");
        JButton resetBtn = new JButton("Clear Selection");
        top.add(refreshBtn);
        top.add(resetBtn);
        add(top, BorderLayout.NORTH);
        
        model = new DefaultTableModel(new Object[]{"Booking ID","Slot","Booking Time","Exit Time","Amount","Status"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        refreshBtn.addActionListener(e -> loadHistory());
        resetBtn.addActionListener(e -> {
            table.clearSelection();
            model.setRowCount(0);
        });
        
        loadHistory();
    }

    public void loadHistory() {
        model.setRowCount(0);
        int uid = Session.getInstance().getUser().getUserId();
        List<Booking> bookings = bookingDAO.getBookingsByUser(uid);
        for (Booking b : bookings) {
            Payment p = paymentDAO.getPaymentByBookingId(b.getBookingId());
            model.addRow(new Object[]{b.getBookingId(), b.getSlotId(), b.getBookingTime(), b.getExitTime(), p==null?0.0:p.getAmount(), p==null?"-":p.getPaymentStatus()});
        }
    }
}
