package com.machuzuerp.controllers.jsf;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Systems {

    static Connection connection;
    static Statement statement;

    static String uk = "";
    static String uName = "";
    static String uType = "";
    static String uEmail = "";
    static String uPassword = "";
    static String oID = "";

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat stf = new SimpleDateFormat("hh:mm:ss");
    static java.util.Date now = new java.util.Date();

    public static Connection initConnection() {

        try {

            closeConnection();
           
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/erp?useUnicode=yes&characterEncoding=UTF-8", "root", "admin");            

        } catch (Exception ce) {  
        }

        return connection;
    }

    public static Connection connDatabase() {
        
        try {
            
                        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

          connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/erp", "root", "admin");            

        } catch (Exception ce) {  
          
            
        }
        return connection;        
    }

    /*public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        connDatabase(request, response);
        
        query = "select * from user_profiles where uk = '" + request.getParameter("uk") + "'";
        statement = connection.createStatement();
        ResultSet up = statement.executeQuery(query);
        
        String cTime = ;

        while (up.next()) {
            
            query = "select * from activitylogs where emailaddress = '" + up.getString(5) + "' and actiondate = '" + sdf.format(java.util.Date()) + "' and actiontime";
            statement = connection.createStatement();
            ResultSet up = statement.executeQuery(query);
    
            while (up.next()) {
                
            }
            
        }

    }*/

    public static boolean Login(String email, String password) throws SQLException, ParseException {

        boolean proceed = false;
        LocalDate a = LocalDate.of(2025, 6, 30);
                
/*        if (LocalDate.now().isAfter(a)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Trial Expired", "The trial period has expired."));
            
        } else*/ {
            
            String query = "";            

        initConnection();

        try {
            
            try {

                email.equals("");
                password.equals("");

                query = "select * from regusers where emailaddress = '" + email + "' and pword = '" + password + "'";
                statement = connection.createStatement();
                ResultSet up = statement.executeQuery(query);

                while (up.next()) {
                    System.out.println(up.getString(5));
                    uk = up.getString(1);
                    uName = up.getString(5); 
                    uType = up.getString(11);
                    uEmail = up.getString(3);
                    oID = up.getString(12);
                    proceed = true;            
                }

                if (proceed == false) {
                    query = "select * from Employee where email = '" + email + "' and password = '" + password + "'";
                    statement = connection.createStatement();
                    up = statement.executeQuery(query);

                    while (up.next()) {
                        uk = up.getString(1);
                        uName = up.getString(9) + " "+ up.getString(26); 
                        uEmail = up.getString(7);
                        oID = up.getString(18);
                        proceed = true;            

                    }
                }

            } catch (NullPointerException ne) {

                if ((now.compareTo(new java.util.Date("2020/12/31"))) < 0 & email.equals("wandile177@gmail.com")) {
                    try {
                        
                        Double.parseDouble(uk);
                        //uk = request.getParameter("uk");

                        query = "select * from regusers where uk = '" + uk + "'";
                        statement = connection.createStatement();
                        ResultSet up = statement.executeQuery(query);

                        while (up.next()) {
                            uk = up.getString(10);
                            uName = up.getString(5); 
                            uType = up.getString(11);
                            uEmail = up.getString(3);
                            oID = up.getString(12);
                            proceed = true;
                        }

                    } catch (Exception fe) {

                        query = "select * from regusers where emailaddress = '" + email + "' and pword = '" + password + "'";
                        statement = connection.createStatement();
                        ResultSet up = statement.executeQuery(query);

                        while (up.next()) {
                            uk = up.getString(10);
                            uName = up.getString(5); 
                            uType = up.getString(11);
                            uEmail = up.getString(3);
                            oID = up.getString(12);
                            proceed = true;            
                        }

                    }

                    if (proceed == false) {

                        try {

                            Double.parseDouble(uk);
                            //uk = request.getParameter("uk");

                            query = "select * from users where uk = '" + uk + "'";
                            statement = connection.createStatement();
                            ResultSet up = statement.executeQuery(query);

                            while (up.next()) {
                                uk = up.getString(9);
                                uName = up.getString(4); 
                                uEmail = up.getString(2);
                                oID = up.getString(8);
                                proceed = true;
                            }

                        } catch (Exception fe) {            

                            query = "select * from users where email = '" + email + "' and upassword = '" + password + "'";
                            statement = connection.createStatement();
                            ResultSet up = statement.executeQuery(query);

                            while (up.next()) {
                                uk = up.getString(9);
                                uName = up.getString(4); 
                                uEmail = up.getString(2);
                                oID = up.getString(8);
                                proceed = true;            
                            }

                        }
                    }
                }

            }

        } catch (SQLException sqle) {
        System.out.println("ERROR: " + sqle);
        }

        int added = 0;
        statement = connection.createStatement();
        query = "insert into activitylogs(username, emailaddress, useraction, actiondate, actiontime) values('" + uName + "','" + uEmail + "','Login','" + sdf.format(new java.util.Date()) + "','" + stf.format(new java.util.Date()) + "')";
        added = statement.executeUpdate(query);
        statement.close();

            
        }
        
        return proceed;

    }
    
     public static boolean checkLogin(String uk) throws SQLException {

        String query = "";    
        boolean proceed = false;

        initConnection();

        try {
            
            try {
                
                uk.equals("");
                
                query = "select * from regusers where uk = '" + uk + "'";
                statement = connection.createStatement();
                ResultSet up = statement.executeQuery(query);

                while (up.next()) {
                    uk = up.getString(10);
                    uName = up.getString(5); 
                    uType = up.getString(11);
                    uEmail = up.getString(3);
                    oID = up.getString(12);
                    proceed = true;            
                }
                
                if (proceed == false) {
                    query = "select * from users where uk = '" + uk + "'";
                    statement = connection.createStatement();
                    up = statement.executeQuery(query);
    
                    while (up.next()) {
                        uk = up.getString(9);
                        uName = up.getString(4); 
                        uEmail = up.getString(2);
                        oID = up.getString(8);
                        proceed = true;            
            
                    }
                }

            } catch (NullPointerException ne) {

               /* try {
    
                    Double.parseDouble(request.getParameter("uk"));
                    //uk = request.getParameter("uk");
    
                    query = "select * from regusers where uk = '" + request.getParameter("uk") + "'";
                    statement = connection.createStatement();
                    ResultSet up = statement.executeQuery(query);
    
                    while (up.next()) {
                        uk = up.getString(10);
                        uName = up.getString(5); 
                        uType = up.getString(11);
                        uEmail = up.getString(3);
                        oID = up.getString(12);
                        proceed = true;
                    }
    
                } catch (Exception fe) {            
    
                    query = "select * from regusers where emailaddress = '" + request.getParameter("email") + "' and pword = '" + request.getParameter("pword") + "'";
                    statement = connection.createStatement();
                    ResultSet up = statement.executeQuery(query);
    
                    while (up.next()) {
                        uk = up.getString(10);
                        uName = up.getString(5); 
                        uType = up.getString(11);
                        uEmail = up.getString(3);
                        oID = up.getString(12);
                        proceed = true;            
                    }
                    
                }
                
                if (proceed == false) {
                    
                    try {
    
                        Double.parseDouble(request.getParameter("uk"));
                        //uk = request.getParameter("uk");
        
                        query = "select * from users where uk = '" + request.getParameter("uk") + "'";
                        statement = connection.createStatement();
                        ResultSet up = statement.executeQuery(query);
        
                        while (up.next()) {
                            uk = up.getString(9);
                            uName = up.getString(4); 
                            uEmail = up.getString(2);
                            oID = up.getString(8);
                            proceed = true;
                        }
        
                    } catch (Exception fe) {            
        
                        query = "select * from users where email = '" + request.getParameter("email") + "' and upassword = '" + request.getParameter("pword") + "'";
                        statement = connection.createStatement();
                        ResultSet up = statement.executeQuery(query);
        
                        while (up.next()) {
                            uk = up.getString(9);
                            uName = up.getString(4); 
                            uEmail = up.getString(2);
                            oID = up.getString(8);
                            proceed = true;            
                        }
                        
                    }
                }*/

            }

        } catch (SQLException sqle) {}

        int added = 0;
        statement = connection.createStatement();
        query = "insert into activitylogs(username, emailaddress, useraction, actiondate, actiontime) values('" + uName + "','" + uEmail + "','Login','" + sdf.format(new java.util.Date()) + "','" + stf.format(new java.util.Date()) + "')";
        added = statement.executeUpdate(query);
        statement.close();

        return proceed;

    }
     
    public int checkLock() throws SQLException {

        initConnection();

        int check =0;
        
        String query = "select * from checksums where invoicecheck = '1' and receiptcheck = '1' and quotecheck = '1' and statementcheck = '1'";
        statement = connection.createStatement();

        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            check = 1;
        }

        return check;
    }
     
    public void lockDatabase() throws SQLException {
        int added = 0;                       
        statement = connection.createStatement();
        String query = "UPDATE checksums SET invoicecheck = '1',receiptcheck = '1',quotecheck = '1',statementcheck = '1'";
        added = statement.executeUpdate(query);
        statement.close();
    }

    public void updatePassword(String id, String newPassword) throws SQLException {
        
        initConnection();
        
        int added = 0;                       
        statement = connection.createStatement();
        String query = "UPDATE employee SET password = '" + newPassword + "'where employee_id = '" + id + "'";
        added = statement.executeUpdate(query);
        statement.close();
    }

    public static String getName() {
        return uName;
    }        

    public String getKey() {
        return uk;
    }

    public static String getType() {
        return uType;
    }
    
     public static String getOID() {
        return oID;
    }  
    
    public static void testFile(HttpServletResponse response) throws FileNotFoundException {
        
   /*     try {
    
    File file = new File("Reports/Produce_Report.pdf");        
        FileOutputStream fileout = new FileOutputStream(file);
        DataOutput dataOutput = new DataOutputStream(fileout);
            fileout.close();
        } catch (Exception e) {
            
            try {
            PrintWriter out = response.getWriter();
            
            out.println(e);
            
        } catch(Exception e1) {}
            
        }*/
        
    }

    public String getEmail() {
        return uEmail;
    }
    
    public String getPassword() {
        return uPassword;
    }

    public static String getUK(HttpServletRequest request, HttpServletResponse response) {
        return request.getParameter("uk");
    }

    public static String getFunc(HttpServletRequest request, HttpServletResponse response) {
        return request.getParameter("func");
    }

    public static void closeConnection() {
        
        try {
        
		connection.close();
		
		uk = "";
		uName = "";
		uType = "";
		uEmail = "";
		oID = "";
    
        } catch (Exception e) {
            
        }
        
    }
    
    public void saveLoginData(String email, String fullname, String cellphone) {
    
        try {
            if (email.length() > 0) {

                initConnection();

                int added = 0;
                statement = connection.createStatement();
                String query = "insert into activitylogs(username, emailaddress, useraction, actiondate, actiontime) values('" + fullname + "','" + email + "','" + cellphone + "','" + sdf.format(new java.util.Date()) + "','" + stf.format(new java.util.Date()) + "')";
                added = statement.executeUpdate(query);
                statement.close();
            }
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Login Error Save: " + ex);
        }
    }
    
}