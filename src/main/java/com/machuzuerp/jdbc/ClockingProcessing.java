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

/**
 *
 * @author Administrator
 */

public class ClockingProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String recNum = "";
    public String SupervisorId;
    public String EmployeeId;
    public Date ExitDate;
    public Date ExitTime;
    public Date ReturnDate;
    public Date ReturnTime;
    public String WorkDescription;
    public String Outputs;
    public String JobStatus;
    public String Comments;
    public String Approved;
    
    String query = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat stf = new SimpleDateFormat("hh:mm:ss");
    
    int availableDays = 0;

    public ClockingProcessing() {
                
    }

    public void editClocking(String uk, String func, String mod, String clockingId) {
        
        try {

            connection = Systems.initConnection();

            {                
                if (!func.equals("")) {

                    edit = true;
                    if (func.equals("delete")) {

                        if (mod.equals("yes")) {

                            int added = 0;
                            statement = connection.createStatement();
                            query = "delete from accesscontrol where recnum = '" + clockingId + "'";
                            added = statement.executeUpdate(query);
                            statement.close();                            

                        } else {

                        }

                    } else {                                                                 
            
                        /*int added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET SupervisorId  = '" + SupervisorId + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET EmployeeId  = '" + EmployeeId + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET ExitTime  = '" + ExitTime + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();
                        
                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET ExitDate  = '" + ExitDate + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();*/
                        System.out.println("111: " +clockingId);
                        int added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET ReturnDate  = '" + sdf.format(ReturnDate) + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();                                                          

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET ReturnTime  = '" + stf.format(new Date()) + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        /*added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET WorkDescription  = '" + WorkDescription + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close(); */

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET Outputs  = '" + Outputs + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close(); 
                        
                        /*added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET JobStatus  = '" + JobStatus + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();*/
                        
                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET Comments  = '" + Comments + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close(); 
                        
                        /*added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE accesscontrol SET Approved  = '" + Approved + "' Where RecNum = '" + clockingId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();*/ 

                    }

                } else {

                    try {

                        /*query = "SELECT supervisor_id FROM Employees where employeenumber = '" + EmployeeId + "'";
                        statement = connection.createStatement();
                        results = statement.executeQuery(query);

                        while (results.next()) {
                            SupervisorId = results.getString(1);
                        }*/

                        connection.setAutoCommit(false);
                        
                        INSERT_DATA = "INSERT INTO accesscontrol(SupervisorId, EmployeeId, ExitDate, ExitTime, WorkDescription, Outputs) VALUES "
                                + " (?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, SupervisorId);
                        ps.setString(2, EmployeeId);
                        ps.setString(3, sdf.format(ExitDate));
                        ps.setString(4, stf.format(new Date()));        
                        ps.setString(5, WorkDescription);
                        ps.setString(6, Outputs);

                        ps.executeUpdate();

                        connection.commit();

                        if (recNum.equals("")) {

                            query = "SELECT max(recnum) FROM accesscontrol";
                            statement = connection.createStatement();
                            results = statement.executeQuery(query);

                            while (results.next()) {
                                recNum = results.getString(1);
                            }

                        }

                    } catch (Exception np) {
                            System.out.println(np);
                    }

                }

                connection.close();
            }            

        } catch (Exception sqle) {

        }

    }
    
    public void getEmployeeClocking(String recnum) throws SQLException {        
            
        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM accesscontrol where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) { 
            
/*            employeeNumber = cfs.getString(2);            
            type = cfs.getString(3);
            startDate = cfs.getDate(4);   
            endDate = cfs.getDate(4);   
            totDays = cfs.getDouble(4);                                         
            year = cfs.getString(5);  */                                       

        }
        
    }
    
    public boolean CheckClocking(String empNum) throws SQLException {        
            
        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM employees where employeenumber = '" + empNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        proceed = false;
        while (results.next()) {     
            proceed = true;
        }
        
        return proceed;
    }
    
    public ResultSet getClocking(String empNum) throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM accesscontrol where EmployeeRecNumber = '" + empNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;        
    }       
  
    
    
}
