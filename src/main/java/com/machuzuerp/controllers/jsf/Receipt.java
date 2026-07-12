package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import com.machuzuerp.entities.Client;
import com.machuzuerp.jdbc.CustomerProcessing;
import com.machuzuerp.jdbc.InventoryProcessing;
import com.machuzuerp.jdbc.ReceiptProcessing;
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
import javax.enterprise.context.SessionScoped;
import java.sql.Connection;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@SessionScoped
@Named
public class Receipt implements Serializable {

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
    private String recurring;
    private BigDecimal amtPaid = new BigDecimal("0");
    private BigDecimal change = new BigDecimal("0");
    private String invDate;
    BigDecimal outstandingAmount = new BigDecimal("0");
    private String cashPayment;

    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private BigDecimal totServCost = new BigDecimal("0");
    private BigDecimal totInvCost = new BigDecimal("0");
    private BigDecimal totCost = new BigDecimal("0");
    private BigDecimal taxAmount = new BigDecimal("0");

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

    ReceiptProcessing rp = new ReceiptProcessing();
//    ReceiptData rd = new ReceiptData();
  //  CustomerData cd = new CustomerData();
    CustomerProcessing cp = new CustomerProcessing();
    InventoryProcessing ip = new InventoryProcessing();
    Stakeholder cust = new Stakeholder();

    private List<ServiceData> allServices = new ArrayList<ServiceData>();
    private List<ServiceData> selectedServs;
    private List<InventoryData> selectedInvs;

    private List servQtys = new ArrayList();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Connection connection;
    java.sql.Statement statement;
    ResultSet results;
    
    HttpServletRequest request;

    public Receipt() {
        
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
    
    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
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
    
    public BigDecimal getOutstandingAmt() {
        return outstandingAmount;
    }
    
    public void setOutstandingAmt(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }
    
    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
        this.cashPayment = cashPayment;
    } 
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");

        rp.iRecNum = params.get("recnum");
        rp.iRecNum = this.iRecNum;
        rp.iCheck = this.iCheck;
        rp.iDate = "" + this.iDate;
        rp.cName = this.cName;
        rp.cSurname = this.cSurname;
        rp.tel = this.tel;
        rp.cell = this.cell;
        rp.fax = this.fax;
        rp.posAdd = this.posAdd;
        rp.physAdd = this.physAdd;
        rp.country = this.country;
        rp.recurring = this.recurring;
        rp.cid = this.cID;

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
        this.email = params.get("email");
        this.vatNum = params.get("vatNum");

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

        rp.iRecNum = params.get("recnum");
        rp.iRecNum = this.iRecNum;
        rp.iCheck = this.iCheck;
        rp.iDate = params.get(this.iDate);
        rp.cName = this.cName;
        rp.cSurname = this.cSurname;
        rp.tel = this.tel;
        rp.cell = this.cell;
        rp.fax = this.fax;
        rp.posAdd = this.posAdd;
        rp.physAdd = this.physAdd;
        rp.country = this.country;
        rp.recurring = this.recurring;
        rp.cid = this.cID;

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public String saveReceipt() {
        getParams();
        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            rp.editReceipt(request.getSession().getAttribute("uk").toString(), "", "", "");
            ret = "/billing/receipt/saved";
        } catch (Exception npe) {
            ret = "/billing/receipt/notsaved";
        }

        return ret;
    }

    public String saveEditReceipt() throws SQLException {

        getParams();

        String ret = "";

        try {

            rp.iRecNum = recnum;
            //sp.code = code;
            //sp.description = description;
            //sp.cost =  cost;

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            rp.editReceipt(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/receipt/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/receipt/notsaved";
        }

        return ret;
    }

    public String mainEditReceipt() throws SQLException {
        getParams();

        String ret = "";

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            rp.editReceipt(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/receipt/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/receipt/notsaved";
        }

        return ret;
    }

    public String editReceipt_1(String iRecNum, String iCheck, java.util.Date iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid) throws SQLException, ParseException {

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

        return "/billing/receipt/edit-receipt";
    }

    public void getReceipt() throws SQLException, ParseException {

        rp.getReceipt(recnum);

        this.iRecNum = rp.iRecNum;
        this.iCheck = rp.iCheck;
        this.iDate = sdf.parse(rp.iDate);
        this.cName = rp.cName;
        this.cSurname = rp.cSurname;
        this.tel = rp.tel;
        this.cell = rp.cell;
        this.fax = rp.fax;
        this.posAdd = rp.posAdd;
        this.physAdd = rp.physAdd;
        this.country = rp.country;
        this.recurring = rp.recurring;
        this.cID = rp.cid;

    }

    public String deleteReceipt() throws SQLException {
        getParams();

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            rp.editReceipt(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/receipt/saved";
        } catch (NullPointerException npe) {
            ret = "/receipt/notsaved";
        }

        return ret;
    }

    public String selectService(String recnum) throws SQLException {
        getParams();

        String clientDat = cp.getCustomerArr(recnum);

        String[] vals = clientDat.split(",");                

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
        
        return "/accounting/billing/receipt/select-service";
    }

    public String selectInventory() {
        
        getAccParams();

        cost = new BigDecimal("0");
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = selectedServs.get(x).getQty();
            this.totServCost = this.totServCost.add((cost.multiply(BigDecimal.valueOf(quantity))));
            selectedServs.get(x).setTotCost((cost.multiply(BigDecimal.valueOf(quantity))));
        }

        return "/accounting/billing/receipt/select-inventory";
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
            this.totServCost = this.totServCost.add((cost.multiply(BigDecimal.valueOf(quantity))));
            selectedServs.get(x).setTotCost((cost.multiply(BigDecimal.valueOf(quantity))));
        }

        invCost = new BigDecimal("0");
        count = selectedInvs.size();

        for (int x = 0; x < count; x++) {
            selectedInvs.iterator().next();
            invCost = selectedInvs.get(x).getSellingPrice();
            quantity = selectedInvs.get(x).getQuantity();
            this.totInvCost = this.totInvCost.add((invCost.multiply(BigDecimal.valueOf(quantity))));
            selectedInvs.get(x).setTotCost((invCost.multiply(BigDecimal.valueOf(quantity))));
        }

        this.totCost = totInvCost.add(totServCost);

        return "/accounting/billing/receipt/receipt-summary";
    }

    public String invComplete(List<ServiceData> selectedServs, List<InventoryData> selectedInvs) throws IOException, SQLException {

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
              
        invDate = sdf.format(iDate);

            int maxRec = rp.getMaxRec();
            maxRec++;
            
            iRecNum = ""+maxRec;
                
            totServCost = new BigDecimal("0");
            totInvCost = new BigDecimal("0");
            cost = new BigDecimal("0");
            count = selectedServs.size();

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
            for (int x = 0; x < count; x++) {
                selectedServs.iterator().next();
                cost = selectedServs.get(x).getCost();
                quantity = selectedServs.get(x).getQty();
                this.totServCost = this.totServCost.add((cost.multiply(BigDecimal.valueOf(quantity))));
                selectedServs.get(x).setTotCost((cost.multiply(BigDecimal.valueOf(quantity))));
                rp.saveReceiptServices(request.getSession().getAttribute("uk").toString(), ""+maxRec, selectedServs.get(x).getDescription(), selectedServs.get(x).getCost(), selectedServs.get(x).getQty(), (selectedServs.get(x).getCost().multiply(BigDecimal.valueOf(selectedServs.get(x).getQty()))), selectedServs.get(x).getRecNum());               
            }

            invCost = new BigDecimal("0");
            count = selectedInvs.size();

            for (int x = 0; x < count; x++) {
                selectedInvs.iterator().next();
                invCost = selectedInvs.get(x).getSellingPrice();
                quantity = selectedInvs.get(x).getQuantity();
                this.totInvCost = this.totInvCost.add((invCost.multiply(BigDecimal.valueOf(quantity))));
                selectedInvs.get(x).setTotCost((invCost.multiply(BigDecimal.valueOf(quantity))));
                rp.saveReceiptInventory(request.getSession().getAttribute("uk").toString(), ""+maxRec, selectedInvs.get(x).getDescription(), selectedInvs.get(x).getCost(), selectedInvs.get(x).getQuantity(), (selectedInvs.get(x).getSellingPrice().multiply(BigDecimal.valueOf(selectedInvs.get(x).getQuantity()))), selectedInvs.get(x).getRecNum(), selectedInvs.get(x).getAtHand());
            }

            this.totCost = totInvCost.add(totServCost);

            //this.change = rp.saveReceipt(invDate, cID, cName, cSurname, tel, cell, fax, posAdd, physAdd, recurring, totCost, taxAmount, amtPaid, request.getSession().getAttribute("uk").toString(), "", email, cashPayment);
            this.outstandingAmount = rp.outstandingAmount;

        return "/accounting/billing/receipt/receipt-complete";
    }

    public void posComplete(List<InventoryData> selectedPOSInvs, Client client, String paymentMethod, BigDecimal totCost, BigDecimal taxAmount, HttpSession session) throws IOException, SQLException {

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
        
        CustomerProcessing cp = new CustomerProcessing();

        results = cp.getCustomerResultset(client.getId());
        while (results.next()) {

            tel = results.getString(8);
            cell = results.getString(9);
            fax = results.getString(10);
            email = results.getString(18);
            posAdd = results.getString(11);
            physAdd = results.getString(12);           

        }        

        invDate = sdf.format(new Date());

        int maxRec = rp.getMaxRec();
        maxRec++;

        iRecNum = ""+maxRec;

        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");

        invCost = new BigDecimal("0");
        count = selectedPOSInvs.size();

        for (InventoryData inv: selectedPOSInvs) {

            invCost = inv.getSellingPrice();

            quantity = inv.getQuantity();

            this.totInvCost = this.totInvCost.add(invCost.multiply(BigDecimal.valueOf(quantity)));

            inv.setTotCost(invCost.multiply(BigDecimal.valueOf(quantity)));
//ip.updateInventory(inv.getRecNum(), (inv.getAtHand() - quantity));            

            rp.saveReceiptInventory(session.getAttribute("uk").toString(), ""+maxRec, inv.getDescription(), inv.getCost(), inv.getQuantity(), (inv.getSellingPrice().multiply(BigDecimal.valueOf(inv.getQuantity()))), inv.getRecNum(), inv.getAtHand());
        }

        this.totCost = totInvCost.add(totServCost);

        //this.change = rp.saveReceipt(sdf.format(new Date()), client.getId(), client.getName(), "", tel, cell, fax, posAdd, physAdd, "", totCost, taxAmount, amtPaid, session.getAttribute("uk").toString(), "", email, cashPayment);
        this.outstandingAmount = rp.outstandingAmount;

        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        invCost = new BigDecimal("0");        
        totCost = new BigDecimal("0");        
        change = new BigDecimal("0");  
        amtPaid = new BigDecimal("0");                  
System.out.println("POS COMPLETE");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Receipt Saved Successfully"));
    }

    public String newReceipt() {

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

        return "/accounting/billing/receipt/customer-listing";
    }

    public String findReceipt() {
        getParams();
       
        return "/accounting/billing/receipt/find-receipt";
    }
    
    public String findReceipt1(HttpSession session) {
        getParams();

        session.setAttribute("receiptData", null);
        
        return "/accounting/billing/receipt/find-receipt-1";
    }

    public String printReceipt() {
        getParams();
        return "/accounting/billing/receipt/print-receipt";
    }

    public void printPDF() throws IOException {

        getAccParams();

        FacesContext.getCurrentInstance().getExternalContext().redirect("print-receipt.jsp?cName=" + cName + "&cSurname=" + cSurname + "&cell=" + cell + "&tel=" + tel + "&posAdd=" + posAdd + "&servDesc=" + servDesc + "&invDesc=" + invDesc + "&sCost=" + sCost + "&iCost=" + iCost);

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
