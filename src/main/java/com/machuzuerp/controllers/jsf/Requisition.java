
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.EmployeesData;
import com.machuzuerp.jdbc.RequisitionProcessing;
import com.machuzuerp.entities.dto.EmployeeDTO;
import com.machuzuerp.jdbc.EmployeesProcessing;
import com.machuzuerp.jdbc.InventoryProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import java.io.IOException;
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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
/*
 * @author Administrator
 */
@SessionScoped
@Named
public class Requisition implements Serializable {        

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
    private String remarks;
    private String status;
    
    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;
    
    private Long deliveryEmployeeId;
    private Long selectedSupplierId;
    private Long selectedAccountManagerId;
    
    protected List uploadedFiles = new ArrayList();
    
    private String filesURL;
    
    private Date dateFrom;
    private Date dateTo;
    
    int maxRec = 0;

    RequisitionProcessing rp = new RequisitionProcessing();
    EmployeesProcessing ep = new EmployeesProcessing();
    InventoryProcessing ip = new InventoryProcessing();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Connection connection;
    Statement statement;
    ResultSet results;
    
    private EmployeeDTO selectedDeliveryEmployee;
    private EmployeeDTO selectedEmployee;
    private SupplierList selectedSupplier;
    
    protected List<Requisition> requisitions = new ArrayList<Requisition>(); 
    protected List<Requisition> filteredRequisitions = new ArrayList<Requisition>(); 
    protected List<Requisition> orderRequisitions = new ArrayList<Requisition>(); 
    protected List<Requisition> displayRequisitions = new ArrayList<Requisition>(); 
    protected List<EmployeesData> approvers = new ArrayList<EmployeesData>(); 
    protected List<EmployeesData> selectedApprovers = new ArrayList<EmployeesData>();
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    SimpleDateFormat stf1 = new SimpleDateFormat("hh:mm:ss");
    
    public Requisition() {
        
    }

    public Requisition(String itemNo, String itemService, int quantity, String estUnitPrice, String estAmount) {
        
        this.itemNo = itemNo;
        this.itemService = itemService;
        this.quantity = quantity;
        this.unit = unit;
        this.estUnitPrice = estUnitPrice;
        this.estAmount = estAmount;

    }     
    
    @PostConstruct
    public void init() {
        requisitions = new ArrayList<Requisition>();               
        
        for (int x=0;x<15;x++) {
            requisitions.add(new Requisition(""+x,"",0,"",""));
        }
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");  
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        requestorName = request.getSession().getAttribute("username").toString();
        
        try {
            // Orders
            results = ip.getOrderInventory();
        
            while (results.next()) {
                orderRequisitions.add(new Requisition(results.getString(4), results.getString(8), 0, results.getString(12), "0"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Requisition.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
    public EmployeeDTO getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(EmployeeDTO selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }
    
    public EmployeeDTO getSelectedDeliveryEmployee() {
        return selectedDeliveryEmployee;
    }

    public void setSelectedDeliveryEmployee(EmployeeDTO selectedDeliveryEmployee) {
        this.selectedDeliveryEmployee = selectedDeliveryEmployee;
    }
    
    public SupplierList getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(SupplierList selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
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
    
    public float getTotalAmount() {
        return totalAmount;
    }

    public List<Requisition> getFilteredRequisitions() throws SQLException {

        requisitions.clear();

        results = rp.getRequisitionItems(""+maxRec);
        while (results.next()) {
            requisitions.add(new Requisition(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
            
            totalAmount += results.getFloat(7);
        }

        return requisitions;
    }

    public List<Requisition> getRequisitions() {        
        return requisitions;
    }

    public void setRequisitions(List<Requisition> requisitions) {                
        this.requisitions = requisitions;
    }
    
    public List<Requisition> getOrderRequisitions() {        
        return orderRequisitions;
    }

    public void setOrderRequisitions(List<Requisition> orderRequisitions) {                
        this.orderRequisitions = orderRequisitions;
    }
    
    public List<EmployeesData> getApprovers() throws SQLException {

        approvers.clear();
        approvers = ep.getApprovers();

        return approvers;
    }

    public void setApprovers(List<EmployeesData> approvers) {
        this.approvers = approvers;
    }

    public List<EmployeesData> getSelectedApprovers() {
        return selectedApprovers;
    }

    public void setSelectedApprovers(List<EmployeesData> selectedApprovers) {
        this.selectedApprovers = selectedApprovers;
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
    
    public List getUploadedFiles() {
        return uploadedFiles;
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
    
    public String getFilesURL() {
        return filesURL;
    }

    public void setFilesURL(String filesURL) {
        this.filesURL = filesURL;
    }
        
    public Long getDeliveryEmployeeId() {
        return deliveryEmployeeId;
    }

    public void setDeliveryEmployeeId(Long deliveryEmployeeId) {
        this.deliveryEmployeeId = deliveryEmployeeId;
    }
    
    public Long getSelectedSupplierId() {
        return selectedSupplierId;
    }

    public void setSelectedSupplierId(Long selectedSupplierId) {
        this.selectedSupplierId = selectedSupplierId;
    }
    
    public Long getSelectedAccountManagerId() {
        return selectedAccountManagerId;
    }

    public void setSelectedAccountManagerId(Long selectedAccountManagerId) {
        this.selectedAccountManagerId = selectedAccountManagerId;
    }
    
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        //System.out.println("PARAMS: " + params.get("recnum"));
        

        //this.recnum = params.get("recnum");         

        //rp.rRecNum = params.get("recnum");
        rp.requestDate = this.requestDate;        
        rp.requestedDeliveryDate = this.requestedDeliveryDate;
        //rp.deliveryEmployeeName = this.selectedDeliveryEmployee.getName();
        //rp.deliveryEmployeeId = Integer.parseInt(this.selectedDeliveryEmployee.getId().toString());
        rp.soleSourceJustificationAttached = this.soleSourceJustificationAttached;
        rp.requestorName = this.requestorName;
        //rp.accountsManagerName = this.selectedEmployee.getName() + " " + this.selectedEmployee.getSurname();
        rp.availableFunds = "0";
        rp.accountingData = "";
        rp.approverName = this.approverName;
        rp.approverJobTitle = this.approverJobTitle;
        rp.approvalDate = this.requestDate;
        rp.remarks = this.remarks;
        rp.status = this .status;
        rp.filteredRequisitions = requisitions;
        rp.approvers = approvers;

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    //public String saveRequisition(List<Requisition> requisitions, List<EmployeesData> approvers, SupplierList supplier, Long deliveryPerson) throws SQLException {
    public String saveRequisition(String type) throws SQLException {

        System.out.println("PARAMSSAVE: " + deliveryEmployeeId + ":" + selectedSupplierId + ":" + selectedAccountManagerId + ":" + this.requisitions.get(0).getQuantity());
        //System.out.println("PARAMSSAVE11111: " + deliveryPerson + ":" + supplier + ": " + selectedDeliveryEmployee);
        getParams();
        String ret = "";

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
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

        try {

            if (type.equals("order")) {
                rp.filteredRequisitions = this.orderRequisitions; 
            System.out.println("PARAMSSAVE1111: " + this.requisitions.get(0).getQuantity());
            } else {
                System.out.println("PARAMSSAVE2222: " + this.requisitions.get(0).getQuantity());
                rp.filteredRequisitions = this.requisitions;            
            }
            rp.approvers = selectedApprovers;
            //rp.deliveryEmployeeId = deliveryPerson;
            rp.deliveryEmployeeId = deliveryEmployeeId;
            rp.accountsManagerId = selectedAccountManagerId;
            //rp.accountsManagerName = selectedEmployee.getName();            
            rp.supplierId = selectedSupplierId;
            
            rp.status = "";
            maxRec = rp.editRequisition("", "", "", "");

            ret = "upload-justification";
        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Requisition Not Saved. + " + npe));
        }

        return ret;
    }

    public String previewRequsition() {
        
        
        
        return "preview-requisition";
    }

    public String saveEditRequisition() throws SQLException {

        getParams();
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String ret = "";

        try {

            maxRec = rp.editRequisition(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);

            ret = "/accounting/payable/requisition/upload-justification";
        } catch (NullPointerException npe) {
            System.out.println("111: "+ npe);
            ret = "/billing/quotation/notsaved";
        }

        return ret;
    }

    public String mainEditRequisition() throws SQLException {
        getParams();
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String ret = "";

        try {
            rp.editRequisition(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/billing/quotation/saved";
        } catch (NullPointerException npe) {
            ret = "/billing/quotation/notsaved";
        }

        return ret;
    }

    public void onRowEdit(RowEditEvent event) throws ParseException {

    }

    public void onRowCancel(RowEditEvent event) {

    }
    
    public void onCellEdit(CellEditEvent event) throws ParseException {
        
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, SQLException {      
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        url = request.getRequestURL().toString();
        endIndex = url.lastIndexOf("faces");
        if (endIndex != -1) {
            newstr = url.substring(0, endIndex);
        }        

        filesURL = newstr + "/Files/";
        System.out.println("111");
        copyFile(event.getFile().getFileName(), event.getFile().getInputStream(), (String) event.getComponent().getAttributes().get("folder"));
    }

    public void copyFile(String fileName, InputStream in, String path) {
        try {

            String directoryName = new java.io.File("../docroot").getAbsolutePath() + "\\MachuzuERP\\Files\\";
            String fTime = stf1.format(new Date()).replace(":", "");

            endIndex = fileName.lastIndexOf(".");
            String fileType = "";
            
            for (int x=endIndex;x<fileName.length();x++) {
                fileType += fileName.charAt(x);
            }
            
            String fName = "";
            for (int x=0;x<fileName.length();x++) {
                if (fileName.charAt(x) == '.') {
                    x=fileName.length();                    
                } else {
                    fName += fileName.charAt(x);                    
                }
            }

            java.io.File directory = new java.io.File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            } 

            Path fullPath = Paths.get(new java.io.File("../docroot").getAbsolutePath() + "\\MachuzuERP\\Files\\" + fName + "_" + fTime + fileType);

            try {
                Files.createFile(fullPath);
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new java.io.File("../docroot").getAbsolutePath() + "\\MachuzuERP\\Files\\" + fName + "_" + fTime + fileType);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();           
            
            fName += "_" + fTime + fileType;

            String ret = rp.saveRequisitionJustification(""+maxRec, filesURL, Paths.get(new java.io.File("../docroot").getAbsolutePath()).toString(), fName, fileType);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", ret));

        } catch (IOException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }


    public void onRowSelect(SelectEvent event) throws IOException {

       /* getParams();
        getLoginParams();

        this.cRecNum = ((CustomerData) event.getObject()).cRecNum;
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
        this.cJDate = ((CustomerData) event.getObject()).cJDate;

        try {
            this.name = URLEncoder.encode(this.name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CustomerData.class.getName()).log(Level.SEVERE, null, ex);
        }

        String param = "name=" + name + "&uk=" + uk + "&recnum=" + this.cRecNum + "&type=" + cType + "&status=" + cStatus + "&cname=" + cName + "&cSurname=" + cSurname + "&gender=" + cGender + "&telephone=" + cTelephone + "&cellphone=" + cCellphone + "&fax=" + cFax + "&email=" + email + "&posaddress=" + cPostalAddress + "&physaddress=" + cPhysicalAddress + "&country=" + cCountry + "&jdate=" + cJDate + "&vatNum=" + vatNum;
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-customer.xhtml?" + param);
        }      */          

    }

    /*public String editRequisition_1(String iRecNum, String rCheck, java.util.Date iDate, String cName, String cSurname, String tel, String cell, String fax, String posAdd, String physAdd, String country, String recurring, String cid) throws SQLException, ParseException {

        this.iRecNum = iRecNum;
        this.rCheck = rCheck;
        this.iDate = iDate;
        this.cName = cName;
        this.cSurname = cSurname;
        this.tel = tel;
        this.cell = cell;
        this.fax = fax;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.country = country;
        this.recurring = recurring;
        this.cID = cid;

        return "/billing/quotation/edit-quotation";
    }

    public void getRequisition() throws SQLException, ParseException {

        rp.getRequisition(recnum);

        this.iRecNum = rp.iRecNum;
        this.rCheck = rp.rCheck;
        this.iDate = sdf.parse(rp.iDate);
        this.cName = rp.cName;
        this.cSurname = rp.cSurname;
        this.tel = rp.tel;
        this.cell = rp.cell;
        this.fax = rp.fax;
        this.posAdd = rp.posAdd;
        this.physAdd = rp.physAdd;
        this.country = rp.country;
        this.recurring = rp.recurring;
        this.cID = rp.cid;

    }

    public String deleteRequisition() throws SQLException {
        getParams();

        try {
            rp.editRequisition(uk, "delete", "yes", recnum);
            ret = "/quotation/saved";
        } catch (NullPointerException npe) {
            ret = "/quotation/notsaved";
        }

        return ret;
    }

    public String selectService(String recnum) throws SQLException {
        getParams();

        String clientDat = sd.getCustomer(recnum);

        String[] vals = clientDat.split(",");                
        System.out.println("vals: "+clientDat);
        cID = vals[0];
        cName = vals[1];
        cSurname = vals[2];
        tel = vals[3];
        cell = vals[4];
        fax = vals[5];
        email = vals[6];
        posAdd = vals[7];
        physAdd = vals[8];
        vatNum = vals[9];

        return "/accounting/billing/quotation/select-service";
    }

    public String selectInventory() {
        getAccParams();

        cost = 0;
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = selectedServs.get(x).getQty();
            this.totServCost = this.totServCost + (cost * quantity);
            selectedServs.get(x).setTotCost(cost * quantity);
        }

        return "/accounting/billing/quotation/select-inventory";
    }

    public String invSummary(List<ServiceData> selectedServs) {
        getAccParams();

        totServCost = 0;
        totInvCost = 0;
        cost = 0;
        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = selectedServs.get(x).getQty();
            this.totServCost = this.totServCost + (cost * quantity);
            selectedServs.get(x).setTotCost(cost * quantity);
        }

        invCost = 0;
        count = selectedInvs.size();

        for (int x = 0; x < count; x++) {
            selectedInvs.iterator().next();
            invCost = selectedInvs.get(x).getSellingPrice();
            quantity = selectedInvs.get(x).getQuantity();
            this.totInvCost = this.totInvCost + (invCost * quantity);
            selectedInvs.get(x).setTotCost(invCost * quantity);
        }

        this.totCost = totInvCost + totServCost;

        return "/accounting/billing/quotation/quotation-summary";
    }

   
    public String newRequisition() {

        getParams();

        selectedServs = null;
        selectedInvs = null;
        this.totServCost = 0;
        this.totInvCost = 0;
        this.totCost = 0;
        recurring = "";
        amtPaid = 0;
        invDate = "";
        iDate = null;

        return "/accounting/billing/quotation/customer-listing";
    }*/

    
/*
    public String printRequisition() {
        getParams();
        return "/accounting/billing/quotation/print-quotation";
    }

    public void printPDF() throws IOException {

        getAccParams();

        FacesContext.getCurrentInstance().getExternalContext().redirect("print-quotation.jsp?cName=" + cName + "&cSurname=" + cSurname + "&cell=" + cell + "&tel=" + tel + "&posAdd=" + posAdd + "&servDesc=" + servDesc + "&invDesc=" + invDesc + "&sCost=" + sCost + "&iCost=" + iCost);

    }

    public void onDateSelect(SelectEvent event) {
        //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        //setJDate(format.format(event.getObject()));
        System.out.println("ONDATESELECT:" + iDate);
        // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }*/
}
