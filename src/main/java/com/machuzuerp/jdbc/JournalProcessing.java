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

/**
 *
 * @author Administrator
 */
public class JournalProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;

    boolean edit = false;
    boolean proceed = true;
    public String jRecNum;
    public String accountNumber;
    public String accType;
    public String description;
    public String transaction;
    public String transactionDate;
    public double debit;
    public double credit;
    public String comments;
    public String orgid;
    
    String query = "";

    public JournalProcessing() {

    }

    public void editJournal(String uk, String func, String mod, String journalRecNo) throws SQLException {
        try {

            String cDate = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cDate = sdf.format(new java.util.Date());

            connection = Systems.initConnection();

            if (!func.equals("")) {
                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from journal where recnum = '" + journalRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                }

            } 

            connection.close();

        } catch (Exception sqle) {
               System.out.println(sqle);
        }

    }

    public void getJournal(String rNum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM journal where recnum = '" + rNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            jRecNum = results.getString(1);
            accountNumber = results.getString(2);
            accType = results.getString(3);
            description = results.getString(4);
            transaction = results.getString(5);
            transactionDate = results.getString(6);
            debit = Double.parseDouble(results.getString(7));
            credit = Double.parseDouble(results.getString(8));
            comments = results.getString(9);
            orgid = results.getString(10);

        }

    }
    

}
