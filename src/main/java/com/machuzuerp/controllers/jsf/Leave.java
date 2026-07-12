/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.LeaveProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class Leave implements Serializable {

    private String recnum;
       
    private String employeeNumber;   
    private String type;
    private Date startDate;
    private Date endDate;
    private long totDays;
    private String comments;   
    private String year;
    
    private String ret = "";
    
    LeaveProcessing lp = new LeaveProcessing();  
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat smf = new SimpleDateFormat("MM");
    
    List years = new ArrayList();
    
    HttpServletRequest request;
        
    public Leave() {                  
        
        try {
            for (int x=(Integer.parseInt(sdf.format(new Date())));x<2030;x++) {
                years.add(x);
            }
        } catch (NumberFormatException nfe) {}

    }

    public void setYears(List years) {
        this.years = years;
    }
    
    public List getYears() {
        return years;
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
           
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }              
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }  
    
    public double getTotDays() {
        return totDays;
    }
    
    public void setTotDays(long totDays) {
        this.totDays = totDays;
    } 
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    } 
    
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    } 

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        

        this.recnum = params.get("recnum");   

        try {
            lp.employeeNumber = this.employeeNumber;
            lp.type = this.type;
            lp.startDate = this.startDate;                        
            lp.endDate = this.endDate;                        
            lp.totDays = this.totDays;                        
            lp.comments = this.comments;
            lp.year = this.year;

        } catch (NullPointerException npe) {}
    }

    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recNum"); 

        try {
            this.recnum = params.get("empRecNum");
        } catch(NullPointerException npe) {}

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public void saveLeave() {
        getParams();

        try {            

            if (lp.checkLeave(recnum, type, year)) {

                lp.recNum = this.recnum;
                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                lp.editLeave(request.getSession().getAttribute("uk").toString(), "", "", "");            

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Leave Added Successfully."));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "You Only Have " + lp.availableDays + " Days " + type + " Leave Left."));
            }

        } catch (SQLException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Leave Not Added. " + npe));
        }
        
    }
    
    //public String saveEditLeave(String recnum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry,String cJDate) throws SQLException {
    public String saveEditLeave() throws SQLException {

        getParams(); 

        try {

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            lp.editLeave(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/hr/saved";
        } catch (NullPointerException npe) {
            ret = "/hr/notsaved";
        }

        return ret;
    }

    public String mainEditLeave() {
        getParams();            

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            lp.editLeave(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/employees/saved";
        } catch (NullPointerException npe) {
            ret = "/employees/notsaved";
        }
        
        return ret;        
    }
    
    public String editEmployee() {        
        
        return "/hr/employees/edit-employee";        
    }
       
    public String editEmployee_1(String recnum, String employeeNumber, String type, Date startDate, Date endDate, long totDays, String comments, String year) throws SQLException, ParseException {        
        
        this.recnum = recnum;
        this.employeeNumber = employeeNumber;           
        this.type = type;           
        this.startDate = startDate; 
        this.endDate = endDate; 
        this.totDays = totDays; 
        this.comments = comments;
        this.year = year;
        
        return "edit-leave.xhtml";        
    }    

    public void getLeave() throws SQLException {        
        lp.getLeave(recnum);                      
    
        employeeNumber = lp.employeeNumber;
        type = lp.type;
        startDate = lp.startDate; 
        endDate = lp.endDate; 
        totDays = lp.totDays;                
        comments = lp.comments;
        year = lp.year;

    }
    
    public String deleteLeave() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            lp.editLeave(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/hr/saved";
        } catch (NullPointerException npe) {
            ret = "/hr/notsaved";
        }
        
        return ret;
    }  
            
}