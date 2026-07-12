/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import com.machuzuerp.jdbc.CustomerProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.jdbc.QuotationProcessing;
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
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@SessionScoped
@Named
public class Quotation implements Serializable {

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
    private String email;
    private String vatNum;
    private String posAdd;
    private String physAdd;
    private String country;
    private String notes;
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
    String query = "";

    BigDecimal invCost = new BigDecimal("0");
    BigDecimal cost = new BigDecimal("0");
    int count = 0;

    int quantity = 0;

    QuotationProcessing qp = new QuotationProcessing();
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

    public Quotation() {
        
    }
    
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

        qp.iRecNum = params.get("recnum");
        qp.iRecNum = this.iRecNum;
        qp.iCheck = this.iCheck;
        qp.iDate = "" + this.iDate;
        qp.cName = this.cName;
        qp.cSurname = this.cSurname;
        qp.tel = this.tel;
        qp.cell = this.cell;
        qp.fax = this.fax;
        qp.posAdd = this.posAdd;
        qp.physAdd = this.physAdd;
        qp.country = this.country;
        qp.notes = this.notes;
        qp.cid = this.cID;

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
        this.vatNum = params.get("vatNum");
        this.email = params.get("email");

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
        this.notes = params.get("notes");
        this.cID = params.get("cid");

        qp.iRecNum = params.get("recnum");
        qp.iRecNum = this.iRecNum;
        qp.iCheck = this.iCheck;
        qp.iDate = params.get(this.iDate);
        qp.cName = this.cName;
        qp.cSurname = this.cSurname;
        qp.tel = this.tel;
        qp.cell = this.cell;
        qp.fax = this.fax;
        qp.posAdd = this.posAdd;
        qp.physAdd = this.physAdd;
        qp.country = this.country;
        qp.notes = this.notes;
        qp.cid = this.cID;

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public String saveQuotation() {
        getParams();
        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            qp.editQuotation(request.getSession().getAttribute("uk").toString(), "", "", "");
            ret = "/billing/quotation/saved";
        } catch (Exception npe) {
            ret = "/billing/quotation/notsaved";
        }

        return ret;
    }

    public String saveEditQuotation() throws SQLException {

        getParams();

        String ret = "";

        try {

            qp.iRecNum = recnum;
            //sp.code = code;
            //sp.description = description;
            //sp.cost =  cost;

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            qp.editQuotation(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/quotation/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/quotation/notsaved";
        }

        return ret;
    }

    public String mainEditQuotation() throws SQLException {
        getParams();

        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            qp.editQuotation(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/quotation/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/quotation/notsaved";
        }

        return ret;
    }

    public String editQuotation_1(String iRecNum, String iCheck, java.util.Date iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String notes, String cid) throws SQLException, ParseException {

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
        this.notes = notes;
        this.cID = cid;

        return "/billing/quotation/edit-quotation";
    }

    public void getQuotation() throws SQLException, ParseException {

        qp.getQuotation(recnum);

        this.iRecNum = qp.iRecNum;
        this.iCheck = qp.iCheck;
        this.iDate = sdf.parse(qp.iDate);
        this.cName = qp.cName;
        this.cSurname = qp.cSurname;
        this.tel = qp.tel;
        this.cell = qp.cell;
        this.fax = qp.fax;
        this.posAdd = qp.posAdd;
        this.physAdd = qp.physAdd;
        this.country = qp.country;
        this.notes = qp.notes;
        this.cID = qp.cid;

    }

    public String deleteQuotation() throws SQLException {
        getParams();

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            qp.editQuotation(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/quotation/saved";
        } catch (NullPointerException npe) {
            ret = "/quotation/notsaved";
        }

        return ret;
    }

    public String selectService(String recnum) throws SQLException {
        getParams();

        String clientDat = cp.getCustomerArr(recnum);

        String[] vals = clientDat.split(",");                
        System.out.println("vals: "+clientDat);
        cID = vals[0];
        cName = vals[1];
        cSurname = vals[2];
        tel = vals[3];
        cell = vals[4];
        fax = vals[5];
        email = vals[6];
        posAdd = vals[7];
        physAdd = vals[8];
        vatNum = vals[9];

        return "/accounting/billing/quotation/select-service";
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
        } catch (Exception e) {}
        
        return "/accounting/billing/quotation/select-inventory";
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

        return "/accounting/billing/quotation/quotation-summary";
    }

    public String invComplete(List<ServiceData> selectedServs, List<InventoryData> selectedInvs) throws IOException, SQLException {
        getAccParams();

        UserProcessing up = new UserProcessing();
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

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

        invDate = sdf.format(iDate);

            String maxRec = "";
            try {
                maxRec = qp.saveQuotation(invDate, vatNum, cID, cName, cSurname, tel, cell, email, posAdd, physAdd, notes, request.getSession().getAttribute("uk").toString());

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
                    qp.saveQuotationServices(request.getSession().getAttribute("uk").toString(), maxRec, selectedServs.get(x).getDescription(), selectedServs.get(x).getCost(), selectedServs.get(x).getQty(), selectedServs.get(x).getCost().multiply(BigDecimal.valueOf(selectedServs.get(x).getQty())), selectedServs.get(x).getRecNum());
                }

                invCost = new BigDecimal("0");
                count = selectedInvs.size();

                for (int x = 0; x < count; x++) {
                    selectedInvs.iterator().next();
                    invCost = selectedInvs.get(x).getSellingPrice();
                    quantity = selectedInvs.get(x).getQuantity();
                    this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));
                    selectedInvs.get(x).setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
                    qp.saveQuotationInventory(request.getSession().getAttribute("uk").toString(), maxRec, selectedInvs.get(x).getDescription(), selectedInvs.get(x).getCost(), selectedInvs.get(x).getQuantity(), selectedInvs.get(x).getSellingPrice().multiply(BigDecimal.valueOf(selectedInvs.get(x).getQuantity())), selectedInvs.get(x).getRecNum());
                }

                this.totCost = totInvCost.add(totServCost);
                
                ret = "/accounting/billing/quotation/quotation-complete";

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Occurred", e.toString()));
                
                ret = "";
            }
                        
        return ret;
    }

    public String newQuotation() {

        getParams();

        selectedServs = null;
        selectedInvs = null;
        this.totServCost = new BigDecimal("0");
        this.totInvCost = new BigDecimal("0");
        this.totCost = new BigDecimal("0");
        notes = "";
        amtPaid = new BigDecimal("0");
        invDate = "";
        iDate = null;

        return "/accounting/billing/quotation/customer-listing";
    }

    public String findQuotation(HttpSession session) {
        getParams();
        
        session.setAttribute("quotationData", null);
        
        return "/accounting/billing/quotation/find-quotation";
    }

    public String printQuotation() {
        getParams();
        return "/accounting/billing/quotation/print-quotation";
    }

    public void printPDF() throws IOException {

        getAccParams();

        FacesContext.getCurrentInstance().getExternalContext().redirect("print-quotation.jsp?cName=" + cName + "&cSurname=" + cSurname + "&cell=" + cell + "&tel=" + tel + "&posAdd=" + posAdd + "&servDesc=" + servDesc + "&invDesc=" + invDesc + "&sCost=" + sCost + "&iCost=" + iCost);

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

    }
}
