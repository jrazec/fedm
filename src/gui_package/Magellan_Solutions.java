package gui_package;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.Color;

public class Magellan_Solutions extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private Map<String, String> credentials;
    private List<User> users;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Magellan_Solutions frame = new Magellan_Solutions();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    class User {
        private String firstName;
        private String middleName;
        private String lastName;
        private String email;
        private String department;
        private String userType;
        private String password;
        private String userId;

        public User(String firstName, String middleName, String lastName, 
                   String email, String department, String userType, 
                   String password, String userId) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.email = email;
            this.department = department;
            this.userType = userType;
            this.password = password;
            this.userId = userId;
        }

        public String getUserId() { return userId; }
        public String getFirstName() { return firstName; }
        public String getMiddleName() { return middleName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public String getDepartment() { return department; }
        public String getUserType() { return userType; }
        public String getPassword() { return password; }
    }

    public Magellan_Solutions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 805, 479);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        users = new ArrayList<>();

        JLabel lblTitle = new JLabel("Magellan's Solutions");
        lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblTitle.setBounds(290, 49, 195, 35);
        contentPane.add(lblTitle);

        JLabel lblUsername = new JLabel("Enter username:");
        lblUsername.setBounds(280, 150, 129, 16);
        contentPane.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(410, 145, 130, 26);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Enter password:");
        lblPassword.setBounds(280, 200, 129, 16);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(410, 195, 130, 26);
        contentPane.add(passwordField);

        JLabel lblRole = new JLabel("Select role:");
        lblRole.setBounds(280, 250, 129, 16);
        contentPane.add(lblRole);

        roleComboBox = new JComboBox<>();
        roleComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Admin", "Manager", "Employee"}));
        roleComboBox.setBounds(410, 245, 131, 27);
        contentPane.add(roleComboBox);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(350, 300, 100, 30);
        contentPane.add(loginButton);

        initializeCredentials();
        loginButton.addActionListener(e -> handleLogin());
    }

    private void initializeCredentials() {
        credentials = new HashMap<>();
        credentials.put("Admin", "Admin123");
        credentials.put("Manager", "Manager123");
        credentials.put("Employee", "Employee123");
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String selectedRole = (String) roleComboBox.getSelectedItem();

        // Check hardcoded credentials first
        if (credentials.containsKey(selectedRole) && 
            credentials.get(selectedRole).equals(password) && 
            username.equals(selectedRole)) {
            if (selectedRole.equals("Admin")) {
                showAdminScreen(username);
            } else {
                showWelcomeScreen(username);
            }
            return;
        }

        // Check if user exists in the users list
        for (User user : users) {
            if (user.getUserType().equals(selectedRole) && 
                user.getPassword().equals(password) && 
                user.getUserId().equals(username)) {
                showWelcomeScreen(username);
                return;
            }
        }

        // If no match found, show error
        JOptionPane.showMessageDialog(this, 
            "Invalid credentials. Please try again.", 
            "Login Failed", 
            JOptionPane.ERROR_MESSAGE);
    }

    private void showWelcomeScreen(String username) {
        contentPane.removeAll();
        contentPane.repaint();
        contentPane.revalidate();

        JLabel welcomeLabel = new JLabel("Welcome " + username + "!");
        welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
        welcomeLabel.setBounds(300, 200, 300, 50);
        contentPane.add(welcomeLabel);
    }

    private void showAdminScreen(String username) {
        setBounds(100, 100, 805, 579);
        contentPane.removeAll();
        contentPane.repaint();
        contentPane.revalidate();

        JLabel welcomeLabel = new JLabel("Welcome " + username + "!");
        welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
        welcomeLabel.setBounds(300, 50, 300, 50);
        contentPane.add(welcomeLabel);

        JButton addButton = new JButton("Add");
        addButton.setBounds(325, 150, 150, 40);
        addButton.addActionListener(e -> showAddUserForm());
        contentPane.add(addButton);

        String[] otherButtons = {"Update", "Delete", "View", "Audit Log", "Logout"};
        int yOffset = 210;
        for (String buttonLabel : otherButtons) {
            JButton button = new JButton(buttonLabel);
            button.setBounds(325, yOffset, 150, 40);
            if (buttonLabel.equals("Logout")) {
                button.addActionListener(e -> logout());
            }
            contentPane.add(button);
            yOffset += 60;
        }
    }

    private void logout() {
        dispose();
        new Magellan_Solutions().setVisible(true);
    }

    private void showAddUserForm() {
        contentPane.removeAll();
        contentPane.repaint();
        contentPane.revalidate();

        String[] labels = {"First Name:", "Middle Name:", "Last Name:", 
                           "Email:", "Password:", "Department:", "User Type:"};
        
        // Initialize array with correct size
        JTextField[] textFields = new JTextField[labels.length];
        
        int yOffset = 50;
        // Initialize all text fields
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(200, yOffset, 100, 30);
            contentPane.add(label);

            // Initialize JTextField here
            textFields[i] = new JTextField();
            textFields[i].setBounds(300, yOffset, 200, 30);
            contentPane.add(textFields[i]);
            
            // Skip adding JComboBoxes here
            yOffset += 40;
        }

        // Add department JComboBox after text fields
        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setBounds(200, yOffset, 100, 30);
        contentPane.add(deptLabel);

        String[] departments = {"IT", "HR", "Finance"};
        JComboBox<String> deptComboBox = new JComboBox<>(departments);
        deptComboBox.setBounds(300, yOffset, 200, 30);
        contentPane.add(deptComboBox);
        yOffset += 40;

        // Add user type JComboBox
        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setBounds(200, yOffset, 100, 30);
        contentPane.add(userTypeLabel);

        String[] userTypes = {"Admin", "Manager", "Employee"};
        JComboBox<String> userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.setBounds(300, yOffset, 200, 30);
        contentPane.add(userTypeComboBox);
        yOffset += 40;

        JButton backButton = new JButton("Back");
        backButton.setBounds(200, yOffset, 100, 30);
        backButton.addActionListener(e -> showAdminScreen("Admin"));
        contentPane.add(backButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(400, yOffset, 100, 30);
        submitButton.addActionListener(e -> {
            // Get values from fields
            String firstName = textFields[0].getText();
            String middleName = textFields[1].getText();
            String lastName = textFields[2].getText();
            String email = textFields[3].getText();
            String department = (String) deptComboBox.getSelectedItem();
            String userType = (String) userTypeComboBox.getSelectedItem();
            String password = textFields[4].getText();  // Changed from index 5 to 4
            
            // Create new user
            User newUser = new User(
                firstName, middleName, lastName, 
                email, department, userType, 
                password, email  // Use email as userId
            );

            // Add to users list
            users.add(newUser);

            // Show success message
            JOptionPane.showMessageDialog(this, 
                "User added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);

            // Go back to admin screen
            showAdminScreen("Admin");
        });
        contentPane.add(submitButton);
    }
}