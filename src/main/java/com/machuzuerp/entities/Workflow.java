/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 *
 * @author Wandile
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Workflow.findAll", query = "SELECT d FROM Workflow d")
    , @NamedQuery(name = "Workflow.findByRecNum", query = "SELECT d FROM Workflow d WHERE d.workflowId = :workflowId")
    , @NamedQuery(name = "Workflow.findByName", query = "SELECT d FROM Workflow d WHERE d.name = :name")
    , @NamedQuery(name = "Workflow.findByDepartment", query = "SELECT d FROM Workflow d WHERE d.department = :department")
    , @NamedQuery(name = "Workflow.findByType", query = "SELECT d FROM Workflow d WHERE d.type = :type")
    , @NamedQuery(name = "Workflow.findByTermination", query = "SELECT d FROM Workflow d WHERE d.termination = :termination")})
        
public class Workflow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "workflowId")
    private Integer workflowId;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "department")
    private String department;
    @Size(max = 255)
    @Column(name = "type")
    private String type;
    @Size(max = 255)
    @Column(name = "termination")
    private String termination;    
    @Column(name = "uuid")
    private String uuid;

    @OneToMany(mappedBy="workflow", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<WorkflowStep> workflowsteps = new ArrayList<>();

    public Workflow() {
    }

    public Workflow(Integer workflowId) {
        this.workflowId = workflowId;
    }

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTermination() {
        return termination;
    }

    public void setTermination(String termination) {
        this.termination = termination;           
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public List<WorkflowStep> getWorkflowSteps() {
        return workflowsteps;
    }

    public void setWorkflowSteps(List<WorkflowStep> workflowsteps) {
        this.workflowsteps = workflowsteps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workflowId != null ? workflowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workflow)) {
            return false;
        }
        Workflow other = (Workflow) object;
        if ((this.workflowId == null && other.workflowId != null) || (this.workflowId != null && !this.workflowId.equals(other.workflowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.machuzuerp.entities.Workflow[ workflowId=" + workflowId + " ]";
    }
    
}
