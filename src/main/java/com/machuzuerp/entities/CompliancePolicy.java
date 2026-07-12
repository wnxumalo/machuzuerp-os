package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class CompliancePolicy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMPLIANCE_POLICY_ID")
    Long id;

    private String title;
    private String description;
    private String category;
    
    @JoinColumn(name="EMPLOYEE_ID", nullable=true)
    private Long owner;
    
    @JoinColumn(name="SUPPLIER_ID", nullable=true)
    private Long supplierId;    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date effectiveDate;

    @OneToMany(mappedBy="compliancePolicy", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<CompliancePolicyRisk> risks = new ArrayList<>();
    
    @OneToMany(mappedBy="compliancePolicy", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<ComplianceControl> controls = new ArrayList<>();       

    public CompliancePolicy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCategory() {
        return category;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
    
    public Long getOwner() {
        return owner;
    }
    
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }        
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setRisks(List<CompliancePolicyRisk> risks) {
        this.risks = risks;
    }
    
    public List<CompliancePolicyRisk> getRisks() {
        return risks;
    }

    public void setControl(List<ComplianceControl> controls) {
        this.controls = controls;
    }

    public List<ComplianceControl> getControl() {
        return controls;
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
        if (!(object instanceof CompliancePolicy)) {
            return false;
        }
        CompliancePolicy other = (CompliancePolicy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Policy[ id=" + id + " ]";
    }
    
}