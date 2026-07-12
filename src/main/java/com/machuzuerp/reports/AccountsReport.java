
package com.machuzuerp.reports;

import com.machuzuerp.controllers.datatableprocessing.jsf.InvoiceData;
import com.machuzuerp.controllers.datatableprocessing.jsf.ReceiptData;
import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.entities.Account;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class AccountsReport implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String aRecNum;
    private String accountNumber;
    private String accType;
    private String description;
    private float balance;
    private String comments;
    private java.util.Date pDate;
    
    private BigDecimal receiptSales = new BigDecimal(0);
    private BigDecimal invoiceSales = new BigDecimal(0);
    private BigDecimal totalSales = new BigDecimal(0);
    private int totalTransactions;
    private Date dateFrom;
    private Date dateTo;

    private String byType;
    int count = 9;
    private String byDescription;

    String accountRecNum = "";
    String query = "";

    private List<AccountsReport> accounts = new ArrayList<AccountsReport>();
    private List<ReceiptData> receipts = new ArrayList<ReceiptData>();
    private List<InvoiceData> invoices = new ArrayList<InvoiceData>();

    HttpServletRequest request;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public AccountsReport() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public AccountsReport(String aRecNum, String accountNumber, String accType, String description, float balance, String comments) {

        this.aRecNum = aRecNum;
        this.accountNumber = accountNumber;
        this.accType = accType;
        this.description = description;
        this.balance = balance;
        this.comments = comments;

    }
    
    public List<AccountsReport> getAllAccounts() {

        accounts.clear();
        
        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM accounts order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accountRecNum = results.getString(1);                
                accounts.add(new AccountsReport(results.getString(1),results.getString(3), results.getString(2), results.getString(4), Float.parseFloat(results.getString(5)), results.getString(6)));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return accounts;
    }
    
    public void reportAccountType(Account account) {

        accounts.clear();
        
        try { 

            connection = Systems.initConnection();
            query = "SELECT * FROM accounts where accounttype = '" + account.getName() + "' order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accountRecNum = results.getString(1);                
                accounts.add(new AccountsReport(results.getString(1),results.getString(3), results.getString(2), results.getString(4), Float.parseFloat(results.getString(5)), results.getString(6)));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

    }

    public void getDailySalesTotals() {

        try {

            totalTransactions = 0;
            setTotalSales(receiptSales.add(new BigDecimal(0)));
            setTotalTransactions(0);
            setInvoiceSales(new BigDecimal(0));
            setReceiptSales(new BigDecimal(0));

            connection = Systems.initConnection();

            query = "SELECT * FROM receipts where (receiptdate between '" + sdf.format(getDateFrom()) + "' and '" + sdf.format(getDateTo()) + "') and (goodsreturned = false or goodsreturned is null)";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            receipts.clear();
            while (results.next()) {
                totalTransactions++;
                try {
                    if (results.getBigDecimal(14).compareTo(new BigDecimal(0)) == 1) {
                        setReceiptSales(receiptSales.add(results.getBigDecimal(12)));//received
                        
                        receipts.add(new ReceiptData(results.getString(1),"",results.getString(2),results.getString(4),results.getString(5),results.getString(6),
                            results.getString(7),results.getString(8),results.getString(9),results.getString(10),"",results.getString(11),results.getString(3),
                            new BigDecimal(12),results.getBigDecimal(12),results.getBigDecimal(14),results.getBigDecimal(15),results.getBoolean(22))); 
                    } else {
                        setReceiptSales(receiptSales.add(results.getBigDecimal(13)));//received
                        
                        receipts.add(new ReceiptData(results.getString(1),"",results.getString(2),results.getString(4),results.getString(5),results.getString(6),
                            results.getString(7),results.getString(8),results.getString(9),results.getString(10),"",results.getString(11),results.getString(3),
                            new BigDecimal(12),results.getBigDecimal(13),results.getBigDecimal(14),results.getBigDecimal(15),results.getBoolean(22))); 
                    }
                        

                    
// ReceiptData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, 
//String recurring, String cid, BigDecimal totCost, BigDecimal amtPaid, BigDecimal change, BigDecimal outstandingAmount) {                                        
//public ReceiptData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid, BigDecimal totCost, BigDecimal amtPaid, BigDecimal change, BigDecimal outstandingAmount) {                    
                } catch (NullPointerException | SQLException npe) {}
            }

            query = "SELECT * FROM invoices where invoicedate between '" + sdf.format(getDateFrom()) + "' and '" + sdf.format(getDateTo()) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            invoices.clear();
            while (results.next()) {
                totalTransactions++;
                try {
                    invoiceSales = invoiceSales.add(results.getBigDecimal(19).subtract(results.getBigDecimal(20)));
                    setInvoiceSales(invoiceSales);
                    System.out.println("ISALES 000: " + results.getBigDecimal(19) + ":" + results.getBigDecimal(20));
System.out.println("ISALES: " + invoiceSales);
                    invoices.add(new InvoiceData(results.getString(1),"", results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8),
                            results.getString(9),"", results.getString(10),results.getString(11), "", results.getString(13), results.getString(15), results.getBigDecimal(19), 
                            results.getBigDecimal(20), results.getString(22), results.getString(23)));

//public InvoiceData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String email, 
            //String posAdd, String physAdd, String country, String recurring, String cid, BigDecimal total, BigDecimal outstanding, String notes, String quoteId) {                    

                } catch (NullPointerException npe) {}
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        setTotalSales(receiptSales.add(invoiceSales));
        setTotalTransactions(totalTransactions);
       // setInvoiceSales(invoiceSales);
        //setReceiptSales(receiptSales);
        
    }

    public List<AccountsReport> getAccountType() {
        return accounts;
    }
    
    public List<ReceiptData> getReceipts() {
        return receipts;
    }
    
    public List<InvoiceData> getInvoices() {
        return invoices;
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
    
    public BigDecimal getReceiptSales() {
        return receiptSales;
    }
    
    public void setReceiptSales(BigDecimal receiptSales) {
        this.receiptSales = receiptSales;
    }
    
    public BigDecimal getInvoiceSales() {
        return invoiceSales;
    }
    
    public void setInvoiceSales(BigDecimal invoiceSales) {
        this.invoiceSales = invoiceSales;
    }
    
    public BigDecimal getTotalSales() {
        return totalSales;
    }
    
    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
    
    public int getTotalTransactions() {
        return totalTransactions;
    }
    
    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
    
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateFrom() {
        return dateFrom;
    }
    
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateTo() {
        return dateTo;
    }

}
