import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateIndividualActivityPage extends JFrame {


    private JTextField nameTextField;
    private JTextField internetLinkTextField;
    private JTextField ageTextField;
    private JButton addButton;
    private JButton backButton;
    private JPanel IndividualActivityPanel;
    private boolean completed=false;
    private String activityName;
    private String internetLink;
    private String age;

    public CreateIndividualActivityPage() {

        add(IndividualActivityPanel);
        setSize(320,400);
        setTitle("Individual Activity");
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
                addIndividualActivity();
                if(completed==true){
                    nameTextField.setText("");
                    internetLinkTextField.setText("");
                    ageTextField.setText("");
                }
                completed=false;
            }
        });
    }

    private void addIndividualActivity() {
        activityName=nameTextField.getText();
        internetLink=internetLinkTextField.getText();
        age=ageTextField.getText();
        activityName=activityName.toLowerCase();
        if(activityName.isEmpty()||internetLink.isEmpty()||age.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter All Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!activityName.matches("[a-zA-Z]+")||activityName.matches(" ")){ //deneme olmazsa "" olanı sil
            JOptionPane.showMessageDialog(this,
                    "Activity name must contain only characters.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!age.matches("\\d*")){ //if contains any (a-z *-..)
            JOptionPane.showMessageDialog(this,
                    "Age limit must contains only digits.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completed=true;

        addToDb(activityName,internetLink,age);
    }

    private void addToDb(String activityName, String internetLink, String age) {
        CreatedIndividualActivity createdIndividualActivity=new CreatedIndividualActivity();
        int ageLimit=Integer.parseInt(age);
        createdIndividualActivity.individualActivity=activityName;
        createdIndividualActivity.internetLink=internetLink;
        createdIndividualActivity.ageLimit=ageLimit;
        databaseManager.addIndividualActivity(createdIndividualActivity);
    }
}
