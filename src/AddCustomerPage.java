import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class AddCustomerPage extends JFrame{
    private JTextField nameTextField;
    private JTextField ageTextField;
    private JTextField roomNumberTextField;
    private JTextField contactPhoneTextField;
    private JButton addButton;
    private JButton backButton;
    private JPanel addCustomerPanel;
    private boolean completed=false;

    public AddCustomerPage(){
        add(addCustomerPanel);
        setSize(320,400);
        setTitle("Create a new customer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,100);

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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerPage mp=new ManagerPage();
                mp.setVisible(true);
                dispose();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
                if(completed==true){
                    nameTextField.setText("");
                    ageTextField.setText("");
                    roomNumberTextField.setText("");
                    contactPhoneTextField.setText("");
                }
               completed=false;
            }
        });
    }
    private void addUser() {
        String name=nameTextField.getText();
        String age=ageTextField.getText();
        String roomNumber=roomNumberTextField.getText();
        String contactPhone=contactPhoneTextField.getText();
        if(name.isEmpty()||age.isEmpty()||roomNumber.isEmpty()||contactPhone.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter All Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(name.length()>20){
            JOptionPane.showMessageDialog(this,
                    "Character limit exceeded.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(age.length()>3){
            JOptionPane.showMessageDialog(this,
                    "Name is wrong.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(roomNumber.length()>4){
            JOptionPane.showMessageDialog(this,
                    "The room number was entered incorrectly.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(contactPhone.length()!=10)
        {
            JOptionPane.showMessageDialog(this,
                    "Phone number length must be 10 digits.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!name.matches("[a-zA-Z]+")){
            JOptionPane.showMessageDialog(this,
                    "Name must contain only characters.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completed=true;
        addToDB(name,age,roomNumber,contactPhone);
    }
    private void addToDB(String name, String age, String roomNumber, String contactPhone) {
        User user=new User();
        int customerAge=Integer.parseInt(age);
        int roomNum=Integer.parseInt(roomNumber);
        user.customerName=name;
        user.age=customerAge;
        user.roomNumber=roomNum;
        user.contactPhone=contactPhone;
        databaseManager.addCustomer(user);
    }
}
