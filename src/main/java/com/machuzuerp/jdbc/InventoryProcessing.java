/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.Serializable;
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
public class InventoryProcessing implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    public String inventoryRecNum = "";
    public String code = "";
    public String description = "";
    public Long supplierId = new Long(0);
    public String supplier = "";
    public String clientId = "0";
    public String client = "";
    public float cost = 0;
    public float sellingPrice = 0;
    public int reorderLevel = 0;
    public float markup = 0;
    public float atHand = 0;
    public float size = 0;
    public String color = "";

    String query = "";
    
    String msg = "";
    
    public InventoryProcessing() {

    }

    public String editInventory(String uk, String func, String mod, String inventoryRecNo) throws SQLException {
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
                        query = "delete from Inventory where INVENTORY_ID = '" + inventoryRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();
                        
                        msg = "Deleted";

                    } else {

                    }

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET service_no  = '" + code + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET description  = '" + description + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    /*added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE inventory SET supplierId  = '" + supplierId + "'Where recnum = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    added = 0;

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE inventory SET supplier  = '" + supplier + "'Where recnum = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    added = 0;*/

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET cost  = '" + cost + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET selling_price  = '" + sellingPrice + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET reorder  = '" + reorderLevel + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET markup  = '" + markup + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET atHand  = '" + atHand + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET size  = '" + size + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET color  = '" + color + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE Inventory SET lastupdate  = '" + cDate + "'Where INVENTORY_ID = '" + inventoryRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    msg = "Updated";
                }

            } else {

                int i = 0;
                query = "SELECT * FROM Inventory where service_no = '" + code + "' and description = '" + description + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {                       
                    if (!results.getString(3).equals("")) {
                        proceed = false;
                        inventoryRecNum = results.getString(1);
                    }
                }

                try {                       

                    if ((proceed == true)) {
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO Inventory(uk, service_no, description, supplierId, supplier, clientId, clientname, cost, selling_price, reorder, markup, atHand, size, color) VALUES "
                                + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, uk);
                        ps.setString(2, code);
                        ps.setString(3, description);
                        ps.setLong(4, supplierId);
                        ps.setString(5, supplier);
                        ps.setString(6, clientId);
                        ps.setString(7, client);
                        ps.setFloat(8, cost);
                        ps.setFloat(9, sellingPrice);
                        ps.setFloat(10, reorderLevel);
                        ps.setFloat(11, markup);
                        ps.setFloat(12, atHand);
                        ps.setFloat(13, size);
                        ps.setString(14, color);

                        ps.executeUpdate();

                        connection.commit();
                        
                        msg = "Saved";

                    } else {
                        msg = "Exists";
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

    public void getInventory(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Inventory where INVENTORY_ID = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            code = results.getString(2);
            description = results.getString(3);
            supplier = results.getString(5);
            client = results.getString(21);
            cost = Float.parseFloat(results.getString(7));
            sellingPrice = Float.parseFloat(results.getString(8));
            reorderLevel = Integer.parseInt(results.getString(9));
            markup = Float.parseFloat(results.getString(11));
            atHand = Float.parseFloat(results.getString(12));
            size = Float.parseFloat(results.getString(19));
            color = results.getString(20);

        }

    }

    public ResultSet getInventory() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT INVENTORY_ID,name,surname FROM inventory order by name, surname";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        return results;
    }
    
    public void updateInventory(String recnum, int atHand) throws SQLException {
        
        connection = Systems.initConnection();
        System.out.println("UPDATE INV ADD: " + atHand + ":" + recnum);
        int added = 0;
        statement = connection.createStatement();
        query = "UPDATE Inventory SET atHand  = '" + atHand + "' where INVENTORY_ID = '" + recnum + "'";
        added = statement.executeUpdate(query);
        statement.close();

    }    
    
    public void getInventoryItems(String receiptNumber) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM receiptitems where invid = '" + receiptNumber + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            code = results.getString(2);
            description = results.getString(3);
            supplier = results.getString(5);
            client = results.getString(21);
            cost = Float.parseFloat(results.getString(7));
            sellingPrice = Float.parseFloat(results.getString(8));
            reorderLevel = Integer.parseInt(results.getString(9));
            markup = Float.parseFloat(results.getString(11));
            atHand = Float.parseFloat(results.getString(12));
            size = Float.parseFloat(results.getString(19));
            color = results.getString(20);

        }

    }

    public ResultSet getOrderInventory() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Inventory where atHand < reorderLevel order by description";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        return results;
    }
    

}
