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
import java.util.List;

public class AccountingProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    String query = "";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private List<DashboardValues> totExpenditure = new ArrayList<DashboardValues>();
    private List<DashboardValues> totRevenue = new ArrayList<DashboardValues>();

    public List<DashboardValues> CalculateExpenditure(Date dateFrom, Date dateTo) throws SQLException {

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
                    totExpenditure.add(new DashboardValues(results.getDate(1),results1.getInt(1)));
                }

            }

        } catch (NullPointerException npe) {}

        return totExpenditure;
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
    
    public int APOpenRequisitions(String uk) throws SQLException {

        connection = Systems.initConnection();
        int total = 0;
        try {
            query = "SELECT count(*) FROM requisitionapprovals where employeeid = '" + uk + "' and status = 'Unapproved'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                total = results.getInt(1);               
            }
        } catch (NullPointerException npe) {}

        return total;
    }
    
    public ResultSet getOpenRequisitions() throws SQLException {
    
    System.out.println("111");
        String ret;

        int count =0;
        connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM requisition where status <> 'Approved'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        return results;
    }
    
    public int getOpenRequisitionCount() throws SQLException {    

        int count =0;
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT count(*) FROM requisition where status <> 'Approved'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        while (results.next()) {
            count = results.getInt(1);
        }
        
        return count;
    }

}
