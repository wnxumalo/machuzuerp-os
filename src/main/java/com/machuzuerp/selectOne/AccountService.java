package com.machuzuerp.selectOne;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import com.machuzuerp.jdbc.AccountProcessing;
import com.machuzuerp.controllers.jsf.Systems;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
 
@ManagedBean(name="accountService", eager = true)
@ApplicationScoped
public class AccountService {
     
    private List<Account> accounts;
     
    @PostConstruct
    public void init() {
        AccountProcessing ap = new AccountProcessing();
       
        accounts = new ArrayList<Account>();

        try {

            Connection connection = Systems.initConnection();
            ResultSet accs = ap.getAccounts();
            int count = 0;
            while (accs.next()) {
                accounts.add(new Account(count,accs.getString(1), accs.getString(4), accs.getString(2) + ":" + accs.getString(3) + " - " + accs.getString(4) + " - " + accs.getString(5), Float.parseFloat(accs.getString(6)), accs.getString(7)));
                System.out.println(accs.getString(1));
                count++;
            }

           /* accs = ap.getUserDefinedAccounts();
            count = 0;
            while (accs.next()) {
                accounts.add(new Account(count,accs.getString(1), accs.getString(4), accs.getString(1) + ":" + accs.getString(2) + ":" + accs.getString(3) + ":" + accs.getString(5), Float.parseFloat(accs.getString(6)), accs.getString(7)));
                count++;
            }*/

        } catch (Exception sqle){
            System.out.println("ERROR:"+sqle);
        }

    }
     
    public List<Account> getAccounts() {
        return accounts;
    } 
}