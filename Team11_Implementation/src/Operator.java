// Class containing information and constructors for operators
// @author Adison Viars

public class Operator {
    
    private String name;
    private long credentials;

    Operator(long credentials, String name) {
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
