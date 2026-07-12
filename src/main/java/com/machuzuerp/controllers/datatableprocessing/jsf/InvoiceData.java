
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.ClientsBean;
import com.machuzuerp.jdbc.InvoiceProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.entities.Client;
import com.machuzuerp.controllers.jsf.Invoice;
import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.entities.ProjectInventoryView;
import com.machuzuerp.jdbc.CustomerProcessing;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class InvoiceData implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String iRecNum;
    private String orderNum;
    private String iCheck;
    private String iDate;
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
    private String cid;
    private String vatNum;
    private String notes;
    private String quoteId;

    private Date dFrom;
    private Date dTo;

    String invoiceRecNum = "";

    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private BigDecimal Days30;
    private BigDecimal Days60;
    private BigDecimal interest;

    private BigDecimal totServCost;
    private BigDecimal totInvCost;
    private BigDecimal totPay;
    private BigDecimal totCost;
    private BigDecimal outstanding;
    private BigDecimal payment;
    private Date paymentDate;  
    
    private BigDecimal taxAmount;

    BigDecimal invCost = new BigDecimal("0");
    BigDecimal cost = new BigDecimal("0");
    int count = 0;
    int quantity = 0;

    private String to;

    private List<InvoiceData> invoices = new ArrayList<InvoiceData>();
    private List<InvoiceData> projectInvoices = new ArrayList<InvoiceData>();
    private List<InvoiceData> allInvoices = new ArrayList<InvoiceData>();
    private List<InvoiceData> filteredInvoices = new ArrayList<InvoiceData>();
    private InvoiceData selectedInvoice;
    private List<Invoice> selectedInv;
    private List<ServiceData> selectedServices = new ArrayList<ServiceData>();
    private List<InventoryData> selectedInventory = new ArrayList<InventoryData>();
    private List<InventoryData> selectedPayments = new ArrayList<InventoryData>();
    private List<ProjectInventoryView> projectInventoryView = new ArrayList<ProjectInventoryView>();

    InvoiceProcessing ip = new InvoiceProcessing();
    CustomerProcessing cp = new CustomerProcessing();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";
    
    private Client selectedClient;
    
    public InvoiceData() {
        
    }

    public InvoiceData(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String email, 
            String posAdd, String physAdd, String country, String recurring, String cid, BigDecimal total, BigDecimal outstanding, String notes, String quoteId) {

            this.iRecNum = iRecNum;
            this.iCheck = iCheck;
            this.iDate = iDate;
            this.cName = cName;
            this.cSurname = cSurname;
            this.tel = tel;
            this.cell = cell;
            this.fax = fax;
            this.email = email;
            this.posAdd = posAdd;
            this.physAdd = physAdd;
            this.country = country;
            this.recurring = recurring;
            this.cid = cid;
            this.outstanding = outstanding;
            this.totCost = total;
            this.notes = notes;
            this.quoteId = quoteId;
            
            this.dFrom = null;
            this.dTo = null;
            
        
    }
    
    public List<ProjectInventoryView> getProjectInventoryView() {
        return projectInventoryView;
    }
 
    public void setProjectInventoryView(List<ProjectInventoryView> projectInventoryView) {
        this.projectInventoryView = projectInventoryView;
    }

    public Client getSelectedClient() {
        return selectedClient;
    }
 
    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
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

    public List<InventoryData> getSelectedPayments() {
        return selectedPayments;
    }

    public void setSelectedPayments(List<InventoryData> selectedPayments) {
        this.selectedPayments = selectedPayments;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
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

    public String searchInvoice(Date dFrom, Date dTo) {

        invoices.clear();
        try {

            connection = Systems.initConnection();

            String from = sdf.format(dFrom);
            String to = sdf.format(dTo);

            int i = 0;               
            query = "SELECT * FROM invoices where invoiceDate between '" + from + "' and '" + to + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                invoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/billing/invoice/invoice-listing";

    }

    public String searchInvoice() {

        invoices.clear();
        try {

            connection = Systems.initConnection();

            String from = sdf.format(dFrom);
            to = sdf.format(dTo);

            int i = 0;
            if (!cName.equals("")) {
                query = "SELECT * FROM invoices where name like '%" + cName + "%' and invoiceDate between '" + from + "' and '" + to + "'";
            } else if (!cSurname.equals("")) {
                query = "SELECT * FROM invoices where surname like '%" + cSurname + "%' and invoiceDate between '" + from + "' and '" + to + "'";
            }
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                invoiceRecNum = results.getString(1);                    
                invoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/billing/invoice/invoice-listing";

    }

    public List<InvoiceData> getAllInvoices() {

        allInvoices.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM invoices order by invoicecheck";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                invoiceRecNum = results.getString(1);
                allInvoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println("ERR:" + sqle);
        }

        return allInvoices;
    }

    public void filterInvoices() {

        filteredInvoices.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM invoices where name like '%" + cName + "%' order by name";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                invoiceRecNum = results.getString(1);
                filteredInvoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
            }
          
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    public String filterProjectInvoices(String projectId) {

        filteredInvoices.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM invoices where projectId = '" + projectId + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                filteredInvoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
            }
          
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        
        return "/accounting/billing/invoice/project-invoice-listing";

    }

    public List<Invoice> getSelectedInv() {
        return selectedInv;
    }

    public void setSelectedInv(List<Invoice> selectedInv) {
        this.selectedInv = selectedInv;
    }

    public List<InvoiceData> getFilteredProjectInvoices() {
        return filteredInvoices;
    }
    
    public List<InvoiceData> getFilteredInvoices() {

        filterInvoices();

        return filteredInvoices;
    }

    public List<InvoiceData> getInvoices() {
        return invoices;
    }
    
    public String loadProjectInvoices(String projectId) throws ParseException, SQLException {
        filterProjectInvoices(projectId);
        System.out.println("111: " + projectId + ":" + filteredInvoices);
        return "/accounting/billing/invoice/project-invoice-listing";
    }

    public String getRecNum() {
        return iRecNum;
    }

    public void setRecNum(String iRecNum) {
        this.iRecNum = iRecNum;
    }

    public String getOrderNumber() {
        return orderNum;
    }

    public void setOrderNumber(String orderNum) {
        this.orderNum = orderNum;
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

    public String getVatNumber() {
        return vatNum;
    }

    public void setVatNumber(String vatNum) {
        this.vatNum = vatNum;
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

    public String getCustId() {
        return cid;
    }

    public void setgetCustId(String cid) {
        this.cid = cid;
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

    public BigDecimal getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(BigDecimal outstanding) {
        this.outstanding = outstanding;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getDays30() {
        return Days30;
    }

    public void setDays30(BigDecimal Days30) {
        this.Days30 = Days30;
    }

    public BigDecimal getDays60() {
        return Days60;
    }

    public void setDays60(BigDecimal Days60) {
        this.Days30 = Days60;
    }

    public void getCustomerParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.iRecNum = params.get("recnum");
        this.cName = params.get("custname");
        this.cSurname = params.get("custsurname");
    }

    public InvoiceData getSelectedInvoice() {
        return selectedInvoice;
    }

    public void setSelectedInvoice(InvoiceData selectedInvoice) {
        this.selectedInvoice = selectedInvoice;
    }

    public String getOutstandingInvoices() throws SQLException {

        invoices.clear();

        getCustomerParams();

        connection = Systems.initConnection();
        
        query = "SELECT * FROM invoices where cid = '" + iRecNum + "' and outstanding > 0";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
//(String iRecNum, String iCheck, String iDate, String cName, String cSurname, String tel, String cell, String fax, String email, String posAdd, String physAdd, String country, String recurring, String cid, BigDecimal total, BigDecimal outstanding, String notes, String quoteId) {

        while (results.next()) {            
            invoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
        }

        return "/accounting/billing/invoice/invoice-listing";
    }

    public void onRowSelect(SelectEvent event) throws IOException, SQLException {
                
        this.iRecNum = ((InvoiceData) event.getObject()).iRecNum;
        this.iCheck = ((InvoiceData) event.getObject()).iCheck;
        this.iDate = ((InvoiceData) event.getObject()).iDate;
        this.cName = ((InvoiceData) event.getObject()).cName;
        this.notes = ((InvoiceData) event.getObject()).notes;
        this.quoteId = ((InvoiceData) event.getObject()).quoteId;

        if (((InvoiceData) event.getObject()).cSurname.equals("null")) {
            this.cSurname = "";
        } else {
            this.cSurname = ((InvoiceData) event.getObject()).cSurname;
        }

        if (((InvoiceData) event.getObject()).tel.equals("null")) {
            this.tel = "";
        } else {
            this.tel = ((InvoiceData) event.getObject()).tel;
        }

        if (((InvoiceData) event.getObject()).cell.equals("null")) {
            this.cell = "";
        } else {
            this.cell = ((InvoiceData) event.getObject()).cell;
        }

        if (((InvoiceData) event.getObject()).email.equals("null")) {
            this.email = "";
        } else {
            this.email = ((InvoiceData) event.getObject()).email;
        }

         if (((InvoiceData) event.getObject()).email.equals("null")) {
            this.email = "";
        } else {
            this.email = ((InvoiceData) event.getObject()).email;
        }

        if (((InvoiceData) event.getObject()).posAdd.equals("null")) {
            this.posAdd = "";
        } else {
            this.posAdd = ((InvoiceData) event.getObject()).posAdd;
        }
        
        if (((InvoiceData) event.getObject()).physAdd.equals("null")) {
            this.physAdd = "";
        } else {
            this.physAdd = ((InvoiceData) event.getObject()).physAdd;
        }
        
        /*if (((InvoiceData) event.getObject()).country.equals("null")) {
            this.country = "";
        } else {
            this.country = ((InvoiceData) event.getObject()).country;
        }*/
    
/*        try {
            this.cName = URLEncoder.encode(this.cName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(InvoiceData.class.getName()).log(Level.SEVERE, null, ex);
        }
  */      
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();         
        
        String param = "iCheck=" + iCheck + "&recnum=" + iRecNum + "&iDate=" + iDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&country=" + country + "&recurring=" + recurring + "&custId=" + cid + "&notes=" + notes;

        selectedServices.clear();
        selectedInventory.clear();
        selectedPayments.clear();
        try {

            connection = Systems.initConnection();

            query = "SELECT total, outstanding FROM invoices where recnum = '" + iRecNum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                this.totCost = results.getBigDecimal(1);
                this.outstanding = results.getBigDecimal(2);
            }

            
            query = "SELECT * FROM invoiceitems where InvoiceCheck = '" + iRecNum + "'";
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
                    query = "SELECT * FROM inventory where recnum = '" + results.getString(8) + "'";
                    statement = connection.createStatement();
                    ResultSet inventory = statement.executeQuery(query);

                    int x = 0;
                    while (inventory.next()) {
                        selectedInventory.add(new InventoryData(inventory.getString(1), inventory.getString(2), inventory.getString(3), inventory.getInt(4), inventory.getString(5), inventory.getBigDecimal(6), inventory.getBigDecimal(7), Integer.parseInt(inventory.getString(8)), inventory.getBigDecimal(9), inventory.getInt(10), inventory.getString(11), inventory.getString(12), 1, new BigDecimal("0"), results.getInt(20), results.getString(21), results.getString(9), x));
                        
                        x++;
                    }
                }

                if ((results.getString(8) == null) & (results.getString(9) == null)) {

                    selectedPayments.add(new InventoryData(results.getString(1), results.getString(2), results.getString(3), results.getInt(4), results.getString(5), results.getBigDecimal(6), results.getBigDecimal(7), Integer.parseInt(results.getString(8)), results.getBigDecimal(9), results.getInt(10), results.getString(11), results.getString(12), 1, new BigDecimal("0"), results.getInt(20), results.getString(21), results.getString(9), count));
                    //selectedPayments.add(new InventoryData(null, null, results.getString(3),0, null, 0, 0, 0, 0, 0, null, null, 0, Double.parseDouble(results.getString(7), results.getInt(20), results.getString(21))));

                }
                count++;
            }

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println("SERVLET ERROR: " + sqle);
        }

        //invoice.invView(selectedServices, selectedInventory, iRecNum, iDate);
        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        totPay = new BigDecimal("0");

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

        count = selectedPayments.size();
        for (int x = 0; x < count; x++) {
            selectedPayments.iterator().next();
            totPay = totPay.add(selectedPayments.get(x).getTotCost());
        }

        //this.totCost = totInvCost + totServCost + totPay;

        LocalDate from = LocalDate.parse(iDate);
        LocalDate to = LocalDate.parse(sdf.format(new Date()));

        long days = ChronoUnit.DAYS.between(from, to);

        query = "SELECT interest FROM Organisation";
        statement = connection.createStatement();
        ResultSet org = statement.executeQuery(query);

        while (org.next()) {
            interest = org.getBigDecimal(1);
        }

        if ((outstanding.compareTo(new BigDecimal("0"))) < 0) {
            if ((days >= 30) & (days < 60)) {
                Days30 = (totCost.multiply(interest)).add(totCost);
            } else if (days >= 60) {
                Days30 = (totCost.multiply(interest)).add(totCost);
                Days60 = (Days30.multiply(interest)).add(Days30);
            }
        }

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
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");
               
        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/invoice-preview.xhtml?" + param);
        }

    }

    public void onRowSelectProject(SelectEvent event) throws IOException, SQLException {

        //getClientsBean();
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        cp.getCustomer(request.getSession().getAttribute("projectStakeholderId").toString());
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                
        this.iRecNum = ((InvoiceData) event.getObject()).iRecNum;
        this.iCheck = ((InvoiceData) event.getObject()).iCheck;
        this.iDate = ((InvoiceData) event.getObject()).iDate;
        this.cName = request.getSession().getAttribute("projectName").toString() + "-" + request.getSession().getAttribute("projectStakeholder").toString();
        this.notes = ((InvoiceData) event.getObject()).notes;
        this.quoteId = ((InvoiceData) event.getObject()).quoteId;
        this.cSurname = cp.customerSurname;
        this.tel = cp.customerTelephone;
        this.cell = cp.customerCellphone;
        this.email = cp.customerEmail;
        this.posAdd = cp.customerPostalAddress;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();         
        
        String param = "iCheck=" + iCheck + "&recnum=" + iRecNum + "&iDate=" + iDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&country=" + country + "&recurring=" + recurring + "&custId=" + cid + "&notes=" + notes;

        selectedServices.clear();
        selectedInventory.clear();
        selectedPayments.clear();
        try {

            connection = Systems.initConnection();

            query = "SELECT total, outstanding FROM invoices where recnum = '" + iRecNum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                this.totCost = results.getBigDecimal(1);
                this.outstanding = results.getBigDecimal(2);
            }

            
            query = "SELECT * FROM invoiceitems where InvoiceCheck = '" + iRecNum + "'";
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
                    query = "SELECT * FROM inventory where recnum = '" + results.getString(8) + "'";
                    statement = connection.createStatement();
                    ResultSet inventory = statement.executeQuery(query);

                    int x = 0;
                    while (inventory.next()) {
                        selectedInventory.add(new InventoryData(inventory.getString(1), inventory.getString(2), inventory.getString(3), inventory.getInt(4), inventory.getString(5), inventory.getBigDecimal(6), inventory.getBigDecimal(7), Integer.parseInt(inventory.getString(8)), inventory.getBigDecimal(9), inventory.getInt(10), inventory.getString(11), inventory.getString(12), 1, new BigDecimal("0"), results.getInt(20), results.getString(21), results.getString(9), x));
                        
                        x++;
                    }
                }

                if ((results.getString(8) == null) & (results.getString(9) == null)) {

                    selectedPayments.add(new InventoryData(results.getString(1), results.getString(2), results.getString(3), results.getInt(4), results.getString(5), results.getBigDecimal(6), results.getBigDecimal(7), Integer.parseInt(results.getString(8)), results.getBigDecimal(9), results.getInt(10), results.getString(11), results.getString(12), 1, new BigDecimal("0"), results.getInt(20), results.getString(21), results.getString(9), count));
                    //selectedPayments.add(new InventoryData(null, null, results.getString(3),0, null, 0, 0, 0, 0, 0, null, null, 0, Double.parseDouble(results.getString(7), results.getInt(20), results.getString(21))));

                }
                count++;
            }

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println("SERVLET ERROR: " + sqle);
        }

        //invoice.invView(selectedServices, selectedInventory, iRecNum, iDate);
        totServCost = new BigDecimal("0");
        totInvCost = new BigDecimal("0");
        totPay = new BigDecimal("0");

        cost = new BigDecimal("0");
        count = selectedServices.size();
        for (int x = 0; x < count; x++) {
            selectedServices.iterator().next();
            cost = selectedServices.get(x).getCost();
            quantity = selectedServices.get(x).getQty();
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            selectedServices.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
        }
        
        for (int x = 0; x < count; x++) {
                projectInventoryView.iterator().next();
                cost = projectInventoryView.get(x).getAmount();
                quantity = projectInventoryView.get(x).getQuantity();
                this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
               // selectedServices.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
            }

        count = selectedPayments.size();
        for (int x = 0; x < count; x++) {
            selectedPayments.iterator().next();
            totPay = totPay.add(selectedPayments.get(x).getTotCost());
        }

        //this.totCost = totInvCost + totServCost + totPay;

        LocalDate from = LocalDate.parse(iDate);
        LocalDate to = LocalDate.parse(sdf.format(new Date()));

        long days = ChronoUnit.DAYS.between(from, to);

        query = "SELECT interest FROM Organisation";
        statement = connection.createStatement();
        ResultSet org = statement.executeQuery(query);

        while (org.next()) {
            interest = org.getBigDecimal(1);
        }

        if ((outstanding.compareTo(new BigDecimal("0"))) < 0) {
            if ((days >= 30) & (days < 60)) {
                Days30 = (totCost.multiply(interest)).add(totCost);
            } else if (days >= 60) {
                Days30 = (totCost.multiply(interest)).add(totCost);
                Days60 = (Days30.multiply(interest)).add(Days30);
            }
        }

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
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");
               
        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/project-invoice-complete.xhtml?" + param);
        }

    }

    public void getAccParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
 
        this.iRecNum = params.get("recnum");

        this.cid = params.get("cID");
        this.cName = params.get("cName");
        this.cSurname = params.get("cSurname");
        this.tel = params.get("tel");
        this.cell = params.get("cell");
        this.fax = params.get("fax");
        this.posAdd = params.get("posAdd");
        this.physAdd = params.get("physAdd");

    }

    public void viewInvoice() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/invoice/view-invoice.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(InvoiceData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String createReceipt() {

        return "/accounting/billing/receipt/new-receipt";

    }

    public String updatePayment() throws SQLException {

        String redirect = null;

        if ((payment.compareTo(outstanding)) > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "This module module does not process change. Enter the exact outstanding amount in Amount Paid."));
        } else {
            BigDecimal change = new BigDecimal("0");
            BigDecimal balance = new BigDecimal("0");
            if ((payment.compareTo(outstanding)) < 0) {
                balance = outstanding.subtract(payment);
            } else {
                balance = new BigDecimal("0");
            }

            taxAmount = balance.divide(new BigDecimal("100"));//* 15);            
            taxAmount = taxAmount.multiply(new BigDecimal("15"));
            try {
                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();         
                ip.updatePayment(this.iRecNum, payment, balance, request.getSession().getAttribute("uk").toString(), sdf.format(paymentDate), Systems.getOID(), this.cid, outstanding, change, taxAmount);
            } catch (SQLException sqle) {
            }
            
            redirect = "/customers/other-saved";

        }

        return redirect;

    }
    
    // REPORTS
    public void reportAllInvoices(Client customer) throws SQLException {

        invoices.clear();

        getCustomerParams();

        connection = Systems.initConnection();
        
        query = "SELECT * FROM invoices where cid = '" + customer.getId() + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            invoices.add(new InvoiceData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getBigDecimal(19), results.getBigDecimal(20), results.getString(22), results.getString(23)));                    
        }

    }
    
    public List<InvoiceData> getRepAllInvoices() {
        return invoices;
    }

}