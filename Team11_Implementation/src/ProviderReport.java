import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//@author Luca Jontz

public class ProviderReport extends Report{
    ArrayList<ServiceRecord> records;
    int weekFee;
    String memberName;
    String report;
    AllMembers members;
    Member currMember;
    Provider currProvider;

    ProviderReport(AllMembers members, Provider provider){
        //ProviderReport initialization
        weekFee = 0;
        memberName = "";
        report = "";
        currMember = null;
        this.members = members;
        this.records = provider.getRecords();
        this.currProvider = provider;
        receiverName = currProvider.getName();
        receiverNum = currProvider.getCreds();
        receiverAdress = currProvider.getAddress();
        receiverCity = currProvider.getCity();
        receiverState = currProvider.getState();
        receiverZip = currProvider.getZip();
    }

    public void writeReport(){
        String currDate;
        LocalDate serviceDate;
        Long memberNum;
        int serviceCode;
        int fee = 0;
        int consultations = 0;
        makeFile(true);

        report+= "Provider name: "; //Add provider's details to the report
        report+= receiverName;
        report+= "\nProvider number: ";
        report+= receiverNum;
        report+= "\nProvider street adress: ";
        report+= receiverAdress;
        report+= "\nProvider city: ";
        report+= receiverCity;
        report+= "\nProvider state: ";
        report+= receiverState;
        report+= "\nProvider zip: ";
        report+= receiverZip;
        report+= "\n\n";

        linesInReport.add("Provider name: "+ receiverName);
        linesInReport.add("Provider number: "+ receiverNum);
        linesInReport.add("Provider street address: "+ receiverAdress);
        linesInReport.add("Provider city: "+ receiverCity);
        linesInReport.add("Provider state: "+ receiverState);
        linesInReport.add("Provider zip: "+ receiverZip);
        linesInReport.add("");
        linesInReport.add("");

        for(ServiceRecord record : records){ //Loop through each of this provider's service records and add them to the report
            currDate = record.getDate();
            serviceDate = record.getDateService();
            memberNum = record.getMemberNum();
            serviceCode = record.getServiceCode();
            fee = record.getServiceFee();
            report += "Date of Service: ";
            report += currDate;
            report += "\nService Recieved on Date: ";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            String formattedString = serviceDate.format(formatter);
            report += formattedString;
            report += "\nMember name and number: ";
            currMember = members.getMember(memberNum);
            memberName = currMember.getName();
            report += memberName;
            report += " ";
            report += memberNum;
            report += "\nService code: ";
            report += serviceCode;
            report += "\nFee: ";
            report += fee;
            report += "\n\n";
            consultations++;
            weekFee += fee;

            linesInReport.add("Date of Service: "+ currDate);
            linesInReport.add("Service Recieved on Date: "+ formattedString);
            linesInReport.add("Member name and number: "+ memberName +" "+memberNum);
            linesInReport.add("Service code: "+ serviceCode);
            linesInReport.add("Fee: "+ fee);
            linesInReport.add("");
            linesInReport.add("");
        }
        report += "Number of consultations: "; //add total consultations to the report
        report += consultations;
        report += "\nTotal fee for the week: "; //add the fee for the week to the report
        report += fee;
        report += "\n";

        linesInReport.add("Number of consultations: "+ consultations);
        linesInReport.add("Total fee for the week: "+ fee);

        writeToFile(report); //write the report to the report file
    }
}
