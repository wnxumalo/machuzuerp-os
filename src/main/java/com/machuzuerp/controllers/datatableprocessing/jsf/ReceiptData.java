package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.jdbc.ReceiptProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.entities.Client;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.entities.ReceiptItems;
import com.machuzuerp.jdbc.InventoryProcessing;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class ReceiptData implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private String iRecNum;
    private String iCheck;
    private String iDate;
    private String cName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String posAdd;
    private String physAdd;
    private String country;
    private String recurring;
    private String cid;
    private String invoiceNum;
    private boolean goodsReturned;
    
    private Date dFrom;
    private Date dTo;
    
    private BigDecimal totServCost = new BigDecimal("0");
    private BigDecimal totInvCost = new BigDecimal("0");
    private BigDecimal totCost = new BigDecimal("0");
    private BigDecimal total = new BigDecimal("0");
    double invCost = 0.0;
    double cost = 0;
    int count = 0;
    int quantity = 0;
    
    private BigDecimal amtPaid = new BigDecimal("0");
    private BigDecimal change = new BigDecimal("0");
    BigDecimal outstandingAmount = new BigDecimal("0");
    BigDecimal taxAmount = new BigDecimal("0");
    
    String func = "";

    String receiptRecNum = "";

    private List<ReceiptData> receipts = new ArrayList<ReceiptData>();
    private List<ReceiptData> allReceipts = new ArrayList<ReceiptData>();
    private List<ReceiptData> filteredReceipts = new ArrayList<ReceiptData>();
    private ReceiptData selectedReceipt;
    private List<InventoryData> selectedInv;
    private List<ServiceData> selectedServices = new ArrayList<ServiceData>();
    private List<InventoryData> selectedInventory = new ArrayList<InventoryData>();
    private List<ReceiptItems> receiptItems = new ArrayList<ReceiptItems>();    

    ReceiptProcessing rp = new ReceiptProcessing();
    InventoryProcessing ip = new InventoryProcessing();

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";
    
    public ReceiptData() {
        
    }    

    public ReceiptData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid, BigDecimal totCost, BigDecimal amtPaid, BigDecimal change, BigDecimal outstandingAmount, boolean goodsReturned) {

        this.iRecNum = iRecNum;
        this.iCheck = iCheck;
        this.iDate = iDate;
        this.cName = cName;
        this.cSurname = cSurname;
        this.tel = tel;
        this.cell = cell;
        this.fax = fax;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.country = country;
        this.recurring = recurring;
        this.cid = cid;
        this.totCost = totCost;
        this.amtPaid = amtPaid;
        this.change = change;
        this.outstandingAmount = outstandingAmount;
        this.goodsReturned = goodsReturned;
    }

    public String searchReceipt() {

        receipts.clear();
        try {

            connection = Systems.initConnection();
            
            String from = sdf.format(dFrom);
            String to = sdf.format(dTo);

            int i = 0;
            if (!cName.equals("")) {
                query = "SELECT * FROM receipts where name like '%" + cName + "%' and receiptDate between '" + from + "' and '" + to + "'";
            } else if (!cSurname.equals("")) {
                query = "SELECT * FROM receipts where surname like '%" + cSurname + "%' and receiptDate between '" + from + "' and '" + to + "'";
            }
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                receiptRecNum = results.getString(1);                        

                receipts.add(new ReceiptData(results.getString(1),results.getString(18), results.getString(2), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10),"",results.getString(11),results.getString(3), results.getBigDecimal(12), results.getBigDecimal(13), results.getBigDecimal(14), results.getBigDecimal(15),results.getBoolean(22)));

            }

            connection.close();


        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
                
        try {
            params.get("func").equals("");

            func = "/accounting/billing/receipt/receipt-listing-1";
        } catch (NullPointerException npe) {
            func = "/accounting/billing/receipt/receipt-listing";
        }
        
        
        return func;

    }

    public String searchReceipt(Date from, Date to) {

        receipts.clear();
        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM receipts where receiptdate between '" + sdf.format(from) + "' and '" + sdf.format(to) + "'";               
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                receiptRecNum = results.getString(1);                        

                receipts.add(new ReceiptData(results.getString(1),results.getString(18), results.getString(2), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10),"",results.getString(11),results.getString(3), results.getBigDecimal(12), results.getBigDecimal(13), results.getBigDecimal(14), results.getBigDecimal(15),results.getBoolean(22)));
//public ReceiptData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid, BigDecimal totCost, BigDecimal amtPaid, BigDecimal change, BigDecimal outstandingAmount) {
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
                
        try {
            params.get("func").equals("");
            
            func = "/accounting/billing/receipt/receipt-listing-1";
        } catch (NullPointerException npe) {
            func = "/accounting/billing/receipt/receipt-listing";
        }
        
        
        return func;

    }

    public String searchClientReceipt(String idnumber) {

        receipts.clear();
        try {
System.out.println("000: " + idnumber);
            connection = Systems.initConnection();

            query = "SELECT * FROM receipts where cid = '" + idnumber + "' and outstanding > 0";               
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                System.out.println("111: " + results.getString(18));
                receipts.add(new ReceiptData(results.getString(1),results.getString(18), results.getString(2), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10),"",results.getString(11),results.getString(3), results.getBigDecimal(12), results.getBigDecimal(13), results.getBigDecimal(14), results.getBigDecimal(15),results.getBoolean(22)));
                
                query = "SELECT * FROM receiptitems where ReceiptCheck = '" + results.getString(1) + "'";               
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {
                    System.out.println("222");
                    receiptItems.add(new ReceiptItems(results1.getInt(1), results1.getString(2), results1.getString(3), results1.getString(4), results1.getBigDecimal(5), results1.getInt(6), results1.getBigDecimal(7), results1.getInt(8), results1.getInt(9)));                    
                }

            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
                
            func = "/accounting/billing/receipt/outstanding";
        
        return func;

    }

    public List<ReceiptData> getAllReceipts() {

        allReceipts.clear();

        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM receipts order by receiptcheck";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                receiptRecNum = results.getString(1);
                allReceipts.add(new ReceiptData(results.getString(1),results.getString(18), results.getString(2), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10),"",results.getString(11),results.getString(3), results.getBigDecimal(12), results.getBigDecimal(13), results.getBigDecimal(14), results.getBigDecimal(15),results.getBoolean(22)));                
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println("ERR:"+sqle);
        }

        return allReceipts;
    }

    public void filterReceipts() {

        filteredReceipts.clear();

        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM receipts where name like '%" + cName + "%' order by name";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                receiptRecNum = results.getString(1);
                filteredReceipts.add(new ReceiptData(results.getString(1),results.getString(18), results.getString(2), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10),"",results.getString(11),results.getString(3), results.getBigDecimal(12), results.getBigDecimal(13), results.getBigDecimal(14), results.getBigDecimal(15),results.getBoolean(22)));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    public List<InventoryData> getSelectedInv() {
        return selectedInv;
    }
 
    public void setSelectedInv(List<InventoryData> selectedInv) {
        this.selectedInv = selectedInv;
    }

    public List<ReceiptData> getFilteredReceipts() {

        filterReceipts();

        return filteredReceipts;
    }

    public List<ReceiptData> getReceipts() {
        return receipts;
    }

   /* public int getCount() {
        return count;
    }*/

    public void setReceipts(List<ReceiptData> receipts) {
        this.receipts = receipts;
    }
    
    public String getRecNum() {
        return iRecNum;
    }

    public void setRecNum(String iRecNum) {
        this.iRecNum = iRecNum;
    }

    public String getInvCheck() {
        return iCheck;
    }

    public void setInvCheck(String iCheck) {
        this.iCheck = iCheck;
    }

    public String getIDate() {
        return iDate;
    }

    public void setIDate(String iDate) {
        this.iDate = iDate;
    }
    
    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }       

    public String getCSurname() {
        return cSurname;
    }

    public void setCSurname(String cSurname) {
        this.cSurname = cSurname;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public String getCell() {
        return cell;
    }
    
    public void setCell(String cell) {
        this.cell = cell;
    }   

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setPosAdd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPosAdd() {
        return posAdd;
    }    

    public String getPhysAdd() {
        return physAdd;
    }      

    public void setPhysAdd(String physAdd) {
        this.physAdd = physAdd;
    }
    
    public String getCountry() {
        return country;
    }  
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    
    public Date getDFrom() {
        return dFrom;
    }

    public void setDFrom(Date dFrom) {
        this.dFrom = dFrom;
    }
    
    public Date getDTo() {
        return dTo;
    }

    public void setDTo(Date dTo) {
        this.dTo = dTo;
    }
    
     public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPosAdd() {
        return orgPosAdd;
    }

    public void setOrgPosAdd(String orgPosAdd) {
        this.orgPosAdd = orgPosAdd;
    }

    public String getOrgPhysAdd() {
        return orgPhysAdd;
    }
    
    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }
    
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public BigDecimal getAmtPaid() {
        return amtPaid;
    }

    public void setAmtPaid(BigDecimal amtPaid) {
        this.amtPaid = amtPaid;
    }
    
    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
    
    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoice(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public void setOrgPhysAdd(String orgPhysAdd) {
        this.orgPhysAdd = orgPhysAdd;
    }
    
    public List<ServiceData> getSelectedServs() {
        return selectedServices;
    }

    public void setSelectedServs(List<ServiceData> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public List<InventoryData> getSelectedInvs() {
        return selectedInventory;
    }

    public void setSelectedInvs(List<InventoryData> selectedInventory) {
        this.selectedInventory = selectedInventory;
    }
    
    public List<ReceiptItems> getReceiptItems() {
        return receiptItems;
    }

    public void setReceiptItems(List<ReceiptItems> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public ReceiptData getSelectedReceipt() {
        return selectedReceipt;
    }

    public void setSelectedReceipt(ReceiptData selectedReceipt) {
        this.selectedReceipt = selectedReceipt;
    }
    
    public boolean getGoodsReturned() {
        return goodsReturned;
    }

    public void setGoodsReturned(boolean goodsReturned) {
        this.goodsReturned = goodsReturned;
    }

    public void onRowSelect(SelectEvent event) throws IOException, SQLException {

        this.iRecNum = ((ReceiptData) event.getObject()).iRecNum;
        this.iCheck = ((ReceiptData) event.getObject()).iCheck;
        this.iDate = ((ReceiptData) event.getObject()).iDate;
        this.cName = ((ReceiptData) event.getObject()).cName;
        this.cSurname = ((ReceiptData) event.getObject()).cSurname;
        this.tel = ((ReceiptData) event.getObject()).tel;
        this.cell = ((ReceiptData) event.getObject()).cell;
        this.fax = ((ReceiptData) event.getObject()).fax;
        this.posAdd = ((ReceiptData) event.getObject()).posAdd;
        this.physAdd = ((ReceiptData) event.getObject()).physAdd;
        this.country = ((ReceiptData) event.getObject()).country;
        this.recurring = ((ReceiptData) event.getObject()).recurring;
        this.cid = ((ReceiptData) event.getObject()).cid;
        this.totCost = ((ReceiptData) event.getObject()).totCost;
        this.outstandingAmount = ((ReceiptData) event.getObject()).outstandingAmount;
        this.amtPaid = ((ReceiptData) event.getObject()).amtPaid;
        this.change = ((ReceiptData) event.getObject()).change;
        this.goodsReturned = ((ReceiptData) event.getObject()).goodsReturned;
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();    

        String param = "recnum=" + iRecNum + "&iDate=" + iDate + "&cName=" + cName + 
                "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&posAdd=" + posAdd + "&physAdd=" + physAdd + 
                "&country=" + country + "&recurring=" + recurring + "&clientid=" + cid + "&totCost=" + totCost + "&outstandingAmount=" + outstandingAmount + "&amtPaid=" + amtPaid + 
                        "&change=" + change;

        connection = Systems.initConnection();
            
        query = "SELECT * FROM receiptitems where ReceiptCheck = '" + iRecNum + "'";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        receiptItems.clear();
        while (results.next()) {

            receiptItems.add(new ReceiptItems(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getBigDecimal(5), results.getInt(6), results.getBigDecimal(7), results.getInt(8), results.getInt(9)));

            setTotal(total.add(results.getBigDecimal(7)));
            
            System.out.println("111: " + total + ":" + results.getBigDecimal(7));
        }

        this.taxAmount = getTotal().divide(new BigDecimal(100));
        setTaxAmount(taxAmount.multiply(new BigDecimal(15)));        

        setTotCost(getTotal().add(taxAmount));
        /*ResultSet receiptItems = getReceiptItems(this.iRecNum);

        while (receiptItems.next()) {
        
            this.taxAmount = receiptItems.getBigDecimal(7).divide(new BigDecimal(100));
            this.taxAmount = taxAmount.multiply(new BigDecimal(15));
        
        }*/

        UserProcessing up = new UserProcessing();
        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }   

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);            
        }                

        try {
            
            params.get("func").equals("");

            setTotals(iRecNum);

            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/receipt-preview-1.xhtml?" + param);            
        } catch (NullPointerException npe) {            
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/receipt-preview.xhtml?" + param);
        }                        

    }

    public void viewReceipt() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/receipts/view-receipt.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/receipt/view-receipt.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ReceiptData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void setTotals(String recNum) throws SQLException {

        this.totCost = rp.getTotCost(recNum);

        String[] vals = rp.getTotals(recNum).split(",");

        totCost = new BigDecimal(vals[0]);
        amtPaid = new BigDecimal(vals[1]);
        outstandingAmount = new BigDecimal(vals[2]);
        change = new BigDecimal(vals[3]);

    }

    // REPORTS
    public void reportAllReceipts(Client customer) throws SQLException {

        receipts.clear();

        connection = Systems.initConnection();
        query = "SELECT * FROM receipts where cid = '" + customer.getId() + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            receipts.add(new ReceiptData(results.getString(1),results.getString(18), results.getString(2), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10),"",results.getString(11),results.getString(3), results.getBigDecimal(12), results.getBigDecimal(13), results.getBigDecimal(14), results.getBigDecimal(15),results.getBoolean(22)));
        }

    }
    
    public List<ReceiptData> getRepAllReceipts() {
        return receipts;
    }
    
    public ResultSet getReceiptEmailClients() throws SQLException {

        receipts.clear();

        connection = Systems.initConnection();
        query = "SELECT recnum, receiptdate, cid, amountdue, received, changeissued, outstanding, name, email FROM receipts where cashPayment = 'Credit' and outstanding > 0";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public void sendCreditEmails() {
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.machuzu.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("info@machuzu.com", "zXZK0l_A@N65");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@machuzu.com"));
            
            results = getReceiptEmailClients();
            
            while (results.next()) {
                
                System.out.println("111: " + results.getString(9) + ":" + results.getString(8) + ":" + results.getString(1) + ":" + results.getString(8) + ":" + results.getString(4));
                
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(results.getString(9)));
                message.setSubject("Outstanding Bill");
                message.setText("Dear " + results.getString(8) + ",\n\nYou have an outstanding debt for Receipt # " + results.getString(1) + ".\n\nThe amount you owe is " + results.getString(8) + " and the total for the Receipt was " + results.getString(4) + "\n\nFor more information please view our website @ http://www.machuzu.com \n\n Feel free to contact me directly with any queries at wandile@machuzu.com or 00268 76242698 \n\nThank you!");

                Transport.send(message);
            }

            System.out.println("Done");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Emails Sent Successfully."));

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }
    }
    
    public ResultSet getReceiptItems(String receiptNumber) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM receiptitems where ReceiptCheck = '" + receiptNumber + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

       return results;
    }


}
