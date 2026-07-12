package com.machuzuerp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
@NamedQueries({  
 @NamedQuery(name="Inventory.getInventory", 
 query="SELECT i FROM Inventory i WHERE i.id = :id")})   
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVENTORY_ID")
    Long id;
    
    private String code;
    private String description;
    private Long clientId;
    private BigDecimal cost;
    private BigDecimal sellingPrice;
    private int reorderLevel;
    private float markup;
    private float atHand;
    private float itemSize;
    private String color;       
    private String expiryDates;
    private LocalDate creationDate;
    private String status;

    @OneToMany(mappedBy="inventoryId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<InventoryStorage> inventoryStorageList = new ArrayList<InventoryStorage>();

    @OneToMany(mappedBy="inventoryId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<QualityAssurance> qualityAssuranceList = new ArrayList<QualityAssurance>();

    public Inventory() {
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

    public String getExpiryDates() {
        return expiryDates;
    }

    public void setExpiryDates(String expiryDates) {
        this.expiryDates = expiryDates;
    }
    
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public List<InventoryStorage> getInventoryStorageList() {
        return inventoryStorageList;
    }

    public void setInventoryStorageList(List<InventoryStorage> inventoryStorageList) {
        this.inventoryStorageList = inventoryStorageList;
    }
    
    public List<QualityAssurance> getQualityAssuranceList() {
        return qualityAssuranceList;
    }

    public void setQualityAssuranceList(List<QualityAssurance> qualityAssuranceList) {
        this.qualityAssuranceList = qualityAssuranceList;
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
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Inventory[ id=" + id + " ]";
    }
    
}