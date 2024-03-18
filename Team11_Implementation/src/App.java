import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

// App Class contains the main to be run and is responsible for holding the frame for the GUI
// App Class also initializes the Record Class (AllMembers, AllManagers, etc) and is responsible for
// passing them to other classes as needed.

// @author Ryan Kutella

class App{

     static AllProviders providers;
     static AllMembers members;
     static AllManagers managers;
     static AllOperators operators;

     //Main functions is just holding instances of terminal and running gui
      public static void main(String args[]){
          //Initialize All... Instances
          providers = new AllProviders();
          members = new AllMembers();
          managers = new AllManagers();
          operators = new AllOperators();

          //Making fake info
          members.addMember(1, "Fake Guy", "Paid", "10 Road", "Tuscaloosa", "Alabama", 0);
          members.addMember(2, "Fake Girl", "Paid", "11 Road", "Birmingham", "Alabama", 0);
          members.addMember(3, "Fake Dude", "Unpaid", "12 Road", "Montana", "Wherever", 8);
          providers.addProvider(1, "Fake Prov", "10 Road", "Tuscaloosa", "Alabama", 0);
          providers.addProvider(2, "Real Prov", "10 Road", "Birm", "Alabama", 0);
          operators.addOperator(1, "Fake Op");
          managers.addManager(1, "Fake Man");

          //Below is just for testing
          ManagerReport m = new ManagerReport(providers);

          //Terminal instances below
          ManagerTerminal mngTerm = new ManagerTerminal(providers, members, managers, m);
          ProviderTerminal provTerm = new ProviderTerminal(providers, members);
          OperatorTerminal opTerm = new OperatorTerminal(providers, members, operators);
          MemberTerminal memTerm = new MemberTerminal(members); //Just for Demo Purpose

          //More testing   
          provTerm.addServiceReports(providers.getProvider(1), members.getMember(1), "12-12-2000", 598470, "null", 50, "Session with a Dietitian");
          provTerm.addServiceReports(providers.getProvider(1), members.getMember(2), "12-12-2000", 883948, "null", 150,"Aerobics Exercise Session");
          provTerm.addServiceReports(providers.getProvider(2), members.getMember(1), "12-12-2000", 598470, "null", 50, "Session with a Dietitian");
          provTerm.addServiceReports(providers.getProvider(2), members.getMember(2), "12-12-2000", 883948, "null", 150,"Aerobics Exercise Session");
          // mngTerm.requestReport();   

          //Timer instance and start timer
          TimerClass timer = new TimerClass(providers, members, m);
          timer.runTask(5,23,59,59);     //5,23,59,59 is correct parameters for friday at 11:59:59 PM

          //Creating the Frame
          JFrame frame = new JFrame("ChocAn System");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setSize(1400, 700);
               
          //Creating the MenuBar and adding components
          JMenuBar mb = new JMenuBar();
          JMenu m1 = new JMenu("Select Terminal");
          mb.add(m1);

          //South Panel
          JPanel terminalPanel = new JPanel(new GridLayout(0, 1));

          //Demo menu below just shows demo options
          JMenu m2 = new JMenu("Demo Options");
          JMenuItem m2_1 = new JMenuItem(new AbstractAction("Timer go off now") {
               public void actionPerformed(ActionEvent e) {
                    timer.runNow(); //will run now and next run will still be friday at midnight
               }
          });
          JMenuItem m2_2 = new JMenuItem(new AbstractAction("Member Terminal") {
               public void actionPerformed(ActionEvent e) {
                    m1.setEnabled(false);    //make it so you can't select other terminals
                    m2.setVisible(false);    //remove demo options
                    terminalPanel.add(memTerm.getPanel());
               }
          });
          JMenuItem m2_3 = new JMenuItem(new AbstractAction("Bill Services") {
               public void actionPerformed(ActionEvent e) {
                    provTerm.addServiceReports(providers.getProvider(1), members.getMember(1), "12-12-2000", 598470, "null", 50, "Session with a Dietitian");
                    provTerm.addServiceReports(providers.getProvider(1), members.getMember(2), "12-12-2000", 883948, "null", 150,"Aerobics Exercise Session");
                    provTerm.addServiceReports(providers.getProvider(2), members.getMember(1), "12-12-2000", 598470, "null", 50, "Session with a Dietitian");
                    provTerm.addServiceReports(providers.getProvider(2), members.getMember(2), "12-12-2000", 883948, "null", 150,"Aerobics Exercise Session");
               }
          });
          JMenuItem m2_4 = new JMenuItem(new AbstractAction("Show EFT") {
               public void actionPerformed(ActionEvent e) {
                    m1.setEnabled(false);
                    m2.setVisible(false);

                    JPanel tempPanel = new JPanel();
                    tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

                    ArrayList<String> strings = m.linesInReport;

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

                    terminalPanel.add(tempPanel);
               }
          });
          m2.add(m2_1);
          m2.add(m2_2);
          m2.add(m2_3);
          m2.add(m2_4);
     
          //Below is menu item 'Operator' and it's onClick function
          JMenuItem m1_1 = new JMenuItem(new AbstractAction("Operator") {
               public void actionPerformed(ActionEvent e) {
                    m1.setText("Operator Terminal");
                    m1.removeAll();
                    m2.setVisible(false);    //remove demo options
                    terminalPanel.add(opTerm.getPanel());
                    frame.repaint();
                    frame.revalidate();
               }
          });

          //Below is menu item 'Provider' and it's onClick function
          JMenuItem m1_2 = new JMenuItem(new AbstractAction("Provider") {
               public void actionPerformed(ActionEvent e) {
                    m1.setText("Provider Terminal");
                    m1.removeAll();
                    m2.setVisible(false);    //remove demo options
                    terminalPanel.add(provTerm.getPanel(m1));
                    frame.repaint();
                    frame.revalidate();
               }
          });
          //Below is menu item 'Manager' and it's onClick function
          JMenuItem m1_3 = new JMenuItem(new AbstractAction("Manager") {
               public void actionPerformed(ActionEvent e) {
                    m1.setText("Manager Terminal");
                    m1.removeAll();
                    m2.setVisible(false);    //remove demo options
                    terminalPanel.add(mngTerm.getPanel());
                    frame.repaint();
                    frame.revalidate();
               }
          });
          
          m1.add(m1_1);
          m1.add(m1_2);
          m1.add(m1_3);

          JMenu m3 = new JMenu("Restart/End");
          //Restart below just resets the menu, puts in a blank panel, and clears sPanel
          JMenuItem m3_1 = new JMenuItem(new AbstractAction("Restart") {
               public void actionPerformed(ActionEvent e) {
                    m1.setText("Select Terminal");
                    m1.setEnabled(true);  
                    m1.removeAll();
                    m1.add(m1_1);
                    m1.add(m1_2);
                    m1.add(m1_3);   //enable selecting terminal
                    m2.setVisible(true);     //enable demo options
                    terminalPanel.removeAll();    //clear anything inside terminalPanels
                    frame.repaint();    
                    frame.revalidate();
               }
          });
          //End Program below just disposes the frame (the view) and terminates the program
          JMenuItem m3_2 = new JMenuItem(new AbstractAction("End Program") {
               public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    System.exit(0);
               }
          });
          mb.add(m2); //adding restart to menubar

          //Adding operator, manager, and provider to menu item 'Select Terminal'
         
          m3.add(m3_1);
          m3.add(m3_2);

          mb.add(Box.createHorizontalGlue());
          mb.add(m3); //adding restart to menubar
          m3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

          //Adding menuBar to the frame. Selected terminal will be added in the actual menu item actionPerformed function
          frame.getContentPane().add(BorderLayout.NORTH, mb);
          frame.getContentPane().add(BorderLayout.SOUTH, terminalPanel);     //This is the terminal panel
          frame.setVisible(true);
     }
}
