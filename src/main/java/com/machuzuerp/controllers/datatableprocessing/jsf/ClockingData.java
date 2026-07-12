/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.jdbc.ClockingProcessing;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class ClockingData implements Serializable {

    /**
     * Creates a new instance of ClockingData
     */
    
    Connection connection;
    ResultSet results;
    Statement statement;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-yyyy");

    private String recnum;
    
    private String supervisorId;
    private String EmployeeId;
    private Date ExitDate;
    private Date ExitTime;
    private Date ReturnDate;
    private Date ReturnTime;
    private String WorkDescription;
    private String Outputs;
    private String JobStatus;
    private String Comments;
    private String Approved;

    private Date DateFrom;
    private Date DateTo;

    private String ret;
    
    private String msgSubject;
    private String msg;
    
    ClockingProcessing cp = new ClockingProcessing();
    
    protected List<ClockingData> missions = new ArrayList<ClockingData>();
    private ClockingData selectedMission;
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;    
    
    String query = "";
    
    public ClockingData() {
    }
    
    public ClockingData(String recnum, String supervisorId, String EmployeeId, Date ExitDate, Date ExitTime, Date ReturnDate, Date ReturnTime, String WorkDescription, String Outputs, String JobStatus, String Comments, String Approved) {
        
        this.recnum = recnum;
        this.EmployeeId = EmployeeId;
        this.supervisorId = supervisorId;
        this.ExitDate = ExitDate;
        this.ExitTime = ExitTime;
        this.ReturnDate = ReturnDate;
        this.ReturnTime = ReturnTime;
        this.WorkDescription = WorkDescription;
        this.Outputs = Outputs;
        this.JobStatus = JobStatus;
        this.Comments = Comments;
        this.Approved = Approved;

    }  
    
    public String getRecNum() {
        return recnum;
    }
    
    public void setRecNum(String recnum) {
        this.recnum = recnum;
    }  
    
    public String getSupervisorId() {
        return supervisorId;
    }
    
    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }   
    
    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String EmployeeId) {
        this.EmployeeId = EmployeeId;
    }   

    public Date getExitDate() {
        return ExitDate;
    }
    
    public void setExitDate(Date ExitDate) {
        this.ExitDate = ExitDate;
    }   
    
    public Date getExitTime() {
        return ExitTime;
    }
    
    public void setExitTime(Date ExitTime) {
        this.ExitTime = ExitTime;
    }    
    
    public void setReturnDate(Date ReturnDate) {
        this.ReturnDate = ReturnDate;
    }

    public Date getReturnDate() {
        return ReturnDate;
    }
    
    public void setReturnTime(Date ReturnTime) {
        this.ReturnTime = ReturnTime;
    }

    public Date getReturnTime() {
        return ReturnTime;
    }   
    
    public void setWorkDescription(String WorkDescription) {
        this.WorkDescription = WorkDescription;
    }

    public String getWorkDescription() {
        return WorkDescription;
    }
    
    public void setOutputs(String Outputs) {
        this.Outputs = Outputs;
    }

    public String getOutputs() {
        return Outputs;
    }
    
    public void setJobStatus(String JobStatus) {
        this.JobStatus = JobStatus;
    }

    public String getJobStatus() {
        return JobStatus;
    }
    
    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getComments() {
        return Comments;
    }
    
    public void setApproved(String Approved) {
        this.Approved = Approved;
    }

    public String getApproved() {
        return Approved;
    }
    
    public void setDateFrom(Date DateFrom) {
        this.DateFrom = DateFrom;
    }

    public Date getDateFrom() {
        return DateFrom;
    }
    
    public void setDateTo(Date DateTo) {
        this.DateTo = DateTo;
    }

    public Date getDateTo() {
        return DateTo;
    }
    
    public void setSelectedMission(ClockingData selectedMission) {
        this.selectedMission = selectedMission;
    }

    public ClockingData getSelectedMission() {
        return selectedMission;
    }
    
    public void setMissions(List<ClockingData> missions) {
        this.missions = missions;
    }

    public List<ClockingData> getMission() {
        return missions;
    }
 
    public void onRowSelect(SelectEvent event) throws IOException {

        this.recnum = ((ClockingData) event.getObject()).recnum;
        this.supervisorId = ((ClockingData) event.getObject()).supervisorId;
        this.EmployeeId = ((ClockingData) event.getObject()).EmployeeId;
        this.ExitTime = ((ClockingData) event.getObject()).ExitTime;
        this.ExitDate = ((ClockingData) event.getObject()).ExitDate;
        this.ReturnDate = ((ClockingData) event.getObject()).ReturnDate;
        this.ReturnTime = ((ClockingData) event.getObject()).ReturnTime;
        this.WorkDescription = ((ClockingData) event.getObject()).WorkDescription;
        this.Outputs = ((ClockingData) event.getObject()).Outputs;
        this.JobStatus = ((ClockingData) event.getObject()).JobStatus;
        this.Comments = ((ClockingData) event.getObject()).Comments;
        this.Approved = ((ClockingData) event.getObject()).Approved;     
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String param = "name=" + request.getSession().getAttribute("username").toString() + "&uk=" + request.getSession().getAttribute("uk").toString() + "&recnum=" + this.recnum + "&supervisorId=" + supervisorId + "&EmployeeId=" + EmployeeId + "&ExitTime=" + ExitTime + "&ExitDate=" + 
                ExitDate + "&ReturnDate=" + ReturnDate + "&ReturnTime=" + ReturnTime + "&WorkDescription=" + WorkDescription + "&Outputs=" + Outputs + "&JobStatus=" + JobStatus + 
                "&Comments=" + Comments + "&Approved=" + Approved;
               
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/complete.xhtml?" + param);
        }                

    }
    
    public String searchMission() {   
        missions.clear();
        try {
            
            connection = Systems.initConnection();

            int i = 0;
            if (!EmployeeId.equals("")) {
                query = "SELECT * FROM accesscontrol where EmployeeId = '" + EmployeeId + "' order by ExitDate";
            } else if ((!EmployeeId.equals("")) & (!DateFrom.equals("")) & (!DateTo.equals(""))) {
                query = "SELECT * FROM accesscontrol where EmployeeId = '" + EmployeeId + "' and ExitDate between '" + DateFrom + "' and '" + DateTo + "' order by ExitDate";
            } else if ((!DateFrom.equals("")) & (!DateTo.equals("")) & (EmployeeId.equals(""))) {
                query = "SELECT * FROM accesscontrol where ExitDate between '" + DateFrom + "' and '" + DateTo + "' order by ExitDate";
            }
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {                    
                missions.add(new ClockingData(results.getString(1), results.getString(2), results.getString(3), results.getDate(4), results.getDate(5), results.getDate(6), results.getDate(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(12)));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/clocking/mission-listing";

    }
    
    public void saveClocking() {

        try {

            cp.recNum = this.recnum;
            cp.SupervisorId = this.supervisorId;
            cp.EmployeeId = this.EmployeeId;
            cp.ExitTime = this.ExitTime;
            cp.ExitDate = this.ExitDate;
            cp.ReturnDate = this.ReturnDate;
            cp.ReturnTime = this.ReturnTime;
            cp.WorkDescription = this.WorkDescription;
            cp.Outputs = this.Outputs;
            cp.JobStatus = this.JobStatus;
            cp.Comments = this.Comments;
            cp.Approved = this.Approved;

            cp.editClocking(request.getSession().getAttribute("uk").toString(), "Edit", "", this.recnum);

            recnum = "";
            supervisorId = "";
            EmployeeId = "";
            ExitDate = null;
            ExitTime = null;
            ReturnDate = null;
            WorkDescription = "";
            Outputs = "";
            Comments = "";

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Clocking", "Saved Successfully."));
            
        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clocking", "Error: " + npe));
        }
                
    }
    

}
