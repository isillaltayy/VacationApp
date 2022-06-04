import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentAdding extends JFrame{
    private JTextField equipmentNameTextField;
    private JTextField purposeTextField;
    private JButton addButton;
    private JButton backButton;
    private JPanel equipmentAdding;
    private boolean completed=false;


    public EquipmentAdding(){
        add(equipmentAdding);
        setSize(320,400);
        setTitle("Animator Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,100);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "Do you want to close?", "Close",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                    databaseManager.disconnectDB();
                }

            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEquipment();
                if(completed==true){
                    equipmentNameTextField.setText("");
                    purposeTextField.setText("");
                }
                completed=false;
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnimatorPage ap=new AnimatorPage();
                ap.setVisible(true);
                dispose();
            }
        });
    }

    private void addEquipment() {
        String equipName=equipmentNameTextField.getText();
        String purpose=purposeTextField.getText();
        if(equipName.isEmpty()||purpose.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter All Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(equipName.length()>40||purpose.length()>50){
            JOptionPane.showMessageDialog(this,
                    "Character limit exceeded.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completed=true;
        addToDB(equipName,purpose);
    }

    private void addToDB(String equipName, String purpose) {
        String phoneNumber =AnimatorLoginPage.getAnimPhoneNumber();

        String query=("select employee_id from animatorinformation where phone_number='"+phoneNumber+"'");
        ResultSet employeeSSN=databaseManager.list(query);
        int animSSN=0;
        try {
            while (employeeSSN.next()) {
                animSSN=employeeSSN.getInt("employee_id");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseManager.addEquipment(equipName,purpose,animSSN);
    }
}

