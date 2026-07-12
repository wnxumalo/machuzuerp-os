/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.jdbc.LeaveProcessing;
import com.machuzuerp.jdbc.TrainingsProcessing;
import com.machuzuerp.jdbc.DeductionsProcessing;
import com.machuzuerp.jdbc.BenefitsProcessing;
import com.machuzuerp.jdbc.EmployeesProcessing;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.entities.Employee;
import com.machuzuerp.entities.dto.EmployeeDTO;
import com.machuzuerp.entities.Payroll;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@SessionScoped
@Named
public class EmployeesData implements Serializable {

    /**
     * Creates a new instance of EmployeesData
     */
    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;

    String query = "";
    boolean proceed = false;

    private String name;
    private String uk;

    private String recNum;

    private String idNumber = "";
    private String taxNumber = "";
    private String employeeNumber = "";
    private String employmentDate = "";
    private String empName = "";
    private String surname = "";
    private String level = "";
    private String gender = "";
    private String telephone = "";
    private String cellphone = "";
    private String email = "";
    private String postalAddress = "";
    private String physicalAddress = "";
    private float salary = 0;
    private String salaryInterval = "";
    private String status = "";
    private String position = "";
    private String approver = "";
    private String department = "";

    private String byIDNumber;
    int count = 9;
    private String byName;
    private String byempSurname;

    String empDat = "";
    
    private Date payDate;
    private Date payDateFrom;
    private Date payDateTo;

    private double totalBenefits;
    private double totalDeductions;
    private double totalPackage;

    private List<EmployeesData> employees = new ArrayList<EmployeesData>();
    private List<EmployeesData> allEmployees = new ArrayList<EmployeesData>();
    private List<EmployeesData> funcEmployees = new ArrayList<EmployeesData>();
    private List<EmployeesData> filteredEmployees = new ArrayList<EmployeesData>();
    private EmployeesData selectedEmployee;
    private List<EmployeesData> filteredEmps;
    private List<BenefitsData> benefits = new ArrayList<BenefitsData>();
    private List<DeductionsData> deductions = new ArrayList<DeductionsData>();
    private List<TrainingsData> trainings = new ArrayList<TrainingsData>();
    private List<LeaveData> leave = new ArrayList<LeaveData>();
    private List<Payroll> payrollList = new ArrayList<Payroll>();
    private Payroll selectedPayroll;
    
    private String selectedAccount;

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
    
    // Payroll
    private int EmployeeID;
    private Date PayDate;
    private String PaidFrom;
    private String Name;
    private String empSurname;
    private String EmpBankName;
    private String EmpBankAccName;
    private String EmpBankAccNum;
    private float TotDeductions;
    private float TotBenefits;
    private float GrossPay;
    private float NettPay;
    private float AnnualLeave;
    private float CompasionateLeave;
    private float SickLeave;
    private float StudyLeave;
    private String branchcode;
    
    SimpleDateFormat sppf = new SimpleDateFormat("yyyy-MM");    
    
    public EmployeesData() {
    }

    public EmployeesData(String recNum, String idNumber, String taxNumber, String employeeNumber, String employmentDate, String empName,
            String empSurname, String position, String gender, String telephone, String cellphone, String email, String postalAddress, String physicalAddress,
            float salary, String salaryInterval, String level, String status, String approver, String department) {

        this.recNum = recNum;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.employeeNumber = employeeNumber;
        this.employmentDate = employmentDate;
        this.empName = empName;
        this.empSurname = empSurname;
        this.position = position;
        this.gender = gender;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.email = email;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.salary = salary;
        this.salaryInterval = salaryInterval;
        this.level = level;
        this.status = status;
        this.approver = approver;
        this.department = department;
    }

    public String searchEmployee() {
      //  request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  //      getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());
//
        employees.clear();
        try {

            connection = Systems.initConnection();

            int i = 0;
            if (!idNumber.equals("")) {
                System.out.println("000: " + idNumber);
                query = "SELECT * FROM employees where idnumber = '" + idNumber + "'";
            } else if (!empName.equals("")) {
                query = "SELECT * FROM employees where empname = '" + empName + "'";
                System.out.println("111: " + empName);
            } else if (!empName.equals("") & (!surname.equals(""))) {
                query = "SELECT * FROM employees where empname like '%" + empName + "%' and surname like '%" + empSurname + "%'";
                System.out.println("333: " + empName + ":" + empSurname);
            }

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                employees.add(new EmployeesData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(21), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14), Float.parseFloat(results.getString(15)), results.getString(16), results.getString(19), results.getString(17), results.getString(22), results.getString(23)));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return "/hr/employees/employee-listing";

    }

    public void runPayroll(String selectedAccount) {
       
        employees.clear();
        try {            

            results = ep.getPayroll(payDate);
            
            if (sppf.format(new java.util.Date()).equals(sppf.format(payDate))) {
            
                if (!results.next()) {                            

                    connection = Systems.initConnection();

                    int i = 0;
                    query = "SELECT * FROM employees";

                    statement = connection.createStatement();
                    results = statement.executeQuery(query);

                    proceed = true;
                    while (results.next()) {                

                        totalBenefits = 0;
                        ResultSet bens = bp.getBenefits(recNum);
                        benefits.clear();
                        while (bens.next()) {
                            benefits.add(new BenefitsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
                            totalBenefits = totalBenefits + bens.getDouble(4);
                        }

                        totalDeductions = 0;
                        bens = dp.getDeductions(recNum);
                        deductions.clear();
                        while (bens.next()) {
                            deductions.add(new DeductionsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
                            totalDeductions = totalDeductions + bens.getDouble(4);
                        }

                        totalPackage = (results.getDouble(15) + totalBenefits) - totalDeductions;

                        ep.insertPayroll(selectedAccount, payDate, totalBenefits, totalDeductions, totalPackage, results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), 
                                results.getString(8), results.getString(21), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13),
                                results.getString(14), results.getString(15), results.getString(16), results.getString(17), results.getString(19), results.getString(22), results.getString(23),
                                results.getString(25), results.getString(26), results.getString(27), results.getString(20));
                    }

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Payroll Run Successfully."));

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unsuccessfull", "Payroll Already Run For Month."));
                }
                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Month", "Cannot Process For Non Current Month."));
            }

        } catch (NumberFormatException | SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    public void filterPayroll() {

        employees.clear();
        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM payroll where paydate between '" + sdf.format(payDate) + "' and '" + sdf.format(payDateTo) + "'";

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {

                payrollList.add(new Payroll(results.getInt(1),results.getString(2),results.getDate(3),results.getString(4),results.getString(5),results.getString(6),
                        results.getString(7),results.getString(8),results.getString(9),results.getString(10),results.getString(11),results.getString(12),results.getString(13),
                        results.getString(14),results.getString(15),results.getString(16),results.getString(17),results.getString(18)));

            }

        } catch (NumberFormatException | SQLException sqle) {
            sqle.printStackTrace();
        }

    }
    
    public void filterEmployeePayroll() {

        employees.clear();
        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM payroll where paydate between '" + sdf.format(payDate) + "' and '" + sdf.format(payDateTo) + "'";

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {

                payrollList.add(new Payroll(results.getInt(1),results.getString(2),results.getDate(3),results.getString(4),results.getString(5),results.getString(6),
                        results.getString(7),results.getString(8),results.getString(9),results.getString(10),results.getString(11),results.getString(12),results.getString(13),
                        results.getString(14),results.getString(15),results.getString(16),results.getString(17),results.getString(18)));

            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Payroll Run Successfully."));

        } catch (NumberFormatException | SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    public void filterSingleEmployeePayroll() {

        payrollList.clear();
        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM payroll where paydate between '" + sdf.format(payDateFrom) + "' and '" + sdf.format(payDateTo) + "' and EmployeeID = '" + this.idNumber + "'";

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {

                payrollList.add(new Payroll(results.getInt(1),results.getString(2),results.getDate(3),results.getString(4),results.getString(5),results.getString(6),
                        results.getString(7),results.getString(8),results.getString(9),results.getString(10),results.getString(11),results.getString(12),results.getString(13),
                        results.getString(14),results.getString(15),results.getString(16),results.getString(17),results.getString(18)));

            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Payroll Run Successfully."));

        } catch (NumberFormatException | SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    public String getEmployee(String recnum) {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM employees where recnum = '" + recnum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                empDat = results.getString(1) + ',' + results.getString(3) + ',' + results.getString(4) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(7) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(11);
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return empDat;

    }

    public List<EmployeesData> getAllEmployees() {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        allEmployees.clear();
        try {

            connection = Systems.initConnection();

            query = "SELECT * FROM employees order by empname, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                allEmployees.add(new EmployeesData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(21), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14), Float.parseFloat(results.getString(15)), results.getString(16), results.getString(19), results.getString(17), results.getString(22), results.getString(23)));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return allEmployees;
    }

    public String ViewPayslips(HttpSession session, String recnum) {

        this.recNum = recnum;                

        return "/hr/employees/employee-pay";
    }

    /*public String filterEmployees() {

        getParams();       

        try {

            String query = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();  
            
            boolean done = false;
            
            try {
                byName.equals("");
                byempSurname.equals("");
                
                if (byVatNum.equals("")) {
                    throw new NullPointerException();
                }

                done = true; 
                System.out.println("ONE:"+byVatNum);
                query = "SELECT * FROM clients where name like '%" + byName + "%' and surname like '%" + byempSurname + "%'  and vatnum like '%" + byVatNum + "%' order by surname, name";            
            } catch (NullPointerException npe5) {
                
                try {
                    byName.equals("");
                    byempSurname.equals(""); 
                    
                    if (byempSurname.equals("")) {
                        throw new NullPointerException();
                    }

                    if (done == false) {
                        done = true;
    
                        query = "SELECT * FROM clients where name like '%" + byName + "%' and surname like '%" + byempSurname + "%' order by surname, name";            
                    }
                } catch (Exception npe2) {
                
                    try {
                        byName.equals("");

                        if (byName.equals("")) {
                            throw new NullPointerException();
                        }

                        if (done == false) {
                            done = true;

                            query = "SELECT * FROM clients where name like '%" + byName + "%' order by surname, name";            
                        }
                     } catch (Exception npe3) {}
                
                }
            
            
            }                                                                    

            filteredCustomers.clear();

            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            int count = 0;
            proceed = true;
            while (results.next()) {                
                proceed = false;
                clientRecNum = results.getString(1);              
                filteredCustomers.add(new CustomerData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(18), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14)));                
                //setPicPath(clientRecNum);
                count++;
            }

           // connection.close();
            this.count = count;
            
           

        } catch (Exception sqle) {
            System.out.println(sqle);
        }               
        
                
        return "/customers/customer-detailed-list.xhtml";

    }*/
        
    public String getIDNumber() {
        return idNumber;
    }

    public void setIDNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }
    
    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
    
    public Date getPayDateFrom() {
        return payDateFrom;
    }

    public void setPayDateFrom(Date payDateFrom) {
        this.payDateFrom = payDateFrom;
    }
    
    public String getPaidFrom() {
        return PaidFrom;
    }

    public void setPaidFrom(String PaidFrom) {
        this.PaidFrom = PaidFrom;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    public String getEmpSurname() {
        return empSurname;
    }

    public void setEmpSurname(String empSurname) {
        this.empSurname = empSurname;
    }
    
    public String getEmpBankName() {
        return EmpBankName;
    }

    public void setEmpBankName(String EmpBankName) {
        this.EmpBankName = EmpBankName;
    }
    
    public String getEmpBankAccName() {
        return EmpBankAccName;
    }

    public void setEmpBankAccName(String EmpBankAccName) {
        this.EmpBankAccName = EmpBankAccName;
    }

    public String getEmpBankAccNum() {
        return EmpBankAccNum;
    }

    public void setEmpBankAccNum(String EmpBankAccNum) {
        this.EmpBankAccNum = EmpBankAccNum;
    }
    
    public float getTotDeductions() {
        return TotDeductions;
    }

    public void setTotDeductions(float TotDeductions) {
        this.TotDeductions = TotDeductions;
    }
    
    public float getTotBenefits() {
        return TotBenefits;
    }

    public void setTotBenefits(float TotBenefits) {
        this.TotBenefits = TotBenefits;
    }
    
    public float getGrossPay() {
        return GrossPay;
    }

    public void setGrossPay(float GrossPay) {
        this.GrossPay = GrossPay;
    }
    
    public float getNettPay() {
        return NettPay;
    }

    public void setNettPay(float NettPay) {
        this.NettPay = NettPay;
    }
    
    public float getAnnualLeave() {
        return AnnualLeave;
    }

    public void setAnnualLeave(float AnnualLeave) {
        this.AnnualLeave = AnnualLeave;
    }
    
    public float getCompasionateLeave() {
        return CompasionateLeave;
    }

    public void setCompasionateLeave(float CompasionateLeave) {
        this.CompasionateLeave = CompasionateLeave;
    }
    
    public float getSickLeave() {
        return SickLeave;
    }

    public void setSickLeave(float SickLeave) {
        this.SickLeave = SickLeave;
    }
    
    public float getStudyLeave() {
        return StudyLeave;
    }

    public void setStudyLeave(float StudyLeave) {
        this.StudyLeave = StudyLeave;
    }
    
    public String getBranchCode() {
        return branchcode;
    }

    public void setBranchCode(String branchcode) {
        this.branchcode = branchcode;
    }    
    
    public List<EmployeesData> getFilteredServs() {
        return filteredEmps;
    }

    public void setBenefits(List<BenefitsData> benefits) {
        this.benefits = benefits;
    }

    public List<BenefitsData> getBenefits() {
        return benefits;
    }

    public void setLeave(List<LeaveData> leave) {
        this.leave = leave;
    }

    public List<LeaveData> getLeave() {
        return leave;
    }

    public void setDeductions(List<DeductionsData> deductions) {
        this.deductions = deductions;
    }

    public List<DeductionsData> getDeductions() {
        return deductions;
    }

    public void setTrainings(List<TrainingsData> trainings) {
        this.trainings = trainings;
    }

    public List<TrainingsData> getTrainings() {
        return trainings;
    }

    public List<EmployeesData> getEmployees() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());
        } catch (Exception e) {}

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

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
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

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
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

    public void setSalaryInterval(String salaryInterval) {
        this.salaryInterval = salaryInterval;
    }

    public String getSalaryInterval() {
        return salaryInterval;
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

    public void getParams(String username, String uk) {
        this.name = username;
        this.uk = uk;
    }

    public List<EmployeesData> getSelectedEmployees() {
        return employees;
    }

    public void setSelectedEmployees(List<EmployeesData> employees) {
        this.employees = employees;
    }

    public EmployeesData getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(EmployeesData selectedEmp) {
        this.selectedEmployee = selectedEmp;
    }

    public void onRowSelect(SelectEvent event) throws IOException, SQLException {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        this.recNum = ((EmployeesData) event.getObject()).recNum;
        this.idNumber = ((EmployeesData) event.getObject()).idNumber;
        this.taxNumber = ((EmployeesData) event.getObject()).taxNumber;
        this.employeeNumber = ((EmployeesData) event.getObject()).employeeNumber;
        this.employmentDate = ((EmployeesData) event.getObject()).employmentDate;
        this.empName = ((EmployeesData) event.getObject()).empName;
        this.surname = ((EmployeesData) event.getObject()).surname;
        this.position = ((EmployeesData) event.getObject()).position;
        this.gender = ((EmployeesData) event.getObject()).gender;
        this.telephone = ((EmployeesData) event.getObject()).telephone;
        this.cellphone = ((EmployeesData) event.getObject()).cellphone;
        this.email = ((EmployeesData) event.getObject()).email;
        this.postalAddress = ((EmployeesData) event.getObject()).postalAddress;
        this.physicalAddress = ((EmployeesData) event.getObject()).physicalAddress;
        this.salary = ((EmployeesData) event.getObject()).salary;
        this.salaryInterval = ((EmployeesData) event.getObject()).salaryInterval;
        this.status = ((EmployeesData) event.getObject()).status;
        this.level = ((EmployeesData) event.getObject()).level;
        this.approver = ((EmployeesData) event.getObject()).approver;
        this.department = ((EmployeesData) event.getObject()).department;

        totalBenefits = 0;
        ResultSet bens = bp.getBenefits(recNum);
        benefits.clear();
        while (bens.next()) {
            benefits.add(new BenefitsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
            totalBenefits = totalBenefits + bens.getDouble(4);
        }

        totalDeductions = 0;
        bens = dp.getDeductions(recNum);
        deductions.clear();
        while (bens.next()) {
            deductions.add(new DeductionsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
            totalDeductions = totalDeductions + bens.getDouble(4);
        }

        totalPackage = (salary + totalBenefits) - totalDeductions;

        bens = tp.getTrainings(recNum);
        trainings.clear();
        while (bens.next()) {
            trainings.add(new TrainingsData(bens.getString(1), bens.getString(3), bens.getDate(4), bens.getDate(5), bens.getString(6), bens.getString(7), bens.getString(8), bens.getString(9)));
        }

        bens = lp.getLeave(recNum);
        leave.clear();
        while (bens.next()) {
            leave.add(new LeaveData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDate(4), bens.getDate(5), bens.getDouble(6), bens.getString(7)));
        }

        String param = "uk=" + uk + "&name=" + name + "&recnum=" + recNum + "&idnumber=" + idNumber + "&taxnumber=" + taxNumber + "&employeenumber=" + employeeNumber + "&employmentdate=" + this.employmentDate + "&empname=" + empName + "&surname=" + surname + "&position=" + position + "&gender=" + gender + "&telephone=" + telephone + "&cellphone=" + cellphone + "&email=" + email + "&postaladdress=" + postalAddress
                + "&physicaladdress=" + physicalAddress + "&salary=" + salary + "&salaryinterval=" + salaryInterval + "&status=" + status + "&level=" + level + "&approver=" + approver + "&department=" + department;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-employee.xhtml?" + param);
        }

    }

    public void onPayrollRowSelect(SelectEvent event) throws IOException, SQLException {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        this.recNum = ((Payroll) event.getObject()).getId().toString();
        this.idNumber = ((Payroll) event.getObject()).getEmployeeId();
        this.taxNumber = ((Payroll) event.getObject()).getAnnualLeave();
        this.employeeNumber = ((Payroll) event.getObject()).getBranchCode();
        this.employmentDate = ((Payroll) event.getObject()).getCompasionateLeave();
        this.EmpBankAccName = ((Payroll) event.getObject()).getEmpBankAccName();
        this.GrossPay = Float.parseFloat(((Payroll) event.getObject()).getGrossPay());
        this.NettPay = Float.parseFloat(((Payroll) event.getObject()).getNettPay());
        this.Name = ((Payroll) event.getObject()).getName();
        this.surname = ((Payroll) event.getObject()).getSurname();
        this.PaidFrom = ((Payroll) event.getObject()).getPaidFrom();
        
        try {
            this.SickLeave = Float.parseFloat(((Payroll) event.getObject()).getSickLeave());
        } catch(NullPointerException npe) {}
        
        try {
            this.StudyLeave = Float.parseFloat(((Payroll) event.getObject()).getStudyLeave());
        } catch(NullPointerException npe) {}
        
        try {
            this.totalBenefits = Float.parseFloat(((Payroll) event.getObject()).getTotBenefits());
        } catch(NullPointerException npe) {}
        
        try {
            this.totalDeductions = Float.parseFloat(((Payroll) event.getObject()).getTotDeductions());
        } catch(NullPointerException npe) {}
        
        try {
            this.name = URLEncoder.encode(this.name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmployeesData.class.getName()).log(Level.SEVERE, null, ex);
        }

        totalBenefits = 0;
        ResultSet bens = bp.getBenefits(recNum);
        while (bens.next()) {
            benefits.add(new BenefitsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
            totalBenefits = totalBenefits + bens.getDouble(4);
        }

        totalDeductions = 0;
        bens = dp.getDeductions(recNum);
        while (bens.next()) {
            deductions.add(new DeductionsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
            totalDeductions = totalDeductions + bens.getDouble(4);
        }

        totalPackage = (salary + totalBenefits) - totalDeductions;

        bens = tp.getTrainings(recNum);
        while (bens.next()) {
            trainings.add(new TrainingsData(bens.getString(1), bens.getString(3), bens.getDate(4), bens.getDate(5), bens.getString(6), bens.getString(7), bens.getString(8), bens.getString(9)));
        }

        bens = lp.getLeave(recNum);
        while (bens.next()) {
            leave.add(new LeaveData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDate(4), bens.getDate(5), bens.getDouble(6), bens.getString(7)));
        }

        String param = "uk=" + uk + "&name=" + name + "&recnum=" + recNum + "&idnumber=" + idNumber + "&taxnumber=" + taxNumber + "&employeenumber=" + employeeNumber + "&employmentdate=" + this.employmentDate + "&empname=" + empName + "&surname=" + surname + "&position=" + position + "&gender=" + gender + "&telephone=" + telephone + "&cellphone=" + cellphone + "&email=" + email + "&postaladdress=" + postalAddress
                + "&physicaladdress=" + physicalAddress + "&salary=" + salary + "&salaryinterval=" + salaryInterval + "&status=" + status + "&level=" + level + "&approver=" + approver + "&department=" + department;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-payslip.xhtml?" + param);
        }

    }

    public void deleteBenefit(String uk, HttpSession session) throws SQLException, IOException {

        try {
            bp.editBenefit(uk, "delete", "yes", recNum);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record Deleted Successfully."));

            refreshEmployee(uk);

        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "Record Not Deleted. " + npe));
        }

    }

    public void deleteDeduction(String uk, HttpSession session) throws SQLException, IOException {

        try {
            dp.editDeduction(uk, "delete", "yes", recNum);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record Deleted Successfully."));

            refreshEmployee(uk);

        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "Record Not Deleted. " + npe));
        }

    }

    public void deleteTraining(String uk, HttpSession session) throws SQLException, IOException {

        try {
            tp.editTraining(uk, "delete", "yes", recNum);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record Deleted Successfully."));

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

            ep.getEmployee(recNum);

            refreshEmployee(uk);

        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "Record Not Deleted. " + npe));
        }

    }

    public void deleteLeave(String uk, HttpSession session) throws SQLException, IOException {

        try {
            lp.editLeave(uk, "delete", "yes", recNum);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record Deleted Successfully."));

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

            ep.getEmployee(recNum);

            refreshEmployee(uk);

        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "Record Not Deleted. " + npe));
        }

    }

    /*public void refreshEmployee(String recnum) throws SQLException, IOException, ParseException {
        refreshEmployee();
    }*/

    public void refreshEmployee(String recNum) throws SQLException, IOException {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        ep.getEmployee(recNum);

        totalBenefits = 0;
        benefits.clear();
        ResultSet bens = bp.getBenefits(recNum);
        while (bens.next()) {
            benefits.add(new BenefitsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
            totalBenefits = totalBenefits + bens.getDouble(4);
        }

        totalDeductions = 0;
        deductions.clear();
        bens = dp.getDeductions(recNum);
        while (bens.next()) {
            deductions.add(new DeductionsData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDouble(4)));
            totalDeductions = totalDeductions + bens.getDouble(4);
        }

        totalPackage = (ep.salary + totalBenefits) - totalDeductions;

        trainings.clear();
        bens = tp.getTrainings(recNum);
        while (bens.next()) {
            trainings.add(new TrainingsData(bens.getString(1), bens.getString(3), bens.getDate(4), bens.getDate(5), bens.getString(6), bens.getString(7), bens.getString(8), bens.getString(9)));
        }

        leave.clear();
        bens = lp.getLeave(recNum);
        while (bens.next()) {
            leave.add(new LeaveData(bens.getString(1), bens.getString(2), bens.getString(3), bens.getDate(4), bens.getDate(5), bens.getDouble(6), bens.getString(7)));
        }

        String param = "uk=" + uk + "&name=" + name + "&recnum=" + recNum + "&idnumber=" + ep.idNumber + "&taxnumber=" + ep.taxNumber + "&employeenumber=" + ep.employeeNumber + "&employmentdate=" + ep.employmentDate + "&empname=" + ep.empName + "&surname=" + ep.surname + "&position=" + ep.position + "&gender=" + ep.gender + "&telephone=" + ep.telephone + "&cellphone=" + ep.cellphone + "&email=" + ep.email + "&postaladdress=" + ep.postalAddress
                + "&physicaladdress=" + ep.physicalAddress + "&salary=" + ep.salary + "&salaryinterval=" + ep.salaryInterval + "&status=" + ep.status + "&level=" + ep.level + "&approver=" + ep.approver;

       /* request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-employee.xhtml?" + param);
        }*/

    }

    public String newTraining() {
        return "new-training";
    }

    public String newBenefit() {   
        System.out.println("111");
        return "new-benefit";
    }

    public String newDeduction() {       
        return "new-deduction";
    }

    public String newLeave() {
        return "new-leave";
    }

    public void onCellEdit(CellEditEvent event) {
        System.out.println("SERVS11");
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println("SERVS:" + "" + oldValue + ", New:" + newValue);
        }

    }

    public void viewService() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/services/view-service.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/services/view-service.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EmployeesData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getPayDateTo() {
        return payDateTo;
    }

    public void setPayDateTo(Date payDateTo) {
        this.payDateTo = payDateTo;
    }
    
    public List<Payroll> getPayrollList() {
        return payrollList;
    }

    public void setPayrollList(List<Payroll> payrollList) {
        this.payrollList = payrollList;
    }
    
    public Payroll getSelectedPayroll() {
        return selectedPayroll;
    }

    public void setSelectedPayroll(Payroll selectedPayroll) {
        this.selectedPayroll = selectedPayroll;
    }

}