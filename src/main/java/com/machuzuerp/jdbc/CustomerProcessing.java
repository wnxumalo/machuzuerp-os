/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.jsf.Systems;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerProcessing implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    public String clientRecNum = "";
    public String vatNum = "";
    public String customerType = "";
    public String customerStat = "";
    public String customerName = "";
    public String customerSurname = "";
    public String customerGender = "";
    public String customerTelephone = "";
    public String customerCellphone = "";
    public String customerEmail = "";
    public String customerFax = "";
    public String customerPostalAddress = "";
    public String customerPhysicalAddress = "";
    public String customerCountry = "";
    public String customerJDate = "";
    public Long supplierId;
    String query = "";
    
    String clientDat= "";

    public CustomerProcessing() {

    }

    public String editCustomer(String uk, String func, String mod, String clientRecNo) {

        String msg = "";

        try {

            connection = Systems.initConnection();

            if (!func.equals("")) {
                edit = true;
                if (func.equals("delete")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from clients where recnum = '" + clientRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        msg = "Deleted";


                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET vatnum  = '" + vatNum + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET ClientType  = '" + customerType + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET ClientStatus  = '" + customerStat + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET Name  = '" + customerName + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET Surname  = '" + customerSurname + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET Gender = '" + customerGender + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET Telephone  = '" + customerTelephone + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET Cellphone  = '" + customerCellphone + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET Email  = '" + customerEmail + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET Fax  = '" + customerFax + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET PostalAddress  = '" + customerPostalAddress + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET PhysicalAddress  = '" + customerPhysicalAddress + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET country  = '" + customerCountry + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE clients SET JoiningDate  = '" + customerJDate + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    msg = "Updated";

                }

            } else {

                int i = 0;
                query = "SELECT * FROM clients where email = '" + customerEmail + "' or vatnum = '" + vatNum + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    clientRecNum = results.getString(1);
                }

                try {

                    if ((proceed == true)) {
                        
                        connection.setAutoCommit(false);
                        
                        INSERT_DATA = "INSERT INTO clients(uk, clienttype, clientstatus, name, surname, gender, telephone, cellphone, email, fax, postaladdress, physicaladdress, "
                                + "country, joiningdate, vatnum, supplierid) VALUES "
                                + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, uk);
                        ps.setString(2, customerType);
                        ps.setString(3, customerStat);
                        ps.setString(4, customerName);        
                        ps.setString(5, customerSurname);
                        ps.setString(6, customerGender);
                        ps.setString(7, customerTelephone);
                        ps.setString(8, customerCellphone);
                        ps.setString(9, customerEmail);
                        ps.setString(10, customerFax);
                        ps.setString(11, customerPostalAddress);
                        ps.setString(12, customerPhysicalAddress);
                        ps.setString(13, customerCountry);
                        ps.setString(14, customerJDate);
                        ps.setString(15, vatNum);
                        ps.setLong(16, supplierId);

                        ps.executeUpdate();

                        connection.commit();

                        if (clientRecNum.equals("")) {

                            query = "SELECT max(recnum) FROM clients";
                            statement = connection.createStatement();
                            results = statement.executeQuery(query);

                            while (results.next()) {
                                clientRecNum = results.getString(1);
                            }

                        }

                        msg = "Saved";

                    } else {

                        msg = "Exists";

                    }

                } catch (SQLException np) {
                    System.out.println(np);
                }

            }

            connection.close();

        } catch (Exception sqle) {

        }

        return msg;
    }
    
    public ResultSet getCustomerResultset(String vatNumber) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM clients where vatnum = '" + vatNumber + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

       return results;
    }

    public void getCustomer(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM clients where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            clientRecNum = results.getString(1);
            vatNum = results.getString(17);
            customerType = results.getString(3);
            customerStat = results.getString(4);
            customerName = results.getString(5);
            customerSurname = results.getString(6);
            customerGender = results.getString(7);
            customerTelephone = results.getString(8);
            customerCellphone = results.getString(9);
            customerEmail = results.getString(18);
            customerFax = results.getString(10);
            customerPostalAddress = results.getString(11);
            customerPhysicalAddress = results.getString(12);
            customerCountry = results.getString(13);
            customerJDate = results.getString(14);

        }

    }

    public String getCustomerObject(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT name,surname, telephone, cellphone, fax, email, postaladdress, physicaladdress,recnum,vatNum FROM clients where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        String ret = "";
        while (results.next()) {

            ret = results.getString(1) + "," + results.getString(2) + "," + results.getString(3) + "," + results.getString(4) + "," + results.getString(5) + ","
                    + results.getString(6) + "," + results.getString(7) + "," + results.getString(8) + "," + results.getString(9) + "," + results.getString(10);

        }

        return ret;
    }

    public String getCustomerDetails(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM clients where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            clientRecNum = results.getString(1);
            vatNum = results.getString(17);
            customerType = results.getString(3);
            customerStat = results.getString(4);
            customerName = results.getString(5);
            customerSurname = results.getString(6);
            customerGender = results.getString(7);
            customerTelephone = results.getString(8);
            customerCellphone = results.getString(9);
            customerEmail = results.getString(18);
            customerFax = results.getString(10);
            customerPostalAddress = results.getString(11);
            customerPhysicalAddress = results.getString(12);
            customerCountry = results.getString(13);
            customerJDate = results.getString(14);

        }

        return clientRecNum + "," + customerName + "," + customerSurname + "," + customerTelephone + "," + customerCellphone + "," + customerFax + "," + customerEmail + "," + customerPostalAddress + ","
                + customerPhysicalAddress + "," + vatNum;
    }
    
    public String getCustomerArr(String recnum) {
//        getParams();

        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            int i = 0;               
            query = "SELECT * FROM clients where recnum = '" + recnum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                clientDat = results.getString(1) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(18) + ',' + results.getString(11) + ',' + results.getString(12) + ',' + results.getString(17);
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return clientDat;

    }

    public void editClient(String projectId) throws SQLException {

        boolean found = false;

        int i = 0;
        query = "SELECT * FROM project_stakeholder where project_id = '" + projectId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            int added = 0;
            statement = connection.createStatement();
            query = "UPDATE project_stakeholder SET client_partner_id  = '" + clientRecNum + "'Where project_id = '" + projectId + "'";
            added = statement.executeUpdate(query);
            statement.close();

            added = 0;
            statement = connection.createStatement();
            query = "UPDATE project_stakeholder SET stakeholder_type  = '" + customerType + "'Where project_id = '" + projectId + "'";
            added = statement.executeUpdate(query);
            statement.close();

            added = 0;
            statement = connection.createStatement();
            query = "UPDATE project_stakeholder SET description  = '" + customerName + " " + customerSurname + "' Where project_id = '" + projectId + "'";
            added = statement.executeUpdate(query);
            statement.close();

            found = true;

        }

        if (found == false) {
            
            connection.setAutoCommit(false);

            INSERT_DATA = "INSERT INTO project_stakeholder(project_id, client_partner_id, stakeholder_type, description) VALUES "
                        + " (?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, projectId);
                ps.setString(2, clientRecNum);
                ps.setString(3, customerType);
                ps.setString(4, customerName+ " " + customerSurname);               

                ps.executeUpdate();

                connection.commit();

        }

    }

    public ResultSet getCustomers() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT recnum,name,surname FROM clients order by name, surname";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

    public boolean getEnrollmentService(String customerId, int serviceId) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM enrolledservices where Stakeholder_Id = '" + customerId + "' and Service_Id = '" + serviceId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        boolean proceed = true;
        while (results.next()) {
            proceed = false;
        }        

        return proceed;

    }

}