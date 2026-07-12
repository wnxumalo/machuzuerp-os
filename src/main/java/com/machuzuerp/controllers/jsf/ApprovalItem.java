package com.machuzuerp.controllers.jsf;

public class ApprovalItem {

    private int approvalId;
    private int requisitionId;
    private String employeeId;
    private String employeeName;
    private String employeeSurname;
    private String position;
    private String department;
    private Float availablefunds;
    private String approvalStatus;
    private String comments;           
    
    public ApprovalItem(int approvalId,int requisitionId, String employeeId, String employeeName, String employeeSurname, String position, String department, Float availablefunds, String approvalStatus, String comments) {
        this.approvalId = approvalId;
        this.requisitionId = requisitionId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSurname = employeeSurname;
        this.position = position;
        this.department = department;
        this.availablefunds = availablefunds;
        this.approvalStatus = approvalStatus;
        this.comments = comments;
    }
 
    public int getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }
    public int getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Integer requisitionId) {
        this.requisitionId = requisitionId;
    }
    

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }    

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }
    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    public Float getAvailablefunds() {
        return availablefunds;
    }

    public void setAvailablefunds(Float availablefunds) {
        this.availablefunds = availablefunds;
    }
    
    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
        
}