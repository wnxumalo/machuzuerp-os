
package com.machuzuerp.reports;

import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.jsf.Systems;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class JournalReport implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String query = "";    

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
    
    private Date jFrom;
    private Date jTo;

    private List<JournalReport> journals = new ArrayList<JournalReport>();   
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    HttpServletRequest request;

    public JournalReport() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public JournalReport(String jRecNum, String accountNumber, String accType, String description, String transactionDate, String debit,String credit, String comments) {
    
        this.jRecNum = jRecNum;
        this.accountNumber = accountNumber;
        this.accType = accType;
        this.description = description;        
        this.transactionDate = transactionDate;
        this.debit = debit;
        this.credit = credit;
        this.comments = comments;
        this.orgid = orgid;

    }
    
    public List<JournalReport> getJournal() {
        return journals;
    }

    public List<JournalReport> getAllJournals() {

        journals.clear();

        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM journal order by recnum";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {             
                journals.add(new JournalReport(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(8), results.getString(6), results.getString(7), results.getString(9)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println("ERR:"+sqle);
        }

        return journals;
    }

    public void searchJournal() {

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
                journals.add(new JournalReport(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(8), results.getString(6), results.getString(7), results.getString(9)));
            }

            connection.close();            

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

       

    }
    
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

}
