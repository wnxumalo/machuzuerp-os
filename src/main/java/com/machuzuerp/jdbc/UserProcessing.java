/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.controllers.jsf.User;
import com.machuzuerp.controllers.datatableprocessing.jsf.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class UserProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    public String userRecNum = "";
    public String userType;
    public String email;
    public String password;
    public String name;
    public String surname;
    public String company;
    public String companyTel;
    public String companyAddress;
    public String uk;
    public String orgid;
    public String level;

    public String orgRecNumber;
    public String orgNumber;
    public String orgName;
    public String orgTel;
    public String orgFax;
    public String orgEmail;
    public String orgPosAdd;
    public String orgPhysAdd;

    String query = "";

    // User u = new User();
    public UserProcessing() {

    }

    public String editUser(String uk, String func, String mod, String userRecNo) {

        String ret = "";

        try {            
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
                    query = "UPDATE regusers SET userType  = '" + userType + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET emailaddress  = '" + email + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET pword  = '" + password + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET name  = '" + name + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET companyname  = '" + company + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET companytelephone  = '" + companyTel + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET companyaddress  = '" + companyAddress + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE regusers SET level  = '" + level + "' where recnum = '" + userRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                }

            } else {

                int i = 0;
                query = "SELECT * FROM regusers where emailaddress = '" + email + "'";
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
                        long orgID = r.nextLong();
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO regusers(usertype, emailaddress, pword, name, companyname, companytelephone, companyaddress, uk, userlevel)"
                                + " VALUES(?,?,?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, userType);
                        ps.setString(2, email);
                        ps.setString(3, password);
                        ps.setString(4, name);
                        ps.setString(5, company);
                        ps.setString(6, companyTel);
                        ps.setString(7, companyAddress);                            
                        ps.setLong(8, userkey);                            
                        ps.setString(9, level);

                        ps.executeUpdate();

                        connection.commit();

                        query = "SELECT max(recnum) FROM regusers";
                        statement = connection.createStatement();
                        results = statement.executeQuery(query);

                        String rNum = "";
                        while (results.next()) {
                            rNum = results.getString(1);
                        }

                        ret = "Saved";
                    } else {
                        ret = "Exists";
                    }

                } catch (SQLException np) {
                    System.out.println(np);
                }

            }

            connection.close();

        } catch (Exception sqle) {

        }
        
        return ret;
    }

    public void getUser(String uk) throws SQLException {

        UserData userDat = new UserData();

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM regusers where uk = '" + uk + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

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

            User u = new User();

            u.setUser(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(12), results.getString(11));

        }

    }

    public void getOrganisation(String uk) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Organisation";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            orgRecNumber = results.getString(1);
            orgNumber = results.getString(11);
            orgName = results.getString(7);
            orgTel = results.getString(10);
            //orgFax = results.getString(5);
            orgEmail = results.getString(5);
            orgPosAdd = results.getString(9);
            orgPhysAdd = results.getString(8);
            orgid = results.getString(11);

        }

    }
    
    public ResultSet getOrganisation() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Organisation";
        statement = connection.createStatement();
        results = statement.executeQuery(query);        
        
        return results;

    }

    public ResultSet getCustomers() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT recnum,name,surname FROM clients order by name, surname";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public void registerUser(String ipAddress) {
        try {

            String endDate = "";
 
            connection = Systems.initConnection();            
                try {
                    
                    int i = 0;
                    query = "SELECT * FROM regusers where emailaddress = '" + email + "'";
                    statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(query);

                    proceed = true;
                    while (results.next()) {
                        proceed = false;
                        userRecNum = results.getString(1);
                    }

                    if ((proceed == true)) {

                            Random r = new Random();
                            long userkey = r.nextLong();
                            r = new Random();
                            long orgkey = r.nextLong();
                            
                            connection.setAutoCommit(false);

                            INSERT_DATA = "INSERT INTO regusers(usertype, emailaddress, pword, name, companyname, companytelephone, companyaddress, uk, userlevel, ipaddress)"
                                    + " (?,?,?,?,?,?,?,?,?,?)";
                            ps = connection.prepareStatement(INSERT_DATA);
                            ps.setString(1, userType);
                            ps.setString(2, email);
                            ps.setString(3, password);
                            ps.setString(4, name);
                            ps.setString(5, company);
                            ps.setString(6, companyTel);
                            ps.setString(7, companyAddress);                            
                            ps.setLong(8, userkey);                            
                            ps.setString(9, "master");                            
                            ps.setString(10, ipAddress);                            

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

                    } catch (Exception np) {
                        System.out.println(np);
                    }

               

                connection.close();

            

        } catch (Exception sqle) {

        }

    }


}
