
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.UserProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class User implements Serializable {

    private String name;
    private String uk;
    private String recnum;

    private String userRecNum = "";
    private String userType;
    private String email;
    private String password;
    private String password1;
    private String userName;
    private String surname;
    private String company;
    private String companyTel;
    private String companyAddress;    
    private String orgid;
    private String level;
    
    String ret = "";
    
    UserProcessing up = new UserProcessing();  
    
    static HttpServletRequest request;
    static  String ipAddress;
    
    private Pattern pattern;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
    public User() {                       

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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }           

    public String getUk() {
        return uk;
    }
    
    public void setUk(String uk) {
        this.uk = uk;
    } 
    
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
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
    
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setUName(String userName) {
        this.userName = userName;
    }

    public String getUName() {
        return userName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }    
    
    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }
 
    public void setCompTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompTel() {
        return companyTel;
    }

    public void setCompAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompAddress() {
        return companyAddress;
    }
    
    public void setOrgID(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgID() {
        return orgid;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }   
    
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.name = params.get("name");
        this.uk = params.get("uk");                   

        up.userRecNum = this.recnum;
        up.userType = this.userType;
        up.email = this.email;
        up.password = this.password;
        up.name = this.userName;
        up.surname = this.surname;
        up.company = this.company;
        up.companyTel = this.companyTel;
        up.companyAddress = this.companyAddress;
        up.orgid = this.orgid;
        up.level = this.level;

    }
    
    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.name = params.get("name");
        this.uk = params.get("uk"); 
        
        this.recnum = params.get("recnum");
        this.userType = params.get("userType");
        this.email = params.get("email");
        this.password = params.get("password");
        this.userName = params.get("userName");
        this.surname = params.get("surname");
        this.company = params.get("company");
        this.companyTel = params.get("companyTel");
        this.companyAddress = params.get("companyAddress");
        this.orgid = params.get("orgid");
        this.level = params.get("level");

        up.userRecNum = this.userRecNum;
        up.userType = this.userType;
        up.email = this.email;
        up.password = this.password;
        up.name = this.userName;
        up.surname = this.surname;
        up.company = this.company;
        up.companyTel = this.companyTel;
        up.companyAddress = this.companyAddress;
        up.orgid = this.orgid;
        up.level = this.level;
    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }
    
    public String saveUser() {
        getParams();          

       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");               
        //cDate = sdf.format(cJDate);
        //System.out.println("CDATEsave:"+cDate);
        String msg = "";
        
        msg = up.editUser(uk, "", "", ""); 

        if (msg.equals("Saved")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "User Saved Scucessfully."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "The email already exists"));
        }

        return ret;
    }       

    public String saveEditUser() throws SQLException {

        getParams();

        try {

            up.userRecNum = this.recnum;
            up.userType = this.userType;
            up.email = this.email;
            up.password = this.password;
            up.name = this.userName;
            up.company = this.company;
            up.companyTel = this.companyTel;
            up.companyAddress = this.companyAddress;
            up.orgid = this.orgid;
            up.level = this.level;
        
            up.editUser(uk, "edit", "", recnum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "User Saved Scucessfully."));                
        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Not Saved", "Error " + npe + " Occurred"));                
        }

        return ret;
    }
    
    public String editUser(String userRecNum, String userType, String userName, String email, String password,  String level) throws SQLException, ParseException {
        
        this.recnum = userRecNum;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.userName = userName;       
        this.level = level;
        
        return "/edit-user";        
    }

    public void getCustomer() throws SQLException {        
        UserProcessing up = new UserProcessing();
        up.getUser(userRecNum);            
        
        userRecNum = up.userRecNum;
        userType = up.userType;
        email = up.email;
        password = up.password;
        userName = up.name;
        surname = up.surname;
        company = up.company;
        companyTel = up.companyTel;
        companyAddress = up.companyAddress;
        orgid = up.orgid;
        level = up.level;        

    }
    
    public String deleteUser() {
         getParams();
        
        try {
            up.editUser(uk, "delete", "", userRecNum);
            ret = "/saved";
        } catch (NullPointerException npe) {
            ret = "/notsaved";
        }
        
        return ret;
    }
    
    public void setUser(String userRecNum, String userType, String email, String password, String userName, String company, String compTel, String compPosAdd, String uk, String level, String orgid) {

        this.userRecNum = userRecNum;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.company = company;
        this.companyTel = compTel;
        this.companyAddress = compPosAdd;
        this.uk = uk;
        this.orgid = orgid;
        this.level = level;

    }
    
    public String addUser() throws SQLException {
        
     //   UserProcessing up = new UserProcessing();
       // up.getOrganisation(uk); 
        
        //this.ouOrgId = up.orgid;
        
        return "new-user";
        
    }
    
    public String registerUser() {
        getParams();  
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ipAddress = request.getHeader("X-FORWARDED-FOR");    
        
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        } 
        
        up.userType = this.userType;
        up.email = this.email;
        up.password = this.password;
        up.name = this.userName;
        up.company = this.company;
        up.companyTel = this.companyTel;
        up.companyAddress = this.companyAddress;     
        
        pattern = Pattern.compile(EMAIL_PATTERN);
        boolean matches = pattern.matcher(email.toString()).matches();
        
        if (matches) {
            try {
                up.registerUser(ipAddress);            
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Thank you for registering for updates."));                
            } catch (Exception npe) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unsuccessful", "Not Registered Successfully"));                
            }            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unsuccessful", "Please enter a valid email adddress."));                
        }            

        return ret;
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
