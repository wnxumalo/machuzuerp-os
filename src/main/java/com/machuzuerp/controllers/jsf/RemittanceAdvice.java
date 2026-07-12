/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.RemittanceAdviceProcessing;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

@SessionScoped
@Named
public class RemittanceAdvice implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String recNum;
    private String invoiceNumber;
    private String uploadedfile;
    private String fileName;
    private Date remittanceDate;
    private String uploadDate;
    private String comments;

    private Date dateFrom;
    private Date dateTo;

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    RemittanceAdviceProcessing rap = new RemittanceAdviceProcessing();

    String query = "";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat stf = new SimpleDateFormat("hh:mm");

    String msg = "";
   
    public RemittanceAdvice() {
        
    }

    public RemittanceAdvice(String recNum, String invoiceNumber, String fileName, Date remittanceDate, String comments) {

        this.recNum = recNum;
        this.invoiceNumber = invoiceNumber;
        this.fileName = fileName;
        this.remittanceDate = remittanceDate;
        this.comments = comments;

    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, SQLException {
        String filename = FilenameUtils.getName(event.getFile().getFileName());
        String ext = "";
        String INSERT_PICTURE = "";

        FileInputStream fis = null;
        PreparedStatement ps = null;
          
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String uDate = sdf.format(new java.util.Date());
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.recNum = ""+rap.getMaxRec();

        connection = Systems.initConnection();
        
        connection.setAutoCommit(false);

        INSERT_PICTURE = "update remittanceadvice set uploaddate = ? where recnum = '" + recNum + "'";
        ps = connection.prepareStatement(INSERT_PICTURE);       
        ps.setString(1, uDate);
        ps.executeUpdate();
        connection.commit();
        
        INSERT_PICTURE = "update remittanceadvice set filename = ? where recnum = '" + recNum + "'";
        ps = connection.prepareStatement(INSERT_PICTURE);       
        
        try {
            InputStream is = event.getFile().getInputStream();            
            ps.setString(1, filename);

        } catch (NullPointerException ne) {
            System.out.println("No file uploaded");
        }

        ps.executeUpdate();
        connection.commit();

        INSERT_PICTURE = "update remittanceadvice set uploadedfile = ? where recnum = '" + recNum + "'";
        ps = connection.prepareStatement(INSERT_PICTURE);
        
        try {
            InputStream is = event.getFile().getInputStream();            
            ps.setBinaryStream(1, is, (int) event.getFile().getSize());

        } catch (NullPointerException ne) {
            System.out.println("No file uploaded");
        }
                
        ps.executeUpdate();
        connection.commit();        

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "Remittance Advice Saved Successully"));

    }
    
    public void saveRemittanceAdvice() throws IOException, SQLException {  
        
        rap.remittanceDate = this.remittanceDate;
        rap.invoiceNumber = this.invoiceNumber;
        rap.comments = this.comments;
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        msg = rap.editRemittanceAdvice(request.getSession().getAttribute("uk").toString(), "", "", "");
        
        if (msg.equals("Saved")) {
        
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/uploadremittance.xhtml?uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString());
            }
        /*} else if (msg.equals("Exists")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "A Delivery Note For The Specified Invoice Already Exists"));*/
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Delivery Note Not Saved"));
        }        
    }

    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDeliveryDate() {
        return remittanceDate;
    }

    public void setDeliveryDate(Date remittanceDate) {
        this.remittanceDate = remittanceDate;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }   

    public void startDownload() throws IOException {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
 
        this.recNum = params.get("recNum");
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
       // String param = "name=" + name + "&uk=" + uk + "&recnum=" + this.recNum + "&fileName=" + fileName + "&teamID=" + teamID + "&teamName=" + teamName + "&uploadDate=" + uploadDate;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
 //           FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/downloadfile.jsp?" + param);
        }
    }
    
    public static HttpServletResponse downloadDocument(HttpServletRequest request, HttpServletResponse response, Connection connection, String uk, String recNum) throws SQLException, IOException {   
//public static HttpServletResponse viewDoc(HttpServletRequest request, HttpServletResponse response, Connection connection, String fID) throws SQLException, IOException {         

        connection = Systems.initConnection();

        String query = "SELECT * FROM files where recnum = '" + recNum + "'";
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        String ftype = "";
        String fext = "";
        boolean count = false;
        while (results.next()) {
            ftype = results.getString(6);

            Blob image;
            byte[ ] imgData ;
            Statement stmt = connection.createStatement();
            image = results.getBlob(5);
            imgData = image.getBytes(1,(int)image.length());

            InputStream readImg = results.getBinaryStream(5);
            int index=readImg.read(imgData, 0, (int)image.length());  
            response.reset();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename="+results.getString(6));

            try (OutputStream o = response.getOutputStream()) {
                o.write(imgData);
                o.flush();
            }

            ftype = "";
            fext = "";
            count = false;
        }

        return response;
        
    }
    
    
}
