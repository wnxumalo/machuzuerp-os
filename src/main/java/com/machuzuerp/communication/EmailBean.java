/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.communication;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Wandile
 */
public class EmailBean {

    public void sendEmail(String msg, String subject, String recipient) {

        //Get the session object  
        Properties properties = System.getProperties();
        
        properties.put("mail.smtp.host", "mail.machuzu.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getInstance(properties,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("info@machuzu.com","zXZK0l_A@N65");
				}
			});
		
        
        //session = Session.getDefaultInstance(properties);

        //compose the message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@machuzu.com"));

            String[] recs = recipient.split(",");

            for (String rec : recs) {

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(rec));

                message.setSubject(subject);
                message.setText(msg + "\n\n Machuzu ERP\n\nhttp://103.125.218.105:8080/MaestroIT\nhttp://www.machuzu.com \n\n");

                Transport.send(message);

            }

            System.out.println("message sent successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        System.out.println("Done");

    }

}
