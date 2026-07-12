package com.machuzuerp.entities;

import java.io.Serializable;
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
 @NamedQuery(name="Location.getSupplierLocations", 
 query="SELECT l FROM Location l WHERE l.supplierId = :id")})   
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOCATION_ID")
    Long id;
    
    private String description;
    private String locationType;
    private String physicalAddress1;
    private String physicalAddress2;    
    private String city;
    private String district;
    private String country;
    private String postalCode;
    private String latitude;
    private String longitude;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String capacity;
    private String area;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updateDate;
    private String status;
    private String notes;
    private String metadata;
    
    @JoinColumn(name="SUPPLIER_ID", nullable=false)
    private Long supplierId;

    
    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCity() {
        return city;
    }
 
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getDistrict() {
        return district;
    }
 
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPostalCode(){
        return postalCode;
    }
 
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCurrency() {
        return locationType;
    }
 
    public void setCurrency(String locationType) {
        this.locationType = locationType;
    }
    
    public String getLatitude() {
        return latitude;
    }
 
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
    
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocationMethod() {
        return physicalAddress1;
    }
 
    public void setLocationMethod(String physicalAddress1) {
        this.physicalAddress1 = physicalAddress1;
    }
    
    public String getLocationMethodDetails() {
        return physicalAddress2;
    }
 
    public void setLocationMethodDetails(String physicalAddress2) {
        this.physicalAddress2 = physicalAddress2;
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
    
    public Long getSupplierId() {
        return supplierId;
    }
 
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Location[ id=" + id + " ]";
    }
    
}