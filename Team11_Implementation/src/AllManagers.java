import java.util.ArrayList;

// Class containing a list of all managers, and functions to manipulate the list
// @author Adison Viars

public class AllManagers {

    public AllManagers(){}

    //Creates a new list of managers
    public ArrayList<Manager> managerList = new ArrayList<Manager>(); 
    public long credentials; // 9 digits
    public String name; // 25 characters

    //Terminal verifies that manager credentials are valid when they log in
    public boolean verifyManager(long credentials) {
        for (int i = 0; i < managerList.size(); i++) {
            Manager toVerify = managerList.get(i);
            //Search for correct manager having input credentials
            if (toVerify.getCreds() == credentials) {
                //Manager credentials exist
                return true;
            }
        }
        //No manager with matching input credentials found
        return false;
    }

    //Finds and returns the manager with matching input credentials
    public Manager getManager(long credentials) {
        for (int i = 0; i < managerList.size(); i++) {
            Manager toGet = managerList.get(i);
            //Search for correct manager having input credentials
            if (toGet.getCreds() == credentials) {
                //Manager with matching credentials found
                return toGet;
            }
        }
        //No manager with matching input credentials found
        return null;
    }

    //Adds a manager to the list with input credentials and name
    public void addManager(long credentials, String name) {
        Manager toAdd = new Manager(credentials, name);
        managerList.add(toAdd);
    }

    //Deletes a manager from the list with input credentials
    public void deleteManager(long credentials) {
        for (int i = 0; i < managerList.size(); i++) {
            Manager toDelete = managerList.get(i);
            //Search for correct manager having input credentials
            if (toDelete.getCreds() == credentials) {
                //Manager with matching credentials found
                managerList.remove(i);
            }
        }
    }
}

