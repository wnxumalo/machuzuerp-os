package com.machuzuerp.controllers.jsf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Future;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("com.machuzuerp.restservices.linkedinlogin")
public class LoginService implements Serializable {

    public LoginService() {
        System.out.println("000");
    }    
    
    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getLinkedInLogin(@QueryParam("email") @DefaultValue("") String email) {  
  System.out.println("EMAIL: " + email);
        try {
            
        URL obj = new URL("http://www.machuzu.com/userapi.php");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
       // con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();        
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == 200) { // success
            System.out.println("222");
            StringBuffer response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();
                System.out.println("333: " + in.readLine());
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println("444: " + inputLine);
                }
            }
                

                // print result
                System.out.println(response.toString());
                
                //if (response.length() > )
        } else {
                System.out.println("GET request not worked");
        }
        
        } catch (Exception e) {
            System.out.println("ERR: " + e.toString());
        }
        
        return email;
    }

  
}
