package com.machuzuerp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

@Entity
@NamedQueries({  
 @NamedQuery(name="QualityAssurance.getQualityAssuranceList", 
 query="SELECT s FROM QualityAssurance s WHERE s.inventoryId = :id")})   
public class QualityAssurance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUALITY_ASSURANCE_ID")
    Long id;

    private String lotNumber;
    private String inspectionType;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inspectionDate;    
    
    @JoinColumn(name="INVENTORY_ID", nullable=false)
    private Long inventoryId;

    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    private Long inspectorId;
    
    private String inspectionStatus;
    private String inspectionNotes;
    private String qualityAttributes;
    private String correctiveAction;
    private String productDisposition;
    private String sampleSize;
    private String testMethod;
    private String equipmentUsed;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updateDate;
    private String status;
    private String notes;
    private String metadata;
    

    public QualityAssurance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingTerms() {
        return inspectionType;
    }

    public void setShippingTerms(String inspectionType) {
        this.inspectionType = inspectionType;
    }
    
    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
    
    public String getShippingMethod() {
        return lotNumber;
    }

    public void setShippingMethod(String lotNumber) {
        this.lotNumber = lotNumber;
    }
        
    public Long getInventoryId() {
        return inventoryId;
    }
 
    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    public Long getInspectorId() {
        return inspectorId;
    }
 
    public void setInspectorId(Long inspectorId) {
        this.inspectorId = inspectorId;
    }
    
    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    
    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }
    
    public String getInspectionNotes() {
        return inspectionNotes;
    }
    
    public void setInspectionNotes(String inspectionNotes) {
        this.inspectionNotes = inspectionNotes;
    }
    
    public String getCorrectiveAction() {
        return correctiveAction;
    }
    
    public void setProductDisposition(String productDisposition) {
        this.productDisposition = productDisposition;
    }
    
    public String getProductDisposition() {
        return productDisposition;
    }
    
    public void setSampleSize(String sampleSize) {
        this.sampleSize = sampleSize;
    }
    
    public String getSampleSize() {
        return sampleSize;
    }
    
    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }
    
    public String getTestMethod() {
        return testMethod;
    }
    
    public void setEquipmentUsed(String equipmentUsed) {
        this.equipmentUsed = equipmentUsed;
    }
    
    public String getEquipmentUsed() {
        return equipmentUsed;
    }

    public void setCorrectiveAction(String inspectionNotes) {
        this.correctiveAction = inspectionNotes;
    }
    
    public String getDiscrepancyReasons() {
        return qualityAttributes;
    }
 
    public void setDiscrepancyReasons(String qualityAttributes) {
        this.qualityAttributes = qualityAttributes;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
 
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
 
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
 
    public void setNotes(String notes) {
        this.notes = notes;
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
        if (!(object instanceof QualityAssurance)) {
            return false;
        }
        QualityAssurance other = (QualityAssurance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.QualityAssurance[ id=" + id + " ]";
    }
    
}