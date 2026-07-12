package com.machuzuerp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
 @NamedQuery(name="InventoryStorageSpace.getInventoryStorageSpaceList", 
 query="SELECT s FROM InventoryStorageSpace s WHERE s.inventoryStorageId = :id")})   
public class InventoryStorageSpace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVENTORY_STORAGE_SPACE_ID")
    Long id;

    private String description;
    private String type;
    private String label;
    private int totalArea;
    private int occupiedArea;
    private int availableArea;
    private String rackingType;
    private float rackingCapacity;
    private float utilizedCapacity;
    private String accessType;
    private String accessibilityScore;
    private int accessibilityTotalScore;
    private String temperatureRequirements;
    private String securityLevel;
    private LocalDate lastOptimized;
    private String responsibleManager;

    private LocalDate implementationDate;
    private String status;
    
    @JoinColumn(name="INVENTORY_STORAGE_ID", nullable=false)
    private Long inventoryStorageId;
   
    public InventoryStorageSpace() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryStorageId() {
        return inventoryStorageId;
    }

    public void setInventoryStorageId(Long inventoryStorageId) {
        this.inventoryStorageId = inventoryStorageId;
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

    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }

    public void setTotalArea(int totalArea) {
        this.totalArea = totalArea;
    }

    public int getTotalArea() {
        return totalArea;
    }
    
    public void setOccupiedArea(int occupiedArea) {
        this.occupiedArea = occupiedArea;
    }

    public int getOccupiedArea() {
        return occupiedArea;
    }
    
    public void setAvailableArea(int availableArea) {
        this.availableArea = availableArea;
    }

    public int getAvailableArea() {
        return availableArea;
    }
    
    public void setRackingType(String rackingType) {
        this.rackingType = rackingType;
    }

    public String getRackingType() {
        return rackingType;
    }
    
    public void setRackingCapacity(float rackingCapacity) {
        this.rackingCapacity = rackingCapacity;
    }

    public float getRackingCapacity() {
        return rackingCapacity;
    }
    
    public void setUtilizedCapacity(float utilizedCapacity) {
        this.utilizedCapacity = utilizedCapacity;
    }

    public float getUtilizedCapacity() {
        return utilizedCapacity;
    }
    
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAccessType() {
        return accessType;
    }
    
    public void setAccessibilityScore(String accessibilityScore) {
        this.accessibilityScore = accessibilityScore;
    }

    public String getAccessibilityScore() {
        return accessibilityScore;
    }
    
    public void setAccessibilityTotalScore(int accessibilityTotalScore) {
        this.accessibilityTotalScore = accessibilityTotalScore;
    }

    public int getAccessibilityTotalScore() {
        return accessibilityTotalScore;
    }
    
    public void setTemperatureRequirements(String temperatureRequirements) {
        this.temperatureRequirements = temperatureRequirements;
    }

    public String getTemperatureRequirements() {
        return temperatureRequirements;
    }
    
    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setLastOptimized(LocalDate lastOptimized) {
        this.lastOptimized = lastOptimized;
    }

    public LocalDate getLastOptimized() {
        return lastOptimized;
    }
    
    public void setResponsibleManager(String responsibleManager) {
        this.responsibleManager = responsibleManager;
    }

    public String getResponsibleManager() {
        return responsibleManager;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventoryStorageSpace)) {
            return false;
        }
        InventoryStorageSpace other = (InventoryStorageSpace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.InventoryStorageSpace[ id=" + id + " ]";
    }
    
}