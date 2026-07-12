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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author hp
 */
public class FileProcessing {

    Connection connection;
    Statement statement;
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    String query;
    ResultSet results;
    ResultSet results1;
    public String recNum;

    public String name;
    public String uk;
    public String teamID;
    public String teamName;
    public String date;
    public String time;
    public String comment;
    public String type;
    public String fileName;
    public String comments;
    
    public String teamEmails;

    List<SelectItem> teams = new ArrayList<SelectItem>();
    String fileComments;

    boolean edit = false;
    boolean proceed = true;

    String msg = "";
    
    int maxRec = 0;
    
    String ret = "";
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    

    public String getMaxFiles() throws SQLException {

        connection = Systems.initConnection();

        query = "SELECT max(recnum) FROM files";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            recNum = results.getString(1);
        }

        return recNum;

    }

    public List<SelectItem> getTeams(String uk) throws SQLException {

        connection = Systems.initConnection();

        query = "SELECT teamname FROM teams order by TeamName";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        teams.clear();
        while (results.next()) {
            teams.add(new SelectItem(results.getString(1)));
        }

        return teams;

    }
    
    public String getTeamEmails() throws SQLException {

        connection = Systems.initConnection();

        query = "SELECT teamemails FROM teams where recnum = '" + teamID + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        teamEmails = "";
        while (results.next()) {
            teamEmails = results.getString(1);
        }

        return teamEmails;
    }

    public String editFile(String uk, String func, String mod, String fileNo) {
        try {

            connection = Systems.initConnection();

            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "delete from files where recnum = '" + fileNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "delete from file_discussions where fileid = '" + fileNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    msg = "Deleted";

                } else if (func.equals("newcomment")) {
                    
                    connection.setAutoCommit(false);

                    INSERT_DATA = "INSERT INTO comments(uuid, teamid, teamname, cdate, ctime, ctype, comment, fileid) VALUES "
                            + " (?,?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setString(1, this.uk);
                    ps.setString(2, teamID);
                    ps.setString(3, teamName);
                    ps.setString(4, date);
                    ps.setString(5, time);
                    ps.setString(6, type);
                    ps.setString(7, comment);
                    ps.setString(8, recNum);

                    ps.executeUpdate();

                    connection.commit();

                    getFileComments(recNum);

                    //FileComments fc = new FileComments();
                    //fc.getComments(recNum);
                    msg = "Saved";

                }

            } else {

                int i = 0;
                query = "SELECT * FROM files where teamid = '" + teamID + "' and filename = '" + fileName + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                }

                query = "SELECT max(recnum) FROM files";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                while (results.next()) {
                    recNum = results.getString(1);
                }

                query = "SELECT recnum FROM teams where teamname = '" + teamName + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                while (results.next()) {
                    teamID = results.getString(1);
                }

                try {

                    if ((proceed == true)) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE files SET uk = '" + uk + "'Where RecNum = '" + recNum + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE files SET teamID = '" + teamID + "'Where RecNum = '" + recNum + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE files SET teamname = '" + teamName + "'Where RecNum = '" + recNum + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE files SET comments = '" + comments + "'Where RecNum = '" + recNum + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                        msg = "Updated";

                    } else {

                        msg = "Exists";

                    }

                } catch (SQLException np) {
                    System.out.println(np);
                }

            }

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return msg;

    }

    public String getFileComments(String fileID) {

        fileComments = "";

        try {

            connection = Systems.initConnection();

            query = "SELECT uuid, comment FROM comments where fileid = '" + fileID + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);            

            while (results.next()) {

                query = "SELECT empname FROM employee where EMPLOYEE_ID = '" + results.getString(1) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {

                    if (fileComments.equals("")) {
                        fileComments = results1.getString(1) + ": " + results.getString(2);
                    } else {
                        fileComments = fileComments + "," + results1.getString(1) + ": " + results.getString(2);
                    }

                }

            }

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        if (fileComments.length() < 1) {
            fileComments = "Ensure you are registered as an Employee in the HR Module before submitting a comment.";
        }
        
        return fileComments;
    }
    
    public ResultSet getRevisionFiles(String fileID) {

        fileComments = "";

        try {

            connection = Systems.initConnection();

            query = "SELECT * FROM revisionfiles where fileid = '" + fileID + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);            

            while (results.next()) {

                query = "SELECT empname FROM employees where idnumber = '" + results.getString(10) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                /*while (results1.next()) {

                    if (fileComments.equals("")) {
                        fileComments = results1.getString(1) + ": " + results.getString(2);
                    } else {
                        fileComments = fileComments + "," + results1.getString(1) + ": " + results.getString(2);
                    }

                }*/

            }

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

       /* if (fileComments.length() < 1) {
            fileComments = "Ensure you are registered as an Employee in the HR Module before submitting a comment.";
        }*/
        
        return results1;
    }

    public String saveCollaborationFile(String url, String fullPath, String fileName, String filetype) throws SQLException {        

        try {

            connection = Systems.initConnection();
            connection.setAutoCommit(false);           

            /*INSERT_DATA = "insert into files(viewurl, fullPath, filename, "
                    + "filetype, uploaddate) VALUES (?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, url+fileName);
            ps.setString(2, fullPath);
            ps.setString(3, fileName);
            ps.setString(4, filetype);
            ps.setString(5, sdf.format(new Date()));*/
            //http://103.125.218.105:8080/
             //http://localhost:8080/Machuzu-ERP-1.0-SNAPSHOT/files/ :::: gluon_084852.png
            url = url.substring(0, 21);
            url += "8082/files/";
System.out.println("SAVEURL: " + url);
            INSERT_DATA = "insert into files(viewurl, fullPath, filename, "
                    + "filetype, uploaddate) VALUES (?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, url+fileName);
            ps.setString(2, fullPath);
            ps.setString(3, fileName);
            ps.setString(4, filetype);
            ps.setString(5, sdf.format(new Date()));
            ps.executeUpdate();

            connection.commit();

            query = "SELECT max(recnum) FROM files";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                maxRec = results.getInt(1);
            }

            ret = "File Uploaded Successfully";

        } catch (Exception e) {
            ret = "Error: " + e;
        }

        return ret;
    }
    
    public String saveRevisionCollaborationFile(String url, String fullPath, String fileName, String filetype, String recnum) throws SQLException {        

        try {

            connection = Systems.initConnection();
            connection.setAutoCommit(false);           
            url = url.substring(0, 21);
            url += "/files/";

            INSERT_DATA = "insert into revisionfiles(viewurl, fullPath, filename, "
                    + "filetype, uploaddate, fileid) VALUES (?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, url+fileName);
            ps.setString(2, fullPath);
            ps.setString(3, fileName);
            ps.setString(4, filetype);
            ps.setString(5, sdf.format(new Date()));
            ps.setString(6, recnum);
            ps.executeUpdate();

            connection.commit();

            ret = "File Uploaded Successfully";

        } catch (Exception e) {
            ret = "Error: " + e;
        }

        return ret;
    }   
    
    public ResultSet getUploadedFiles(String fId) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM files where recnum = '" + fId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);        

        return results;
    }

}
