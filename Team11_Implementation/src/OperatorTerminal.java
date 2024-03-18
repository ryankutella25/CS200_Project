
// @author Jack Bentley
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OperatorTerminal {

    AllProviders providers;
    AllMembers members;
    AllOperators operators;

    boolean operatorVerified = false;
    Operator currentOperator;

    // generates the member ID and provider credentials, gives random 9 digit
    // numbers
    private long RandomGeneratedNumber() {
        // Create an instance of the Random class
        Random random = new Random();

        // Generate a random 9-digit number
        long min = 100000000L; // Smallest 9-digit number
        long max = 999999999L; // Largest 9-digit number

        long generatedNumber = min + ((long) (random.nextDouble() * (max - min)));
        return generatedNumber;

    }

    OperatorTerminal(AllProviders providers, AllMembers members, AllOperators operators) {
        this.providers = providers;
        this.members = members;
        this.operators = operators;

    }

    private JPanel panel = new JPanel();

    public JPanel getPanel() {
        operatorVerified = false;
        refreshPanel();
        return panel;
    }

    // Called to update screen
    public void refreshPanel() {
        panel.removeAll();

        if (operatorVerified) {
            setVerfiedPanel();
        } else {
            setUnverifiedPanel();
        }

        // need to repaint and revalidate for screen to update
        panel.revalidate();
        panel.repaint();
    }

    String name; // 25 characters
    String status;
    String address; // 25 characters
    String city; // 14 characters
    String state; // 2 characters
    int zip;
    long memberNumber;
    long credentials;

    // Sets mainPanel to verified page
    public void setVerfiedPanel() {
        // Adds a new member to the member arraylist
        JButton addMember = new JButton(new AbstractAction("Add Member") {
            public void actionPerformed(ActionEvent e) {
                //
                memberAddPanel();
            }
        });
        // deletes a member from the arraylist
        JButton deleteMember = new JButton(new AbstractAction("Delete Member") {
            public void actionPerformed(ActionEvent e) {
                //
                setMemberRequestPanel(false);
            }
        });
        // updates member's information
        JButton updateMember = new JButton(new AbstractAction("Update Member") {
            public void actionPerformed(ActionEvent e) {
                //
                setMemberRequestPanel(true);
            }
        });
        // adds a provider to the provider arraylist
        JButton addProvider = new JButton(new AbstractAction("Add Provider") {
            public void actionPerformed(ActionEvent e) {
                //
                providerAddPanel();
            }
        });
        // deletes a provider from the arraylist
        JButton deleteProvider = new JButton(new AbstractAction("Delete Provider") {
            public void actionPerformed(ActionEvent e) {
                //
                setProviderRequestPanel(false);
            }
        });
        // can update any of the provider's information from the providerupdatepanel
        JButton updateProvider = new JButton(new AbstractAction("Update Provider") {
            public void actionPerformed(ActionEvent e) {
                //
                setProviderRequestPanel(true);
            }
        });
        JPanel rowOne = new JPanel();
        rowOne.add(addMember);
        rowOne.add(addProvider);
        JPanel rowTwo = new JPanel();
        rowTwo.add(deleteMember);
        rowTwo.add(deleteProvider);
        JPanel rowThree = new JPanel();
        rowThree.add(updateMember);
        rowThree.add(updateProvider);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        tempPanel.add(rowOne);
        tempPanel.add(rowTwo);
        tempPanel.add(rowThree);

        panel.add(tempPanel);
    }

    // Sets panel to the provider # _____ page
    public void setUnverifiedPanel() {
        JTextField input = new JTextField(10);
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                // when submitted do more stuff here
                // like setting currentOperator and checking if input is verified
                verify(input.getText());
            }
        });

        JLabel label = new JLabel("Credentials:");
        label.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(label);
        panel.add(input);
        panel.add(submitButton);
        panel.repaint();
        panel.revalidate();
    }

    // Called when submit is clicked when asking for Operator #
    public void verify(String input) {
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException rand) {
            inputInt = -1;
        }

        if (operators.verifyOperator(inputInt)) {
            operatorVerified = true;
            currentOperator = operators.getOperator(inputInt);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Code, Please Retry");
        }

        refreshPanel();
    }

    public void verifyMember(String input, boolean toUpdate) {
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException rand) {
            inputInt = -1;
        }

        String memberStatus = members.verifyMember(inputInt);

        if (memberStatus.equals("Validated") || memberStatus.equals("Member suspended")) {
            Member m = members.getMember(inputInt);
            if (toUpdate) {
                memberUpdatePanel(m);
            } else {
                members.deleteMember(inputInt);
                JOptionPane.showMessageDialog(null, "Member successfully deleted");
                refreshPanel();
            }
        } else
            JOptionPane.showMessageDialog(null, "Invalid Code, Please Retry");
    }

    // verifies that a provider exists before you try to delete it
    public void verifyProvider(String input, boolean toUpdate) {
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException rand) {
            inputInt = -1;
        }

        if (providers.verifyProvider(inputInt)) {
            Provider p = providers.getProvider(inputInt);
            if (toUpdate) {
                providerUpdatePanel(p);
            } else {
                providers.deleteProvider(inputInt);
                JOptionPane.showMessageDialog(null, "Provider successfully deleted");
                refreshPanel();
            }
        } else
            JOptionPane.showMessageDialog(null, "Invalid Code, Please Retry");
    }

    // This is the panel that shows what things you can update for the member
    public void memberAddPanel() {
        panel.removeAll();

        JPanel rowOne = new JPanel();
        JLabel nameLabel = new JLabel("Member Name: ");
        JTextField nameText = new JTextField(10);
        rowOne.add(nameLabel);
        rowOne.add(nameText);

        JPanel rowTwo = new JPanel();
        JLabel addressLabel = new JLabel("Member Address: ");
        JTextField addressText = new JTextField(10);
        rowTwo.add(addressLabel);
        rowTwo.add(addressText);

        JPanel rowThree = new JPanel();
        JLabel cityLabel = new JLabel("Member City: ");
        JTextField cityText = new JTextField(10);
        rowThree.add(cityLabel);
        rowThree.add(cityText);

        JPanel rowFour = new JPanel();
        JLabel stateLabel = new JLabel("Member State: ");
        JTextField stateText = new JTextField(10);
        rowFour.add(stateLabel);
        rowFour.add(stateText);

        JPanel rowFive = new JPanel();
        JLabel zipLabel = new JLabel("Member Zip: ");
        JTextField zipText = new JTextField(10);
        rowFive.add(zipLabel);
        rowFive.add(zipText);

        JPanel rowSix = new JPanel();
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                int zipInt;
                try {
                    zipInt = Integer.parseInt(zipText.getText());
                } catch (NumberFormatException rand) {
                    JOptionPane.showMessageDialog(null, "Invalid Zip, Please Retry");
                    return;
                }
                addMember(nameText.getText(), addressText.getText(), cityText.getText(), stateText.getText(), zipInt);
                refreshPanel();
            }
        });
        rowSix.add(submitButton);

        JPanel rowSeven = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        rowSeven.add(goBack);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        tempPanel.add(rowSeven);
        tempPanel.add(rowOne);
        tempPanel.add(rowTwo);
        tempPanel.add(rowThree);
        tempPanel.add(rowFour);
        tempPanel.add(rowFive);
        tempPanel.add(rowSix);

        panel.add(tempPanel);

        panel.revalidate();
        panel.repaint();
    }

    public void addMember(String name, String addy, String city, String state, int zipInt) {
        Long creds = RandomGeneratedNumber();
        members.addMember(creds, name, "Paid", addy, city, state, zipInt);
        JPanel temp = new JPanel();
        JLabel test = new JLabel("Member #" + creds + " Added!");
        JButton testButton = new JButton(new AbstractAction("Copy Number") {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(creds + "");
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        temp.add(test);
        temp.add(testButton);
        JOptionPane.showMessageDialog(null, temp);
    }

    public void addProvider(String name, String addy, String city, String state, int zipInt) {
        Long creds = RandomGeneratedNumber();
        providers.addProvider(creds, name, addy, city, state, zipInt);
        JPanel temp = new JPanel();
        JLabel test = new JLabel("Provider #" + creds + " Added!");
        JButton testButton = new JButton(new AbstractAction("Copy Number") {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(creds + "");
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        temp.add(test);
        temp.add(testButton);
        JOptionPane.showMessageDialog(null, temp);
    }

    // This is the panel that shows what things you can update for the member
    public void providerAddPanel() {
        panel.removeAll();

        JPanel rowOne = new JPanel();
        JLabel nameLabel = new JLabel("Provider Name: ");
        JTextField nameText = new JTextField(10);
        rowOne.add(nameLabel);
        rowOne.add(nameText);

        JPanel rowTwo = new JPanel();
        JLabel addressLabel = new JLabel("Provider Address: ");
        JTextField addressText = new JTextField(10);
        rowTwo.add(addressLabel);
        rowTwo.add(addressText);

        JPanel rowThree = new JPanel();
        JLabel cityLabel = new JLabel("Provider City: ");
        JTextField cityText = new JTextField(10);
        rowThree.add(cityLabel);
        rowThree.add(cityText);

        JPanel rowFour = new JPanel();
        JLabel stateLabel = new JLabel("Provider State: ");
        JTextField stateText = new JTextField(10);
        rowFour.add(stateLabel);
        rowFour.add(stateText);

        JPanel rowFive = new JPanel();
        JLabel zipLabel = new JLabel("Provider Zip: ");
        JTextField zipText = new JTextField(10);
        rowFive.add(zipLabel);
        rowFive.add(zipText);

        JPanel rowSix = new JPanel();
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                int zipInt;
                try {
                    zipInt = Integer.parseInt(zipText.getText());
                } catch (NumberFormatException rand) {
                    JOptionPane.showMessageDialog(null, "Invalid Zip, Please Retry");
                    return;
                }
                addProvider(nameText.getText(), addressText.getText(), cityText.getText(), stateText.getText(), zipInt);
                refreshPanel();
            }
        });
        rowSix.add(submitButton);

        JPanel rowSeven = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        rowSeven.add(goBack);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        tempPanel.add(rowSeven);
        tempPanel.add(rowOne);
        tempPanel.add(rowTwo);
        tempPanel.add(rowThree);
        tempPanel.add(rowFour);
        tempPanel.add(rowFive);
        tempPanel.add(rowSix);

        panel.add(tempPanel);

        panel.revalidate();
        panel.repaint();
    }

    // This is the panel that shows what things you can update for the member
    public void memberUpdatePanel(Member cMember) {
        panel.removeAll();

        JPanel rowOne = new JPanel();
        JLabel nameLabel = new JLabel("Member Name: ");
        JTextField nameText = new JTextField(cMember.getName(), 10);
        rowOne.add(nameLabel);
        rowOne.add(nameText);

        JPanel rowTwo = new JPanel();
        JLabel addressLabel = new JLabel("Member Address: ");
        JTextField addressText = new JTextField(cMember.getAddress(), 10);
        rowTwo.add(addressLabel);
        rowTwo.add(addressText);

        JPanel rowThree = new JPanel();
        JLabel cityLabel = new JLabel("Member City: ");
        JTextField cityText = new JTextField(cMember.getCity(), 10);
        rowThree.add(cityLabel);
        rowThree.add(cityText);

        JPanel rowFour = new JPanel();
        JLabel stateLabel = new JLabel("Member State: ");
        JTextField stateText = new JTextField(cMember.getState(), 10);
        rowFour.add(stateLabel);
        rowFour.add(stateText);

        JPanel rowFive = new JPanel();
        JLabel zipLabel = new JLabel("Member Zip: ");
        JTextField zipText = new JTextField(cMember.getZip() + "", 10);
        rowFive.add(zipLabel);
        rowFive.add(zipText);

        JPanel rowSix = new JPanel();
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                int zipInt;
                try {
                    zipInt = Integer.parseInt(zipText.getText());
                } catch (NumberFormatException rand) {
                    JOptionPane.showMessageDialog(null, "Invalid Zip, Please Retry");
                    return;
                }
                cMember.setName(nameText.getText());
                cMember.setAddress(addressText.getText());
                cMember.setCity(cityText.getText());
                cMember.setState(stateText.getText());
                cMember.setZip(zipInt);
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                refreshPanel();
            }
        });
        rowSix.add(submitButton);

        JPanel rowSeven = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        rowSeven.add(goBack);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        tempPanel.add(rowSeven);
        tempPanel.add(rowOne);
        tempPanel.add(rowTwo);
        tempPanel.add(rowThree);
        tempPanel.add(rowFour);
        tempPanel.add(rowFive);
        tempPanel.add(rowSix);

        panel.add(tempPanel);

        panel.revalidate();
        panel.repaint();
    }

    // panel that shows what provider information you can update
    public void providerUpdatePanel(Provider cProvider) {
        panel.removeAll();
        JPanel rowOne = new JPanel();
        JLabel nameLabel = new JLabel("Provider Name: ");
        JTextField nameText = new JTextField(cProvider.getName(), 10);
        rowOne.add(nameLabel);
        rowOne.add(nameText);

        JPanel rowTwo = new JPanel();
        JLabel addressLabel = new JLabel("Provider Address: ");
        JTextField addressText = new JTextField(cProvider.getAddress(), 10);
        rowTwo.add(addressLabel);
        rowTwo.add(addressText);

        JPanel rowThree = new JPanel();
        JLabel cityLabel = new JLabel("Provider City: ");
        JTextField cityText = new JTextField(cProvider.getCity(), 10);
        rowThree.add(cityLabel);
        rowThree.add(cityText);

        JPanel rowFour = new JPanel();
        JLabel stateLabel = new JLabel("Provider State: ");
        JTextField stateText = new JTextField(cProvider.getState(), 10);
        rowFour.add(stateLabel);
        rowFour.add(stateText);

        JPanel rowFive = new JPanel();
        JLabel zipLabel = new JLabel("Provider Zip: ");
        JTextField zipText = new JTextField(cProvider.getZip() + "", 10);
        rowFive.add(zipLabel);
        rowFive.add(zipText);

        JPanel rowSix = new JPanel();
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                int zipInt;
                try {
                    zipInt = Integer.parseInt(zipText.getText());
                } catch (NumberFormatException rand) {
                    JOptionPane.showMessageDialog(null, "Invalid Zip, Please Retry");
                    return;
                }
                cProvider.setName(nameText.getText());
                cProvider.setAddress(addressText.getText());
                cProvider.setCity(cityText.getText());
                cProvider.setState(stateText.getText());
                cProvider.setZip(zipInt);
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                refreshPanel();
            }
        });
        rowSix.add(submitButton);

        panel.add(rowSix);
        panel.add(rowOne);
        panel.add(rowTwo);
        panel.add(rowThree);
        panel.add(rowFour);
        panel.add(rowFive);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        JPanel rowSeven = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        rowSeven.add(goBack);

        tempPanel.add(rowOne);
        tempPanel.add(rowTwo);
        tempPanel.add(rowThree);
        tempPanel.add(rowFour);
        tempPanel.add(rowFive);
        tempPanel.add(rowSix);
        tempPanel.add(rowSeven);

        panel.add(tempPanel);

        panel.revalidate();
        panel.repaint();
    }

    // panel for member update
    public void setMemberRequestPanel(boolean toUpdate) {
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
                verifyMember(input.getText(), toUpdate);
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

    // This is the panel for provider update
    public void setProviderRequestPanel(boolean toUpdate) {
        panel.removeAll();

        JPanel rowZero = new JPanel();
        JButton goBack = new JButton(new AbstractAction("Go Back") {
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
        });
        rowZero.add(goBack);

        JPanel rowOne = new JPanel();
        JLabel label = new JLabel("Provider #:");
        label.setHorizontalAlignment(JLabel.RIGHT);
        JTextField input = new JTextField(10);
        JButton submitButton = new JButton(new AbstractAction("Submit") {
            public void actionPerformed(ActionEvent e) {
                verifyProvider(input.getText(), toUpdate);
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
}
