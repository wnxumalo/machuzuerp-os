/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.CheckInProcessing;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;

@SessionScoped
@Named
public class CheckInAspects implements Serializable {

    Connection connection;
    Statement statement;

    private String recNum;
    
    private String jobId;
    private String checkInId;

    private String RootCause;
    private String ActionRecommendation;
    private String TargetRootCause;
    private String TargetRecommendation;
    private String ResourcesRootCause;
    private String ResourcesRecommendation;
    private String saveMsg;

    private List<CheckInAspects> checkInAspects = new ArrayList<CheckInAspects>();    
    private CheckInAspects selectedCheckInAspects;
    CheckInProcessing cip = new CheckInProcessing();

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;        

    public CheckInAspects() {                
        
        checkInAspects.clear();

        for (int x=0;x<15;x++) {
            
            checkInAspects.add(new CheckInAspects("", "", "", "", "", ""));
            
        }

    }

    public CheckInAspects(String RootCause, String ActionRecommendation, String TargetRootCause, String TargetRecommendation, String ResourcesRootCause, String ResourcesRecommendation) {

        this.RootCause = RootCause;
        this.ActionRecommendation = ActionRecommendation;
        this.TargetRootCause = TargetRootCause;
        this.TargetRecommendation = TargetRecommendation;
        this.ResourcesRootCause = ResourcesRootCause;
        this.ResourcesRecommendation = ResourcesRecommendation;

    }   

    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setCheckInId(String checkInId) {
        this.checkInId = checkInId;
    }

    public String getCheckInId() {
        return checkInId;
    }           

    public void setRootCause(String RootCause) {
        this.RootCause = RootCause;
    }

    public String getRootCause() {
        return RootCause;
    }            

    public void setActionRecommendation(String ActionRecommendation) {
        this.ActionRecommendation = ActionRecommendation;
    }

    public String getActionRecommendation() {
        return ActionRecommendation;
    }

    public void setTargetRootCause(String TargetRootCause) {
        this.TargetRootCause = TargetRootCause;
    }

    public String getTargetRootCause() {
        return TargetRootCause;
    }

    public void setTargetRecommendation(String TargetRecommendation) {
        this.TargetRecommendation = TargetRecommendation;
    }

    public String getTargetRecommendation() {
        return TargetRecommendation;
    }   

    public void setResourcesRootCause(String ResourcesRootCause) {
        this.ResourcesRootCause = ResourcesRootCause;
    }

    public String getResourcesRootCause() {
        return ResourcesRootCause;
    }   
    
    public void setResourcesRecommendation(String ResourcesRecommendation) {
        this.ResourcesRecommendation = ResourcesRecommendation;
    }

    public String getResourcesRecommendation() {
        return ResourcesRecommendation;
    }
    
    public void setSaveMsg(String saveMsg) {
        this.saveMsg = saveMsg;
    }

    public String getSaveMsg() {
        return saveMsg;
    }

    public List getCheckInAspects() {                         
        return checkInAspects;
    }

    public void setCheckInAspects(List checkInAspects) {
        this.checkInAspects = checkInAspects;
    } 
        
    
    public CheckInAspects getSelectedSelectedCheckInAspects() {
        return selectedCheckInAspects;
    }

    public void setSelectedSelectedCheckInAspects(CheckInAspects selectedCheckInAspects) {
        this.selectedCheckInAspects = selectedCheckInAspects;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.RootCause = ((CheckInAspects) event.getObject()).RootCause;
        this.ActionRecommendation = ((CheckInAspects) event.getObject()).ActionRecommendation;
        this.TargetRootCause = ((CheckInAspects) event.getObject()).TargetRootCause;
        this.TargetRecommendation = ((CheckInAspects) event.getObject()).TargetRecommendation;
        this.ResourcesRootCause = ((CheckInAspects) event.getObject()).ResourcesRootCause;
        this.ResourcesRecommendation = ((CheckInAspects) event.getObject()).ResourcesRecommendation;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
        
        String param = "name=" + request.getSession().getAttribute("username").toString() + "&uk=" + request.getSession().getAttribute("uk").toString() + "&recnum=" + this.recNum + "&jobId=" + jobId + "&checkInId=" + checkInId 
                + "&RootCause=" + RootCause + "&ActionRecommendation=" + ActionRecommendation
                + "&TargetRootCause=" + TargetRootCause 
                + "&TargetRecommendation=" + TargetRecommendation + "&ResourcesRootCause=" + ResourcesRootCause
                + "&ResourcesRecommendation=" + ResourcesRecommendation;
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/complete-job.xhtml?" + param);
        }                

    }

    public void viewCustomer() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/customers/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CheckInAspects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        
//        this.jobId = selectedCheckInAspects.jobId;  
        System.out.println(((CheckInAspects) event.getObject()).getActionRecommendation());
                
        
        //FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
       // FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        
    } 

    public String completeJob(String jobId, String checkInId, List<CheckInActions> checkInActions) throws SQLException {

        this.jobId = jobId;   
        this.checkInId = checkInId; 
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            cip.completeJob(request.getSession().getAttribute("uk").toString(), jobId, checkInId, checkInActions, checkInAspects); 
            saveMsg = "Check in saved successfully.";
        } catch (Exception e) {
            saveMsg = e.toString();
        }
        
        return "/jobs/complete-job";
    }

}