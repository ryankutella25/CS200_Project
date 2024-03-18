import java.util.ArrayList;

// Class containing a list of all operators, and functions to manipulate the list
// @author Adison Viars

public class AllOperators {

    public AllOperators(){}

    //Creates a new list of operators
    public ArrayList<Operator> operatorList = new ArrayList<Operator>(); 
    public long credentials; // 9 digits
    public String name; // 25 characters
    
    //Terminal verifies that operator credentials are valid when they log in
    public boolean verifyOperator(long credentials) {
        for (int i = 0; i < operatorList.size(); i++) {
            Operator toVerify = operatorList.get(i);
            //Search for correct operator having input credentials
            if (toVerify.getCreds() == credentials) {
                //Operator credentials exist
                return true;
            }
        }
        //No operator with matching input credentials found
        return false;
    }

    //Finds and returns the operator with matching input credentials
    public Operator getOperator(long credentials) {
        for (int i = 0; i < operatorList.size(); i++) {
            Operator toGet = operatorList.get(i);
            //Search for correct operator having input credentials
            if (toGet.getCreds() == credentials) {
                //Operator with matching credentials found
                return toGet;
            }
        }
        //No operator with matching input credentials found
        return null;
    }

    //Adds an operator to the list with input credentials and name
    public void addOperator(long credentials, String name) {
        Operator toAdd = new Operator(credentials, name);
        operatorList.add(toAdd);
    }

    //Deletes an operator from the list with input credentials
    public void deleteOperator(long credentials) {
        for (int i = 0; i < operatorList.size(); i++) {
            Operator toDelete = operatorList.get(i);
            //Search for correct operator having input credentials
            if (toDelete.getCreds() == credentials) {
                //Operator with matching credentials found
                operatorList.remove(i);
            }
        }
    }
}
