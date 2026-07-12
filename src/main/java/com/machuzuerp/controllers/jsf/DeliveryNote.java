/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.SupplierData;
import com.machuzuerp.jdbc.DeliveryNoteProcessing;
import com.machuzuerp.jdbc.SupplierProcessing;
import com.machuzuerp.jdbc.UserProcessing;
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
import org.primefaces.event.SelectEvent;

@SessionScoped
@Named
public class DeliveryNote implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private String recNum;
    private String invoiceNumber;
    private String uploadedfile;
    private String fileName;
    private Date deliveryDate;
    private String uploadDate;
    private String comments;
    
    //Supplier
    private String supplierId;
    private String supplierName;
    private String telephone;
    private String cellphone;
    private String fax;
    private String postalAddress;
    private String physicalAddress;
    private String country;
    private String description;
    private String vatNum;
    private String email;

    private Date dateFrom;
    private Date dateTo;

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    DeliveryNoteProcessing dnp = new DeliveryNoteProcessing();

    protected List<DeliveryNote> deliveryNotes = new ArrayList<DeliveryNote>();
    private DeliveryNote selectedDeliveryNote;
    private SupplierData selectedSupplier = new SupplierData();
    SupplierProcessing sp = new SupplierProcessing();

    String query = "";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat stf = new SimpleDateFormat("hh:mm");

    String msg = "";   

    public DeliveryNote() {
        
    }

    public DeliveryNote(String recNum, String supplierName, Date deliveryDate, String invoiceNumber, String fileName, String comments) {

        this.recNum = recNum;
        this.supplierName = supplierName;
        this.invoiceNumber = invoiceNumber;
        this.fileName = fileName;
        this.deliveryDate = deliveryDate;
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
        this.recNum = ""+dnp.getMaxRec();

        connection = Systems.initConnection();
        connection.setAutoCommit(false);

        INSERT_PICTURE = "update deliverynotes set uploaddate = ? where recnum = '" + recNum + "'";
        ps = connection.prepareStatement(INSERT_PICTURE);       
        ps.setString(1, uDate);
        ps.executeUpdate();
        connection.commit();
        
        INSERT_PICTURE = "update deliverynotes set filename = ? where recnum = '" + recNum + "'";
        ps = connection.prepareStatement(INSERT_PICTURE);       
        
        try {
            InputStream is = event.getFile().getInputStream();            
            ps.setString(1, filename);

        } catch (NullPointerException ne) {
            System.out.println("No file uploaded");
        }

        ps.executeUpdate();
        connection.commit();

        INSERT_PICTURE = "update deliverynotes set uploadedfile = ? where recnum = '" + recNum + "'";
        ps = connection.prepareStatement(INSERT_PICTURE);
        
        try {
            InputStream is = event.getFile().getInputStream();            
            ps.setBinaryStream(1, is, (int) event.getFile().getSize());

        } catch (NullPointerException ne) {
            System.out.println("No file uploaded");
        }
                
        ps.executeUpdate();
        connection.commit();        

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "Delivery Note Saved Successully"));

    }
    
    public void saveDeliveryNote() throws IOException, SQLException {
               
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        
        dnp.supplierId = params.get("supplierId");
        dnp.supplierName =  params.get("supplierName");
        dnp.deliveryDate = this.deliveryDate;
        dnp.invoiceNumber = this.invoiceNumber;
        dnp.comments = this.comments;
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        msg = dnp.editDeliveryNote(request.getSession().getAttribute("uk").toString(), "", "", "");
        
        if (msg.equals("Saved")) {

            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/uploadnote.xhtml?uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString());
            }
     
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Delivery Note Not Saved"));
        }        
    }
    
    public String searchDeliveryNote(Date dFrom, Date dTo) {

        deliveryNotes.clear();

        try {

            connection = Systems.initConnection();

            query = "SELECT * FROM deliverynotes where deliverydate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {     
                deliveryNotes.add(new DeliveryNote(results.getString(1), results.getString(3), results.getDate(4), results.getString(5), results.getString(7), results.getString(10)));
            }
            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/payable/deliverynote/deliverynote-listing";
    }

    public List<DeliveryNote> getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(List<DeliveryNote> deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
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
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public DeliveryNote getSelectedDeliveryNote() {
        return selectedDeliveryNote;
    }

    public void setSelectedDeliveryNote(DeliveryNote selectedDeliveryNote) {
        this.selectedDeliveryNote = selectedDeliveryNote;
    }
    
    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPosAdd() {
        return orgPosAdd;
    }

    public void setOrgPosAdd(String orgPosAdd) {
        this.orgPosAdd = orgPosAdd;
    }

    public String getOrgPhysAdd() {
        return orgPhysAdd;
    }

    public void setOrgPhysAdd(String orgPhysAdd) {
        this.orgPhysAdd = orgPhysAdd;
    }
    
    public SupplierData getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(SupplierData selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }
    
    public String getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    
    public String getSupplierName() {
        return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }   
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    } 
        
    public String getCellphone() {
        return cellphone;
    }
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }          
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }  
    
    public String getPostalAddress() {
        return postalAddress;
    }
    
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }  
    
    public String getPhysicalAddress() {
        return physicalAddress;
    }
    
    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }             
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }  
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }        
    
    public String getVatNum() {
        return vatNum;
    }
    
    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }  
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void startDownload() throws IOException {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
 
        this.recNum = params.get("recNum");
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String param = "name=" + request.getSession().getAttribute("username").toString() + "&uk=" + request.getSession().getAttribute("uk").toString() + "&recnum=" + this.recNum;

        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/downloadfile.jsp?" + param);
        }
    }

    public static HttpServletResponse downloadDocument(HttpServletRequest request, HttpServletResponse response, Connection connection, String uk, String recNum) throws SQLException, IOException {

        connection = Systems.initConnection();

        String query = "SELECT * FROM deliverynotes where recnum = '" + recNum + "'";
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
            image = results.getBlob(6);
            imgData = image.getBytes(1,(int)image.length());

            InputStream readImg = results.getBinaryStream(6);
            int index=readImg.read(imgData, 0, (int)image.length());  
            response.reset();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename="+results.getString(7));

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
    
    public String CreateDeliveryNote() throws IOException, SQLException {

        this.recNum = selectedSupplier.getRecNum();
        this.supplierName = selectedSupplier.getSupplierName();
        this.telephone = selectedSupplier.getTelephone();        
        this.cellphone = selectedSupplier.getCellphone();
        this.fax = selectedSupplier.getFax(); 
        this.postalAddress = selectedSupplier.getPostalAddress();
        this.physicalAddress = selectedSupplier.getPhysicalAddress();
        this.country = selectedSupplier.getCountry();
        this.description = selectedSupplier.getDescription();
        this.vatNum = selectedSupplier.getVatNum();
        this.email = selectedSupplier.getEmail();
      
        return "new-note";
    }
    
    public void onRowSelectMain(SelectEvent event) throws IOException, SQLException {

        this.recNum = selectedDeliveryNote.getRecNum();
        this.supplierId = selectedDeliveryNote.getSupplierId();
        this.supplierName = selectedDeliveryNote.getSupplierName();
        this.deliveryDate = selectedDeliveryNote.getDeliveryDate();
        this.invoiceNumber = selectedDeliveryNote.getInvoiceNumber();
        this.fileName = selectedDeliveryNote.getFilename();
        this.comments = selectedDeliveryNote.getComments(); 
        
        results = sp.getSupplierRes(recNum);
        
        while(results.next()) {
         
            this.telephone = results.getString(3);    
            this.cellphone = results.getString(4);
            this.fax = results.getString(5);
            this.postalAddress = results.getString(6);
            this.physicalAddress = results.getString(7);
            this.country = results.getString(8);
            this.description = results.getString(9);
            this.vatNum = results.getString(10);
            this.email = results.getString(11);

        }            
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String param = "uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + recNum + "&supplierId=" + supplierId + "&supplierName=" + supplierName + "&deliveryDate=" + deliveryDate +
                "&invoiceNumber=" + invoiceNumber + "&fileName=" + fileName + "&comments=" + comments + "&telephone=" + telephone + "&cellphone=" + cellphone + "&fax=" + fax
                 + "&postalAddress=" + postalAddress + "&physicalAddress=" + physicalAddress + "&country=" + country + "&description=" + description + "&vatNum=" + vatNum + "&email=" + email;

        UserProcessing up = new UserProcessing();
        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }

        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String mod = params.get("CR");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-deliverynote.xhtml?" + param);
        }

    }
    
    
}
