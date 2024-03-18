import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;

//@author Luca Jontz

public class ProviderDirectory {
    
    ArrayList<Service> services;
    Provider currProvider;

    ProviderDirectory(Provider provider){
        //ProviderDirectory initialization
        services = provider.getServices();
        currProvider = provider;
    }

    public void requestDirectory(){
        Collections.sort(services, new ServiceComparator()); //sort the list of services by alphabetical order
            
        String name = currProvider.getName();
        name = name.replaceAll("\\s", "");
        File outputFile = new File("Team11_Implementation" + File.separator + "data" + File.separator + "ProviderDirectories"+File.separator+name+"Services.txt"); //create a file to storet the directory

        try{
            FileWriter myWriter = new FileWriter(outputFile.getPath());
            if(services!=null){
                for(Service s : services){
                    myWriter.write(s.toString()); //loop through the list of services if it is not empty and write them to the directory file
                    myWriter.write("\n");
                }
            }
            myWriter.close();
        } catch(IOException e){
            System.out.println("error occurred in provider directory file creation\n");
        };
        return;
    }

    public ArrayList<Service> requestServices(){
        return services; //return the list of services
    }

    class ServiceComparator implements java.util.Comparator<Service> { //function to compare services so that they can be sorted alphabetically
        public int compare(Service a, Service b){
            String aName = a.getName();
            String bName = b.getName();
            return aName.compareTo(bName);
        }
    }

    public JPanel getPanel(){
        JPanel panel = new JPanel(); 
        panel.add(new JLabel("Provider Directory"));
        return panel;
    }
}
