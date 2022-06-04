import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmergencyInformation extends JFrame{
    private JComboBox activityComboBox;
    private JTextField emergencyInformationTextField;
    private JButton saveButton;
    private JButton backButton;
    private JCheckBox yesCheckBox;
    private JPanel emergencyInfo;
    private JTextField lockerTextField;
    private boolean completed=false;

    private String activityName;
    private String contactPhone;
    private String lockerNumber;
    private String getMassActivityNameQuery="select * from massActivityNames";
    private String getIndividualActivityNameQuery="select * from individualActivityNames";
    private boolean activityDeleteClicked=false;
    private boolean activitySelected=false;
    private boolean hourSelected=false;

    private int clickedCounter=0;
    private int massCounter=0;

    public EmergencyInformation() {
        add(emergencyInfo);
        setSize(320,400);
        setTitle("Emergency Info Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,100);
        setResizable(false);

        ResultSet rs=databaseManager.list(getMassActivityNameQuery);
        String names=null;
        try {
            while (rs.next()) {
                names=rs.getString("mass_activity_name");
                activityComboBox.addItem(names);
                massCounter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs2=databaseManager.list(getIndividualActivityNameQuery);
        String names2=null;
        try {
            while (rs2.next()) {
                names2=rs2.getString("individual_activity_name");
                activityComboBox.addItem(names2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnimatorPage ap=new AnimatorPage();
                ap.setVisible(true);
                dispose();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmergency();
                if(completed==true){
                    emergencyInformationTextField.setText("");
                }
                if(clickedCounter%2==1){
                    if(activityComboBox.getSelectedIndex()<massCounter){
                        databaseManager.deleteMassActivity(activityComboBox.getSelectedItem()+"");
                    }else {
                        databaseManager.deleteIndividualActivity(activityComboBox.getSelectedItem() + "");
                    }
                }
                completed=false;
            }
        });
        yesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCounter++;
            }
        });
    }

    private void addEmergency() {
        String activityName = ""+activityComboBox.getSelectedItem();
        String emergencyInfo = emergencyInformationTextField.getText();
        String lockerNumber=lockerTextField.getText();
        if(emergencyInfo.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter All Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completed=true;
        addToDB(activityName,emergencyInfo,lockerNumber);
    }

    private void addToDB(String activityName, String emergencyInfo,String lockerNumber) {
        String animPhone=AnimatorLoginPage.getAnimPhoneNumber();
        String query=null;
        String activity=null;
        if(activityComboBox.getSelectedIndex()<massCounter){
            query=("select mass_activity_id from createdmassactivity where mass_activity_name='"+activityName+"'");
            activity="mass_activity_id";

        }else {
            query=("select individual_activity_id from createdindiviualactivity where individual_activity_name='"+activityName+"'");
            activity="individual_activity_id";
        }
        ResultSet list=databaseManager.list(query);
        int activityId=0;
        try {
            while (list.next()) {
                activityId=list.getInt(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseManager.addEmergencyInformation(activityId,animPhone,emergencyInfo,lockerNumber);

        return;
    }
}
