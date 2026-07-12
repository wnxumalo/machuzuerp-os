/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.BillingStatement;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
public class BillingStatementData implements Serializable {

    /**
     * Creates a new instance of BillingStatementData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String iRecNum;
    private String iCheck;
    private String iDate;
    private String cName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String posAdd;
    private String physAdd;
    private String country;
    private String recurring;
    private String cid;

    String billingStatementRecNum = "";

    private List<BillingStatementData> billingStatements = new ArrayList<BillingStatementData>();
    private List<BillingStatementData> allBillingStatements = new ArrayList<BillingStatementData>();
    private List<BillingStatementData> filteredBillingStatements = new ArrayList<BillingStatementData>();
    private BillingStatementData selectedBillingStatement;
    private List<BillingStatement> selectedInv;
    
    HttpServletRequest request;
    
    String query = "";

    public BillingStatementData() {
        
    }

    public BillingStatementData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid) {

        this.iRecNum = iRecNum;
        this.iCheck = iCheck;
        this.iDate = iDate;
        this.cName = cName;
        this.cSurname = cSurname;
        this.tel = tel;
        this.cell = cell;
        this.fax = fax;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.country = country;
        this.recurring = recurring;
        this.cid = cid;
    }

    public String searchBillingStatement() {        

        billingStatements.clear();
        try {

            connection = Systems.initConnection();

            if (!cName.equals("")) {
                query = "SELECT * FROM billingStatements where and name like '%" + cName + "%'";
            } else if (!cSurname.equals("")) {
                query = "SELECT * FROM billingStatements where and surname like '%" + cSurname + "%'";
            }
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                billingStatementRecNum = results.getString(1);
                billingStatements.add(new BillingStatementData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15)));

            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/billing/billingStatement/billingStatement-listing";

    }

    public List<BillingStatementData> getAllBillingStatements() {

        allBillingStatements.clear();

        try {

            connection = Systems.initConnection();

            query = "SELECT * FROM billingStatements order by billingStatementcheck";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                billingStatementRecNum = results.getString(1);
                allBillingStatements.add(new BillingStatementData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println("ERR:"+sqle);
        }

        return allBillingStatements;
    }

    public void filterBillingStatements() {

        filteredBillingStatements.clear();

        try {

            connection = Systems.initConnection();

            query = "SELECT * FROM billingstatements where name like '%" + cName + "%' order by name";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                billingStatementRecNum = results.getString(1);
                filteredBillingStatements.add(new BillingStatementData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15)));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    public List<BillingStatement> getSelectedInv() {
        return selectedInv;
    }
 
    public void setSelectedInv(List<BillingStatement> selectedInv) {
        this.selectedInv = selectedInv;
    }

    public List<BillingStatementData> getFilteredBillingStatements() {

        filterBillingStatements();

        return filteredBillingStatements;
    }

    public List<BillingStatementData> getBillingStatements() {    
        return billingStatements;
    }
    
    public String getRecNum() {
        return iRecNum;
    }

    public void setRecNum(String iRecNum) {
        this.iRecNum = iRecNum;
    }

    public String getInvCheck() {
        return iCheck;
    }

    public void setInvCheck(String iCheck) {
        this.iCheck = iCheck;
    }

    public String getIDate() {
        return iDate;
    }

    public void setIDate(String iDate) {
        this.iDate = iDate;
    }
    
    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }       

    public String getCSurname() {
        return cSurname;
    }

    public void setCSurname(String cSurname) {
        this.cSurname = cSurname;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public String getCell() {
        return cell;
    }
    
    public void setCell(String cell) {
        this.cell = cell;
    }   

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setPosAdd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPosAdd() {
        return posAdd;
    }    

    public String getPhysAdd() {
        return physAdd;
    }      

    public void setPhysAdd(String physAdd) {
        this.physAdd = physAdd;
    }
    
    public String getCountry() {
        return country;
    }  
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public BillingStatementData getSelectedBillingStatement() {
        return selectedBillingStatement;
    }

    public void setSelectedBillingStatement(BillingStatementData selectedBillingStatement) {
        this.selectedBillingStatement = selectedBillingStatement;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.iRecNum = ((BillingStatementData) event.getObject()).iRecNum;
        this.iCheck = ((BillingStatementData) event.getObject()).iCheck;
        this.iDate = ((BillingStatementData) event.getObject()).iDate;
        this.cName = ((BillingStatementData) event.getObject()).cName;                  
        this.cSurname = ((BillingStatementData) event.getObject()).cSurname;             
        this.tel = ((BillingStatementData) event.getObject()).tel;             
        this.cell = ((BillingStatementData) event.getObject()).cell;             
        this.fax = ((BillingStatementData) event.getObject()).fax;             
        this.posAdd = ((BillingStatementData) event.getObject()).posAdd;             
        this.physAdd = ((BillingStatementData) event.getObject()).physAdd;             
        this.country = ((BillingStatementData) event.getObject()).country;  
        this.recurring = ((BillingStatementData) event.getObject()).recurring;  
        this.cid = ((BillingStatementData) event.getObject()).cid;  
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String param = "iCheck=" + iCheck + "&uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + iRecNum + "&iDate=" + iDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&country=" + country + "&recurring=" + recurring + "&cid=" + cid;

        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/accounting/billing/billingStatement/view-billingStatement.xhtml?" + param);

    }

    public void viewBillingStatement() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/billingStatement/view-billingStatement.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(BillingStatementData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
