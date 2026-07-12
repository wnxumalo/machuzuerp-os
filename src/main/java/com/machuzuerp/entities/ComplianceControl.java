package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;

@Entity
public class ComplianceControl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMPLIANCE_CONTROL_ID")
    Long id;

    private String description;
    private String type;
    private String owner;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date implementationDate;
    private String status;
    
    @JoinColumn(name="COMPLIANCE_POLICY_ID", nullable=true)
    private Long compliancePolicy;

    public ComplianceControl() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompliancePolicy() {
        return compliancePolicy;
    }

    public void setCompliancePolicy(Long compliancePolicy) {
        this.compliancePolicy = compliancePolicy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public String getOwner() {
        return owner;
    }
 
    public void setImplementationDate(Date implementationDate) {
        this.implementationDate = implementationDate;
    }

    public Date getImplementationDate() {
        return implementationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
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
        if (!(object instanceof ComplianceControl)) {
            return false;
        }
        ComplianceControl other = (ComplianceControl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Control[ id=" + id + " ]";
    }
    
}