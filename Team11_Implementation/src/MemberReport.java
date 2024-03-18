// Class containing information for member report.

// @author Ali Burkemper

// passes in member to generate member report

import java.util.ArrayList;

public class MemberReport extends Report{
    String member_report = "";
    ArrayList<ServiceRecord> records;
    AllProviders providers;

    // Generate member report
    public void generateReport(Member member, AllProviders providers){
        this.records = member.getService();
        this.providers = providers;
     
        String memberName;
        String memberAddress;
        String memberCity;
        String memberState;
        int memberZip;
        long memberNum;
        String serviceDate = "";

        // Gather member information
        memberName = member.getName();
        memberNum = member.getMemberNumber();
        memberAddress = member.getAddress();
        memberCity = member.getCity();
        memberState = member.getState();
        memberZip = member.getZip();

        // Add member information to report string
        member_report += "Name: " + memberName + "\n" + "Member number: " + memberNum + "\n" + "Member address: " + memberAddress + "\n" + "Member City: " + memberCity + "\n" + "Member state: " + memberState + "\n" + "Member zipcode: " + memberZip + "\n";
        linesInReport.add("Name: " + memberName);
        linesInReport.add("Member number: " + memberNum );
        linesInReport.add("Member address: " + memberAddress);

        linesInReport.add("Member City: " + memberCity);
        linesInReport.add("Member state: " + memberState);
        linesInReport.add("Member zipcode: " + memberZip);



        // Get all services for currMember and add to report string
        for(ServiceRecord record : records) {
            Provider tempProvider;
            tempProvider = providers.getProvider(record.getProviderNum());
            String providerName = tempProvider.getName();
            String serviceName = record.getServiceName();

            serviceDate = record.getDate();
            member_report +=  "Service name: " + serviceName + "\n" + "Date of service: " + serviceDate + "\n" + "Provider name: " + providerName + "\n";
            
            linesInReport.add("Service name: " + serviceName);
        linesInReport.add("Member zipcode: " + memberZip);
        linesInReport.add( "Date of service: " + serviceDate);
        linesInReport.add( "Provider name: " + providerName );

        }

        
        // Make file
        receiverName = memberName;

        makeFile(false);
        writeToFile(member_report);


    }

}
