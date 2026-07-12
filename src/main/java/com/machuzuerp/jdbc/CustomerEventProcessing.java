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
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author Administrator
 */

public class CustomerEventProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    String clientRecNum = "";
    String customerType;
    String customerStat;
    String customerName;
    String customerSurname;
    String customerGender;
    String customerTelephone;
    String customerCellphone;
    String customerFax;
    String customerPostalAddress;
    String customerPhysicalAddress;
    String customerCountry;
    String customerJDate;
    String query = "";
    
    public CustomerEventProcessing() {
                
    }
    
    public void editEvent(String func, String mod, String uk, ScheduleEvent evt, String eventID, String customerName, String customerSurname, String custRecNum) {
        try {
            
            String endDate = "";

            connection = Systems.initConnection();

            if (!func.equals("")) {

                int added = 0;
                statement = connection.createStatement();
                query = "UPDATE events SET event  = '" + evt + "' Where eventid = '" + eventID + "'";
                added = statement.executeUpdate(query);
                statement.close();

            } else {

                try {

                    if ((proceed == true)) {
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO events(userkey, event,eventid, custname, custsurname, custrecnum) VALUES "
                            + " (?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, uk);
                        ps.setString(2, evt.toString());
                        ps.setString(3, eventID);        
                        ps.setString(4, customerName);
                        ps.setString(5, customerSurname);
                        ps.setString(6, custRecNum);

                        ps.executeUpdate();

                        connection.commit();

                        if (clientRecNum.equals("")) {

                            query = "SELECT max(recnum) FROM events";
                            statement = connection.createStatement();
                            results = statement.executeQuery(query);

                            while (results.next()) {
                                clientRecNum = results.getString(1);
                            }

                        }

                    } else {

                    }

                } catch (SQLException np) {
                        System.out.println(np);
                }

            }

            connection.close();

        } catch (Exception sqle) {

        }

    }
    
     public void deleteEvent(String func, String uk, ScheduleEvent evt) {
        try {

            connection = Systems.initConnection();

            int added = 0;
            statement = connection.createStatement();
            query = "delete from events where event = '" + evt + "'";
            added = statement.executeUpdate(query);
            statement.close();                               

        } catch (Exception e) {
            
        }
     }
     
     public String getEvent(ScheduleEvent evt, String uk) {

        String eventID = "";
        try {

            connection = Systems.initConnection();
            
            String query = "SELECT eventid FROM events where event = '" + evt + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query); 

            while (results.next()) {
                eventID= results.getString(1);
            }

        } catch (Exception e) {
            
        }

        return eventID;
     }
    
    public ResultSet getEvents(String uk) throws SQLException {
   
        connection = Systems.initConnection();
       
        String query = "SELECT * FROM events";
        statement = connection.createStatement();
        results = statement.executeQuery(query);               
        
        return results;
    }
    
    public void getCustomer(String recnum) throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM clients where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {          

            customerType = results.getString(3);
            customerStat = results.getString(4);
            customerName = results.getString(5);
            customerSurname = results.getString(6);
            customerGender = results.getString(7);
            customerTelephone = results.getString(8);
            customerCellphone = results.getString(9);
            customerFax = results.getString(10);
            customerPostalAddress = results.getString(11);
            customerPhysicalAddress = results.getString(12);
            customerCountry = results.getString(13);
            customerJDate = results.getString(14);

        }
        
    }
    
    
    
}
