package ui;

import dao.BookingDAO;
import dao.ParkingAreaDAO;
import dao.ParkingSlotDAO;
import dao.PaymentDAO;
import model.Booking;
import model.ParkingArea;
import model.ParkingSlot;
import model.Payment;
import utils.Session;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class BookingPanel extends JPanel {
    private JComboBox<ParkingArea> areaCombo;
    private JComboBox<ParkingSlot> slotCombo;
    private JLabel timeLabel;
    private ParkingAreaDAO areaDAO = new ParkingAreaDAO();
    private ParkingSlotDAO slotDAO = new ParkingSlotDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    public BookingPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx=0; gbc.gridy=0; add(new JLabel("Area:"), gbc);
        areaCombo = new JComboBox<>(); gbc.gridx=1; add(areaCombo, gbc);
        gbc.gridx=0; gbc.gridy=1; add(new JLabel("Slot:"), gbc);
        slotCombo = new JComboBox<>(); gbc.gridx=1; add(slotCombo, gbc);
        gbc.gridx=0; gbc.gridy=2; add(new JLabel("Time:"), gbc);
        timeLabel = new JLabel(new Date().toString()); gbc.gridx=1; add(timeLabel, gbc);

        JButton bookBtn = new JButton("Book Now"); 
        JButton resetBtn = new JButton("Reset");
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=1; add(bookBtn, gbc);
        gbc.gridx=1; gbc.gridwidth=1; add(resetBtn, gbc);

        areaCombo.addActionListener(e -> loadFreeSlots());
        bookBtn.addActionListener(e -> doBook());
        resetBtn.addActionListener(e -> {
            areaCombo.setSelectedIndex(0);
            loadFreeSlots();
            timeLabel.setText(new Date().toString());
        });

        loadAreas();
    }

    private void loadAreas() {
        areaCombo.removeAllItems();
        List<ParkingArea> areas = areaDAO.getAllAreas();
        for (ParkingArea a : areas) areaCombo.addItem(a);
        if (areaCombo.getItemCount() > 0) areaCombo.setSelectedIndex(0);
        loadFreeSlots();
    }

    private void loadFreeSlots() {
        slotCombo.removeAllItems();
        ParkingArea a = (ParkingArea) areaCombo.getSelectedItem();
        if (a == null) return;
        List<ParkingSlot> free = slotDAO.getFreeSlotsByArea(a.getAreaId());
        for (ParkingSlot s : free) slotCombo.addItem(s);
    }

    private void doBook() {
        ParkingSlot s = (ParkingSlot) slotCombo.getSelectedItem();
        if (s == null) { JOptionPane.showMessageDialog(this, "No free slot selected"); return; }
        int userId = utils.Session.getInstance().getUser().getUserId();
        Booking b = new Booking();
        b.setUserId(userId); b.setSlotId(s.getSlotId());
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            int bookingId = bookingDAO.addBooking(b);
            if (bookingId > 0) {
                slotDAO.updateSlotStatus(s.getSlotId(), "Occupied");
                Payment p = new Payment(); p.setBookingId(bookingId); p.setAmount(0.0); p.setPaymentStatus("Pending");
                paymentDAO.addPayment(p);
                JOptionPane.showMessageDialog(this, "Booked successfully. Booking ID: " + bookingId);
                loadFreeSlots();
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed");
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
