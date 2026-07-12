package com.machuzuerp.entities;

import java.io.Serializable;
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
 @NamedQuery(name="Supplier.getSupplier", 
 query="SELECT s FROM Supplier s WHERE s.id = :id")})   
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUPPLIER_ID")
    Long id;
    
    private String vatNum;
    private String description;
    private String supplierType;
    private String contactPerson;
    private String contactTitle;
    private String contactEmail;
    private String contactPhone;
    private String physicalAddress;
    private String country;
    private String city;
    private String district;   
    private String postalAddress;       
    private String website;
    private String productsServices;
    private String paymentTerms;
    private String leadTime;
    private String qualityRating;
    private String deliveryPerformance;
    private String certifications;
    private String notes;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    private String status;
    
    @OneToMany(mappedBy="supplierId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<InventoryAddition> inventoryList = new ArrayList<InventoryAddition>();
    
    @OneToMany(mappedBy="supplierId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
    
    @OneToMany(mappedBy="supplierId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Location> locations = new ArrayList<Location>();
    
    @OneToMany(mappedBy="supplierId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Payment> payments = new ArrayList<Payment>();
    
    @OneToMany(mappedBy="supplierId", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Shipment> shipments = new ArrayList<Shipment>();


    public Supplier() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public List<InventoryAddition> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<InventoryAddition> inventoryList) {
        this.inventoryList = inventoryList;
    }
    
    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrders;
    }

    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
    
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
    
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }
    
    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }        

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }
    
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
        
    public String getContactPhone() {
        return contactPhone;
    }
 
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public String getPhysicalAddress() {
        return physicalAddress;
    }
 
    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
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
    
    public String getPostalAddress() {
        return postalAddress;
    }
 
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getWebsite() {
        return website;
    }
 
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getProductsServices() {
        return productsServices;
    }
 
    public void setProductsServices(String productsServices) {
        this.productsServices = productsServices;
    }
    
    public String getPaymentTerms() {
        return paymentTerms;
    }
 
    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }
    
    public String getLeadTime() {
        return leadTime;
    }
 
    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }
    
    public String getQualityRating() {
        return qualityRating;
    }
 
    public void setQualityRating(String qualityRating) {
        this.qualityRating = qualityRating;
    }
    
    public String getDeliveryPerformance() {
        return deliveryPerformance;
    }
 
    public void setDeliveryPerformance(String deliveryPerformance) {
        this.deliveryPerformance = deliveryPerformance;
    }
    
    public String getCertifications() {
        return certifications;
    }
 
    public void setCertifications(String certifications) {
        this.certifications = certifications;
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
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getNotes() {
        return notes;
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
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Supplier[ id=" + id + " ]";
    }
    
}