import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//@author Adison Viars
//Class for testing the feature Verify Manager
//Testing methods addManager(long credentials, String name) and getCreds() from my classes
//Testing method verify(String input) from ManagerTerminal class written by Ryan

public class AdisonJUnitTests {
    
    ManagerTerminal mt;
    AllManagers managers = new AllManagers();
    AllProviders providers = new AllProviders();
    AllMembers members = new AllMembers();
    ManagerReport testReport = new ManagerReport(providers);
    Manager testManager;
    String input;

    @Before
    public void setUp() {
        //Set up for all tests
        mt = new ManagerTerminal(providers, members, managers, testReport);
        long credentials1 = 000000000;
        String name1 = "Manager One";
        //Set up for testAddManager
        managers.addManager(credentials1, name1);
        //Set up for testVerify
        mt.managerVerified = false;
        input = "000000000";
        //Set up for testGetCreds
        testManager = new Manager(000000001, "Manager Two");
    }

    //Success test for adding a manager
    //Passes if getManager is able to return the added manager (the manager was added correctly)
    @Test
    public void testAddManager() {
        assertNotNull(managers.getManager(000000000));
    }

    //Success test for verifying a manager's credentials in the Manager Terminal
    //Passes if managerVerified is correctly set to true for existing input credentials
    @Test
    public void testVerify1() {
        //Have to start with managerVerified as false
        mt.managerVerified = false;
        mt.verify(input);
        assertTrue(mt.managerVerified);
    }

    //Failure test for verifying a manager's credentials in the Manager Terminal
    //Passes if managerVerified is correctly set to false for input credentials that do not exist
    @Test
    public void testVerify2() {
        //Have to start with managerVerified as false
        mt.managerVerified = false;
        mt.verify("1");
        assertFalse(mt.managerVerified);
    }

    //Success test for getting a manager's credentials
    //Passes if testManager.getCreds() correctly returns testManager's credentials
    @Test
    public void testGetCreds() {
        assertEquals(000000001, testManager.getCreds());
    }

    @After
    public void notNeeded() {}
}
