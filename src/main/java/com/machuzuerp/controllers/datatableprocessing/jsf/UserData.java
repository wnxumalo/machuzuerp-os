/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Login;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class UserData implements Serializable {

    /**
     * Creates a new instance of CustomerData
     */
    Connection connection;
    Statement statement;
    
    private String userType;
    private String email;
    private String password;
    private String userName;
    private String company;
    private String compTel;        
    private String compPosAdd;    
    private String orgid;
    private String level;

    private String byName;
    int count = 9;
    private String bySurname;

    String userRecNum = "";
    String userDat= "";

    private List<UserData> users;

    public UserData() {
    }

    public UserData(String userRecNum, String userType, String email, String password, String userName, String company, String compTel, String compPosAdd, String uk, String level, String orgid) {
        
        this.userRecNum = userRecNum;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.userName = userName;        
        this.company = company;
        this.compTel = compTel;      
        this.compPosAdd = compPosAdd;        
        this.orgid = orgid;
        this.level = level;

    }

  /*  public List<OrgUserData> getUsers() {

        getParams();

        Systems.closeConnection();
        connection = Systems.initConnection();

        try {
            int i = 0;
            //String query = "SELECT * FROM users where orgid = '" + orgid + "'";
            String query = "SELECT * FROM regusers";
            statement = connection.createStatement();
            ResultSet cfs = statement.executeQuery(query);

            while (cfs.next()) {

                oUsers.add(new OrgUserData(cfs.getString(1), cfs.getString(2), cfs.getString(3), cfs.getString(4), cfs.getString(5), cfs.getString(6), cfs.getString(7), cfs.getString(8), cfs.getString(9), cfs.getString(10)));

            }
        } catch (SQLException sqle) {
            System.out.println("SQLERROR:"+sqle);
        }

        System.out.println("USER1:"+oUsers +":"+orgid);

        return oUsers;
    }
*/        

    public String getRecNum() {
        return userRecNum;
    }
    
    public void setRecNum(String userRecNum) {
        this.userRecNum = userRecNum;
    }   

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }   
  
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }    
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }        

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }        

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }        
    
    public void setCompTel(String compTel) {
        this.compTel = compTel;
    }

    public String getCompTel() {
        return compTel;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
        
    public void setOrgID(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgID() {
        return orgid;
    }
    
    public void setCompPosAdd(String compPosAdd) {
        this.compPosAdd = compPosAdd;
    }

    public String getCompPosAdd() {
        return compPosAdd;
    }

    public void viewOrgUsers() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/customers/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
