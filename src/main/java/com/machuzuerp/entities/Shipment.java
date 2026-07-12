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
 @NamedQuery(name="Shipment.getSupplierShipments", 
 query="SELECT sh FROM Shipment sh WHERE sh.supplierId = :id")})   
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHIPMENT_ID")
    Long id;

    private String carrierName;
    private String trackingNumber;
    private String shippingMethod;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date shippingDate;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date estimateDeliveryDate;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date actualDeliveryDate;    

    @JoinColumn(name="SUPPLIER_ID", nullable=false)
    private Long supplierId;
    
    @JoinColumn(name="PURCHASE_ORDER_ID", nullable=true)
    private Long purchaseOrderId;

    //@JoinColumn(name="INVENTORY_ID", nullable=false)
    //private Long inventoryId;

    private int quantityShipped;
    private int quantityReceived;
    private int discrepancy;
    private String discrepancyReasons;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    private String status;
    private String notes;
    
    public Shipment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingTerms() {
        return trackingNumber;
    }

    public void setShippingTerms(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public String getShippingMethod() {
        return carrierName;
    }

    public void setShippingMethod(String carrierName) {
        this.carrierName = carrierName;
    }
    
    public String getPaymentTerms() {
        return shippingMethod;
    }

    public void setPaymentTerms(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
        
    public Long getSupplierId() {
        return supplierId;
    }
 
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }
 
    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
    
/*    public Long getInventoryId() {
        return inventoryId;
    }
 
    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }*/
    
    public Date getShippingDate() {
        return shippingDate;
    }
 
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }
    
    public Date getEstimateDeliveryDate() {
        return estimateDeliveryDate;
    }

    public void setEstimateDeliveryDate(Date estimateDeliveryDate) {
        this.estimateDeliveryDate = estimateDeliveryDate;
    }
    
    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }
    
    public int getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(int quantityShipped) {
        this.quantityShipped = quantityShipped;
    }
    
    public int getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(int quantityReceived) {
        this.quantityReceived = quantityReceived;
    }
    
    public int getDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(int discrepancy) {
        this.discrepancy = discrepancy;
    }
    
    public String getDiscrepancyReasons() {
        return discrepancyReasons;
    }
 
    public void setDiscrepancyReasons(String discrepancyReasons) {
        this.discrepancyReasons = discrepancyReasons;
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
        if (!(object instanceof Shipment)) {
            return false;
        }
        Shipment other = (Shipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Shipment[ id=" + id + " ]";
    }
    
}