/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.jdbc.AccountProcessing;
import com.machuzuerp.controllers.jsf.Accounts;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class AccountData implements Serializable {

    /**
     * Creates a new instance of AccountData
     */
    Connection connection;
    Statement statement;
    ResultSet results;
    
    String query = "";

    private String aRecNum;
    private String accountNumber;
    private String accType;
    private String description;
    private float balance;
    private String comments;
    private java.util.Date pDate;

    private String byType;
    int count = 9;
    private String byDescription;

    String accountRecNum = "";

    Accounts accounts;
    AccountProcessing ap;
    
    private List<AccountData> account = new ArrayList<AccountData>();
    private List<AccountData> allAccount = new ArrayList<AccountData>();
    private List<AccountData> filteredAccount = new ArrayList<AccountData>();
    private AccountData selectedAccount;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-yyyy");
    
    HttpServletRequest request;
    
    public AccountData() {
        
    }

    public AccountData(String aRecNum, String accountNumber, String accType, String description, float balance, String comments) {

        this.aRecNum = aRecNum;
        this.accountNumber = accountNumber;
        this.accType = accType;
        this.description = description;
        this.balance = balance;
        this.comments = comments;

    }

    public String findAccount(HttpSession session) {
        
        accountNumber = "";
        description = "";
        
        return "/accounting/find-account";
    }

    public String searchAccount() {        

        account.clear();
        try {
            
            connection = Systems.initConnection();

            int i = 0;               
            query = "SELECT * FROM accounts where description like '%" + description + "%'";                                    

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accountRecNum = results.getString(1);                    
                account.add(new AccountData(results.getString(1),results.getString(3), results.getString(2), results.getString(4), Float.parseFloat(results.getString(5)), results.getString(6)));
            }

            connection.close();


        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/payable/select-account";

    }
    
    public String getAllAccounts() {        
       
        allAccount.clear();
        
        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM accounts order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accountRecNum = results.getString(1);                
                allAccount.add(new AccountData(results.getString(1),results.getString(3), results.getString(2), results.getString(4), Float.parseFloat(results.getString(5)), results.getString(6)));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/all-accounts";
    }
    
    public String getAllAccountsReceivable() {

        allAccount.clear();
        
        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM accounts order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accountRecNum = results.getString(1);                
                allAccount.add(new AccountData(results.getString(1),results.getString(3), results.getString(2), results.getString(4), Float.parseFloat(results.getString(5)), results.getString(6)));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/receivable/all-accounts";
    }
    
     public String newPayable(String recnum) throws SQLException {

        ap = new AccountProcessing();
        ap.getAccount(recnum);
                
        this.aRecNum = recnum;
        
        getAccountRecord();

        return "/accounting/payable/new-payable";
    }  
     
     public void getAccountRecord() throws SQLException {        
        
        this.accountNumber = ap.accountNumber;
        this.accType= ap.accType;
        this.description = ap.description;
        balance = 0;
        this.comments = "";

    }
     
    public String saveAccountPayable() throws UnsupportedEncodingException {

        String ret = "";
        try {
            
                                
                ap.accountNumber = getAccountNumber();
                ap.accountType = getAccType();
                ap.description = getDescription();
                ap.balance = getBalance();
                ap.comments = getComments();               
                ap.pDate = sdf.format(pDate);

                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                request.setCharacterEncoding("UTF-8");
                
                try {
                    ap.editAccountPayable(request.getSession().getAttribute("uk").toString(), "", "", "");
                    ret = "/accounting/saved";
                } catch (SQLException npe) {
                    ret = "/accounting/notsaved";
                }
            
        } catch (NullPointerException npe) {
            //System.out.println(npe);
        }

        return ret;
    }


    public List<AccountData> getAllAccount() {

        allAccount.clear();
        
        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM accounts order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accountRecNum = results.getString(1);                
                allAccount.add(new AccountData(results.getString(1),results.getString(3), results.getString(2), results.getString(4), Float.parseFloat(results.getString(5)), results.getString(6)));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return allAccount;
    }
        
    public void filterAccount() {

        filteredAccount.clear();

        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM accounts where description like '%" + description + "%' order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accountRecNum = results.getString(1);
                filteredAccount.add(new AccountData(results.getString(1),results.getString(4), results.getString(4), results.getString(5), Float.parseFloat(results.getString(6)), results.getString(7)));
            }

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

    }

    public List<AccountData> getFilteredAccounts() {

        filterAccount();

        return filteredAccount;
    }

    public List<AccountData> getAccount() {        
        return account;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
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
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setPDate(java.util.Date pDate) {
        this.pDate = pDate;
    }

    public java.util.Date getPDate() {
        return pDate;
    }

    public AccountData getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(AccountData selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.aRecNum = ((AccountData) event.getObject()).aRecNum;
        this.accountNumber = ((AccountData) event.getObject()).accountNumber;
        this.accType = ((AccountData) event.getObject()).accType;
        this.description = ((AccountData) event.getObject()).description;
        this.balance = ((AccountData) event.getObject()).balance;
        this.comments = ((AccountData) event.getObject()).comments;
       
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/accounting/view-account.xhtml");

    }

    public void viewAccount() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/account/view-account.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AccountData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
