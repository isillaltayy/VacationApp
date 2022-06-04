import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEquipPersonPage extends JFrame{
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField phoneNumberTextField;
    private JButton addButton;
    private JButton backButton;
    private JPanel equipPersonPanel;
    private boolean completed=false;

    public AddEquipPersonPage(){
        add(equipPersonPanel);
        setSize(320,400);
        setTitle("Create a equip person");
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
                addEquipPerson();
                if(completed==true){
                    nameTextField.setText("");
                    surnameTextField.setText("");
                    phoneNumberTextField.setText("");
                }
                completed=false;
            }
        });
    }
    private void addEquipPerson() {
        String name=nameTextField.getText();
        String surname=surnameTextField.getText();
        String phoneNumber=phoneNumberTextField.getText();

        if(name.isEmpty()||surname.isEmpty()||phoneNumber.isEmpty()){
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
        if(surname.length()>20){
            JOptionPane.showMessageDialog(this,
                    "Character limit exceeded.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(phoneNumber.length()!=10)
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
        if(!surname.matches("[a-zA-Z]+")){
            JOptionPane.showMessageDialog(this,
                    "Surname must contain only characters.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completed=true;
        addToDB(name,surname,phoneNumber);
    }

    private void addToDB(String name, String surname, String phoneNumber) {
        EquipPersonUser equipPersonUser=new EquipPersonUser();
        equipPersonUser.equipPersonName=name;
        equipPersonUser.equipPersonSurname=surname;
        equipPersonUser.contact_phone=phoneNumber;
        databaseManager.addEquipPerson(equipPersonUser);
    }
}
