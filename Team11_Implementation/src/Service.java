//@author Luca Jontz
public class Service{
    private String name;
    private int code;
    private int fee;

    public Service(){}

    public Service(String name, int code, int fee){
        //Service initialization
        this.name = name;
        this.code = code;
        this.fee = fee;
    }

    public String getName(){
        return name; //return this service's name
    }
    public int getCode(){
        return code; //return this service's code
    }
    public int getFee(){
        return fee; //return this service's fee
    }
    public String toString(){
        return name + ", Code: " + code + ", Fee: " + fee; //return a string that has this service's info
    }
}
