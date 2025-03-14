package gui_package;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Add extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailTxt;
	private JTextField firstNameTxt;
	private JTextField passwordTxt;
	private JTextField passwordValidatorTxt;
	private JTextField lastNameTxt;
    private JComboBox<String> roleComboBox;
    private JLabel empErrorLbl, passErrorTxt;
    private static Users users;  


	/**
	 * Create the frame.
	 */
public Add(Users users) { 
        this.users = users;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAddEmployee = new JLabel("Add User");
        lblAddEmployee.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 30));
        lblAddEmployee.setBounds(264, 37, 141, 51);
        contentPane.add(lblAddEmployee);

        JLabel lblFirstName = new JLabel("Enter first name:");
        lblFirstName.setBounds(43, 110, 221, 16);
        contentPane.add(lblFirstName);

        firstNameTxt = new JTextField();
        firstNameTxt.setBounds(43, 126, 221, 26);
        contentPane.add(firstNameTxt);

        JLabel lblLastName = new JLabel("Enter last name:");
        lblLastName.setBounds(43, 180, 221, 16);
        contentPane.add(lblLastName);

        lastNameTxt = new JTextField();
        lastNameTxt.setBounds(43, 196, 221, 26);
        contentPane.add(lastNameTxt);

        JLabel lblEmail = new JLabel("Enter email:");
        lblEmail.setBounds(43, 249, 221, 16);
        contentPane.add(lblEmail);

        emailTxt = new JTextField();
        emailTxt.setBounds(43, 265, 221, 26);
        contentPane.add(emailTxt);

        empErrorLbl = new JLabel("");
        empErrorLbl.setForeground(Color.RED);
        empErrorLbl.setBounds(43, 293, 300, 16);
        contentPane.add(empErrorLbl);

        JLabel lblRole = new JLabel("Choose role/department:");
        lblRole.setBounds(297, 110, 221, 16);
        contentPane.add(lblRole);

        roleComboBox = new JComboBox<>(new String[]{"Customer Service", "IT", "Manager", "Admin"});
        roleComboBox.setBounds(297, 127, 259, 27);
        contentPane.add(roleComboBox);

        JLabel lblPassword = new JLabel("Enter password:");
        lblPassword.setBounds(297, 180, 221, 16);
        contentPane.add(lblPassword);

        passwordTxt = new JTextField();
        passwordTxt.setBounds(297, 196, 221, 26);
        contentPane.add(passwordTxt);

        passErrorTxt = new JLabel("");
        passErrorTxt.setForeground(Color.RED);
        passErrorTxt.setBounds(297, 221, 300, 16);
        contentPane.add(passErrorTxt);

        JLabel lblConfirmPassword = new JLabel("Re-enter password:");
        lblConfirmPassword.setBounds(297, 249, 221, 16);
        contentPane.add(lblConfirmPassword);

        passwordValidatorTxt = new JTextField();
        passwordValidatorTxt.setBounds(297, 265, 221, 26);
        contentPane.add(passwordValidatorTxt);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(411, 330, 117, 29);
        contentPane.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(282, 330, 117, 29);
        contentPane.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                    new AdminSection(users).setVisible(true);
                }
            }
        });
    }
	private void saveUser() {
        String firstName = firstNameTxt.getText().trim();
        String lastName = lastNameTxt.getText().trim();
        String email = emailTxt.getText().trim();
        String password = passwordTxt.getText();
        String confirmPassword = passwordValidatorTxt.getText();
        String role = (String) roleComboBox.getSelectedItem();

        // FOR REG EXXX - Only caters ---@--.com
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        boolean isEmailValid = Pattern.matches(emailRegex, email);
        boolean isPasswordValid = password.length() >= 8 && password.equals(confirmPassword);

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isEmailValid) {
            empErrorLbl.setText("Invalid email format!");
            return;
        } else {
            empErrorLbl.setText("");
        }
        if (!isPasswordValid) {
            passErrorTxt.setText("Password must be at least 8 chars and match!");
            return;
        } else {
            passErrorTxt.setText("");
        }

        //ONCE THE TRANSACTION/INPUTTING OF VALUES IS DONE
        users.addUser(firstName, lastName, email, role, password);

        JOptionPane.showMessageDialog(this, "User successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new AdminSection(users).setVisible(true);
    }
	

}
