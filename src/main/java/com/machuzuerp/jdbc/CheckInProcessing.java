/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.CheckInActions;
import com.machuzuerp.controllers.jsf.CheckInAspects;
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

public class CheckInProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String recNum = "";
    public String checkInId;
    public String jobId;
    public java.util.Date startDate = null;
    public String verifiedRequest = "";    
    public String verificationResults = "";    
    public String additionalRequirements = "";    
    public String externalIssues = "";
    public String internalIssues = "";
    public String associatedRisks = "";
    public String customerSatisfaction = "";
    public String customerSatisfactionComment = "";
    public String responsibleSignature = "";
    public String supervisorSignature = "";
    public java.util.Date responsibleSignatureDate = null;
    public java.util.Date supervisorSignatureDate = null;   
    
    String query = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    int count = 0;

    public CheckInProcessing() {
                
    }

    public void editCheckIn(String uk, String func, String mod, String jobId) {
        try {

            String endDate = "";

            connection = Systems.initConnection();

            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from checkin where recnum = '" + jobId + "'";
                        added = statement.executeUpdate(query);
                        statement.close();                            

                    } else {

                    }

                } else {               

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET startDate  = '" + startDate + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET verifiedRequest  = '" + verifiedRequest + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET verificationResults  = '" + verificationResults + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();                           

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET additionalRequirements  = '" + additionalRequirements + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET externalIssues  = '" + externalIssues + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET internalIssues = '" + internalIssues + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();    

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET associatedRisks = '" + associatedRisks + "' Where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close(); 

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET customerSatisfaction  = '" + customerSatisfaction + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET customerSatisfactionComment  = '" + customerSatisfactionComment + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET responsibleSignature  = '" + responsibleSignature + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET supervisorSignature  = '" + supervisorSignature + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET responsibleSignatureDate  = '" + responsibleSignatureDate + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE checkin SET supervisorSignatureDate  = '" + supervisorSignatureDate + "' where recnum = '" + jobId + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                }

            } else {

                if ((proceed == true)) {   
                    
                    connection.setAutoCommit(false);

                    INSERT_DATA = "INSERT INTO statementitems(userrecnum, statementCheck, description, price, quantity, amount, servid, orgid) VALUES "
                            + " (?,?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setString(1, uk);
                    ps.setString(2, jobId);
                    ps.setString(3, verifiedRequest);
                    ps.setString(4, additionalRequirements);        
                    ps.setString(5, externalIssues);
                    ps.setString(6, internalIssues);
                    ps.setString(7, associatedRisks);                            
                    ps.setString(8, customerSatisfaction);                            
                    ps.setString(8, customerSatisfactionComment);                            


                    ps.executeUpdate();

                    connection.commit();

                    if (recNum.equals("")) {

                        query = "SELECT max(recnum) FROM checkin";
                        statement = connection.createStatement();
                        results = statement.executeQuery(query);

                        while (results.next()) {
                            checkInId = results.getString(1);                                  
                        }

                    }

                } else {

                }

            }

            connection.close();


        } catch (Exception sqle) {
            System.out.println("PROC111: "+sqle);
        }

    }
    
    public void getCheckIn(String recnum) throws SQLException {

      /*  Systems.closeConnection();
         
            String oid = Systems.getOID();
            connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM clients where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        ResultSet cfs = statement.executeQuery(query);

        while (cfs.next()) { 
            
            idNumber = cfs.getString(3);
            taxNumber = cfs.getString(4);
            employeeNumber = cfs.getString(5);
            employmentDate = cfs.getString(6);
            empName = cfs.getString(7);
            surname = cfs.getString(8);
            level = cfs.getString(19);
            gender = cfs.getString(9);
            telephone = cfs.getString(10);
            cellphone = cfs.getString(11);
            email = cfs.getString(12);
            postalAddress = cfs.getString(13);
            physicalAddress = cfs.getString(14);
            salary = cfs.getFloat(15);
            salaryInterval = cfs.getString(16);
            status = cfs.getString(17);

        }*/
        
    }
    
   /* public void editEmployee(String activityId) throws SQLException {
        
        boolean found = false;
        
        int i = 0;
        query = "SELECT * FROM task_assigned where activity_id = '" + activityId + "'";
        statement = connection.createStatement();
        ResultSet cfs = statement.executeQuery(query);

        while (cfs.next()) {
            
            int added = 0;                        
            statement = connection.createStatement();
            query = "UPDATE task_assigned SET employee_id  = '" + recNum + "' where activity_id = '" + activityId + "'";
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

    public void completeJob(String uuid, String jobId, String checkInId, List<CheckInActions> checkInActions, List<CheckInAspects> checkInAspects) throws SQLException {

        connection = Systems.initConnection();
        
        count = checkInActions.size();
        for (int x = 0; x < count; x++) {
            checkInActions.iterator().next();

            try {
                            
                int added = 0;
                statement = connection.createStatement();
                query = "INSERT INTO checkinactions(uuid, jobid, checkinid, plannedaction, actualaction, plannedtargetdate, actualtargetdate, plannedresourcesbudget, actualresourcesbudget, responsiblePerson) VALUES ('" + uuid + "','" + 
                        jobId + "','" + checkInId + "','" + checkInActions.get(x).getPlannedAction() + "','" + checkInActions.get(x).getActualAction()+ "','" + checkInActions.get(x).getPlannedTargetDate()
                         + "','" + checkInActions.get(x).getActualTargetDate()+ "','" + checkInActions.get(x).getPlannedResourcesBudget()+ "','" + checkInActions.get(x).getActualResourcesBudget()+ "','" + checkInActions.get(x).getResponsiblePerson()+ "')";
                added = statement.executeUpdate(query);
                statement.close();
                
            } catch (Exception sql) {}

        }

        count = checkInAspects.size();
        for (int x = 0; x < count; x++) {
            checkInAspects.iterator().next();

            if (checkInAspects.get(x).getRootCause().length() > 0) {

                int added = 0;
                statement = connection.createStatement();
                query = "INSERT INTO checkinaspects(uuid, jobid, checkinid, rootcause, actionrecommendation, targetrootcause, resourcesrecommendation) VALUES ('" + uuid + "','" + 
                        jobId + "','" + checkInId + "','" + checkInAspects.get(x).getRootCause() + "','" + checkInAspects.get(x).getActionRecommendation()+ "','" + checkInAspects.get(x).getTargetRootCause()
                         + "','" + checkInAspects.get(x).getResourcesRecommendation() + "')";
                added = statement.executeUpdate(query);
                statement.close();

            }

        }

    }

}