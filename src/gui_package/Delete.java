package gui_package;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Delete extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField emailTxt;
    private JTable userToDeleteTable;
    private DefaultTableModel tableModel;
    private Users users; 

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Delete frame = new Delete(new Users());
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Delete(Users users) {
        this.users = users; 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDeleteUsers = new JLabel("DELETE USERS");
        lblDeleteUsers.setBounds(212, 47, 221, 16);
        contentPane.add(lblDeleteUsers);

        emailTxt = new JTextField();
        emailTxt.setBounds(25, 117, 221, 26);
        contentPane.add(emailTxt);
        emailTxt.setColumns(10);

        JLabel lblFindEmail = new JLabel("Find email:");
        lblFindEmail.setBounds(25, 101, 221, 16);
        contentPane.add(lblFindEmail);

        JButton searchEmailBtn = new JButton("Find");
        searchEmailBtn.setBounds(297, 117, 117, 29);
        contentPane.add(searchEmailBtn);

        JLabel foundLbl = new JLabel("");
        foundLbl.setBounds(35, 155, 300, 16);
        contentPane.add(foundLbl);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(36, 180, 700, 50);
        contentPane.add(scrollPane);

        userToDeleteTable = new JTable();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"First Name", "Last Name", "Email", "Role"});
        userToDeleteTable.setModel(tableModel);
        scrollPane.setViewportView(userToDeleteTable);

        JButton deleteUserButton = new JButton("Delete");
        deleteUserButton.setBounds(249, 279, 117, 29);
        deleteUserButton.setEnabled(false); 
        contentPane.add(deleteUserButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 400, 100, 30);
        contentPane.add(backButton);

        // -- SEARCHING A USER BASED ON THEIR EMAIL
        searchEmailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTxt.getText().trim();

                if (!isValidEmail(email)) {
                    foundLbl.setText("Invalid email format.");
                    deleteUserButton.setEnabled(false);
                    return;
                }

                String[] user = users.getUserByEmail(email);
                if (user != null) {
                    foundLbl.setText("User Found!");
                    tableModel.setRowCount(0); // Clear table
                    tableModel.addRow(new Object[]{user[0], user[1], user[2], user[3]});
                    deleteUserButton.setEnabled(true);
                } else {
                    foundLbl.setText("User Not Found.");
                    tableModel.setRowCount(0);
                    deleteUserButton.setEnabled(false);
                }
            }
        });

        // DELETING THE USER PERMANENTLY
        deleteUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String email = emailTxt.getText().trim();
                    boolean deleted = users.deleteUserByEmail(email);
                    if (deleted) {
                        JOptionPane.showMessageDialog(null, "User deleted successfully.");
                        tableModel.setRowCount(0);
                        foundLbl.setText("User Deleted.");
                        deleteUserButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error deleting user.");
                    }
                }
            }
        });


        backButton.addActionListener(e -> {
            dispose(); 
            new AdminSection(users).setVisible(true); 
        });
    }

    // Email Validation heree
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
