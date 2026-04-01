package ui;

import dao.BookingDAO;
import dao.PaymentDAO;
import model.Booking;
import model.Payment;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class PaymentPanel extends JPanel {
    private JTextField bookingIdField;
    private JTextArea detailsArea;
    private JComboBox<String> methodCombo;
    private BookingDAO bookingDAO = new BookingDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    public PaymentPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Booking ID:"));
        bookingIdField = new JTextField(8); top.add(bookingIdField);
        JButton fetch = new JButton("Fetch");
        JButton resetBtn = new JButton("Reset");
        top.add(fetch);
        top.add(resetBtn);
        add(top, BorderLayout.NORTH);

        detailsArea = new JTextArea(8,40); detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        methodCombo = new JComboBox<>(new String[]{"UPI","Card","Cash"});
        bottom.add(new JLabel("Method:")); bottom.add(methodCombo);
        JButton payBtn = new JButton("Pay Now"); bottom.add(payBtn);
        add(bottom, BorderLayout.SOUTH);

        fetch.addActionListener(e -> fetchBooking());
        resetBtn.addActionListener(e -> {
            bookingIdField.setText("");
            detailsArea.setText("");
            methodCombo.setSelectedIndex(0);
            currentBooking = null;
        });
        payBtn.addActionListener(e -> doPay());
    }

    private Booking currentBooking;

    private void fetchBooking() {
        String bid = bookingIdField.getText().trim();
        if (bid.isEmpty()) return;
        try {
            int id = Integer.parseInt(bid);
            currentBooking = bookingDAO.getBookingById(id);
            Payment p = paymentDAO.getPaymentByBookingId(id);
            if (currentBooking == null) {
                detailsArea.setText("Booking not found"); return;
            }
            Date bt = currentBooking.getBookingTime();
            Date ex = currentBooking.getExitTime();
            Date now = new Date();
            long diff = (ex == null ? now.getTime() : ex.getTime()) - bt.getTime();
            double hours = Math.max(1, Math.ceil(diff / (1000.0*60*60)));
            double amount = hours * 20.0;
            StringBuilder sb = new StringBuilder();
            sb.append("Booking ID: ").append(id).append('\n');
            sb.append("Slot ID: ").append(currentBooking.getSlotId()).append('\n');
            sb.append("Booked at: ").append(bt).append('\n');
            sb.append("Exit at: ").append(ex==null?"-":ex).append('\n');
            sb.append("Duration (hrs): ").append(hours).append('\n');
            sb.append("Amount: ₹").append(String.format("%.2f", amount)).append('\n');
            if (p != null) sb.append("Payment status: ").append(p.getPaymentStatus()).append('\n');
            detailsArea.setText(sb.toString());
        } catch (NumberFormatException ex) {
            detailsArea.setText("Invalid booking id");
        }
    }

    private void doPay() {
        if (currentBooking == null) { JOptionPane.showMessageDialog(this, "Fetch booking first"); return; }
        int id = currentBooking.getBookingId();
        Date bt = currentBooking.getBookingTime();
        Date now = new Date();
        long diff = now.getTime() - bt.getTime();
        double hours = Math.max(1, Math.ceil(diff / (1000.0*60*60)));
        double amount = hours * 20.0;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            boolean ok1 = bookingDAO.updateExitTime(id);
            boolean ok2 = paymentDAO.updatePayment(id, amount, "Paid");
            // NOTE: Slot should remain "Occupied" after payment - user is still parked
            // Slot becomes "Free" only when user physically exits the parking lot
            if (ok1 && ok2) {
                JOptionPane.showMessageDialog(this, "Paid successfully. Receipt:\nBooking: " + id + "\nAmount: ₹"+amount + "\n\nSlot remains reserved until you exit.");
                fetchBooking();
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed");
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
