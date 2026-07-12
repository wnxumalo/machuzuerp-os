package com.machuzuerp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class ComplianceRiskControl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMPLIANCE_RISK_CONTROL_ID")
    Long id;

    @JoinColumn(name="RISK_ID", nullable=true)
    private Long risk;
    
    @JoinColumn(name="COMPLIANCE_CONTROL_ID", nullable=true)
    private Long control;

    public ComplianceRiskControl() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRisk() {
        return risk;
    }

    public void setRisk(Long risk) {
        this.risk = risk;
    }

    public Long getControl() {
        return control;
    }

    public void setControl(Long control) {
        this.control = control;
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
        if (!(object instanceof ComplianceRiskControl)) {
            return false;
        }
        ComplianceRiskControl other = (ComplianceRiskControl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.RiskControl[ id=" + id + " ]";
    }
    
}