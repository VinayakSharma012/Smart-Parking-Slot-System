package ui;

import dao.UserDAO;
import java.awt.*;
import javax.swing.*;
import model.User;
import utils.Session;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Smart Parking - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        emailField = new JTextField(20);
        p.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(20);
        p.add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        JButton resetBtn = new JButton("Reset");
        JButton exitBtn = new JButton("Exit");

        JPanel btns = new JPanel();
        btns.add(loginBtn);
        btns.add(registerBtn);
        btns.add(resetBtn);
        btns.add(exitBtn);

        add(p, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        loginBtn.addActionListener(e -> doLogin());
        registerBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new RegisterFrame().setVisible(true);
            });
            dispose();
        });
        resetBtn.addActionListener(e -> {
            emailField.setText("");
            passwordField.setText("");
        });
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String pwd = new String(passwordField.getPassword());
        if (email.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide email and password");
            return;
        }
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            UserDAO dao = new UserDAO();
            User u = dao.getUserByEmailAndPassword(email, pwd);
            if (u != null) {
                Session.getInstance().setUser(u);
                SwingUtilities.invokeLater(() -> {
                    if ("admin".equalsIgnoreCase(u.getRole())) {
                        new AdminDashboard().setVisible(true);
                    } else {
                        new UserDashboard().setVisible(true);
                    }
                });
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
