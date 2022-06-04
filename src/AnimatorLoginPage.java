import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatorLoginPage extends JFrame{
    private JTextField phoneNumberTextField;
    private JButton signInButton;
    private JPanel animatorLoginPanel;
    private JButton backButton;
    private JPasswordField passwordField;
    private boolean verify;
    private String phoneNumber;
    private static String sendPhoneNumber;

    public static String getAnimPhoneNumber() {
        return sendPhoneNumber;
    }
    public void setAnimPhoneNumber(String phoneNumber) {
        this.sendPhoneNumber = phoneNumber;
    }


    public AnimatorLoginPage(){
        add(animatorLoginPanel);
        setSize(320,400);
        setTitle("Animator Login Page");
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
                FirstPage fp=new FirstPage();
                fp.setVisible(true);
                dispose();
            }
        });
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password;
                phoneNumber= phoneNumberTextField.getText();
                password=passwordField.getText();
                if (phoneNumber.isEmpty()||password.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Fill in the blanks.");
                    return;
                }
                verify=databaseManager.getAuthenticationAnimator(phoneNumber,password);
                if(verify==true){
                    AnimatorPage ap=new AnimatorPage();
                    ap.setVisible(true);
                    dispose();
                }
                else if(verify==false){
                    JOptionPane.showMessageDialog(null,"Wrong. Try Again");
                }
                setAnimPhoneNumber(phoneNumber);
                verify=false;
            }
        });
    }
}


