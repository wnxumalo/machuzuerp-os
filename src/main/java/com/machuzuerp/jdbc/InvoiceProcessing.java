/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.datatableprocessing.jsf.InvoiceData;
import com.machuzuerp.controllers.jsf.Systems;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class InvoiceProcessing {

    Connection connection;
    Statement statement;
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    public String iRecNum;
    public String iCheck;
    public String iDate;
    public String cName;
    public String cSurname;
    public String tel;
    public String cell;
    public String fax;
    public String email;
    public String posAdd;
    public String physAdd;
    public String country;
    public String recurring;
    public String cid;
    public String orderNumber;
    public String vatNum;
    public String notes;
    
    private String change;
    
    String query = "";
    
    ResultSet results;
    ResultSet clients;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private List<InvoiceData> filteredInvoices = new ArrayList<InvoiceData>();

    public InvoiceProcessing() {

    }

    public void editInvoice(String uk, String func, String mod, String invoiceRecNo) throws SQLException {
        try {

            String cDate = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cDate = sdf.format(new java.util.Date());

            connection = Systems.initConnection();

            if (!func.equals("")) {
                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from invoices where recnum = '" + invoiceRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                }

            } else {

                connection.setAutoCommit(false);

                INSERT_DATA = "INSERT INTO invoices(uk, invoicecheck, invoicedate, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, country, recurring, cid, ordernum, vatnum, notes) VALUES "
                        + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, uk);
                ps.setString(2, iCheck);
                ps.setString(3, iDate);
                ps.setString(4, cName);
                ps.setString(5, cSurname);
                ps.setString(6, tel);
                ps.setString(7, cell);
                ps.setString(8, fax);
                ps.setString(9, posAdd);
                ps.setString(10, physAdd);
                ps.setString(11, country);
                ps.setString(12, recurring);
                ps.setString(13, cid);
                ps.setString(14, orderNumber);
                ps.setString(15, vatNum);
                ps.setString(16, notes);

                ps.executeUpdate();

                connection.commit();
            }

        } catch (Exception sqle) {
               System.out.println(sqle);
        }

    }

    public void getInvoice(String iCheck) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM invoices where invoiceCheck = '" + iCheck + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            iDate = results.getString(4);
            cName = results.getString(5);
            cSurname = results.getString(6);
            tel = results.getString(7);
            cell = results.getString(8);
            fax = results.getString(9);
            posAdd = results.getString(10);
            physAdd = results.getString(11);
            country = results.getString(12);
            recurring = results.getString(13);
            cid = results.getString(15);
            notes = results.getString(16);

        }

    }

    public ResultSet getInvoice() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT invoicecheck FROM invoice";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

    public String saveCreditNote(Date invDate,String cID,String cName,String cSurname,String tel,String cell,String fax,String posAdd,String physAdd,String uk, String vatNum, String notes, String total, String outstanding, String invId, String amount) throws SQLException {

        connection = Systems.initConnection();

        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO creditnotes(notedate, cid, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, uk, vatnum, amount, notes, invoicetotal, outstanding, invoiceId) VALUES "
                + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(INSERT_DATA);
        ps.setString(1, sdf.format(invDate));
        ps.setString(2, cID);
        ps.setString(3, cName);
        ps.setString(4, cSurname);
        ps.setString(5, tel);
        ps.setString(6, cell);
        ps.setString(7, fax);
        ps.setString(8, posAdd);
        ps.setString(9, physAdd);
        ps.setString(10, uk);
        ps.setString(11, vatNum);
        ps.setString(12, amount);
        ps.setString(13, notes);
        ps.setString(14, total);
        ps.setString(15, outstanding);
        ps.setString(16, invId);

        ps.executeUpdate();

        connection.commit();
                    
        statement = connection.createStatement();
        query = "UPDATE invoices SET outstanding  = '" + (Double.parseDouble(outstanding) + Double.parseDouble(amount)) + "' Where recnum = '" + invId + "'";
        int added = statement.executeUpdate(query);
        statement.close();
                        
        int i = 0;
        query = "SELECT max(recnum) FROM creditnotes";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        String maxRec = "";
        while(results.next()) {
            maxRec = results.getString(1);
        }

        return maxRec;

    }

    public String saveEditCreditNote(String cnId, Date noteDate,String cID,String cName,String cSurname,String tel,String cell,String fax,String posAdd,String physAdd,String uk, String vatNum, String notes, String total, String outstanding, String invId, String amount) throws SQLException {

        connection = Systems.initConnection();

        statement = connection.createStatement();
        query = "UPDATE creditnotes SET notedate  = '" + sdf.format(noteDate) + "' Where recnum = '" + cnId + "'";
        int added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET cid  = '" + cID + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET name  = '" + cName + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET surname  = '" + cSurname + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET telephone  = '" + tel + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET cellphone  = '" + cell + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET fax  = '" + fax + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET postaladdress  = '" + posAdd + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();                                    
    
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET physicaladdress  = '" + physAdd + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET vatnum  = '" + vatNum + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET invoiceId  = '" + invId + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET amount  = '" + amount + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET notes  = '" + notes + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();        
        
        statement = connection.createStatement();
        query = "UPDATE creditnotes SET invoicetotal  = '" + total + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE invoices SET outstanding  = '" + (Double.parseDouble(outstanding) + Double.parseDouble(amount)) + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
                        
        int i = 0;
        query = "SELECT max(recnum) FROM creditnotes";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        String maxRec = "";
        while(results.next()) {
            maxRec = results.getString(1);
        }

        return maxRec;

    }    
    
    public String saveInvoice(String invDate, String recnum, String cID,String cName,String cSurname,String tel,String cell,String email,String posAdd,String physAdd,String recurring,String uk,String oid, String orderNumber, String vatNum, String notes, String quoteId, String projectId) throws SQLException {
//String maxRec = ip.saveInvoice(invDate, customer.getRecnum(), customer.getVatNum(), customer.getName(), customer.getSurname(), customer.getTelephone(), customer.getCellphone(), customer.getEmail(), customer.getPostalAddress(), customer.getPhysicalAddress(), recurring, request.getSession().getAttribute("uk").toString(), "", orderNumber, vatNum, notes, quoteId,"");
        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);
     
        INSERT_DATA = "INSERT INTO invoices(invoicedate, cid, name, surname, telephone, cellphone, email, postaladdress, physicaladdress, recurring, uk, ordernum, vatnum, notes, quoteid, projectid) VALUES  "
                + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(INSERT_DATA);
        ps.setString(1, invDate);
        ps.setString(2, recnum);
        ps.setString(3, cName);
        ps.setString(4, cSurname);        
        ps.setString(5, tel);
        ps.setString(6, cell);
        ps.setString(7, email);
        ps.setString(8, posAdd);
        ps.setString(9, physAdd);
        ps.setString(10, recurring);
        ps.setString(11, uk);
        ps.setString(12, orderNumber);
        ps.setString(13, cID);
        ps.setString(14, notes);
        ps.setString(15, quoteId);
        //ps.setString(16, projectId);
        ps.setString(16, "0");

        ps.executeUpdate();

        connection.commit();
        
        int i = 0;
        query = "SELECT max(recnum) FROM invoices";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        String maxRec = "";
        while(results.next()) {
            maxRec = results.getString(1);
        }
        
        return maxRec;

    }
    
    public void saveInvoiceServices(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum, String orgid) throws SQLException {

        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);
     
        INSERT_DATA = "INSERT INTO invoiceitems(userrecnum, invoiceCheck, description, price, quantity, amount, servid) VALUES "
                + " (?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(INSERT_DATA);
        ps.setString(1, uk);
        ps.setString(2, maxRec);
        ps.setString(3, description);
        ps.setBigDecimal(4, cost);        
        ps.setInt(5, qty);
        ps.setBigDecimal(6, amount);
        ps.setString(7, recNum);

        ps.executeUpdate();

        connection.commit();
        
    }
    
    public void saveInvoiceInventory(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum, String orgid) throws SQLException {
        
        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO invoiceitems(userrecnum, invoiceCheck, description, price, quantity, amount, invid) VALUES "
                + " (?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(INSERT_DATA);
        ps.setString(1, uk);
        ps.setString(2, maxRec);
        ps.setString(3, description);
        ps.setBigDecimal(4, cost);        
        ps.setInt(5, qty);
        ps.setBigDecimal(6, amount);
        ps.setString(7, recNum);

        ps.executeUpdate();

        connection.commit();
        
    }
    
    public void updateInvoice(String recnum, BigDecimal totCost) throws SQLException {
 
        connection = Systems.initConnection();

        int added = 0;
        statement = connection.createStatement();
        String query = "UPDATE invoices SET total  = '" + totCost + "'Where RecNum = '" + recnum + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        added = 0;
        statement = connection.createStatement();
        query = "UPDATE invoices SET outstanding  = '" + totCost + "'Where RecNum = '" + recnum + "'";
        added = statement.executeUpdate(query);
        statement.close();

    }
    
    public void updatePayment(String recnum, BigDecimal payment, BigDecimal balance, String uk, String payDate, String oid, String cid, BigDecimal outstanding, BigDecimal change, BigDecimal taxAmount) throws SQLException {

        connection = Systems.initConnection();

        int added = 0;               
        statement = connection.createStatement();
        query = "UPDATE invoices SET outstanding  = '" + balance + "'Where RecNum = '" + recnum + "'";
        added = statement.executeUpdate(query);
        statement.close();

        String description = "PAYMENT " + payDate;

        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO invoiceitems(userrecnum, invoiceCheck, description, amount) VALUES "
                + " (?,?,?,?)";
        ps = connection.prepareStatement(INSERT_DATA);
        ps.setString(1, uk);
        ps.setString(2, recnum);
        ps.setString(3, description);
        ps.setString(4, "-" +payment);        

        ps.executeUpdate();

        connection.commit();
        
        query = "SELECT cid FROM invoices where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        ReceiptProcessing rp = new ReceiptProcessing();
        
        while(results.next()) {

            query = "SELECT * FROM clients where recnum = '" + results.getString(1) + "'";
            statement = connection.createStatement();
            clients = statement.executeQuery(query);
             
            while(clients.next()) {
                
                //change = rp.saveReceipt(payDate +","+recnum, clients.getString(17), clients.getString(5), clients.getString(6), clients.getString(8), clients.getString(9), clients.getString(10), clients.getString(11), clients.getString(12), "", outstanding, taxAmount, payment, uk, oid, email, "");
            }
                    
        }

    }
    
    public List<InvoiceData> findInvoice(Date from, Date to) throws SQLException {

        filteredInvoices.clear();

        connection = Systems.initConnection();
        
        String data = "";
        
        query = "SELECT * FROM invoices where InvoiceDate between '" + sdf.format(from) + "' and '" + sdf.format(to) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);       

        while(results.next()) {                
            filteredInvoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
        }

        return filteredInvoices;        
    }

    public String saveDebitNote(String poId, String supplierId, String sName,  String tel, String cell, String fax, String posAdd, String physAdd, String vatNum, Date noteDate, BigDecimal amount, String notes, String uk) throws SQLException {

        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO debitnotes(piid, sid, name, telephone, cellphone, fax, postaladdress, physicaladdress, vatnum,notedate, amount, notes, uk) VALUES "
                + " (?,?,?,?,?,?,?,?,?,?,?,?.?)";
        ps = connection.prepareStatement(INSERT_DATA);
        ps.setString(1, poId);
        ps.setString(2, supplierId);
        ps.setString(3, sName);
        ps.setString(4, tel);        
        ps.setString(5, cell);
        ps.setString(6, fax);
        ps.setString(7, posAdd);
        ps.setString(8, physAdd);
        ps.setString(9, vatNum);
        ps.setString(10, sdf.format(noteDate));
        ps.setBigDecimal(11, amount);
        ps.setString(12, notes);
        ps.setString(13, uk);

        ps.executeUpdate();

        connection.commit();
        
        return "";

    }

    public String saveEditDebitNote(String dnId, String poId, String supplierId, String sName,  String tel, String cell, String fax, String posAdd, String physAdd, String vatNum, Date noteDate, BigDecimal amount, String notes, String uk) throws SQLException {

        connection = Systems.initConnection();

        statement = connection.createStatement();
        query = "UPDATE debitnotes SET piid  = '" + poId + "' Where recnum = '" + dnId + "'";
        int added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET sid  = '" + supplierId + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET name  = '" + sName + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET telephone  = '" + tel + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();       
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET cellphone  = '" + cell + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET fax  = '" + fax + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET postaladdress  = '" + posAdd + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();                                    
    
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET physicaladdress  = '" + physAdd + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET vatnum  = '" + vatNum + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET notedate  = '" + sdf.format(noteDate) + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET amount  = '" + amount + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE debitnotes SET notes  = '" + notes + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();        
        
        /*statement = connection.createStatement();
        query = "UPDATE debitnotes SET invoicetotal  = '" + total + "' Where recnum = '" + dnId + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        statement = connection.createStatement();
        query = "UPDATE invoices SET outstanding  = '" + (Double.parseDouble(outstanding) + Double.parseDouble(amount)) + "' Where recnum = '" + cnId + "'";
        added = statement.executeUpdate(query);
        statement.close();*/
                        
        int i = 0;
        query = "SELECT max(recnum) FROM creditnotes";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        String maxRec = "";
        while(results.next()) {
            maxRec = results.getString(1);
        }

        return maxRec;

    }

}