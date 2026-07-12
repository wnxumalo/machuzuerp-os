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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
public class OrgData implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String recNum;
    private String userType;
    private String number;
    private String orgName;
    private String tel;
    private String fax;
    private String email;        
    private String posAdd;    
    private String physAdd;
    private String orgID;   

    private List<OrgData> orgs;
    
    HttpServletRequest request;
    
    String query = "";

    public OrgData() {
        
    }

    public OrgData(String recNum, String number, String orgName, String tel, String fax, String email, String posAdd, String physAdd, String orgID) {
        
        this.recNum = recNum;
        this.number = number;
        this.orgName = orgName;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.orgID = orgID;

    }

    public List<OrgData> getOrgDat() {

        connection = Systems.initConnection();

        try {

            query = "SELECT * FROM Organisation where orgid = '" + orgID + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                orgs.add(new OrgData(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9)));

            }
        } catch (SQLException sqle) {
            System.out.println("SQLERROR:"+sqle);
        }

        return orgs;
    }
        

    public String getRecNum() {
        return recNum;
    }
    
    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }   

    public String getUType() {
        return userType;
    }
    
    public void setUType(String userType) {
        this.userType = userType;
    }   

    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }    

    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }    
    
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }          

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }        

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }        
    
    public void setPosAd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPosAd() {
        return posAdd;
    }
    
    public void setPhysAds(String physAdd) {
        this.physAdd = physAdd;
    }

    public String getPhysAdd() {
        return physAdd;
    }
        
    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getOrgID() {
        return orgID;
    }

    public void viewOrgUsers() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/customers/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
