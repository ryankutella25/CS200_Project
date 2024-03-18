import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// MemberTerminal Class is responsible for the GUI of the member terminal for demo purpose.
// This class, although not part of ChocAn, helps demo the reports being made for each member.

// @author Ryan Kutella

public class MemberTerminal {

    // All... instances (passed from App on creation)
    AllMembers members;

    Member currentMember; // is null until after verify
    boolean memberVerified = false;
    JPanel panel = new JPanel();

    MemberTerminal(AllMembers members) {
        this.members = members;
    }

    public JPanel getPanel() {
        memberVerified = false; // set providerVerified to false so everytime provider terminal is opened
        refreshPanel();
        return panel;
    }

    // Called to update screen to either verified or unverified page
    private void refreshPanel() {
        panel.removeAll();

        if (memberVerified) {
            setVerfiedPanel();
        } else {
            setUnverifiedPanel();
        }

        // need to repaint and revalidate for screen to update
        panel.revalidate();
        panel.repaint();
    }

    // Sets mainPanel to verified page
    private void setVerfiedPanel() {
        JButton showButton = new JButton(new AbstractAction("Show Reports") {
            public void actionPerformed(ActionEvent e) {
                showReports();
            }
        });
        panel.add(showButton);
    }

    // Sets panel to the provider # _____ page
    private void setUnverifiedPanel() {
        JTextField input = new JTextField(10);
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                verify(input.getText());
            }
        });

        JLabel label = new JLabel("Member #:");
        label.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(label);
        panel.add(input);
        panel.add(submitButton);
    }

    // Called when submit is clicked when asking for Provider OR Member #
    public void verify(String input) {
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException rand) {
            JOptionPane.showMessageDialog(null, "Invalid Code, Please Retry");
            return;
        }

        String memberStatus = members.verifyMember(inputInt);
        if (memberStatus.equals("Validated")||memberStatus.equals("Member suspended")) {
            JOptionPane.showMessageDialog(null, "Valid Member Number");
            currentMember = members.getMember(inputInt);
            memberVerified = true;
        } else JOptionPane.showMessageDialog(null, "Code Invalid");

        refreshPanel();
    }

    // initiateReport is going to be called from GUI (getPanel function)
    private void showReports() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        Boolean hasReport = true;
        ArrayList<String> strings;
        if(currentMember.getReport()==null){
            System.out.println("HUH");
            hasReport = false;
            strings = new ArrayList<>();
        }else{
            strings = currentMember.getReport().linesInReport; //fix when member report done
        }

        if(!hasReport || strings.size()==0){
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
}
