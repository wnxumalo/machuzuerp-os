/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.CustomerProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class Stakeholder implements Serializable {

    /**
     * Creates a new instance of Stakeholder
     */

    private String recnum;
    
    private String vatNum = "";
    private String cType = "";
    private String cStatus = "";
    private String cName = "";
    private String cSurname = "";
    private String cGender = "";
    private String cTelephone = "";
    private String cCellphone = "";
    private String email = "";
    private String cFax = "";
    private String cPostalAddress = "";
    private String cPhysicalAddress = "";
    private String cCountry = "";
    private java.util.Date cJDate;
    String cDate;
    
    private String ret = "";
    
    private String msgSubject = "";
    private String msg = "";
    
    private String tranHisType = "";
    
    CustomerProcessing cp = new CustomerProcessing();        
    
    HttpServletRequest request;

    public Stakeholder() {        
        
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();                
       
        this.recnum = params.get("custnum");
        this.cName = params.get("custname");
        this.cSurname = params.get("custsurname");
        } catch (NullPointerException npe){}

    }
    
    public void onDateSelect(SelectEvent event) {
     //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");               
        //setJDate(format.format(event.getObject()));
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

    public void setJDate(java.util.Date cJDate) {
        this.cJDate = cJDate;
    }

    public java.util.Date getJDate() {
        return cJDate;
    } 

    public void setTranHisType(String tranHisType) {
        this.tranHisType = tranHisType;
    }

    public String getTranHisType() {
        return tranHisType;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        

        this.recnum = params.get("recnum");           
        
        try {
            cp.vatNum = this.vatNum;
            cp.customerType = this.cType;
            cp.customerStat = this.cStatus;
            cp.customerName = this.cName;
            cp.customerSurname = this.cSurname;
            cp.customerGender = this.cGender;
            cp.customerTelephone = this.cTelephone;
            cp.customerCellphone = this.cCellphone;
            cp.customerEmail = this.email;
            cp.customerFax = this.cFax;
            cp.customerPostalAddress = this.cPostalAddress;
            cp.customerPhysicalAddress = this.cPhysicalAddress;
            cp.customerCountry = this.cCountry;
            cp.customerJDate = ""+sqlDate(this.cJDate);
        } catch (NullPointerException npe) {}
    }
    
    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");           
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

        cp.clientRecNum= params.get("recnum");
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
    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }

    public String saveCustomer() {
        getParams();          

        try {
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        

            cp.editCustomer(request.getSession().getAttribute("uk").toString(), "", "", "");            

            ret = "/customers/saved";
        } catch (Exception npe) {
            System.out.println("1:"+npe);
            ret = "/customers/notsaved";
        }
        
        return ret;
    }
    
    //public String saveEditCustomer(String recnum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry,String cJDate) throws SQLException {
    public String saveEditCustomer() throws SQLException {

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
       
    public String editCustomer_1(String recnum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry, String cJDate) throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        this.recnum = recnum;
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
        try {
            this.cJDate = sdf.parse(cJDate);
            System.out.println(""+cJDate);
        } catch (ParseException ex) {
            this.cJDate = sdf.parse(sdf.format(new java.util.Date()));
        }
        return "edit-customer.xhtml";        
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
//        cJDate = cp.customerJDate; 

    }
    
    public String deleteCustomer() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
            cp.editCustomer(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/customers/deleted";
        } catch (NullPointerException npe) {
            ret = "/customers/notsaved";
        }
        
        return ret;
    }

    public String emailCust() {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        

        this.email = params.get("email");
        this.cName = params.get("CName");
        
       Properties props = new Properties();
        props.put("mail.smtp.host", "mail.erpbyw.com");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        // create some properties and get the default Session
        Session sessionMail = Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("crm@erpbyw.com", "v,vac(mLeAMR");
            }
        });

        sessionMail.setDebug(true);

		try {

			Message message = new MimeMessage(sessionMail);
                        message.setFrom(new InternetAddress("CRM@erpbyw.com"));
            
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(this.email));
			message.setSubject(this.msgSubject);
			message.setText("Dear " + this.cName + "," + "\n\n " + msg + "\n\nRegards,\n\nERPbyW CRM\nhttp://www.erpbyw.com");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);                        
		}
                
                return "dashboard.xhtml";
    }
    
    public String smsCust() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();                
        this.cCellphone = params.get("cellphone");
        this.cName = params.get("name");
        
       Properties props = new Properties();
        props.put("mail.smtp.host", "mail.erpbyw.com");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        // create some properties and get the default Session
        Session sessionMail = Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("crm@erpbyw.com", "v,vac(mLeAMR");
            }
        });

        sessionMail.setDebug(true);

		try {

			Message message = new MimeMessage(sessionMail);
                        message.setFrom(new InternetAddress("CRM@erpbyw.com"));
            
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse("sms@messaging.clickatell.com "));
			message.setSubject(this.msgSubject);
                        String sendMsg= "user:maestroit\npassword:2TNr5ZGI\napi_id:3428084\ntext:Dear " + this.cName + ',' + msg + ".\n" + "to:" + this.cCellphone;
			message.setText(sendMsg);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);                        
		}
                
                return "dashboard.xhtml";
    }
    
    public String getInvoices() {         
        return "/customers/find-invoice";
    }
        
    public void showError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Incorrect username and/or password."));
    }
    
}
