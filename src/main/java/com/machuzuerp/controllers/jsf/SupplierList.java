package com.machuzuerp.controllers.jsf;

public class SupplierList {

    private Long id;
    private String name;
    private String telephone;
    private String cellphone;
    private String postalAddress;
    private String physicalAddress;
    private String email;
    private String vatNum;

    public SupplierList() {
        
    }
    
    public SupplierList(Long id, String name, String telephone, String cellphone, String postalAddress, String physicalAddress, String email, String vatNum) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.email = email;
        this.vatNum = vatNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }
}