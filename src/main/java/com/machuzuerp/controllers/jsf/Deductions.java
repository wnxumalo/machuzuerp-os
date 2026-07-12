/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.DeductionsProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
/*
 * @author Administrator
 */
@SessionScoped
@Named
public class Deductions implements Serializable {
 
    private String recnum;
       
    private String employeeNumber = "";   
    private String description = "";
    private double amount = 0;   
    
    private String ret = "";
    
    DeductionsProcessing dp = new DeductionsProcessing();  
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    HttpServletRequest request;
    
    public Deductions() {                               
        
    }
    
    public void onDateSelect(SelectEvent event) {
     //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");               
        //setJDate(format.format(event.getObject()));
       // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    public void setRecNum(String recNum) {
        this.recnum = recNum;
    }

    public String getRecNum() {
        return recnum;
    }
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }   
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }              
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }                                     
   
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        
  
        this.recnum = params.get("recnum");   
        
        try {
            dp.employeeNumber = this.employeeNumber;
            dp.description = this.description;
            dp.amount = this.amount;                        

        } catch (NullPointerException npe) {}
    }

    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recNum"); 
        
        try {
            this.recnum = params.get("empRecNum");   
        } catch(NullPointerException npe) {}
        
        this.employeeNumber = params.get("employeeNumber");           
        this.description = params.get("description");           
        try {
            this.amount = Double.parseDouble(params.get("amount"));
        } catch (NullPointerException npe) {
            this.amount = 0;
        }

       /* ep.recNum= params.get("recnum");
        ep.customerType = this.idNumber;        
        ep.customerStat = this.taxNumber;
        ep.customerName = params.get("cname");
        ep.customerSurname = this.cSurname;
        ep.customerGender = this.cGender;
        ep.customerTelephone = this.cTelephone;
        ep.customerCellphone = this.cCellphone;
        ep.customerFax = this.cFax;
        ep.customerPostalAddress = this.cPostalAddress;
        ep.customerPhysicalAddress = this.cPhysicalAddress;
        ep.customerCountry = this.cCountry;
        ep.customerJDate = ""+sqlDate(this.cJDate);*/
    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }

    public void saveDeduction() {
        getParams();  

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        

        try {

            dp.recNum = this.recnum;
            dp.editDeduction(request.getSession().getAttribute("uk").toString(), "", "", "");            
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Deduction Added Successfully."));
            
        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Deduction Not Added. " + npe));
        }
        
    }
    
    //public String saveEditDeductions(String recnum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry,String cJDate) throws SQLException {
    public String saveEditDeductions() throws SQLException {

        getParams(); 
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        

        try {

            dp.editDeduction(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/hr/saved";
        } catch (NullPointerException npe) {
            ret = "/hr/notsaved";
        }

        return ret;
    }

    public String mainEditDeductions() {
        getParams();        
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        

        try {
            dp.editDeduction(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/employees/saved";
        } catch (NullPointerException npe) {
            ret = "/employees/notsaved";
        }
        
        return ret;        
    }
    
    public String editEmployee() {        
        
        return "/hr/employees/edit-employee";        
    }
       
    public String editEmployee_1(String recnum, String idNumber, String taxNumber, String empDate, String employeeNumber, String empName, String surname, String level, String gender, String telephone, String cellphone, String email, String postalAddress, String physicalAddress, float salary, String salaryInterval, String status) throws SQLException, ParseException {        
        
        this.recnum = recnum;
        this.employeeNumber = employeeNumber;           

        return "edit-employee.xhtml";        
    }    

    public void getDeductions() throws SQLException {        
        dp.getDeductions(recnum);                      
    
        employeeNumber = dp.employeeNumber;
        description = dp.description;
        amount = dp.amount;       

    }
    
    public String deleteDeductions() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
            dp.editDeduction(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/hr/saved";
        } catch (NullPointerException npe) {
            ret = "/hr/notsaved";
        }

        return ret;
    }  
        
    public void showError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Incorrect username and/or password."));
    }
    
}
