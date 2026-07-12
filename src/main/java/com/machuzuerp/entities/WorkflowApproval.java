package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 *
 * @author Wandile
 */
@Entity
public class WorkflowApproval implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer approvalId;

    @JoinColumn(name="WORKFLOW_ID", nullable=false)
    private Integer workflow;

    @JoinColumn(name="WORKFLOWSTEP_ID", nullable=false)
    private Integer workflowstep;

    private String empId;
    private String empName;
    private String approved;
    private Date approvalDate;
    private String comments;

    public WorkflowApproval() {
    }

    public WorkflowApproval(Integer approvalId, String empId, String approved, Date approvalDate, String comments) {
        
        this.approvalId = approvalId;
        this.empId = empId;
        this.approved = approved;
        this.approvalDate = approvalDate;
        this.comments = comments;
 
    }

    public Integer getId() {
        return approvalId;
    }

    public void setId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }  
    
    public String getEmpId() {
        return empId;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }  
    
    public String getEmpName() {
        return empName;
    }
    
    public String getApproved() {
        return approved;
    }
    
    public void setApproved(String approved) {
        this.approved = approved;
    }  
    
    public Date getApprovalDate() {
        return approvalDate;
    }
   
    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }  
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }  
    
    public Integer getWorkflow() {
        return workflow;
    }
    
    public void setWorkflow(Integer workflow) {
        this.workflow = workflow;
    }
    
    public Integer getWorkflowStep() {
        return workflowstep;
    }
    
    public void setWorkflowStep(Integer workflowstep) {
        this.workflowstep = workflowstep;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (approvalId != null ? approvalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkflowApproval)) {
            return false;
        }
        WorkflowApproval other = (WorkflowApproval) object;
        if ((this.approvalId == null && other.approvalId != null) || (this.approvalId != null && !this.approvalId.equals(other.approvalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.machuzuerp.entities.WorkflowApproval[ approvalId=" + approvalId + " ]";
    }        
    
}
