package gui_package;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.regex.Pattern;

public class Update extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchEmail;
    private JTextField oldEmailToChangeTxt;
    private JTextField firstNameTxt;
    private JTextField lastNameTxt;
    private JTextField passwordTxt;
    private JComboBox<String> roleComboBox;
    private JLabel foundLbl;
    private JLabel errPasswordLbl;
    private Users users;  // Reference to Users class

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Update frame = new Update();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Update() {
        users = new Users();  // Initialize Users class

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Enter email:");
        lblNewLabel.setBounds(30, 98, 221, 16);
        contentPane.add(lblNewLabel);

        searchEmail = new JTextField();
        searchEmail.setColumns(10);
        searchEmail.setBounds(30, 114, 221, 26);
        contentPane.add(searchEmail);

        foundLbl = new JLabel("");
        foundLbl.setBounds(40, 152, 400, 16);
        contentPane.add(foundLbl);

        JButton searchEmailBtn = new JButton("Find");
        searchEmailBtn.setBounds(302, 114, 117, 29);
        contentPane.add(searchEmailBtn);

        JLabel lblEnterNewEmail = new JLabel("Update email:");
        lblEnterNewEmail.setBounds(30, 207, 221, 16);
        contentPane.add(lblEnterNewEmail);

        oldEmailToChangeTxt = new JTextField();
        oldEmailToChangeTxt.setColumns(10);
        oldEmailToChangeTxt.setBounds(30, 224, 221, 26);
        oldEmailToChangeTxt.setEditable(false); // Disable editing
        contentPane.add(oldEmailToChangeTxt);

        JLabel lblFirstName = new JLabel("Update first name:");
        lblFirstName.setBounds(30, 267, 221, 16);
        contentPane.add(lblFirstName);

        firstNameTxt = new JTextField();
        firstNameTxt.setColumns(10);
        firstNameTxt.setBounds(30, 284, 221, 26);
        contentPane.add(firstNameTxt);

        JLabel lblLastName = new JLabel("Update last name:");
        lblLastName.setBounds(30, 327, 221, 16);
        contentPane.add(lblLastName);

        lastNameTxt = new JTextField();
        lastNameTxt.setColumns(10);
        lastNameTxt.setBounds(30, 344, 221, 26);
        contentPane.add(lastNameTxt);

        JLabel lblUpdatePassword = new JLabel("Update password:");
        lblUpdatePassword.setBounds(301, 267, 221, 16);
        contentPane.add(lblUpdatePassword);

        passwordTxt = new JTextField();
        passwordTxt.setColumns(10);
        passwordTxt.setBounds(301, 284, 221, 26);
        contentPane.add(passwordTxt);

        errPasswordLbl = new JLabel("");
        errPasswordLbl.setBounds(301, 310, 221, 16);
        contentPane.add(errPasswordLbl);

        JLabel lblRole = new JLabel("Choose role/department:");
        lblRole.setBounds(301, 206, 221, 16);
        contentPane.add(lblRole);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "Manager", "Customer Service Department", "IT Department"});
        roleComboBox.setBounds(301, 223, 259, 27);
        contentPane.add(roleComboBox);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(302, 344, 117, 29);
        contentPane.add(updateButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 10, 100, 25);
        contentPane.add(backButton);

        JLabel lblUpdateUsers = new JLabel("UPDATE USERS");
        lblUpdateUsers.setBounds(217, 44, 221, 16);
        contentPane.add(lblUpdateUsers);

        // SEARCHING AN EMAIL AND SETTING THEIR RECORD
        searchEmailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = searchEmail.getText().trim().toLowerCase();
                if (!isValidEmail(email)) {
                    foundLbl.setText("Invalid email format");
                    return;
                }

                String[] user = users.getUserByEmail(email);
                if (user == null) {
                    foundLbl.setText("No user found");
                } else {
                    foundLbl.setText("User found");
                    oldEmailToChangeTxt.setText(user[2]); // email(ACTING AS PRIMARY KEY)
                    firstNameTxt.setText(user[0]); // fname
                    lastNameTxt.setText(user[1]); // lnamee
                    passwordTxt.setText(user[4]); // Password of user
                    roleComboBox.setSelectedItem(user[3]); // Role/ deptt
                }
            }
        });

        // Updatingg the user
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (passwordTxt.getText().length() < 8) {
                    errPasswordLbl.setText("Password must be at least 8 characters!");
                    return;
                }

                String email = oldEmailToChangeTxt.getText();
                String firstName = firstNameTxt.getText();
                String lastName = lastNameTxt.getText();
                String password = passwordTxt.getText();
                String role = (String) roleComboBox.getSelectedItem();

                users.updateUser(email, firstName, lastName, role, password);
                foundLbl.setText("User updated successfully!");
            }
        });


        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new AdminSection(users).setVisible(true); 
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }
}
