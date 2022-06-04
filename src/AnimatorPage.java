import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatorPage extends JFrame {


    private JPanel animatorPanel;
    private JButton activityCreatmentButton;
    private JButton emergencyInformationButton;
    private JButton equipmentAddingButton;
    private JButton quitButton;

    public AnimatorPage(){
        add(animatorPanel);
        setSize(320,400);
        setTitle("Animator Page");
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
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnimatorLoginPage alp=new AnimatorLoginPage();
                alp.setVisible(true);
                dispose();
            }
        });
        activityCreatmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animatorCreateActivityPage acap=new animatorCreateActivityPage();
                acap.setVisible(true);
                dispose();
            }
        });
        emergencyInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmergencyInformation ei=new EmergencyInformation();
                ei.setVisible(true);
                dispose();
            }
        });
        equipmentAddingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EquipmentAdding ea=new EquipmentAdding();
                ea.setVisible(true);
                dispose();
            }
        });
    }
}
