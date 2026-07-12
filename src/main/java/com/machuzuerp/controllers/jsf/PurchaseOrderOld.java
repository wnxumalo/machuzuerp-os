package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.RequisitionData;
import com.machuzuerp.controllers.datatableprocessing.jsf.SupplierData;
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import com.machuzuerp.jdbc.CustomerProcessing;
import com.machuzuerp.jdbc.EmployeesProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import com.machuzuerp.jdbc.RequisitionProcessing;
import com.machuzuerp.jdbc.SupplierProcessing;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

@SessionScoped
public class PurchaseOrderOld implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String recnum;
    private String poId;
    private String requisitionId;
    private Date poDate;
    private String poDateStr;
    private String supplierName;
    private String tel;
    private String cell;
    private String fax;
    private String email;
    private String posAdd;
    private String physAdd;
    private String supplierId;
    private String vatNum;
    private String notes;

    private Date dFrom;
    private Date dTo;

    String invoiceRecNum = "";

    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private BigDecimal totServCost = new BigDecimal("0");
    private BigDecimal totInvCost = new BigDecimal("0");
    private BigDecimal totPay = new BigDecimal("0");
    private BigDecimal totCost = new BigDecimal("0");
    private BigDecimal outstanding = new BigDecimal("0");
    private BigDecimal payment = new BigDecimal("0");
    private Date paymentDate;

    BigDecimal invCost = new BigDecimal("0");
    BigDecimal cost = new BigDecimal("0");
    int count = 0;
    //int quantity = 0;

    private String to;

    private String requestorName;
    private String remarks;

    private List<PurchaseOrderOld> purchaseOrders = new ArrayList<PurchaseOrderOld>();
    private List<PurchaseOrderOld> filteredPurchaseOrders = new ArrayList<PurchaseOrderOld>();
    private PurchaseOrderOld selectedPurchaseOrder;
    private RequisitionData selectedRequisitionData = new RequisitionData();
    private List<RequisitionData> filteredRequisitionData = new ArrayList<RequisitionData>();
    private List<Requisition> filteredRequisition = new ArrayList<Requisition>();
    private Requisition selectedRequisition = new Requisition();
    private List<SupplierData> filteredSupplier = new ArrayList<SupplierData>();
    private SupplierData selectedSupplier = new SupplierData();
    private SupplierControllerOld selectedSupplierMain;
    private List<ServiceData> selectedServs;

    private PurchaseOrderDTO selectedDTO;
    
    RequisitionProcessing rp = new RequisitionProcessing();
    SupplierProcessing sp = new SupplierProcessing();
    EmployeesProcessing ep = new EmployeesProcessing();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Date requestDate;
    private String itemNo;
    private String itemService;
    private int quantity;
    private int unit;
    String estUnitPrice;
    String estAmount;

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    String beanMsg = "";
    
    String query = "";

    public PurchaseOrderOld() {
        
    }

    public PurchaseOrderOld(String recnum, String requisitionId, Date poDate, String supplierName, String tel, String cell, String fax, String shippingMethod, String posAdd, String physAdd, String vatNum, String notes, String requestorName) {

        this.recnum = recnum;
        this.requisitionId = requisitionId;
        this.poDate = poDate;
        this.supplierName = supplierName;
        this.tel = tel;
        this.cell = cell;
        this.fax = fax;
        this.email = email;
        this.posAdd = posAdd;
        this.physAdd = physAdd;
        this.supplierId = supplierId;
        this.vatNum = vatNum;
        this.requestorName = requestorName;

        this.dFrom = null;
        this.dTo = null;

    }

    // PO DTO
    public PurchaseOrderOld(String itemNo, String itemService, int quantity, String estUnitPrice, String estAmount) {

        this.itemNo = itemNo;
        this.itemService = itemService;
        this.quantity = quantity;
        this.estUnitPrice = estUnitPrice;
        this.estAmount = estAmount;

    }
    
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    // End DTO

    @PostConstruct
    public void init() {
      /*  purchaseOrders = new ArrayList<PurchaseOrderOld>();

        for (int x = 0; x < 15; x++) {
            purchaseOrders.add(new PurchaseOrderOld("" + x, "", 0, "", ""));
        }
*/
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        requestorName = request.getSession().getAttribute("username").toString();
        
        try {
            loadPurchaseOrders();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public Date getPODate() {
        return poDate;
    }

    public void setPODate(Date poDate) {
        this.poDate = poDate;
    }
    
    public String getPODateStr() {
        return poDateStr;
    }

    public void setPODateStr(String poDateStr) {
        this.poDateStr = poDateStr;
    }        

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosAdd() {
        return posAdd;
    }

    public void setPosAdd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPhysAdd() {
        return physAdd;
    }

    public void setPhysAdd(String physAdd) {
        this.physAdd = physAdd;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public List<PurchaseOrderOld> getPurchaseOrderRequisition(String requisitionId) throws SQLException {

        purchaseOrders = new ArrayList<PurchaseOrderOld>();
        System.out.println("REQ: " + requisitionId);
        results = rp.getRequisitionItems(requisitionId);

        while(results.next()) {
            System.out.println("POS: " + results.getString(4));
            purchaseOrders.add(new PurchaseOrderOld(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }
            System.out.println("POSCOUNT: " + purchaseOrders.size());

        /*FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.deliverTo = params.get("deliverTo");
        this.uk = params.get("uk");
        this.recnum = params.get("recnum");

        requestorName = deliverTo;
        */
        return purchaseOrders;
    }
    
    public void onRowSelectRequisitions(SelectEvent event) throws IOException, SQLException {

        String param = "";

        selectedRequisitionData = ((RequisitionData) event.getObject());                  
//        selectedSupplierMain = sp.getSupp(selectedRequisitionData.getSupplierId());

        try {
           // param = "uk=" + session.getAttribute("uk").toString() + "&deliverTo=" + Login.deliverTo + "&recnum=" + selectedRequisition.getRecnum() + "&requestDate=" + selectedRequisition.getRequestDate() + "&deliverTo=" + selectedRequisition.getDeliverTo() + "&requestorName=" + selectedRequisition.getRequestorName() + "&accountsManagerName=" + selectedRequisition.getAccountsManagerName() + "&approverName=" + selectedRequisition.getApproverName();
            
            UserProcessing up = new UserProcessing();
            up.getOrganisation();        

            getPurchaseOrderRequisition(selectedRequisitionData.getRecnum());
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/new-purchaseorder.xhtml");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Requisition Has Not Been Approved Yet"));
        }

    }

    public List<PurchaseOrderOld> getRequisitionPurchaseOrders(String requisitionId) throws SQLException {                
        return purchaseOrders;
    }
    
    public List<PurchaseOrderOld> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setSelectedPurchaseOrders(List<PurchaseOrderOld> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public List<PurchaseOrderOld> getFilteredPurchaseOrders() {
        
        if (filteredPurchaseOrders == null) {
            filteredPurchaseOrders.clear();
            for (PurchaseOrderOld item : purchaseOrders) {

                if (!item.estUnitPrice.equals("")) {
                    item.estAmount = ""+(item.quantity * Float.parseFloat(item.estUnitPrice));
                    filteredPurchaseOrders.add(new PurchaseOrderOld(item.itemNo, item.itemService, item.quantity, item.estUnitPrice, item.estAmount));

                    //totCost =  totCost.add(new BigDecimal(item.estAmount));
                }
            }

        }
        
        return filteredPurchaseOrders;
    }

    public void setFilteredPurchaseOrder(List<PurchaseOrderOld> filteredPurchaseOrders) {
        this.filteredPurchaseOrders = filteredPurchaseOrders;
    }

    public PurchaseOrderOld getSelectedPurchaseOrder() {
        return selectedPurchaseOrder;
    }

    public void setSelectedPurchaseOrder(PurchaseOrderOld selectedPurchaseOrder) {
        this.selectedPurchaseOrder = selectedPurchaseOrder;
    }

    public RequisitionData getSelectedRequisitionData() {
        return selectedRequisitionData;
    }

    public void setSelectedRequisitionData(RequisitionData selectedRequisitionData) {
        this.selectedRequisitionData = selectedRequisitionData;
    }

    public Date getDFrom() {
        return dFrom;
    }

    public void setDFrom(Date dFrom) {
        this.dFrom = dFrom;
    }

    public Date getDTo() {
        return dTo;
    }

    public void setDTo(Date dTo) {
        this.dTo = dTo;
    }

    public String getRecNum() {
        return recnum;
    }

    public void setRecNum(String recnum) {
        this.recnum = recnum;
    }
    
    public String getPOId() {
        return poId;
    }

    public void setPOId(String poId) {
        this.poId = poId;
    }

    public String getItemService() {
        return itemService;
    }

    public void setItemService(String itemService) {
        this.itemService = itemService;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public SupplierData getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(SupplierData selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

    public SupplierControllerOld getSelectedSupplierMain() {
        return selectedSupplierMain;
    }

    public void setSelectedSupplierMain(SupplierControllerOld selectedSupplierMain) {
        this.selectedSupplierMain = selectedSupplierMain;
    }

    public List<ServiceData> getSelectedServs() {
        return selectedServs;
    }

    public void setSelectedServs(List<ServiceData> selectedServs) {
        this.selectedServs = selectedServs;
    }

    public BigDecimal getTotalCost() {
        return totCost;
    }

    public void setTotalCost(BigDecimal totCost) {
        this.totCost = totCost;
    }
    
    public PurchaseOrderDTO getSelectedDTO() {
        return selectedDTO;
    }

    public void setSelectedDTO(PurchaseOrderDTO selectedDTO) {
        this.selectedDTO = selectedDTO;
    }
   
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

    }

    public void creditNoteEditComplete() throws IOException, SQLException {

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

            /*this.cid = params.get("cid");
            this.invId = params.get("invId");
            this.cName = params.get("cName");
            this.cSurdeliverTo = params.get("cSurdeliverTo");
            this.tel = params.get("tel");
            this.cell = params.get("cell");
            this.fax = params.get("fax");
            this.shippingMethod = params.get("shippingMethod");
            this.posAdd = params.get("posAdd");
            this.physAdd = params.get("physAdd");
            this.vatNum = params.get("vatNum");
            this.notes = params.get("notes");
            this.total = params.get("total");
            this.outstanding = params.get("outstanding");   */

            //   if (amount > Double.parseDouble(outstanding)) {
            //     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Credit Note Value Cannot Be Greater Than Invoice Total. "));
            //} else {
            {
                UserProcessing up = new UserProcessing();
                up.getOrganisation();

                if (up.orgNumber.equals("")) {
                    orgNumber = "-";
                } else {
                    orgNumber = up.orgNumber;
                }

                if (up.orgName.equals("")) {
                    orgName = "-";
                } else {
                    orgName = up.orgName;
                }

                if (up.orgTel.equals("")) {
                    orgTel = "-";
                } else {
                    orgTel = up.orgTel;
                }

                if (up.orgFax.equals("")) {
                    orgFax = "-";
                } else {
                    orgFax = up.orgFax;
                }

                if (up.orgEmail.equals("")) {
                    orgEmail = "-";
                } else {
                    orgEmail = up.orgEmail;
                }

                if (up.orgPhysAdd.equals("")) {
                    orgPhysAdd = "-";
                } else {
                    orgPhysAdd = up.orgPhysAdd;
                }

                if (up.orgPosAdd.equals("")) {
                    orgPosAdd = "-";
                } else {
                    orgPosAdd = up.orgPosAdd;
                }

                /* amount = "0";
                outstanding = "0";
                cid = */
                // if (amount > Double.parseDouble(outstanding)) {
                //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Amount cannot be greater than Invoice Outstanding Amount."));
                //} else {
                {

                    connection = Systems.initConnection();

                    // String maxRec = ip.saveEditCreditNote(recnum, cnDate, cid, cName, cSurdeliverTo, tel, cell, fax, posAdd, physAdd, uk, vatNum, notes, total, outstanding, invId, ""+amount);
                    //     recnum = maxRec; 
                    
                    request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

                    url = request.getRequestURL().toString();
                    uri = request.getRequestURI();

                    endIndex = uri.lastIndexOf("/");

                    if (endIndex != -1) {
                        newstr = uri.substring(0, endIndex);
                        FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/credit-note-complete.xhtml");
                    }
                }

            }
        } catch (IOException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Not Saved. " + e));
        }

    }

    public String searchInvoiceCreditNote(Date dFrom, Date dTo) {

        getParams();

//        creditNotes.clear();
        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM invoices where invoiceDate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                //         creditNotes.add(new CreditNote(results.getString(1), results.getDate(4), results.getString(15), results.getString(1), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(18), results.getString(19), results.getString(20),results.getString(14), results.getDouble(15)));                
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/accounting/receivable/creditnote/creditnote-listing";

    }

    public String searchCreditNote(Date dFrom, Date dTo) {

        getParams();

        //  creditNotes.clear();
        try {

            String query = "";
            String invoicerecnum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM creditnotes where notedate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

//public CreditNote(String recnum, Date cnDate, String cid, String invId, String cName, String cSurdeliverTo, String tel, String cell, String fax, String shippingMethod, String posAdd, String physAdd, String vatNum, String total, String outstanding, String notes, Double amount) {
            while (results.next()) {
//                creditNotes.add(new CreditNote(results.getString(1), results.getDate(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(13), results.getString(16), results.getString(17), results.getString(14), results.getDouble(15)));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/accounting/receivable/creditnote/creditnote-listing";

    }
 
    public void onRowEdit(RowEditEvent event) throws ParseException {

    }

    public void onRowCancel(RowEditEvent event) {

    }

    public String savePurchaseOrder(HttpSession session) throws ParseException {

        getParams();

        String ret = "";
        try {
            int maxRec = 0;
            try {

//                maxRec = rp.editPurchaseOrder(session.getAttribute("uk").toString(), "", "", "", selectedRequisitionData.getRecnum(), selectedRequisitionData.getSupplierId(),poDate, session.getAttribute("userdeliverTo").toString(), this.remarks, purchaseOrders);                                

                this.recnum = "" + maxRec;

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfull", "Purchase Order Saved."));

                UserProcessing up = new UserProcessing();
        results = up.getOrganisation();
        while (results.next()) {

            vatNum = results.getString(2);
            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }

                this.poDateStr = sdf.format(poDate);

                filteredPurchaseOrders.clear();
                for (PurchaseOrderOld item : purchaseOrders) {

                    if (!item.estUnitPrice.equals("")) {
                        item.estAmount = ""+(item.quantity * Float.parseFloat(item.estUnitPrice));
                        filteredPurchaseOrders.add(new PurchaseOrderOld(item.itemNo, item.itemService, item.quantity, item.estUnitPrice, item.estAmount));

                        totCost =  totCost.add(new BigDecimal(item.estAmount));
                    }
                }

            } catch (Exception e) {
                System.out.println("579: " + e);
            }

            ret = "/accounting/payable/purchaseorder/purchaseorder-complete";

        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Purchase Order Not Saved. + " + npe));

            ret = "";
        }

        return ret;
    }

    public List<PurchaseOrderOld> loadPurchaseOrders() throws SQLException {
    //System.out.println("PO: " + purchaseOrders.r);
        purchaseOrders = rp.getPurchaseOrders();
System.out.println("PO: " + purchaseOrders.size());
        //purchaseOrders.add(new PurchaseOrderOld(item.itemNo, item.itemService, item.quantity, item.estUnitPrice, item.estAmount));
   
        return purchaseOrders;
    }

    public String loadPurchaseInvoicePurchaseOrders() throws SQLException {
        //} else if (navOption.equals("Purchase Invoice")) {
   //        session.setAttribute("purchaseOrder", null);

try {

           purchaseOrders = rp.getPurchaseOrders();
                      
} catch(Exception e) { 
    System.out.println("222:"+ e);
}
            return "/accounting/payable/purchaseorder/purchaseorder-listing-two";
    }

    public String findPurchaseOrder(Date dateFrom, Date dateTo) throws SQLException {

        String ret;

        this.dFrom = dateFrom;
        this.dTo = dateTo;

        connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM purchaseorder where poDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        purchaseOrders.clear();
        while (results.next()) {

            sp.getSupplier(results.getString(18));
            ep.getEmployee(results.getString(7));

            purchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(10), results.getInt(5), results.getString(6), results.getString(7)));
            
            //public PurchaseOrderOld(String itemNo, String itemService, int quantity, String estUnitPrice, String estAmount) {
            //purchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(12), results.getDate(10), sp.supplierName, sp.deliveryDate, sp.paymentTerms, sp.fax, sp.shippingMethod,
              //      sp.shippingTerms, sp.physicalAddress, sp.vatNum, "", ep.empName + " " + ep.surdeliverTo));
        }
      //  public PurchaseOrderOld(String recnum, String requisitionId, Date poDate, String supplierName, String tel, String cell, String fax, String shippingMethod, String posAdd, 
        //        String physAdd, String vatNum, String notes, String requestorName) {
        connection.close();            

        return "/accounting/payable/purchaseorder/purchaseorder-listing";
    }

    public String newPurchaseOrder(String recnum) throws SQLException, ParseException {
        getParams();

        recnum = selectedSupplier.getRecNum();
        supplierName = selectedSupplier.getSupplierName();
        tel = selectedSupplier.getTelephone();
        cell = selectedSupplier.getCellphone();
        fax = selectedSupplier.getFax();
        email = selectedSupplier.getEmail();
        posAdd = selectedSupplier.getPostalAddress();
        physAdd = selectedSupplier.getPhysicalAddress();
        vatNum = selectedSupplier.getVatNum();        

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        selectedRequisitionData.setRecnum(params.get("requisitionId"));
        selectedRequisitionData.setRequestDate(sdf.parse(params.get("requisitionDate")));
        selectedRequisitionData.setDeliverTo(params.get("deliverTo"));
        selectedRequisitionData.setRequestorName(params.get("requestorName"));
        selectedRequisitionData.setAccountsManagerName(params.get("accountsManagerName"));
        selectedRequisitionData.setApproverName(params.get("approverName"));

        requestorName = request.getSession().getAttribute("userdeliverTo").toString();

       /* connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM requisitionitems where RequisitionId = '" + params.get("requisitionId") + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);    
        
        purchaseOrders.clear();
        while (results.next()) {
            purchaseOrders.add(new PurchaseOrderOld(results.getString(1),results.getString(2),results.getDate(3),results.getString(4),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getString(9),results.getString(10),results.getString(11),results.getString(12),results.getString(13)));                        
        }         
        
        connection.close();   */

        return "/accounting/payable/purchaseorder/new-purchaseorder";
    }

    public String selectInventory(List<ServiceData> selectedServs) {
        getParams();

        cost = new BigDecimal("0");

        count = selectedServs.size();

        for (int x = 0; x < count; x++) {
            selectedServs.iterator().next();
            cost = selectedServs.get(x).getCost();
            quantity = selectedServs.get(x).getQty();
            this.totServCost = this.totServCost.add(cost.multiply(BigDecimal.valueOf(quantity)));
            selectedServs.get(x).setTotCost(cost.multiply(BigDecimal.valueOf(quantity)));
        }

        return "/accounting/billing/invoice/select-inventory";
    }
    
    public void onRowSelect(SelectEvent event) throws IOException, SQLException, ParseException {
        
        this.recnum = ((PurchaseOrderDTO) event.getObject()).getId().toString();

        String param = "recnum=" + this.recnum;
        
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

            /*if (orgNumber.equals("")) {
                orgNumber = "-";
            } 

            if (orgName.equals("")) {
                orgName = "-";
            }

            if (orgTel.equals("")) {
                orgTel = "-";
            } 

            if (orgFax.equals("")) {
                orgFax = "-";
            } 
            System.out.println("555");
            if (orgEmail.equals("")) {
                orgEmail = "-";
            }
            
            if (orgPhysAdd.equals("")) {
                orgPhysAdd = "-";
            }

            if (orgPosAdd.equals("")) {
                orgPosAdd = "-";
            } */
            
            String ret = rp.getPurchaseOrder(recnum);
            String retArr[] = ret.split(",");
            
            sp.getSupplier(retArr[10]);

            this.recnum = retArr[0];
            this.requisitionId = retArr[1];
            this.poDate = sdf.parse(retArr[2]);
            this.supplierName = sp.supplierName;
            this.tel = sp.telephone;
            this.cell = sp.cellphone;
            this.fax = sp.fax;
            this.email = sp.email;
            this.posAdd = sp.postalAddress;
            this.physAdd = sp.physicalAddress;
            this.supplierId = retArr[10];
            this.vatNum = sp.vatNum;
            //this.notes = retArr[12];     
            
            filteredPurchaseOrders.clear();
            filteredPurchaseOrders = rp.getPurchaseOrderItems(recnum);

            try {
                for (PurchaseOrderOld item : filteredPurchaseOrders) {
                    System.out.println("111: " + item.estAmount);
                    if (!item.estUnitPrice.equals("")) {
                        totCost = totCost.add(new BigDecimal(item.estAmount));
                    }
                }
            } catch (NullPointerException npe) {
                System.out.println("222: " + npe);
            }

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-purchaseorder.xhtml");
            }               

        } catch (NullPointerException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An Organisation Must Be Defined Before Continuing"));
        }
      
    }
    
    public void onRowSelectTwo(SelectEvent event) throws IOException, SQLException, ParseException {

        this.recnum = ((PurchaseOrderOld) event.getObject()).recnum;

        String param = "recnum=" + this.recnum;
        
        UserProcessing up = new UserProcessing();
        up.getOrganisation();

        if (up.orgNumber.equals("")) {
            orgNumber = "-";
        } else {
            orgNumber = up.orgNumber;
        }

        if (up.orgName.equals("")) {
            orgName = "-";
        } else {
            orgName = up.orgName;
        }

        if (up.orgTel.equals("")) {
            orgTel = "-";
        } else {
            orgTel = up.orgTel;
        }

        if (up.orgFax.equals("")) {
            orgFax = "-";
        } else {
            orgFax = up.orgFax;
        }

        if (up.orgEmail.equals("")) {
            orgEmail = "-";
        } else {
            orgEmail = up.orgEmail;
        }

        if (up.orgPhysAdd.equals("")) {
            orgPhysAdd = "-";
        } else {
            orgPhysAdd = up.orgPhysAdd;
        }

        if (up.orgPosAdd.equals("")) {
            orgPosAdd = "-";
        } else {
            orgPosAdd = up.orgPosAdd;
        }   

        String ret = rp.getPurchaseOrder(recnum);
        String retArr[] = ret.split(",");

        this.recnum = retArr[0];
        this.requisitionId = retArr[1];
        this.poDate = sdf.parse(retArr[2]);
        this.supplierName = retArr[3];
        this.tel = retArr[4];
        this.cell = retArr[5];
        this.fax = retArr[6];
        this.email = retArr[7];
        this.posAdd = retArr[8];
        this.physAdd = retArr[9];
        this.supplierId = retArr[10];
        this.vatNum = retArr[11];
        this.notes = retArr[12];        
        
        filteredPurchaseOrders = rp.getPurchaseOrderItems(recnum);

        for (PurchaseOrderOld item : filteredPurchaseOrders) {
            if (!item.estUnitPrice.equals("")) {
                totCost = totCost.add(new BigDecimal(item.estAmount));
            }
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-purchaseorder.xhtml");
        }               

    }
    
    public void deletePurchaseOrder() throws SQLException, IOException {

        int maxRec = rp.deletePurchaseOrder(this.recnum); 
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Purchase Order", "Deleted Successfully "));
        
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
            UIComponent component = view.findComponent("form:fsPurchaseOrder");
            component.getAttributes().put("rendered", false);
            
        view = FacesContext.getCurrentInstance().getViewRoot();
            component = view.findComponent("form:print");
            component.getAttributes().put("rendered", false);
            
        view = FacesContext.getCurrentInstance().getViewRoot();
            component = view.findComponent("form:delete");
            component.getAttributes().put("rendered", false);

    }

    public void onRowSelectPO(SelectEvent event) throws IOException, SQLException {

        try {
            savePurchaseInvoice(selectedPurchaseOrder, this.recnum);
        } catch (ParseException ex) {
            Logger.getLogger(PurchaseOrderOld.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void savePurchaseInvoice(PurchaseOrderOld po, String poId) throws ParseException, SQLException, IOException {

        getParams();

        try {
            
            this.poId = po.recnum;
            this.requisitionId = po.requisitionId;
            this.poDate = po.poDate;
            this.supplierName = po.supplierName;
            this.tel = po.tel;
            this.cell = po.cell;
            this.fax = po.fax;
            this.email = po.email;
            this.posAdd = po.posAdd;
            this.physAdd = po.physAdd;
            this.supplierId = po.supplierId;
            this.vatNum = po.vatNum;
            this.notes = po.notes;

            this.filteredPurchaseOrders = rp.getPurchaseOrderItems(this.poId);
            
            for(PurchaseOrderOld item: filteredPurchaseOrders) {
                totCost = totCost.add(new BigDecimal(item.estAmount));
            }

            //int maxRec = rp.editPurchaseInvoice(request.getSession().getAttribute("userdeliverTo").toString(), "", "", "", po, poId);

          //  this.recnum = "" + maxRec;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfull", "Purchase Order Saved."));

            UserProcessing up = new UserProcessing();
            up.getOrganisation();

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
        } catch (Exception e) {}

       /* String ret = rp.getPurchaseInvoice(poId);
        String retArr[] = ret.split(",");

        this.poId = retArr[0];
        this.requisitionId = retArr[1];
        this.poDate = sdf.parse(retArr[2]);
        this.supplierName = retArr[3];
        this.tel = retArr[4];
        this.cell = retArr[5];
        this.fax = retArr[6];
        this.shippingMethod = retArr[7];
        this.posAdd = retArr[8];
        this.physAdd = retArr[9];
        this.supplierId = retArr[10];
        this.vatNum = retArr[11];
        this.notes = retArr[12];

        filteredPurchaseOrders = rp.getPurchaseInvoiceItems(poId);

        try {
            for (PurchaseOrderOld item : filteredPurchaseOrders) {
                if (!item.estUnitPrice.equals("")) {
                    totCost += Double.parseDouble(item.estAmount);
                }
            }
        } catch (NullPointerException npe) {
            System.out.println("000: " + npe);
        }
*/
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/purchaseinvoice-complete.xhtml");
        }
    }

    public void onRowSelectPIEdit(SelectEvent event) throws IOException, SQLException, ParseException {

        this.recnum = ((PurchaseOrderOld) event.getObject()).recnum;

        String param = "recnum=" + this.recnum;

        UserProcessing up = new UserProcessing();
        up.getOrganisation();

        /*if (up.orgNumber.equals("")) {
            orgNumber = "-";
        } else {
            orgNumber = up.orgNumber;
        }

        if (up.orgName.equals("")) {
            orgName = "-";
        } else {
            orgName = up.orgName;
        }

        if (up.orgTel.equals("")) {
            orgTel = "-";
        } else {
            orgTel = up.orgTel;
        }

        if (up.orgFax.equals("")) {
            orgFax = "-";
        } else {
            orgFax = up.orgFax;
        }

        if (up.orgEmail.equals("")) {
            orgEmail = "-";
        } else {
            orgEmail = up.orgEmail;
        }

        if (up.orgPhysAdd.equals("")) {
            orgPhysAdd = "-";
        } else {
            orgPhysAdd = up.orgPhysAdd;
        }

        if (up.orgPosAdd.equals("")) {
            orgPosAdd = "-";
        } else {
            orgPosAdd = up.orgPosAdd;
        }
*/

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
        
        String ret = rp.getPurchaseInvoice(recnum);
        String retArr[] = ret.split(",");

        this.requisitionId = retArr[1];
        this.poId = retArr[2];
        this.poDate = sdf.parse(retArr[3]); //piDate
        this.supplierName = retArr[4];
        this.tel = retArr[5];
        this.cell = retArr[6];
        this.fax = retArr[7];
        this.email = retArr[8];
        this.posAdd = retArr[9];
        this.physAdd = retArr[10];
        this.supplierId = retArr[11];
        this.vatNum = retArr[12];
        this.notes = retArr[13];

        filteredPurchaseOrders = rp.getPurchaseInvoiceItems(recnum);

        try {
            for (PurchaseOrderOld item : filteredPurchaseOrders) {
                if (!item.estUnitPrice.equals("")) {
                    totCost = totCost.add(new BigDecimal(item.estAmount));
                }
            }
        } catch (NullPointerException npe) {
            System.out.println("000: " + npe);
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-purchaseinvoice.xhtml");
        }
    }

    public String findPurchaseInvoice(Date dateFrom, Date dateTo) throws SQLException {

        this.dFrom = dateFrom;
        this.dTo = dateTo;                

        connection = Systems.initConnection();                
        
        int i = 0;
        purchaseOrders = new ArrayList<PurchaseOrderOld>();
        System.out.println("REQ: " + requisitionId);
        results = rp.getRequisitionItems(requisitionId);

        while(results.next()) {
            System.out.println("POS: " + results.getString(4));
            purchaseOrders.add(new PurchaseOrderOld(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }
            System.out.println("POSCOUNT: " + purchaseOrders.size());

        connection.close();

        return "/accounting/payable/purchaseinvoice/purchaseinvoice-listing";
    }
    
    public String newPurchaseInvoice(int poId) {
        return "/";
    }
    
    public List<PurchaseOrderOld> getPurchaseOrderList() {
        return purchaseOrders;
    }
}

class PurchaseOrderDTO {

    private String id;
    private String deliverTo;
    private String deliveryDate;
    private String paymentTerms;
    private String shippingMethod;
    private String shippingTerms;

    public PurchaseOrderDTO(String id, String deliverTo, String deliveryDate, String paymentTerms, String shippingMethod, String shippingTerms) {
        this.id = id;
        this.deliverTo = deliverTo;
        this.deliveryDate = deliveryDate;
        this.paymentTerms = paymentTerms;
        this.shippingMethod = shippingMethod;
        this.shippingTerms = shippingTerms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(String deliverTo) {
        this.deliverTo = deliverTo;
    }
    
    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }
    
    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public String getShippingTerms() {
        return shippingTerms;
    }

    public void setShippingTerms(String shippingTerms) {
        this.shippingTerms = shippingTerms;
    }
}