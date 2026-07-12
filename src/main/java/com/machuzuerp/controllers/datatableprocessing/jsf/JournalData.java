/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Journal;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class JournalData implements Serializable {

    /**
     * Creates a new instance of JournalData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String jRecNum;
    private String accountNumber;
    private String accType;
    private String description;
    private String transaction;
    private String transactionDate;
    private String debit;
    private String credit;
    private String comments;
    private String orgid;
    
    String query = "";
    String journalRecNum = "";
    
    private Date jFrom;
    private Date jTo;

    private List<JournalData> journals = new ArrayList<JournalData>();
    private List<JournalData> allJournals = new ArrayList<JournalData>();
    private List<JournalData> filteredJournals = new ArrayList<JournalData>();
    private JournalData selectedJournal;
    private List<Journal> selectedInv;
    
    HttpServletRequest request;

    public JournalData() {
        
    }

    public JournalData(String jRecNum, String accountNumber, String accType, String description, String transactionDate, String debit,String credit, String comments) {
    
        this.jRecNum = jRecNum;
        this.accountNumber = accountNumber;
        this.accType = accType;
        this.description = description;        
        this.transactionDate = transactionDate;
        this.debit = debit;
        this.credit = credit;
        this.comments = comments;

    }

    public String searchJournal() {

        journals.clear();

        try {

            connection = Systems.initConnection();

            String dFrom = sdf.format(jFrom);
            String dTo = sdf.format(jTo);
            int i = 0;                
            query = "SELECT * FROM journal where transactionDate between '" + dFrom + "' and '" + dTo + "'";                
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                journalRecNum = results.getString(1);
                //String jRecNum, String accountNumber, String accType, String description, String transactionDate, String debit,String credit, String comments) {
                journals.add(new JournalData(results.getString(1),results.getString(3), results.getString(4), results.getString(5) , results.getString(8), results.getString(6), results.getString(7), results.getString(9)));


            }

            connection.close();
            System.out.println("JORM000: " + journals.size());
            getJournal();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/accounting/journal/journal-listing";        

    }
    
    public List<JournalData> getJournal() {
        System.out.println("JORM: " + journals.size());
        return journals;
    }

    public List<JournalData> getAllJournals() {

        allJournals.clear();

        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM journals order by journalcheck";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                journalRecNum = results.getString(1);
                allJournals.add(new JournalData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9)));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println("ERR:"+sqle);
        }

        return allJournals;
    }

    /*public void filterJournals() {

        getParams();

        filteredJournals.clear();

        try {

            String query = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();
            query = "SELECT * FROM Journals where name like '%" + cName + "%' order by name";
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            int count = 0;
            proceed = true;
            while (results.next()) {
                proceed = false;
                journalRecNum = results.getString(1);
                filteredJournals.add(new JournalData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15)));
                count++;
            }

           // this.count = count;

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

    }
    
    public List<Journal> getSelectedJournal() {
        return selectedJou;
    }
 
    public void setSelectedInv(List<Journal> selectedInv) {
        this.selectedInv = selectedInv;
    }

    public List<JournalData> getFilteredJournals() {

        filterJournals();

        return filteredJournals;
    }

    public List<JournalData> getJournals() {
        getParams();

        return journals;
    }

   /* public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }*/
    
    public String getRecNum() {
        return jRecNum;
    }
 
    public void setRecNum(String jRecNum) {
        this.jRecNum = jRecNum;
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

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }
  
    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Date getDateFrom() {
        return jFrom;
    }

    public void setDateFrom(Date jFrom) {
        this.jFrom = jFrom;
    }
    
    public Date getDateTo() {
        return jTo;
    }

    public void setDateTo(Date jTo) {
        this.jTo = jTo;
    }

    public JournalData getSelectedJournal() {
        return selectedJournal;
    }

    public void setSelectedJournal(JournalData selectedJournal) {
        this.selectedJournal = selectedJournal;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.jRecNum = ((JournalData) event.getObject()).jRecNum;
        this.accountNumber = ((JournalData) event.getObject()).accountNumber;
        this.accType = ((JournalData) event.getObject()).accType;
        this.description = ((JournalData) event.getObject()).description;                  
        this.transaction = ((JournalData) event.getObject()).transaction;             
        this.transactionDate = ((JournalData) event.getObject()).transactionDate;             
        this.debit = ((JournalData) event.getObject()).debit;             
        this.credit = ((JournalData) event.getObject()).credit;             
        this.comments = ((JournalData) event.getObject()).comments;             
        this.orgid = ((JournalData) event.getObject()).orgid;             

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();    
        
        String param = "uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + jRecNum + "&accountNumber=" + accountNumber + "&accType=" + accType + "&description=" + description + "&transaction=" + transaction + "&transactionDate=" + transactionDate + "&debit=" + debit + "&credit=" + credit + "&comments=" + comments + "&orgid=" + orgid;

        //FacesContext.getCurrentInstance().getExternalContext().redirect("/journals/view-journal.xhtml?" + param);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/accounting/journal/view-journal.xhtml?" + param);

    }

    public void viewJournal() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/journals/view-journal.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/accounting/journal/view-journal.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(JournalData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
