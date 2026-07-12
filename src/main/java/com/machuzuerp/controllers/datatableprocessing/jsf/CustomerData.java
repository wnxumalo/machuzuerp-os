/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Stakeholder;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.jdbc.CustomerProcessing;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.*;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class CustomerData implements Serializable {
  
    Connection connection;
    Statement statement;
    ResultSet results;

    private String name;
    private String uk;

    private String cRecNum;
    private String cType;
    private String cStatus;
    protected String cName;
    protected String cSurname;
    private String cGender;
    private String cTelephone;
    private String cCellphone;
    private String email;
    private String cFax;
    private String cPostalAddress;
    private String cPhysicalAddress;
    private String cCountry;
    private String cJDate;
    private String vatNum;

    private String byName;
    private String bySurname;
    private String byVatNum;
    private String byCellphone;
    int count = 9;    

    private StreamedContent picPath;
    private StreamedContent clientPic;
    String clientRecNum = "";
    String clientDat= "";

    protected List<CustomerData> customers = new ArrayList<CustomerData>();
    private List<CustomerData> allCustomers = new ArrayList<CustomerData>();
    private List<CustomerData> filteredCustomers = new ArrayList<CustomerData>();
    private CustomerData selectedCustomer;
    private Stakeholder customer = new Stakeholder();

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    public CustomerData() {        
    }

    public CustomerData(String cRecNum, String vatNum, String cType, String cStat, String cName, String cSurname, String cGender, String cTelephone, String cCellphone, String email, String cFax, String cPosAddress, String cPhysAddress, String cCountry, String cJoinDate) {

        this.cRecNum = cRecNum;
        this.vatNum = vatNum;
        this.cType = cType;
        this.cStatus = cStat;
        this.cName = cName;
        this.cSurname = cSurname;
        this.cGender = cGender;
        this.cTelephone = cTelephone;
        this.cCellphone = cCellphone;
        this.email = email;
        this.cFax = cFax;
        this.cPostalAddress = cPosAddress;
        this.cPhysicalAddress = cPhysAddress;
        this.cCountry = cCountry;
        this.cJDate = cJoinDate;
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, SQLException {
        String rnum = (String) event.getComponent().getAttributes().get("rnum");
        
        String filename = FilenameUtils.getName(event.getFile().getFileName());
        String ext = "";
        String INSERT_PICTURE = "";    
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();     
        
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        FileInputStream fis = null;
        PreparedStatement ps = null;
  
        connection = Systems.initConnection();

        connection.setAutoCommit(false);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String cDate = sdf.format(new java.util.Date());
        
        INSERT_PICTURE = "insert into clientfiles(clientnum, uploaddate, cfile, cfilename) values (?,?,?,?)";
        ps = connection.prepareStatement(INSERT_PICTURE);
        ps.setString(1, rnum);
        ps.setString(2, cDate);
     
        try {
            InputStream is = event.getFile().getInputStream();
           // fis = new FileInputStream(event.getFile().getInputstream());
            ps.setBinaryStream(3, is, (int) event.getFile().getSize());
            ps.setString(4, filename);

        } catch (NullPointerException ne) {
            System.out.println("No file uploaded");
        }

        ps.executeUpdate();

        connection.commit();

       /* boolean count = false;
        for (int x = 0; x < filename.length(); x++) {
            if (filename.charAt(x) == '.') {
                count = true;
            }

            if (count == true) {
                ext = ext + filename.charAt(x);
            }
        }

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("C:\\Wandile\\ERPbyW\\Uploads\\ClientPictures\\" + rnum + ext)));

        InputStream is = event.getFile().getInputstream();

        byte buf[] = new byte[1048576];
        int len;
        while ((len = is.read(buf)) > 0) {
            stream.write(buf, 0, len);
        }
        is.close();
        stream.close();*/

    }

    public String searchCustomer() {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        customers.clear();
        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            int i = 0;
            if (!cName.equals("")) {
                query = "SELECT * FROM clients where name like '%" + cName + "%'";
            } else if (!cSurname.equals("")) {
                query = "SELECT * FROM clients where surname like '%" + cSurname + "%'";
            } else if (!cSurname.equals("") & (!cName.equals(""))) {
                query = "SELECT * FROM clients where name like '%" + cName + "%' and surname like '%" + cSurname + "%'";
            }

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                clientRecNum = results.getString(1);
                customers.add(new CustomerData(results.getString(1), results.getString(17), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(18), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14)));

            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/customers/customer-listing";
    }

    public List<CustomerData> getAllCustomers() {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());
        
        allCustomers.clear();
        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();
            
            query = "SELECT * FROM clients order by name";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                clientRecNum = results.getString(1);
                allCustomers.add(new CustomerData(results.getString(1), results.getString(17), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(18), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14)));                
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return allCustomers;
    }

    public String filterCustomers() {

        //request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        //getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());       

        try {

            String query = "";
            boolean proceed = false;

            connection = Systems.initConnection();  
            
            boolean done = false;
            
            try {
                byName.equals("");
                bySurname.equals("");
                
                if (byVatNum.equals("")) {
                    throw new NullPointerException();
                }

                done = true; 
                query = "SELECT * FROM clients where name like '%" + byName + "%' and surname like '%" + bySurname + "%'  and vatnum like '%" + byVatNum + "%' order by surname, name";            
            } catch (NullPointerException npe5) {
                
                try {
                    byName.equals("");
                    bySurname.equals(""); 
                    
                    if (bySurname.equals("")) {
                        throw new NullPointerException();
                    }

                    if (done == false) {
                        done = true;
    
                        query = "SELECT * FROM clients where name like '%" + byName + "%' order by surname, name";            
                    }
                } catch (Exception npe2) {
                
                    try {
                        byName.equals("");
                        bySurname.equals(""); 

                        if (byName.equals("")) {
                            throw new NullPointerException();
                        } else if (bySurname.equals("")) {
                            throw new NullPointerException();
                        }

                        if (done == false) {
                            done = true;

                            query = "SELECT * FROM clients where name like '%" + byName + "%' and surname like '%" + bySurname + "%' order by surname, name";            
                        }
                     } catch (Exception npe3) {
                         
                         try {                           
                            byCellphone.equals(""); 

                            if (byCellphone.equals("")) {
                                throw new NullPointerException();
                            } 

                            if (done == false) {
                                done = true;

                                query = "SELECT * FROM clients where cellphone like '%" + byCellphone + "%'";            
                            }
                         } catch (Exception npe4) {}                         
                     
                     }
                
                }
            
            } 
            
            if (query.length() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Missing Fields", "Ensure Name+Surname/VAT Number/Cellphone have values"));
            } else {

                filteredCustomers.clear();

                statement = connection.createStatement();
                results = statement.executeQuery(query);

                int count = 0;
                proceed = true;
                while (results.next()) {                
                    proceed = false;
                    clientRecNum = results.getString(1);              
                    filteredCustomers.add(new CustomerData(results.getString(1), results.getString(17), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(18), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14)));                
                    count++;
                }

                this.count = count;
                
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }        
                
        return "/customers/customer-detailed-list.xhtml";

    }

    public byte[] setPicPath(String productId) throws IOException, SQLException {

        /*Systems.closeConnection();
        boolean logged = Systems.checkLogin(uk);
        String oid = Systems.getOID();
        connection = Systems.initConnection();*/

        byte[] productImage = null;
        String query = "";
        try {
            query = "SELECT cfile, cfilename FROM clientfiles where clientnum = '" + productId + "'";
            //query = "SELECT cfile, cfilename FROM clientfiles where clientnum = '0'";
        } catch (NullPointerException n) {
            query = "SELECT cfile, cfilename FROM clientfiles where clientnum = '0'";
        }
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        String ftype = "";
        String fext = "";
        boolean count = false;
        while (results.next()) {
            ftype = results.getString(2);

            productImage = results.getBytes("cfile");

          /*  InputStream readImg = results.getBinaryStream(1);
            BufferedImage bufferedImage = ImageIO.read(readImg);*/
            //int index=readImg.read(imgData, 0, (int)image.length());  

            for (int x =0;x<ftype.length();x++) {
                if (ftype.charAt(x) == '.') {
                    count = true;
                }

                if (count == true) {
                    fext = fext + ftype.charAt(x);
                }
            }
            
        }

    
        return productImage;
    }

    public List<CustomerData> getFilteredCustomers() {

        filterCustomers();
        //customers.add(new CustomerList("0000","Wandile","Nxum","dwed","sdfvsdf","sdfsd","Sdfsdf","ergergt","bergfergf","wfwrewer","2432edwqed","sdfwefrw","wsfwerw"));

        return filteredCustomers;
    }

    public List<CustomerData> getCustomers() {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());
        searchCustomer();
        //customers.add(new CustomerList("0000","Wandile","Nxum","dwed","sdfvsdf","sdfsd","Sdfsdf","ergergt","bergfergf","wfwrewer","2432edwqed","sdfwefrw","wsfwerw"));

        return customers;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    public String getBySurname() {
        return bySurname;
    }

    public void setBySurname(String bySurname) {
        this.bySurname = bySurname;
    }
    
    public String getByVatNum() {
        return byVatNum;
    }

    public void setByVatNum(String byVatNum) {
        this.byVatNum = byVatNum;
    }
    
    public String getByCellphone() {
        return byCellphone;
    }

    public void setByCellphone(String byCellphone) {
        this.byCellphone = byCellphone;
    }

    public String getRecnum() {
        return cRecNum;
    }

    public void setRecNum(String cRecNum) {
        this.cRecNum = cRecNum;
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

    public void setJDate(String cJDate) {
        this.cJDate = cJDate;
    }

    public String getJDate() {
        return cJDate;
    }
    
    public String getVatNum() {
        return vatNum;
    }
    
    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }    

    public void getParams(String username, String uk) {       
        this.name = username;
        this.uk = uk;
    }
   
    public CustomerData getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(CustomerData selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        /*this.cRecNum = ((CustomerData) event.getObject()).cRecNum;
        this.vatNum = ((CustomerData) event.getObject()).vatNum;
        this.cType = ((CustomerData) event.getObject()).cType;
        this.cStatus = ((CustomerData) event.getObject()).cStatus;
        this.cName = ((CustomerData) event.getObject()).cName;
        this.cSurname = ((CustomerData) event.getObject()).cSurname;
        this.cGender = ((CustomerData) event.getObject()).cGender;
        this.cTelephone = ((CustomerData) event.getObject()).cTelephone;
        this.cCellphone = ((CustomerData) event.getObject()).cCellphone;
        this.email = ((CustomerData) event.getObject()).email;
        this.cFax = ((CustomerData) event.getObject()).cFax;
        this.cPostalAddress = ((CustomerData) event.getObject()).cPostalAddress;
        this.cPhysicalAddress = ((CustomerData) event.getObject()).cPhysicalAddress;
        this.cCountry = ((CustomerData) event.getObject()).cCountry;
        this.cJDate = ((CustomerData) event.getObject()).cJDate;*/

        System.out.println("CUSTOMER: " + selectedCustomer.getRecnum() + ":" + selectedCustomer.getCustomerName());

        String param = "name=" + name + "&uk=" + uk + "&recnum=" + this.cRecNum + "&type=" + cType + "&status=" + cStatus + "&cname=" + cName + "&cSurname=" + cSurname + "&gender=" + cGender + "&telephone=" + cTelephone + "&cellphone=" + cCellphone + "&fax=" + cFax + "&email=" + email + "&posaddress=" + cPostalAddress + "&physaddress=" + cPhysicalAddress + "&country=" + cCountry + "&jdate=" + cJDate + "&vatNum=" + vatNum;
                
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            //FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-customer.xhtml?" + param);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/select-service.xhtml");
        }

    }

    public void viewCustomer() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/customers/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CustomerData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}