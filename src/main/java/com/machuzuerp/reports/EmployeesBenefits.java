
package com.machuzuerp.reports;

import com.machuzuerp.controllers.datatableprocessing.jsf.LeaveData;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.jdbc.TrainingsProcessing;
import com.machuzuerp.jdbc.LeaveProcessing;
import com.machuzuerp.jdbc.BenefitsProcessing;
import com.machuzuerp.jdbc.DeductionsProcessing;
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
public class EmployeesBenefits implements Serializable {

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
    private String benefit;
    private String contribution;    

    private String byIDNumber;
    int count = 9;
    private String byName;
    private String bySurname;

    private List<EmployeesBenefits> employees = new ArrayList<EmployeesBenefits>();   
    private List<LeaveData> leave = new ArrayList<LeaveData>();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    BenefitsProcessing bp = new BenefitsProcessing();
    DeductionsProcessing dp = new DeductionsProcessing();
    TrainingsProcessing tp = new TrainingsProcessing();
    EmployeesProcessing ep = new EmployeesProcessing();
    LeaveProcessing lp = new LeaveProcessing();
    
    String query = "";

    public EmployeesBenefits() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public EmployeesBenefits(String recNum, String idNumber, String taxNumber, String employeeNumber, String employmentDate, String empName, 
            String surname, String benefit, String contribution) {

        this.recNum = recNum;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.employeeNumber = employeeNumber;
        this.employmentDate = employmentDate;
        this.empName = empName;
        this.surname = surname;
        this.benefit = benefit;
        this.contribution = contribution;
        
    }
    public List<EmployeesBenefits> getAllBenefits() {

        employees.clear();
        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM employees order by empname, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {   
                
                query = "SELECT * FROM employeebenefits where employeenumber = '" + results.getString(1) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {               
                    employees.add(new EmployeesBenefits(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results1.getString(3), results1.getString(4)));                
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

    public List<EmployeesBenefits> getEmployees() {
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
    
    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getBenefit() {
        return benefit;
    }
    
    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public String getContribution() {
        return contribution;
    }

}
