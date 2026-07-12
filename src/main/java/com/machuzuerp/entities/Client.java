package com.machuzuerp.entities;

public class Client {

    private String id;
    private String name;
    private String telephone;
    private String cellphone;
    private String email;
    private String postalAddress;

    public Client(String id, String name, String telephone, String cellphone, String email, String postalAddress) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.email = email;
        this.postalAddress = postalAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
}