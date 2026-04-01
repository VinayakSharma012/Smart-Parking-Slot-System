package ui;

import dao.ParkingAreaDAO;
import dao.ParkingSlotDAO;
import model.ParkingArea;
import model.ParkingSlot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ParkingSlotPanel extends JPanel {
    private JComboBox<ParkingArea> areaCombo;
    private JTable table;
    private DefaultTableModel model;
    private ParkingAreaDAO areaDAO = new ParkingAreaDAO();
    private ParkingSlotDAO slotDAO = new ParkingSlotDAO();

    public ParkingSlotPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        areaCombo = new JComboBox<>();
        top.add(new JLabel("Parking Area:"));
        top.add(areaCombo);
        JButton refresh = new JButton("Refresh");
        JButton reset = new JButton("Reset");
        top.add(refresh);
        top.add(reset);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Slot ID","Slot Number","Status"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(24);
        table.setDefaultRenderer(Object.class, new SlotRowRenderer());
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh.addActionListener(e -> loadAreas());
        reset.addActionListener(e -> {
            areaCombo.setSelectedIndex(0);
            loadSlots();
        });
        areaCombo.addActionListener(e -> loadSlots());

        loadAreas();
    }

    private void loadAreas() {
        areaCombo.removeAllItems();
        List<ParkingArea> areas = areaDAO.getAllAreas();
        for (ParkingArea a : areas) areaCombo.addItem(a);
        if (areaCombo.getItemCount() > 0) areaCombo.setSelectedIndex(0);
        loadSlots();
    }

    private void loadSlots() {
        model.setRowCount(0);
        ParkingArea a = (ParkingArea) areaCombo.getSelectedItem();
        if (a == null) return;
        java.util.List<ParkingSlot> slots = slotDAO.getSlotsByArea(a.getAreaId());
        for (ParkingSlot s : slots) {
            model.addRow(new Object[]{s.getSlotId(), s.getSlotNumber(), s.getStatus()});
        }
    }

    // Public method to refresh slots (called when tab is selected)
    public void refreshSlots() {
        loadSlots();
    }

    // renderer colors rows
    private static class SlotRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) table.getModel().getValueAt(row, 2);
            if ("Free".equalsIgnoreCase(status)) {
                c.setBackground(new Color(0x34,0xa8,0x53)); // green-ish
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(new Color(0xea,0x43,0x35)); // red-ish
                c.setForeground(Color.WHITE);
            }
            if (isSelected) {
                c.setBackground(c.getBackground().darker());
            }
            return c;
        }
    }
}
