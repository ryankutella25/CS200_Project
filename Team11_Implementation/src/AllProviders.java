import java.util.ArrayList;

// Class containing a list of all providers, and functions to manipulate the list
// @author Adison Viars

public class AllProviders {

    //Creates a new list of providers
    public ArrayList<Provider> providerList = new ArrayList<Provider>();
    public long credentials; // 9 digits
    public String name; // 25 characters
    public String address; // 25 characters
    public String city; // 14 characters
    public String state; // 2 characters
    public int zip; // 5 digits

    public AllProviders(){}

    //Terminal verifies that provider credentials are valid when they log in
    public boolean verifyProvider(long credentials){
        for (int i = 0; i < providerList.size(); i++) {
            Provider toVerify = providerList.get(i);
            //Search for correct provider having input credentials
            if (toVerify.getCreds() == credentials) {
                //Provider credentials exist
                return true;
            }
        }
        //No provider with matching input credentials found
        return false;
    }

    //Finds and returns the provider with matching input credentials
    public Provider getProvider(long credentials) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider toGet = providerList.get(i);
            //Search for correct provider having input credentials
            if (toGet.getCreds() == credentials) {
                //Provider with matching credentials found
                return toGet;
            }
        }
        //No provider with matching input credentials found
        return null;
    }

    //Operator chooses to add a provider with input credentials, name, address, city, state, and zip
    public void addProvider(long credentials, String name, String address, String city, String state, int zip) {
        Provider toAdd = new Provider(name, credentials, address, city, state, zip);
        providerList.add(toAdd);
    }

    //Operator chooses to delete the provider with input credentials
    public void deleteProvider(long credentials) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider toDelete = providerList.get(i);
            //Search for correct provider having input credentials
            if (toDelete.getCreds() == credentials) {
                //Provider with matching credentials found
                providerList.remove(i);
            }
        }
    }

    //Operator chooses to update the provider name with input credentials
    public void updateProviderName(long credentials, String name) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider toUpdate = providerList.get(i);
            //Search for correct provider having input credentials
            if (toUpdate.getCreds() == credentials) {
                //Provider with matching credentials found
                toUpdate.setName(name);
            }
        }
    }

    //Operator chooses to update the provider address with input credentials
    public void updateProviderAddress(long credentials, String address) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider toUpdate = providerList.get(i);
            //Search for correct provider having input credentials
            if (toUpdate.getCreds() == credentials) {
                //Provider with matching credentials found
                toUpdate.setAddress(address);
            }
        }
    }

    //Operator chooses to update the provider city with input credentials
    public void updateProviderCity(long credentials, String city) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider toUpdate = providerList.get(i);
            //Search for correct provider having input credentials
            if (toUpdate.getCreds() == credentials) {
                //Provider with matching credentials found
                toUpdate.setCity(city);
            }
        }
    }

    //Operator chooses to update the provider state with input credentials
    public void updateProviderState(long credentials, String state) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider toUpdate = providerList.get(i);
            //Search for correct provider having input credentials
            if (toUpdate.getCreds() == credentials) {
                //Provider with matching credentials found
                toUpdate.setState(state);
            }
        }
    }

    //Operator chooses to update the provider zipcode with input credentials
    public void updateProviderZip(long credentials, int zip) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider toUpdate = providerList.get(i);
            //Search for correct provider having input credentials
            if (toUpdate.getCreds() == credentials) {
                //Provider with matching credentials found
                toUpdate.setZip(zip);
            }
        }
    }
}

