import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPage extends JFrame {

    private JPanel firstPagePanel;
    private JButton managerButton;
    private JButton customerButton;
    private JButton animatorButton;
    private JLabel firstPageLabel;
    private boolean enter=false;

    public FirstPage(){
        add(firstPagePanel);
        setSize(320,400);
        setTitle("Vacation Village");
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
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};



        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerPage cp=new customerPage();
                cp.setVisible(true);
                dispose();
            }
        });
        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showOptionDialog(null, panel, "Password",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
                if(option == 0) // pressing OK button
                {
                    char[] password = pass.getPassword();
                    String pass=String.valueOf(password);
                    if(!pass.equals("123")){
                        JOptionPane.showMessageDialog(null,"Incorrect password. Try again");
                    }
                    else
                        enter=true;
                }
                if(enter==true){
                    enter=false;
                    ManagerPage mp=new ManagerPage();
                    mp.setVisible(true);
                    dispose();
                }
            }
        });
        animatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnimatorLoginPage alp=new AnimatorLoginPage();
                alp.setVisible(true);
                dispose();
            }
        });
    }

}
