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
public class StakeholdersProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    String query = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    private List<DashboardValues> totStakeholders = new ArrayList<DashboardValues>();

    public List<DashboardValues> TotalByJoiningDate(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();

        try {
            query = "SELECT distinct(JoiningDate) FROM clients where JoiningDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                query = "SELECT count(*) FROM clients where JoiningDate = '" + results.getString(1) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {
                    totStakeholders.add(new DashboardValues(results.getDate(1),results1.getInt(1)));
                }

            }
        } catch (NullPointerException npe) {}

        return totStakeholders;
    }
    
    public int CalculateMale(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();
        int total = 0;
        try {
            query = "SELECT count(*) FROM clients where JoiningDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "' and Gender = 'Male'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateFemale(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();
        int total = 0;
        try {
            query = "SELECT count(*) FROM clients where JoiningDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "' and Gender = 'Female'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateTypeClient(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();
        int total = 0;
        try {
            query = "SELECT count(*) FROM clients where JoiningDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "' and ClientType = 'Client'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateTypePartner(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();
        int total = 0;
        try {
            query = "SELECT count(*) FROM clients where JoiningDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "' and ClientType = 'Partner'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateActive(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();
        int total = 0;
        try {
            query = "SELECT count(*) FROM clients where JoiningDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "' and ClientStatus = 'Active'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }
    
    public int CalculateInactive(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();
        int total = 0;
        try {
            query = "SELECT count(*) FROM clients where JoiningDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "' and ClientStatus = 'Inactive'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;        
    }

}
