import java.util.ArrayList;


// Class containing information for members.

// @author Ali Burkemper

public class Member {

    private String name; // 25 characters
    private long memberNumber; //9 digits
    private String status; // Paid, terminated, unpaid
    private String address; // 25 characters
    private String city; // 14 characters
    private int zipcode; // 6 digits
    private String state; // 2 characters
    public ArrayList<ServiceRecord> serviceReports;
    private MemberReport memberReport;

    // Create a new member to be put into the ArrayList
    Member(String name, long memberNumber, String status, String address, String city, int zipcode, String state) {
        this.name = name;
        this.memberNumber = memberNumber;
        this.status = status;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
        serviceReports = new ArrayList<ServiceRecord>();
    }

    // Add report to memberReport
    public void setReport(MemberReport report){
        memberReport = report;
    }

    // Get member report
    public MemberReport getReport(){
        return memberReport;
    }

    // Add service to ArrayList
    public void addService(ServiceRecord service){
        serviceReports.add(service);
    }

    // Get Service from ArrayList
    public ArrayList<ServiceRecord> getService() {
        return serviceReports;
    }

    // Get member number
    public long getMemberNumber() {
        return memberNumber;
    }

    // Set member number
    public void setMemberNumber(long memberNumber) {
        this.memberNumber = memberNumber;
    }

    // Get name of member
    public String getName() {
        return name;
    }

    // Set name of member
    public void setName(String name) {
        this.name = name;
    }

    // Get status of member
    public  String getStatus() {
        return status;
    }

    // Set status of member
    public void setStatus(String status) {
        this.status = status;
    }

    // Get address of member
    public  String getAddress() {
        return address;
    }

    // Set address of member
    public void setAddress(String address) {
        this.address = address;
    }
    
    // Get city of member
    public String getCity() {
        return city;
    }

    // Set city of member
    public void setCity(String city) {
        this.city = city;
    }

    // Get zipcode of member
    public int getZip() {
        return zipcode;
    }

    // Set zipcode of member
    public void setZip(int zipcode) {
        this.zipcode = zipcode;
    }

    // Get state of member
    public String getState() {
        return state;
    }

    // Set state of member
    public void setState(String state) {
        this.state = state;
    }

    // Clear the service record array list
    public void clearReports(ArrayList<ServiceRecord> serviceReports) {
        serviceReports.clear();
    }
}
