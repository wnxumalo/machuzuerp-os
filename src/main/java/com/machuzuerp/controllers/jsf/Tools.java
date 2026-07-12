
package com.machuzuerp.controllers.jsf;

import java.io.IOException;
import java.util.Properties;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RequestScoped
@Named
public class Tools {

    private String msg;
    private String name;
    private String organisation;
    private String email;

    public Tools() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void viewDesktopManual() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/desktop/ERPbyW-Desktop-Manual/index.htm");
    }

    public void supportRequest() {

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
                return new PasswordAuthentication("erpbyw@gmail.com", "wandz179");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("erpbyw@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("softwarehs@yahoo.com"));
            message.setSubject("ERPbyW Support Request");
            message.setText(msg);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String passwordRequest() {

      Properties props = new Properties();
        props.put("mail.smtp.host", "mail.erpbyw.com");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        // create some properties and get the default Session
        Session sessionMail = Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("notifications@erpbyw.com", "F0^pB_U6.Q}F");
            }
        });

        sessionMail.setDebug(true);

        try {

            Message message = new MimeMessage(sessionMail);
            message.setFrom(new InternetAddress("notifications@erpbyw.com"));
            message.setSubject("Password Request");
            message.setText("NAME: " + name + "\n\nORGANISATION: " + organisation + "\n\nEMAIL: " + email);
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("wandile177@gmail.com"));
            Transport.send(message);           
            
            msg = "Request submitted successfully";

        } catch (MessagingException e) {
            msg = "ERROR: " + e;
        }
        
        return "request-sent.xhtml";
    }

    public static String getPassword() {
        return "password-request.xhtml";
    }

}
