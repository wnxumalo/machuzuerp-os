/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Client;
import com.machuzuerp.jdbc.CustomerProcessing;
import com.machuzuerp.controllers.datatableprocessing.jsf.CustomerData;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@SessionScoped
@Named
public class EnrolledServices implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private int recNum;    
    private String stakeholderId;
    private String serviceId;
    private String description;
    private BigDecimal cost;
    private BigDecimal interestRate;
    private Date recurringMaturityDate;
    private Date enrolmentDate; 
    
    String query = "";
   
    private List<EnrolledServices> services = new ArrayList<EnrolledServices>();   
    private EnrolledServices selectedEnrolledService;
    private List<EnrolledServices> selectedEnrolledServices;   
    private StakeholderServices selectedService;
    
    CustomerProcessing cp = new CustomerProcessing();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private String cRecNum;
    private String cType;
    private String cStatus;
    protected String cName;
    protected String cSurname;
    private String cGender;
    private String cTelephone;
    private String cCellphone;
    private String email;
    private String cFax;
    private String cPostalAddress;
    private String cPhysicalAddress;
    private String cCountry;
    private String cJDate;
    private String vatNum;
    
    CustomerData cd = new CustomerData();
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    public EnrolledServices() {
    }

    public EnrolledServices(int recNum, String stakeholderId, String serviceId, String description, BigDecimal cost, BigDecimal interestRate, Date recurringMaturityDate, Date enrolmentDate) {

        this.recNum = recNum;
        this.stakeholderId = stakeholderId;
        this.serviceId = serviceId;
        this.description = description;
        this.cost = cost;
        this.interestRate = interestRate;
        this.recurringMaturityDate = recurringMaturityDate;
        this.enrolmentDate = enrolmentDate;
    }

    public List<EnrolledServices> getServices() {
        return services;
    }
    
    /*public void setServices(List<EnrolledServices> services) {
        this.services = services;
    }*/
    
    public List<EnrolledServices> getSelectedEnrolledServices() {
        return selectedEnrolledServices;
    }
    
    public void setSelectedEnrolledServices(List<EnrolledServices> selectedEnrolledServices) {
        this.selectedEnrolledServices = selectedEnrolledServices;
    }
    
    public int getRecNum() {
        return recNum;
    }

    public void setRecNum(int recNum) {
        this.recNum = recNum;
    }

    public String getCRecNum() {
        return cRecNum;
    }

    public void setCRecNum(String cRecNum) {
        this.cRecNum = cRecNum;
    }
    
    public String getStakeholderId() {
        return stakeholderId;
    }

    public void setStakeholderId(String stakeholderId) {
        this.stakeholderId = stakeholderId;
    }
    
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }        
        
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
         
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Date getRecurringMaturityDate() {
        return recurringMaturityDate;
    }

    public void setRecurringMaturityDate(Date recurringMaturityDate) {
        this.recurringMaturityDate = recurringMaturityDate;
    }    
            
    public Date getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(Date enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }
  
    public void getCustomerParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        
        this.cRecNum = params.get("crecnum");
    }

    public EnrolledServices getSelectedEnrolledService() {
        return selectedEnrolledService;
    }

    public void setSelectedEnrolledService(EnrolledServices selectedEnrolledService) {
        this.selectedEnrolledService = selectedEnrolledService;
    }
    
    public StakeholderServices getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(StakeholderServices selectedService) {
        this.selectedService = selectedService;
    }

    public void onRowSelect(SelectEvent event) throws IOException {
       selectedEnrolledService = ((EnrolledServices) event.getObject());
    }
    
    public void onCellEdit(CellEditEvent event) {

        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }                         

    }

    public void viewService() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/services/view-service.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EnrolledServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getStakeholderServices() throws SQLException {

        services.clear();

        getCustomerParams();

        connection = Systems.initConnection();
        
        query = "SELECT * FROM enrolledservices where Stakeholder_Id = '" + cRecNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {            
            services.add(new EnrolledServices(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getBigDecimal(5), results.getBigDecimal(6), results.getDate(7), results.getDate(8)));
        }
        
        query = "SELECT * FROM clients where recnum = '" + cRecNum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) { 
            
            cRecNum = results.getString(1);
            cType = results.getString(3);
            cStatus = results.getString(4);
            cName = results.getString(5);
            cSurname = results.getString(6);
            cGender = results.getString(7);
            cTelephone = results.getString(8);
            cCellphone = results.getString(9);
            email = results.getString(18);
            cFax = results.getString(10);
            cPostalAddress = results.getString(11);
            cPhysicalAddress = results.getString(12);
            cCountry = results.getString(13);
            cJDate = results.getString(14);
            vatNum = results.getString(17);
    
        }

        return "/services/stakeholder-service-listing";
    } 
    
    public void newEnrollment(HttpSession session, StakeholderServices service) {
        
        session.setAttribute("enrolledServices", null);

        services.clear();

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();       

        connection = Systems.initConnection();

        try {            

                try {
                    recurringMaturityDate.toString().length();
                } catch (NullPointerException npe) {
                    recurringMaturityDate = new java.util.Date();
                }
                
                try {
                    enrolmentDate.toString().length();
                } catch (NullPointerException npe) {
                    enrolmentDate = new java.util.Date();
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(recurringMaturityDate);
                String rmDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE);
                cal.setTime(enrolmentDate);
                String eDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE);

                if ((selectedService.getDescription().length() > 0) && (cost.compareTo(new BigDecimal(0)) > -1)) {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "INSERT INTO enrolledservices(Stakeholder_Id, Service_Id, Description, Cost, Interest_Rate, Recurring_Maturity_Date, Enrolment_Date) VALUES ('" + 
                            params.get("crecnum") + "','" + service.getId() + "','" +  service.getDescription() + "','" + cost + "','" + interestRate + "','" + rmDate + "','" + eDate + "')";
                    added = statement.executeUpdate(query);
                    statement.close();

                    query = "SELECT * FROM enrolledservices where Stakeholder_Id = '" + params.get("crecnum") + "'";
                    statement = connection.createStatement();
                    results = statement.executeQuery(query);

                    while (results.next()) {
                        services.add(new EnrolledServices(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getBigDecimal(5), results.getBigDecimal(6), results.getDate(7), results.getDate(8)));
                    }

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enrolled Services", "Enrolled Successfully."));
                    
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Enrolled Services", "Ensure a Service is Selected and Cost Entered Before Saving."));
                }
            
        } catch (SQLException sqle) {
            System.out.println("ERR: " + sqle);
        }
    }
    
    public void refresh() throws SQLException {
        
        services.clear();

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();               
       
        connection = Systems.initConnection();
        
        query = "SELECT * FROM enrolledservices where Stakeholder_Id = '" + params.get("crecnum") + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {                
            services.add(new EnrolledServices(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getBigDecimal(5), results.getBigDecimal(6), results.getDate(7), results.getDate(8)));
        }            
        
    }

    public void discontinueService() throws SQLException {

        try {

            selectedEnrolledService.description.length();               

            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

            connection = Systems.initConnection();

            int added = 0;
            statement = connection.createStatement();
            query = "delete from enrolledservices where recnum = '" + selectedEnrolledService.recNum + "'";
            added = statement.executeUpdate(query);
            statement.close();

            services.clear();
            query = "SELECT * FROM enrolledservices where Stakeholder_Id = '" + params.get("crecnum") + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {                
                services.add(new EnrolledServices(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getBigDecimal(5), results.getBigDecimal(6), results.getDate(7), results.getDate(8)));
            }

        } catch (NullPointerException npe) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Discontinue", "Ensure a Service is Selected."));           

        }

    }
    
    public void newInvoice() throws IOException, SQLException {
        
        try {
            
            selectedEnrolledService.description.length();   
        
            this.selectedEnrolledService = selectedEnrolledService;   
            services.clear();

            String[] cust = cp.getCustomerDetails(selectedEnrolledService.stakeholderId).split(",");

            services.add(new EnrolledServices(selectedEnrolledService.recNum, cp.clientRecNum, selectedEnrolledService.serviceId, selectedEnrolledService.description, selectedEnrolledService.cost, selectedEnrolledService.interestRate, selectedEnrolledService.recurringMaturityDate, selectedEnrolledService.enrolmentDate));

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/../accounting/billing/invoice/select-enrollment-service.xhtml");
            }

        } catch (NullPointerException npe) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Create Invoice", "Ensure a Service is Selected."));           

        }
    }
    
    // Stakeholder
    public String getRecnum() {
        return cRecNum;
    }

    public void setRecNum(String cRecNum) {
        this.cRecNum = cRecNum;
    }

    public String getType() {
        return cType;
    }

    public void setType(String cType) {
        this.cType = cType;
    }

    public void setStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getStatus() {
        return cStatus;
    }

    public void setCustomerName(String cName) {
        this.cName = cName;
    }

    public String getCustomerName() {
        return cName;
    }

    public void setSurname(String cSurname) {
        this.cSurname = cSurname;
    }

    public String getSurname() {
        return cSurname;
    }

    public void setGender(String cGender) {
        this.cGender = cGender;
    }

    public String getGender() {
        return cGender;
    }

    public void setTelephone(String cTelephone) {
        this.cTelephone = cTelephone;
    }

    public String getTelephone() {
        return cTelephone;
    }

    public void setCellphone(String cCellphone) {
        this.cCellphone = cCellphone;
    }

    public String getCellphone() {
        return cCellphone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFax(String cFax) {
        this.cFax = cFax;
    }

    public String getFax() {
        return cFax;
    }

    public void setPostalAddress(String cPostalAddress) {
        this.cPostalAddress = cPostalAddress;
    }

    public String getPostalAddress() {
        return cPostalAddress;
    }

    public void setPhysicalAddress(String cPhysicalAddress) {
        this.cPhysicalAddress = cPhysicalAddress;
    }

    public String getPhysicalAddress() {
        return cPhysicalAddress;
    }

    public void setCountry(String cCountry) {
        this.cCountry = cCountry;
    }

    public String getCountry() {
        return cCountry;
    }

    public void setJDate(String cJDate) {
        this.cJDate = cJDate;
    }

    public String getJDate() {
        return cJDate;
    }
    
    public String getVatNum() {
        return vatNum;
    }
    
    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }   
    
    // REPORTS
    public void getReportStakeholderServices(Client customer) throws SQLException {

        services.clear();

        getCustomerParams();

        connection = Systems.initConnection();
        query = "SELECT * FROM enrolledservices where Stakeholder_Id = '" + customer.getId() + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {            
            services.add(new EnrolledServices(results.getInt(1), results.getString(2), results.getString(3), results.getString(4), results.getBigDecimal(5), results.getBigDecimal(6), results.getDate(7), results.getDate(8)));
        }
        
       
    }
    
    public List<EnrolledServices> getEnrolledServices() {
        return services;
    }

}
