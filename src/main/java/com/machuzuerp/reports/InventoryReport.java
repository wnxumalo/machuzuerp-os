
package com.machuzuerp.reports;

import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.controllers.jsf.SupplierList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class InventoryReport implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String iRecNum;
    private String code;
    private String description;
    private int supplierId;
    private String supplier;
    private BigDecimal cost;
    private BigDecimal sellingPrice;
    private float reorderLevel;
    private double markup;
    private double atHand;
    private String size;
    private String color;
    private int quantity;
    private BigDecimal totCost = new BigDecimal(0);
    private BigDecimal turnOver = new BigDecimal(0);

    private String byCode;
    int count = 9;
    private String byDescription;

    String inventoryRecNum = "";

    private List<InventoryReport> inventory = new ArrayList<InventoryReport>();   
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";

    public InventoryReport(String iRecNum, String code, String description, int supplierId, String supplier, BigDecimal cost, BigDecimal sellingPrice, float reorderLevel, double markup, double atHand, String size, String color, int quantity, BigDecimal totCost, BigDecimal turnOver) {

        this.iRecNum = iRecNum;
        this.code = code;
        this.description = description;
        this.supplierId = supplierId;
        this.supplier = supplier;
        this.cost = cost;
        this.sellingPrice = sellingPrice;
        this.reorderLevel = reorderLevel;
        this.markup = markup;
        this.atHand = atHand;
        this.size = size;
        this.color = color;       
        this.quantity = quantity;       
        this.totCost = totCost;       
        this.turnOver = turnOver;       
    }
    
    public InventoryReport() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public void reportSupplierInventory(Long supplierId) {

        inventory.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM inventory where supplier = '" + supplierId + "' order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            
            while (results.next()) {                        
                System.out.println("SQLE CHECK 111: "+ results.getBigDecimal(6) + ":" + results.getBigDecimal(12));
                totCost.add(results.getBigDecimal(6).add(results.getBigDecimal(12)));
                turnOver.add(results.getBigDecimal(6).add(results.getBigDecimal(12)));
                System.out.println("SQLE CHECK 222: " + totCost + ":" + turnOver);
                inventory.add(new InventoryReport(results.getString(1), results.getString(4), results.getString(8), results.getInt(14), "", results.getBigDecimal(6), results.getBigDecimal(12), results.getFloat(11), results.getDouble(10), results.getDouble(2), results.getString(9),"", 1, totCost, turnOver));
                
                //public InventoryReport(String iRecNum, String code, String description, int supplierId, String supplier, BigDecimal cost, BigDecimal sellingPrice, float reorderLevel, double markup, double atHand, String size, String color, int quantity, BigDecimal totCost) {
            }
System.out.println("SQLE CHECK 333");
            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println("ERR:"+sqle);
        }
        
    }

    public List<InventoryReport> getReportAllInventory() {

        inventory.clear();

        try {

            connection = Systems.initConnection();

            query = "SELECT * FROM inventory order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);            
            
            while (results.next()) {                
                totCost.add(results.getBigDecimal(6).add(results.getBigDecimal(12)));
                turnOver.add(results.getBigDecimal(6).add(results.getBigDecimal(12)));
                //inventory.add(new InventoryReport(results.getString(1), results.getString(2), results.getString(3), results.getInt(4), results.getString(5), Double.parseDouble(results.getString(6)), Double.parseDouble(results.getString(7)), Float.parseFloat(results.getString(8)), Double.parseDouble(results.getString(9)), Double.parseDouble(results.getString(10)), results.getString(14), results.getString(15), 1, 0.0));
                inventory.add(new InventoryReport(results.getString(1), results.getString(4), results.getString(8), results.getInt(14), "", results.getBigDecimal(6), results.getBigDecimal(12), results.getFloat(11), results.getDouble(10), results.getDouble(2), results.getString(9),"", 1, totCost, turnOver));
                
    //public InventoryReport(String iRecNum, String code, String description, int supplierId, String supplier, BigDecimal cost, BigDecimal sellingPrice, float reorderLevel, double markup, double atHand, String size, String color, int quantity, BigDecimal totCost) {                
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println("ERR:"+sqle);
        }
        
        return inventory;
        
    }
    
    public List<InventoryReport> getReportInventory() {
        return inventory;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public String getRecNum() {
        return iRecNum;
    }

    public void setRecNum(String iRecNum) {
        this.iRecNum = iRecNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }       

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }
    
    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    
    public float getReorderLevel() {
        return reorderLevel;
    }
    
    public void setReorderLevel(float reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
    
    public double getMarkup() {
        return markup;
    }
    
    public void setMarkup(double markup) {
        this.markup = markup;
    }

    public void setAtHand(double atHand) {
        this.atHand = atHand;
    }
    
    public double getAtHand() {
        return atHand;
    }    
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getSize() {
        return size;
    }  
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }  
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    } 
    
    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }
    
    public BigDecimal getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(BigDecimal turnOver) {
        this.turnOver = turnOver;
    }

}
