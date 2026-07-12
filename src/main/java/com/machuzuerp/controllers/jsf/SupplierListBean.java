package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Inventory;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

@SessionScoped
@Named
public class SupplierListBean implements Serializable {

    private SupplierList selectedSupplierList;
    private List<SupplierList> supplier;
    
    Connection conn;
    Statement statement;
    ResultSet results;

    public SupplierListBean(){
        
    }
    
    @PostConstruct
    public void init() {
        
        supplier = new ArrayList<SupplierList>();
        
        supplier.clear();
       // supplier.add(new SupplierList(new Long(0), "","","","","","",""));
        conn = Systems.initConnection();
        
        try {

            String query = "";
              
                 query = "SELECT * FROM supplier order by description";                
                statement = conn.createStatement();
                results = statement.executeQuery(query);

              //  supplier.add(new SupplierList(new Long(0), "","","","","","",""));
                while (results.next()) {
                    supplier.add(new SupplierList(results.getLong(1), results.getString(11),results.getString(6),"",results.getString(17),results.getString(16),results.getString(4),results.getString(22)));                    
                }
                          
        } catch (SQLException sqle) {
            System.out.println("SUPPERR: " + sqle);
        }    
        
    }

    public SupplierList getSelectedSupplierList() {
        if (selectedSupplierList == null) {
            selectedSupplierList = new SupplierList();
        }
        return selectedSupplierList;
    }

    public void setSelectedSupplierList(SupplierList selectedSupplierList) {
        this.selectedSupplierList = selectedSupplierList;
    }

    public List<SupplierList> getSupplierList() {
        System.out.println("SUPPPLIER: " + supplier.size());
        return supplier;
    }
    
    public void setSupplierList(List<SupplierList> supplier) {
        this.supplier = supplier;
    }

    public SupplierList getSupplierList(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (SupplierList client : supplier){
            if (id.equals(client.getId())){
                return client;
            }
        }
        return null;
    }        

    
}
