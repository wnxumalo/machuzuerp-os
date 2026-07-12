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
public class PurchaseRequisition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PURCHASE_REQUISITION_ID")
    Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date requestDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date requestedDeliveryDate;
    private String soleSourceJustificationAttached;
    private String requestorName;
    private String remarks;    

    @JoinColumn(name="SUPPLIER_ID", nullable=false)
    private Long supplierId;

    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    private Long accountsManagerId;
    
    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    private Long deliveryEmployeeId;
   
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    private String status;
    private String notes;   

    public PurchaseRequisition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPODate() {
        return requestDate;
    }

    public void setPODate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getDeliveryDate() {
        return requestedDeliveryDate;
    }

    public void setDeliveryDate(Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }
    
    public String getShippingTerms() {
        return requestorName;
    }

    public void setShippingTerms(String requestorName) {
        this.requestorName = requestorName;
    }
    
    public String getShippingMethod() {
        return soleSourceJustificationAttached;
    }

    public void setShippingMethod(String soleSourceJustificationAttached) {
        this.soleSourceJustificationAttached = soleSourceJustificationAttached;
    }
    
    public String getPaymentTerms() {
        return remarks;
    }

    public void setPaymentTerms(String remarks) {
        this.remarks = remarks;
    }
        
    public Long getSupplierId() {
        return supplierId;
    }
 
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getInventoryId() {
        return accountsManagerId;
    }
 
    public void setInventoryId(Long accountsManagerId) {
        this.accountsManagerId = accountsManagerId;
    }
    
    public Long getDeliveryEmployeeId() {
        return deliveryEmployeeId;
    }
 
    public void setDeliveryEmployeeId(Long deliveryEmployeeId) {
        this.deliveryEmployeeId = deliveryEmployeeId;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
 
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
        if (!(object instanceof PurchaseRequisition)) {
            return false;
        }
        PurchaseRequisition other = (PurchaseRequisition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.PurchaseRequisition[ id=" + id + " ]";
    }
    
}