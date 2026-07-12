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
 @NamedQuery(name="Payment.getSupplierPayments", 
 query="SELECT sp FROM Payment sp WHERE sp.supplierId = :id")})   
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PAYMENT_ID")
    Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dueDate;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date paymentDate;    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date actualDeliveryDate;    

    @JoinColumn(name="SUPPLIER_ID", nullable=false)
    private Long supplierId;
    
    @JoinColumn(name="PURCHASE_ORDER_ID", nullable=false)
    private Long purchaseOrderId;
    
    @JoinColumn(name="INVOICE_ID", nullable=false)
    private Long invoiceId;
    
    private String account;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String paymentMethodDetails;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updateDate;
    private String status;
    private String notes;
    private String metadata;
    
    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public Long getInvoiceId() {
        return invoiceId;
    }
 
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
 
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getAccount() {
        return account;
    }
 
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getCurrency() {
        return currency;
    }
 
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
 
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }
 
    public String getPaymentMethod() {
        return paymentMethod;
    }
 
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentMethodDetails() {
        return paymentMethodDetails;
    }
 
    public void setPaymentMethodDetails(String paymentMethodDetails) {
        this.paymentMethodDetails = paymentMethodDetails;
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
    
    public String getMetadata() {
        return metadata;
    }
 
    public void setMetadata(String metadata) {
        this.metadata = metadata;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Payment[ id=" + id + " ]";
    }
    
}