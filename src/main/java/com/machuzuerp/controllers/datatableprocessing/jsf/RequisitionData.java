/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.jdbc.RequisitionProcessing;
import com.machuzuerp.jdbc.SupplierProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.controllers.jsf.ApprovalItem;
import com.machuzuerp.controllers.jsf.Requisition;
import com.machuzuerp.controllers.jsf.RequisitionJustification;
import com.machuzuerp.controllers.jsf.RequisitionReceipt;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.jdbc.EmployeesProcessing;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.event.SelectEvent;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

/*
 * @author Administrator
 */
@SessionScoped
@Named
public class RequisitionData implements Serializable {

    protected List<RequisitionData> requisitionData = new ArrayList<RequisitionData>();
    protected List<Requisition> requisitions = new ArrayList<Requisition>();
    RequisitionData selectedRequisition;

    protected List<RequisitionReceipt> uploadedReceipts = new ArrayList();
    protected List<RequisitionJustification> uploadedJustifications = new ArrayList();

    private String recnum;

    private Date requestDate;
    private String itemNo;
    private String itemService;
    private int quantity;
    private int unit;
    private String estUnitPrice;
    private String estAmount;

    private float totalAmount;

    private Date requestedDeliveryDate;
    private String deliverTo;
    private String soleSourceJustificationAttached;
    private String requestorName;
    private String accountsManagerName;
    private String availableFunds;
    private String accountingData;
    private String approverName;
    private String approverJobTitle;
    private Date approvalDate;
    private Long supplierId;
    private String remarks;
    private String status;

    private Date dateFrom;
    private Date dateTo;

    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private String supplierRecNum;
    private String supplierName;
    private String telephone;
    private String cellphone;
    private String postalAddress;
    private String physicalAddress;
    private String vatNum;
    private String email;

    private String approvalColor;

    private String filesURL;

    SimpleDateFormat stf1 = new SimpleDateFormat("hh:mm:ss");

    int maxRec = 0;

    RequisitionProcessing rp = new RequisitionProcessing();
    SupplierProcessing sp = new SupplierProcessing();
    EmployeesProcessing ep = new EmployeesProcessing();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Connection connection;
    Statement statement;
    ResultSet results;

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    String ret = "";
    String query = "";

    protected List<ApprovalItem> approvalitems = new ArrayList<ApprovalItem>();

    public RequisitionData() {

    }

    public RequisitionData(String recnum, Date requestDate, String deliverTo, String requestorName, String accountsManagerName, Date requestedDeliveryDate, String status, String approverName, Long supplierId) {

        this.recnum = recnum;
        this.requestDate = requestDate;
        this.deliverTo = deliverTo;
        this.requestorName = requestorName;
        this.accountsManagerName = accountsManagerName;
        this.requestedDeliveryDate = requestedDeliveryDate;
        this.status = status;
        this.approverName = approverName;
        this.supplierId = supplierId;

    }

    @PostConstruct
    public void init() {

    }

    public RequisitionData getSelectedRequisition() {
        return selectedRequisition;
    }

    public void setSelectedRequisition(RequisitionData selectedRequisition) {
        this.selectedRequisition = selectedRequisition;
    }

    public java.util.Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(java.util.Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public java.util.Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(java.util.Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<RequisitionData> getRequisitions() {
        return requisitionData;
    }

    public List<RequisitionData> getOpenRequisitions() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM requisition where status <> 'Approved'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        requisitionData.clear();
        while (results.next()) {
            requisitionData.add(new RequisitionData(results.getString(1), results.getDate(2), results.getString(12), results.getString(5), results.getString(12), results.getDate(3), results.getString(13), "", results.getLong(7)));

        }

        return requisitionData;
    }

    public List<Requisition> getRequisitionItems() {
        return requisitions;
    }

    public void setRequisitionItems(List<Requisition> requisitions) {
        this.requisitions = requisitions;
    }

    public java.util.Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(java.util.Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getRecnum() {
        return recnum;
    }

    public void setRecnum(String recnum) {
        this.recnum = recnum;
    }

    public String getItemService() {
        return itemService;
    }

    public void setItemService(String itemService) {
        this.itemService = itemService;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getEstUnitPrice() {
        return estUnitPrice;
    }

    public void setEstUnitPrice(String estUnitPrice) {
        this.estUnitPrice = estUnitPrice;
    }

    public String getEstAmount() {
        return estAmount;
    }

    public void setEstAmount(String estAmount) {
        this.estAmount = estAmount;
    }

    public java.util.Date getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    public void setRequestedDeliveryDate(java.util.Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(String deliverTo) {
        this.deliverTo = deliverTo;
    }

    public String getSoleSourceJustificationAttached() {
        return soleSourceJustificationAttached;
    }

    public void setSoleSourceJustificationAttached(String soleSourceJustificationAttached) {
        this.soleSourceJustificationAttached = soleSourceJustificationAttached;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getAccountsManagerName() {
        return accountsManagerName;
    }

    public void setAccountsManagerName(String accountsManagerName) {
        this.accountsManagerName = accountsManagerName;
    }

    public String getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(String availableFunds) {
        this.availableFunds = availableFunds;
    }

    public String getAccountingData() {
        return accountingData;
    }

    public void setAccountingData(String accountingData) {
        this.accountingData = accountingData;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverJobTitle() {
        return approverJobTitle;
    }

    public void setApproverJobTitle(String approverJobTitle) {
        this.approverJobTitle = approverJobTitle;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<RequisitionReceipt> getUploadedReceipts() {
        return uploadedReceipts;
    }

    public List<RequisitionJustification> getUploadedJustifications() {
        return uploadedJustifications;
    }

    public float getTotalAmount() {
        return totalAmount;
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

    public String getSupplierRecNum() {
        return supplierRecNum;
    }

    public void setSupplierRecNum(String supplierRecNum) {
        this.supplierRecNum = supplierRecNum;
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

    public String getApprovalColor() {
        return approvalColor;
    }

    public void setApprovalColor(String approvalColor) {
        this.approvalColor = approvalColor;
    }

    public String getFilesURL() {
        return filesURL;
    }

    public void setFilesURL(String filesURL) {
        this.filesURL = filesURL;
    }

    public List<ApprovalItem> getApprovalItems() {
        return approvalitems;
    }

    public void setApprovalItems(List<ApprovalItem> approvalitems) {
        this.approvalitems = approvalitems;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        if ((this.recnum.equals("")) | (this.recnum == null)) {
            this.recnum = params.get("recnum");
        }

        rp.rRecNum = this.recnum;
        rp.requestDate = this.requestDate;
        rp.requestedDeliveryDate = this.requestedDeliveryDate;
//        rp.deliverTo = this.deliverTo;
        rp.soleSourceJustificationAttached = this.soleSourceJustificationAttached;
        rp.requestorName = this.requestorName;
        rp.accountsManagerName = this.accountsManagerName;
        rp.availableFunds = this.availableFunds;
        rp.accountingData = this.accountingData;
        rp.approverName = this.approverName;
        rp.approverJobTitle = this.approverJobTitle;
        rp.approvalDate = this.approvalDate;
        rp.remarks = this.remarks;

        //rp.requisitionData = requisitionData;
    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public void onRowEdit(RowEditEvent event) throws ParseException {

    }

    public void onRowCancel(RowEditEvent event) {

    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, SQLException {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        endIndex = url.lastIndexOf("faces");
        if (endIndex != -1) {
            newstr = url.substring(0, endIndex);
        }

        filesURL = newstr + "/Files/";

        copyFile(event.getFile().getFileName(), event.getFile().getInputStream(), (String) event.getComponent().getAttributes().get("folder"));

    }

    public void copyFile(String fileName, InputStream in, String path) {
        try {

            String directoryName = new File("../docroot").getAbsolutePath() + "\\MachuzuERP\\Files\\";

            String fTime = stf1.format(new Date()).replace(":", "");

            endIndex = fileName.lastIndexOf(".");
            String fileType = "";

            for (int x = endIndex; x < fileName.length(); x++) {
                fileType += fileName.charAt(x);
            }

            String fName = "";
            for (int x = 0; x < fileName.length(); x++) {
                if (fileName.charAt(x) == '.') {
                    x = fileName.length();
                } else {
                    fName += fileName.charAt(x);
                }
            }

            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Path fullPath = Paths.get(new File("../docroot").getAbsolutePath() + "\\MachuzuERP\\Files\\" + fName + "_" + fTime + fileType);

            try {
                Files.createFile(fullPath);
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File("../docroot").getAbsolutePath() + "\\MachuzuERP\\Files\\" + fName + "_" + fTime + fileType);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            fName += "_" + fTime + fileType;

            String ret = rp.saveRequisitionReceipt(this.recnum, filesURL, Paths.get(new java.io.File("../docroot").getAbsolutePath()).toString(), fName, fileType);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", ret));

        } catch (IOException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public static HttpServletResponse viewFile(String filename, HttpServletRequest request, HttpServletResponse response) {

        //http://localhost:8080/MachuzuERP/Files/MoA.docx
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String newstr = "";

        String url = request.getRequestURL().toString();

        int endIndex = url.lastIndexOf("faces");

        if (endIndex != -1) {
            newstr = url.substring(0, endIndex);
        }

        response.reset();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + newstr + "Files/" + filename);

        return response;
    }

    public String findRequisition(Date dateFrom, Date dateTo) throws SQLException {

        String ret;

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM requisition where requestDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        requisitionData.clear();
        //results = rp.getOpenRequisitions();

        while (results.next()) {
            ep.getEmployee(results.getString(11));
            deliverTo = ep.empName + " " + ep.surname;
            ep.getEmployee(results.getString(9));
            accountsManagerName = ep.empName + " " + ep.surname;
            requisitionData.add(new RequisitionData(results.getString(1), results.getDate(2), deliverTo, results.getString(5), accountsManagerName, results.getDate(3), results.getString(13), "", results.getLong(7)));
        }

        connection.close();

        //getRequisitions();

        return "requisition/requisition-listing";
    }

    public String findPORequisition(Date dateFrom, Date dateTo) throws SQLException {

        String ret;

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        Systems.closeConnection();
        connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM requisition where requestDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        requisitionData.clear();
        while (results.next()) {
            requisitionData.add(new RequisitionData(results.getString(1), results.getDate(2), results.getString(14), results.getString(5), results.getString(12), results.getDate(3), results.getString(13), "", results.getLong(7)));
        }

        connection.close();

        getRequisitions();

        return "purchaseorder/purchaseorder-listing";
    }

    public void onRowSelect(SelectEvent event) throws IOException, SQLException {

        this.recnum = ((RequisitionData) event.getObject()).recnum;

        rp.getRequisition(recnum);

        this.requestDate = rp.requestDate;
        this.requestedDeliveryDate = rp.requestedDeliveryDate;
        this.deliverTo = ((RequisitionData) event.getObject()).deliverTo;
        this.requestorName = ((RequisitionData) event.getObject()).requestorName;
        this.accountsManagerName = ((RequisitionData) event.getObject()).accountsManagerName;
        this.availableFunds = rp.availableFunds;
        this.accountingData = rp.accountingData;
        this.approverName = rp.approverName;
        this.approverJobTitle = rp.approverJobTitle;
        this.approvalDate = rp.approvalDate;
        this.remarks = rp.remarks;
        this.supplierId = rp.supplierId;

        sp.getSupplier(this.supplierId.toString());

        this.supplierName = sp.supplierName;
        this.telephone = sp.telephone;
        this.cellphone = sp.cellphone;
        this.postalAddress = sp.postalAddress;
        this.physicalAddress = sp.physicalAddress;
        this.vatNum = sp.vatNum;
        this.email = sp.email;

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

        results = rp.getRequisitionApprovals(recnum);

        boolean approved = true;

        approvalitems.clear();
        while (results.next()) {
            approvalitems.add(new ApprovalItem(results.getInt(1), results.getInt(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(10), results.getFloat(7), results.getString(8), results.getString(9)));

            if (results.getString(8).equals("Unapproved")) {
                approved = false;
            }
        }

        if (approved) {
            approvalColor = "#036fab";
        } else {
            approvalColor = "#c7254e";
        }

        connection = Systems.initConnection();

        query = "Select * From requisitionitems where requisitionid = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        requisitions.clear();
        while (results.next()) {

            if (results.getInt(5) > 0) {
                requisitions.add(new Requisition(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));

                totalAmount += Float.parseFloat(results.getString(7));
            }

        }

        results = rp.getUploadedJustifications(Integer.parseInt(this.recnum));

        uploadedJustifications.clear();
        while (results.next()) {
            uploadedJustifications.add(new RequisitionJustification(results.getInt(1), results.getInt(2), results.getString(3), results.getString(5), results.getDate(7)));
        }

        results = rp.getUploadedReceipts(Integer.parseInt(this.recnum));

        uploadedReceipts.clear();
        while (results.next()) {
            uploadedReceipts.add(new RequisitionReceipt(results.getInt(1), results.getInt(2), results.getString(3), results.getString(5), results.getDate(7)));
        }

        String param = "?recnum=" + this.recnum;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/requisition-preview-2.xhtml" + param);
        }

    }

    public void refreshRequisitions() throws SQLException {
        results = rp.getRequisitionApprovals(recnum);

        boolean approved = true;

        approvalitems.clear();
        while (results.next()) {
            approvalitems.add(new ApprovalItem(results.getInt(1), results.getInt(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(10), results.getFloat(7), results.getString(8), results.getString(9)));

            if (results.getString(8).equals("Unapproved")) {
                approved = false;
            }
        }

        if (approved) {
            approvalColor = "#036fab";
        } else {
            approvalColor = "#c7254e";
        }

        connection = Systems.initConnection();

        query = "Select * From requisitionitems where requisitionid = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        requisitions.clear();
        while (results.next()) {

            if (results.getInt(5) > 0) {
                requisitions.add(new Requisition(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));

                totalAmount += Float.parseFloat(results.getString(7));
            }

        }

        results = rp.getUploadedJustifications(Integer.parseInt(this.recnum));

        uploadedJustifications.clear();
        while (results.next()) {
            uploadedJustifications.add(new RequisitionJustification(results.getInt(1), results.getInt(2), results.getString(3), results.getString(5), results.getDate(7)));
        }

        results = rp.getUploadedReceipts(Integer.parseInt(this.recnum));

        uploadedReceipts.clear();
        while (results.next()) {
            uploadedReceipts.add(new RequisitionReceipt(results.getInt(1), results.getInt(2), results.getString(3), results.getString(5), results.getDate(7)));
        }
    }

    public void startDownload(String fileId, String fileName, String uploadDate) throws IOException {

        String param = "recnum=" + fileId + "&fileName=" + fileName + "&uploadDate=" + uploadDate;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/downloadfile.jsp?" + param);
        }
    }

    public static HttpServletResponse downloadDocument(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        String ftype = "";
        String fext = "";
        boolean count = false;

        Path fullPath = Paths.get(new File("C:\\MachuzuERP\\requisitions\\justifications\\").getAbsolutePath() + "\\MoA.pdf");
        String filename = fullPath.toString();
        System.out.println("FNAME: " + filename);
        File file = new File(filename);

        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline;filename=\"" + filename + "\"");

        Files.copy(file.toPath(), response.getOutputStream());
        return response;
    }

    public void submitApproval(int approvalId, int requisitionId, float availableFunds, String approvalStatus, String comments, String employeeId, HttpSession session) {
        try {
            //String employeeId = session.getAttribute("uk").toString();
            System.out.println("DATA: " + approvalId + ":" + employeeId);
            ret = rp.updateRequisitionApproval(approvalId, session.getAttribute("uk").toString(), availableFunds, approvalStatus, comments);

            if (ret.equals("Successful")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Updated Successfully", ""));

                results = rp.getRequisitionApprovals("" + requisitionId);

                boolean approved = true;

                approvalitems.clear();
                while (results.next()) {
                    approvalitems.add(new ApprovalItem(results.getInt(1), results.getInt(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(10), results.getFloat(7), results.getString(8), results.getString(9)));

                    if (results.getString(8).equals("Unapproved")) {
                        approved = false;
                    }
                }

                if (approved) {
                    approvalColor = "#036fab";
                } else {
                    approvalColor = "#c7254e";
                }

            } else if (ret.equals("Incorrect")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Current User Cannot Update This Approval.", ""));
            } else if (ret.equals("Blank")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Capture Approval Status.", ""));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Not Saved: " + e));
        }

    }

    public List<Requisition> loadItems(String recnum) throws SQLException {
        results = rp.getRequisitionItems(recnum);

        requisitions.clear();
        while (results.next()) {
            requisitions.add(new Requisition(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }

        int size = requisitions.size();

        for (int x = size; x < 15; x++) {
            requisitions.add(new Requisition("" + x, "", 0, "", ""));
        }

        return requisitions;
    }

    public void receiptUpload(FileUploadEvent event) throws IOException, SQLException {
        String filename = FilenameUtils.getName(event.getFile().getFileName());

        String ext = "";
        String INSERT_PICTURE = "";

        FileInputStream fis = null;
        PreparedStatement ps = null;

        try {
            connection = Systems.initConnection();

            connection.setAutoCommit(false);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            String cDate = sdf.format(new java.util.Date());

            INSERT_PICTURE = "insert into requisitionreceipts(requisitionId, uploaddate, uploadedfile, filename) values (?,?,?,?)";
            ps = connection.prepareStatement(INSERT_PICTURE);
            ps.setInt(1, Integer.parseInt(this.recnum));
            ps.setString(2, cDate);

            try {
                InputStream is = event.getFile().getInputStream();
                ps.setBinaryStream(3, is, (int) event.getFile().getSize());
                ps.setString(4, filename);

            } catch (NullPointerException ne) {
                System.out.println("No file uploaded");
            }

            ps.executeUpdate();

            connection.commit();

            results = rp.getUploadedReceipts(maxRec);

            uploadedReceipts.clear();
            while (results.next()) {
                uploadedReceipts.add(new RequisitionReceipt(results.getInt(1), results.getInt(2), results.getString(3), results.getString(5), results.getDate(7)));
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "File(s) Uploaded Successfully"));
        } catch (IOException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Error: " + e));
        }

    }

    public String editRequisition() {
        getParams();

        try {

            rp.filteredRequisitions = requisitions;
            maxRec = rp.editRequisition(request.getSession().getAttribute("uk").toString(), "edit", "", this.recnum);

            results = rp.getUploadedReceipts(Integer.parseInt(this.recnum));

            uploadedReceipts.clear();
            while (results.next()) {
                uploadedReceipts.add(new RequisitionReceipt(results.getInt(1), results.getInt(2), results.getString(3), results.getString(5), results.getDate(7)));
            }

            ret = "edit-justification";
        } catch (NumberFormatException | SQLException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Requisition Not Saved. + " + npe));
        }

        return ret;
    }

    public void editFile(String fDat) {
        System.out.println("DDDDD: " + fDat);
    }

}
