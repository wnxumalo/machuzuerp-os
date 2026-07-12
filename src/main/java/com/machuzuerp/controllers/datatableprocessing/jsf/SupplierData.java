/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class SupplierData implements Serializable {

    /**
     * Creates a new instance of SupplierData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String sRecNum;
    private String supplierName;
    private String telephone;
    private String cellphone;
    private String fax;
    private String postalAddress;
    private String physicalAddress;
    private String country;
    private String description;
    private String vatNum;
    private String email;

    int count = 9;

    String supplierRecNum = "";

    private List<SupplierData> suppliers = new ArrayList<SupplierData>();
    private List<SupplierData> allSuppliers = new ArrayList<SupplierData>();
    private List<SupplierData> funcSuppliers = new ArrayList<SupplierData>();
    private List<SupplierData> filteredSuppliers = new ArrayList<SupplierData>();
    private SupplierData selectedSuppliers;        
    private List<SupplierData> filteredServs;  
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";

    public SupplierData() {
        
    }

    public SupplierData(String sRecNum, String supplierName, String telephone, String cellphone, String fax, String postalAddress, String physicalAddress, String country, String description, String vatNum, String email) {

        this.sRecNum = sRecNum;
        this.supplierName = supplierName;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.fax = fax;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.country = country;
        this.description = description;
        this.vatNum = vatNum;
        this.email = email;
                
    }

    public String searchSupplier() {

        suppliers.clear();
        try {
            connection = Systems.initConnection();

            int i = 0;                
            query = "SELECT * FROM supplier where name like '%" + description + "%'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                supplierRecNum = results.getString(1);
                suppliers.add(new SupplierData(results.getString(1),results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/suppliers/supplier-listing";

    }

    public List<SupplierData> getAllSuppliers() {

        allSuppliers.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM supplier order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                supplierRecNum = results.getString(1);
                suppliers.add(new SupplierData(results.getString(1),results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return allSuppliers;
    }

    

    public void filterSuppliers() {

        filteredSuppliers.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM supplier where description like '%" + description + "%' order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                supplierRecNum = results.getString(1);
                suppliers.add(new SupplierData(results.getString(1),results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11)));
                count++;
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    public void fetchSuppliers() {

        suppliers.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM supplier order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                supplierRecNum = results.getString(1);
                suppliers.add(new SupplierData(results.getString(1),results.getString(11), results.getString(6), "", "", results.getString(17), results.getString(16), results.getString(8), results.getString(11), results.getString(22), results.getString(4)));
                count++;
            }

            //public SupplierData(String sRecNum, String supplierName, String telephone, String cellphone, String fax, String postalAddress, String physicalAddress, String country, String description, String vatNum, String email) {

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    
    public List<SupplierData> getFilteredServs() {
        return filteredServs;
    }
 
    public void setFilteredServs(List<SupplierData> filteredServs) {
        this.filteredServs = filteredServs;
    }

    public List<SupplierData> getFilteredSuppliers() {

        filterSuppliers();

        return filteredSuppliers;
    }

    public List<SupplierData> getSuppliers() {
        fetchSuppliers();

        return suppliers;
    }
    
    public void setSuppliers(List<SupplierData> suppliers) {
        this.suppliers = suppliers;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public String getRecNum() {
        return sRecNum;
    }

    public void setRecNum(String sRecNum) {
        this.sRecNum = sRecNum;
    }

    public String getSupplierName() {
        return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }   
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    } 
        
    public String getCellphone() {
        return cellphone;
    }
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }          
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }  
    
    public String getPostalAddress() {
        return postalAddress;
    }
    
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }  
    
    public String getPhysicalAddress() {
        return physicalAddress;
    }
    
    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }             
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }  
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }        
    
    public String getVatNum() {
        return vatNum;
    }
    
    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }  
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public SupplierData getSelectedSupplier() {
        return selectedSuppliers;
    }

    public void setSelectedSupplier(SupplierData selectedServ) {
        this.selectedSuppliers = selectedServ;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.sRecNum = ((SupplierData) event.getObject()).sRecNum;
        this.supplierName = ((SupplierData) event.getObject()).supplierName;
        this.telephone = ((SupplierData) event.getObject()).telephone;
        this.cellphone = ((SupplierData) event.getObject()).cellphone;  
        this.fax = ((SupplierData) event.getObject()).fax;  
        this.postalAddress = ((SupplierData) event.getObject()).postalAddress;  
        this.physicalAddress = ((SupplierData) event.getObject()).physicalAddress;  
        this.country = ((SupplierData) event.getObject()).country;  
        this.description = ((SupplierData) event.getObject()).description;  
        this.vatNum = ((SupplierData) event.getObject()).vatNum;  
        this.email = ((SupplierData) event.getObject()).email;         

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       
        String param = "?uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&supplierName=" + supplierName + "&telephone=" + telephone + "&recnum=" + sRecNum + "&cellphone=" + cellphone + 
                "&fax=" + fax + "&postalAddress=" + postalAddress + "&physicalAddress=" + physicalAddress + "&country=" + country + 
                "&description=" + description + "&vatNum=" + vatNum + "&email=" + email;
        
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");
               
        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-supplier.xhtml?" + param);
        }      

    }
    
    public void onCellEdit(CellEditEvent event) {
        System.out.println("SERVS11");
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println("SERVS:"+""+oldValue + ", New:" + newValue);
        }                         

    }

    public void viewSupplier() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/suppliers/view-supplier.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/suppliers/view-supplier.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SupplierData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
