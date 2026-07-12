/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.jdbc.BillingStatementProcessing;
import com.machuzuerp.jdbc.CustomerProcessing;
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
import javax.enterprise.context.SessionScoped;
import java.sql.Connection;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CellEditEvent;

@SessionScoped
@Named
public class BillingStatement implements Serializable {

    private String recnum;

    private String iRecNum;
    private String iCheck;
    private java.util.Date iDate;
    private String cID;
    private String cName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String posAdd;
    private String physAdd;
    private String country;
    private String recurring;
    private BigDecimal amtPaid;
    private String invDate;  
    
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
    
    String servDesc;
    String invDesc;
    String sCost;
    String iCost;

    String cDate;
    String ret = "";
    String query ="";

    BigDecimal invCost = new BigDecimal("0");
    BigDecimal cost = new BigDecimal("0");
    int count = 0;
    
    int quantity = 0;

    BillingStatementProcessing ip = new BillingStatementProcessing();
    CustomerProcessing cp = new CustomerProcessing();
    //Stakeholder cust = new Stakeholder();

    private List<ServiceData> allServices = new ArrayList<ServiceData>();
    private List<ServiceData> selectedServs;
    private List<InventoryData> selectedInvs;
    
    private List servQtys = new ArrayList();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    Connection connection;
    Statement statement;
    ResultSet results;
    
    HttpServletRequest request;

    public BillingStatement() {
        
    }
     
    public List<ServiceData> getAllServices () {
        
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
        
//        allServices.add(new ServiceData("1", "007", "testServ", 350.45, 12,0));
    
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

    public List<InventoryData> getSelectedInvs() {
        return selectedInvs;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setSelectedInvs(List<InventoryData> selectedInvs) {
        this.selectedInvs = selectedInvs;
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

    }

    public void getAccParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");

        this.cID = params.get("cID");
        this.cName = params.get("cName");
        this.cSurname = params.get("cSurname");
        this.tel = params.get("tel");
        this.cell = params.get("cell");
        this.fax = params.get("fax");
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

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public String saveBillingStatement() {
        getParams();
        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            ip.editBillingStatement(request.getSession().getAttribute("uk").toString(), "", "", "");
            ret = "/billing/billingStatement/saved";
        } catch (Exception npe) {
            ret = "/billing/billingStatement/notsaved";
        }

        return ret;
    }

    public String saveEditBillingStatement() throws SQLException {

        getParams();

        String ret = "";

        try {

            ip.iRecNum = recnum;
            //sp.code = code;
            //sp.description = description;
            //sp.cost =  cost;

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            ip.editBillingStatement(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/billingStatement/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/billingStatement/notsaved";
        }

        return ret;
    }

    public String mainEditBillingStatement() throws SQLException {
        getParams();

        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            ip.editBillingStatement(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/billingStatement/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/billingStatement/notsaved";
        }

        return ret;
    }

    public String editBillingStatement_1(String iRecNum, String iCheck, java.util.Date iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid) throws SQLException, ParseException {

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

        return "/billing/billingStatement/edit-billingStatement";
    }

    public void getBillingStatement() throws SQLException, ParseException {

        ip.getBillingStatement(recnum);

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

    public String deleteBillingStatement() throws SQLException {
        getParams();

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            ip.editBillingStatement(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/billingStatement/saved";
        } catch (NullPointerException npe) {
            ret = "/billingStatement/notsaved";
        }

        return ret;
    }

    public String selectService(String recnum) throws SQLException {
        getParams();               
        
        String clientDat = cp.getCustomerArr(recnum);

        count = 0;
        cID = "";
        cName = "";
        cSurname = "";
        tel = "";
        cell = "";
        fax = "";
        posAdd = "";
        physAdd = "";
        for (int x = 0; x < clientDat.length(); x++) {

            if (clientDat.charAt(x) == ',') {
                count++;
            }
            
            if ((clientDat.charAt(x) != ',') & (count == 0)) {
                cID = cID + clientDat.charAt(x);
            }

            if ((clientDat.charAt(x) != ',') & (count == 1)) {
                cName = cName + clientDat.charAt(x);
            }

            if ((clientDat.charAt(x) != ',') & (count == 2)) {
                cSurname = cSurname + clientDat.charAt(x);
            }

            if ((clientDat.charAt(x) != ',') & (count == 3)) {
                tel = tel + clientDat.charAt(x);
            }

            if ((clientDat.charAt(x) != ',') & (count == 4)) {
                cell = cell + clientDat.charAt(x);
            }

            if ((clientDat.charAt(x) != ',') & (count == 5)) {
                fax = fax + clientDat.charAt(x);
            }

            if ((clientDat.charAt(x) != ',') & (count == 6)) {
                posAdd = posAdd + clientDat.charAt(x);
            }

            if ((clientDat.charAt(x) != ',') & (count == 7)) {
                physAdd = physAdd + clientDat.charAt(x);
            }

        }

        return "/accounting/billing/billingStatement/select-service";
    }

    public String selectInventory() {
        getAccParams();        

        cost = new BigDecimal("0");
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = selectedServs.get(x).getQty();            
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            selectedServs.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
        }

        return "/accounting/billing/billingStatement/select-inventory";
    }

    public String invSummary(List<ServiceData> selectedServs) {
        getAccParams();

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

        return "/accounting/billing/billingStatement/billingStatement-summary";
    }

    public String invComplete(List<ServiceData> selectedServs, List<InventoryData> selectedInvs) throws IOException, SQLException {
        getAccParams();
        
        UserProcessing up = new UserProcessing();
       // up.getOrganisation(request.getSession().getAttribute("uk").toString()); 
        
        /*if (up.orgNumber.equals("")) {
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
            orgTel= up.orgTel;
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
            orgPosAdd= up.orgPhysAdd;
        }
        
        if (up.orgPhysAdd.equals("")) {
            orgPhysAdd = "-";
        } else {
            orgPhysAdd= up.orgPhysAdd;
        }*/

        connection = Systems.initConnection();        

        invDate = sdf.format(iDate);                                  
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
        String maxRec = ip.saveBillingStatement(invDate, cID, cName, cSurname, tel, cell, fax, posAdd, physAdd, recurring, request.getSession().getAttribute("uk").toString(), "");
        iRecNum = maxRec;

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
            ip.saveBillingStatementServices(request.getSession().getAttribute("uk").toString(), maxRec, selectedServs.get(x).getDescription(), selectedServs.get(x).getCost(), selectedServs.get(x).getQty(), (selectedServs.get(x).getCost().multiply(BigDecimal.valueOf(selectedServs.get(x).getQty()))), selectedServs.get(x).getRecNum(), "");
        }

        invCost = new BigDecimal("0");
        count = selectedInvs.size();

        for (int x = 0; x < count; x++) {
            selectedInvs.iterator().next();
            invCost = selectedInvs.get(x).getSellingPrice();
            quantity = selectedInvs.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
            selectedInvs.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
            ip.saveBillingStatementInventory(request.getSession().getAttribute("uk").toString(), maxRec, selectedInvs.get(x).getDescription(), selectedInvs.get(x).getCost(), selectedInvs.get(x).getQuantity(), (selectedInvs.get(x).getSellingPrice().multiply(BigDecimal.valueOf(selectedInvs.get(x).getQuantity()))), selectedInvs.get(x).getRecNum(), "");
        }

        this.totCost = totInvCost.add(totServCost);

        return "/accounting/billing/billingStatement/billingStatement-complete";
    }

    public String newBillingStatement() {

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

        return "/accounting/billing/billingStatement/customer-listing";
    }

    public String findBillingStatement() {
        getParams();
        return "/accounting/billing/billingStatement/find-billingStatement";
    }

    public String printBillingStatement() {
        getParams();
        return "/accounting/billing/billingStatement/print-billingStatement";
    }

    public void printPDF() throws IOException {
        
        getAccParams();

        FacesContext.getCurrentInstance().getExternalContext().redirect("print-billingStatement.jsp?cName=" + cName +"&cSurname=" + cSurname + "&cell=" + cell + "&tel=" + tel + "&posAdd=" + posAdd + "&servDesc=" + servDesc + "&invDesc=" + invDesc + "&sCost=" + sCost + "&iCost=" + iCost);
        
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
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);            
        }
                 
     


        
   //     servQtys.add(Integer.parseInt(recnum),newValue);
        
     //   this.quantity = Float.parseFloat(""+newValue);
    }
}
