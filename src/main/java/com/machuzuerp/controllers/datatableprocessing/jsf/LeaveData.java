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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class LeaveData implements Serializable {

    /**
     * Creates a new instance of LeaveData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String recNum;
    private String employeeNumber;   
    private String type;
    private Date startDate;
    private Date endDate;
    private double totDays;
    private String comments;    

    private List<LeaveData> leave = new ArrayList<LeaveData>();   
    private LeaveData selectedLeave;            
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query;

    public LeaveData() {
        
    }
  
    public LeaveData(String recNum, String employeeNumber, String type, Date startDate, Date endDate, double totDays, String comments) {

        this.recNum = recNum;
        this.employeeNumber = employeeNumber;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totDays = totDays;
        this.comments = comments;
        
    }  
    
    public String getLeave(String recnum) {

        try {

            connection = Systems.initConnection();

            int i = 0;               
            query = "SELECT * FROM leave where recnum = '" + recnum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                //empDat = results.getString(1) + ',' + results.getString(3) + ',' + results.getString(4) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(7) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(11);
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "";//empDat;

    }  
       
    public List<LeaveData> getLeave() {

        return leave;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getRecNum() {
        return recNum;
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
    
    public void setTotDays(double totDays) {
        this.totDays = totDays;
    } 
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }          

    public LeaveData getSelectedLeave() {
        return selectedLeave;
    }

    public void setSelectedLeave(LeaveData selectedEmp) {
        this.selectedLeave = selectedEmp;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

      /*  this.recNum = ((LeaveData) event.getObject()).recNum;
        this.idNumber = ((LeaveData) event.getObject()).idNumber;
        this.taxNumber = ((LeaveData) event.getObject()).taxNumber;
        this.employeeNumber = ((LeaveData) event.getObject()).employeeNumber;
        this.employmentDate = ((LeaveData) event.getObject()).employmentDate;      
        this.empName = ((LeaveData) event.getObject()).empName;
        this.surname = ((LeaveData) event.getObject()).surname;    
        this.gender = ((LeaveData) event.getObject()).gender;
        this.telephone = ((LeaveData) event.getObject()).telephone;       
        this.cellphone = ((LeaveData) event.getObject()).cellphone;
        this.email = ((LeaveData) event.getObject()).email;
        this.postalAddress = ((LeaveData) event.getObject()).postalAddress;
        this.physicalAddress = ((LeaveData) event.getObject()).physicalAddress;
        this.salary = ((LeaveData) event.getObject()).salary;
        this.salaryInterval = ((LeaveData) event.getObject()).salaryInterval;
        this.status = ((LeaveData) event.getObject()).status;
System.out.println("ID: "+idNumber);
        try {
            this.name = URLEncoder.encode(this.name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LeaveData.class.getName()).log(Level.SEVERE, null, ex);
        }        

        String param = "uk=" + uk + "&name=" + name + "&recnum=" + recNum + "&idnumber=" + idNumber + "&taxnumber=" + taxNumber + "&employeenumber=" + employeeNumber + "&employmentdate=" + this.employmentDate + "&empname=" + empName + "&surname=" + surname + "&gender=" + gender + "&telephone=" + telephone + "&cellphone=" + cellphone + "&email=" + email + "&postaladdress=" + postalAddress + "&physicaladdress=" + physicalAddress + "&salary=" + salary + "&salaryinterval=" + salaryInterval + "&status=" + status;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-employee.xhtml?" + param);
        }        */   

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

    public void viewService() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/services/view-service.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/services/view-service.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LeaveData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}