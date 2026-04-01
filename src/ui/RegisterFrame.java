package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JPasswordField pwdField, confirmField;

    public RegisterFrame() {
        setTitle("Smart Parking - Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx=0; gbc.gridy=0; gbc.anchor = GridBagConstraints.EAST; p.add(new JLabel("Name:"), gbc);
        gbc.gridx=1; gbc.anchor = GridBagConstraints.WEST; nameField = new JTextField(20); p.add(nameField, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.anchor = GridBagConstraints.EAST; p.add(new JLabel("Email:"), gbc);
        gbc.gridx=1; gbc.anchor = GridBagConstraints.WEST; emailField = new JTextField(20); p.add(emailField, gbc);
        gbc.gridx=0; gbc.gridy=2; gbc.anchor = GridBagConstraints.EAST; p.add(new JLabel("Phone:"), gbc);
        gbc.gridx=1; gbc.anchor = GridBagConstraints.WEST; phoneField = new JTextField(15); p.add(phoneField, gbc);
        gbc.gridx=0; gbc.gridy=3; gbc.anchor = GridBagConstraints.EAST; p.add(new JLabel("Password:"), gbc);
        gbc.gridx=1; gbc.anchor = GridBagConstraints.WEST; pwdField = new JPasswordField(20); p.add(pwdField, gbc);
        gbc.gridx=0; gbc.gridy=4; gbc.anchor = GridBagConstraints.EAST; p.add(new JLabel("Confirm:"), gbc);
        gbc.gridx=1; gbc.anchor = GridBagConstraints.WEST; confirmField = new JPasswordField(20); p.add(confirmField, gbc);

        JButton submit = new JButton("Register");
        JButton loginBtn = new JButton("Login");
        JButton resetBtn = new JButton("Reset");
        JButton backBtn = new JButton("Back");
        JPanel btns = new JPanel(); 
        btns.add(submit); 
        btns.add(loginBtn);
        btns.add(resetBtn);
        btns.add(backBtn);

        add(p, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);
        pack(); setLocationRelativeTo(null);

        submit.addActionListener(e -> doRegister());
        loginBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
            dispose();
        });
        resetBtn.addActionListener(e -> {
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            pwdField.setText("");
            confirmField.setText("");
        });
        backBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
            dispose();
        });
    }

    private void doRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String pwd = new String(pwdField.getPassword());
        String conf = new String(confirmField.getPassword());

        if (name.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields");
            return;
        }
        if (!pwd.equals(conf)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Invalid email");
            return;
        }

        User u = new User();
        u.setName(name); u.setEmail(email); u.setPhone(phone); u.setPassword(pwd); u.setRole("user");
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            UserDAO dao = new UserDAO();
            boolean ok = dao.registerUser(u);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registration successful - please login");
                dispose();
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
