
package com.machuzuerp.reports;

import com.machuzuerp.controllers.datatableprocessing.jsf.LeaveData;
import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.jdbc.TrainingsProcessing;
import com.machuzuerp.jdbc.LeaveProcessing;
import com.machuzuerp.jdbc.EmployeesProcessing;
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
import java.text.SimpleDateFormat;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class EmployeesLeave implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;

    private String recNum;
    private String idNumber;
    private String taxNumber;
    private String employeeNumber;
    private String employmentDate;
    private String empName;
    private String surname;
    private String leaveType;
    private java.util.Date startDate;    
    private java.util.Date endDate;    
    private String totalDays;    
    private String comments;    

    private String byIDNumber;
    int count = 9;
    private String byName;
    private String bySurname;

    private List<EmployeesLeave> employees = new ArrayList<EmployeesLeave>();   
    private List<LeaveData> leave = new ArrayList<LeaveData>();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    LeaveProcessing bp = new LeaveProcessing();
    LeaveProcessing dp = new LeaveProcessing();
    TrainingsProcessing tp = new TrainingsProcessing();
    EmployeesProcessing ep = new EmployeesProcessing();
    LeaveProcessing lp = new LeaveProcessing();
    
    String query = "";

    public EmployeesLeave() {
    }

    public EmployeesLeave(String idNumber,String employeeNumber, String empName, String surname, String leaveType, java.util.Date startDate, java.util.Date endDate, String totalDays, String comments) {

        this.idNumber = idNumber;
        this.employeeNumber = employeeNumber;
        this.empName = empName;
        this.surname = surname;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.comments = comments;
        
    }
    public List<EmployeesLeave> searchLeave() {

        getParams();
        employees.clear();
        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM employees order by empname, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {   

                query = "SELECT * FROM leaveapplications where employeerecnumber = '" + results.getString(1) + "' and (startdate >= '" + sdf.format(startDate) + "' and enddate <= '" + sdf.format(endDate) + "')";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {               
                    employees.add(new EmployeesLeave(results.getString(1), results.getString(5), results.getString(7), results.getString(8), results1.getString(3), results1.getDate(4), results1.getDate(5), results1.getString(6), results1.getString(7)));
                }
             
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return employees;
    }

    public void setLeave(List<LeaveData> leave) {
        this.leave = leave;
    }

    public List<LeaveData> getLeave() {
        return leave;
    }

    public List<EmployeesLeave> getEmployees() {
        getParams();

        return employees;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getIDNumber() {
        return idNumber;
    }
    
    public void setIDNumber(String idNumber) {
        this.idNumber = idNumber;
    }   
    
    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }              
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }           
    
    public String getEmploymentDate() {
        return employmentDate;
    }
    
    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }    
    
    public String getEmpName() {
        return empName;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }               
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }
 
    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveType() {
        return leaveType;
    }
    
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public java.util.Date getStartDate() {
        return startDate;
    }
    
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }
    
    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getTotalDays() {
        return totalDays;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        try {
            this.recNum = params.get("recnum");
        } catch (NullPointerException npe) {}        
    }

}
