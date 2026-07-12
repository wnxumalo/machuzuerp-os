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
import java.util.List;

/**
 *
 * @author Administrator
 */
public class WorkflowProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String workflowstepid;
    public String workflowid;
    public String number;
    public String stepName;
    public String module;
    public String approvers;
    public String output;

    String query = "";
    
    String msg = "";

    public WorkflowProcessing() {

    }

    public String editWorkflowStep(String uk, String func, String mod, String serviceRecNo) throws SQLException {
   
           /* connection = Systems.initConnection();

            if (!func.equals("")) {
                edit = true;
                if (func.equals("delete")) {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "delete from services where pk = '" + serviceRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    msg = "Deleted";                 

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE services SET service_no  = '" + code + "'Where pk = '" + serviceRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE services SET description  = '" + description + "'Where pk = '" + serviceRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE services SET cost  = '" + cost + "' Where pk = '" + serviceRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    msg = "Updated";

                }

            } else {

                int i = 0;
                query = "SELECT * FROM services where service_no = '" + code + "' and description = '" + description + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {                       
                    if (!results.getString(3).equals("")) {
                        proceed = false;
                        serviceRecNum = results.getString(1);
                    }

                }

                if ((proceed == true)) {
                    
                    connection.setAutoCommit(false);

                    INSERT_DATA = "INSERT INTO services(uk, service_no, description, cost)"
                        + " VALUES (?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, uk);
                        ps.setString(2, code);
                        ps.setString(3, description);
                        ps.setFloat(4, cost);

                        ps.executeUpdate();

                        connection.commit();

                    msg = "Saved";

                } else {

                    msg = "Exists";

                }

                

            }               

        System.out.println("MDG: "+msg);*/
        return msg;
    }

    public void get(String recnum) throws SQLException {

      /*  connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM service where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            code = results.getString(2);
            description = results.getString(3);
            cost = Float.parseFloat(results.getString(7));

        }*/

    }

    public ResultSet getWorkflowStep(int recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM workflowstep where workflow = '" + recnum + "' order by stepNumber";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

}