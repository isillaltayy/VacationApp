import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MassActivityPage extends JFrame{
    private JPanel massActivityPanel;
    private JComboBox comboBox1;
    private JComboBox comboBox3;
    private JButton chooseActivityButton;
    private JButton backButton;
    private JPanel jpCald;
    private JLabel capactiySetTextLabel;
    private JLabel enrolledCounterLabel;
    private String activityName;
    private String activityDate;
    private String activityHour;
    private int activityIndex;
    private int hourIndex;
    private String getActivityNameQuery="select * from massActivityNames";
    private String getCapacityQuery= "select * from massActivityCapacity";
    private ArrayList<Integer> capacityList=null;
    private boolean activityEnrolled=false;
    private boolean activitySelected=false;
    private boolean hourSelected=false;

    Calendar calendar=Calendar.getInstance();
    JDateChooser dateChooser=new JDateChooser(calendar.getTime());

    public MassActivityPage(){
        add(massActivityPanel);
        setSize(320,400);
        setTitle("Mass Activity Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,100);
        setResizable(false);
        dateChooser.setDateFormatString("dd-MM-yyyy");
        jpCald.add(dateChooser);
        activityIndex=0;
        hourIndex=0;
        capacityList = new ArrayList<Integer>();

        ResultSet rs=databaseManager.list(getActivityNameQuery);
        String names=null;
        try {
            while (rs.next()) {
                names=rs.getString("mass_activity_name");
                comboBox1.addItem(names);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs2=databaseManager.list(getCapacityQuery);
        int capacities;
        try {
            while (rs2.next()) {
                capacities=rs2.getInt("capacity");
                capacityList.add(capacities);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        capactiySetTextLabel.setText(capacityList.get(0).toString());
        enrolledCounterLabel.setText("-");



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
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activityName=(""+comboBox1.getSelectedItem());
                activityIndex=comboBox1.getSelectedIndex();
                capactiySetTextLabel.setText(capacityList.get(activityIndex).toString());
                enrolledCounterLabel.setText("-");
                activitySelected=true;
            }
        });
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activityHour=(""+comboBox3.getSelectedItem());
                hourIndex=comboBox3.getSelectedIndex();
                enrolledCounterLabel.setText("-");
                hourSelected=true;
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appointmentPage ap=new appointmentPage();
                ap.setVisible(true);
                dispose();
            }
        });
        chooseActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(activitySelected==false||hourSelected==false){
                    JOptionPane.showMessageDialog(null,"Choose an activity or hour");
                    return;
                }
                activitySelected=false;
                hourSelected=false;
                SimpleDateFormat sdfmt=new SimpleDateFormat("dd-MM-yyyy");
                activityDate=sdfmt.format(dateChooser.getDate());
                if(calendar.getTime().after(dateChooser.getDate())) { //günümüzden daha önce bir tarih seçilirse
                    JOptionPane.showMessageDialog(null, "Date is invalid! Please try again.",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(Integer.parseInt(capactiySetTextLabel.getText())<=0){
                    JOptionPane.showMessageDialog(null, "Capacity is full!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controlForDB(activityName,activityDate,hourIndex,activityHour);
            }
        });



    }
    private void controlForDB(String activityName, String activityDate, int hourIndex, String activityHour) {
        //activity id
        String query=("select mass_activity_id from createdmassactivity where mass_activity_name='"+activityName+"'");
        ResultSet activityId=databaseManager.list(query);
        int activitId=0;
        try {
            while (activityId.next()) {
                activitId=activityId.getInt("mass_activity_id");
                //System.out.println(activitId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //animator id
        String query2=("select employee_id from animatorinformation where expertise_area='"+activityName+"'");
        ResultSet animatorID=databaseManager.list(query2);
        int animator_id=0;
        try {
            while (animatorID.next()) {
                animator_id=animatorID.getInt("employee_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //customerid
        int customerId = 0;
        String customerPhone=customerPage.getPhoneNumber();
        String query3=("select vacation_village_id from customerinformation where contact_phone='"+customerPhone+"'");
        ResultSet employeeID=databaseManager.list(query3);
        try {
            while (employeeID.next()) {
                customerId=employeeID.getInt("vacation_village_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addToDB(customerId,activitId,animator_id,activityDate,activityHour);
    }

    private void addToDB(int customerId, int activityId,int animatorId, String activityDate, String activityHour) {
        //activity_id - activity_date - activity_hour

        databaseManager.addAppointment(customerId,activityId,animatorId,activityDate,activityHour);

        String query=("select * from massActivitySelectedCount");
        ResultSet count=databaseManager.list(query);
        int counter=0;
        String hours="";
        String dates="";
        int totalCapacity =Integer.parseInt(capactiySetTextLabel.getText());
        int deme;
        try {
            while (count.next()) {
                deme=count.getInt("activity_id");
                dates=count.getString("activity_date");
                hours=count.getString("activity_hour");
                counter=count.getInt("count");
                if(hours.equals(activityHour)&&dates.equals(activityDate)){
                    totalCapacity-=counter;
                    enrolledCounterLabel.setText(""+totalCapacity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(Integer.parseInt(enrolledCounterLabel.getText())<=0){
            JOptionPane.showMessageDialog(null, "Capacity is full!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }




    }
}
