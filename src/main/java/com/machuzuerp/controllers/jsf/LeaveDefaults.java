/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;

@SessionScoped
@Named
public class LeaveDefaults implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    private String recNum;
    private String description;
    private String type;
    private String days;
    private String comments;
    
    private List<LeaveDefaults> leaveDefaults = new ArrayList<LeaveDefaults>();
    private LeaveDefaults selectedLeaveDefault;        
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    public LeaveDefaults() {                
        
        for (int x=0;x<15;x++) {
            leaveDefaults.add(new LeaveDefaults(null, null, null,null,null));
        }
    }

    public LeaveDefaults(String recNum, String description, String type, String days, String comments) {

        this.recNum = recNum;
        this.description = description;
        this.type = type;
        this.days = days;
        this.comments = comments;

    }

   public void onRowEdit(RowEditEvent event) throws ParseException {
                
        /*DateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());

        //String inputText = "2012-11-17T00:00:00.000-05:00";
        Date date = inputFormat.parse(((CheckInActions) event.getObject()) .getPlannedTargetDate());

        System.out.println("TDATE: "+ date);
        
        this.plannedTargetDate = date;*/
                
        
        //FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
       // FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        
    }    
    public List getLeaveDefaults() {
        return leaveDefaults;
    }
 
    public void setLeaveDefaults(List leaveDefaults) {
        this.leaveDefaults = leaveDefaults;
    }

    public LeaveDefaults getLeaveDefault() {
        return selectedLeaveDefault;
    }
    
    public void setLeaveDefault(LeaveDefaults selectedLeaveDefault) {
        this.selectedLeaveDefault = selectedLeaveDefault;
    }
       
    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }   
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }              
    
    public String getDays() {
        return days;
    }
    
    public void setDays(String days) {
        this.days = days;
    }           
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        try {
            this.recNum = params.get("recnum");
        } catch (NullPointerException npe) {}        
    }

    public void saveDefaults(String recNum) {
        getParams();
        
        boolean error = false;
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        try {

            int count = 0;
 
            connection = Systems.initConnection();

            LocalDateTime convertDate;
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate;

            count = leaveDefaults.size();        
            for (int x = 0; x < count; x++) {
                leaveDefaults.iterator().next();

                try {                
                    if (leaveDefaults.get(x).getDescription() != null) {
                        
                        connection.setAutoCommit(false);
  
                        INSERT_DATA = "INSERT INTO leavedefaults(Description, LeaveType, TotalDays, Comments, uuid) VALUES "
                                + " (?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, leaveDefaults.get(x).getDescription());
                        ps.setString(2, leaveDefaults.get(x).getType());
                        ps.setString(3, leaveDefaults.get(x).getDays());
                        ps.setString(4, leaveDefaults.get(x).getComments());        
                        ps.setString(5, request.getSession().getAttribute("uk").toString());

                        ps.executeUpdate();

                        connection.commit();


                    }                                

                } catch (SQLException sql) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "Leave Defaults Not Saved." + sql));
                    error = true;
                }

            }                        
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "Leave Defaults Not Saved." + e));
            error = true;
        }
        
        if (!error)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Leave Defaults Saved Successfully."));

    }  
        
}