/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.controllers.jsf.Invoice;
import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.jsf.Quotation;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class QuotationData implements Serializable {

    /**
     * Creates a new instance of QuotationData
     */
    Connection connection;
    Statement statement;
    ResultSet results;
 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String iRecNum;
    private String iCheck;
    private String iDate;
    private String cName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String email;
    private String vatNum;
    private String posAdd;
    private String physAdd;
    private String country;
    private String recurring;
    private String custid;
    
    private Date dFrom;
    private Date dTo;
    
    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;
    
    private BigDecimal totServCost;
    private BigDecimal totInvCost;
    private BigDecimal totCost;
    BigDecimal invCost = new BigDecimal("0");
    BigDecimal cost = new BigDecimal("0");
    int count = 0;
    int quantity = 0;

    String quotationRecNum = "";

    private List<QuotationData> quotations = new ArrayList<QuotationData>();
    private List<QuotationData> allQuotations = new ArrayList<QuotationData>();
    private List<QuotationData> filteredQuotations = new ArrayList<QuotationData>();
    private QuotationData selectedQuotation;
    private List<Quotation> selectedInv;
    
    private List<ServiceData> selectedServices = new ArrayList<ServiceData>();
    private List<InventoryData> selectedInventory = new ArrayList<InventoryData>();
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";

    public QuotationData() {
        
    }

    public QuotationData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String email, String posAdd, String physAdd, String country, String recurring, String custid, String vatNum) {

        this.iRecNum = iRecNum;
        this.iCheck = iCheck;
        this.iDate = iDate;
        this.cName = cName;
        this.cSurname = cSurname;
        this.tel = tel;
        this.cell = cell;
        this.email = email;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.country = country;
        this.recurring = recurring;
        this.custid = custid;
        this.vatNum = vatNum;
    }

    public String searchQuotation(Date dFrom, Date dTo) {

        quotations.clear();
        try {

            connection = Systems.initConnection();

            String from = sdf.format(dFrom);
            String to = sdf.format(dTo);

            int i = 0;               
            query = "SELECT * FROM quotations where quotationdate between '" + from + "' and '" + to + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
System.out.println("0000000: " + from + ":" + to);
            while (results.next()) {
                System.out.println("1111: " + results.getString(4));
                quotations.add(new QuotationData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getString(17)));

            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/billing/quotation/quote-listing";

    }

    public List<QuotationData> getAllQuotations() {

        allQuotations.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM quotations order by quotationcheck";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                quotationRecNum = results.getString(1);
                allQuotations.add(new QuotationData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15), results.getString(17)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println("ERR:"+sqle);
        }

        return allQuotations;
    }

    public void filterQuotations() {

        filteredQuotations.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM quotations where name like '%" + cName + "%' order by name";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                quotationRecNum = results.getString(1);
                filteredQuotations.add(new QuotationData(results.getString(1),results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11),results.getString(12),results.getString(13),results.getString(15), results.getString(17)));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    public List<Quotation> getSelectedInv() {
        return selectedInv;
    }
 
    public void setSelectedInv(List<Quotation> selectedInv) {
        this.selectedInv = selectedInv;
    }

    public List<QuotationData> getFilteredQuotations() {

        filterQuotations();

        return filteredQuotations;
    }

    public List<QuotationData> getQuotations() {

        return quotations;
    }

   /* public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }*/
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
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
    
    public String getCustId(){
        return custid;
    }

    public void setCustId(String custid) {
        this.custid = custid;
    }
    
    public List<ServiceData> getSelectedServs() {
        return selectedServices;
    }

    public void setSelectedServs(List<ServiceData> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public List<InventoryData> getSelectedInvs() {
        return selectedInventory;
    }

    public void setSelectedInvs(List<InventoryData> selectedInventory) {
        this.selectedInventory = selectedInventory;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPosAdd() {
        return orgPosAdd;
    }

    public void setOrgPosAdd(String orgPosAdd) {
        this.orgPosAdd = orgPosAdd;
    }

    public String getOrgPhysAdd() {
        return orgPhysAdd;
    }

    public void setOrgPhysAdd(String orgPhysAdd) {
        this.orgPhysAdd = orgPhysAdd;
    }       
    
    public BigDecimal getTotServCost() {
        return totServCost;
    }

    public void setTotServCost(BigDecimal totServCost) {
        this.totServCost = totServCost;
    }

    public BigDecimal getTotInvCost() {
        return totInvCost;
    }

    public void setTotInvCost(BigDecimal totInvCost) {
        this.totInvCost = totInvCost;
    }

    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }
    
    public Date getDFrom() {
        return dFrom;
    }

    public void setDFrom(Date dFrom) {
        this.dFrom = dFrom;
    }
    
    public Date getDTo() {
        return dTo;
    }

    public void setDTo(Date dTo) {
        this.dTo = dTo;
    }

    public QuotationData getSelectedQuotation() {
        return selectedQuotation;
    }

    public void setSelectedQuotation(QuotationData selectedQuotation) {
        this.selectedQuotation = selectedQuotation;
    }

    /*public void onRowSelect(SelectEvent event) throws IOException {

        getParams();
        getLoginParams();

        this.iRecNum = ((QuotationData) event.getObject()).iRecNum;
        this.iCheck = ((QuotationData) event.getObject()).iCheck;
        this.iDate = ((QuotationData) event.getObject()).iDate;
        this.cName = ((QuotationData) event.getObject()).cName;                  
        this.cSurname = ((QuotationData) event.getObject()).cSurname;             
        this.tel = ((QuotationData) event.getObject()).tel;             
        this.cell = ((QuotationData) event.getObject()).cell;             
        this.fax = ((QuotationData) event.getObject()).fax;             
        this.posAdd = ((QuotationData) event.getObject()).posAdd;             
        this.physAdd = ((QuotationData) event.getObject()).physAdd;             
        this.country = ((QuotationData) event.getObject()).country;  
        this.recurring = ((QuotationData) event.getObject()).recurring;  
        this.custid = ((QuotationData) event.getObject()).custid;  

        try {
            this.cName = URLEncoder.encode(this.cName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QuotationData.class.getName()).log(Level.SEVERE, null, ex);
        }
        String param = "iCheck=" + iCheck + "&uk=" + uk + "&name=" + name + "&recnum=" + iRecNum + "&iDate=" + iDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&country=" + country + "&recurring=" + recurring + "&custid=" + custid;

        //FacesContext.getCurrentInstance().getExternalContext().redirect("/quotations/view-quotation.xhtml?" + param);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/accounting/billing/quotation/view-quotation.xhtml?" + param);

    }*/
    
    public void onRowSelect(SelectEvent event) throws IOException, SQLException {

        this.iRecNum = ((QuotationData) event.getObject()).iRecNum;
        this.iCheck = ((QuotationData) event.getObject()).iCheck;
        this.iDate = ((QuotationData) event.getObject()).iDate;
        this.vatNum = ((QuotationData) event.getObject()).vatNum;        
        this.cName = ((QuotationData) event.getObject()).cName;        
        this.cSurname = ((QuotationData) event.getObject()).cSurname;
        this.tel = ((QuotationData) event.getObject()).tel;
        this.cell = ((QuotationData) event.getObject()).cell;
        this.fax = ((QuotationData) event.getObject()).fax;
        this.email = ((QuotationData) event.getObject()).email;
        this.posAdd = ((QuotationData) event.getObject()).posAdd;
        this.physAdd = ((QuotationData) event.getObject()).physAdd;
        this.country = ((QuotationData) event.getObject()).country;
        this.recurring = ((QuotationData) event.getObject()).recurring;
        this.custid = ((QuotationData) event.getObject()).custid;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();   
        String param = "iCheck=" + iCheck + "&recnum=" + iRecNum + "&iDate=" + iDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&country=" + country + "&recurring=" + recurring + "&custid=" + custid;

        selectedServices.clear();
        selectedInventory.clear();
        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM quotationitems where QuotationCheck = '" + iRecNum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {

                if (results.getString(9) != null) {
                    query = "SELECT * FROM services where pk = '" + results.getString(9) + "'";
                    statement = connection.createStatement();
                    ResultSet services = statement.executeQuery(query);

                    while (services.next()) {
                        selectedServices.add(new ServiceData(services.getString(1), services.getString(2), services.getString(3), results.getBigDecimal(5), results.getInt(6), results.getBigDecimal(5)));
                    }
                }

                if (results.getString(8) != null) {
                    query = "SELECT * FROM results where recnum = '" + results.getString(8) + "'";
                    statement = connection.createStatement();
                    results = statement.executeQuery(query);

                    int x = 0;
                    while (results.next()) {
                        selectedInventory.add(new InventoryData(results.getString(1), results.getString(2), results.getString(3), results.getInt(4), results.getString(5), results.getBigDecimal(6), results.getBigDecimal(7), results.getInt(8), results.getBigDecimal(9), results.getInt(10), results.getString(11), results.getString(12), results.getInt(6), results.getBigDecimal(5), results.getInt(20), results.getString(21), results.getString(9),0));
                        
                        x++;
                    }
                }

            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        //invoice.invView(selectedServices, selectedInventory, iRecNum, iDate);
        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        count = selectedServices.size();

        for (int x = 0; x < count; x++) {
            selectedServices.iterator().next();
            cost = selectedServices.get(x).getCost();
            quantity = selectedServices.get(x).getQty();
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            selectedServices.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
        }

        invCost = new BigDecimal("0");
        count = selectedInventory.size();

        for (int x = 0; x < count; x++) {
            selectedInventory.iterator().next();
            invCost = selectedInventory.get(x).getSellingPrice();
            quantity = selectedInventory.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
            selectedInventory.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
        }

        this.totCost = totInvCost.add(totServCost);

        UserProcessing up = new UserProcessing();

        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(11);
            orgName = results.getString(7);
            orgTel = results.getString(10);
            //orgFax = results.getString(5);
            orgEmail = results.getString(5);
            orgPosAdd = results.getString(9);
            orgPhysAdd = results.getString(8);

        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/quote-preview.xhtml?" + param);
        }
        

    }

    public void viewQuotation() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/quotations/view-quotation.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/quotation/view-quotation.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(QuotationData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
