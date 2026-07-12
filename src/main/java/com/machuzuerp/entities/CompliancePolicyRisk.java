package com.machuzuerp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class CompliancePolicyRisk implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMPLIANCE_RISKS_ID")
    Long id;

    private String description;
    private String likelihood;
    private String impact;
    private String status;
    
    @JoinColumn(name="COMPLIANCE_POLICY_ID", nullable=true)
    private Long compliancePolicy;

    public CompliancePolicyRisk() {
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

    public String getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(String likelihood) {
        this.likelihood = likelihood;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
    
    public String getImpact() {
        return impact;
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
        if (!(object instanceof CompliancePolicyRisk)) {
            return false;
        }
        CompliancePolicyRisk other = (CompliancePolicyRisk) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Risks[ id=" + id + " ]";
    }
    
}