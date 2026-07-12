/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.Systems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;

public class DeliveryNoteProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String recNum = "";
    public String invoiceNumber;
    public String uploadedfile;
    public String fileName;
    public String supplierId;
    public String supplierName;
    public Date deliveryDate;
    public String uploadDate;
    public String comments;    
    
    int maxRec = 0;
    
    String query = "";
    String ret = "";  
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DeliveryNoteProcessing() {

    }

    public String editDeliveryNote(String uk, String func, String mod, String noteNumber) {
        try {

            connection = Systems.initConnection();
        
                if (!func.equals("")) {

                    edit = true;
                    if (func.equals("delete")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from employeedeductions where recnum = '" + noteNumber + "'";
                        added = statement.executeUpdate(query);
                        statement.close(); 

                        ret = "Deleted";

                    } else {                                                  
    
                       /* int added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE employeedeductions SET EmployeeNumber  = '" + employeeNumber + "'Where RecNum = '" + empRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE employeedeductions SET Description  = '" + description + "'Where RecNum = '" + empRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE employeedeductions SET Amount  = '" + amount + "'Where RecNum = '" + empRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();*/
                       
                       ret = "Updated";
                     
                    }

                } else {

                    /*int i = 0;
                    query = "SELECT * FROM deliverynotes where invoicenumber = '" + invoiceNumber + "'";
                    statement = connection.createStatement();
                    results = statement.executeQuery(query);

                    proceed = true;
                    while (results.next()) {
                        proceed = false;
                        recNum = results.getString(1);
                    }
*/
                    try {

                        //if ((proceed == true)) {
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO deliverynotes(supplierId, supplierName, deliverydate, invoicenumber, comments) VALUES "
                                + " (?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, supplierId);
                        ps.setString(2, supplierName);
                        ps.setString(3, sdf.format(deliveryDate));                                       
                        ps.setString(4, invoiceNumber);                                       
                        ps.setString(5, comments);                                       

                        ps.executeUpdate();

                        connection.commit();

                        /*} else {
                           ret = "Exists"; 
                        }*/

                    } catch (SQLException np) {
                            System.out.println(np);
                    }
                    
                    ret = "Saved";

                }

                connection.close();            

        } catch (Exception sqle) {

        }
        
        return ret;

    }   
    
    public int getMaxRec() throws SQLException {
        
        connection = Systems.initConnection();
        
        int i = 0;
        query = "SELECT max(recnum) FROM deliverynotes";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
           maxRec = results.getInt(1);
        }
        
        return maxRec;
    }

}