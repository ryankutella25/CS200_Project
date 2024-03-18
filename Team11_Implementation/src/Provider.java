import java.util.ArrayList;

//@author Luca Jontz

public class Provider{

    public String name;
    private long credentials;
    private String address;
    private String city;
    private String state;
    private int zipcode;
    private ArrayList<Service> services = new ArrayList<Service>();
    public ArrayList<ServiceRecord> records = new ArrayList<ServiceRecord>();
    public ProviderReport report;
    
    Provider(String name, long credentials, String address, String city, String state, int zipcode){
        //Provider initialization
        this.name = name;
        this.credentials = credentials;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
        Service temp1 = new Service("Session with Dietitian", 598470, 50);
        Service temp2 = new Service("Aerobics Exercise Session", 883948, 150);
        services.add(temp1);
        services.add(temp2);
    }

    public void addService(Service newService){
        services.add(newService); //add a new service to this provider's list
    }

    public void setReport(ProviderReport newReport){
        report = newReport; //Set this provider's report
    }

    public ProviderReport getReport(){
        return report; //return this provider's report
    }

    public String getName(){
        return name; //return this provider's name
    }

    public void setName(String name) {
        this.name = name; //set this provider's name
    }

    public long getCreds(){
        return credentials; //return this provider's credentials(provider num)
    }

    public void setCreds(long credentials) {
        this.credentials = credentials; //set this provider's credentials(provider num)
    }

    public  String getAddress() {
        return address; //return this provider's address
    }

    public void setAddress(String address) {
        this.address = address; //set this provider's address
    }

    public String getCity() {
        return city; //return this provider's city
    }

    public void setCity(String city) {
        this.city = city; //set this provider's city
    }

    public int getZip() {
        return zipcode; //return this provider's zipcode
    }

    public void setZip(int zipcode) {
        this.zipcode = zipcode; //set this provider's zipcode
    }

    public String getState() {
        return state; //return this provider's state
    }

    public void setState(String state) {
        this.state = state; //set this provider's state
    }

    public ArrayList<Service> getServices(){
        return services; //return the list of this provider's services
    }
    public void addRecord(ServiceRecord record){
        records.add(record); //add a service record to this provider's list of records
    }
    public ArrayList<ServiceRecord> getRecords(){
        return records; //return this provider's list of records
    }
    public void clearReports(ArrayList<ServiceRecord> serviceReports) {
        serviceReports.clear(); //clear this provider's service records
    }
}