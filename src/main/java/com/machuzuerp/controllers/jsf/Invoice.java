/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.CustomerData;
import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import com.machuzuerp.entities.ProjectInventoryView;
import com.machuzuerp.jdbc.CustomerProcessing;
import com.machuzuerp.jdbc.EmployeesProcessing;
import com.machuzuerp.jdbc.InvoiceProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.event.SelectEvent;
import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.Statement;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@SessionScoped
@Named
public class Invoice implements Serializable {

    private String recnum;

    private String iRecNum;
    private String orderNumber;
    private String iCheck;
    private java.util.Date iDate;
    private String cID;
    private String cName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String email;
    private String posAdd;
    private String physAdd;
    private String country;
    private String recurring;
    private BigDecimal amtPaid;
    private String invDate;
    private String vatNum;
    private String notes;
    private String quoteId;

    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private String dFrom;
    private String dTo;

    private BigDecimal totServCost;
    private BigDecimal totInvCost;
    private BigDecimal totCost;

    String servDesc;
    String invDesc;
    String sCost;
    String iCost;

    String cDate;
    String ret = "";
    String query = "";

    BigDecimal invCost = new BigDecimal("0");
    BigDecimal cost = new BigDecimal("0");
    int count = 0;

    int quantity = 0;

    InvoiceProcessing ip = new InvoiceProcessing();
    CustomerProcessing cp = new CustomerProcessing();

    private List<ServiceData> allServices = new ArrayList<ServiceData>();
    private List<ServiceData> selectedServs;    
    private List<InventoryData> selectedInvs;

    private List servQtys = new ArrayList();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    List<ProjectInventoryView> projectInventoryView = new ArrayList<ProjectInventoryView>();

    public Invoice() {        
    }

    public List<ServiceData> getAllServices() {

        getParams();
        allServices.clear();

        /*try {

            String query = "";
            String serviceRecNum = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();
            query = "SELECT * FROM services order by description";
            statement = connection.createStatement();
            ResultSet cfs = statement.executeQuery(query);

            proceed = true;
            while (cfs.next()) {
                proceed = false;
                serviceRecNum = cfs.getString(1);
                allServices.add(new ServiceData(cfs.getString(1),cfs.getString(2), cfs.getString(3), Float.parseFloat(cfs.getString(7)),0));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }*/
        //allServices.add(new ServiceData("1", "007", "testServ", 350.45, 12, 0));

        return allServices;
    }

    /* public void filterServices() {

        getParams();

        filteredServices.clear();

        try {

            String query = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();
            query = "SELECT * FROM Services where description like '%" + description + "%' order by description";
            statement = connection.createStatement();
            ResultSet cfs = statement.executeQuery(query);

            int count = 0;
            proceed = true;
            while (cfs.next()) {
                proceed = false;
                serviceRecNum = cfs.getString(1);
                filteredServices.add(new ServiceData(cfs.getString(1), cfs.getString(2), cfs.getString(3), Float.parseFloat(cfs.getString(7)),0));
                count++;
            }

            this.count = count;

        } catch (Exception sqle) {
            System.out.println(sqle);
        }
        
    }*/

    public List<ServiceData> getSelectedServs() {
        return selectedServs;
    }

    public void setSelectedServs(List<ServiceData> selectedServs) {
        this.selectedServs = selectedServs;
    }

    public List<ProjectInventoryView> getProjectInventoryView() {
        return projectInventoryView;
    }

    public void setProjectInventoryView(List<ProjectInventoryView> projectInventoryView) {
        this.projectInventoryView = projectInventoryView;
    }

    public List<InventoryData> getSelectedInvs() {
        return selectedInvs;
    }

    public void setSelectedInvs(List<InventoryData> selectedInvs) {
        this.selectedInvs = selectedInvs;
    }

    public void setDFrom(String dFrom) {
        this.dFrom = dFrom;
    }

    public String getDFrom() {
        return dFrom;
    }

    public void setDTo(String dTo) {
        this.dTo = dTo;
    }

    public String getDTo() {
        return dTo;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getInvDate() {
        return invDate;
    }

    public BigDecimal getAmountPaid() {
        return amtPaid;
    }

    public void setAmountPaid(BigDecimal amtPaid) {
        this.amtPaid = amtPaid;
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

    public String getRecNum() {
        return iRecNum;
    }

    public void setRecNum(String iRecNum) {
        this.iRecNum = iRecNum;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInvCheck() {
        return iCheck;
    }

    public void setInvCheck(String iCheck) {
        this.iCheck = iCheck;
    }

    public java.util.Date getIDate() {
        return iDate;
    }

    public void setIDate(java.util.Date iDate) {
        this.iDate = iDate;
    }

    public String getCID() {
        return cID;
    }

    public void setCID(String cID) {
        this.cID = cID;
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

    public int getQty() {
        return quantity;
    }

    public void setQty(int quantity) {
        this.quantity = quantity;
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

    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");

        ip.iRecNum = params.get("recnum");
        ip.iRecNum = this.iRecNum;
        ip.iCheck = this.iCheck;
        ip.iDate = "" + this.iDate;
        ip.cName = this.cName;
        ip.cSurname = this.cSurname;
        ip.tel = this.tel;
        ip.cell = this.cell;
        ip.fax = this.fax;
        ip.posAdd = this.posAdd;
        ip.physAdd = this.physAdd;
        ip.country = this.country;
        ip.recurring = this.recurring;
        ip.cid = this.cID;
        ip.notes = this.notes;

    }

    public void getAccParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
       
        this.recnum = params.get("recnum");

        this.vatNum = params.get("vatNum");
        this.cName = params.get("cName");
        this.cSurname = params.get("cSurname");
        this.tel = params.get("tel");
        this.cell = params.get("cell");
        this.fax = params.get("fax");
        this.email = params.get("email");
        this.posAdd = params.get("posAdd");
        this.physAdd = params.get("physAdd");

    }

    public void getURLParams() throws ParseException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.recnum = params.get("recnum");
        this.iRecNum = params.get("irecnum");
        this.iCheck = params.get("icheck");
        this.iDate = sdf.parse(params.get("idate"));
        this.cName = params.get("cname");
        this.cSurname = params.get("csurname");
        this.tel = params.get("tel");
        this.cell = params.get("cell");
        this.fax = params.get("fax");
        this.posAdd = params.get("posadd");
        this.physAdd = params.get("physadd");
        this.country = params.get("country");
        this.recurring = params.get("recurring");
        this.cID = params.get("cid");
        this.notes = params.get("notes");
        
        ip.iRecNum = params.get("recnum");
        ip.iRecNum = this.iRecNum;
        ip.iCheck = this.iCheck;
        ip.iDate = params.get(this.iDate);
        ip.cName = this.cName;
        ip.cSurname = this.cSurname;
        ip.tel = this.tel;
        ip.cell = this.cell;
        ip.fax = this.fax;
        ip.posAdd = this.posAdd;
        ip.physAdd = this.physAdd;
        ip.country = this.country;
        ip.recurring = this.recurring;
        ip.cid = this.cID;
        ip.notes = this.notes;

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public String saveInvoice() {
        getParams();
        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            ip.editInvoice(request.getSession().getAttribute("uk").toString(), "", "", "");
            ret = "/billing/invoice/saved";
        } catch (SQLException npe) {
            ret = "/billing/invoice/notsaved";
        }

        return ret;
    }

    public String saveEditInvoice() throws SQLException {

        getParams();

        String ret = "";

        try {

            ip.iRecNum = recnum;
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            ip.editInvoice(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/invoice/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/invoice/notsaved";
        }

        return ret;
    }

    public String mainEditInvoice() throws SQLException {
        getParams();

        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            ip.editInvoice(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/invoice/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/invoice/notsaved";
        }

        return ret;
    }

    public String editInvoice_1(String iRecNum, String iCheck, java.util.Date iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid) throws SQLException, ParseException {

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
        this.cID = cid;

        return "/billing/invoice/edit-invoice";
    }

    public void getInvoice() throws SQLException, ParseException {

        ip.getInvoice(recnum);

        this.iRecNum = ip.iRecNum;
        this.iCheck = ip.iCheck;
        this.iDate = sdf.parse(ip.iDate);
        this.cName = ip.cName;
        this.cSurname = ip.cSurname;
        this.tel = ip.tel;
        this.cell = ip.cell;
        this.fax = ip.fax;
        this.posAdd = ip.posAdd;
        this.physAdd = ip.physAdd;
        this.country = ip.country;
        this.recurring = ip.recurring;
        this.cID = ip.cid;

    }

    public String deleteInvoice() throws SQLException {
        getParams();

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            ip.editInvoice(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/invoice/saved";
        } catch (NullPointerException npe) {
            ret = "/invoice/notsaved";
        }

        return ret;
    }

    public String selectService() throws SQLException {
        getParams();

       /* String clientDat = cp.getCustomerArr(customerData.getRecnum());

        String[] vals = clientDat.split(",");

        cID = vals[0];
        cName = vals[1];
        cSurname = vals[2];

        try {
            tel = vals[3];
        } catch (java.lang.ArrayIndexOutOfBoundsException aiobe) {
            tel = "-";
        }
        
        try {
            cell = vals[4];
        } catch (java.lang.ArrayIndexOutOfBoundsException aiobe) {
            cell = "-";
        }
        
        try {
            fax = vals[5];
        } catch (java.lang.ArrayIndexOutOfBoundsException aiobe) {
            fax = "-";
        }
        
        try {
            email = vals[6];
        } catch (java.lang.ArrayIndexOutOfBoundsException aiobe) {
            email = "-";
        }
        
        try {
            posAdd = vals[7];
        } catch (java.lang.ArrayIndexOutOfBoundsException aiobe) {
            posAdd = "-";
        }
        
        try {
            physAdd = vals[8];
        } catch (java.lang.ArrayIndexOutOfBoundsException aiobe) {
            physAdd = "-";
        }     
        
        try {
            vatNum = vals[9];
        } catch (java.lang.ArrayIndexOutOfBoundsException aiobe) {
            vatNum = "-";
        }      */  

        return "/accounting/billing/invoice/select-service";
    }
        
    public void selectServiceInventory(List<EnrolledServices> invoiceServices) throws IOException {
        getAccParams();

        cost = new BigDecimal("0");               
        count = invoiceServices.size();

        try {
            for (int x = 0; x < count; x++) {
                invoiceServices.iterator().next();
                cost = invoiceServices.get(x).getCost();
                quantity = 1;
                this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));                
            }
        } catch (Exception e) {}
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        String param = "cID=" + params.get("cID") + "&cName=" + params.get("cName") + "&cSurname=" + params.get("cSurname") + "&tel=" + params.get("tel") + "&cell=" 
                + params.get("cell") + "&fax=" + params.get("fax") + "&email=" + params.get("email") + "&posAd=" + params.get("posAd") + "&physAd=" + params.get("physAd") + "&vatNum=" + params.get("vatNum");
           
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/select-service-inventory.xhtml?" + param);
        }
                
    }
    public String selectInventory() {
        getAccParams();

        cost = new BigDecimal("0");
        
        count = selectedServs.size();

        try {
            for (int x = 0; x < count; x++) {
                selectedServs.iterator().next();
                cost = selectedServs.get(x).getCost();
                quantity = selectedServs.get(x).getQty();
                this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
                selectedServs.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
            }
        } catch(Exception e) {
            
        }
                   
        return "/accounting/billing/invoice/select-inventory";
    }

    public String invSummary(List<ServiceData> selectedServs) {
        getAccParams();

        this.orderNumber = "";
        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = selectedServs.get(x).getQty();
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            selectedServs.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
        }

        invCost = new BigDecimal("0");
        count = selectedInvs.size();

        for (int x = 0; x < count; x++) {
            selectedInvs.iterator().next();
            invCost = selectedInvs.get(x).getSellingPrice();
            quantity = selectedInvs.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
            selectedInvs.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
        }

        this.totCost = totInvCost.add(totServCost);

        return "/accounting/billing/invoice/invoice-summary";
    }

    public String QuoteInvSummary(List<ServiceData> selectedServs, List<InventoryData> selectedInvs, BigDecimal totCost, String custId, String vatNum, String CName, 
            String CSurname, String tel, String cell, String fax, String email, String posAdd, String physAdd, String quoteId) throws SQLException {

        getAccParams();
        
        this.quoteId = quoteId;
        this.cID = custId;
        this.cName = CName;
        this.cSurname = CSurname;
        this.tel = tel;
        this.cell = cell;
        this.fax = fax;
        this.email = email;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.vatNum = vatNum;
        /*this.country = custId;
        this.recurring = custId;
        this.amtPaid = custId;
        this.invDate = custId;*/

        this.orderNumber = "";
        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        
        this.selectedServs = selectedServs;
        this.selectedInvs = selectedInvs;
        
        count = selectedServs.size();

        this.totCost = totCost;
        
        UserProcessing up = new UserProcessing();

        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }

        return "/accounting/billing/invoice/invoice-summary";
    }    
    
    public String invServiceSummary(List<EnrolledServices> selectedServs) {
        getAccParams();

        this.orderNumber = "";
        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = 1;
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
        }

        invCost = new BigDecimal("0");
        count = selectedInvs.size();

        for (int x = 0; x < count; x++) {
            selectedInvs.iterator().next();
            invCost = selectedInvs.get(x).getSellingPrice();
            quantity = selectedInvs.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
            selectedInvs.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
        }

        this.totCost = totInvCost.add(totServCost);

        return "/accounting/billing/invoice/invoice-service-summary";
    }

    public String invComplete(List<ServiceData> selectedServs, List<InventoryData> selectedInvs, CustomerData customer) throws IOException, SQLException {

        getAccParams();

        UserProcessing up = new UserProcessing();
        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }        

        connection = Systems.initConnection();

        invDate = sdf.format(iDate);

        if (orderNumber.equals("")) {
            orderNumber = "0";
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String maxRec = ip.saveInvoice(invDate, customer.getRecnum(), customer.getVatNum(), customer.getCustomerName(), customer.getSurname(), customer.getTelephone(), customer.getCellphone(), customer.getEmail(), customer.getPostalAddress(), customer.getPhysicalAddress(), recurring, request.getSession().getAttribute("uk").toString(), "", orderNumber, vatNum, notes, quoteId,"");
        iRecNum = maxRec;

        try {
            totServCost = new BigDecimal(0);
            totInvCost = new BigDecimal(0);
            cost = new BigDecimal(0);
            count = selectedServs.size();

            for (int x = 0; x < count; x++) {
                selectedServs.iterator().next();
                cost = selectedServs.get(x).getCost();
                quantity = selectedServs.get(x).getQty();
                this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
                selectedServs.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
                ip.saveInvoiceServices(request.getSession().getAttribute("uk").toString(), maxRec, selectedServs.get(x).getDescription(), selectedServs.get(x).getCost(), selectedServs.get(x).getQty(), (selectedServs.get(x).getCost().multiply(BigDecimal.valueOf(selectedServs.get(x).getQty()))), selectedServs.get(x).getRecNum(), "");
            }
            

            invCost = new BigDecimal(0);
            count = selectedInvs.size();

            for (int x = 0; x < count; x++) {
                selectedInvs.iterator().next();
                invCost = selectedInvs.get(x).getSellingPrice();
                quantity = selectedInvs.get(x).getQuantity();
                this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
                selectedInvs.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
                ip.saveInvoiceInventory(request.getSession().getAttribute("uk").toString(), maxRec, selectedInvs.get(x).getDescription(), selectedInvs.get(x).getCost(), selectedInvs.get(x).getQuantity(), (selectedInvs.get(x).getSellingPrice().multiply(BigDecimal.valueOf(selectedInvs.get(x).getQuantity()))), selectedInvs.get(x).getRecNum(), "");
            }

            this.totCost = totInvCost.add(totServCost);
        } catch (Exception e) {}
        
        ip.updateInvoice(iRecNum, this.totCost);

        return "/accounting/billing/invoice/invoice-complete";
    }

    public String projectInvoiceComplete(List<ProjectInventoryView> projectInventoryView, String projectId) throws IOException, SQLException {

        getAccParams();

        UserProcessing up = new UserProcessing();
        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }        

        connection = Systems.initConnection();
        
        EmployeesProcessing ep = new EmployeesProcessing();
        results = ep.getEmployeeStakeholder(projectId, email);
        while (results.next()) {
            
            ep.getEmployee(results.getString(3));
            while (results1.next()){
                
                
                
                    
         /*    = results.getString(3);
            taxNumber = results.getString(4);
            employeeNumber = results.getString(5);
            employmentDate = results.getString(6);
            empName = results.getString(7);
            surname = results.getString(8);
            level = results.getString(19);
            gender = results.getString(9);
            telephone = results.getString(10);
            cellphone = results.getString(11);
            email = results.getString(12);
            postalAddress = results.getString(13);
            physicalAddress = results.getString(14);
            salary = results.getFloat(15);
            salaryInterval = results.getString(16);
            status = results.getString(17);
            position = results.getString(21);
            approver = results.getString(22);

            }            
*/         
             this.setCID(ep.idNumber); 
             this.setCName(cName);
             this.setCSurname(ep.surname); 
             this.setTel(ep.telephone); 
             this.setCell(ep.cellphone); 
             this.setEmail(ep.email); 
             this.setPosAdd(ep.postalAddress); 
             this.setPhysAdd(ep.physicalAddress); 
            }

        }

        invDate = sdf.format(iDate);

System.out.println("111: " + orgNumber + ":" + orgName + ":" + orgTel);
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (quoteId.length() < 1)
            quoteId = "0";

        String maxRec = ip.saveInvoice(invDate, cID, cID, cName, cSurname, tel, cell, email, posAdd, physAdd, recurring, request.getSession().getAttribute("uk").toString(), "", orderNumber, vatNum, notes, quoteId, projectId);
        iRecNum = maxRec;

        try {
            totServCost = new BigDecimal(0);
            totInvCost = new BigDecimal(0);
            cost = new BigDecimal(0);
            count = projectInventoryView.size();

            for (int x = 0; x < count; x++) {
                projectInventoryView.iterator().next();
                cost = projectInventoryView.get(x).getAmount();
                quantity = projectInventoryView.get(x).getQuantity();
                this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
                //projectInventoryView.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
                ip.saveInvoiceServices(request.getSession().getAttribute("uk").toString(), maxRec, projectInventoryView.get(x).getProjectDescription() + "-"+ projectInventoryView.get(x).getActivityDescription(), projectInventoryView.get(x).getPrice(), projectInventoryView.get(x).getQuantity(), (projectInventoryView.get(x).getPrice().multiply(BigDecimal.valueOf(projectInventoryView.get(x).getQuantity()))), projectInventoryView.get(x).getRecNum(), "");
                
//public void saveInvoiceServices(String uk, String maxRec, String description, BigDecimal cost, int qty, BigDecimal amount, String recNum, String orgid) throws SQLException {                
            }
            
            this.totCost = totInvCost.add(totServCost);
        } catch (Exception e) {}
        
        ip.updateInvoice(iRecNum, this.totCost);

        return "/accounting/billing/invoice/project-invoice-complete";
    }        
    
    public String invServiceComplete(List<EnrolledServices> selectedServs, List<InventoryData> selectedInvs, EnrolledServices enrolledServices) throws IOException, SQLException {

        getAccParams();

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        UserProcessing up = new UserProcessing();
        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }

        connection = Systems.initConnection();

        invDate = sdf.format(iDate);

        if (orderNumber.equals("")) {
            orderNumber = "0";
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        String maxRec = ip.saveInvoice(invDate, enrolledServices.getRecnum(), enrolledServices.getRecnum(), enrolledServices.getCustomerName(), enrolledServices.getSurname(), enrolledServices.getFax(), enrolledServices.getCellphone(), enrolledServices.getFax(), enrolledServices.getPostalAddress(), enrolledServices.getPhysicalAddress(), recurring, request.getSession().getAttribute("uk").toString(), "", "0", params.get("vatNum"), params.get("notes"), params.get("quoteId"),"0");
        iRecNum = maxRec;

        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = 1;
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            ip.saveInvoiceServices(request.getSession().getAttribute("uk").toString(), maxRec, selectedServs.get(x).getDescription(), selectedServs.get(x).getCost(), 1, selectedServs.get(x).getCost(), ""+selectedServs.get(x).getRecNum(), "");
        }

        invCost = new BigDecimal("0");
        count = selectedInvs.size();

        for (int x = 0; x < count; x++) {
            selectedInvs.iterator().next();
            invCost = selectedInvs.get(x).getSellingPrice();
            quantity = selectedInvs.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
            selectedInvs.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
            ip.saveInvoiceInventory(request.getSession().getAttribute("uk").toString(), maxRec, selectedInvs.get(x).getDescription(), selectedInvs.get(x).getCost(), selectedInvs.get(x).getQuantity(), (selectedInvs.get(x).getSellingPrice().multiply(BigDecimal.valueOf(selectedInvs.get(x).getQuantity()))), selectedInvs.get(x).getRecNum(), "");
        }

        this.totCost = totInvCost.add(totServCost);

        ip.updateInvoice(iRecNum, this.totCost);


        return "/accounting/billing/invoice/service-invoice-complete";
    }

    public void invView(List<ServiceData> selectedServs, List<InventoryData> selectedInvs, String iRecNum, String iDate) throws IOException, SQLException {

        getAccParams();
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        UserProcessing up = new UserProcessing();
        up.getOrganisation(request.getSession().getAttribute("uk").toString());

        if (up.orgNumber.equals("")) {
            orgNumber = "-";
        } else {
            orgNumber = up.orgNumber;
        }

        if (up.orgName.equals("")) {
            orgName = "-";
        } else {
            orgName = up.orgName;
        }

        if (up.orgTel.equals("")) {
            orgTel = "-";
        } else {
            orgTel = up.orgTel;
        }

        if (up.orgFax.equals("")) {
            orgFax = "-";
        } else {
            orgFax = up.orgFax;
        }

        if (up.orgEmail.equals("")) {
            orgEmail = "-";
        } else {
            orgEmail = up.orgEmail;
        }

        if (up.orgPhysAdd.equals("")) {
            orgPhysAdd = "-";
        } else {
            orgPhysAdd = up.orgPhysAdd;
        }

        if (up.orgPosAdd.equals("")) {
            orgPosAdd = "-";
        } else {
            orgPosAdd = up.orgPosAdd;
        }

        setSelectedInvs(selectedInvs);
        setSelectedServs(selectedServs);
     
        connection = Systems.initConnection();

        invDate = iDate;

        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = selectedServs.get(x).getQty();
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            selectedServs.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
        }

        invCost = new BigDecimal("0");
        count = selectedInvs.size();

        for (int x = 0; x < count; x++) {
            selectedInvs.iterator().next();
            invCost = selectedInvs.get(x).getSellingPrice();
            quantity = selectedInvs.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
            selectedInvs.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
        }

        this.totCost = totInvCost.add(totServCost);


    }

    public String newInvoice() {

        getParams();

        selectedServs = null;
        selectedInvs = null;
        this.totServCost = new BigDecimal("0");
        this.totInvCost = new BigDecimal("0");
        this.totCost = new BigDecimal("0");
        recurring = "";
        amtPaid = new BigDecimal("0");
        invDate = "";
        iDate = null;
        notes = "";

        return "/accounting/billing/invoice/customer-listing";
    }

    public String printInvoice() {
        getParams();
        return "/accounting/billing/invoice/print-invoice";
    }

    public void printPDF() throws IOException {

        getAccParams();

        FacesContext.getCurrentInstance().getExternalContext().redirect("print-invoice.jsp?cName=" + cName + "&cSurname=" + cSurname + "&cell=" + cell + "&tel=" + tel + "&posAdd=" + posAdd + "&servDesc=" + servDesc + "&invDesc=" + invDesc + "&sCost=" + sCost + "&iCost=" + iCost);

    }

    public void onDateSelect(SelectEvent event) {
        //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        //setJDate(format.format(event.getObject()));
        System.out.println("ONDATESELECT:" + iDate);
        // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        //     servQtys.add(Integer.parseInt(recnum),newValue);
        //   this.quantity = Float.parseFloat(""+newValue);
    }

    public String findInvoice(HttpSession session) {

        getParams();

        /*Enumeration keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            System.out.println(key + ": " + session.getValue(key).toString() + "<br>");
        }*/
        
        session.setAttribute("invoiceData", null);
        
        //session.removeAttribute("invoiceData");
        //session.invalidate();                

        return "/accounting/billing/invoice/find-invoice";
    }
}
