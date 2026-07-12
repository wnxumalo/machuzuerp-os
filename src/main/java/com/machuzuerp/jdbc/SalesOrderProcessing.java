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

public class SalesOrderProcessing {

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
    public String notes;
    public String cid;
    
    String query = "";

    public SalesOrderProcessing() {

    }

    public void editSalesOrder(String uk, String func, String mod, String salesorderRecNo) throws SQLException {
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
                        query = "delete from salesorders where recnum = '" + salesorderRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                }

            } else {
                
                connection.setAutoCommit(false);

                INSERT_DATA = "INSERT INTO salesorders(uk, salesordercheck, salesorderdate, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, country, notes, cid)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                ps.setString(12, notes);
                ps.setString(13, cid);

                ps.executeUpdate();

                connection.commit();

            }

            connection.close();           

        } catch (Exception sqle) {
               System.out.println(sqle);
        }

    }

    public void getSalesOrder(String iCheck) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM salesorders where salesorderCheck = '" + iCheck + "'";
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
            notes = results.getString(13);
            cid = results.getString(15);

        }

    }

    public ResultSet getSalesOrder() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT salesordercheck FROM salesorders";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public String saveSalesOrder(String invDate,String cID,String cName,String cSurname,String tel,String cell,String fax,String posAdd,String physAdd,String notes,String uk) throws SQLException {

        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO salesorders(salesorderdate, cid, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, notes, uk)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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
        ps.setString(10, notes);
        ps.setString(11, uk);

        ps.executeUpdate();

        connection.commit();
        
        int i = 0;
        query = "SELECT max(recnum) FROM salesorders";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        String maxRec = "";
        while(results.next()) {
            maxRec = results.getString(1);
        }
        
        return maxRec;

    }
    
    public void saveSalesOrderServices(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum) throws SQLException {

        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO salesorderitems(uk, salesorderCheck, description, price, quantity, amount, servid)"
                + " VALUES (?,?,?,?,?,?,?)";
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
    
    public void saveSalesOrderInventory(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum) throws SQLException {
 
        connection = Systems.initConnection();
     
        connection.setAutoCommit(false);

        INSERT_DATA = "INSERT INTO salesorderitems(uk, salesorderCheck, description, price, quantity, amount, invid)"
                + " VALUES (?,?,?,?,?,?,?)";
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
