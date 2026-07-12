
package com.machuzuerp.reports;

import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.datatableprocessing.jsf.LeaveData;
import com.machuzuerp.jdbc.TrainingsProcessing;
import com.machuzuerp.jdbc.BenefitsProcessing;
import com.machuzuerp.jdbc.DeductionsProcessing;
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
public class EmployeesReport implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String recNum;
    private String idNumber;
    private String taxNumber;
    private String employeeNumber;
    private String employmentDate;
    private String empName;
    private String surname;
    private String gender;
    private String telephone;
    private String cellphone;
    private String email;
    private String postalAddress;
    private String physicalAddress;
    private float salary;
    private String position;
    private String status;
    private String level;

    private String byIDNumber;
    int count = 9;
    private String byName;
    private String bySurname;

    String empDat = "";
    
    private double totalBenefits;
    private double totalDeductions;
    private double totalPackage;

    private List<EmployeesReport> employees = new ArrayList<EmployeesReport>();   
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

    public EmployeesReport() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public EmployeesReport(String recNum, String idNumber, String taxNumber, String employeeNumber, String employmentDate, String empName, 
            String surname, String gender, String telephone, String cellphone, String email, String postalAddress, String physicalAddress, 
            float salary, String position, String level, String status) {

        this.recNum = recNum;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.employeeNumber = employeeNumber;
        this.employmentDate = employmentDate;
        this.empName = empName;
        this.surname = surname;
        this.gender = gender;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.email = email;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.salary = salary;
        this.position = position;
        this.level = level;
        this.status = status;
    }
    public List<EmployeesReport> getAllEmployees() {

        getParams();
        employees.clear();
        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM employees order by empname, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {               
                employees.add(new EmployeesReport(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14), Float.parseFloat(results.getString(15)), results.getString(21), results.getString(19), results.getString(17)));                
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
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

    public List<EmployeesReport> getEmployees() {
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
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }   
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphone() {
        return cellphone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }
    
    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }
    
    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getSalary() {
        return salary;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    public void setTotalBenefits(double totalBenefits) {
        this.totalBenefits = totalBenefits;
    }

    public double getTotalBenefits() {
        return totalBenefits;
    }
    
    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }
    
    public void setTotalPackage(double totalPackage) {
        this.totalPackage = totalPackage;
    }

    public double getTotalPackage() {
        return totalPackage;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
  
        try {
            this.recNum = params.get("recnum");
        } catch (NullPointerException npe) {}        
    }

}
