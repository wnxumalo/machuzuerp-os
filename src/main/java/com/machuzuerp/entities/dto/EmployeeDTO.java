package com.machuzuerp.entities.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Temporal;

public class EmployeeDTO {

    private Long id;
    private String idNumber;
    private String taxNumber;
    private String employeeNumber;    
    private LocalDate employmentDate;        
    private String name;
    private String surname;
    private String gender;    
    private String telephone;        
    private String position;
    private String cellphone;
    private String email;    
    private String postalAddress;    
    private BigDecimal salary;    
    private String salaryInterval;    
    private String empLevel; 
    private String approver;    
    private String department;    
    private String password;    
    private String empbankaccnum;    
    private String empbankname;    
    private String empbankaccname;    
    private String branchcode;    
    private String userRole;
    private Long organisation;
    private Long supervisorId;    
    private LocalDate creationDate;
    private String status;
    private String notes;   

    public EmployeeDTO(Long id, String idNumber, String taxNumber, String employeeNumber, LocalDate employmentDate, String name, String surname, String gender, String telephone,
                        String position, String cellphone, String email, String postalAddress, BigDecimal salary, String salaryInterval, String empLevel, String approver, String department, 
                        String password, String empbankaccnum, String empbankname, String empbankaccname, String branchcode, String userRole, Long organisation, Long supervisorId, 
                        LocalDate creationDate, String status, String notes) {

        this.id = id;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.employeeNumber = employeeNumber;
        this.employmentDate = employmentDate;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.telephone = telephone;
        this.position = position;
        this.cellphone = cellphone;
        this.email = email;
        this.postalAddress = postalAddress;
        this.salary = salary;
        this.salaryInterval = salaryInterval;
        this.empLevel = empLevel;
        this.approver = approver;
        this.department = department;
        this.password = password;
        this.empbankaccnum = empbankaccnum;
        this.empbankname = empbankname;
        this.empbankaccname = empbankaccname;
        this.branchcode = branchcode;
        this.userRole = userRole;
        this.organisation = organisation;
        this.supervisorId = supervisorId;
        this.creationDate = creationDate;
        this.status = status;
        this.notes = notes;

    }

    public EmployeeDTO(Long id, String idNumber, String name, String surname, String position, String cellphone, BigDecimal salary) {
        this.id = id;
        this.idNumber = idNumber;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.cellphone = cellphone;
        this.salary = salary;
    }
    
    public EmployeeDTO() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }

    public String getCellphone() {
        return cellphone;
    }
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    
    //////
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
    
    public LocalDate getEmploymentDate() {
        return employmentDate;
    }
    
    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
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
    
    public void BigDecimal(BigDecimal salary) {
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
    
    public Long getOrganisation() {
        return organisation;
    }
    
    public void setOrganisation(Long organisation) {
        this.organisation = organisation;
    }
    
    public Long getSupervisorId() {
        return supervisorId;
    }
    
    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
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

}