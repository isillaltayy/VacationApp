import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAnimatorPage extends JFrame{
    private JTextField AnimatorNameTextField;
    private JTextField phoneNumberTextField;
    private JButton addAnimatorButton;
    private JButton backButton;
    private JPanel addAnimatorPanel;
    private JTextField expertiseAreaTextField;
    private boolean completed=false;

    public AddAnimatorPage(){
        add(addAnimatorPanel);
        setSize(320,400);
        setTitle("Create a new animator");
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
        addAnimatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnimator();
                if(completed==true){
                    AnimatorNameTextField.setText("");
                    phoneNumberTextField.setText("");
                    expertiseAreaTextField.setText("");
                }
                completed=false;
            }
        });
    }
    private void addAnimator() {
        String animatorName=AnimatorNameTextField.getText();
        String phoneNumber=phoneNumberTextField.getText();
        String expertiseArea=expertiseAreaTextField.getText();
        if(animatorName.isEmpty()||phoneNumber.isEmpty()||expertiseArea.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter All Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(animatorName.length()>20){
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
        if(expertiseArea.length()>20){
            JOptionPane.showMessageDialog(this,
                    "Character limit exceeded.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!animatorName.matches("[a-zA-Z]+")){
            JOptionPane.showMessageDialog(this,
                    "Name must contain only characters.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completed=true;
        addToDB(animatorName,phoneNumber,expertiseArea);
    }

    private void addToDB(String animatorName, String phoneNumber, String expertiseArea) {
        AnimatorUser animatorUser = new AnimatorUser();
        animatorUser.animatorName=animatorName;
        animatorUser.contactPhone=phoneNumber;
        animatorUser.expertiseArea=expertiseArea;
        databaseManager.addAnimator(animatorUser);
    }
}
