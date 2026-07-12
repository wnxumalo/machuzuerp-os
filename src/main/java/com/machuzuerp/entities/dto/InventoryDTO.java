package com.machuzuerp.entities.dto;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;

public class InventoryDTO {

    private Long id;
    private String code;
    private String description;
    private Long clientId;
    private String clientName;
    private BigDecimal cost;
    private BigDecimal sellingPrice;
    private int reorderLevel;
    private float markup;
    private float atHand;
    private float itemSize;
    private String color;   
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    private String status;
    
    private Long supplierId;   
    private String supplier;         
    private String low;
    private String normal;
    private String expiryDate;

    public InventoryDTO() {
        
    }
    
    public InventoryDTO(Long id, String code, String description, Long clientId, String clientName, BigDecimal cost, BigDecimal sellingPrice, int reorderLevel, float markup,
            float atHand, float itemSize, String color, Long supplierId, String supplier, String low, String normal, String expiryDate) {

        this.id = id;
        this.code = code;
        this.description = description;
        this.clientId = clientId;
        this.clientName = clientName;
        this.cost = cost;
        this.sellingPrice = sellingPrice;
        this.reorderLevel = reorderLevel;
        this.markup = markup;
        this.atHand = atHand;
        this.itemSize = itemSize;
        this.color = color;
        this.supplierId = supplierId;
        this.supplier = supplier;
        this.low = low;
        this.normal = normal;
        this.expiryDate = expiryDate;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
  
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    
    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
        
    public float getMarkup() {
        return markup;
    }
 
    public void setMarkup(float markup) {
        this.markup = markup;
    }
    
    public float getAtHand() {
        return atHand;
    }
 
    public void setAtHand(float atHand) {
        this.atHand = atHand;
    }
    
    public float getSize() {
        return itemSize;
    }
 
    public void setSize(float itemSize) {
        this.itemSize = itemSize;
    }
    
    public String getColor() {
        return color;
    }
 
    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }
    
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    public String getSupplier() {
        return supplier;
    }
    
    public void setLowColor(String low) {
        this.low = low;
    }
    
    public String getLowColor() {
        return low;
    }
    
    public void setNormal(String normal) {
        this.normal = normal;
    }
    
    public String getNormal() {
        return normal;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }

}