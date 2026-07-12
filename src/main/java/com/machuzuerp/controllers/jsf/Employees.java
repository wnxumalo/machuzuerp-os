/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.TrainingsData;
import com.machuzuerp.controllers.datatableprocessing.jsf.BenefitsData;
import com.machuzuerp.controllers.datatableprocessing.jsf.DeductionsData;
import com.machuzuerp.jdbc.TrainingsProcessing;
import com.machuzuerp.jdbc.BenefitsProcessing;
import com.machuzuerp.jdbc.DeductionsProcessing;
import com.machuzuerp.entities.dto.EmployeeDTO;
import com.machuzuerp.jdbc.EmployeesProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Employees implements Serializable {

    private String recnum;
    
    private String idNumber = "";
    private String taxNumber = "";
    private String employeeNumber = "";
    private java.util.Date employmentDate = null;
    private String empName = "";
    private String surname = "";
    private String level = "";
    private String gender = "";
    private String telephone = "";
    private String cellphone = "";
    private String email = "";
    private String postalAddress = "";
    private String physicalAddress = "";
    private String bankName = "";
    private String accountName = "";
    private String accountNumber = "";
    private String branchCode = "";
    private float salary = 0;
    private String salaryInterval = "";
    private String status = "";
    private String position = "";
    private String approver = "";
    private String department = "";
    private String password = "";

    private String ret = "";

    private String msgSubject = "";
    private String msg = "";

    private String tranHisType = "";

    EmployeesProcessing ep = new EmployeesProcessing();  
    BenefitsProcessing bp = new BenefitsProcessing();
    DeductionsProcessing dp = new DeductionsProcessing();
    TrainingsProcessing tp = new TrainingsProcessing();
    private List<BenefitsData> benefits = new ArrayList<BenefitsData>();
    private List<DeductionsData> deductions = new ArrayList<DeductionsData>();
    private List<TrainingsData> trainings = new ArrayList<TrainingsData>();
    
    private Employees selectedEmployee;
    private EmployeeDTO selectedEmployeeDTO;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
        
    public Employees() {                 
        
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();                

            this.recnum = params.get("recnum");
            this.empName = params.get("empname");
            this.surname = params.get("empsurname");
            this.level = params.get("level");
        } catch (NullPointerException npe){}       

    }
    
    public void onDateSelect(SelectEvent event) {
     //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");               
        //setJDate(format.format(event.getObject()));
       // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
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
    
    public java.util.Date getEmploymentDate() {
        return employmentDate;
    }
    
    public void setEmploymentDate(java.util.Date employmentDate) {
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
    
    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
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
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setSalaryInterval(String salaryInterval) {
        this.salaryInterval = salaryInterval;
    }

    public String getSalaryInterval() {
        return salaryInterval;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
    
    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApprover() {
        return approver;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }    
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public EmployeeDTO getSelectedEmployeeDTO() {
        return selectedEmployeeDTO;
    }

    public void setSelectedEmployeeDTO(EmployeeDTO selectedEmployeeDTO) {
        this.selectedEmployeeDTO = selectedEmployeeDTO;
    }
    
    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }
    
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        

        this.recnum = params.get("recnum");   

        try {
            ep.idNumber = this.idNumber;
            ep.taxNumber = this.taxNumber;
            ep.employeeNumber = this.employeeNumber;            
            ep.empName = this.empName;
            ep.surname = this.surname;
            ep.level = this.level;
            ep.gender = this.gender;
            ep.telephone = this.telephone;
            ep.cellphone = this.cellphone;
            ep.email = this.email;
            ep.postalAddress = this.postalAddress;
            ep.physicalAddress = this.physicalAddress;
            ep.bankName = this.bankName;
            ep.branchCode = this.branchCode;
            ep.accountName = this.accountName;
            ep.accountNumber = this.accountNumber;
            ep.salary = this.salary;
            ep.salaryInterval = this.salaryInterval;
            ep.status = this.status;
            ep.position = this.position;
            ep.approver = this.approver;
            ep.supervisorId = Integer.parseInt(this.selectedEmployeeDTO.getId().toString());            
            ep.department = this.department;
            ep.employmentDate =  sdf.format(this.employmentDate);            

        } catch (NullPointerException npe) {}
    }

    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        
        this.idNumber = params.get("idnumber");           
        this.taxNumber = params.get("taxnumber");           
        this.employeeNumber = params.get("employeenumber");           
        try {
            this.employmentDate = sdf.parse(params.get("employmentdate"));
        } catch (ParseException ex) {
            Logger.getLogger(Employees.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.empName = params.get("empname");
        this.surname = params.get("surname");
        this.level = params.get("level");
        this.gender = params.get("gender");
        this.telephone = params.get("telephone");
        this.cellphone = params.get("cellphone");
        this.email = params.get("email");
        this.postalAddress = params.get("postaladdress");
        this.physicalAddress = params.get("physicaladdress");
        this.salary = Float.parseFloat(params.get("salary"));
        this.salaryInterval = params.get("salaryinterval");
        this.status = params.get("status");
        this.position = params.get("position");

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

    public void saveEmployee() {
        System.out.println("2323");
        getParams();          

        try {
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            msg = ep.editEmployees(request.getSession().getAttribute("uk").toString(), "", "", "", selectedEmployeeDTO);            

            if (msg.equals("Exists"))
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "Employee ID Number Already Exists."));
            else 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Employee " + msg + " Successfully."));            
            
        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }
        
       // return ret;
    }
    
    public void saveEditEmployees(Employees supervisor) throws SQLException {

        getParams(); 

        try {

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
//            msg = ep.editEmployees(request.getSession().getAttribute("uk").toString(), "edit", "", recnum, supervisor);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Employee " + msg + " Successfully."));

        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }

    }

    public String mainEditEmployees(Employees supervisor) {
        getParams();            
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        try {
            //ep.editEmployees(request.getSession().getAttribute("uk").toString(), "edit", "", recnum, supervisor);
            ret = "/employees/saved";
        } catch (NullPointerException npe) {
            ret = "/employees/notsaved";
        }
        
        return ret;        
    }
    
    public String editEmployee() {        
        
        return "/hr/employees/edit-employee";        
    }
       
    public String editEmployee_1(String recnum, String idNumber, String taxNumber, String empDate, String employeeNumber, String empName, String surname, String level, String approver, String gender, String telephone, String cellphone, String email, String postalAddress, String physicalAddress, float salary, String salaryInterval, String status, String position, String department) throws SQLException, ParseException {        

        this.recnum = recnum;
        this.idNumber = idNumber;           
        this.taxNumber = taxNumber;           
        //this.employmentDate = empDate;
        this.employeeNumber = employeeNumber;                   
        this.empName = empName;
        this.surname = surname;
        this.level = level;
        this.approver = approver;
        this.gender = gender;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.email = email;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.salary = salary;
        this.salaryInterval = salaryInterval;
        this.status = status;
        this.position = position;
        this.department = department;
                        
        
        try {
            this.employmentDate = sdf.parse(empDate);            
        } catch (ParseException ex) {
            this.employmentDate = sdf.parse(sdf.format(new java.util.Date()));
        }
        
        return "edit-employee.xhtml";        
    }    

    public void getEmployees() throws SQLException {        
        EmployeesProcessing ep = new EmployeesProcessing();
        ep.getEmployee(recnum); 
            
        idNumber = ep.idNumber;
        taxNumber = ep.taxNumber;
        employeeNumber = ep.employeeNumber;
        empName = ep.empName;
        surname = ep.surname;
        level = ep.level;
        gender = ep.gender;
        telephone = ep.telephone;
        cellphone = ep.cellphone;
        email = ep.email;
        postalAddress = ep.postalAddress;
        physicalAddress = ep.physicalAddress;
        salary = ep.salary;
        salaryInterval = ep.salaryInterval;
        status = ep.status;
        position = ep.position;

    }
    
    public String deleteEmployees() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            ep.editEmployees(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum, null);
            ret = "/hr/saved";
        } catch (NullPointerException npe) {
            ret = "/hr/notsaved";
        }
        
        return ret;
    }   
        
    public void showError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Incorrect username and/or password."));
    }
    
    public void reset() {  
        this.recnum = "";
        this.idNumber = "";
        this.taxNumber = "";
        this.employmentDate = null;
        this.employeeNumber = "";
        this.empName = "";
        this.surname = "";
        this.level = "";
        this.approver = "";
        this.gender = "";
        this.telephone = "";
        this.cellphone = "";
        this.email = "";
        this.postalAddress = "";
        this.physicalAddress = "";
        this.salary = 0;
        this.salaryInterval = "";
        this.status = "";
        this.position = "";
        this.department = "";
                        
        
    } 
    
}
