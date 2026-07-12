/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.Systems;
import java.sql.Connection;
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

public class JobsProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;

    boolean edit = false;
    boolean proceed = true;
    
    String recNum = "";
    String jobId = "";
    String process = "";
    String owner = "";    
    String contactPerson = "";    
    String contactDetails = "";    
    String indicatorId = "";
    String indicator = "";

    String query = "";

    public JobsProcessing() {
                
    }

    public void editJobs(String uk, String func, String mod, String jobRecNo) {
        try {

            String endDate = "";

            connection = Systems.initConnection();

            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from jobs where recnum = '" + jobRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();                            

                    } else {

                    }

                } else {      

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE jobs SET process  = '" + process + "'Where RecNum = '" + jobRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE jobs SET owner  = '" + owner + "'Where RecNum = '" + jobRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE jobs SET contactperson  = '" + contactPerson + "'Where RecNum = '" + jobRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE jobs SET contactdetails  = '" + contactDetails + "'Where RecNum = '" + jobRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE jobs SET IndicatorId  = '" + indicatorId + "'Where RecNum = '" + jobRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE jobs SET Indicator = '" + indicator + "'Where RecNum = '" + jobRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();                           

                }

            } else {

                int i = 0;
                query = "SELECT * FROM jobs where uuid = '" + uk + "' and process = '" + process + "' and owner = '" + owner + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    recNum = results.getString(1);
                }

                try {

                    if ((proceed == true)) {                                                        

                        int added = 0;
                        statement = connection.createStatement();
                        query = "INSERT INTO jobs(uuid, process, owner, contactperson, contactdetails, indicatorId, indicator) VALUES ('" + uk + "','" + 
                                process + "','" + owner + "','" + contactPerson + "','" + contactDetails + "','" + indicatorId + "','" + indicator + "')";
                        added = statement.executeUpdate(query);
                        statement.close();

                        if (recNum.equals("")) {

                            query = "SELECT max(recnum) FROM jobs";
                            statement = connection.createStatement();
                            ResultSet c = statement.executeQuery(query);

                            while (c.next()) {
                                recNum = c.getString(1);
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
    
    public void getJob(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM tasks where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {    
            
            jobId = results.getString(1);
            process = results.getString(3);
            owner = results.getString(4);
            contactPerson = results.getString(5);
            contactDetails = results.getString(6);
            indicatorId = results.getString(7);
            indicator = results.getString(8);
            
        }
        
    }
    
   /* public void editEmployee(String activityId) throws SQLException {
        
        boolean found = false;
        
        int i = 0;
        query = "SELECT * FROM task_assigned where activity_id = '" + activityId + "'";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            
            int added = 0;                        
            statement = connection.createStatement();
            query = "UPDATE task_assigned SET employee_id  = '" + recNum + "'Where activity_id = '" + activityId + "'";
            added = statement.executeUpdate(query);
            statement.close();

            added = 0;
            statement = connection.createStatement();
            query = "UPDATE task_assigned SET employee_name  = '" + empName + "' Where activity_id = '" + activityId + "'";
            added = statement.executeUpdate(query);
            statement.close(); 
            
            added = 0;
            statement = connection.createStatement();
            query = "UPDATE task_assigned SET employee_surname  = '" + surname + "' Where activity_id = '" + activityId + "'";
            added = statement.executeUpdate(query);
            statement.close(); 
            
            found = true;
            
        }
        
        if (found == false) {

            int added = 0;
            statement = connection.createStatement();
            query = "INSERT INTO task_assigned(activity_id, employee_id, employee_name, employee_surname) VALUES ('" + activityId + "','" + 
                    recNum + "','" + empName + "','" + surname + "')";
            added = statement.executeUpdate(query);
            statement.close();

        }
                
    }
    
*/   
    
    
}
