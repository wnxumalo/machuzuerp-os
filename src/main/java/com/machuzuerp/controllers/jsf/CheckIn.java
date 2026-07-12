/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.CheckInProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class CheckIn implements Serializable {

    private String recnum;
    
    private String jobId;
    private String checkInId;
    private java.util.Date startDate;
    private String verifiedRequest = "";    
    private String verificationResults = "";    
    private String additionalRequirements = "";    
    private String externalIssues = "";
    private String internalIssues = "";
    private String associatedRisks = "";
    private String customerSatisfaction = "";
    private String customerSatisfactionComment = "";
    private String responsibleSignature = "";
    private String supervisorSignature = "";
    private java.util.Date responsibleSignatureDate = null;
    private java.util.Date supervisorSignatureDate = null;

    private String ret = "";

    CheckInProcessing cip = new CheckInProcessing();  

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    HttpServletRequest request;    

    public CheckIn() {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();                
          
            this.recnum = params.get("recnum");            
            
        } catch (NullPointerException npe){}

    }
    
    public void onDateSelect(SelectEvent event) {
     //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");               
        //setJDate(format.format(event.getObject()));
       // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public String getRecNum() {
        return recnum;
    }
    
    public String getJobId() {
        return jobId;
    }
    
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    
    public String getCheckInId() {
        return checkInId;
    }
    
    public void setCheckInId(String checkInId) {
        this.checkInId = checkInId;
    }
        
    public java.util.Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }   
    
    public String getVerifiedRequest() {
        return verifiedRequest;
    }

    public void setVerifiedRequest(String verifiedRequest) {
        this.verifiedRequest = verifiedRequest;
    }              
    
    public String getVerificationResults() {
        return verificationResults;
    }
    
    public void setVerificationResults(String verificationResults) {
        this.verificationResults = verificationResults;
    }         
    
    public String getAdditionalRequirements() {
        return additionalRequirements;
    }
    
    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }    
    
    public String getExternalIssues() {
        return externalIssues;
    }
    
    public void setExternalIssues(String externalIssues) {
        this.externalIssues = externalIssues;
    }               
    
    public void setInternalIssues(String internalIssues) {
        this.internalIssues = internalIssues;
    }

    public String getInternalIssues() {
        return internalIssues;
    }  
    
    public void setAssociatedRisks(String associatedRisks) {
        this.associatedRisks = associatedRisks;
    }

    public String getAssociatedRisks() {
        return associatedRisks;
    }
    
    public void setCustomerSatisfaction(String customerSatisfaction) {
        this.customerSatisfaction = customerSatisfaction;
    }

    public String getCustomerSatisfaction() {
        return customerSatisfaction;
    }
    
    public void setCustomerSatisfactionComment(String customerSatisfactionComment) {
        this.customerSatisfactionComment = customerSatisfactionComment;
    }

    public String getCustomerSatisfactionComment() {
        return customerSatisfactionComment;
    }

    public void setExpectedBenefit(String associatedRisks) {
        this.associatedRisks = associatedRisks;
    }

    public String getExpectedBenefit() {
        return associatedRisks;
    }
    
    public void setResponsibleSignature(String responsibleSignature) {
        this.responsibleSignature = responsibleSignature;
    }

    public String getResponsibleSignature() {
        return responsibleSignature;
    }
    
    public void setSupervisorSignature(String supervisorSignature) {
        this.supervisorSignature = supervisorSignature;
    }

    public String getSupervisorSignature() {
        return supervisorSignature;
    }     

    public void setSupervisorSignatureDate(java.util.Date supervisorSignatureDate) {
        this.supervisorSignatureDate = supervisorSignatureDate;
    }

    public java.util.Date getSupervisorSignatureDate() {
        return supervisorSignatureDate;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");

        try {

            cip.jobId = this.jobId;
            cip.startDate = this.startDate;
            cip.verifiedRequest = this.verifiedRequest;
            cip.verificationResults = this.verificationResults;            
            cip.additionalRequirements = this.additionalRequirements;
            cip.externalIssues = this.externalIssues;
            cip.internalIssues = this.internalIssues;
            cip.associatedRisks = this.associatedRisks;
            cip.responsibleSignature = this.responsibleSignature;
            cip.supervisorSignature = this.supervisorSignature;           
            cip.responsibleSignatureDate = this.responsibleSignatureDate;           
            cip.supervisorSignatureDate = this.supervisorSignatureDate;           

        } catch (NullPointerException npe) {}
    }

    public void getURLParams() throws ParseException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");           
    
        this.jobId = params.get("jobId");
        this.startDate = sdf.parse(params.get("startDate"));           
        this.verifiedRequest = params.get("verifiedRequest");                  
        this.verificationResults = params.get("verificationResults");
        this.additionalRequirements = params.get("additionalRequirements");
        this.externalIssues = params.get("externalIssues");
        this.internalIssues = params.get("internalIssues");
        this.associatedRisks = params.get("associatedRisks");
        this.responsibleSignature = params.get("responsibleSignature");      
        this.supervisorSignature = params.get("supervisorSignature");      
        this.responsibleSignatureDate = sdf.parse(params.get("responsibleSignatureDate"));      
        this.supervisorSignatureDate = sdf.parse(params.get("supervisorSignatureDate"));      

       /* ep.recNum= params.get("recnum");
        ep.customerType = this.idNumber;        
        ep.customerStat = this.taxNumber;
        ep.customerName = params.get("cname");
        ep.customerSurname = this.cSurname;
        ep.customerGender = this.cGender;
        ep.customerTelephone = this.cTelephone;
        ep.customerCellphone = this.cCellphone;
        ep.customerFax = this.cFax;
        ep.customerPostalAddress = this.cPostalAddress;
        ep.customerPhysicalAddress = this.cPhysicalAddress;
        ep.customerCountry = this.cCountry;
        ep.customerJDate = ""+sqlDate(this.cJDate);*/
    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }

    public String saveCheckIn() {
        getParams();          

        try {

            cip.editCheckIn(request.getSession().getAttribute("uk").toString(), "", "", "");            

            ret = "/check-in/saved";
        } catch (Exception npe) {
            ret = "/check-in/notsaved";
        }
        
        return ret;
    }
    
    //public String saveEditJobs(String recnum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry,String cJDate) throws SQLException {
    public String saveEditCheckIn() throws SQLException {

        getParams(); 

        try {

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            cip.editCheckIn(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/hr/saved";
        } catch (NullPointerException npe) {
            ret = "/hr/notsaved";
        }

        return ret;
    }

    public String mainEditCheckIn() {
        getParams();            

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            cip.editCheckIn(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/jobs/saved";
        } catch (NullPointerException npe) {
            ret = "/jobs/notsaved";
        }
        
        return ret;        
    }
    
    public String editCheckIn() {        
        
        return "/jobs/edit-check-in";        
    }

    public String checkInActions(String jobId) {
        getParams();              

        // save checkin
        try {
            this.jobId = jobId;   
            
            cip.editCheckIn(request.getSession().getAttribute("uk").toString(), "", "", jobId);
            
            this.recnum = cip.checkInId;   

        } catch (Exception e) {
            System.out.println("ERROR: "+ e);
        }       
        
        return "/jobs/check-in-actions";
    }        

    public String editCheckIn_1(String recnum, String jobId, java.util.Date startDate, String verifiedRequest, String verificationResults, String additionalRequirements, String externalIssues, 
            String internalIssues, String associatedRisks, String responsibleSignature, String supervisorSignature, java.util.Date responsibleSignatureDate, java.util.Date supervisorSignatureDate) throws SQLException, ParseException {        

        this.recnum = recnum;
        this.jobId = jobId;
        this.startDate = startDate;           
        this.verifiedRequest = verifiedRequest;           
        this.verificationResults = verificationResults;                   
        this.additionalRequirements = additionalRequirements;
        this.externalIssues = externalIssues;
        this.internalIssues = internalIssues;
        this.associatedRisks = associatedRisks;
        this.responsibleSignature = responsibleSignature;
        this.supervisorSignature = supervisorSignature;
        this.responsibleSignatureDate = responsibleSignatureDate;
        this.supervisorSignatureDate = supervisorSignatureDate;

        return "edit-checkin.xhtml";        
    }    

    public void getCheckIn() throws SQLException {        
        CheckInProcessing cip = new CheckInProcessing();
        cip.getCheckIn(recnum);
    
        recnum = cip.recNum;
        jobId = cip.jobId;
        startDate = cip.startDate;
        verifiedRequest = cip.verifiedRequest;
        verificationResults = cip.verificationResults;
        additionalRequirements = cip.additionalRequirements;
        externalIssues = cip.externalIssues;
        internalIssues = cip.internalIssues;
        associatedRisks = cip.associatedRisks;
        responsibleSignature = cip.responsibleSignature;      
        supervisorSignature = cip.supervisorSignature;      
        responsibleSignatureDate = cip.responsibleSignatureDate;      
        supervisorSignatureDate = cip.supervisorSignatureDate;      

    }
    
    public String deleteCheckIn() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();                    
            cip.editCheckIn(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/jobs/saved";
        } catch (NullPointerException npe) {
            ret = "/jobs/notsaved";
        }
        
        return ret;
    }

    /*public String emailCust() {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        
        
        this.uk = params.get("uk");
        this.name = params.get("name");
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
    }*/
        
    public void showError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Incorrect username and/or password."));
    }        
    
}
