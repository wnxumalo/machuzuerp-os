package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "WorkflowStep.findAll", query = "SELECT d FROM WorkflowStep d")
    , @NamedQuery(name = "WorkflowStep.findByRecNum", query = "SELECT d FROM WorkflowStep d WHERE d.stepId = :stepId")
    , @NamedQuery(name = "WorkflowStep.findByName", query = "SELECT d FROM WorkflowStep d WHERE d.stepName = :stepName")})
public class WorkflowStep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepId;

    @JoinColumn(name="WORKFLOW_ID", nullable=false)
    private int workflow;

    @OneToMany(mappedBy="workflowstep", cascade=CascadeType.ALL,fetch=FetchType.EAGER)    
    private List<WorkflowTable> workflowTable = new ArrayList<>();

    private String stepNumber;
    private String stepName;
    private String stepType;
    private String outputs;
    private String succeed; //next step, end, start over
    private String fail; //end, start over
    private String failOverIdentifier; // yes, no, none
    private String failOverStepId;

    public WorkflowStep() {
    }

    public WorkflowStep(Integer stepId, String stepNumber, String stepName, String stepType, String outputs, String succeed, String fail, String failOverIdentifier, String failOverStepId) {
        
        this.stepId = stepId;
        this.stepNumber = stepNumber;
        this.stepName = stepName;
        this.stepType = stepType;
        this.outputs = outputs;
        this.succeed = succeed;
        this.fail = fail;
        this.failOverIdentifier = failOverIdentifier;
        this.failOverStepId = failOverStepId;
 
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }  
    
    public String getStepNumber() {
        return stepNumber;
    }
    
    public String getName() {
        return stepName;
    }
    
    public void setName(String stepName) {
        this.stepName = stepName;
    }  
    
    public String getStepType() {
        return stepType;
    }
   
    public void setStepType(String stepType) {
        this.stepType = stepType;
    }  
    
    public String getOutputs() {
        return outputs;
    }
    
    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }
    
    public String getSucceed() {
        return succeed;
    }
    
    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }
    
    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }
    
    public String getFailOverIdentifier() {
        return failOverIdentifier;
    }
    
    public void setFailOverIdentifier(String failOverIdentifier) {
        this.failOverIdentifier = failOverIdentifier;
    }

    public String getFailOverStepId() {
        return failOverStepId;
    }
    
    public void setFailOverStepId(String failOverStepId) {
        this.failOverStepId = failOverStepId;
    }
        
    public int getWorkflow() {
        return workflow;
    }
    
    public void setWorkflow(int workflow) {
        this.workflow = workflow;
    }  
    
    public List<WorkflowTable> getWorkflowTable() {
        return workflowTable;
    }

    public void setWorkflowTable(List<WorkflowTable> workflowTable) {
        this.workflowTable = workflowTable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stepId != null ? stepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkflowStep)) {
            return false;
        }
        WorkflowStep other = (WorkflowStep) object;
        if ((this.stepId == null && other.stepId != null) || (this.stepId != null && !this.stepId.equals(other.stepId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.machuzuerp.entities.WorkflowStep[ stepId=" + stepId + " ]";
    }        
    
}
