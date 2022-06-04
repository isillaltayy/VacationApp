import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class appointmentPage extends JFrame{
    private JPanel appointmentPanel;
    private JButton massActivityButton;
    private JButton individualActivityButton;
    private JLabel activityTextLine;
    private JLabel activityLabel;
    private JButton backButton;

    public  appointmentPage(){
        add(appointmentPanel);
        setSize(300,400);
        setTitle("Activity Page");
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

        massActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MassActivityPage map=new MassActivityPage();
                map.setVisible(true);
                dispose();
            }
        });
        individualActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndividualActivityPage iap=new IndividualActivityPage();
                iap.setVisible(true);
                dispose();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerPage cp=new customerPage();
                cp.setVisible(true);
                dispose();
            }
        });
    }
}
