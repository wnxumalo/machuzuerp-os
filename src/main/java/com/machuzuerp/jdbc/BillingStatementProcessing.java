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
public class BillingStatementProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;

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
    public String cid;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;
    
    String query = "";

    public BillingStatementProcessing() {

    }

    public void editBillingStatement(String uk, String func, String mod, String statementRecNo) throws SQLException {
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
                        query = "delete from statements where recnum = '" + statementRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                }

            } else {



            }

            connection.close();


        } catch (Exception sqle) {
               System.out.println(sqle);
        }

    }

    public void getBillingStatement(String iCheck) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM statement where statementCheck = '" + iCheck + "'";
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

    public ResultSet getBillingStatement() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT statementcheck FROM statement";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public String saveBillingStatement(String invDate,String cID,String cName,String cSurname,String tel,String cell,String fax,String posAdd,String physAdd,String recurring,String uk,String oid) throws SQLException {
               
        connection = Systems.initConnection();
       
        INSERT_DATA = "INSERT INTO statements(statementdate, cid, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, recurring, uk) VALUES "
                + " (?,?,?,?,?,?,?,?,?,?,?)";
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
        ps.setString(11, uk);

        ps.executeUpdate();

        connection.commit();
                            
        int i = 0;
        query = "SELECT max(recnum) FROM statements";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        String maxRec = "";
        while(results.next()) {
            maxRec = results.getString(1);
        }
        
        return maxRec;

    }
    
    public void saveBillingStatementServices(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum, String orgid) throws SQLException {
        
        connection = Systems.initConnection();

        INSERT_DATA = "INSERT INTO statementitems(userrecnum, statementCheck, description, price, quantity, amount, servid) VALUES "
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
    
    public void saveBillingStatementInventory(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum, String orgid) throws SQLException {
        
        connection = Systems.initConnection();        
        
        INSERT_DATA = "INSERT INTO statementitems(userrecnum, statementCheck, description, price, quantity, amount, servid) VALUES "
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

    

}
