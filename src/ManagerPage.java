import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerPage extends  JFrame{
    private JPanel managerPanel;
    private JButton addCustomerButton;
    private JButton addAnimatorButton;
    private JButton addEquipPersonButton;
    private JButton backButton;

    public ManagerPage(){
        add(managerPanel);
        setSize(320,400);
        setTitle("Manager Page");
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
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCustomerPage acp=new AddCustomerPage();
                acp.setVisible(true);
                dispose();
            }
        });
        addAnimatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddAnimatorPage aap=new AddAnimatorPage();
                aap.setVisible(true);
                dispose();
            }
        });
        addEquipPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEquipPersonPage aepp=new AddEquipPersonPage();
                aepp.setVisible(true);
                dispose();
            }
        });
    }
}
