package gui_package;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

// FOR TABLEE COLUMN HEADERR
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable userTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> roleFilter;
    private JTextField searchField;
    private Users users; 


	/**
	 * Create the frame.
	 */
    public View(Users users) {
        this.users = users; 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblViewUsers = new JLabel("VIEW USERS");
        lblViewUsers.setBounds(340, 20, 120, 20);
        contentPane.add(lblViewUsers);

        //  FILTERING
        roleFilter = new JComboBox<>(new String[]{"All", "Admin", "Manager", "Customer Service", "IT"});
        roleFilter.setBounds(50, 60, 150, 25);
        contentPane.add(roleFilter);
        roleFilter.addActionListener(e -> filterUsers());

        // SEARCH
        searchField = new JTextField();
        searchField.setBounds(230, 60, 150, 25);
        contentPane.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(400, 60, 90, 25);
        contentPane.add(searchButton);
        searchButton.addActionListener(e -> searchUsers());

        // TABLEE
        tableModel = new DefaultTableModel(new Object[]{"#", "Email", "Full Name", "Password", "Department"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(50, 100, 700, 300);
        contentPane.add(scrollPane);

        // TO GO BACK IN TE ADMIN SECTION
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(50, 420, 100, 30);
        contentPane.add(backBtn);
        backBtn.addActionListener(e -> {
            dispose();
            new AdminSection(users).setVisible(true);
        });

        // Load users initially
        loadUsers();
    }

    private void loadUsers() {
        tableModel.setRowCount(0); 
        List<User> userList = users.getAllUsers();
        int index = 1;
        for (User user : userList) {
            tableModel.addRow(new Object[]{index++, user.getEmail(), user.getFullName(), user.getPassword(), user.getRole()});
        }
    }

    private void filterUsers() {
        String selectedRole = (String) roleFilter.getSelectedItem();
        tableModel.setRowCount(0); 

        List<User> userList = users.getAllUsers();
        int index = 1;
        for (User user : userList) {
            if ("All".equals(selectedRole) || user.getRole().equals(selectedRole)) {
                tableModel.addRow(new Object[]{index++, user.getEmail(), user.getFullName(), user.getPassword(), user.getRole()});
            }
        }
    }

    private void searchUsers() {
        String searchText = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0); 

        List<User> userList = users.getAllUsers();
        int index = 1;
        for (User user : userList) {
            if (user.getFullName().toLowerCase().contains(searchText)) {
                tableModel.addRow(new Object[]{index++, user.getEmail(), user.getFullName(), user.getPassword(), user.getRole()});
            }
        }
    }
}