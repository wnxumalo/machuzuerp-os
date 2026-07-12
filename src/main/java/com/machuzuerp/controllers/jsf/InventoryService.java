package com.machuzuerp.controllers.jsf;
 
import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import com.machuzuerp.jdbc.SupplierProcessing;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
 
@ApplicationScoped
@Named("invService")
public class InventoryService {
    
    Connection connection;
    Statement statement;
    ResultSet results;

    String query = "";
    private List<InventoryData> allInventory = new ArrayList<InventoryData>();
    
    SupplierProcessing sp = new SupplierProcessing();
     
    public List<InventoryData> createServices() {
        List<InventoryData> list = new ArrayList<InventoryData>();

        allInventory.clear();

        try {
              
            connection = Systems.initConnection();
            query = "SELECT * FROM inventory order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            int x = 0;
            while (results.next()) {
                //allInventory.add(new InventoryData(results.getString(1), results.getString(2), results.getString(3), results.getInt(4), results.getString(5), results.getBigDecimal(6), results.getBigDecimal(7), results.getInt(8), results.getBigDecimal(9), results.getInt(10), results.getString(14), results.getString(15), 1, new BigDecimal("0"), results.getInt(20), results.getString(21)));                

                sp.getSupplier(results.getString(14));

                allInventory.add(new InventoryData(results.getString(1), results.getString(4), results.getString(8), results.getInt(14), sp.supplierName, results.getBigDecimal(6), results.getBigDecimal(12), results.getInt(11), results.getBigDecimal(10), results.getInt(2), results.getString(9), results.getString(5), 1, new BigDecimal("0"), results.getInt(3), "", results.getString(9), x));
                
                x++;
            }
//public InventoryData(String iRecNum, String code, String description, int supplierId, String supplier, BigDecimal cost, BigDecimal sellingPrice, int reorderLevel, BigDecimal markup, int atHand, String size, String color, int quantity, BigDecimal totCost, int clientId, String client) {
            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return allInventory;
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
