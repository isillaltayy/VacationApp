import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class CreateMassActivityPage extends JFrame{
    private JPanel MassActivityPanel;
    private JTextField nameTextField;
    private JTextField internetLinkTextField;
    private JTextField capacityTextField;
    private JButton addButton;
    private JButton backButton;
    private String activityName;
    private String internetLink;
    private String capacity;
    private boolean completed=false;

    public CreateMassActivityPage(){
        add(MassActivityPanel);
        setSize(320,400);
        setTitle("Mass Activity Page");
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
                animatorCreateActivityPage acap=new animatorCreateActivityPage();
                acap.setVisible(true);
                dispose();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMassActivity();
                if(completed==true){
                    nameTextField.setText("");
                    internetLinkTextField.setText("");
                    capacityTextField.setText("");
                }
                completed=false;
            }
        });
    }

    private void addMassActivity() {
        activityName=nameTextField.getText();
        internetLink=internetLinkTextField.getText();
        capacity=capacityTextField.getText();
        activityName=activityName.toLowerCase();
        System.out.println(activityName);
        if(activityName.isEmpty()||internetLink.isEmpty()||capacity.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter All Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!activityName.matches("[a-zA-Z]+")||activityName.matches(" ")){ //deneme olmazsa "" olanı sil
            JOptionPane.showMessageDialog(this,
                    "Name must contain only characters.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!capacity.matches("\\d*")){ //if contains any (a-z *-..)
            JOptionPane.showMessageDialog(this,
                    "Capacity must contains only digits.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completed=true;

        addToDb(activityName,internetLink,capacity);
    }

    private void addToDb(String activityName, String internetLink, String capacity) {
            CreatedMassActivity createdMassActivity=new CreatedMassActivity();
            int massCapacity=Integer.parseInt(capacity);
            createdMassActivity.massActivityName=activityName;
            createdMassActivity.internetLink=internetLink;
            createdMassActivity.massCapacity=massCapacity;
            databaseManager.addMassActivity(createdMassActivity);
    }
}
