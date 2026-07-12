/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.SupplierProcessing;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class SupplierControllerOld {

    private String recnum;

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
    
    String cDate;
    String ret = "";
    String msg = "";

    SupplierProcessing sp = new SupplierProcessing();
    
    private List<SupplierControllerOld> suppliers;
    private SupplierControllerOld selectedSupplier;        
    
    HttpServletRequest request;
    
    public SupplierControllerOld() {
        
    }
    
    public SupplierControllerOld(String recnum, String supplierName, String telephone, String cellphone, String fax, String postalAddress, String physicalAddress, String country, String description, String vatNum, String email) {
        this.recnum = recnum;
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
    
    public String getRecNum() {
        return recnum;
    }
    
    public void setRecNum(String recnum) {
        this.recnum = recnum;
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
    
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");                          

        sp.supplierRecNum= params.get("recnum");
        sp.supplierName = this.supplierName;        
        sp.telephone = this.telephone;
        sp.cellphone = this.cellphone;  
        sp.fax = this.fax;  
        sp.postalAddress = this.postalAddress;  
        sp.physicalAddress = this.physicalAddress;  
        sp.country = this.country;  
        sp.description = this.description;  
        sp.vatNum = this.vatNum;  
        sp.email = this.email;  
        
    }

    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");           
        this.supplierName = params.get("supplierName");           
        this.telephone = params.get("telephone");           
        this.cellphone = params.get("cellphone");
        this.fax = params.get("fax");
        this.postalAddress = params.get("postalAddress");
        this.physicalAddress = params.get("physicalAddress");
        this.country = params.get("country");
        this.description = params.get("description");
        this.vatNum = params.get("vatNum");
        this.email = params.get("email");

        sp.supplierRecNum= params.get("recnum");
        sp.supplierName = this.supplierName;        
        sp.telephone = this.telephone;
        sp.cellphone = this.cellphone;  
        sp.fax = this.fax;  
        sp.postalAddress = this.postalAddress;  
        sp.physicalAddress = this.physicalAddress;  
        sp.country = this.country;  
        sp.description = this.description;  
        sp.vatNum = this.vatNum;  
        sp.email = this.email;
    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public void saveSupplier() {
        getParams();          

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            msg = sp.editSupplier(request.getSession().getAttribute("uk").toString(), "", "", "");            
            if (msg.equals("Exists"))
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "Supplier Email or Vat/ID Number Already Exists."));
            else 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Supplier " + msg + " Successfully."));

        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }
    
    }

    public String saveEditSupplier() throws SQLException {

        getParams(); 

        String ret = "";

        try {

            sp.supplierRecNum = recnum;
            sp.supplierName = this.supplierName;        
            sp.telephone = this.telephone;
            sp.cellphone = this.cellphone;  
            sp.fax = this.fax;  
            sp.postalAddress = this.postalAddress;  
            sp.physicalAddress = this.physicalAddress;  
            sp.country = this.country;  
            sp.description = this.description;  
            sp.vatNum = this.vatNum;  
            sp.email = this.email;

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            msg = sp.editSupplier(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
                      
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Supplier " + msg + " Successfully."));

        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }

        return ret;
    }

    public String mainEditSupplier() {
        getParams();
        
        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            sp.editSupplier(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/suppliers/saved";
        } catch (NullPointerException npe) {
            ret = "/suppliers/notsaved";
        }
        
        return ret;        
    }
       
    public String editSupplier_1(String recnum, String supplierName, String telephone, String cellphone, String fax, String postalAddress, String physicalAddress, String country, String description, String vatNum, String email) throws SQLException, ParseException {        

        this.recnum = recnum;           
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
     
        return "/suppliers/edit-supplier";        
    }

    public void getSupplier() throws SQLException {        

        sp.getSupplier(recnum);            
        
        this.recnum = sp.supplierRecNum;        
        this.supplierName = sp.supplierName;  
        this.telephone = sp.telephone;         
        this.cellphone = sp.cellphone;
        this.fax = sp.fax;
        this.postalAddress = sp.postalAddress;
        this.physicalAddress = sp.physicalAddress;
        this.country = sp.country;
        this.description = sp.description;
        this.vatNum = sp.vatNum;
        this.email = sp.email;

    }
    
    public String deleteSupplier() {
         getParams();
         
         request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        try {
            msg = sp.editSupplier(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Supplier " + msg + " Successfully."));
        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }
        
        return ret;
    }    
    
    public List<SupplierControllerOld> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierControllerOld> suppliers) {
        this.suppliers = suppliers;
    } 
    
    public SupplierControllerOld getSupplier(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (SupplierControllerOld supplier : suppliers){
            if (id.equals(supplier.getRecNum())){
                return supplier;
            }
        }
        return null;
    }
    
}
