/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.SupplierControllerOld;
import com.machuzuerp.controllers.jsf.Systems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SupplierProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    String INSERT_DATA = "";
    PreparedStatement ps = null;
    
    SupplierControllerOld selectedSupplier;

    boolean edit = false;
    boolean proceed = true;

    public String supplierRecNum;
    public String supplierName;
    public String telephone;
    public String cellphone;
    public String fax;
    public String postalAddress;
    public String physicalAddress;
    public String country;
    public String description;
    public String vatNum;
    public String email;

    String query = "";
    String msg = "";

    public SupplierProcessing() {

    }

    public String editSupplier(String uk, String func, String mod, String supplierRecNo) {
        try {

            connection = Systems.initConnection();

            if (!func.equals("")) {
                edit = true;
                if (func.equals("delete")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from Supplier where recnum = '" + supplierRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();
                        
                        msg = "Deleted";                 

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET Name  = '" + supplierName + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET Telephone  = '" + telephone + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET Cellphone  = '" + cellphone + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET Fax  = '" + fax + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET PostalAddress  = '" + postalAddress + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET PhysicalAddress  = '" + physicalAddress + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET Country  = '" + country + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET Description  = '" + description + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET VatNum  = '" + vatNum + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Supplier SET Email  = '" + email + "'Where recnum = '" + supplierRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    msg = "Updated";

                }

            } else {

                int i = 0;
                query = "SELECT * FROM Supplier where VatNum = '" + vatNum + "' or email = '" + email + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {                       
                    if (!results.getString(3).equals("")) {
                        proceed = false;
                        supplierRecNum = results.getString(1);
                    }
                }

                try {

                    if ((proceed == true)) {
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO Supplier(uk, Name, Telephone, Cellphone, Fax, PostalAddress, PhysicalAddress, Country, Description, VatNum, Email)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, uk);
                        ps.setString(2, supplierName);
                        ps.setString(3, telephone);
                        ps.setString(4, cellphone);
                        ps.setString(5, fax);
                        ps.setString(6, postalAddress);
                        ps.setString(7, physicalAddress);
                        ps.setString(8, country);
                        ps.setString(9, description);
                        ps.setString(10, vatNum);
                        ps.setString(11, email);

                        ps.executeUpdate();

                        connection.commit();

                        msg = "Saved";

                    } else {
                        System.out.println("222");
                        msg = "Updated";

                    }

                } catch (SQLException np) {
                    System.out.println(np);
                }

            }

        } catch (Exception sqle) {
               System.out.println(sqle);
        }

        return msg;

    }

    public void getSupplier(String recnum) throws SQLException {

        connection = Systems.connDatabase();

        int i = 0;
        query = "SELECT * FROM Supplier where SUPPLIER_ID = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            supplierRecNum = results.getString(1);
            supplierName = results.getString(11);
            telephone = results.getString(6);
            //cellphone = results.getString(6);
            //fax = results.getString(5);
            postalAddress = results.getString(17);
            physicalAddress = results.getString(16);
            country = results.getString(8);
            description = results.getString(18);
            vatNum = results.getString(22);
            email = results.getString(4);                       

        }

    }

    public ResultSet getSupplier() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT SUPPLIER_ID,name FROM Supplier order by name";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public ResultSet getSupplierRes(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Supplier where SUPPLIER_ID = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public SupplierControllerOld getSupp(String recnum) throws SQLException {
        
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Supplier where SUPPLIER_ID = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        selectedSupplier = new SupplierControllerOld();
        
        while (results.next()) {
            selectedSupplier.setRecNum(results.getString(1));
            selectedSupplier.setSupplierName(results.getString(2));            
            selectedSupplier.setTelephone(results.getString(3));
            selectedSupplier.setCellphone(results.getString(4));
            selectedSupplier.setFax(results.getString(5));
            selectedSupplier.setPostalAddress(results.getString(6));
            selectedSupplier.setPhysicalAddress(results.getString(7));
            selectedSupplier.setCountry(results.getString(8));
            selectedSupplier.setDescription(results.getString(9));
            selectedSupplier.setVatNum(results.getString(10));
            selectedSupplier.setEmail(results.getString(11));
        }
        
        return selectedSupplier;
    }

    public String getSupplierObject(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Supplier where SUPPLIER_ID = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        String ret = "";
        while (results.next()) {

            ret = results.getString(2) + "~" + results.getString(3) + "~" + results.getString(4) + "~" + results.getString(5) + "~" + results.getString(6) + "~" + 
            results.getString(7) + "~" + results.getString(8) + "~" + results.getString(10);

        }
        
        return ret;
    }
}
