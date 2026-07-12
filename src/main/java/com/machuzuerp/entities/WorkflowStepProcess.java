package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WorkflowStepProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepProcessId;

    @JoinColumn(name="WORKFLOW_ID", nullable=false)
    private Integer workflow;  

    @JoinColumn(name="WORKFLOWSTEP_ID", nullable=false)
    private Integer workflowstep;

    private Date initiationDate;
    private Date expiryDate;
    private String comments;
    private String formData;
    private Workflow workflowObject;
    private WorkflowStep workflowStepObject;

    public WorkflowStepProcess() {
    }

    public WorkflowStepProcess(Integer stepProcessId, Date initiationDate, Date expiryDate, String comments, String formData, Workflow workflowObject, WorkflowStep workflowStepObject) {

        this.stepProcessId = stepProcessId;
        this.initiationDate = initiationDate;
        this.expiryDate = expiryDate;
        this.comments = comments;
        this.formData = formData;
        this.workflowObject = workflowObject;
        this.workflowStepObject = workflowStepObject;
 
    }

    public Integer getId() {
        return stepProcessId;
    }

    public void setId(Integer stepProcessId) {
        this.stepProcessId = stepProcessId;
    }   

    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }  
    
    public Date getInitiationDate() {
        return initiationDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }  
    
    public String getFormData() {
        return formData;
    }
    
    public void setFormData(String formData) {
        this.formData = formData;
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
    
    public Workflow getWorkflowObject() {
        return workflowObject;
    }
    
    public void setWorkflowObject(Workflow workflowObject) {
        this.workflowObject = workflowObject;
    } 

    public WorkflowStep getWorkflowStepObject() {
        return workflowStepObject;
    }

    public void setWorkflowStep(WorkflowStep workflowStepObject) {
        this.workflowStepObject = workflowStepObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stepProcessId != null ? stepProcessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkflowStepProcess)) {
            return false;
        }
        WorkflowStepProcess other = (WorkflowStepProcess) object;
        if ((this.stepProcessId == null && other.stepProcessId != null) || (this.stepProcessId != null && !this.stepProcessId.equals(other.stepProcessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.machuzuerp.entities.WorkflowStepProcess[ stepProcessId=" + stepProcessId + " ]";
    }        
    
}