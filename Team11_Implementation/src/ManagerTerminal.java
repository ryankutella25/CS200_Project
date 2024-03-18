import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// ManagerTerminal Class is responsible for the GUI of the terminal
// and the verification of managers. It is also the first step of when 
// manager requests reports and calls MainAccountingProcedure.

// @author Ryan Kutella

public class ManagerTerminal {

    //All... instances (passed from App on creation)
    private AllProviders providers;
    private AllMembers members;
    private AllManagers managers;

    private ManagerReport mReport;

    public boolean managerVerified = false;

    private JPanel panel = new JPanel();
   
    ManagerTerminal(AllProviders providers, AllMembers members, AllManagers managers, ManagerReport report){
        this.providers = providers;
        this.members = members;
        this.managers = managers;
        mReport = report;
    }

    //Called from App when Manager Terminal is selcted
    public JPanel getPanel() {
        managerVerified = false; // set managerVerified to false so everytime provider terminal is opened
        refreshPanel();
        return panel;
    }

    // Called to update screen to either verified or unverified page
    private void refreshPanel() {
        panel.removeAll();

        if (managerVerified) {
            setVerfiedPanel();
        } else {
            setUnverifiedPanel();
        }

        // need to repaint and revalidate for screen to update
        panel.revalidate();
        panel.repaint();
    }

    // Sets panel to verified page
    private void setVerfiedPanel() {
        JButton requestReport = new JButton(new AbstractAction("Request Reports") {
            public void actionPerformed(ActionEvent e) {
                requestReport();
            }
        });
        JButton showReport = new JButton(new AbstractAction("Show Latest Report") {
            public void actionPerformed(ActionEvent e) {
                setReportPanel();
            }
        });
        panel.add(requestReport);
        panel.add(showReport);
    }

    // Sets panel to the manager # _____ page
    private void setUnverifiedPanel() {
        JTextField input = new JTextField(10);
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                verify(input.getText());
            }
        });

        JLabel label = new JLabel("Manager #:");
        label.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(label);
        panel.add(input);
        panel.add(submitButton);
    }

    private void setReportPanel(){
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        ArrayList<String> strings = mReport.linesInReport;

        if(strings.size()==0){
            JPanel row = new JPanel();
            JLabel noReport = new JLabel("No reports ever made or requested.");
            row.add(noReport);
            tempPanel.add(row);
        }else {
            for(int i = 0; i < strings.size(); i++){
                JPanel row = new JPanel();
                JLabel temp = new JLabel(strings.get(i));
                row.add(temp);
                tempPanel.add(row);
            }
        }

        JPanel lastRow = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        lastRow.add(goBack);
        tempPanel.add(lastRow);

        panel.removeAll();
        panel.add(tempPanel);
        panel.revalidate();
        panel.repaint();
    }

    //Called when submit is clicked when asking for Provider OR Member #
    public void verify(String input){
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        }
        catch (NumberFormatException rand) {
            inputInt = -1;
        }

        //now we know if code was valid or not
        if(managers.verifyManager(inputInt)){//Manager code was right
            managerVerified = true;
            refreshPanel();
        }
        else{//provider code was wrong
            JOptionPane.showMessageDialog(null, "Invalid Manager Code, Please Retry");
        }
    }

    //initiateReport is going to be called from GUI (getPanel function)
    public void requestReport(){
        MainAccountingProcedure temp = new MainAccountingProcedure(members, providers, mReport);
        temp.runMainAccountingProcedure();
    }
}
