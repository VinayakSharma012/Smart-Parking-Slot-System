package main;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ui.RegisterFrame;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Try FlatLaf, fallback to Nimbus
            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            } catch (Exception e) {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Could not set look and feel: " + ex.getMessage());
                }
            }

            RegisterFrame rf = new RegisterFrame();
            rf.setVisible(true);
        });
    }
}
