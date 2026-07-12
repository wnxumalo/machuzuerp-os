/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class OrgUserData implements Serializable {

    /**
     * Creates a new instance of CustomerData
     */
    Connection connection;
    Statement statement;
    ResultSet results;
    
    private String uk;
    private String email;    
    private String password;
    private String userName;
    private String surname;
    private String department;        
    private String mkey;    
    private String orgid;
    private String level;

    private String byName;
    int count = 9;
    private String bySurname;

    String userRecNum = "";
    String userDat= "";

    private List<OrgUserData> oUsers = new ArrayList<OrgUserData>();
    private OrgUserData selectedOrgUser;
    
    HttpServletRequest request;

    public OrgUserData() {
        
    }

    public OrgUserData(String userRecNum, String email, String password, String userName, String surname, String department, String mkey, String orgid, String uk, String level) {

        this.userRecNum = userRecNum;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.surname = surname;
        this.department = department;
        this.mkey = mkey;      
        this.orgid = orgid;
        this.uk = uk;
        this.level = level;

    }

    public List<OrgUserData> getUsers() {

        oUsers.clear();

        connection = Systems.initConnection();

        try {
            int i = 0;
            //String query = "SELECT * FROM users where orgid = '" + orgid + "'";
            String query = "SELECT * FROM regusers";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                oUsers.add(new OrgUserData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), null, null, results.getString(10), results.getString(11)));
            }
        } catch (SQLException sqle) {
            System.out.println("SQLERROR:"+sqle);
        }

        

        return oUsers;
    }
    
    public OrgUserData getSelectedOrgUser() {
        return selectedOrgUser;
    }

    public void setSelectedOrgUser(OrgUserData selectedOrgUser) {
        this.selectedOrgUser = selectedOrgUser;
    }

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
    
    public String getUk() {
        return uk;
    }
    
    public void setUk(String uk) {
        this.uk = uk;
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
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
    
    public void setMKey(String mkey) {
        this.mkey = mkey;
    }

    public String getMKey() {
        return mkey;
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

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
      
        this.userRecNum = params.get("userRecNum");
        this.email = params.get("email");
        this.password = params.get("password");
        this.userName = params.get("userName");
        this.surname = params.get("surname");
        this.department = params.get("department");
        this.mkey = params.get("mkey");
        this.orgid = params.get("orgid");
        this.level = params.get("level");       
    }

    public void viewOrgUsers() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/customers/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        getParams();

        this.userRecNum = ((OrgUserData) event.getObject()).userRecNum;
        this.email = ((OrgUserData) event.getObject()).email;
        this.userName = ((OrgUserData) event.getObject()).userName;
        this.surname = ((OrgUserData) event.getObject()).surname;
        this.department = ((OrgUserData) event.getObject()).department;
        this.level = ((OrgUserData) event.getObject()).level;
        this.password = ((OrgUserData) event.getObject()).password;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        String param = "userRecNum=" + userRecNum + "&email=" + email + "&username=" + userName + "&surname=" + surname + "&department=" + department + "&uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&level=" + level + "&password=" + password;

        FacesContext.getCurrentInstance().getExternalContext().redirect("/ERPbyW/faces/view-organisation-user.xhtml?" + param);
    }

}
