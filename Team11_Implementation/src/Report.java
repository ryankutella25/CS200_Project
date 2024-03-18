import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// @author Jack Sherry

public class Report {
    String receiverName;
    Long receiverNum;
    String receiverAdress;
    String receiverCity;
    String receiverState;
    int receiverZip;

    public ArrayList<String> linesInReport;

    Report(){
        linesInReport = new ArrayList<String>();
    }
    
    protected File myObj;
    protected void makeFile(boolean providerReport) {
        try {
            ZoneId z = ZoneId.of( "America/Chicago" ); //just sets zone
            LocalDate today = LocalDate.now(z); //current date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            String formatDate = today.format(formatter);

            String name = receiverName;
            name = name.replaceAll("\\s", "");

            String whichFolder = "MemberReports";
            if(providerReport) whichFolder = "ProviderReports";
            myObj = new File("Team11_Implementation" + File.separator + "data" + File.separator + whichFolder+File.separator+name+"_"+formatDate+".txt");
            int count = 0;
            while (!myObj.createNewFile()) {
                //file already exists but want to keep old file
                count++;
                myObj = new File("Team11_Implementation" + File.separator + "data" + File.separator + whichFolder+File.separator+name+"_"+formatDate+"("+count+").txt");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    protected void writeToFile(String report) {
        try {
            FileWriter myWriter = new FileWriter(myObj.getPath());
            myWriter.write(report);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error writing to ProviderReport.txt occurred.");
            e.printStackTrace();
        }
    }
}
