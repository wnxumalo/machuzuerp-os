/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.Organisation;
import com.machuzuerp.controllers.jsf.Systems;
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
public class OrganisationProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;


    boolean edit = false;
    boolean proceed = true;

    public String recnum;
    public String number;
    public String orgName;
    public String tel;
    public String fax;
    public String email;
    public String posAdd;
    public String physAdd;
    public String orgid;
            
    String query = "";
    
   // User u = new User();

    public OrganisationProcessing() {

    }

    public void editOrganisation(String uk, String func, String mod, String recNo) {
        try {

            String endDate = "";

            connection = Systems.initConnection();

            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete FROM Organisation where recnum = '" + recNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE organisations SET number  = '" + number + "' where recnum = '" + recNo + "'";
                    added = statement.executeUpdate(query);                        
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE organisations SET name  = '" + orgName + "' where recnum = '" + recNo + "'";
                    added = statement.executeUpdate(query);                        
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE organisations SET telephone  = '" + tel + "' where recnum = '" + recNo + "'";
                    added = statement.executeUpdate(query);                        
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE organisations SET fax  = '" + fax + "' where recnum = '" + recNo + "'";
                    added = statement.executeUpdate(query);                        
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE organisations SET email  = '" + email + "' where recnum = '" + recNo + "'";
                    added = statement.executeUpdate(query);                        
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE organisations SET posAddress  = '" + posAdd + "' where recnum = '" + recNo + "'";
                    added = statement.executeUpdate(query);                        
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE organisations SET physAddress  = '" + physAdd + "' where recnum = '" + recNo + "'";
                    added = statement.executeUpdate(query);                        
                    statement.close();                                                

                }

            } else {

                int i = 0;
                query = "SELECT * FROM Organisation where orgid = '" + orgid + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    recnum = results.getString(1);
                }

                try {

                    if ((proceed == true)) {

                        Random r = new Random();
                        long userkey = r.nextLong();    
                        long orgID = r.nextLong();
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO organisations(number, name, telephone, fax, email, posaddress, physaddress) VALUES "
                                + " (?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, number);
                        ps.setString(2, orgName);
                        ps.setString(3, tel);
                        ps.setString(4, fax);        
                        ps.setString(5, email);       
                        ps.setString(6, posAdd);       
                        ps.setString(7, physAdd);       

                        ps.executeUpdate();

                        connection.commit();

                        query = "SELECT max(recnum) FROM Organisation";
                        statement = connection.createStatement();
                        results = statement.executeQuery(query);

                        String rNum = "";                                            
                        while (results.next()) {
                            rNum = results.getString(1);
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

    public void getOrganisation(String uk) throws SQLException {                

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Organisation";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            
            number = results.getString(2);
            orgName = results.getString(3);
            tel = results.getString(4);
            fax = results.getString(5);
            email = results.getString(6);
            posAdd = results.getString(7);
            physAdd = results.getString(8);
            orgid = results.getString(9);

            Organisation o = new Organisation();
            
            o.setOrganisation(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9));

        }

    }
    
    public ResultSet getOrganisation() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT recnum,number,name FROM organisation order by name";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

}
