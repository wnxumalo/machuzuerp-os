package com.machuzuerp.controllers.jsf;
 
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
 
@ApplicationScoped
@Named("servService")
public class ServService {
    
    Connection connection;
    Statement statement;
    
    ResultSet results;

    private List<ServiceData> allServices = new ArrayList<ServiceData>();    

    public List<ServiceData> createServices() {

        allServices.clear();

        try {

            String query = "";          

            connection = Systems.initConnection();
            
            query = "SELECT * FROM services order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {            
                allServices.add(new ServiceData(results.getString(1),results.getString(2), results.getString(3), results.getBigDecimal(7),1,BigDecimal.valueOf(0)));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return allServices;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }
     
    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }
     
    public boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true: false;
    }
   
}
