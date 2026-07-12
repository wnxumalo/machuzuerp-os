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
 @NamedQuery(name="PurchaseOrder.getSupplierPurchaseOrders", 
 query="SELECT o FROM PurchaseOrder o WHERE o.supplierId = :supplierId"),
 @NamedQuery(name="PurchaseOrder.getPurchaseOrdersByDate", 
 query="SELECT o FROM PurchaseOrder o WHERE o.poDate between :dateFrom and :dateTo")})

public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PURCHASE_ORDER_ID")
    Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date poDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date deliveryDate;
    private String shippingMethod;
    private String shippingTerms;
    private String paymentTerms;   
    
    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    private long deliverTo;        
    
    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    private long requestorId;
    
    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    private long accountsManagerId;
    
    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    private long approverId;

    @JoinColumn(name="SUPPLIER_ID", nullable=false)
    private Long supplierId;

    @JoinColumn(name="INVENTORY_ID", nullable=false)
    private Long inventoryId;
    
    @JoinColumn(name="PURCHASE_REQUISITION_ID", nullable=false)
    private Long requisitionId;

    private BigDecimal total = new BigDecimal("0");
    private BigDecimal subTotal = new BigDecimal("0");
    private BigDecimal shippingCharges = new BigDecimal("0");
    private BigDecimal taxAmount = new BigDecimal("0");
    private BigDecimal totalAmount = new BigDecimal("0");

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    private String status;
    private String notes;

    public PurchaseOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPODate() {
        return poDate;
    }

    public void setPODate(Date poDate) {
        this.poDate = poDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public String getShippingTerms() {
        return shippingTerms;
    }

    public void setShippingTerms(String shippingTerms) {
        this.shippingTerms = shippingTerms;
    }
    
    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }
 
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getDeliverTo() {
        return deliverTo;
    }
 
    public void setDeliverTo(Long deliverTo) {
        this.deliverTo = deliverTo;
    }
    
    public Long getRequestorId() {
        return requestorId;
    }
 
    public void setRequestorId(Long requestorId) {
        this.requestorId = requestorId;
    }
    
    public Long getAccountsManagerId() {
        return accountsManagerId;
    }
 
    public void setAccountsManagerId(Long accountsManagerId) {
        this.accountsManagerId = accountsManagerId;
    }
    
    public Long getApproverId() {
        return approverId;
    }
 
    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }
            
    public Long getInventoryId() {
        return inventoryId;
    }
 
    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    public Long getRequisitionId() {
        return requisitionId;
    }
 
    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
 
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public BigDecimal getSubTotal() {
        return subTotal;
    }
 
    public void setubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
    public BigDecimal getShippingCharges() {
        return shippingCharges;
    }
 
    public void setShippingCharges(BigDecimal shippingCharges) {
        this.shippingCharges = shippingCharges;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
 
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public BigDecimal getProductsServices() {
        return totalAmount;
    }
 
    public void setProductsServices(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
        if (!(object instanceof PurchaseOrder)) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.PurchaseOrder[ id=" + id + " ]";
    }
    
}