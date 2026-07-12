package com.machuzuerp.reports;

import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.controllers.jsf.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class SuppliersReport implements Serializable {

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

    private String byCode;
    int count = 9;
    private String byDescription;

    String supplierRecNum = "";

    private List<SuppliersReport> suppliers = new ArrayList<SuppliersReport>();   
    
    HttpServletRequest request;
    
    String query = "";

    public SuppliersReport() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public SuppliersReport(String sRecNum, String supplierName, String telephone, String cellphone, String fax, String postalAddress, String physicalAddress, String country, String description, String vatNum, String email) {

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

    public List<SuppliersReport> getAllSuppliers() {

        suppliers.clear();

        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM supplier order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {              
                suppliers.add(new SuppliersReport(results.getString(1),results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return suppliers;
    }

    public List<SuppliersReport> getSuppliers() {
        return suppliers;
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

}
