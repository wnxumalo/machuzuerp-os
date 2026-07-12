package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Inv;
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
public class InventoryBean implements Serializable {

    private Inv selectedInventory;
    private List<Inv> inventory;
    
    ResultSet results;

    public InventoryBean(){
        inventory = new ArrayList<Inv>();
        
        inventory.clear();
        inventory.add(new Inv(0, "N/A"));
        try {

            String query = "";
            String employeeRecNum = "";
            boolean proceed = false;         
              
                query = "SELECT * FROM inventory order by description";                
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    employeeRecNum = results.getString(1);
                    inventory.add(new Inv(Integer.parseInt(results.getString(1)), results.getString(7)));
                    System.out.println("222: " + results.getString(1) + ":"+ results.getString(2));
                }
                          System.out.println("333");
        } catch (Exception sqle) {
            System.out.println(sqle);
        }                          
    }

    public Inv getSelectedInventory() {
        return selectedInventory;
    }

    public void setSelectedInventory(Inv selectedInventory) {
        this.selectedInventory = selectedInventory;
    }

    public List<Inv> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inv> inventory) {
        this.inventory = inventory;
    }

    public Inv getInventory(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Inv invs : inventory){
            if (id.equals(invs.getId())){
                return invs;
            }
        }
        return null;
    }

    
}