// Class containing information and constructors for managers
// @author Adison Viars

public class Manager {

    private String name;
    private long credentials;

    Manager(long credentials, String name) {
        this.credentials = credentials;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreds(){
        return credentials;
    }

    public void setCreds(long credentials) {
        this.credentials = credentials;
    }
}
