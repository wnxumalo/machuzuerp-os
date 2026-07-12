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

/**
 *
 * @author Administrator
 */

public class TrainingsProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;

    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String recNum = "";
    public String employeeNumber;
    public Date startDate;
    public Date endDate;
    public String description;
    public String outputs;
    public String status;
    public String comments;

    String query = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public TrainingsProcessing() {
                
    }

    public void editTraining(String uk, String func, String mod, String empRecNo) {
        try {

            connection = Systems.initConnection();

            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from EmployeeTraining where id = '" + empRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();                            

                    } else {

                    }

                } else {                                                  

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeTraining SET employeeId  = '" + employeeNumber + "'Where id = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeTraining SET startDate  = '" + sdf.format(startDate) + "'Where id = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeTraining SET endDate  = '" + sdf.format(endDate) + "'Where id = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();                                                

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeTraining SET description  = '" + description + "'Where id = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeTraining SET outputs  = '" + outputs + "'Where id = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeTraining SET status  = '" + status + "'Where id = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeTraining SET comments  = '" + comments + "'Where id = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                }

            } else {

                int i = 0;
                query = "SELECT * FROM EmployeeTraining where employeeId = '" + employeeNumber + "' and description = '" + description + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    recNum = results.getString(1);
                }

                try {

                    if ((proceed == true)) {                 
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT EmployeeTraining(employeeId, startDate, endDate, description, outputs, status, comments) VALUES(?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, recNum);
                        ps.setString(2, sdf.format(startDate));
                        ps.setString(3, sdf.format(endDate));
                        ps.setString(4, description);
                        ps.setString(5, outputs);
                        ps.setString(6, status);
                        ps.setString(7, comments);                            

                        ps.executeUpdate();

                        connection.commit();

                        if (recNum.equals("")) {

                            query = "SELECT max(recnum) FROM EmployeeTraining";
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
    
    public void getEmployeeTraining(String recnum) throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM EmployeeTraining where employeeId = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {                         
            
            employeeNumber = results.getString(2);            
            startDate = results.getDate(3);
            endDate = results.getDate(4);
            description = results.getString(4);
            outputs = results.getString(4);
            status = results.getString(4);
            comments = results.getString(4);

        }
        
    }       

    public ResultSet getTrainings(String empNum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM EmployeeTraining where employeeId = '" + empNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;        
    }       
            
}