/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.UserData;
import com.machuzuerp.controllers.datatableprocessing.jsf.OrgData;
import com.machuzuerp.controllers.datatableprocessing.jsf.CustomerData;
import com.machuzuerp.entities.AuditLogging;
import com.machuzuerp.controllers.jpa.AuditLoggingFacade;
import com.machuzuerp.jdbc.UserProcessing;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named
public class Login implements Serializable {

    private String fullname;
    private String cellphone;
    private String email;
    private String accessEmail;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private String rPath;
    private String uMsg;
    private String navAction;

    private List<String> users = new ArrayList<String>();
    private List<UserData> userDat = new ArrayList<UserData>();
    private List<OrgData> orgDat = new ArrayList<OrgData>();
    private List themes = new ArrayList();

    private CustomerData customerData = new CustomerData();

    private Date dateFrom;
    private Date dateTo;
    private String currDate;
    private String organisation;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat syf = new SimpleDateFormat("yyyy");

    Systems systems = new Systems();
    PurchaseOrderOld po = new PurchaseOrderOld();
    
    @EJB
    private com.machuzuerp.controllers.jpa.AuditLoggingFacade auditFacade;

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    public Login() {
        //setEmail("test@erpbyw.com");
        //setPassword("test123");

        this.email = "";
        this.password = "";
        
        currDate = syf.format(new Date());
        dateTo = new Date();
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public List getThemes() {

        themes.add("humanity");
        themes.add("blitzer");
        themes.add("excite-bike");
        themes.add("glass-x");
        themes.add("hot-sneaks");
        themes.add("le-frog");
        themes.add("overcast");
        themes.add("pepper-grinder");
        themes.add("redmond");
        themes.add("ui-darkness");
        themes.add("ui-lightness");

        return themes;
    }
    
    public void viewCRMInvoice() throws IOException {
        
            //FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.machuzu.com/documentation/CRM–ViewInvoice.mp4");

        
        //https://www.machuzu.com/documentation/CRM–ViewInvoice.mp4
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public java.util.Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(java.util.Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public java.util.Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(java.util.Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public void setMsg(String uMsg) {
        this.uMsg = uMsg;
    }

    public String getMsg() {
        return uMsg;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setAccessEmail(String accessEmail) {
        this.accessEmail = accessEmail;
    }

    public String getAccessEmail() {
        return accessEmail;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setNavAction(String navAction) {
        this.navAction = navAction;
    }

    public String getNavAction() {
        return navAction;
    }
    
    private AuditLoggingFacade getAuditFacade() {
        return auditFacade;
    }

    public String loginUser(HttpSession session) {

        String page = "/index";
        try {

            /*if (systems.checkLock() == 1) {

                systems.lockDatabase();
                uMsg = "The trial has expired.";

                loginError();

            } else if ((new Date().compareTo(sdf.parse("2022-06-01"))) > 0) {

                systems.lockDatabase();
                uMsg = "The trial has expired.";

                loginError();

            } else*/
            if (systems.Login(email, password) == true) {

                if (password.equals("temppass")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Change Password", "Kindly create a new password."));
                    uMsg = "Kindly create a new password.";
                } else {

                    session.setAttribute("username", systems.uName);
                    session.setAttribute("uk", systems.uk);
                    
                    //organisation = systems.o                  
                    //systems.saveLoginData(email, fullname, cellphone);

                    if (email.equals("administrator@machuzu.com"))
                        page = "/v4/administration/administrator";
                    else
                        page = "maindashboard";
                }

            } else {
                System.out.println("555");
                uMsg = "Incorrect username and/or password.";
                loginError();
                page = "/index";
            }

        } catch (SQLException | ParseException ex) {
            uMsg = "" + ex;
            System.out.println("ERROR: " + ex + ":" + uMsg);
        }

        return page;
    }

    public void saveNewPassword(String id) throws SQLException {

        if (newPassword.equals(confirmPassword)) {
            systems.updatePassword(id, newPassword);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Change Password", "Password updated successfully."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Change Password", "Passwords do not match."));
        }

    }

    public String mainDashboard() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Dashboard");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/maindashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/maindashboard";
    }

    public String customerDashboard() {

        rPath = "/customers/dashboard";
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Dashboard - Customer");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction(rPath);
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return rPath;
    }

    public String newUser(HttpSession session) {

        session.setAttribute("customer", null);

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("New User");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/new-customer");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/customers/new-customer";
    }

    public String findUser(HttpSession session) {

        session.setAttribute("customerData", null);

        return "/customers/find-customer";
    }

    public String helpPage() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Help Page");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/erpbyw-help");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/erpbyw-help";
    }

    public String customerResults() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/customer-listing");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/customers/customer-listing";
    }

    public String customerListing() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/customer-listing");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/customers/customer-listing";
    }

    public String customerDetailedListing(HttpSession session) {

        session.setAttribute("customerData", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/customer-detailed-list");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/customers/customer-detailed-list";
    }

    public String customerDetailedListing() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/customer-detailed-list");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/customers/customer-detailed-list";
    }

    public String customerAppointments() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/calendar");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/customers/calendar";
    }

    //Services
    public String servicesDashboard() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Services");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/services/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        rPath = "/services/dashboard";
        return rPath;
    }

    public String newService(HttpSession session) {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Services");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/services/new-service");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        session.setAttribute("services", null);

        return "/services/new-service";
    }

    public String serviceListing() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Services");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/services/services-listing");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/services/services-listing";
    }

    //Suppliers
    public String suppliersDashboard() {

        rPath = "/suppliers/dashboard";
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Supplier");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction(rPath);
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return rPath;
    }

    public String newSupplier() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Supplier");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/suppliers/new-supplier");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/suppliers/new-supplier";
    }

    public String findSupplier(HttpSession session) {

        session.setAttribute("supplierData", null);

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Supplier");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/v4/supplier/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/v4/supplier/List";
    }
    
    public String findShipment(HttpSession session) {

        session.setAttribute("supplierData", null);

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Supplier");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/v4/shipment/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/v4/shipment/List";
    }
    
    public String createShipment(HttpSession session) {

        session.setAttribute("supplierData", null);

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Supplier");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/v4/shipment/ListPurchaseOrders");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/v4/shipment/ListPurchaseOrders";
    }

    public String supplierListing() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Supplier");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/suppliers/supplier-listing");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/suppliers/supplier-listing";
    }

    //Inventory
    public String inventoryDashboard() {

        rPath = "/inventory/dashboard";
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Inventory");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction(rPath);
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return rPath;
    }

    public String newInventory(HttpSession session) {

        session.setAttribute("inventory", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Inventory");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/inventory/new-inventory");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/inventory/new-inventory";
    }

    public String inventoryListing() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Inventory");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/inventory/inventory-listing");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/inventory/inventory-listing";
    }

    public String selectInventory() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Inventory");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/inventory/select-project");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/inventory/select-project";
    }

    //Accounting
    public String accountingDashboard() {

        rPath = "/accounting/dashboard";
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction(rPath);
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return rPath;
    }

    public String accountingAccounts() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/accounts");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/accounts";
    }

    public String accountingInvoice() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/billing/invoice/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/accounting/billing/invoice/dashboard";
    }

    public String accountingQuotation() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/billing/quotation/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/billing/quotation/dashboard";
    }

    public String accountingStatement() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/billing/statement/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/billing/statement/dashboard";
    }

    public String accountingReceipt() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/billing/receipt/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/billing/receipt/dashboard";
    }

    public String generalLedger() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("General Ledger");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/general-ledger");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/general-ledger";
    }

    public String accountsJournal() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/journal/find-journal");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/accounting/journal/find-journal";
    }

    public String accountingListing() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/accounts-listing");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/accounts-listing";
    }

    public String newAccount(HttpSession session) {

        session.setAttribute("accounts", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/new-account");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/new-account";
    }

    public String accountsPayable() {
System.out.println("UKA111: " + request.getSession().getAttribute("uk").toString());
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounting");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/payable/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        po.beanMsg = "";
        return "/accounting/payable/dashboard";
    }

    public String accountsReceivable() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/receivable/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/receivable/dashboard";
    }

    public String newPayable(HttpSession session) {

        session.setAttribute("payable", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/payable/new-payable");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/payable/new-payable";
    }

    public String accountsBilling() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/accounting/billing/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/accounting/billing/dashboard";
    }

    // Online Collaboration
    public String ocCalendar() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/calendarEvents");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/calendarEvents";
    }

    public String ocDashboard() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/dashboard";
    }

    public String ocLists() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/teams/teams");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/teams/teams";
    }

    public String ocNewTeam(HttpSession session) {

        session.setAttribute("team", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/teams/new-team");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/teams/new-team";
    }

    public String ocFindTeam() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/teams/find-team");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/teams/find-team";
    }

    public String ocNewFile() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/files/new-file");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/files/new-file";
    }

    public String ocFindFile() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/files/find-file");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/files/find-file";
    }

    public String ocFiles() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/files/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/files/dashboard";
    }

    public String ocCompliancePolicy() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration - Compliance");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/compliance/policy/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/compliance/policy/List";
    }
    
    public String ocCompliancePolicyRisks() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration - Compliance");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/compliance/policy/risk/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/compliance/policy/risk/List";
    }
    
    public String ocCompliancePolicyControls() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration - Compliance");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/compliance/policy/risk/control/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/oc/compliance/policy/risk/control/List";
    }
    
    public String ocComplianceControl() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration - Compliance");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/compliance/control/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/oc/compliance/control/List";
    }
    
    public String ocComplianceIssues() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Collaboration - Compliance");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/oc/compliance/policy/risk/issues/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/oc/compliance/policy/risk/issues/List";
    }
    
    public String projectsDashboard() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Projects");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/projects/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/projects/dashboard";
    }

    public String newProject() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Projects");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/projects/new-project");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/projects/new-project";
    }

    public String findProject(HttpSession session) {

        session.setAttribute("projectData", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Projects");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/projects/project-detailed-list");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/projects/project-detailed-list";
    }

    public String msgDashboard() {

        return "/unavailable";
    }

    public String hdDashboard() {

        return "/unavailable";
    }

    // Human Resources
    public String hrmDashboard() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/hr/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/hr/dashboard";
    }

    public String hrmEmployees() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/v4/hr/employees/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/v4/hr/employees/dashboard";
    }

    public String newEmployee(HttpSession session) {

        session.setAttribute("employees", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/hr/employees/new-employee");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/hr/employees/new-employee";
    }

    public String findEmployee(HttpSession session) {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/v4/hr/employee/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/v4/hr/employee/List";
    }

    public String jobsDashboard() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("New User");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/jobs/dashboard");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/jobs/dashboard";
    }

    public String newJob(HttpSession session) {

        session.setAttribute("jobs", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("New User");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/new-customer");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/jobs/new-job";
    }

    public String jobCheckIn() {

        return "/jobs/select-jobs";
    }

    public String newSteps(HttpSession session) {

        session.setAttribute("team", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Workflow");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/workflow/new-steps");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/workflow/new-steps";
    }

    public String newWorkflow(HttpSession session) {

        session.setAttribute("team", null);
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Workflow");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/workflow/new-workflow");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/workflow/new-workflow";
    }

    public String configureWorkflow() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Workflow");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/workflow/List");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/workflow/List";
    }

    public String processWorkflow() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Workflow");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/workflow/List-Process");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/workflow/List-Process";
    }

    public String searchWorkflow() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Workflow");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/workflow/Find-Step-Process");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/workflow/Find-Step-Process";
    }

    // REPORTS
    // Stakeholders
    public String clientGeneral() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/stakeholders/general");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/reports/stakeholders/general";
    }

    public String clientInvoices() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/stakeholders/invoices");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/stakeholders/invoices";
    }

    public String clientReciepts() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/stakeholders/receipts");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/stakeholders/receipts";
    }

    public String clientProjects() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - CRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("reports/stakeholders/projects");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/stakeholders/projects";
    }

    public String clientServices() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Services");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/stakeholders/services");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/stakeholders/services";
    }

    public String clientSharedDocs() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/stakeholders/shared-documents");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/stakeholders/shared-documents";
    }

    // Inventory
    public String inventoryGeneral() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Inventory");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/inventory/general");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/inventory/general";
    }

    public String inventorySupplier() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Inventory");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/inventory/supplier");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/inventory/supplier";
    }

    public String inventoryTurnover() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Inventory");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/inventory/turnover");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/inventory/turnover";
    }    

    // Services
    public String servicesGeneral() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Services");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/services/general");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/services/general";
    }

    // Suppliers
    public String suppliersGeneral() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Supplier");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/suppliers/general");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/reports/suppliers/general";
    }

    // Accounting
    public String accountingSales() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/accounting/sales-report");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/accounting/sales-report";
    }
    
    public String accountingGeneral() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/accounting/general");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/accounting/general";
    }

    public String accountingType() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/accounting/bytype");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/accounting/bytype";
    }

    public String accountsJournalReport() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Accounts");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/accounting/journal-listing");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/accounting/journal-listing";
    }

    // Projects
    public String projectGeneral() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Projects");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/project/general");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/project/general";
    }

    public String projectStatus() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Projects");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/project/status");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/reports/project/status";
    }

    public String projectSingle() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Projects");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/project/single");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/project/single";
    }

    // Human Resources
    public String hrGeneral() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/hr/general");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/hr/general";
    }
    
    public String hrPayroll() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/hr/payroll");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/hr/payroll";
    }

    public String hrBenefits() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/hr/benefits");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/reports/hr/benefits";
    }

    public String hrLeave() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/hr/leave");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/hr/leave";
    }

    public String hrDeductions() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/hr/deductions");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/reports/hr/deductions";
    }

    public String hrTrainings() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/hr/trainings");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/hr/trainings";
    }

    public String hrProjects() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("HRM");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/hr/projects");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/hr/projects";
    }

    // Collaboration
    public String collaborationTeams() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/collaboration/teams");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/collaboration/teams";
    }

    public String collaborationEvents() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/collaboration/events");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/collaboration/events";
    }

    public String collaborationFiles() {

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/collaboration/files");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);
        
        return "/reports/collaboration/files";
    }

    public String collaborationTeamFiles() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("Reports - Collaboration");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/reports/collaboration/team-files");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/reports/collaboration/team-files";
    }

    public String completeMission() {
        
        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Click");
        audit.setAppModule("New User");
        audit.setEmployeeId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        audit.setUserAction("/customers/new-customer");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        return "/clocking/find-mission";
    }

    public String accessControlDashboard() {

        return "/clocking/dashboard";
    }

    public String settingsPage() throws SQLException, IOException {

        UserProcessing up = new UserProcessing();

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        up.getUser(request.getSession().getAttribute("uk").toString());
        up.getOrganisation(request.getSession().getAttribute("username").toString());

        userDat.add(new UserData(up.userRecNum, up.userType, up.email, up.password, up.name, up.company, up.companyTel, up.companyAddress, up.uk, up.level, up.orgid));
        orgDat.add(new OrgData(up.orgRecNumber, up.orgNumber, up.orgName, up.orgTel, up.orgFax, up.orgEmail, up.orgPosAdd, up.orgPhysAdd, up.orgid));

        return "settings";

    }

    public List<UserData> getUserDat() {
        return userDat;
    }

    public List<OrgData> getOrgDat() {
        return orgDat;
    }

    public void loginError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", uMsg));
    }

    public String requestAccess1() {

        this.accessEmail = "";
        this.fullname = "";
        this.cellphone = "";

        return "request-trial-access";
    }

    public void requestAccess() {

        systems.saveLoginData(accessEmail, fullname, cellphone);

        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.machuzu.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("info@machuzu.com", "zXZK0l_A@N65");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@machuzu.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(accessEmail));
            message.setSubject("MachuzuERP Access Request");
            message.setText("Dear " + fullname + "," + "\n\n" + "Thank you for requesting access to the software\n\nLogin with the details below;\n\nUsername: trial@machuzu.com\nPassword: 12345\nLink: http://103.125.218.105:8080/MachuzuERP/\n\nFor more information please view our website @ http://www.machuzu.com \n\n Feel free to contact me directly with any queries at wandile@machuzu.com or 00268 76242698 \n\nThank you!");

            Transport.send(message);

            System.out.println("Done");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Please check your email for instructions."));

        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error has occured: " + e));
        }

    }

    public void linkedInLogin() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=77qke18tfdtjec&state=foobar&scope=r_emailaddress,r_liteprofile&redirect_uri=https://www.machuzu.com/LinkedInLogin.php");
//FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=77qke18tfdtjec&state=foobar&scope=r_emailaddress,r_liteprofile&redirect_uri=http://103.125.218.105:8080/MachuzuERP/LinkedInLogin.xhtml");

        //FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/Machuzu-ERP-1.0-SNAPSHOT/faces/LinkedInLogin.xhtml?code=AQQCaBvQ4CepfrQ9UC66Xswi2iG2sFdkgsU3WicYoOfcler3eY1Ny73xM6dyEq1gFvo6OHHeDkZNk7Eq7x2tWLRR4-1jYeM6hdpcNjkbzCiYkH6F4Iwp4PqXV1EcgatSqxRTANfouaCVS1rG6n521czdBqVeQx3izwe1-6JI3odTZN8AuIpb7jENkgUbG7mu3Ad0E4mkFR0-nReGbXw");
        // return "LinkedInLogin.xhtml?code=AQQCaBvQ4CepfrQ9UC66Xswi2iG2sFdkgsU3WicYoOfcler3eY1Ny73xM6dyEq1gFvo6OHHeDkZNk7Eq7x2tWLRR4-1jYeM6hdpcNjkbzCiYkH6F4Iwp4PqXV1EcgatSqxRTANfouaCVS1rG6n521czdBqVeQx3izwe1-6JI3odTZN8AuIpb7jENkgUbG7mu3Ad0E4mkFR0-nReGbXw";
    }

}
