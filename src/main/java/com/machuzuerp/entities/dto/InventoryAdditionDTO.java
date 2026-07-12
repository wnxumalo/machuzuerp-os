package com.machuzuerp.entities.dto;

import java.time.LocalDate;

public class InventoryAdditionDTO {

    Long id;
    private String sKUNumber;
    private LocalDate deliveryDate;
    private String comments;
    private int quantity;

    private Long inventoryId;  
    private String inventoryDescription;  
    private Long supplierId;  
    private String supplierDescription;  
    private Long deliveryNoteId;
    private LocalDate expiryDate;

    public InventoryAdditionDTO(Long id, LocalDate deliveryDate, String comments, int quantity, Long inventoryId, String inventoryDescription, Long supplierId, String supplierDescription, Long deliveryNoteId) {
        
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.comments = comments;
        this.quantity = quantity;
        this.inventoryId = inventoryId;
        this.inventoryDescription = inventoryDescription;
        this.supplierId = supplierId;
        this.supplierDescription = supplierDescription;
        this.deliveryNoteId = deliveryNoteId;
    }

    public InventoryAdditionDTO(String sKUNumber, LocalDate expiryDate) {
        this.sKUNumber = sKUNumber;
        this.expiryDate = expiryDate;
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
    
    public String getInventoryDescription() {
        return inventoryDescription;
    }

    public void setInventoryDescription(String inventoryDescription) {
        this.inventoryDescription = inventoryDescription;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public String getSupplierDescription() {
        return supplierDescription;
    }

    public void setSupplierId(String supplierDescription) {
        this.supplierDescription = supplierDescription;
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

}