/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.JournalData;
import com.machuzuerp.jdbc.JournalProcessing;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class Journal {

    private String recnum;

    private String accountNumber;
    private String accType;
    private String description;
    private String transaction;
    private String transactionDate;
    private double debit;
    private double credit;
    private String comments;
    private String orgid;
    
    String cDate;

    String ret = "";

    private List<JournalData> journals;

    Connection connection;
    Statement statement;
    ResultSet results;
    
    JournalProcessing jp = new JournalProcessing();
    
    HttpServletRequest request;

    public Journal() {        
    }
    
    public Journal(String description) {

        this.description = description;
        
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
    
    public String getTransaction() {
        return transaction;
    }
    
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    
    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }
  
    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum"); 
 
        jp.accountNumber = params.get("accountNumber");
        jp.accType = this.accType;
        jp.description = this.description;
        jp.transaction = this.transaction;
        jp.transactionDate = this.transactionDate;
        jp.debit = this.debit;
        jp.credit = this.credit;
        jp.comments = this.comments;
        jp.orgid = this.orgid;

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

   /* public String saveAccount() {
        getParams();

        try {
            ap.editAccount(uk, "", "", "");
            ret = "/accounting/saved";
        } catch (Exception npe) {
            ret = "/accounting/notsaved";
        }

        return ret;
    }

    public String saveEditAccount() throws SQLException {

        getParams();

        try {

            ap.aRecNum = recnum;
            //sp.code = code;
            //sp.description = description;
            //sp.cost =  cost;

            ap.editAccount(uk, "edit", "", recnum);
            ret = "/accounting/saved";
        } catch (NullPointerException npe) {
            ret = "/accounting/notsaved";
        }

        return ret;
    }
*/
    
    public String editJournal_1(String aRecNum, String accountNumber, String accType, String description, String transaction, String transactionDate, double debit,double credit, String comments) throws SQLException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        this.recnum = aRecNum;
        this.accountNumber = accountNumber;
        this.accType = accType;
        this.description = description;
        this.transaction = transaction;
        this.transactionDate = transactionDate;
        this.debit = debit;
        this.credit = credit;
       
        return "/accounting/edit-account";
    }

    public void getAccount() throws SQLException {

        jp.getJournal(recnum);

       this.recnum = jp.jRecNum;
        this.accountNumber = jp.accountNumber;
        this.accType = jp.accType;
        this.description = jp.description;
        this.transaction = jp.transaction;
        this.transactionDate = jp.transactionDate;
        this.debit = jp.debit;
        this.credit = jp.credit;

    }

    public String deleteJournal() throws SQLException {
        getParams();

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            jp.editJournal(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "saved";
        } catch (NullPointerException npe) {
            ret = "notsaved";
        }

        return ret;
    }

}
