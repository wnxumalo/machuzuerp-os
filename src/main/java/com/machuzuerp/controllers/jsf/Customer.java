/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.communication.EmailBean;
import com.machuzuerp.controllers.jpa.AdministrationFacade;
import com.machuzuerp.controllers.jpa.AuditLoggingFacade;
import com.machuzuerp.entities.Administration;
import com.machuzuerp.entities.AuditLogging;
import com.machuzuerp.jdbc.CustomerProcessing;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class Customer implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.AuditLoggingFacade auditFacade;
    
    private String recnum;
    
    private String vatNum;
    private String cType;
    private String cStatus;
    private String cName;
    private String cSurname;
    private String cGender;
    private String cTelephone;
    private String cCellphone;
    private String cFax;
    private String cPostalAddress;
    private String cPhysicalAddress;
    private String cCountry;
    private String employer;
    private Long supplierId;
    private String email;
    private java.util.Date cJDate;
    String cDate;
    
    private String ret;
    
    private String msgSubject;
    private String msg;
    
    CustomerProcessing cp = new CustomerProcessing();
    
    HttpServletRequest request;
    
    EmailBean sendEmail = new EmailBean();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    
    String newstr;
    int endIndex;
    String url;
    String uri;
    
    public Customer() {        
    }
    
    private AuditLoggingFacade getAuditFacade() {
        return auditFacade;
    }
    
    public void onDateSelect(SelectEvent event) {
     //   FacesContext facesContext = FacesContext.getCurrentInstance();
        
        //setJDate(format.format(event.getObject()));
        System.out.println("ONDATESELECT:"+cJDate);
       // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }   
    
    public String getSubject() {
        return msgSubject;
    }

    public void setSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }   

    public String getVatNum() {
        return vatNum;
    }
    
    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
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
    
    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployer() {
        return employer;
    }
    
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setJDate(java.util.Date cJDate) {
        this.cJDate = cJDate;
    }

    public java.util.Date getJDate() {
        return cJDate;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");           
        
        cp.customerType = this.cType;
        cp.customerStat = this.cStatus;
        cp.customerName = this.cName;
        cp.customerSurname = this.cSurname;
        cp.customerGender = this.cGender;
        cp.customerTelephone = this.cTelephone;
        cp.customerCellphone = this.cCellphone;
        cp.customerFax = this.cFax;
        cp.customerPostalAddress = this.cPostalAddress;
        cp.customerPhysicalAddress = this.cPhysicalAddress;
        cp.customerCountry = this.cCountry;
        cp.supplierId = this.supplierId;
        
        try {
            cp.customerJDate = ""+sqlDate(this.cJDate);
        } catch (NullPointerException npe) {}
        cp.customerEmail = this.email;
        cp.vatNum = this.vatNum;
    }
    
    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");           
        this.vatNum = params.get("vatnum"); 
        this.cType = params.get("type");           
        this.cStatus = params.get("recnum");           
        this.cName = params.get("cname");
        this.cSurname = params.get("cSurname");
        this.cGender = params.get("gender");
        this.cTelephone = params.get("telephone");
        this.cCellphone = params.get("cellphone");
        this.cFax = params.get("fax");
        this.cPostalAddress = params.get("posaddress");
        this.cPhysicalAddress = params.get("physaddress");
        this.cCountry = params.get("country");
//        this.cJDate = params.get("jdate");       
        this.email = params.get("email");

        cp.clientRecNum= params.get("recnum");
        cp.vatNum = vatNum;
        cp.customerType = this.cType;        
        cp.customerStat = this.cStatus;
        cp.customerName = params.get("cname");
        cp.customerSurname = this.cSurname;
        cp.customerGender = this.cGender;
        cp.customerTelephone = this.cTelephone;
        cp.customerCellphone = this.cCellphone;
        cp.customerFax = this.cFax;
        cp.customerPostalAddress = this.cPostalAddress;
        cp.customerPhysicalAddress = this.cPhysicalAddress;
        cp.customerCountry = this.cCountry;
        cp.customerJDate = ""+sqlDate(this.cJDate);
        cp.customerEmail = this.email;
    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public void saveCustomer(Administration admin) throws UnsupportedEncodingException {

        getParams();         
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setCharacterEncoding("UTF-8");

        try {
            msg = cp.editCustomer("", "", "", "");  

            if (msg.equals("Exists"))
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "Stakeholder Email or Vat/ID Number Already Exists."));
            else {
                AuditLogging audit = new AuditLogging();  
                audit.setActionResponse("Success");
                audit.setAppModule("Stakeholder");
                audit.setEmployeeId(admin.getEmployeeId());
                audit.setUserAction("Save");
                audit.setDateCreated(LocalDate.now());
                audit.setTimeCreated(LocalTime.now());
                getAuditFacade().create(audit);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Stakeholder " + msg + " Successfully."));                
            }

        } catch (Exception npe) {

            AuditLogging audit = new AuditLogging();
            audit.setActionResponse("Error: " + npe);
            audit.setAppModule("Stakeholder");
            audit.setEmployeeId(admin.getEmployeeId());
            audit.setUserAction("Save");
            audit.setDateCreated(LocalDate.now());
            audit.setTimeCreated(LocalTime.now());
            getAuditFacade().create(audit);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }
       
    }

    public void saveEditCustomer() throws SQLException {

        getParams(); 

        try {

            cp.clientRecNum = recnum;
            cp.vatNum = vatNum;
            cp.customerType = cType;
            cp.customerStat = cStatus;
            cp.customerName =  cName;
            cp.customerSurname = cSurname;
            cp.customerGender = cGender;
            cp.customerTelephone = cTelephone;
            cp.customerCellphone = cCellphone;
            cp.customerFax = cFax;
            cp.customerPostalAddress = cPostalAddress;
            cp.customerPhysicalAddress = cPhysicalAddress;
            cp.customerCountry = cCountry;
            cp.customerJDate = ""+sqlDate(this.cJDate);
            cp.customerEmail = email;

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            msg = cp.editCustomer(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Stakeholder " + msg + " Successfully."));

        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error " + npe + " Occurred."));
        }

    }

    public String mainEditCustomer() {
        getParams();            

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            cp.editCustomer(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/customers/saved";
        } catch (NullPointerException npe) {
            ret = "/customers/notsaved";
        }
        
        return ret;        
    }
       
    public String editCustomer_1(String recnum, String vatNum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry, String cJDate, String email) throws SQLException, ParseException {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.recnum = recnum;
        this.vatNum = vatNum;
        this.cType = ctype;
        this.cStatus = cStat;
        this.cName = cName;
        this.cSurname = cSurname;
        this.cGender = cGender;
        this.cTelephone = cTel;
        this.cCellphone = cCell;
        this.cFax = cFax;
        this.cPostalAddress = cPosAd;
        this.cPhysicalAddress = cPhysAd;
        this.cCountry = cCountry;
        this.email = email;
        try {
            this.cJDate = sdf.parse(cJDate);
            System.out.println(""+cJDate);
        } catch (ParseException ex) {
            this.cJDate = sdf.parse(sdf.format(new java.util.Date()));
        }
        return "/customers/edit-customer";        
    }

    public void getCustomer() throws SQLException {        
        CustomerProcessing cp = new CustomerProcessing();
        cp.getCustomer(recnum);            
        
        cType = cp.customerType;
        setType(cp.customerType);
        cStatus = cp.customerStat;
        cName = cp.customerName;
        setCustomerName(cName);

        cSurname = cp.customerSurname;
        cGender = cp.customerGender;
        cTelephone = cp.customerTelephone;
        cCellphone = cp.customerCellphone;
        cFax = cp.customerFax;
        cPostalAddress = cp.customerPostalAddress;
        cPhysicalAddress = cp.customerPhysicalAddress;
        cCountry = cp.customerCountry;
        email = cp.customerEmail;
//        cJDate = cp.customerJDate; 

    }
    
    public void deleteCustomer() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            msg = cp.editCustomer(request.getSession().getAttribute("uk").toString(), "delete", "", recnum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Stakeholder " + msg + " Successfully."));
        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Erorr " + npe + " occurred."));
        }
        
    }
    
    public void emailCust() {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        sendEmail.sendEmail("Dear " + params.get("cName") + "\n\n" + msg , msgSubject, params.get("email"));

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Send Email", "Email Sent Successfully."));

    }
    
    public void getCustomer(String recnum, String vatNum, String type, String status, String name, String surname, String gender, String telephone, String cellphone, String email, String fax, String postalAddress, String physicalAddress, String country, String JDate) throws SQLException {  
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       

        this.recnum = recnum;
        this.vatNum = vatNum;
        this.cType = type;
        this.cStatus = status;
        this.cName = name;
        this.cSurname = surname;
        this.cGender = gender;
        this.cTelephone = telephone;
        this.cCellphone = cellphone;
        this.email = email;
        this.cFax = fax;
        this.cPostalAddress = postalAddress;
        this.cPhysicalAddress = physicalAddress;
        this.cCountry = country;
        try {
            this.cJDate = sdf.parse(JDate);
        } catch (ParseException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            try {
                newstr = uri.substring(0, endIndex);
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-customer.xhtml?vatNum=" + vatNum + "&recnum=" + recnum + "&type=" + type + "&status=" + status + "&cname=" + name
                        + "&cSurname=" + surname + "&gender=" + gender +"&telephone=" + telephone + "&email=" + email + "&fax=" + fax + "cellphone=" + cellphone
                        + "&posaddres=" + postalAddress + "&physaddress=" + physicalAddress + "&country=" + country + "&jdate=" + JDate);
            } catch (IOException ex) {
                Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

}
