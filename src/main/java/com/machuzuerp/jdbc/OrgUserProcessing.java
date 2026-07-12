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
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class OrgUserProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    public String recnum;
    public String email;
    public String password;
    public String userName;
    public String surname;
    public String department;        
    public String uk;    
    public String mkey;    
    public String orgid;
    public String level;
    
    String userRecNum = "";

    String query = "";

    public OrgUserProcessing() {

    }        

    public void editUser(String uk, String func, String mod, String userRecNo) {
        try {

            String endDate = "";

            connection = Systems.initConnection();

            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from regusers where recnum = '" + userRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET email  = '" + email + "'Where RecNum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET upassword  = '" + password + "'Where RecNum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET name  = '" + userName + "'Where RecNum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET surname  = '" + surname + "'Where RecNum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET department  = '" + department + "'Where RecNum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET level  = '" + level + "'Where RecNum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                }

            } else {

                int i = 0;
                query = "SELECT * FROM regusers where email = '" + email + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    userRecNum = results.getString(1);
                }

                try {

                    if ((proceed == true)) {

                        Random r = new Random();
                        long userkey = r.nextLong();
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO regusers(email, upassword, name, surname, department, uk, level) VALUES "
                                + " (?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, email);
                        ps.setString(2, password);
                        ps.setString(3, userName);
                        ps.setString(4, surname);        
                        ps.setString(5, department);       
                        ps.setLong(6, userkey);       
                        ps.setString(7, level);       

                        ps.executeUpdate();

                        connection.commit();

                        if (userRecNum.equals("")) {

                            query = "SELECT max(recnum) FROM regusers";
                            statement = connection.createStatement();
                            ResultSet c = statement.executeQuery(query);

                            while (c.next()) {
                                userRecNum = c.getString(1);
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

    /*public void getUser(String uk) throws SQLException {
        
        UserData userDat = new UserData();

        Systems.closeConnection();

        String oid = Systems.getOID();
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM regusers where uk = '" + uk + "'";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            
            userRecNum = results.getString(1);
            userType = results.getString(2);
            email = results.getString(3);
            password = results.getString(4);
            name = results.getString(5);
            surname = results.getString(6);
            company = results.getString(7);
            companyTel = results.getString(8);
            companyAddress = results.getString(9);
            uk = results.getString(10);
            orgid = results.getString(12);
            level = results.getString(11);

            UserData ud = new UserData(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(12), results.getString(11));

        }

    }
    
    public void getOrganisation(String uk) throws SQLException {        

        Systems.closeConnection();

        Systems.checkLogin(uk);
        String oid = Systems.getOID();
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Organisation where orgid = '" + oid + "'";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);
            orgid = results.getString(9);

        }

    }*/
        
    public ResultSet getCustomers() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT recnum,name,surname FROM clients order by name, surname";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

}
