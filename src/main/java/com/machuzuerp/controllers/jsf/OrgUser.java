/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.OrgUserData;
import com.machuzuerp.controllers.datatableprocessing.jsf.CustomerData;
import com.machuzuerp.jdbc.OrgUserProcessing;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class OrgUser implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String ouRecNum;
    private String ouEmail;
    private String ouPassword;
    private String ouPassword1;
    private String ouName;
    private String ouSurname;
    private String ouDep;
    private String ouMKey;
    private String ouLevel;
    private String ouOrgId;
    private String ouUK;

    String ret = "";

    private OrgUserData selectedUser;
    private List<OrgUserData> oUsers = new ArrayList<OrgUserData>();
    
    OrgUserProcessing oup = new OrgUserProcessing();        

    static HttpServletRequest request;
    static  String ipAddress;

    public OrgUser() {
         
    }
    
    public String getRecNum() {
        return ouRecNum;
    }

    public void setRecNum(String ouRecNum) {
        this.ouRecNum = ouRecNum;
    }

    public String getEmail() {
        return ouEmail;
    }

    public void setEmail(String ouEmail) {
        this.ouEmail = ouEmail;
    }         
 
    public String getPassword() {
        return ouPassword;
    }

    public void setPassword(String ouPassword) {
        this.ouPassword = ouPassword;
    }
    
    public String getPassword1() {
        return ouPassword1;
    }

    public void setPassword1(String ouPassword1) {
        this.ouPassword1 = ouPassword1;
    }

    public String getName() {
        return ouName;
    }
    
    public void setName(String ouName) {
        this.ouName = ouName;
    }      
    
    public String getSurname() {
        return ouSurname;
    }

    public void setSurname(String ouSurname) {
        this.ouSurname = ouSurname;
    }       
    
    public String getDepartment() {
        return ouDep;
    }
    
    public void setDepartment(String ouDep) {
        this.ouDep = ouDep;
    }   
    
    public String getMKey() {
        return ouMKey;
    }
    
    public void setMKey(String ouMKey) {
        this.ouMKey = ouMKey;
    } 
    
    public void setLevel(String ouLevel) {
        this.ouLevel = ouLevel;
    }

    public String getLevel() {
        return ouLevel;
    }

    public String getOrgID() {
        return ouOrgId;
    }

    public void setOrgID(String ouOrgId) {
        this.ouOrgId = ouOrgId;
    }    

    public void setUK(String ouUK) {
        this.ouUK = ouUK;
    }

    public String getUK() {
        return ouUK;
    }

    public String editOrgUser_1(String ouRecNum, String ouEmail, String ouPassword, String ouName, String ouSurname, String ouDep, String ouLevel) throws SQLException {

        this.ouRecNum = ouRecNum;
        this.ouEmail = ouEmail;
        this.ouPassword = ouPassword;
        this.ouName = ouName;
        this.ouSurname = ouSurname;
        this.ouDep = ouDep;
        this.ouMKey = ouMKey;
        this.ouLevel = ouLevel;
        this.ouOrgId = ouOrgId;
        this.ouUK = ouUK;

        return "/edit-organisation-user";        
    }

    public String saveEditUser() throws SQLException {

        try {

            oup.recnum = ouRecNum;
            oup.email = ouEmail;
            oup.password = ouPassword;
            oup.userName = ouName;
            oup.surname = ouSurname;
            oup.department = ouDep;
            oup.level = ouLevel;
            oup.uk = ouUK;
            oup.mkey = ouMKey;
            oup.orgid = ouOrgId;
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            oup.editUser(request.getSession().getAttribute("uk").toString(), "edit", "", ouRecNum);
            ret = "/saved";
        } catch (NullPointerException npe) {
            ret = "/notsaved";
        }

        return ret;
    }

    public String saveOrgUser() {
        
        oup.recnum = ouRecNum;
        oup.email = ouEmail;
        oup.password = ouPassword;
        oup.userName = ouName;
        oup.surname = ouSurname;
        oup.department = ouDep;
        oup.level = ouLevel;
        oup.uk = ouUK;
        oup.mkey = ouMKey;
        oup.orgid = ouOrgId;

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            oup.editUser(request.getSession().getAttribute("uk").toString(), "", "", "");            
            ret = "/saved";
        } catch (Exception npe) {
            ret = "/notsaved";
        }

        return ret;
    }
    
    public String addUser(String uk) throws SQLException {
        
     //   UserProcessing up = new UserProcessing();
       // up.getOrganisation(uk); 
        
        //this.ouOrgId = up.orgid;
        
        return "new-org-user";
        
    }

    public void viewCustomer() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/customers/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String deleteUser(String recnum) {

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            oup.editUser(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);

            ret = "/saved";
        } catch (NullPointerException npe) {
            ret = "/notsaved";
        }
        
        return ret;
    }

}
