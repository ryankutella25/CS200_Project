import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

// ManagerReport Class is responsible for holding the manager reports in linesInReport
// as well making and writing the file for the report.

// @author Ryan Kutella

public class ManagerReport {

    private ZoneId z = ZoneId.of("America/Chicago"); // just sets zone
    private LocalDate today = LocalDate.now(z); // current date (needs time zone above)

    private File myObj;
    private File myEFT;

    public ArrayList<String> linesInReport = new ArrayList<String>();
    public ArrayList<String> EFT_Lines = new ArrayList<String>();
    
    private AllProviders providers;

    public ManagerReport(AllProviders providers) {
        this.providers = providers;
    }

    // Called in managerReport
    public void createReport() {
        linesInReport.clear();
        makeFile();
        ArrayList<Provider> pList = providers.providerList;
        for (int i = 0; i < pList.size(); i++) {
            Provider temp = pList.get(i);
            ArrayList<ServiceRecord> sRecords = temp.getRecords();
            String name = temp.getName();
            int totalFee = 0;
            int totalConsultations = 0;
            for (int j = 0; j < sRecords.size(); j++) {
                ServiceRecord s = sRecords.get(j);
                LocalDate ld = s.getDateService();
                if (checkIfSameWeek(ld)) {
                    totalFee += s.getServiceFee(); //need service fee in ServiceReport
                    totalConsultations++;
                }
                // if not in week just ignore
            }
            linesInReport.add("Provider Name: "+name + ",  Total Consultations: " + totalConsultations + ",  Total Fee: " + totalFee);
            EFT_Lines.add("Provider Name: "+ name + " Num: "+temp.getCreds()+"     Amount To Transfer: "+ totalFee);
        }
        writeToFile();
    }

    // Returns true if check date is in the week of gloabl variable today
    private boolean checkIfSameWeek(LocalDate check) {
        DayOfWeek sun = DayOfWeek.of(7); // Sunday = 7.
        DayOfWeek sat = DayOfWeek.of(6); // Saturday = 6.

        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(sat)); // Gets next friday
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(sun));

        return !(check.isBefore(startOfWeek) || check.isAfter(endOfWeek));
    }

    // Makes file in data folder called "ManagerReport.txt"
    private void makeFile() {
        ZoneId z = ZoneId.of( "America/Chicago" ); //just sets zone
        LocalDate today = LocalDate.now(z); //current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formatDate = today.format(formatter);

        try {
                myObj = new File("Team11_Implementation" + File.separator + "data" + File.separator + "ManagerReports" + File.separator + "ManagerReport_"+formatDate+".txt");
            int count = 0;
            while (!myObj.createNewFile()) {
                //file already exists but want to keep old file
                count++;
                myObj = new File("Team11_Implementation" + File.separator + "data" + File.separator + "ManagerReports" + File.separator + "ManagerReport_"+formatDate+"("+count+").txt");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            myEFT = new File("Team11_Implementation" + File.separator + "data" + File.separator + "EFTData" + File.separator +"EFTData_"+formatDate+".txt");
            int count = 0;
            while (!myEFT.createNewFile()) {
                //file already exists but want to keep old file
                count++;
                myEFT = new File("Team11_Implementation" + File.separator + "data" + File.separator + "EFTData" + File.separator + "EFTData_"+formatDate+"("+count+").txt");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // After linesInReport is complete this just takes each string in array and writes to new line
    private void writeToFile() {
        try {
            FileWriter myWriter = new FileWriter(myObj.getPath());
            for(int i = 0; i < linesInReport.size(); i++){
                myWriter.write(linesInReport.get(i));
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error writing to ManagerReport.txt occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(myEFT.getPath());
            for(int i = 0; i < EFT_Lines.size(); i++){
                myWriter.write(EFT_Lines.get(i));
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error writing to ManagerReport.txt occurred.");
            e.printStackTrace();
        }
    }
}
