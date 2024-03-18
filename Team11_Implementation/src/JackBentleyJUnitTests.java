import static org.junit.Assert.*;

import org.junit.After;
import  org.junit.Before;
import org.junit.Test;

//@author Jack Bentley

public class JackBentleyJUnitTests {
    OperatorTerminal oT;
    long creds = 123456789;

    AllMembers testMember;
    AllOperators testOperators;
    AllMembers members = new AllMembers();
    AllProviders providers = new AllProviders();
    

    @Before
    public void setUp(){
        //Setup for both
        //setup for testOperatorTerminalVerify
        AllOperators operators = new AllOperators();
        operators.addOperator(1, "name");
        oT = new OperatorTerminal(providers, members, operators);

        //setup for testAddMember
        oT = new OperatorTerminal(providers, members, operators);
        members.addMember(223456789, "stuff", "stuff", "stuff", "stuff", "stuff", 123456); 
    }

    @Test
    public void testOperatorTerminalVerify(){
        if(oT.operatorVerified!=false){
            fail("Operator terminal verify should be false by default");
        }
        oT.verify("1");
        if(oT.operatorVerified!=true){
            fail("Operator terminal verify should have been true");
        }
    }
    @Test
    public void testAddMember(){
        int totalMembersBefore = members.memberList.size();

        oT.addMember("stuff", "stuff", "stuff", "stuff", 123456);
        int totalMembersAfter = members.memberList.size();

        if(totalMembersBefore + 1 != totalMembersAfter){
            fail("Did not add one member");
        }


    }
    //JUnit test for someone elses 
    @Test
    public void successTest(){
        Provider testProv = new Provider("prov1", creds, "4405 ridge ave", "Tuscaloosa", "Alabama", 20198);
        if(testProv.getCreds() != creds){
            fail("Credentials is wrong");
        }
    }
    @After
    public void noNeed(){}
}
