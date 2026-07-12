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
 @NamedQuery(name="PasswordManagement.getPasswordManagement", 
 query="SELECT p FROM PasswordManagement p WHERE p.employeeId = :id")})   
public class PasswordManagement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PASSWORD_MANAGEMENT_ID")
    Long id;

    private String ruleType;
    private boolean twoFactor;
    private String changeInterval;

    @JoinColumn(name="EMPLOYEE", nullable=false)
    private Long employeeId;
    
    private LocalDate creationDate;

    public PasswordManagement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }
    
    public boolean getTwoFactor() {
        return twoFactor;
    }
 
    public void setTwoFactor(boolean twoFactor) {
        this.twoFactor = twoFactor;
    }
    
    public String getChangeInterval() {
        return changeInterval;
    }
 
    public void setChangeInterval(String changeInterval) {
        this.changeInterval = changeInterval;
    }
        
    public LocalDate getCreationDate() {
        return creationDate;
    }
 
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
        if (!(object instanceof PasswordManagement)) {
            return false;
        }
        PasswordManagement other = (PasswordManagement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.PasswordManagement[ id=" + id + " ]";
    }
    
}