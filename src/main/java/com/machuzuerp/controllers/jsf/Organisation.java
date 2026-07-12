/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.OrganisationProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class Organisation implements Serializable {

    private String recnum;
    private String number;
    private String orgName;
    private String tel;
    private String fax;
    private String email;
    private String posAdd;
    private String physAdd;
    private String orgid;

    String ret = "";

    OrganisationProcessing op = new OrganisationProcessing();

    HttpServletRequest request;

    public Organisation() {       
    }
    
    public void onDateSelect(SelectEvent event) {
     //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");               
        //setJDate(format.format(event.getObject()));
        //System.out.println("ONDATESELECT:"+cJDate);
       // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public String getRecNum() {
        return recnum;
    }
    
    public void setRecNum(String recnum) {
        this.recnum = recnum;
    }                     
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }               

    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    } 
    
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }
   
    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPosAdd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPosAdd() {
        return posAdd;
    }

    public void setPhysAdd(String physAdd) {
        this.physAdd = physAdd;
    }

    public String getPhysAdd() {
        return physAdd;
    }    
    
    public void setOrgID(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgID() {
        return orgid;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        op.recnum = this.recnum;
        op.number = this.number;
        op.orgName = this.orgName;
        op.tel = this.tel;
        op.fax = this.fax;
        op.email = this.email;
        op.posAdd = this.posAdd;
        op.physAdd = this.physAdd;
        op.orgid = this.orgid;
      
    }
    
    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        
        this.recnum = params.get("recnum");
        this.number = params.get("number");
        this.orgName = params.get("orgName");
        this.tel = params.get("tel");
        this.fax = params.get("fax");
        this.email = params.get("email");
        this.posAdd = params.get("posAdd");
        this.physAdd = params.get("physAdd");
        this.orgid = params.get("orgid");
        
        op.recnum = this.recnum;
        op.number = this.number;
        op.orgName = this.orgName;
        op.tel = this.tel;
        op.fax = this.fax;
        op.email = this.email;
        op.posAdd = this.posAdd;
        op.physAdd = this.physAdd;
        op.orgid = this.orgid;

    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }
    
    public String saveOrganisation() {
        getParams();          

       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");               
        //cDate = sdf.format(cJDate);
        //System.out.println("CDATEsave:"+cDate);

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            op.editOrganisation(request.getSession().getAttribute("uk").toString(), "", "", "");            
            ret = "saved";
        } catch (Exception npe) {
            ret = "notsaved";
        }
        
        return ret;
    }
    
    public String saveEditOrg() throws SQLException {

        getParams(); 

        try {

            op.recnum = this.recnum;
            op.number = this.number;
            op.orgName = this.orgName;
            op.tel = this.tel;
            op.fax = this.fax;
            op.email = this.email;
            op.posAdd = this.posAdd;
            op.physAdd = this.physAdd;
            op.orgid = this.orgid;
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            op.editOrganisation(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/saved";
        } catch (NullPointerException npe) {
            ret = "/notsaved";
        }

        return ret;
    }
    
    public String editOrganisation(String recnum, String number, String orgName, String tel, String fax,  String email, String posAdd, String physAdd, String orgid) throws SQLException, ParseException {
        
        this.recnum = recnum;
        this.number = number;
        this.orgName = orgName;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.orgid = orgid;
        
        return "/edit-organisation";        
    }

    public void getOrganisation() throws SQLException {        

        op.getOrganisation(recnum);            
        
        op.recnum = this.recnum;
        op.number = this.number;
        op.orgName = this.orgName;
        op.tel = this.tel;
        op.fax = this.fax;
        op.email = this.email;
        op.posAdd = this.posAdd;
        op.physAdd = this.physAdd;
        op.orgid = this.orgid;

    }
    
    public String deleteOrganisation() {
         getParams();
        
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            op.editOrganisation(request.getSession().getAttribute("uk").toString(), "delete", "", recnum);
            ret = "/saved";
        } catch (NullPointerException npe) {
            ret = "/notsaved";
        }
        
        return ret;
    }
    
    public void setOrganisation(String recnum, String number, String orgName, String tel, String fax,  String email, String posAdd, String physAdd, String orgid) {

        this.recnum = recnum;
        this.number = number;
        this.orgName = orgName;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.orgid = orgid;

    }

   /* public void emailCust() {
        
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
			message.setText("Dear " + cName + "," + "\n\n " + msg);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    */

}
