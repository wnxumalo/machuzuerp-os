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
 @NamedQuery(name="InventoryAddition.getInventoryAdditionList", 
 query="SELECT s FROM InventoryAddition s WHERE s.inventoryId = :id")})
public class InventoryAddition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVENTORY_ADDITION_ID")
    Long id;

    private LocalDate deliveryDate;
    private String comments;
    private int quantity;
    private String sKUNumber;
    private LocalDate expiryDate;

    @JoinColumn(name="INVENTORY_ID", nullable=true)
    private Long inventoryId;  

    @JoinColumn(name="SUPPLIER_ID", nullable=true)
    private Long supplierId;  

    @JoinColumn(name="DELIVERY_NOTE_ID", nullable=false)
    private Long deliveryNoteId;

    public InventoryAddition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getDeliveryNoteId() {
        return deliveryNoteId;
    }

    public void setDeliveryNoteId(Long deliveryNoteId) {
        this.deliveryNoteId = deliveryNoteId;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSKUNumber() {
        return sKUNumber;
    }

    public void setSKUNumber(String sKUNumber) {
        this.sKUNumber = sKUNumber;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        if (!(object instanceof InventoryAddition)) {
            return false;
        }
        InventoryAddition other = (InventoryAddition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.InventoryAddition[ id=" + id + " ]";
    }
    
}