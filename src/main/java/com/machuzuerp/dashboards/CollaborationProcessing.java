/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.dashboards;

import com.machuzuerp.controllers.jsf.Systems;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wandile
 */
public class CollaborationProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    String query = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    private List<DashboardValues> totFiles = new ArrayList<DashboardValues>();
    private List<DashboardValues> totRevenue = new ArrayList<DashboardValues>();
    
    int total = 0;

    public List<DashboardValues> CalculateFilesByTeam(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();

        try {
            query = "SELECT * FROM teams order by TeamName";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                query = "SELECT count(*) FROM files where teamid = '" + results.getInt(1) + "' and uploaddate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {
                    totFiles.add(new DashboardValues(results.getString(3),results1.getInt(1)));
                }

            }

        } catch (NullPointerException npe) {}

        return totFiles;
    }
    
    public List<DashboardValues> CalculateFilesByType(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();

        try {
            query = "SELECT distinct(TeamType), recnum, TeamName FROM teams";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                query = "SELECT count(*) FROM files where teamid = '" + results.getInt(2) + "' and uploaddate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {
                    totFiles.add(new DashboardValues(results.getString(3),results1.getInt(1)));
                }

            }

        } catch (NullPointerException npe) {}

        return totFiles;
    }

    public List<DashboardValues> CalculateRevenue(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();

        try {
            query = "SELECT transactiondate FROM journal where transactiondate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                query = "SELECT sum(credit) FROM journal where transactiondate = '" + results.getString(1) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {
                    totRevenue.add(new DashboardValues(results.getDate(1),results1.getInt(1)));
                }

            }

        } catch (NullPointerException npe) {}

        return totRevenue;
    }
    
    public int CalculateAssociates() throws SQLException {

        connection = Systems.initConnection();
        
        try {
            query = "SELECT count(*) FROM teams where TeamType = 'Associates'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateBoard() throws SQLException {

        connection = Systems.initConnection();
        
        try {
            query = "SELECT count(*) FROM teams where TeamType = 'Board'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateExecutive() throws SQLException {

        connection = Systems.initConnection();
        
        try {
            query = "SELECT count(*) FROM teams where TeamType = 'Executive'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateFriends() throws SQLException {

        connection = Systems.initConnection();
        
        try {
            query = "SELECT count(*) FROM teams where TeamType = 'Friends'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateGeneral() throws SQLException {

        connection = Systems.initConnection();
        
        try {
            query = "SELECT count(*) FROM teams where TeamType = 'General'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateManagement() throws SQLException {

        connection = Systems.initConnection();
        
        try {
            query = "SELECT count(*) FROM teams where TeamType = 'Management'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateStaff() throws SQLException {

        connection = Systems.initConnection();
        
        try {
            query = "SELECT count(*) FROM teams where TeamType = 'Staff'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }

}
