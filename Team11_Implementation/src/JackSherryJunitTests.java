import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class JackSherryJunitTests {

    AllMembers members;
    AllProviders providers;
    Provider singleProvider;

    @Before
    public void setUp() {
        // this.members = members;
        // this.providers = providers;
        members = new AllMembers();
        providers = new AllProviders();
        singleProvider = new Provider("Jon", 0, null, null, null, 0);
    }

    @Test
    public void clearTest1() {
        ArrayList<Member> memberList = members.memberList;
        memberList.clear();
        assertTrue(memberList.isEmpty());
    }

    @Test
    public void clearTest2() {
        ArrayList<Provider> providerList = providers.providerList;
        providerList.clear();
        assertTrue(providerList.isEmpty());
    }

    @Test
    public void test3() {
        singleProvider.setCity("Atlanta");
        assertTrue(singleProvider.getCity() == "Atlanta");
    }
}