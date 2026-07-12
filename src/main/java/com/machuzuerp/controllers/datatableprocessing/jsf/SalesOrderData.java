/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.controllers.jsf.SalesOrder;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class SalesOrderData implements Serializable {

    /**
     * Creates a new instance of SalesOrderData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 
    private String iRecNum;
    private String iCheck;
    private String iDate;
    private String cName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String email;
    private String vatNum;
    private String posAdd;
    private String physAdd;
    private String country;
    private String notes;
    private String cid;
    
    private Date dFrom;
    private Date dTo;
    
    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;
    
    private BigDecimal totServCost;
    private BigDecimal totInvCost;
    private BigDecimal totCost;
    BigDecimal invCost = new BigDecimal("0");
    BigDecimal cost = new BigDecimal("0");
    int count = 0;
    int quantity = 0;

    String salesOrderRecNum = "";

    private List<SalesOrderData> salesOrders = new ArrayList<SalesOrderData>();
    private List<SalesOrderData> allSalesOrders = new ArrayList<SalesOrderData>();
    private List<SalesOrderData> filteredSalesOrders = new ArrayList<SalesOrderData>();
    private SalesOrderData selectedSalesOrder;
    private List<SalesOrder> selectedInv;
    
    private List<ServiceData> selectedServices = new ArrayList<ServiceData>();
    private List<InventoryData> selectedInventory = new ArrayList<InventoryData>();
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";

    public SalesOrderData() {
        
    }

    public SalesOrderData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String notes, String cid) {

        this.iRecNum = iRecNum;
        this.iCheck = iCheck;
        this.iDate = iDate;
        this.cName = cName;
        this.cSurname = cSurname;
        this.tel = tel;
        this.cell = cell;
        this.fax = fax;
        this.email = email;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.country = country;
        this.notes = notes;
        this.cid = cid;
    }

    public String searchSalesOrder(Date dFrom, Date dTo) {

        salesOrders.clear();
        try {

            String query = "";
            String invoiceRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            String from = sdf.format(dFrom);
            String to = sdf.format(dTo);

            int i = 0;
            query = "SELECT * FROM salesorders where salesorderdate between '" + from + "' and '" + to + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                salesOrders.add(new SalesOrderData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(16), results.getString(15)));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/accounting/receivable/salesorder/sales-order-listing";

    }

    public List<SalesOrderData> getAllSalesOrders() {

        allSalesOrders.clear();

        try {

            String query = "";
            String salesOrderRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();
            
            query = "SELECT * FROM salesOrders order by salesOrdercheck";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                salesOrderRecNum = results.getString(1);
                allSalesOrders.add(new SalesOrderData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15)));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println("ERR:"+sqle);
        }

        return allSalesOrders;
    }

    public void filterSalesOrders() {

        filteredSalesOrders.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM salesOrders where name like '%" + cName + "%' order by name";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                salesOrderRecNum = results.getString(1);
                filteredSalesOrders.add(new SalesOrderData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15)));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    public List<SalesOrder> getSelectedInv() {
        return selectedInv;
    }
 
    public void setSelectedInv(List<SalesOrder> selectedInv) {
        this.selectedInv = selectedInv;
    }

    public List<SalesOrderData> getFilteredSalesOrders() {

        filterSalesOrders();

        return filteredSalesOrders;
    }

    public List<SalesOrderData> getSalesOrders() {
        return salesOrders;
    }

   /* public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }*/
    
    public String getRecNum() {
        return iRecNum;
    }

    public void setRecNum(String iRecNum) {
        this.iRecNum = iRecNum;
    }

    public String getInvCheck() {
        return iCheck;
    }

    public void setInvCheck(String iCheck) {
        this.iCheck = iCheck;
    }

    public String getSalesOrderDate() {
        return iDate;
    }

    public void setSalesOrderDate(String iDate) {
        this.iDate = iDate;
    }
    
    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }       

    public String getCSurname() {
        return cSurname;
    }

    public void setCSurname(String cSurname) {
        this.cSurname = cSurname;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public String getCell() {
        return cell;
    }
    
    public void setCell(String cell) {
        this.cell = cell;
    }   

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }

    public void setPosAdd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPosAdd() {
        return posAdd;
    }    

    public String getPhysAdd() {
        return physAdd;
    }      

    public void setPhysAdd(String physAdd) {
        this.physAdd = physAdd;
    }
    
    public String getCountry() {
        return country;
    }  
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    
    public List<ServiceData> getSelectedServs() {
        return selectedServices;
    }

    public void setSelectedServs(List<ServiceData> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public List<InventoryData> getSelectedInvs() {
        return selectedInventory;
    }

    public void setSelectedInvs(List<InventoryData> selectedInventory) {
        this.selectedInventory = selectedInventory;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPosAdd() {
        return orgPosAdd;
    }

    public void setOrgPosAdd(String orgPosAdd) {
        this.orgPosAdd = orgPosAdd;
    }

    public String getOrgPhysAdd() {
        return orgPhysAdd;
    }

    public void setOrgPhysAdd(String orgPhysAdd) {
        this.orgPhysAdd = orgPhysAdd;
    }       
    
    public BigDecimal getTotServCost() {
        return totServCost;
    }

    public void setTotServCost(BigDecimal totServCost) {
        this.totServCost = totServCost;
    }

    public BigDecimal getTotInvCost() {
        return totInvCost;
    }

    public void setTotInvCost(BigDecimal totInvCost) {
        this.totInvCost = totInvCost;
    }

    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }
    
    public Date getDFrom() {
        return dFrom;
    }

    public void setDFrom(Date dFrom) {
        this.dFrom = dFrom;
    }
    
    public Date getDTo() {
        return dTo;
    }

    public void setDTo(Date dTo) {
        this.dTo = dTo;
    }

    public SalesOrderData getSelectedSalesOrder() {
        return selectedSalesOrder;
    }

    public void setSelectedSalesOrder(SalesOrderData selectedSalesOrder) {
        this.selectedSalesOrder = selectedSalesOrder;
    }
    
    public String onRowSelect(SelectEvent event) throws IOException, SQLException {
System.out.println("00000");
        this.iRecNum = ((SalesOrderData) event.getObject()).iRecNum;
        this.iCheck = ((SalesOrderData) event.getObject()).iCheck;
        this.iDate = ((SalesOrderData) event.getObject()).iDate;
        this.cName = ((SalesOrderData) event.getObject()).cName;
        this.cSurname = ((SalesOrderData) event.getObject()).cSurname;
        this.tel = ((SalesOrderData) event.getObject()).tel;
        this.cell = ((SalesOrderData) event.getObject()).cell;
        this.fax = ((SalesOrderData) event.getObject()).fax;
        this.notes = ((SalesOrderData) event.getObject()).notes;
        this.email = ((SalesOrderData) event.getObject()).email;
        this.posAdd = ((SalesOrderData) event.getObject()).posAdd;
        this.physAdd = ((SalesOrderData) event.getObject()).physAdd;
        this.country = ((SalesOrderData) event.getObject()).country;
        this.cid = ((SalesOrderData) event.getObject()).cid;                

        String param = "iCheck=" + iCheck + "&recnum=" + iRecNum + "&iDate=" + iDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&country=" + country + "&notes=" + notes + "&cid=" + cid;

        selectedServices.clear();
        selectedInventory.clear();
        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM salesOrderitems where SalesOrderCheck = '" + iRecNum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                if (results.getString(9) != null) {
                    query = "SELECT * FROM services where pk = '" + results.getString(9) + "'";
                    statement = connection.createStatement();
                    ResultSet services = statement.executeQuery(query);

                    while (services.next()) {
                        selectedServices.add(new ServiceData(services.getString(1), services.getString(2), services.getString(3), results.getBigDecimal(5), results.getInt(6), results.getBigDecimal(5)));
                    }
                }

                if (results.getString(8) != null) {
                    query = "SELECT * FROM inventory where INVENTORY_ID = '" + results.getString(8) + "'";
                    statement = connection.createStatement();
                    ResultSet inventory = statement.executeQuery(query);

                    int x = 0;
                    while (inventory.next()) {
                        selectedInventory.add(new InventoryData(inventory.getString(1), inventory.getString(4), inventory.getString(8), inventory.getInt(14), "", results.getBigDecimal(6), inventory.getBigDecimal(12), inventory.getInt(11), inventory.getBigDecimal(10), inventory.getInt(2), inventory.getString(9), inventory.getString(5),1, new BigDecimal("0"), inventory.getInt(3), "", results.getString(9),0));
                        
                        x++;
                    }
                }
//public InventoryData(String iRecNum, String code, String description, int supplierId, String supplier, BigDecimal cost, BigDecimal sellingPrice, int reorderLevel, BigDecimal markup, int atHand, String size, String color, int quantity, BigDecimal totCost, int clientId, String client) {
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        count = selectedServices.size();

        for (int x = 0; x < count; x++) {
            selectedServices.iterator().next();
            cost = selectedServices.get(x).getCost();
            quantity = selectedServices.get(x).getQty();
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            selectedServices.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
        }

        invCost = new BigDecimal("0");
        count = selectedInventory.size();

        for (int x = 0; x < count; x++) {
            selectedInventory.iterator().next();
            invCost = selectedInventory.get(x).getSellingPrice();
            quantity = selectedInventory.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
            selectedInventory.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
        }

        this.totCost = totInvCost.add(totServCost);

        UserProcessing up = new UserProcessing();
        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }        
System.out.println("0000011111");
        /*request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/sales-order-complete.xhtml?" + param);
        }*/

        return "/accounting/receivable/salesorder/sales-order-complete.xhtml";

    }

    public void viewSalesOrder() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/salesOrder/view-salesOrder.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SalesOrderData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
