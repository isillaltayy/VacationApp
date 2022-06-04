import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class IndividualActivityPage extends JFrame{
    private JComboBox activityComboBox;
    private JLabel date;
    private JComboBox hourComboBox;
    private JButton chooseButton;
    private JButton backButton;
    private JPanel jpCald;
    private JPanel individualActivityPanel;
    private JLabel ageLimitLabel;
    private String activityName;
    private String activityDate;
    private String activityHour;
    private ArrayList<Integer> ageLimitList;
    private int hourIndex;
    private int activityIndex;
    private String getActivityNameQuery="select * from individualActivityNames";
    private String getAgeLimitQuery= "select * from individualActivityAgeLimit";
    private boolean activityEnrolled=false;

    Calendar calendar=Calendar.getInstance();
    JDateChooser dateChooser=new JDateChooser(calendar.getTime());

    public IndividualActivityPage(){
        add(individualActivityPanel);
        setSize(320,400);
        setTitle("Individual Activity Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,100);
        setResizable(false);
        dateChooser.setDateFormatString("dd-MM-yyyy");
        jpCald.add(dateChooser);
        activityIndex=0;
        hourIndex=0;
        activityHour="09:00-09:50";
        ageLimitList = new ArrayList<Integer>();

        ResultSet rs=databaseManager.list(getActivityNameQuery);
        String names=null;
        try {
            while (rs.next()) {
                names=rs.getString("individual_activity_name");
                activityComboBox.addItem(names);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs2=databaseManager.list(getAgeLimitQuery);
        int ageLimit;
        try {
            while (rs2.next()) {
                ageLimit=rs2.getInt("age_limit");
                ageLimitList.add(ageLimit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ageLimitLabel.setText(ageLimitList.get(0).toString());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure do you want to exit?", "Close",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                    databaseManager.disconnectDB();
                }
            }
        });
        activityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activityName=(""+activityComboBox.getSelectedItem());
                activityIndex=activityComboBox.getSelectedIndex();
                ageLimitLabel.setText(ageLimitList.get(activityIndex).toString());
            }
        });
        hourComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activityHour=(""+hourComboBox.getSelectedItem());
                hourIndex=hourComboBox.getSelectedIndex();
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
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdfmt=new SimpleDateFormat("dd-MM-yyyy");
                activityDate=sdfmt.format(dateChooser.getDate());
                if(calendar.getTime().after(dateChooser.getDate())) { //günümüzden daha önce bir tarih seçilirse
                    JOptionPane.showMessageDialog(null, "Date is invalid! Please try again.",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controlForDB(activityName,activityDate,hourIndex,activityHour);
            }
        });
    }

    private void controlForDB(String activityName, String activityDate, int hourIndex, String activityHour) {
        //activity id
        String query=("select individual_activity_id from createdIndiviualActivity where individual_activity_name='"+activityName+"'");
        ResultSet activityId=databaseManager.list(query);
        int activitId=0;
        try {
            while (activityId.next()) {
                activitId=activityId.getInt("individual_activity_id");
                System.out.println(activitId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //animator id
        String strNew = activityName.replace("personal", "");
        String query2=("select employee_id from animatorinformation where expertise_area='"+strNew+"'");
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
        int customerAge=0;
        String customerPhone=customerPage.getPhoneNumber();
        String query3=("select vacation_village_id, age from customerinformation where contact_phone='"+customerPhone+"'");
        ResultSet employeeID=databaseManager.list(query3);
        try {
            while (employeeID.next()) {
                customerId=employeeID.getInt("vacation_village_id");
                customerAge=employeeID.getInt("age");
                System.out.println(customerAge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(Integer.parseInt(ageLimitLabel.getText())>customerAge){
            JOptionPane.showMessageDialog(null, "Your age is under for this activity!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        addToDB(customerId,activitId,animator_id,activityDate,activityHour);
    }

    private void addToDB(int customerId, int activitId, int animator_id, String activityDate, String activityHour) {
        String query=("select * from individualActivitySelectedCount");
        ResultSet count=databaseManager.list(query);
        int counter=0;
        String hours="";
        String dates="";
        int deme;
        try {
            while (count.next()) {
                deme=count.getInt("activity_id");
                dates=count.getString("activity_date");
                hours=count.getString("activity_hour");
                counter=count.getInt("count");
                if(hours.equals(activityHour)&&dates.equals(activityDate)){
                    JOptionPane.showMessageDialog(null,"The activity has been selected. Please try another option.");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseManager.addAppointmentIndividual(customerId,activitId,animator_id,activityDate,activityHour);
    }

}
