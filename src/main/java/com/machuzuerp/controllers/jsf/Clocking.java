/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.ClockingProcessing;
import com.machuzuerp.jdbc.EmployeesProcessing;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class Clocking {

    /**
     * Creates a new instance of Clocking
     */
 
    private String recnum;
    
    private String SupervisorId;
    private String EmployeeId;
    private Date ExitDate;
    private Date ExitTime;
    private Date ReturnDate;
    private Date ReturnTime;
    private String WorkDescription;
    private String Outputs;
    private String JobStatus;
    private String Comments;
    private String Approved;

    private String DateFrom;
    private String DateTo;

    private String ret;
    
    private String msgSubject;
    private String msg;
    
    ClockingProcessing cp = new ClockingProcessing();
    EmployeesProcessing ep = new EmployeesProcessing();
    
    HttpServletRequest request;
    
    //public Clocking(String uRecNum, String cType, String cStat, String cName, String cSurname, String cGender, String cTelephone, String cCellphone, String cFax, String cPosAddress, String cPhysAddress, String cCountry, String cJoinDate) {
    public Clocking() {
        
    }     
    
    public String getSupervisorId() {
        return SupervisorId;
    }
    
    public void setSupervisorId(String SupervisorId) {
        this.SupervisorId = SupervisorId;
    }   
    
    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String EmployeeId) {
        this.EmployeeId = EmployeeId;
    }   

    public Date getExitDate() {
        return ExitDate;
    }
    
    public void setExitDate(Date ExitDate) {
        this.ExitDate = ExitDate;
    }   
    
    public Date getExitTime() {
        return ExitTime;
    }
    
    public void setExitTime(Date ExitTime) {
        this.ExitTime = ExitTime;
    }    
    
    public void setReturnDate(Date ReturnDate) {
        this.ReturnDate = ReturnDate;
    }

    public Date getReturnDate() {
        return ReturnDate;
    }
    
    public void setReturnTime(Date ReturnTime) {
        this.ReturnTime = ReturnTime;
    }

    public Date getReturnTime() {
        return ReturnTime;
    }   
    
    public void setWorkDescription(String WorkDescription) {
        this.WorkDescription = WorkDescription;
    }

    public String getWorkDescription() {
        return WorkDescription;
    }
    
    public void setOutputs(String Outputs) {
        this.Outputs = Outputs;
    }

    public String getOutputs() {
        return Outputs;
    }
    
    public void setJobStatus(String JobStatus) {
        this.JobStatus = JobStatus;
    }

    public String getJobStatus() {
        return JobStatus;
    }
    
    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getComments() {
        return Comments;
    }
    
    public void setApproved(String Approved) {
        this.Approved = Approved;
    }

    public String getApproved() {
        return Approved;
    }
    
    public void setDateFrom(String DateFrom) {
        this.DateFrom = DateFrom;
    }

    public String getDateFrom() {
        return DateFrom;
    }
    
    public void setDateTo(String DateTo) {
        this.DateTo = DateTo;
    }

    public String getDateTo() {
        return DateTo;
    }
    
    public void getParams() throws SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
     
        this.recnum = params.get("recnum");           
        
        ResultSet results = ep.getEmployeeByEmpNum(EmployeeId);
        
        while (results.next()) {
            cp.recNum = results.getString(1);
            cp.SupervisorId = results.getString(20);
        }
                
        cp.EmployeeId = this.EmployeeId;
        cp.ExitDate = this.ExitDate;
        cp.ExitTime = this.ExitTime;
        cp.ReturnDate = this.ReturnDate;
        cp.ReturnTime = this.ReturnTime;
        cp.WorkDescription = this.WorkDescription;
        cp.Outputs = this.Outputs;
        cp.JobStatus = this.JobStatus;
        cp.Comments = this.Comments;
        cp.Approved = this.Approved;
       
    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }

    public void saveClocking() throws SQLException {

        getParams();

        try {
            if (cp.CheckClocking(EmployeeId)) {

                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
                cp.editClocking("", "", "", "");

                SupervisorId = "";
                EmployeeId = "";
                ExitDate = null;
                ExitTime = null;
                ReturnDate = null;
                WorkDescription = "";
                Outputs = "";
    
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Clocking", "Saved Successfully."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Clocking", "Employee Id Not Found ."));
            }

        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Clocking", "Error: " + npe));
        }
                
    }

    public String saveEditClocking() throws SQLException {

        getParams(); 

        try {

          /*  cp.clientRecNum = recnum;
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
            cp.customerEmail = email;*/

           // cp.editClocking(uk, "edit", "", recnum);
            ret = "/customers/saved";
        } catch (NullPointerException npe) {
            ret = "/customers/notsaved";
        }

        return ret;
    }

    public String mainEditClocking() throws SQLException {
        getParams();            

        try {
//            cp.editClocking(uk, "edit", "", recnum);
            ret = "/customers/saved";
        } catch (NullPointerException npe) {
            ret = "/customers/notsaved";
        }
        
        return ret;        
    }
       
    public String editClocking_1(String recnum, String vatNum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry, String cJDate, String email) throws SQLException, ParseException {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       /* this.recnum = recnum;
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
        }*/
        return "/customers/edit-customer";        
    }

    public void getClocking() throws SQLException {        
       /* ClockingProcessing cp = new ClockingProcessing();
        cp.getClocking(recnum);            
        
        cType = cp.customerType;
        setType(cp.customerType);
        cStatus = cp.customerStat;
        cName = cp.customerName;
        setClockingName(cName);

        cSurname = cp.customerSurname;
        cGender = cp.customerGender;
        cTelephone = cp.customerTelephone;
        cCellphone = cp.customerCellphone;
        cFax = cp.customerFax;
        cPostalAddress = cp.customerPostalAddress;
        cPhysicalAddress = cp.customerPhysicalAddress;
        cCountry = cp.customerCountry;
        email = cp.customerEmail;
//        cJDate = cp.customerJDate; */

    }
    
    public String deleteClocking() throws SQLException {
         getParams();
        
        try {
//            cp.editClocking(uk, "delete", "", recnum);
            ret = "/customers/saved";
        } catch (NullPointerException npe) {
            ret = "/customers/notsaved";
        }
        
        return ret;
    }
    
    public void emailCust() {
        
      Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("erpbyw@gmail.com","wandz179");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("erpbyw@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("softwarehs@yahoo.com"));
			message.setSubject(msgSubject);
			//message.setText("Dear " + cName + "," + "\n\n " + msg);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    

}
