import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class databaseManager {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/HW2";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "isil2414P";
    private static Connection connection;

    public databaseManager() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"database not connected");
        }
    }
    public Connection connection(){
        Connection c=null;
        try {
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"database not connected");
        }
        return c;
    }
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void disconnectDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static ResultSet list(String query){
        try {
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void addCustomer (User user){
        try {
            String query=("insert into customerinformation "+
                    "(customer_name, age, room_number , contact_phone)"+
                    "values(?, ?, ?, ?)");
            PreparedStatement preparedStatement=connection.prepareStatement(query);
           // preparedStatement.setString(1,user.vacationVillageId);
            preparedStatement.setString(1,user.customerName);
            preparedStatement.setInt(2,user.age);
            preparedStatement.setInt(3,user.roomNumber);
            preparedStatement.setString(4,user.contactPhone);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            String password = getPassword();
            JOptionPane.showMessageDialog(null, "Customer Password is: "+password);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Try again. Id or phone is already exist.");
        }
    }
    public static void  addAnimator(AnimatorUser animatorUser){
        try {
            String query=("insert into animatorinformation "+
                    "(animator_name, phone_number, expertise_area)"+
                    "values(?, ?, ?)");
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            //preparedStatement.setInt(1,animatorUser.employeeId);
            preparedStatement.setString(1,animatorUser.animatorName);
            preparedStatement.setString(2,animatorUser.contactPhone);
            preparedStatement.setString(3,animatorUser.expertiseArea);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            String password = getPasswordAnim();
            JOptionPane.showMessageDialog(null, "Animator Password is: "+password);
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Try again. Id or phone is already exist.");
        }

    }
    public static void addEquipPerson(EquipPersonUser equipPersonUser){
        try {
            String query=("insert into equippersoninformation"+
                    "(equip_person_name, equip_person_surname, contact_phone)"+
                    "values(?, ?, ?)");
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,equipPersonUser.equipPersonName);
            preparedStatement.setString(2,equipPersonUser.equipPersonSurname);
            preparedStatement.setString(3,equipPersonUser.contact_phone);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null,"New equip person added to the system.");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Try again. Id or phone is already exist.");
        }
    }

    public static void addMassActivity(CreatedMassActivity createdMassActivity) {
        try {
            String query=("insert into createdMassActivity"+
                    "(mass_activity_name, internet_link, capacity)"+
                    "values(?, ?, ?)");
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,createdMassActivity.massActivityName);
            preparedStatement.setString(2,createdMassActivity.internetLink);
            preparedStatement.setInt(3,createdMassActivity.massCapacity);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null,"New mass activity added to the system.");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Try again. Mass Activity is already exist.");
        }
    }

    public static void addIndividualActivity(CreatedIndividualActivity createdIndividualActivity) {
        try {
            String query=("insert into createdIndiviualActivity"+
                    "(individual_activity_name, internet_link, age_limit)"+
                    "values(?, ?, ?)");
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,createdIndividualActivity.individualActivity);
            preparedStatement.setString(2,createdIndividualActivity.internetLink);
            preparedStatement.setInt(3,createdIndividualActivity.ageLimit);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null,"New individual activity added to the system.");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Try again. Individual Activity is already exist.");
        }
    }
    public static void addAppointment(int customerId, int activityId, int animatorId, String activityDate, String activityHour) {

        try {
            String query=("insert into massactivityappointment"+
                    "(customer_id, activity_id, animator_id,activity_date,activity_hour)"+
                    "values(?, ?, ?, ?, ?)");
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,customerId);
            preparedStatement.setInt(2,activityId);
            preparedStatement.setInt(3,animatorId);
            preparedStatement.setString(4, activityDate);
            preparedStatement.setString(5,activityHour);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null,"New appointment added to the system.");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"The activity has been selected. Please try another option.");
        }
    }
    public static void addAppointmentIndividual(int customerId, int activitId, int animator_id, String activityDate, String activityHour) {
       try{
        String query=("insert into indivudualactivityappointment"+
                "(customer_id, activity_id, animator_id,activity_date,activity_hour)"+
                "values(?, ?, ?, ?, ?)");
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,customerId);
        preparedStatement.setInt(2,activitId);
        preparedStatement.setInt(3,animator_id);
        preparedStatement.setString(4, activityDate);
        preparedStatement.setString(5,activityHour);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        JOptionPane.showMessageDialog(null,"New appointment added to the system.");
    }catch (SQLException e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,"The activity has been selected. Please try another option.");
    }
    }
    public static void addEquipment(String equipName, String purpose, int employeeSSN) {
        try {
            String query = ("insert into addEquipment" +
                    "(equipname, purpose, equip_person_ssn)" +
                    "values(?, ?, ?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, equipName);
            preparedStatement.setString(2, purpose);
            preparedStatement.setInt(3, employeeSSN);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null, "New equipment added to the system.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Try again. Something went wrong.");
        }
    }
    public static void addEmergencyInformation(int activity, String animPhone, String emergencyInfo, String lockerNumber) {
        try {
            String query = ("insert into emergencyinformation" +
                    "(activity_id, phone_number, emergencyInfo, lockerNumber)" +
                    "values(?, ?, ?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, activity);
            preparedStatement.setString(2, animPhone);
            preparedStatement.setString(3, emergencyInfo);
            preparedStatement.setInt(4,Integer.parseInt(lockerNumber));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null, "New emergency info added to the system.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Try again. Something went wrong.");
        }
    }



    public static void deleteIndividualActivity(String activityName) {
        String query =("delete from createdindiviualactivity\n" +
                "where individual_activity_name ='"+activityName+"'");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null, "Activity Deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Try again. Something went wrong.");
        }


    }
    public static void deleteMassActivity(String activityName) {
        String query =("delete from createdMassActivity\n" +
                "where mass_activity_name ='"+activityName+"'");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            JOptionPane.showMessageDialog(null, "Activity Deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Try again. Something went wrong.");
        }
    }


    private static String getPasswordAnim(){
        String query2=("select given_password "+
                "from animatorinformation");
        ResultSet rs=databaseManager.list(query2);
        String password=null;
        try {
            while (rs.next()) {
                password=rs.getString("given_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }
    private static String getPassword(){
        String query2=("select given_password "+
                "from customerinformation");
        ResultSet rs=databaseManager.list(query2);
        String password=null;
        try {
            while (rs.next()) {
                password=rs.getString("given_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }
    public static boolean getAuthentication(String contact_phone, String customer_password){
        String query= ("select * from customerinformation");
        int customerPassword=Integer.parseInt(customer_password);
        try {
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(query);

            while (rs.next()){
                String getPhone= rs.getString("contact_phone");
                if(getPhone.equals(contact_phone)&&customerPassword==rs.getInt("given_password")){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean getAuthenticationAnimator(String phoneNumber, String password) {
        String query= ("select * from animatorinformation");
        int customerPassword=Integer.parseInt(password);
        try {
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(query);

            while (rs.next()){
                String getPhone= rs.getString("phone_number");
                if(getPhone.equals(phoneNumber)&&customerPassword==rs.getInt("given_password")){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
