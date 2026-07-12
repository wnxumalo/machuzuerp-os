package com.machuzuerp.selectOne;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
 
@ManagedBean
public class AccountView {
     
    private String option;   
    private Account acc; 
    private List<Account> accounts;
     
    @ManagedProperty("#{accountService}")
    private AccountService service;       
 
    public String getOption() {
        return option;
    }
 
    public void setOption(String option) {
        this.option = option;
    }
 
    public List<Account> getAccount() {
        return service.getAccounts();
    }

    public void setAccount(Account acc) {
        this.acc = acc;        
    }
 
    public List<Account> getAccounts() {
        return accounts;
    }
 
    public void setService(AccountService service) {
        this.service = service;
    }
}