package com.machuzuerp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
@NamedQueries({  
 @NamedQuery(name="Employee.getUpdateEmployeeBenefitTotal", 
 query="SELECT e FROM Employee e WHERE e.id = :id"),
@NamedQuery(name="Employee.getOrganisationEmployees", 
 query="SELECT t FROM Employee t WHERE t.organisation = :id")})   
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EMPLOYEE_ID")
    Long id;

    private String idNumber;
    private String taxNumber;
    private String employeeNumber;    
    private LocalDate employmentDate;    
    private String empName;    
    private String surname;    
    private String gender;    
    private String telephone;    
    private String cellphone;    
    private String email;    
    private String postalAddress;    
    private BigDecimal salary;    
    private String salaryInterval;    
    private String empLevel;    
    private String position;    
    private String approver;    
    private String department;    
    private String password;    
    private String empbankaccnum;    
    private String empbankname;    
    private String empbankaccname;    
    private String branchcode;    
    private String userRole;

    @JoinColumn(name="ORGANISATION_ID", nullable=false)
    private Long organisation;

    @JoinColumn(name="EMPLOYEE", nullable=false)
    private Long supervisorId;
    
    private LocalDate creationDate;
    private String status;
    private String notes;   

    @OneToMany(mappedBy="employeeId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<EmployeeBenefit> employeeBenefits = new ArrayList<EmployeeBenefit>();

    @OneToMany(mappedBy="employeeId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<EmployeeDeduction> employeeDeduction = new ArrayList<EmployeeDeduction>();
    
    @OneToMany(mappedBy="employeeId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<EmployeeLeave> employeeLeave = new ArrayList<EmployeeLeave>();

    @OneToMany(mappedBy="employeeId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<EmployeeTraining> employeeTraining = new ArrayList<EmployeeTraining>();
    
    @OneToMany(mappedBy="employeeId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Administration> employeeAdministration = new ArrayList<Administration>();
    
    @OneToMany(mappedBy="employeeId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<PasswordManagement> passwordManagement = new ArrayList<PasswordManagement>();

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrganisationId() {
        return organisation;
    }

    public void setOrganisationId(Long organisation) {
        this.organisation = organisation;
    }
    
    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
    
    public String getSurname() {
        return surname;
    }
 
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getTelephone() {
        return telephone;
    }
 
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getCellphone() {
        return cellphone;
    }
 
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPostalAddress() {
        return postalAddress;
    }
 
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
    public BigDecimal getSalary() {
        return salary;
    }
 
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    public String getSalaryInterval() {
        return salaryInterval;
    }
 
    public void setSalaryInterval(String salaryInterval) {
        this.salaryInterval = salaryInterval;
    }
    
    public String getEmpLevel() {
        return empLevel;
    }
 
    public void setEmpLevel(String empLevel) {
        this.empLevel = empLevel;
    }
    
    public String getPosition() {
        return position;
    }
 
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getApprover() {
        return approver;
    }
 
    public void setApprover(String approver) {
        this.approver = approver;
    }
    
    public String getDepartment() {
        return department;
    }
 
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmpBankAccNum() {
        return empbankaccnum;
    }
 
    public void setEmpBankAccNum(String empbankaccnum) {
        this.empbankaccnum = empbankaccnum;
    }
    
    public String getEmpBankName() {
        return empbankname;
    }
 
    public void setEmpBankName(String empbankname) {
        this.empbankname = empbankname;
    }
    
    public String getEmpBankAccName() {
        return empbankaccname;
    }
 
    public void setEmpBankAccName(String empbankaccname) {
        this.empbankaccname = empbankaccname;
    }
    
    public String getBranchCode() {
        return branchcode;
    }
 
    public void setBranchCode(String branchcode) {
        this.branchcode = branchcode;
    }    
    
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
    
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }    
    
    public LocalDate getCreationDate() {
        return creationDate;
    }
 
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
 
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<EmployeeBenefit> getEmployeeBenefits() {
        return employeeBenefits;
    }
 
    public void setEmployeeBenefits(List<EmployeeBenefit> employeeBenefits) {
        this.employeeBenefits = employeeBenefits;
    }
    
    public List<EmployeeDeduction> getEmployeeDeduction() {
        return employeeDeduction;
    }
 
    public void setEmployeeDeduction(List<EmployeeDeduction> employeeDeduction) {
        this.employeeDeduction = employeeDeduction;
    }

    public List<EmployeeLeave> getEmployeeLeave() {
        return employeeLeave;
    }
 
    public void setEmployeeLeave(List<EmployeeLeave> employeeLeave) {
        this.employeeLeave = employeeLeave;
    }
    
    public List<EmployeeTraining> getEmployeeTraining() {
        return employeeTraining;
    }
 
    public void setEmployeeTraining(List<EmployeeTraining> employeeTraining) {
        this.employeeTraining = employeeTraining;
    }
    
    public void setEmployeeAdministration(List<Administration> employeeAdministration) {
        this.employeeAdministration = employeeAdministration;
    }

    public List<Administration> getEmployeeAdministration() {
        return employeeAdministration;
    }

    public void setPasswordManagement(List<PasswordManagement> passwordManagement) {
        this.passwordManagement = passwordManagement;
    }

    public List<PasswordManagement> getPasswordManagement() {
        return passwordManagement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Employee[ id=" + id + " ]";
    }
    
}