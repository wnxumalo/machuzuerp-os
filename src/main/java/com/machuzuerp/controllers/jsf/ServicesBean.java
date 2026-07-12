package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.CustomerProcessing;
import static com.machuzuerp.controllers.jsf.Systems.connection;
import static com.machuzuerp.controllers.jsf.Systems.statement;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class ServicesBean implements Serializable {

    private StakeholderServices selectedService;
    private List<StakeholderServices> services;
    
    ResultSet results;
    
    CustomerProcessing cp = new CustomerProcessing();

    public ServicesBean() {
        services = new ArrayList<StakeholderServices>();
        
        services.clear();
        services.add(new StakeholderServices(0, "N/A"));
        try {

            String query = "";
            String employeeRecNum = "";
            boolean proceed = false;         
              
                query = "SELECT * FROM services order by description";                
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                while (results.next()) {                    
                    services.add(new StakeholderServices(Integer.parseInt(results.getString(1)), results.getString(3)));
                }
                          
        } catch (Exception sqle) {
            System.out.println(sqle);
        }                          
    }

    public StakeholderServices getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(StakeholderServices selectedService) {
        this.selectedService = selectedService;
    }

    public List<StakeholderServices> getServices() {
        return services;
    }

    public void setServices(List<StakeholderServices> services) {
        this.services = services;
    }

    public StakeholderServices getServices(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (StakeholderServices emps : services){
            if (id.equals(emps.getId())){
                return emps;
            }
        }
        return null;
    }        
    
}