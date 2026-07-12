package com.machuzuerp.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class WorkflowTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tableId;

    @JoinColumn(name="WORKFLOW_ID", nullable=false)
    private int workflow;
    
    @JoinColumn(name="WORKFLOWSTEP_ID", nullable=false)
    private int workflowstep;

    private String tableDefinition;

    public WorkflowTable() {
    }

    public WorkflowTable(String tableDefinition) {

        this.tableDefinition = tableDefinition;        
 
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public void setTableDefinition(String tableDefinition) {
        this.tableDefinition = tableDefinition;
    }  
    
    public String getTableDefinition() {
        return tableDefinition;
    }    

    public int getWorkflow() {
        return workflow;
    }
    
    public void setWorkflow(int workflow) {
        this.workflow = workflow;
    }  

    public int getWorkflowStep() {
        return workflowstep;
    }

    public void setWorkflowStep(int workflowstep) {
        this.workflowstep = workflowstep;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tableId != null ? tableId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkflowTable)) {
            return false;
        }
        WorkflowTable other = (WorkflowTable) object;
        if ((this.tableId == null && other.tableId != null) || (this.tableId != null && !this.tableId.equals(other.tableId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.machuzuerp.entities.WorkflowTable[ tableId=" + tableId + " ]";
    }

}
