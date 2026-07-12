package com.machuzuerp.selectOne;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
 
@ManagedBean
public class SelectOneView {
     
    private String aType;   
    private Account acc; 
    private List<Account> accounts;
     
    @ManagedProperty("#{accountService}")
    private AccountService service;
     
    @PostConstruct
    public void init() {
        accounts = service.getAccounts();
    }
 
    public String getAccType() {
        return aType;
    }
 
    public void setAccType(String aType) {
        this.aType = aType;
    }
 
    public Account getAccounts() {
        return acc;
    }
 
    public void setAccounts(Account acc) {
        this.acc = acc;
    }
 
    public List<Account> getAccount() {
        return accounts;
    }
 
    public void setService(AccountService service) {
        this.service = service;
    }
}