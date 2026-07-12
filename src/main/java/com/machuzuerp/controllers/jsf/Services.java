/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.ServiceProcessing;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class Services {

    private String recnum;
    
    private String code;
    private String description;
    private float cost;
    String cDate;
    String msg = "";    

    ServiceProcessing sp = new ServiceProcessing();
    
    HttpServletRequest request;
    
    public Services() {        
    }

    public String getRecNum() {
        return recnum;
    }
    
    public void setRecNum(String recnum) {
        this.recnum = recnum;
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
    
    public Float getCost() {
        return cost;
    }
    
    public void setCost(Float cost) {
        this.cost = cost;
    }  
    
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
  
        this.recnum = params.get("recnum");           

        sp.serviceRecNum= params.get("recnum");
        sp.code = this.code;        
        sp.description = this.description;
        sp.cost = this.cost;       
    }

    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");           
        this.code = params.get("code");           
        this.description = params.get("description");           
        this.cost = Float.parseFloat(params.get("cost"));

        sp.serviceRecNum= params.get("recnum");
        sp.code = this.code;        
        sp.description = this.description;
        sp.cost = this.cost;        
    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }
    
    public String saveService() {
        getParams();          
        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            msg = sp.editService(request.getSession().getAttribute("uk").toString(), "", "", "");            
            if (msg.equals("Exists"))
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "Service Code or Description Already Exists."));
            else 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Stakeholder " + msg + " Successfully."));

        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }
        
        return ret;
    }

    public void saveEditService() throws SQLException {

        getParams(); 

        String ret = "";

        try {
           
            sp.serviceRecNum = recnum;
            sp.code = code;
            sp.description = description;
            sp.cost =  cost;
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            msg = sp.editService(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Service " + msg + " Successfully."));
        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }

    }

    public String mainEditService() {
        getParams();
        
        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            sp.editService(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/customers/saved";
        } catch (NullPointerException | SQLException npe) {
            ret = "/customers/notsaved";
        }
        
        return ret;        
    }
       
    public String editService_1(String recnum, String code, String description, float cost) throws SQLException, ParseException {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.recnum = recnum;
        this.code = code;
        this.description = description;
        this.cost = cost;
     
        return "/services/edit-service";        
    }

    public void getService() throws SQLException {        

        sp.getService(recnum);            
        
        code = sp.code;
        description = sp.description;
        cost = sp.cost;

    }
    
    public void deleteService() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            msg = sp.editService(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Service " + msg + " Successfully."));
        } catch (NullPointerException | SQLException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }

    }

}