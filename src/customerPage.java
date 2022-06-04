import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class customerPage extends JFrame {
    private JLabel phoneNumberLabel;
    private JTextField phoneNumberTextField;
    private JLabel passwordLabel;
    private JButton signInButton;
    private JPanel customerPanel;
    private JButton backButton;
    private JLabel informationLabel;
    private JPasswordField passwordField;
    private boolean verify;
    private String phoneNumber;
    private static String sendPhoneNumber;

    public static String getPhoneNumber() {
        return sendPhoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.sendPhoneNumber = phoneNumber;
    }

    public customerPage(){
        add(customerPanel);
        setSize(300,400);
        setTitle("Customer Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,100);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to close?", "Close",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                    databaseManager.disconnectDB();
                }
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password;
                phoneNumber=phoneNumberTextField.getText();
                password=passwordField.getText();
                if (phoneNumber.isEmpty()||password.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Fill in the blanks.");
                    return;
                }
                verify=databaseManager.getAuthentication(phoneNumber,password);
                if(verify==true){
                    setPhoneNumber(phoneNumber);
                    appointmentPage ap=new appointmentPage();
                    ap.setVisible(true);
                    dispose();
                }
                else if(verify==false){
                    JOptionPane.showMessageDialog(null,"Wrong. Try Again");
                }
                verify=false;
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             FirstPage fp=new FirstPage();
             fp.setVisible(true);
             dispose();
            }
        });
    }



}
