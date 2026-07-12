package com.machuzuerp.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;

@Entity
public class AuditLogging implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUDITLOGGING_ID")
    Long id;

    @JoinColumn(name="ORGANISATION_ID", nullable=false)
    private Long organisation;

    @JoinColumn(name="EMPLOYEE", nullable=false)
    private Long employeeId;

    private String userAction;
    private String appModule;
    private String actionResponse;
    
    private LocalDate dateCreated;
    private LocalTime timeCreated;

    public AuditLogging() {
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

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }
    
    public String getAppModule() {
        return appModule;
    }
 
    public void setAppModule(String appModule) {
        this.appModule = appModule;
    }
    
    public String getActionResponse() {
        return actionResponse;
    }
 
    public void setActionResponse(String actionResponse) {
        this.actionResponse = actionResponse;
    }
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }
 
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalTime getTimeCreated() {
        return timeCreated;
    }
 
    public void setTimeCreated(LocalTime timeCreated) {
        this.timeCreated = timeCreated;
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
        if (!(object instanceof AuditLogging)) {
            return false;
        }
        AuditLogging other = (AuditLogging) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.AuditLogging[ id=" + id + " ]";
    }
    
}