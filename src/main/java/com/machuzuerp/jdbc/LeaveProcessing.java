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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Administrator
 */

public class LeaveProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;

    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String recNum = "";
    public String employeeNumber;   
    public String type;
    public Date startDate;
    public Date endDate;
    public long totDays; 
    public String comments;
    public String year;
    
    String query = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    public int availableDays = 0;

    public LeaveProcessing() {
                
    }

    public void editLeave(String uk, String func, String mod, String leaveRecNo) {

        try {

            connection = Systems.initConnection();

            // calculate days
            long diffInMillies = Math.abs(startDate.getTime() - endDate.getTime());
            long totDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            totDays += 1;
        
                if (!func.equals("")) {

                    edit = true;
                    if (func.equals("delete")) {

                        if (mod.equals("yes")) {

                            int added = 0;
                            statement = connection.createStatement();
                            query = "delete from leaveapplications where recnum = '" + leaveRecNo + "'";
                            added = statement.executeUpdate(query);
                            statement.close();                            

                        } else {

                        }

                    } else {                                                  
    
                        int added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE leaveapplications SET EmployeeRecNumber  = '" + employeeNumber + "' Where RecNum = '" + leaveRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE leaveapplications SET LeaveType  = '" + type + "' Where RecNum = '" + leaveRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE leaveapplications SET StartDate  = '" + startDate + "' Where RecNum = '" + leaveRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();
                        
                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE leaveapplications SET EndDate  = '" + endDate + "' Where RecNum = '" + leaveRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();
                        
                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE leaveapplications SET TotalDays  = '" + totDays + "' Where RecNum = '" + leaveRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();                                                
                        
                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE leaveapplications SET Comments  = '" + comments + "' Where RecNum = '" + leaveRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();                        

                    }

                } else {

                    proceed = true;
                    try {

                        if ((proceed == true)) {
                            
                            connection.setAutoCommit(false);

                            INSERT_DATA = "INSERT INTO leaveapplications(EmployeeRecNumber, LeaveType, StartDate, EndDate, TotalDays, Comments, uuid) VALUES "
                                    + " (?,?,?,?,?,?,?)";
                            ps = connection.prepareStatement(INSERT_DATA);
                            ps.setString(1, recNum);
                            ps.setString(2, type);
                            ps.setString(3, sdf.format(startDate));
                            ps.setString(4, sdf.format(endDate));        
                            ps.setLong(5, totDays);       
                            ps.setString(6, comments);       
                            ps.setString(7, uk);       

                            ps.executeUpdate();

                            connection.commit();                            

                            if (recNum.equals("")) {

                                query = "SELECT max(recnum) FROM leaveapplications";
                                statement = connection.createStatement();
                                ResultSet c = statement.executeQuery(query);

                                while (c.next()) {
                                    recNum = c.getString(1);
                                }

                            }

                        } else {
                            
                        }

                    } catch (Exception np) {
                            System.out.println(np);
                    }

                }

                connection.close();            

        } catch (Exception sqle) {

        }

    }
    
    public void getEmployeeLeave(String recnum) throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM leaveapplications where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) { 
            
            employeeNumber = results.getString(2);            
            type = results.getString(3);
            startDate = results.getDate(4);   
            endDate = results.getDate(4);   
            totDays = results.getLong(4);                                         
            year = results.getString(5);                                         

        }
        
    }       
    
    public ResultSet getLeave(String empNum) throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM leaveapplications where EmployeeRecNumber = '" + empNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;        
    }       
    
    public boolean checkLeave(String empNum, String type, String year) throws SQLException {

        connection = Systems.initConnection();
        
        query = "SELECT sum(TotalDays) FROM leaveapplications where EmployeeRecNumber = '" + empNum + "' and LeaveType = '" + type + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        int takenDays = 0;
        while (results.next()) {
            takenDays = results.getInt(1);
        }    

        int maxDays = 0;
        query = "SELECT TotalDays FROM leavedefaults where LeaveType = '" + type + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            maxDays = results.getInt(1);
        }

        availableDays = maxDays - takenDays;

        return (availableDays - totDays) >= 1;
    }     
    
    
    
}
