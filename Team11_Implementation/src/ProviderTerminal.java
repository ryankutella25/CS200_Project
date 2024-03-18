import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

// ProviderTerminal class is responsible for GUI for the provider.
// It also verifies provider by calling method from AllProvider
// and is the start for all methods providers can do. 

// @author Ryan Kutella

public class ProviderTerminal {

    // All... instances (passed from App on creation)
    private AllProviders providers;
    private AllMembers members;

    // Creates the class, called from App
    ProviderTerminal(AllProviders providers, AllMembers members) {
        this.providers = providers;
        this.members = members;
    }

    private ProviderDirectory providerDirectory;
    private JPanel provDirectoryPanel;
    private boolean providerVerified = false;
    private Provider currentProvider;
    ArrayList<Service> services;

    // The actual screen and menu
    private JPanel panel = new JPanel();
    private JMenu menu;

    // Called from App to get this panel
    public JPanel getPanel(JMenu mb) {
        providerVerified = false; // set providerVerified to false so everytime provider terminal is opened
        refreshPanel();
        menu = mb;
        return panel;
    }

    // Called to update screen to either verified or unverified page
    private void refreshPanel() {
        panel.removeAll();

        if (providerVerified) {
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
        JButton verifyMember = new JButton(new AbstractAction("Provide Services") {
            public void actionPerformed(ActionEvent e) {
                setMemberRequestPanel(false);
            }
        });
        JButton addBill = new JButton(new AbstractAction("Bill Service") {
            public void actionPerformed(ActionEvent e) {
                setMemberRequestPanel(true);
            }
        });
        JButton showReport = new JButton(new AbstractAction("Show Report") {
            public void actionPerformed(ActionEvent e) {
                setReportPanel();
            }
        });

        panel.add(verifyMember);
        panel.add(addBill);
        panel.add(showReport);
        panel.revalidate();
        panel.repaint();
    }

    // Sets panel to the provider # _____ page
    private void setUnverifiedPanel() {
        JTextField input = new JTextField(10);
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                verify(false, input.getText(), false);
            }
        });

        JLabel label = new JLabel("Provider #:");
        label.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(label);
        panel.add(input);
        panel.add(submitButton);
    }

    // Sets panel to the member # ___ page
    // fromBill is true when it's called from Bill Service btn.
    private void setMemberRequestPanel(boolean fromBill) {
        panel.removeAll();

        JPanel rowZero = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        rowZero.add(goBack);

        JPanel rowOne = new JPanel();
        JLabel label = new JLabel("Member #:");
        label.setHorizontalAlignment(JLabel.RIGHT);
        JTextField input = new JTextField(10);
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                verify(true, input.getText(), fromBill);
            }
        });

        rowOne.add(label);
        rowOne.add(input);
        rowOne.add(submitButton);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        tempPanel.add(rowZero);
        tempPanel.add(rowOne);

        panel.add(tempPanel);

        panel.revalidate();
        panel.repaint();
    }

    // Called when submit is clicked when asking for Provider OR Member #
    // memberCode is true when it was prompting for member num,
    // fromBill is true when it's called from Bill Service btn.
    private void verify(boolean memberCode, String input, boolean fromBill) {
        long inputInt;
        try {
            inputInt = Long.parseLong(input);
        } catch (NumberFormatException rand) {
            JOptionPane.showMessageDialog(null, "Invalid Code, Please Retry");
            return;
        }

        if (memberCode) {
            String memberStatus = members.verifyMember(inputInt);
            if (memberStatus.equals("Validated")) {
                JOptionPane.showMessageDialog(null, "Member Valid");
                if (fromBill) {
                    Member cMember = members.getMember(inputInt);
                    setBillPanel(cMember, false);
                    return;
                } else
                    refreshPanel();
            } else if (memberStatus.equals("Member suspended"))
                JOptionPane.showMessageDialog(null, "Member Suspended");
            else
                JOptionPane.showMessageDialog(null, "Invalid Code, Please Retry");
        } else {
            if (providers.verifyProvider(inputInt)) {
                providerVerified = true;
                JMenuItem m1 = new JMenuItem(new AbstractAction("Show Directory") {
                    public void actionPerformed(ActionEvent e) {
                        showProviderDirectory();
                    }
                });
                menu.setText("Show Directory");
                menu.add(m1);
                refreshPanel();
                currentProvider = providers.getProvider(inputInt);
                providerDirectory = new ProviderDirectory(currentProvider);
                makeProviderDirectoryPanel();
            } else
                JOptionPane.showMessageDialog(null, "Invalid Code, Please Retry");
        }
    }

    // variables used for setBillPanel and setConfirmationPanel
    private String codeText;
    private String monthText;
    private String dayText;
    private String yearText;
    private String comments;

    // Panel for filling out bill after member was verified
    // Called when "Bill Service" clicked
    private void setBillPanel(Member cMember, boolean useTextValues) {
        Member currentMember = cMember;
        panel.removeAll();

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        JPanel rowZero = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                setMemberRequestPanel(true);
            }
        });
        rowZero.add(goBack);

        // panel.add(datePicker);
        JPanel rowOne = new JPanel();
        JLabel label = new JLabel("MM-DD-YYYY:");
        JLabel dash1 = new JLabel("-");
        JLabel dash2 = new JLabel("-");
        JTextField monthInput = new JTextField(2);
        JTextField dayInput = new JTextField(2);
        JTextField yearInput = new JTextField(4);

        rowOne.add(label);
        rowOne.add(monthInput);
        rowOne.add(dash1);
        rowOne.add(dayInput);
        rowOne.add(dash2);
        rowOne.add(yearInput);

        JPanel rowTwo = new JPanel();
        JLabel serviceLabel = new JLabel("Service Code:");
        JTextField codeInput = new JTextField(10);
        rowTwo.add(serviceLabel);
        rowTwo.add(codeInput);

        JPanel rowThree = new JPanel();
        JLabel commentLabel = new JLabel("Comments:");
        rowThree.add(commentLabel);

        JPanel rowFour = new JPanel();
        JTextArea textArea = new JTextArea(4, 30);
        rowFour.add(textArea);

        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                codeText = codeInput.getText();
                monthText = monthInput.getText();
                dayText = dayInput.getText();
                yearText = yearInput.getText();
                comments = textArea.getText();
                setConfirmationPanel(currentMember);
            }
        });

        JPanel rowFive = new JPanel();
        rowFive.add(submitButton);

        tempPanel.add(rowZero);
        tempPanel.add(rowOne);
        tempPanel.add(rowTwo);
        tempPanel.add(rowThree);
        tempPanel.add(rowFour);
        tempPanel.add(rowFive);

        panel.add(tempPanel);

        if (useTextValues) {
            monthInput.setText(monthText);
            dayInput.setText(dayText);
            yearInput.setText(yearText);
            codeInput.setText(codeText);
            textArea.setText(comments);
        }

        panel.revalidate();
        panel.repaint();
    }

    // The screen after submit is clicked when billing
    // Show the service code and name and asks if it's correct
    // If "No" pressed then goes back to billPanel
    // If "Yes" pressed then calls addServiceReports and alerts it worked and calls
    // showServiceFeePanel()
    private void setConfirmationPanel(Member cMember) {
        int monthInt;
        int dayInt;
        int yearInt;
        int codeInt;
        try {
            codeInt = Integer.parseInt(codeText);
            // check if codeInt is valid service, if not then alert and return
        } catch (NumberFormatException rand) {
            JOptionPane.showMessageDialog(null, "Code needs to be all numbers");
            return;
        }
        // get month input
        try {
            monthInt = Integer.parseInt(monthText);
            if (monthInt < 1 || monthInt > 12) {
                JOptionPane.showMessageDialog(null, "Month isn't valid (Needs to be number 1-12)");
                return;
            }
        } catch (NumberFormatException rand) {
            JOptionPane.showMessageDialog(null, "Month isn't valid (Needs to be number 1-12)");
            return;
        }
        // get day input
        try {
            dayInt = Integer.parseInt(dayText);
            if (dayInt < 1 || dayInt > 31) {
                JOptionPane.showMessageDialog(null, "Day isn't valid (Needs to be number 1-31)");
                return;
            }
        } catch (NumberFormatException rand) {
            JOptionPane.showMessageDialog(null, "Day isn't valid (Needs to be number 1-31)");
            return;
        }
        // get year input
        try {
            yearInt = Integer.parseInt(yearText);
            if (yearInt < 1990) {
                JOptionPane.showMessageDialog(null, "Year isn't valid (Needs to be number greater than 1990)");
                return;
            }
        } catch (NumberFormatException rand) {
            JOptionPane.showMessageDialog(null, "Year isn't valid (Needs to be number greater than 1990)");
            return;
        }

        Service cService = new Service();
        boolean serviceFound = false;
        for(int i = 0; i < services.size(); i++){
            cService = services.get(i);
            if(cService.getCode()==codeInt){
                serviceFound = true;
                break;
            }
        }
        if(!serviceFound){
            JOptionPane.showMessageDialog(null, "Service code isn't valid");
            return;
        }

        // at this point: code is valid service code and month, day, and year are valid;

        JPanel rowOne = new JPanel();
        JLabel isCorrectLabel = new JLabel("Is this service correct?");
        rowOne.add(isCorrectLabel);

        JPanel rowTwo = new JPanel();
        JLabel serviceCodeLabel = new JLabel("Service Code: " + codeInt);
        rowTwo.add(serviceCodeLabel);

        JPanel rowThree = new JPanel();
        String serviceName = cService.getName();
        JLabel serviceCodeName = new JLabel("Service Name: " + serviceName);
        rowThree.add(serviceCodeName);

        JPanel rowFour = new JPanel();
        int serviceFee = cService.getFee();
        JLabel serviceFeeLabel = new JLabel("Service Fee: " + serviceFee);
        rowFour.add(serviceFeeLabel);

        JPanel rowFive = new JPanel();
        JButton yesButton = new JButton(new AbstractAction("YES") {
            public void actionPerformed(ActionEvent e) {
                String date = monthInt + "-" + dayInt + "-" + yearInt;
                addServiceReports(currentProvider, cMember, date, codeInt, comments, serviceFee, serviceName);
                showServiceFeePanel(codeInt, serviceName, serviceFee);
            }
        });
        JButton noButton = new JButton(new AbstractAction("NO") {
            public void actionPerformed(ActionEvent e) {
                setBillPanel(cMember, true);
            }
        });
        rowFive.add(noButton);
        rowFive.add(yesButton);

        JPanel tempPanel1 = new JPanel();
        tempPanel1.setLayout(new BoxLayout(tempPanel1, BoxLayout.Y_AXIS));

        tempPanel1.add(rowOne);
        tempPanel1.add(rowTwo);
        tempPanel1.add(rowThree);
        tempPanel1.add(rowFour);
        tempPanel1.add(rowFive);
        panel.removeAll();
        panel.add(tempPanel1);

        panel.revalidate();
        panel.repaint();
    }

    private void setReportPanel(){
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        Boolean hasReport = true;
        ArrayList<String> strings;
        if(currentProvider.getReport()==null){
            hasReport = false;
            strings = new ArrayList<>();
        }else{
            strings = currentProvider.getReport().linesInReport;
        }

        if(!hasReport || strings.size()==0){
            JPanel row = new JPanel();
            JLabel noReport = new JLabel("No reports ever made for this provider.");
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

        JScrollPane pane = new JScrollPane(tempPanel, 
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,  
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pane.setPreferredSize(new Dimension(1200,650));

        panel.removeAll();
        panel.add(pane);
        panel.revalidate();
        panel.repaint();
    }

    // Makes the Show Directory panel after provider is verified
    private void makeProviderDirectoryPanel(){
        provDirectoryPanel = new JPanel();

        services = providerDirectory.requestServices();

        provDirectoryPanel.setLayout(new BoxLayout(provDirectoryPanel, BoxLayout.Y_AXIS));

        for(int i = 0; i < services.size(); i++){
            JPanel row = new JPanel();
            Service s = services.get(i);
            JLabel temp = new JLabel("Code: "+ s.getCode()+", Name: "+s.getName()+", Fee: "+s.getFee());
            row.add(temp);
            provDirectoryPanel.add(row);
        }
    }

    boolean frameOpen = false;
    JFrame frame;
    // Opens new frame which should show all services available
    private void showProviderDirectory() {
        if(frameOpen) frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Provider Directory");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.getContentPane().add(BorderLayout.SOUTH, provDirectoryPanel);  
        frame.setVisible(true);
        frameOpen = true;
    }

    // Called after bill is submitted to show fee for provider's files
    private void showServiceFeePanel(int serviceCode, String serviceName, int serviceFee) {
        panel.removeAll();

        JPanel tempPanel1 = new JPanel();
        tempPanel1.setLayout(new BoxLayout(tempPanel1, BoxLayout.Y_AXIS));

        JPanel rowOne = new JPanel();
        JLabel message = new JLabel("Service Successfully Billed!");
        rowOne.add(message);

        JPanel rowTwo = new JPanel();
        JLabel serviceCodeLabel = new JLabel("Service Code: " + serviceCode);
        rowTwo.add(serviceCodeLabel);

        JPanel rowThree = new JPanel();
        JLabel serviceNameLabel = new JLabel("Service Name: " + serviceName);
        rowThree.add(serviceNameLabel);

        JPanel rowFour = new JPanel();
        JLabel serviceFeeLabel = new JLabel("Service Fee: $" + serviceFee);
        rowFour.add(serviceFeeLabel);

        JPanel rowFive = new JPanel();
        JButton doneButton = new JButton(new AbstractAction("Done") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        rowFive.add(doneButton);

        tempPanel1.add(rowOne);
        tempPanel1.add(rowTwo);
        tempPanel1.add(rowThree);
        tempPanel1.add(rowFour);
        tempPanel1.add(rowFive);

        panel.add(tempPanel1);
        panel.revalidate();
        panel.repaint();
    }

    // Uses information passed in to make a new ServiceRecord() and add it to the
    // member and provider involved
    // Called when YES is clicked to confirm bill
    public void addServiceReports(Provider cProvider, Member cMember, String date, int serviceCode, String comments, int serviceFee, String serviceName) {
        ServiceRecord temp = new ServiceRecord(date, cProvider.getCreds(), cMember.getMemberNumber(), serviceCode, comments, serviceFee, serviceName);
        cProvider.addRecord(temp);
        cMember.addService(temp);
    }

    // private void billService(Member currMember, String date, String serviceDate, int serviceCode, String comment){
    //     members.verifyMember(currMember.getMemberNumber());
    //     ServiceRecord service =  new ServiceRecord(date, serviceDate, currentProvider.getProviderNum(), currMember.getMemberNumber(), serviceCode, comment);
    //     currentProvider.addRecord(service);
    // }

    // private void getReport(){
    //     ArrayList<ServiceRecord> records = currentProvider.getRecords();
    //     ProviderReport report = new ProviderReport(members, records);
    //     report.writeReport();
    // }
}
