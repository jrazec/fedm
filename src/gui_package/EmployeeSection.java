package gui_package;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class EmployeeSection extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private Users users;

    public EmployeeSection() {
        users = new Users(); // Load user data

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Employee - View Users");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setBounds(300, 10, 250, 30);
        contentPane.add(lblTitle);

        // Table setup
        String[] columnNames = {"First Name", "Last Name", "Email", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(30, 50, 720, 350);
        contentPane.add(scrollPane);
        loadUsersIntoTable();

        JButton backButton = new JButton("Back");
        backButton.setBounds(30, 420, 100, 30);
        contentPane.add(backButton);

        // Back button action
        backButton.addActionListener(e -> {
            dispose();
            new Magellan_Solutions().setVisible(true);
        });
    }

    private void loadUsersIntoTable() {
        tableModel.setRowCount(0);
        List<User> userList = users.getAllUsers();
        for (User user : userList) {
            tableModel.addRow(new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole()});
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EmployeeSection frame = new EmployeeSection();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
