/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.Systems;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 *
 * @author Administrator
 */
public class ReceiptProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
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
    public String posAdd;
    public String physAdd;
    public String country;
    public String recurring;
    public BigDecimal outstandingAmount;
    public String cid;
    public int atHand = 0;
    
    String query = "";
    
    BigDecimal totCost = new BigDecimal("0");
    BigDecimal due = new BigDecimal("0");
    BigDecimal paid = new BigDecimal("0");
    BigDecimal outstanding = new BigDecimal("0");
    BigDecimal change = new BigDecimal("0");
    
    InventoryProcessing ip = new InventoryProcessing();

    public ReceiptProcessing() {

    }

    public void editReceipt(String uk, String func, String mod, String receiptRecNo) throws SQLException {
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
                        query = "delete from receipts where recnum = '" + receiptRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                }

            } else {
                
                connection.setAutoCommit(false);

                INSERT_DATA = "INSERT INTO receipts(uk, receiptcheck, receiptdate, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, country, recurring, cid) VALUES "
                        + " (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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

                ps.executeUpdate();

                connection.commit();

            }

            connection.close();

        } catch (Exception sqle) {
               System.out.println(sqle);
        }

    }

    public void getReceipt(String iCheck) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM receipts where receiptCheck = '" + iCheck + "'";
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

        }

    }

    public ResultSet getReceipt() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT receiptcheck FROM receipt";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

    public BigDecimal saveReceipt(String invDate,String cID,String cName,String cSurname,String tel,String cell,String fax,String posAdd,String physAdd,String recurring, BigDecimal amtDue, BigDecimal taxAmount, BigDecimal received, BigDecimal change, String uk,String oid, String email, String cashPayment) throws SQLException {
//this.change = rp.saveReceipt(sdf.format(new Date()), client.split(",")[0], client.split(",")[1], "", tel, cell, fax, posAdd, physAdd, "", totCost, taxAmount, this.getAmountPaid(), session.getAttribute("uk").toString(), "", email, paymentMethod);        

        String vals[] = invDate.split(",");
        String iRecNum = "";
        if (vals.length>1){
            invDate = vals[0];
            iRecNum = vals[1];
        }
       /* System.out.println("RECEVIED: " + received);
        BigDecimal changeAmt = new BigDecimal("0");
            */
               BigDecimal outstandingAmt = amtDue.subtract(received);

        if ((outstandingAmt.compareTo(new BigDecimal("0"))) < 0) {
            outstandingAmt = new BigDecimal("0");
        }        
        
        /*
        if ((amtDue.compareTo(received)) < 0) {
            changeAmt = received.subtract(amtDue);
        } else {
            changeAmt = new BigDecimal("0");
        }
*/
        connection = Systems.initConnection();  
        
        connection.setAutoCommit(false);

        if (iRecNum.length()>0) {

            INSERT_DATA = "INSERT INTO receipts(receiptdate, cid, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, recurring, AmountDue,received, changeIssued, outstanding, uk, InvoiceNumber, email, cashPayment, taxAmount) VALUES "
                    + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, invDate);
            ps.setString(2, cID);
            ps.setString(3, cName);
            ps.setString(4, cSurname);
            ps.setString(5, tel);
            ps.setString(6, cell);
            ps.setString(7, fax);
            ps.setString(8, posAdd);
            ps.setString(9, physAdd);
            ps.setString(10, recurring);
            ps.setBigDecimal(11, amtDue);
            ps.setBigDecimal(12, received);
            ps.setBigDecimal(13, change);
            ps.setBigDecimal(14, outstandingAmt);
            ps.setString(15, uk);
            ps.setString(16, iRecNum);
            ps.setString(17, email);
            ps.setString(18, cashPayment);
            ps.setBigDecimal(19, taxAmount);

            ps.executeUpdate();

            connection.commit();

            int i = 0;
            query = "SELECT max(recnum) FROM receipts";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while(results.next()) {

                INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, credit, transactiondate, comments) VALUES "
                        + " (?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, uk);
                ps.setString(2, "2035");
                ps.setString(3, "Stakeholder deposits");
                ps.setString(4, cID + "/" + cName + " " + cSurname);
                ps.setBigDecimal(5, amtDue);
                ps.setString(6, invDate);
                ps.setString(7, "Receipt # " + results.getString(1));

                ps.executeUpdate();

                connection.commit();

                INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, transactiondate, comments) VALUES "
                        + " (?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, uk);
                ps.setString(2, "1210");
                ps.setString(3, "A/REC TRADE");
                ps.setString(4, "Receivables");
                ps.setBigDecimal(5, amtDue);
                ps.setString(6, invDate);
                ps.setString(7, "Receipt # " + results.getString(1));       

                ps.executeUpdate();

                connection.commit();
            }
            
        } else {
           
            INSERT_DATA = "INSERT INTO receipts(receiptdate, cid, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, recurring, AmountDue,received, changeIssued, outstanding, uk, InvoiceNumber, email, cashPayment, taxAmount) VALUES "
                    + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, invDate);
            ps.setString(2, cID);
            ps.setString(3, cName);
            ps.setString(4, cSurname);
            ps.setString(5, tel);
            ps.setString(6, cell);
            ps.setString(7, fax);
            ps.setString(8, posAdd);
            ps.setString(9, physAdd);
            ps.setString(10, recurring);
            ps.setBigDecimal(11, amtDue);
            ps.setBigDecimal(12, received);
            ps.setBigDecimal(13, change);
            ps.setBigDecimal(14, outstandingAmt);
            ps.setString(15, uk);
            ps.setString(16, "0");
            ps.setString(17, email);
            ps.setString(18, cashPayment);
            ps.setBigDecimal(19, taxAmount);

            ps.executeUpdate();

            connection.commit();

            int i = 0;
            query = "SELECT max(recnum) FROM receipts";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while(results.next()) {

                INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, credit, transactiondate, comments) VALUES "
                        + " (?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, uk);
                ps.setString(2, "2035");
                ps.setString(3, "Stakeholder deposits");
                ps.setString(4, cID + "/" + cName + " " + cSurname);
                ps.setBigDecimal(5, amtDue);
                ps.setString(6, invDate);
                ps.setString(7, "Receipt # " + results.getString(1));

                ps.executeUpdate();

                connection.commit();

                INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, transactiondate, comments) VALUES "
                        + " (?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, uk);
                ps.setString(2, "1210");
                ps.setString(3, "A/REC TRADE");
                ps.setString(4, "Receivables");
                ps.setBigDecimal(5, amtDue);
                ps.setString(6, invDate);
                ps.setString(7, "Receipt # " + results.getString(1));       

                ps.executeUpdate();

                connection.commit();
            }
        }

        setOutstandingAmt(outstandingAmt);

        return change;

    }

    public void setOutstandingAmt(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }       

    public int getMaxRec() throws SQLException{

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT max(recnum) FROM receipts";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        int maxRec = 1;
        while(results.next()) {
            try {
                maxRec = Integer.parseInt(results.getString(1));
            } catch (NumberFormatException nfe) {}
        }

        return maxRec;
    }
    
    public void saveReceiptServices(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum) throws SQLException {
        
        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO receiptitems(userrecnum, receiptCheck, description, price, quantity, amount, servid) VALUES "
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

    public void saveReceiptInventory(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum, int atHand) throws SQLException {
        try {
        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);
               
        INSERT_DATA = "INSERT INTO receiptitems(userrecnum, receiptCheck, description, price, quantity, amount, invid) VALUES "
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
        //ip.updateInventory(recNum, (atHand - qty));

        int added = 0;
        statement = connection.createStatement();
        query = "UPDATE inventory SET atHand  = '" + (atHand - qty) + "' where INVENTORY_ID = '" + recNum + "'";
        added = statement.executeUpdate(query);
        statement.close();
        connection.commit();

        if (atHand > 0)
            this.atHand = (atHand - qty);

        }catch (Exception e) {
            System.out.println("RECEIPT: " + e);
        }
        
    }
    
    public BigDecimal getTotCost(String iCheck) throws SQLException {
        
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM receiptitems where receiptCheck = '" + iCheck + "'";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            totCost = totCost.add(results.getBigDecimal(7));
        }
        
        return totCost;

    }

    public String getTotals(String iCheck) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM receipts where recnum = '" + iCheck + "'";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            due = due.add(results.getBigDecimal(12));
            paid = paid.add(results.getBigDecimal(13));
            outstanding = outstanding.add(results.getBigDecimal(15));
            change = change.add(results.getBigDecimal(14));
        }

        String vals = ""+due + ',' + ""+paid + ',' + ""+outstanding + ',' + ""+change;
        
        return vals;

    }
    
    public void setGoodsReturned(int id) throws SQLException {

        connection = Systems.initConnection();

        int added = 0;
        statement = connection.createStatement();
        query = "UPDATE receipts SET goodsreturned  = true where recnum = '" + id + "'";
        added = statement.executeUpdate(query);
        statement.close();

    }

}