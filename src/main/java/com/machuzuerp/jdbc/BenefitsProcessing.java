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

/**
 *
 * @author Administrator
 */

public class BenefitsProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String recNum = "";
    public String employeeNumber = "";   
    public String description = "";
    public double amount = 0;   
    
    String query = "";

    public BenefitsProcessing() {
                
    }

    public void editBenefit(String uk, String func, String mod, String empRecNo) {
        try {

            String endDate = "";

            connection = Systems.initConnection();

            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from EmployeeBenefit where EMPLOYEE_BENEFIT_ID = '" + empRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();                            

                    } else {

                    }

                } else {                                                  

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeBenefit SET EmployeeNumber  = '" + employeeNumber + "'Where RecNum = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeBenefit SET Description  = '" + description + "'Where RecNum = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE EmployeeBenefit SET Amount  = '" + amount + "'Where RecNum = '" + empRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                }

            } else {

                int i = 0;
                query = "SELECT * FROM EmployeeBenefit where EmployeeNumber = '" + empRecNo + "' and Description = '" + description + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;                    
                }

                try {

                    if ((proceed == true)) {
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO EmployeeBenefit(EmployeeNumber, Description, Amount) VALUES "
                                + " (?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, empRecNo);
                        ps.setString(2, description);
                        ps.setDouble(3, amount);

                        ps.executeUpdate();

                        connection.commit();

                        if (recNum.equals("")) {

                            query = "SELECT max(recnum) FROM EmployeeBenefit";
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
    
    public void getEmployeeBenefit(String recnum) throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM EmployeeBenefit where employeeId = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) { 
            
            employeeNumber = results.getString(2);            
            description = results.getString(3);
            amount = results.getDouble(4);            

        }
        
    }       
    
    public ResultSet getBenefits(String empNum) throws SQLException {

        connection = Systems.connDatabase();
            
        int i = 0;
        query = "SELECT * FROM EmployeeBenefit where employeeId = '" + empNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;        
    }       
    
    
    
}
