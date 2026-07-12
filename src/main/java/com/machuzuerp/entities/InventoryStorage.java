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
 @NamedQuery(name="InventoryStorage.getInventoryStorageList", 
 query="SELECT s FROM InventoryStorage s WHERE s.inventoryId = :id")})   
public class InventoryStorage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVENTORY_STORAGE_ID")
    Long id;

    private String description;
    private String type;
    private String category;
    private String manufacturer;
    private int quantity;
    private String location;
    private LocalDate arrivalDate;
    private LocalDate expirationDate;
    private String itemCondition;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private int reorderLevel;
    private int reorderQuantity;
    private String warehouseSection;
    private LocalDate implementationDate;
    private String status;
    
    @JoinColumn(name="SUPPLIER_ID", nullable=false)
    private Long supplierId;
    
    @JoinColumn(name="INVENTORY_ID", nullable=false)
    private Long inventoryId;
    
    @OneToMany(mappedBy="inventoryStorageId",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<InventoryStorageSpace> inventoryStorageSpaceList = new ArrayList<InventoryStorageSpace>();    
   
    public InventoryStorage() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCategory() {
        return category;
    }
 
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
    
    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }
    
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    
    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public String getItemCondition() {
        return itemCondition;
    }
    
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    
    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }
    
    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }
    
    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public int getReorderQuantity() {
        return reorderQuantity;
    }

    public void setWareHouseSection(String warehouseSection) {
        this.warehouseSection = warehouseSection;
    }

    public String getWareHouseSection() {
        return warehouseSection;
    }
    
    public void setImplementationDate(LocalDate implementationDate) {
        this.implementationDate = implementationDate;
    }

    public LocalDate getImplementationDate() {
        return implementationDate;
    }    

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public List<InventoryStorageSpace> getInventoryStorageSpaceList() {
        return inventoryStorageSpaceList;
    }

    public void setInventoryStorageSpaceList(List<InventoryStorageSpace> inventoryStorageSpaceList) {
        this.inventoryStorageSpaceList = inventoryStorageSpaceList;
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
        if (!(object instanceof InventoryStorage)) {
            return false;
        }
        InventoryStorage other = (InventoryStorage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.InventoryStorage[ id=" + id + " ]";
    }
    
}