import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class animatorCreateActivityPage extends JFrame {
    private JButton massActivityButton;
    private JButton individualActivityButton;
    private JPanel animatorCreateActivityPanel;
    private JButton quitButton;

    public animatorCreateActivityPage() {
        add(animatorCreateActivityPanel);
        setSize(320,400);
        setTitle("Activity Create Page");
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
        massActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateMassActivityPage cmap=new CreateMassActivityPage();
                cmap.setVisible(true);
                dispose();
            }
        });
        individualActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateIndividualActivityPage ciap=new CreateIndividualActivityPage();
                ciap.setVisible(true);
                dispose();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnimatorPage alp=new AnimatorPage();
                alp.setVisible(true);
                dispose();
            }
        });
    }
}
