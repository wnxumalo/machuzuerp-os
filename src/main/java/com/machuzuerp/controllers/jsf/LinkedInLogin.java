package com.machuzuerp.controllers.jsf;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.machuzuerp.jdbc.UserProcessing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import static org.primefaces.component.menuitem.UIMenuItemBase.PropertyKeys.url;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

//@Stateless
//@Path("com.machuzuerp.restservices.linkedinlogin")
@SessionScoped
@Named
public class LinkedInLogin implements Serializable {

    private static final String USER_AGENT = "Mozilla/5.0";
    UserProcessing up = new UserProcessing();
    Systems systems = new Systems();

    private String welcomeMessage;

    public LinkedInLogin() {

    }

    public String checkLoginValidity(String name, String surname, String email, HttpSession session) throws IOException {

        checkLinkedInLogin(email, session);

        return welcomeMessage;

    }

    public String checkLoginValidity1(String code, String state, HttpSession session) throws IOException, MalformedURLException, InterruptedException, ExecutionException {

        String strResponse = setLinkedInLoginForm(code);

        JSONObject jsonObj = new JSONObject(strResponse);

        URL obj = new URL("https://api.linkedin.com/v2/me");

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", "Bearer " + jsonObj.getString("access_token"));
        int responseCode = con.getResponseCode();

        StringBuffer response = null;
        if (responseCode == 200) { // success

            try ( BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        }

        obj = new URL("https://api.linkedin.com/v2/clientAwareMemberHandles?q=members&projection=(elements*(primary,EMAIL,handle~))");

        con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", "Bearer " + jsonObj.getString("access_token"));
        responseCode = con.getResponseCode();

        StringBuffer response1 = null;
        if (responseCode == 200) { // success

            try ( BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()))) {
                String inputLine;
                response1 = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response1.append(inputLine);
                }
            }
        }

        obj = new URL("https://api.linkedin.com/v2/clientAwareMemberHandles?q=members&projection=(elements*(primary,PHONE,handle~))");

        con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", "Bearer " + jsonObj.getString("access_token"));
        responseCode = con.getResponseCode();

        StringBuffer response2 = null;
        if (responseCode == 200) { // success

            try ( BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()))) {
                String inputLine;
                response2 = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response2.append(inputLine);
                }
            }
        }

        JSONObject resp1 = new JSONObject(response.toString());

        JSONObject resp2 = new JSONObject(response2.toString());

        //{"elements":[{"handle":"urn:li:emailAddress:446867052","handle~":{"emailAddress":"softwarehs@yahoo.com"},"primary":true}]}
        String jArr = response2.toString().replace('"', '-');
        try {
            int count = 0;
            String emailAddress = "";
            String mainEmailAddress = "";
            boolean endMail = false;
            for (int x = 0; x < jArr.length(); x++) {

                if (jArr.charAt(x) == '{') {
                    count++;
                }

                if (count == 3) {
                    emailAddress = jArr.substring(x, jArr.length());
                    emailAddress = emailAddress.substring(emailAddress.indexOf(":"));

                    x = jArr.length();

                }

            }

            count = 0;
            for (int x = 0; x < emailAddress.length(); x++) {

                if ((emailAddress.charAt(x) == '-') && (count > 1)) {

                    x = emailAddress.length();

                } else if ((emailAddress.charAt(x) != '-') && (count > 1)) {
                    mainEmailAddress += emailAddress.charAt(x);
                }

                count++;

            }
            
            up.email = mainEmailAddress;
        up.name = resp1.getString("localizedFirstName");
        up.surname = resp1.getString("localizedLastName");
        up.company = "LinkedIn-App";

        String msg = up.editUser("", "", "", "");

        session.setAttribute("username", resp1.getString("localizedFirstName"));
        session.setAttribute("uk", systems.uk);

        systems.saveLoginData(mainEmailAddress, resp1.getString("localizedFirstName") + " " + resp1.getString("localizedLastName"), "");


        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

        welcomeMessage = "Thank you for signing in. Welcome To Machuzu ERP!";

        return welcomeMessage;
    }

    private void checkLinkedInLogin(String email, HttpSession session) throws IOException {
        try {
            URL obj = new URL("http://www.machuzu.com/userapi.php?email_address=" + email);
            /*System.out.println("URL: " + obj);
        System.out.println("protocol = " + obj.getProtocol());
        System.out.println("authority = " + obj.getAuthority());
        System.out.println("host = " + obj.getHost());
        System.out.println("port = " + obj.getPort());
        System.out.println("path = " + obj.getPath());
        System.out.println("query = " + obj.getQuery());
        System.out.println("filename = " + obj.getFile());
        System.out.println("ref = " + obj.getRef());*/
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();

            if (responseCode == 200) { // success

                StringBuffer response;
                try ( BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()))) {
                    String inputLine;
                    response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }

                JSONObject jsonObj = new JSONObject(response.toString());

                up.email = jsonObj.getString("email");
                up.name = jsonObj.getString("name");
                up.surname = jsonObj.getString("surname");
                up.company = jsonObj.getString("department");

                String msg = up.editUser("", "", "", "");

                session.setAttribute("username", jsonObj.getString("name"));
                session.setAttribute("uk", systems.uk);

                systems.saveLoginData(jsonObj.getString("email"), jsonObj.getString("name") + " " + jsonObj.getString("surname"), "");

                welcomeMessage = "Thank you for signing in. Welcome To Machuzu ERP!";
            }
        } catch (Exception e) {
            System.out.println("ERR: " + e.toString());
        }

    }

    public String checkLoginValidity1(String name, String surname, String email, HttpSession session) throws IOException {

        up.email = email;
        up.name = name;
        up.surname = surname;
        up.company = "LinkedIn-App";

        String msg = up.editUser("", "", "", "");

        session.setAttribute("username", name);
        session.setAttribute("uk", systems.uk);

        systems.saveLoginData(email, name + " " + surname, "");

        welcomeMessage = "Thank you for signing in. Welcome To Machuzu ERP!";

        return welcomeMessage;

    }

    public void goLogin() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=77qke18tfdtjec&state=foobar&scope=r_emailaddress,r_liteprofile&redirect_uri=http://103.125.218.144:8080/MachuzuERP/faces/LinkedInLogin1.xhtml");
    }

    public String setLinkedInLoginForm(String authCode) throws MalformedURLException, InterruptedException, ExecutionException {

        Form form = new Form();
        form.param("Content-Type", "application/x-www-form-urlencoded")
                .param("grant_type", "authorization_code")
                .param("code", authCode)
                .param("client_id", "77qke18tfdtjec")
                .param("client_secret", "zjrY3ivkMPN7K16S")
                .param("redirect_uri", "http://103.125.218.144:8080/MachuzuERP/faces/LinkedInLogin1.xhtml");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://www.linkedin.com/oauth/v2/accessToken");
        Future<String> response = target.
                request(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, target)
                .buildPost(Entity.form(form)).submit(String.class);

        return response.get();
    }

}
