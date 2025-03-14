package gui_package;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EmployeeSection extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private Users users;
    private String loggedInUserEmail;

    // Modified constructor to accept Users and loggedInUserEmail
    public EmployeeSection(Users users, String loggedInUserEmail) {
        this.users = users;
        this.loggedInUserEmail = loggedInUserEmail;

        setTitle("Employee Section");
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblViewUsers = new JLabel("VIEW USERS");
        lblViewUsers.setBounds(340, 20, 120, 20);
        contentPane.add(lblViewUsers);

        // TABLE
        tableModel = new DefaultTableModel(new Object[]{"First Name", "Last Name", "Email", "Role"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(50, 100, 700, 300);
        contentPane.add(scrollPane);

        // Back Button (to go back to the previous screen, like a dashboard or home screen)
        JButton backBtn = new JButton("Logout");
        backBtn.setBounds(50, 420, 100, 30);
        contentPane.add(backBtn);
        backBtn.addActionListener(e -> {
            dispose();
            new Magellan_Solutions().setVisible(true); // Go back to login or previous screen
        });

        // Load user data into the table
        loadUsersIntoTable();
    }

    // This method loads users into the table based on the role
    private void loadUsersIntoTable() {
        tableModel.setRowCount(0); // Clear the table first

        List<User> userList = users.getAllUsers(); // Get all users from the database
        for (User user : userList) {
            // If the logged-in user is Admin or Manager, show all users
            if (loggedInUserEmail.equals(user.getEmail())) {
                if("Customer Service Department".equals(user.getRole())) {
                	tableModel.addRow(new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole()});
                }else if("IT Department".equals(user.getRole())) {
                	tableModel.addRow(new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole()});
                }
                
            }
        }
    }

    public static void main(String[] args) {
        // Assuming Users class is already initialized
        Users users = new Users();
        String loggedInUserEmail = "employeeEmail@example.com"; // Pass the logged-in user's email
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmployeeSection frame = new EmployeeSection(users, loggedInUserEmail);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
