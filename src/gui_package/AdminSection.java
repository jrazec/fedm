package gui_package;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class AdminSection extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static Users users;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    users = new Users(); // Initializing users 
                    AdminSection frame = new AdminSection(users);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AdminSection(Users users) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(30, 144, 255)); // Blue background
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Add Employee Button
        JButton addBtn = new JButton("Add Employee");
        addBtn.setBounds(300, 80, 200, 40);
        addBtn.addActionListener(e -> openFrame(new Add(users)));
        contentPane.add(addBtn);

        // View Employee Button
        JButton btnEditEmployee = new JButton("View Employee");
        btnEditEmployee.setBounds(300, 130, 200, 40);
        btnEditEmployee.addActionListener(e -> openFrame(new View(users)));
        contentPane.add(btnEditEmployee);

        // Update Employee Button
        JButton btnUpdateEmployee = new JButton("Update Employee");
        btnUpdateEmployee.setBounds(300, 180, 200, 40);
        btnUpdateEmployee.addActionListener(e -> openFrame(new Update()));
        contentPane.add(btnUpdateEmployee);

        // Delete Employee Button
        JButton btnDeleteEmployee = new JButton("Delete Employee");
        btnDeleteEmployee.setBounds(300, 230, 200, 40);
        btnDeleteEmployee.addActionListener(e -> openFrame(new Delete(users)));
        contentPane.add(btnDeleteEmployee);

        // View Audit Logs Button
        JButton btnViewAuditLogs = new JButton("View Audit Logs");
        btnViewAuditLogs.setBounds(300, 280, 200, 40);
        btnViewAuditLogs.addActionListener(e -> openFrame(new AuditLogs()));
        contentPane.add(btnViewAuditLogs);

        // Log Out Button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBounds(300, 350, 200, 40);
        logoutButton.addActionListener(e -> {
            dispose();  // Close the AdminSection
            new Magellan_Solutions().setVisible(true);  // Open the login screen
        });
        contentPane.add(logoutButton);
    }

    private void openFrame(JFrame frame) {
        frame.setVisible(true);
        dispose();
    }
}
