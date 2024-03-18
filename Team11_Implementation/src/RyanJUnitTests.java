import static org.junit.Assert.*;

import org.junit.After;
import  org.junit.Before;
import org.junit.Test;

// RyanJUnitTests is just the class for running the testing.
// Methods testManagerTerminalVerify and testProviderTerminalAddService use
// methods made in MY classes. Other method is testing someone elses class.

// @author Ryan Kutella

public class RyanJUnitTests {
    
    MemberTerminal mT;
    ProviderTerminal pT;
    OperatorTerminal oT;

    Member memToTest;
    Provider provToTest;

    AllProviders providers;

    @Before
    public void setUp(){
        //Setup for both
        providers = new AllProviders();
        AllMembers members = new AllMembers();
        members.addMember(2, "Fake Girl", "Paid", "11 Road", "Birmingham", "Alabama", 0);

        //setup for testMemberTerminalVerify
        mT = new MemberTerminal(members);

        //setup for testProviderTerminalAddService
        pT = new ProviderTerminal(providers, members);
        providers.addProvider(2, "Fake Prov", "10 Road", "Tuscaloosa", "Alabama", 0);
        memToTest = members.getMember(2);
        provToTest = providers.getProvider(2);

        //setup for idk
        AllOperators operators = new AllOperators();
        oT = new OperatorTerminal(providers, members, operators);

    }

    //Test the verify function in MemberTerminal class;
    @Test
    public void testMemberTerminalVerify(){
        if(mT.memberVerified!=false){
            fail("Manager terminal verify should be false by default");
        }
        mT.verify("2");
        if(mT.memberVerified!=true){
            fail("Manager terminal verify should have been true after inputting valid manager");
        }
    }
    
    //Test the addService function in ProviderTerminal class;
    @Test
    public void testProviderTerminalAddService(){
        int memberServicesBefore = memToTest.serviceReports.size();
        int providerServicesBefore = provToTest.getRecords().size();

        pT.addServiceReports(provToTest, memToTest, "12-12-2000", 1, "No Comments", 20, "Blank");

        int memberServicesAfter = memToTest.serviceReports.size();
        int providerServicesAfter = provToTest.getRecords().size();
        
        if(memberServicesBefore+1!=memberServicesAfter){
            fail("Did not add one service");
        }   
        if(providerServicesBefore+1!=providerServicesAfter){
            fail("Did not add one service");
        }

        ServiceRecord mRecord = memToTest.serviceReports.get(memberServicesAfter-1);
        if(mRecord.getServiceFee()!=20){
            fail("Service fee wrong");
        }
        if(mRecord.getServiceCode()!=1){
            fail("Service code wrong");
        }
        if(!mRecord.getDate().equals("12-12-2000")){
            fail("Service date wrong");
        }
        if(!mRecord.getComment().equals("No Comments")){
            fail("Service comment wrong");
        }

        ServiceRecord pRecord = provToTest.getRecords().get(memberServicesAfter-1);
        if(pRecord.getServiceFee()!=20){
            fail("Service fee wrong");
        }
        if(pRecord.getServiceCode()!=1){
            fail("Service code wrong");
        }
        if(!pRecord.getDate().equals("12-12-2000")){
            fail("Service date wrong");
        }
        if(!pRecord.getComment().equals("No Comments")){
            fail("Service comment wrong");
        }
    }

    //Test AddProvider function in Jack B's Operator Terminal Class
    @Test
    public void testOperatorTerminalAddProvider(){
        int totalProvidersBefore = providers.providerList.size();

        oT.addProvider("stuff", "stuff", "stuff", "stuff", 123456);
    
        int totalProvidersAfter = providers.providerList.size();

        if(totalProvidersBefore + 1 != totalProvidersAfter){
            fail("Did not add one provider");
        }

    }

    @After
    public void notNeeded(){}
}
