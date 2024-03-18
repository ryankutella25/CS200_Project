// @author Ali Burkemper
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Class to run testing.
// Testing methods getMemberNumber() and runMainAccountingProcedure() from MY classes.
// Testing getServiceCode() from ServiceRecord.java class.

public class AliJUnitTests {
    
    long memTestNum;
    Member memToTest;
    ProviderTerminal pT;
    ArrayList<ServiceRecord> rT;
    TimerClass tT;
    MemberReport report = new MemberReport();
    AllMembers members = new AllMembers();
    AllProviders providers = new AllProviders();
    ZoneId z = ZoneId.of( "America/Chicago" );
    // Get time
    LocalDate today = LocalDate.now(z); //current date
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    String formatDate = today.format(formatter);

    @Before
    public void setUp(){
        // Setup for getMemberNumber()
        pT = new ProviderTerminal(providers, members);
        members.addMember(2, "Fake Girl", "Unpaid", "1 ABC street", "St. Louis", "Missouri", 1);
        providers.addProvider(1, "Bob", "1", "NYC", "NY", 0);
        memToTest = members.getMember(2);
        pT.addServiceReports(providers.getProvider(1), members.getMember(2), "01-01-2023", 883948, "null", 150, "Swimming");
        rT = memToTest.getService();
        report.generateReport(memToTest, providers);
    }

    // getMemberNumber from member.java
    @Test
    public void testMemberNumber(){
        if(memToTest.getMemberNumber() != 2){
            fail("Not the right member number");
        }
    }

    // generateReport from memberReport.java
    @Test
    public void testGenerateReport(){
        String name = memToTest.getName();
        name = name.replaceAll("\\s", "");

        File f = new File("Team11_Implementation" + File.separator + "data" + File.separator + "MemberReports" +File.separator+name+"_"+formatDate+".txt");
        // Get date for path
        try{
        if(f.createNewFile()) {
            fail("Fail is not created");
        }

        } catch (IOException e) {
            fail("Error in file creation");
        }
    }

    // getServiceCode from serviceRecord.java
    @Test
    public void testGetServiceCode(){
        if(rT.get(0).getServiceCode() != 883948) {
            fail("Not the right service code");
        }
    }

    @After
    public void notNeeded(){}
}
