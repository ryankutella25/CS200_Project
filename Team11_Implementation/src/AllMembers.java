import java.util.ArrayList;

// Class containing a list of all members, and functions to manipulate the list
// @author Adison Viars

public class AllMembers {

    public AllMembers(){}

    //Creates a new list of members
    public ArrayList<Member> memberList = new ArrayList<Member>(); 
    public long memberNumber; // 9 digits
    public String name; // 25 characters
    public String status; // "Paid" or "Unpaid"
    public String address; // 25 characters
    public String city; // 14 characters
    public String state; // 2 characters
    public int zip; // 5 digits

    //Provider needs to verify that member is valid in order to provide services and bill ChocAn
    public String verifyMember(long memberNumber) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toVerify = memberList.get(i);
            //Search for correct member having input memberNumber and pay status
            if (toVerify.getMemberNumber() == memberNumber && toVerify.getStatus() == "Paid") {
                //Member has paid and memberNumber exists
                return "Validated";
            }
            else if (toVerify.getMemberNumber() == memberNumber && toVerify.getStatus() == "Unpaid") {
                //Member has not paid dues and memberNumber exists
                return "Member suspended";
            }
        }
        //No member with matching input memberNumber found
        return "Invalid number";
    }

    //Finds and returns the member with matching input memberNumber
    public Member getMember(long memberNumber) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toGet = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toGet.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                return toGet;
            }
        }
        //No member with matching input memberNumber found
        return null;
    }

    //Operator chooses to add a member with input memberNumber, name, status, address, city, state, zip
    public void addMember(long memberNumber, String name, String status, String address, String city, String state, int zip) {
        Member toAdd = new Member(name, memberNumber, status, address, city, zip, state);
        memberList.add(toAdd);
    }

    //Operator chooses to delete the member with input memberNumber
    public void deleteMember(long memberNumber) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toDelete = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toDelete.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                memberList.remove(i);
                return;
            }
        }
    }

    //Operator chooses to update the member name with input memberNumber
    public void updateMemberName(long memberNumber, String name) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toUpdate = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toUpdate.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                toUpdate.setName(name);
                return;
            }
        }
    }

    //Operator chooses to update the member address with input memberNumber
    public void updateMemberAddress(long memberNumber, String address) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toUpdate = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toUpdate.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                toUpdate.setAddress(address);
                return;
            }
        }
    }

    //Operator chooses to update the member status with input memberNumber
    public void updateMemberStatus(long memberNumber, String status) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toUpdate = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toUpdate.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                toUpdate.setStatus(status);
                return;
            }
        }
    }

    //Operator chooses to update the member city with input memberNumber
    public void updateMemberCity(long memberNumber, String city) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toUpdate = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toUpdate.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                toUpdate.setCity(city);
                return;
            }
        }
    }

    //Operator chooses to update the member state with input memberNumber
    public void updateMemberState(long memberNumber, String state) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toUpdate = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toUpdate.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                toUpdate.setState(state);
                return;
            }
        }
    }

    //Operator chooses to update the member zipcode with input memberNumber
    public void updateMemberZip(long memberNumber, int zip) {
        for (int i = 0; i < memberList.size(); i++) {
            Member toUpdate = memberList.get(i);
            //Search for correct member having input memberNumber
            if (toUpdate.getMemberNumber() == memberNumber) {
                //Member with matching memberNumber found
                toUpdate.setZip(zip);
                return;
            }
        }
    }
}
