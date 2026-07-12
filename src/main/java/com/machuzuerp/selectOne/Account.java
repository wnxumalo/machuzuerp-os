/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.selectOne;

public class Account {

    private String aRecNum;
    private String accountNumber;
    private String accType;
    private String description;
    private float balance;
    private String comments;       
    private int id;

    public Account() {
        
    }
    
    public Account(int id,String aRecNum, String accountNumber, String description, float balance, String comments) {

        this.id = id;
        this.aRecNum = aRecNum;
        this.accountNumber = accountNumber;
        this.description = description;
        this.balance = balance;
        this.comments = comments;

    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getRecNum() {
        return aRecNum;
    }

    public void setRecNum(String aRecNum) {
        this.aRecNum = aRecNum;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }
    
     @Override
    public String toString() {
        return description;
    }

}
