package com.machuzuerp.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({  
 @NamedQuery(name="Administration.getUpdateAdministrationBenefitTotal", 
 query="SELECT e FROM Administration e WHERE e.id = :id"),
 @NamedQuery(name="Administration.getEmployeeAdministration", 
 query="SELECT t FROM Administration t WHERE t.employeeId = :id"),
 @NamedQuery(name="Administration.getLoginEmployeeAdministration", 
 query="SELECT t FROM Administration t WHERE t.employeeId = :id")})
public class Administration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADMINISTRATION_ID")
    Long id;

    @JoinColumn(name="ORGANISATION_ID", nullable=false)
    private Long organisation;

    @JoinColumn(name="EMPLOYEE", nullable=false)
    private Long employeeId;

    private boolean stakeholderView;
    private boolean stakeholderCreate;
    private boolean stakeholderEdit;
    private boolean stakeholderDelete;
    private String stakeholderOwner;
    
    private boolean accountingView;
    private boolean accountingCreate;
    private boolean accountingEdit;
    private boolean accountingDelete;
    private String accountingOwner;
    
    private boolean projectManagementView;
    private boolean projectManagementCreate;
    private boolean projectManagementEdit;
    private boolean projectManagementDelete;
    private String projectManagementOwner;
    
    private boolean humanResourcesView;
    private boolean humanResourcesCreate;
    private boolean humanResourcesEdit;
    private boolean humanResourcesDelete;
    private String humanResourcesOwner;
    
    private boolean collaborationView;
    private boolean collaborationCreate;
    private boolean collaborationEdit;
    private boolean collaborationDelete;
    private String collaborationOwner;
    
    private boolean workflowView;
    private boolean workflowCreate;
    private boolean workflowEdit;
    private boolean workflowDelete;
    private String workflowOwner;
    
    private boolean inventoryView;
    private boolean inventoryCreate;
    private boolean inventoryEdit;
    private boolean inventoryDelete;
    private String inventoryOwner;

    private boolean servicesView;
    private boolean servicesCreate;
    private boolean servicesEdit;
    private boolean servicesDelete;
    private String servicesOwner;
    
    private boolean suppliersView;
    private boolean suppliersCreate;
    private boolean suppliersEdit;
    private boolean suppliersDelete;
    private String suppliersOwner;

    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    
    public Administration() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Long organisation) {
        this.organisation = organisation;
    }    

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public boolean getStakeholderView() {
        return stakeholderView;
    }

    public void setStakeholderView(boolean stakeholderView) {
        this.stakeholderView = stakeholderView;
    }
    
    public boolean getStakeholderCreate() {
        return stakeholderCreate;
    }
 
    public void setStakeholderCreate(boolean stakeholderCreate) {
        this.stakeholderCreate = stakeholderCreate;
    }
    
    public boolean getStakeholderEdit() {
        return stakeholderEdit;
    }
 
    public void setStakeholderEdit(boolean stakeholderEdit) {
        this.stakeholderEdit = stakeholderEdit;
    }
    
    public boolean getStakeholderDelete() {
        return stakeholderDelete;
    }
 
    public void setStakeholderDelete(boolean stakeholderDelete) {
        this.stakeholderDelete = stakeholderDelete;
    }
    
    public boolean getAccountingView() {
        return accountingView;
    }
 
    public void setAccountingView(boolean accountingView) {
        this.accountingView = accountingView;
    }
    
    public boolean getAccountingCreate() {
        return accountingCreate;
    }
 
    public void setAccountingCreate(boolean accountingCreate) {
        this.accountingCreate = accountingCreate;
    }
    
    public boolean getAccountingEdit() {
        return accountingEdit;
    }
 
    public void setAccountingEdit(boolean accountingEdit) {
        this.accountingEdit = accountingEdit;
    }
    
    public boolean getAccountingDelete() {
        return accountingDelete;
    }
 
    public void setAccountingDelete(boolean accountingDelete) {
        this.accountingDelete = accountingDelete;
    }
    
    public boolean getProjectManagementView() {
        return projectManagementView;
    }
 
    public void setProjectManagementView(boolean projectManagementView) {
        this.projectManagementView = projectManagementView;
    }
    
    public boolean getProjectManagementCreate() {
        return projectManagementCreate;
    }
 
    public void setProjectManagementCreate(boolean projectManagementCreate) {
        this.projectManagementCreate = projectManagementCreate;
    }
    
    public boolean getProjectManagementEdit() {
        return projectManagementEdit;
    }
 
    public void setProjectManagementEdit(boolean projectManagementEdit) {
        this.projectManagementEdit = projectManagementEdit;
    }
    
    public boolean getProjectManagementDelete() {
        return projectManagementDelete;
    }
 
    public void setProjectManagementDelete(boolean projectManagementDelete) {
        this.projectManagementDelete = projectManagementDelete;
    }
    
    public boolean getHumanResourcesView() {
        return humanResourcesView;
    }
 
    public void setHumanResourcesView(boolean humanResourcesView) {
        this.humanResourcesView = humanResourcesView;
    }
    
    public boolean getHumanResourcesCreate() {
        return humanResourcesCreate;
    }
 
    public void setHumanResourcesCreate(boolean humanResourcesCreate) {
        this.humanResourcesCreate = humanResourcesCreate;
    }
    
    public boolean getHumanResourcesEdit() {
        return humanResourcesEdit;
    }
 
    public void setHumanResourcesEdit(boolean humanResourcesEdit) {
        this.humanResourcesEdit = humanResourcesEdit;
    }
    
    public boolean getHumanResourcesDelete() {
        return humanResourcesDelete;
    }
 
    public void setHumanResourcesDelete(boolean humanResourcesDelete) {
        this.humanResourcesDelete = humanResourcesDelete;
    }
   
    public boolean getCollaborationView() {
        return collaborationView;
    }
 
    public void setCollaborationView(boolean collaborationView) {
        this.collaborationView = collaborationView;
    }
    
    public boolean getCollaborationCreate() {
        return collaborationCreate;
    }
 
    public void setCollaborationCreate(boolean collaborationCreate) {
        this.collaborationCreate = collaborationCreate;
    }
    
    public boolean getCollaborationEdit() {
        return collaborationEdit;
    }
 
    public void setCollaborationEdit(boolean collaborationEdit) {
        this.collaborationEdit = collaborationEdit;
    }
    
    public boolean getCollaborationDelete() {
        return collaborationDelete;
    }
 
    public void setCollaborationDelete(boolean collaborationDelete) {
        this.collaborationDelete = collaborationDelete;
    }
    
    public boolean getWorkflowView() {
        return workflowView;
    }
 
    public void setWorkflowView(boolean workflowView) {
        this.workflowView = workflowView;
    }
    
    public boolean getWorkflowCreate() {
        return workflowCreate;
    }
 
    public void setWorkflowCreate(boolean workflowCreate) {
        this.workflowCreate = workflowCreate;
    }
    
    public boolean getWorkflowEdit() {
        return workflowEdit;
    }
 
    public void setWorkflowEdit(boolean workflowEdit) {
        this.workflowEdit = workflowEdit;
    }
    
    public boolean getWorkflowDelete() {
        return workflowDelete;
    }
 
    public void setWorkflowDelete(boolean workflowDelete) {
        this.workflowDelete = workflowDelete;
    }
    
    public boolean getInventoryView() {
        return inventoryView;
    }
 
    public void setInventoryView(boolean inventoryView) {
        this.inventoryView = inventoryView;
    }
    
    public boolean getInventoryCreate() {
        return inventoryCreate;
    }
 
    public void setInventoryCreate(boolean inventoryCreate) {
        this.inventoryCreate = inventoryCreate;
    }
    
    public boolean getInventoryEdit() {
        return inventoryEdit;
    }
 
    public void setInventoryEdit(boolean inventoryEdit) {
        this.inventoryEdit = inventoryEdit;
    }
    
    public boolean getInventoryDelete() {
        return inventoryDelete;
    }
 
    public void setInventoryDelete(boolean inventoryDelete) {
        this.inventoryDelete = inventoryDelete;
    }
    
    public boolean getServicesView() {
        return servicesView;
    }
 
    public void setServicesView(boolean servicesView) {
        this.servicesView = servicesView;
    }
    
    public boolean getServicesCreate() {
        return servicesCreate;
    }
 
    public void setServicesCreate(boolean servicesCreate) {
        this.servicesCreate = servicesCreate;
    }
    
    public boolean getServicesEdit() {
        return servicesEdit;
    }
 
    public void setServicesEdit(boolean servicesEdit) {
        this.servicesEdit = servicesEdit;
    }
    
    public boolean getServicesDelete() {
        return servicesDelete;
    }
 
    public void setServicesDelete(boolean servicesDelete) {
        this.servicesDelete = servicesDelete;
    }
    
    public boolean getSuppliersView() {
        return suppliersView;
    }
 
    public void setSuppliersView(boolean suppliersView) {
        this.suppliersView = suppliersView;
    }
    
    public boolean getSuppliersCreate() {
        return suppliersCreate;
    }
 
    public void setSuppliersCreate(boolean suppliersCreate) {
        this.suppliersCreate = suppliersCreate;
    }
    
    public boolean getSuppliersEdit() {
        return suppliersEdit;
    }
 
    public void setSuppliersEdit(boolean suppliersEdit) {
        this.suppliersEdit = suppliersEdit;
    }
    
    public boolean getSuppliersDelete() {
        return suppliersDelete;
    }
 
    public void setSuppliersDelete(boolean suppliersDelete) {
        this.suppliersDelete = suppliersDelete;
    }   
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }
 
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public LocalDate getDateUpdated() {
        return dateUpdated;
    }
 
    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
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
        if (!(object instanceof Administration)) {
            return false;
        }
        Administration other = (Administration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Administration[ id=" + id + " ]";
    }
    
}