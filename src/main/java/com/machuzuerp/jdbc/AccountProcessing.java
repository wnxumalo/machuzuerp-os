/*
 * To change this license header, choose License Headers in Project Properties.r
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.datatableprocessing.jsf.AccountData;
import com.machuzuerp.controllers.jsf.Systems;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class AccountProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    public String aRecNum = "";
    public String accountNumber = "";
    public String accType = "";
    public String description = "";
    public float balance = 0;
    public String comments = "";
    public String pDate;
    public String accountType = "";

    String query = "";       
    
    private String option;   
    private AccountData accData; 
    private List<AccountData> account;

    public AccountProcessing() {
        

    }

    public void editAccount(String uk, String func, String mod, String accountRecNo) throws SQLException {
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
                        query = "delete from accounts where recnum = '" + accountRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE accounts SET AccountNumber  = '" + accountNumber + "'Where recnum = '" + accountRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    statement = connection.createStatement();
                    query = "UPDATE accounts SET AccountType  = '" + accType + "'Where recnum = '" + accountRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE accounts SET Description  = '" + description + "'Where recnum = '" + accountRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE accounts SET Balance  = '" + balance + "'Where recnum = '" + accountRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    added = 0;

                    statement = connection.createStatement();
                    query = "UPDATE accounts SET Comments  = '" + comments + "'Where recnum = '" + accountRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();                                               

                }

            } else {

                int i = 0;
                query = "SELECT * FROM accounts where uk = '" + uk + "' and accounttype = '" + accType + "' and description = '" + description + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {                       
                    if (!results.getString(3).equals("")) {
                        proceed = false;
                        aRecNum = results.getString(1);
                    }
                }

                try {                       

                    if ((proceed == true)) {

                        String accCode = "";
                        boolean getVal = true;
                        for (int x=0;x<accType.length();x++) {

                            if ((accType.charAt(x) != ' ') & (getVal == true)) {
                                accCode = accCode + accType.charAt(x);
                            }

                            if (accType.charAt(x) == ' ') {
                                getVal = false;
                                x = accType.length();
                            }
                        }  
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO accounts(uk, accounttype, accountnumber, description, balance, comments) VALUES "
                                + " (?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, uk);
                        ps.setString(2, accType);
                        ps.setString(3, accountNumber);
                        ps.setString(4, description);        
                        ps.setFloat(5, balance);
                        ps.setString(6, comments);                  

                        ps.executeUpdate();

                        connection.commit();

                        if (aRecNum.equals("")) {

                            query = "SELECT max(recnum) FROM accounts";
                            statement = connection.createStatement();
                            results = statement.executeQuery(query);

                            while (results.next()) {
                                aRecNum = results.getString(1);
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
               System.out.println(sqle);
        }

    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }
    
    public void editAccountPayable(String uk, String func, String mod, String accountRecNo) throws SQLException {
        try {

            String cDate = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");            

            connection = Systems.initConnection();

            if (!func.equals("")) {

            } else {

                try {

                    if ((proceed == true)) {

                        int added = 0;
                        
                            connection.setAutoCommit(false);

                            INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, comments, transactiondate) VALUES "
                                    + " (?,?,?,?,?,?,?)";
                            ps = connection.prepareStatement(INSERT_DATA);
                            ps.setString(1, uk);
                            ps.setString(2, accountNumber);
                            ps.setString(3, accountType);
                            ps.setString(4, description);        
                            ps.setFloat(5, balance);
                            ps.setString(6, comments);
                            ps.setString(7, pDate);

                            ps.executeUpdate();

                            connection.commit();                                                                           

                            INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, comments, transactiondate) VALUES "
                                    + " (?,?,?,?,?,?,?)";
                            ps = connection.prepareStatement(INSERT_DATA);
                            ps.setString(1, uk);
                            ps.setString(2, "2100");
                            ps.setString(3, "Payables");
                            ps.setString(4, accountType);        
                            ps.setFloat(5, balance);
                            ps.setString(6, comments);
                            ps.setString(7, pDate);                                                    

                            ps.executeUpdate();

                            connection.commit();

                        }

                        if (aRecNum.equals("")) {

                            query = "SELECT max(recnum) FROM accounts";
                            statement = connection.createStatement();
                            results = statement.executeQuery(query);

                            while (results.next()) {
                                aRecNum = results.getString(1);
                            }

                        }



                } catch (SQLException np) {
                    System.out.println("ERROR:" + np);
                }

            }

            connection.close();

        } catch (Exception sqle) {
               System.out.println("ERROR1:"+sqle);
        }
    }

    public void updateJournalAccountPayable(String uk, String accountnumber, String accounttype, String accountdescription, BigDecimal debit, String comments, LocalDate transactiondate) throws SQLException {
        try {

            String cDate = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");            

            connection = Systems.initConnection();

            int added = 0;
                        
            connection.setAutoCommit(false);

            INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, comments, transactiondate) VALUES "
                    + " (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, uk);
            ps.setString(2, accountNumber);
            ps.setString(3, accountType);
            ps.setString(4, description);        
            ps.setFloat(5, balance);
            ps.setString(6, comments);
            ps.setString(7, pDate);

            ps.executeUpdate();

            connection.commit();                                                                           

            INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, comments, transactiondate) VALUES "
                    + " (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, uk);
            ps.setString(2, "2100");
            ps.setString(3, "Payables");
            ps.setString(4, accountType);        
            ps.setFloat(5, balance);
            ps.setString(6, comments);
            ps.setString(7, pDate);                                                    

            ps.executeUpdate();

            connection.commit();
            connection.close();

        } catch (Exception sqle) {
               System.out.println("ERROR1:"+sqle);
        }
    }

    public void updateJournalAccountReceivable(String uk, String accountnumber, String accounttype, String accountdescription, BigDecimal debit, String comments, LocalDate transactiondate) throws SQLException {
        try {

            String cDate = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");            

            connection = Systems.initConnection();

            int added = 0;
                        
            connection.setAutoCommit(false);

            INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, credit, comments, transactiondate) VALUES "
                    + " (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, uk);
            ps.setString(2, accountNumber);
            ps.setString(3, accountType);
            ps.setString(4, description);        
            ps.setFloat(5, balance);
            ps.setString(6, comments);
            ps.setString(7, pDate);

            ps.executeUpdate();

            connection.commit();                                                                           

            INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, comments, transactiondate) VALUES "
                    + " (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, uk);
            ps.setString(2, "1200");
            ps.setString(3, "Receivables");
            ps.setString(4, accountType);        
            ps.setFloat(5, balance);
            ps.setString(6, comments);
            ps.setString(7, pDate);                                               

            ps.executeUpdate();

            connection.commit();
            connection.close();

        } catch (Exception sqle) {
               System.out.println("ERROR1:"+sqle);
        }
    }

    public void editAccountReceivable(String uk, String func, String mod, String accountRecNo) throws SQLException {
        try {

            String cDate = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cDate = sdf.format(new java.util.Date());

            connection = Systems.initConnection();

            if (!func.equals("")) {

            } else {

                try {                       

                    if ((proceed == true)) {

                        int added = 0;
//                            Statement st = null;
                        int count = 0;
                        while (count<2) {

                            if (count == 0) {

                                String aMainNum = "";                                    
                                String aNum = "";
                                String aDesc = "";
                                int cCount = 0;

                                for (int x=0;x<description.length();x++) {

                                    if ((cCount == 0) & (description.charAt(x)!=':')) {
                                        aMainNum = aMainNum + description.charAt(x);
                                    }                                                                                

                                    if ((cCount == 2) & (description.charAt(x)!= ' ') & (description.charAt(x)!= '-')) {
                                        aNum = aNum + description.charAt(x);
                                    }

                                    if ((cCount == 3) & (description.charAt(x)!= ' ') & (description.charAt(x)!= '-')) {
                                        aDesc = aDesc + description.charAt(x);
                                    }

                                    if ((description.charAt(x)=='-') | (description.charAt(x)==':')) {
                                        cCount++;
                                    }                                                

                                }

                                query = "SELECT accounttype, description FROM subaccounts where accountnumber = '" + aNum + "'";
                                statement = connection.createStatement();
                                results = statement.executeQuery(query);

                                while (results.next()) {

                                    INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, comments, transactiondate) VALUES "
                                            + " (?,?,?,?,?,?,?)";
                                    ps = connection.prepareStatement(INSERT_DATA);
                                    ps.setString(1, uk);
                                    ps.setString(2, aNum);
                                    ps.setString(3, aMainNum + "/"+ results.getString(1));
                                    ps.setString(4, results.getString(2));        
                                    ps.setFloat(5, balance);
                                    ps.setString(6, comments);
                                    ps.setString(7, pDate);                                                      

                                    ps.executeUpdate();

                                    connection.commit();
                                }                                    

                            } else if (count == 1) {                                     

                                INSERT_DATA = "INSERT INTO journal(uk, accountnumber, accounttype, accountdescription, debit, comments, transactiondate) VALUES "
                                        + " (?,?,?,?,?,?,?)";
                                ps = connection.prepareStatement(INSERT_DATA);
                                ps.setString(1, uk);
                                ps.setString(2, "1200");
                                ps.setString(3, "Receivables");
                                ps.setString(4, accountType);        
                                ps.setFloat(5, balance);
                                ps.setString(6, comments);
                                ps.setString(7, pDate);                                               

                                ps.executeUpdate();

                                connection.commit();
                            }
                            count++;
                        }

                        if (aRecNum.equals("")) {

                            query = "SELECT max(recnum) FROM accounts";
                            statement = connection.createStatement();
                            results = statement.executeQuery(query);

                            while (results.next()) {
                                aRecNum = results.getString(1);
                            }

                        }

                    } else {

                    }

                } catch (SQLException np) {
                    System.out.println("ERROR:" + np);
                }

            }

            connection.close();

        } catch (Exception sqle) {
               System.out.println("ERROR1:"+sqle);
        }
    }

    public void getAccount(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM accounts where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            accType = results.getString(2);
            accountNumber = results.getString(3);
            description = results.getString(4);            
            balance = Float.parseFloat(results.getString(5));
            comments = results.getString(6);            

        }

    }

    public AccountData getAccount(Integer id) throws SQLException {

        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (AccountData acc : account){
            if (id.equals(acc.getRecNum())){
                return acc;
            }
        }
        return null;
    }

    public ResultSet getAccount() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT recnum,accountnumber,description FROM accounts order by description";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public ResultSet getAccounts() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM accounts order by description";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public ResultSet getSubAccounts() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM subaccounts order by accountnumber";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

    public ResultSet getUserDefinedAccounts() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM userdefinedaccounts order by accountnumber";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

    public ResultSet getSubAccount(String code) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM subaccounts where accountnumber = '" + code + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }
    
    public ResultSet getSubAccounts(int begin, int end) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM subaccounts where accountnumber between '" + begin + "' and '" + end + "' order by accountnumber";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

}
