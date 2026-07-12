package com.machuzuerp.entities;

import java.util.Date;

public class Payroll {

    private Integer id;
    private String employeeid;
    private Date paydate;
    private String paidfrom;
    private String name;
    private String surname;
    private String empbankname;
    private String empbankaccname;
    private String empbankaccNum;
    private String totdeductions;
    private String totbenefits;
    private String grosspay;
    private String nettpay;
    private String annualleave;
    private String compasionateleave;
    private String sickleave;
    private String studyleave;
    private String branchcode;    

    public Payroll(Integer id,String employeeid,Date paydate,String paidfrom,String name,String surname,String empbankname,String empbankaccname,String empbankaccNum,
            String totdeductions,String totbenefits,String grosspay,String nettpay,String annualleave,String compasionateleave,String sickleave,String studyleave,
            String branchcode) {

        this.id = id;
        this.employeeid = employeeid;
        this.paydate = paydate;
        this.paidfrom = paidfrom;
        this.name = name;
        this.surname = surname;
        this.empbankname = empbankname;
        this.empbankaccname = empbankaccname;
        this.empbankaccNum = empbankaccNum;
        this.totdeductions = totdeductions;
        this.totbenefits = totbenefits;
        this.grosspay = grosspay;
        this.nettpay = nettpay;
        this.annualleave = annualleave;
        this.compasionateleave = compasionateleave;
        this.sickleave = sickleave;
        this.studyleave = studyleave;
        this.branchcode = branchcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeid;
    }

    public void setEmployeeId(String employeeid) {
        this.employeeid = employeeid;
    }
    
    public Date getPayDate() {
        return paydate;
    }

    public void setPayDate(Date paydate) {
        this.paydate = paydate;
    }
    
    public String getPaidFrom() {
        return paidfrom;
    }

    public void setPaidFrom(String paidfrom) {
        this.paidfrom = paidfrom;
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
    
    public String getEmpBankAccNum() {
        return empbankaccNum;
    }

    public void setEmpBankAccNum(String empbankaccNum) {
        this.empbankaccNum = empbankaccNum;
    }
    
    public String getTotDeductions() {
        return totdeductions;
    }

    public void setTotDeductions(String totdeductions) {
        this.totdeductions = totdeductions;
    }
    
    public String getTotBenefits() {
        return totbenefits;
    }

    public void setTotBenefits(String totbenefits) {
        this.totbenefits = totbenefits;
    }
    
    public String getGrossPay() {
        return grosspay;
    }

    public void setGrossPay(String grosspay) {
        this.grosspay = grosspay;
    }
    
    public String getNettPay() {
        return nettpay;
    }

    public void setNettPay(String nettpay) {
        this.nettpay = nettpay;
    }
    
    public String getAnnualLeave() {
        return annualleave;
    }

    public void setAnnualLeave(String annualleave) {
        this.annualleave = annualleave;
    }
    
    public String getCompasionateLeave() {
        return compasionateleave;
    }

    public void setCompasionateLeave(String compasionateleave) {
        this.compasionateleave = compasionateleave;
    }
    
    public String getSickLeave() {
        return sickleave;
    }

    public void setSickLeave(String sickleave) {
        this.sickleave = sickleave;
    }
    
    public String getStudyLeave() {
        return studyleave;
    }

    public void setStudyLeave(String studyleave) {
        this.studyleave = studyleave;
    }
    
    public String getBranchCode() {
        return branchcode;
    }

    public void setBranchCode(String branchcode) {
        this.branchcode = branchcode;
    }
}