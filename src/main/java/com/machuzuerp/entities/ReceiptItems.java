package com.machuzuerp.entities;

import java.math.BigDecimal;

public class ReceiptItems {

    private Integer id;
    private String userRecNum;
    private String receiptCheck;
    private String description;
    private BigDecimal price;
    private int quantity;
    private BigDecimal amount;
    private int servId;
    private int invId;

    public ReceiptItems(Integer id, String userRecNum, String receiptCheck, String description, BigDecimal price, int quantity, BigDecimal amount, int servId, int invId) {
        this.id = id;
        this.userRecNum = userRecNum;
        this.receiptCheck = receiptCheck;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.servId = servId;
        this.invId = invId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserRecNum() {
        return userRecNum;
    }

    public void setUserRecNum(String userRecNum) {
        this.userRecNum = userRecNum;
    }
    
    public String getReceiptCheck() {
        return receiptCheck;
    }

    public void setReceiptCheck(String receiptCheck) {
        this.receiptCheck = receiptCheck;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setDescription(BigDecimal price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public int getServId() {
        return servId;
    }
    
    public void setServId(int servId) {
        this.servId = servId;
    }
    
    public int getInvId() {
        return invId;
    }
    
    public void setInvId(int invId) {
        this.invId = invId;
    }
}