package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.RequisitionData;
import com.machuzuerp.controllers.jpa.PurchaseOrderFacade;
import com.machuzuerp.controllers.jpa.SupplierFacade;
import com.machuzuerp.entities.PurchaseOrder;
import com.machuzuerp.controllers.jpa.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.entities.Employee;
import com.machuzuerp.entities.Supplier;
import com.machuzuerp.entities.dto.PurchaseOrderDTO;
import com.machuzuerp.jdbc.RequisitionProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import java.io.IOException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import com.machuzuerp.controllers.jpa.EmployeeFacade;

@Named("purchaseOrderController")
@SessionScoped
public class PurchaseOrderController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.PurchaseOrderFacade ejbFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.SupplierFacade ejbSupplierFacade;
    
    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeFacade ejbEmployeeFacade;

    private List<com.machuzuerp.controllers.jsf.PurchaseOrderOld> purchaseOrderDTO = new ArrayList<com.machuzuerp.controllers.jsf.PurchaseOrderOld>();    
    private List<PurchaseOrder> items = new ArrayList<PurchaseOrder>();
    private List<PurchaseOrder> itemsB = null;
    private List<PurchaseOrderDTO> itemsDTO = new ArrayList<PurchaseOrderDTO>();
    private PurchaseOrderDTO selectedDTO;
    private PurchaseOrder selected;
    
    private String requestorName;

    private RequisitionData selectedRequisitionData = new RequisitionData();
    
    private Supplier selectedSupplierMain = new Supplier();
    
    RequisitionProcessing rp = new RequisitionProcessing();
    ResultSet results;
    Connection connection;
    Statement statement;    
    String INSERT_DATA = "";
    PreparedStatement ps = null;
    
    private BigDecimal totCost = new BigDecimal("0");
    
    private List<com.machuzuerp.controllers.jsf.PurchaseOrderOld> filteredPurchaseOrders = new ArrayList<com.machuzuerp.controllers.jsf.PurchaseOrderOld>();

    String query = "";
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    private String piId;
    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;
    
    private String recnum;
    private String poId;
    private String requisitionId;
    private Date poDate;
    private String poDateStr;
    private String supplierName;
    private String tel;
    private String cell;
    private String fax;
    private String shippingMethod;    
    private String email;
    private String posAdd;
    private String physAdd;
    private String supplierId;
    private String vatNum;
    private String notes;
    
    Supplier supplier = null;

    public PurchaseOrderController() {
                            
    }
    
    @PostConstruct
    public void init() {
        items = getFacade().findAll();

        Long blank = new Long(0);
        BigDecimal blank1 = new BigDecimal(0);
        
        itemsDTO.clear();
        items.forEach(order -> {

            supplier  = getSupplierFacade().getSupplier(order.getSupplierId());

            itemsDTO.add(new PurchaseOrderDTO(order.getId(), order.getPODate(), order.getDeliveryDate(), order.getShippingMethod(), order.getShippingTerms(), order.getPaymentTerms(), order.getDeliverTo(),
            "",0,"", 0,"", blank,"", supplier.getId(), supplier.getDescription(), blank, 
            "", blank, "", blank1, blank1, blank1, blank1, blank1));
            
            System.out.println("ITEMS DTO: " +  supplier.getDescription() + ":" + supplier.getId());
        });
        
        System.out.println("ITEMS DTO: " + ":" + itemsDTO.size());

    }
    
    public List<PurchaseOrderDTO> getPurchaseOrders() {
        
        items = getFacade().findAll();
        
        Employee deliverTo = null;
        Employee requestor = null;
        Employee accountsManager = null;
        Employee approver = null;
        Supplier supplier = null;
        
        itemsDTO.clear();
        for (PurchaseOrder item : items) {
            try {
            deliverTo = getEmployeeFacade().find(item.getDeliverTo());
            requestor = getEmployeeFacade().find(item.getRequestorId());
            accountsManager = getEmployeeFacade().find(item.getAccountsManagerId());
            approver = getEmployeeFacade().find(item.getApproverId());
            supplier = getSupplierFacade().find(item.getSupplierId());
            
            
                System.out.println("VALS: " + deliverTo.getEmpName());
                System.out.println("VALS111: " + requestor.getEmpName());
                System.out.println("VALS222: " + accountsManager.getEmpName());
                System.out.println("VALS333: " + approver.getEmpName());
                System.out.println("VALS444: " + supplier.getDescription());

            itemsDTO.add(new PurchaseOrderDTO(item.getId(), item.getPODate(), item.getDeliveryDate(), item.getShippingMethod(), item.getShippingTerms(), item.getPaymentTerms(),
            item.getDeliverTo(), "", item.getRequestorId(), requestor.getEmpName(), 
            item.getAccountsManagerId(), "", item.getApproverId(), "",
            item.getSupplierId(), supplier.getDescription(), item.getInventoryId(), "", item.getRequisitionId(), "", item.getTotal(), item.getSubTotal(), item.getShippingCharges(), item.getTaxAmount(), item.getTotal()));

             } catch (NullPointerException npe) {
                //System.out.println("VALS ERR: " + requestor.getEmpName() + ":" + item.getRequestorId());
            }

            /*itemsDTO.add(new PurchaseOrderDTO(item.getId(), item.getPODate(), item.getDeliveryDate(), item.getShippingMethod(), item.getShippingTerms(), item.getPaymentTerms(),
            item.getDeliverTo(), deliverTo.getEmpName() + " " + deliverTo.getSurname(), item.getRequestorId(), requestor.getEmpName() + " " + requestor.getSurname(), 
            item.getAccountsManagerId(), accountsManager.getEmpName() + " " + accountsManager.getSurname(), item.getApproverId(), approver.getEmpName() + " " + approver.getSurname(),
            item.getSupplierId(), supplier.getDescription(), item.getInventoryId(), "", item.getRequisitionId(), "", item.getTotal(), item.getSubTotal(), item.getShippingCharges(), item.getTaxAmount(), item.getTotal()));*/
        }
        
     /*    public PurchaseOrderDTO(Long id, Date poDate, Date deliveryDate, String shippingMethod, String shippingTerms, String paymentTerms, Long deliverToId, 
        String deliverToName, long requestorId, String requestorName, long accountsManagerId, 
            String accountsManagerName, Long approverId, String approverName, Long supplierId, String supplierName, Long inventoryId, String inventoryDescription, Long requisitionId, 
            String requisitionName, BigDecimal total, BigDecimal subTotal, BigDecimal shippingCharges, BigDecimal taxAmount, BigDecimal totalAmount) {
   */
        
        return itemsDTO;
    }

    public String getPurchaseOrdersByDate(Date dateFrom, Date dateTo) {
        
        items = getFacade().getPurchaseOrdersByDate(dateFrom, dateTo);
        
        Employee deliverTo = null;
        Employee requestor = null;
        Employee accountsManager = null;
        Employee approver = null;
        Supplier supplier = null;
        
        itemsDTO.clear();
        for (PurchaseOrder item : items) {
            
            deliverTo = getEmployeeFacade().find(item.getDeliverTo());
            requestor = getEmployeeFacade().find(item.getRequestorId());
            accountsManager = getEmployeeFacade().find(item.getAccountsManagerId());
            approver = getEmployeeFacade().find(item.getApproverId());
            supplier = getSupplierFacade().find(item.getSupplierId());
            
            itemsDTO.add(new PurchaseOrderDTO(item.getId(), item.getPODate(), item.getDeliveryDate(), item.getShippingMethod(), item.getShippingTerms(), item.getPaymentTerms(),
            item.getDeliverTo(), deliverTo.getEmpName() + " " + deliverTo.getSurname(), item.getRequestorId(), requestor.getEmpName() + " " + requestor.getSurname(), 
            item.getAccountsManagerId(), accountsManager.getEmpName() + " " + accountsManager.getSurname(), item.getApproverId(), approver.getEmpName() + " " + approver.getSurname(),
            item.getSupplierId(), supplier.getDescription(), item.getInventoryId(), "", item.getRequisitionId(), "", item.getTotal(), item.getSubTotal(), item.getShippingCharges(), item.getTaxAmount(), item.getTotal()));
        }
        
     /*    public PurchaseOrderDTO(Long id, Date poDate, Date deliveryDate, String shippingMethod, String shippingTerms, String paymentTerms, Long deliverToId, 
        String deliverToName, long requestorId, String requestorName, long accountsManagerId, 
            String accountsManagerName, Long approverId, String approverName, Long supplierId, String supplierName, Long inventoryId, String inventoryDescription, Long requisitionId, 
            String requisitionName, BigDecimal total, BigDecimal subTotal, BigDecimal shippingCharges, BigDecimal taxAmount, BigDecimal totalAmount) {
   */
        
        return "/accounting/payable/purchaseorder/purchaseorder-listing";
    }

    public PurchaseOrder getSelected() {
        return selected;
    }

    public void setSelected(PurchaseOrder selected) {
        this.selected = selected;
    }
    
    public PurchaseOrderDTO getSelectedDTO() {
        return selectedDTO;
    }

    public void setSelectedDTO(PurchaseOrderDTO selectedDTO) {
        this.selectedDTO = selectedDTO;
    }
    
    public Supplier getSelectedSupplierMain() {
        return selectedSupplierMain;
    }

    public void setSelectedSupplierMain(Supplier selectedSupplierMain) {
        this.selectedSupplierMain = selectedSupplierMain;
    }
    
    public BigDecimal getTotalCost() {
        return totCost;
    }

    public void setTotalCost(BigDecimal totCost) {
        this.totCost = totCost;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PurchaseOrderFacade getFacade() {
        return ejbFacade;
    }
    
    private SupplierFacade getSupplierFacade() {
        return ejbSupplierFacade;
    }
    
    private EmployeeFacade getEmployeeFacade() {
        return ejbEmployeeFacade;
    }
    
    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }
    
    public String getRequestorName() {
        return requestorName;
    }
    
    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
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
    
    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
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

    public List<com.machuzuerp.controllers.jsf.PurchaseOrderOld> getFilteredPurchaseOrders() {
        return filteredPurchaseOrders;
    }

    public void setFilteredPurchaseOrder(List<com.machuzuerp.controllers.jsf.PurchaseOrderOld> filteredPurchaseOrders) {
        this.filteredPurchaseOrders = filteredPurchaseOrders;
    }
    
    public PurchaseOrder prepareCreate() {
        selected = new PurchaseOrder();
        initializeEmbeddableKey();
        return selected;
    }

    public RequisitionData getSelectedRequisitionData() {
        return selectedRequisitionData;
    }

    public void setSelectedRequisitionData(RequisitionData selectedRequisitionData) {
        this.selectedRequisitionData = selectedRequisitionData;
    }
    
    public void create(HttpSession session) throws SQLException {        
        this.selected.setRequestorId(Long.parseLong(request.getSession().getAttribute("uk").toString()));
        this.selected.setCreationDate(new Date());
        selected.setRequisitionId(Long.parseLong(selectedRequisitionData.getRecnum()));
        selected.setStatus("1");
        selected.setSupplierId(selectedRequisitionData.getSupplierId());

        filteredPurchaseOrders.clear();
        for (com.machuzuerp.controllers.jsf.PurchaseOrderOld item : purchaseOrderDTO) {

            if (!item.getEstAmount().equals("")) {
                item.setEstAmount(""+(item.getQuantity() * Float.parseFloat(item.getEstAmount())));
                filteredPurchaseOrders.add(new com.machuzuerp.controllers.jsf.PurchaseOrderOld(item.getItemNo(), item.getItemService(), item.getQuantity(), item.getEstUnitPrice(), item.getEstAmount()));

                //totCost =  totCost.add(new BigDecimal(item.getEstAmount()));
            }
        }

        persist(PersistAction.CREATE, null);
        
        items = getFacade().findAll();
        int size = items.size();
        selected = items.get(size-1);
        Long poId = selected.getId();        

        connection = Systems.initConnection();
        connection.setAutoCommit(false);

        for (com.machuzuerp.controllers.jsf.PurchaseOrderOld orders : filteredPurchaseOrders) {

            String INSERT_DATA = "INSERT INTO purchaseorderitems(purchaseorderid, itemno, itemservice, quantity, estunitprice, estamount) VALUES (?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setLong(1, poId);
            ps.setString(2, orders.getItemNo());
            ps.setString(3, orders.getItemService());
            ps.setInt(4, orders.getQuantity());
            ps.setString(5, orders.getEstUnitPrice());
            ps.setString(6, orders.getEstAmount());

            ps.executeUpdate();

        }

        connection.commit();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Purchase Order", "Purchase Order Saved Successfully"));

    }
    
    public void update() {
        persist(PersistAction.UPDATE, "Purchase Order Updated");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Purchase Order Deleted");
    }

    public List<PurchaseOrder> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<PurchaseOrderDTO> getItemsDTO() {        
        return itemsDTO;
    }

    public void setItemsDTO(List<PurchaseOrderDTO> itemsDTO) {
        this.itemsDTO = itemsDTO;
    }            
    
    public List<PurchaseOrderDTO> getItemsSupplierDTO(Long supplierId) {

        items = getFacade().getSupplierPurchaseOrders(supplierId);


        Long blank = new Long(0);
        BigDecimal blank1 = new BigDecimal(0);
        
        itemsDTO.clear();
        items.forEach(order -> {

            Supplier supplier  = getSupplierFacade().getSupplier(order.getSupplierId());

            itemsDTO.add(new PurchaseOrderDTO(order.getId(), order.getPODate(), order.getDeliveryDate(), order.getShippingMethod(), order.getShippingTerms(), order.getPaymentTerms(), order.getDeliverTo(),
            "",0,"", 0,"", blank,"", supplier.getId(), supplier.getDescription(), blank, 
            "", blank, "", blank1, blank1, blank1, blank1, blank1));
        });
                
        return itemsDTO;
    }
    
    public List<com.machuzuerp.controllers.jsf.PurchaseOrderOld> getPurchaseOrderDTO() {
        return purchaseOrderDTO;
    }
    
    public void setPurchaseOrderDTO(List<com.machuzuerp.controllers.jsf.PurchaseOrderOld> purchaseOrderDTO) {
        this.purchaseOrderDTO = purchaseOrderDTO;
    }
    
    public void onRowEdit(RowEditEvent event) throws ParseException {

    }

    public void onRowCancel(RowEditEvent event) {
 
    }
    
    public void onRowSelect(SelectEvent event) throws IOException, SQLException, ParseException {
        
            filteredPurchaseOrders = rp.getPurchaseOrderItems(selectedDTO.getId().toString());

            try {
                for (com.machuzuerp.controllers.jsf.PurchaseOrderOld item : filteredPurchaseOrders) {
                    if (!item.getEstUnitPrice().equals("")) {
                        totCost = totCost.add(new BigDecimal(item.getEstAmount()));
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
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/../../../accounting/payable/purchaseinvoice/new-purchaseinvoice.xhtml");
            }               
      
    }
    
    public void savePurchaseInvoice() throws ParseException, SQLException, IOException {

        try {
            
         /*   this.poId = selectedDTO.getId();
            this.re = selectedDTO.getRequestorId();
            this.poDate = selectedDTO.getPODate();
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
*/
            this.filteredPurchaseOrders = rp.getPurchaseOrderItems(selectedDTO.getId().toString());
            
            for(PurchaseOrderOld item: filteredPurchaseOrders) {
                totCost = totCost.add(new BigDecimal(item.getEstAmount()));
            }

            int maxRec = rp.editPurchaseInvoice(selectedDTO.getDeliverToName(), "", "", "", selectedDTO, selectedDTO.getId().toString());

            this.piId = "" + maxRec;

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

        String ret = rp.getPurchaseInvoice(piId);
        String retArr[] = ret.split(",");

        //this.poId = retArr[0];
        this.requisitionId = retArr[1];
        try {
            this.poDate = sdf.parse(retArr[3]);
        } catch (Exception e) {}
        this.supplierName = retArr[4];
        this.tel = retArr[5];
        this.cell = retArr[6];
        this.fax = retArr[7];
        this.shippingMethod = retArr[8];
        this.posAdd = retArr[9];
        this.physAdd = retArr[10];
        this.supplierId = retArr[11];
        this.vatNum = retArr[12];
        this.notes = retArr[13];

        filteredPurchaseOrders = rp.getPurchaseInvoiceItems(poId);

        try {
            for (PurchaseOrderOld item : filteredPurchaseOrders) {
                if (!item.estUnitPrice.equals("")) {
                    totCost.add(new BigDecimal(item.estAmount));
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
            //FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/purchaseinvoice-complete.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/../../../accounting/payable/purchaseinvoice/purchaseinvoice-complete.xhtml");
        }
    }

    
    public void onRowSelectSupplier(SelectEvent event) throws IOException, SQLException, ParseException {
        
            filteredPurchaseOrders = rp.getPurchaseOrderItems(selectedDTO.getId().toString());

            try {
                for (com.machuzuerp.controllers.jsf.PurchaseOrderOld item : filteredPurchaseOrders) {
                    if (!item.getEstUnitPrice().equals("")) {
                        totCost = totCost.add(new BigDecimal(item.getEstAmount()));
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
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/../shipment/CreatePO.xhtml");
            }               
      
    }

    public void onRowSelectRequisitions(SelectEvent event) throws IOException, SQLException {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
        
       // requestorName = request.getSession().getAttribute("username").toString();
        
        //selected.setRequestorId();
        
        selectedRequisitionData = ((RequisitionData) event.getObject());                  
        selectedSupplierMain =  getSupplierFacade().find(selectedRequisitionData.getSupplierId()); //.getSupplier(Long.parseLong(((RequisitionData) event.getObject()).getSupplierId()));
        
        try {
           // param = "uk=" + session.getAttribute("uk").toString() + "&name=" + Login.name + "&recnum=" + selectedRequisition.getRecnum() + "&requestDate=" + selectedRequisition.getRequestDate() + "&deliverTo=" + selectedRequisition.getDeliverTo() + "&requestorName=" + selectedRequisition.getRequestorName() + "&accountsManagerName=" + selectedRequisition.getAccountsManagerName() + "&approverName=" + selectedRequisition.getApproverName();
            
            UserProcessing up = new UserProcessing();
            up.getOrganisation();        

            getPurchaseOrderRequisition(selectedRequisitionData.getRecnum());
                        
            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");
            
            prepareCreate();

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/new-purchaseorder.xhtml");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Requisition Has Not Been Approved Yet"));
        }

    }
    
    public void onRowSelectPayment(SelectEvent event) throws IOException, SQLException, ParseException {
        
            filteredPurchaseOrders = rp.getPurchaseOrderItems(selectedDTO.getId().toString());

            try {
                for (com.machuzuerp.controllers.jsf.PurchaseOrderOld item : filteredPurchaseOrders) {
                    if (!item.getEstUnitPrice().equals("")) {
                        totCost = totCost.add(new BigDecimal(item.getEstAmount()));
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
                FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/../payment/CreatePO.xhtml");
            }               
      
    }

    public List<com.machuzuerp.controllers.jsf.PurchaseOrderOld> getPurchaseOrderRequisition(String requisitionId) throws SQLException {

        //purchaseOrderDTO = new ArrayList<com.machuzuerp.controllers.jsf.PurchaseOrderOld>();
        results = rp.getRequisitionItems(requisitionId);

        while(results.next()) {
            purchaseOrderDTO.add(new com.machuzuerp.controllers.jsf.PurchaseOrderOld(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }

        /*FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.name = params.get("name");
        this.uk = params.get("uk");
        this.recnum = params.get("recnum");

        requestorName = name;
        */
        return purchaseOrderDTO;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                if (successMessage != null)
                    JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PurchaseOrder getPurchaseOrder(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<PurchaseOrder> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PurchaseOrder> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public String savePurchaseOrder(HttpSession session) throws ParseException {

        String ret = "";
        try {
            int maxRec = 0;
            try {

                maxRec = rp.editPurchaseOrder(session.getAttribute("uk").toString(), "", "", "", selectedRequisitionData.getRecnum(), selectedRequisitionData.getSupplierId().toString(),selected.getPODate(), 
                        session.getAttribute("username").toString(), selected.getNotes(), purchaseOrderDTO);                                


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfull", "Purchase Order Saved."));

       /*         UserProcessing up = new UserProcessing();
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

        }*/

                //this.poDateStr = sdf.format(poDate);

                purchaseOrderDTO.clear();
                for (com.machuzuerp.controllers.jsf.PurchaseOrderOld item : purchaseOrderDTO) {

                    if (!item.getEstUnitPrice().equals("")) {
                        item.setEstAmount(""+(item.getQuantity() * Float.parseFloat(item.getEstUnitPrice())));
                        purchaseOrderDTO.add(new com.machuzuerp.controllers.jsf.PurchaseOrderOld(item.getItemNo(), item.getItemService(), item.getQuantity(), item.getEstUnitPrice(), item.getEstAmount()));

                        totCost =  totCost.add(new BigDecimal(item.getEstAmount()));
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

    

    @FacesConverter(forClass = PurchaseOrder.class)
    public static class PurchaseOrderControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PurchaseOrderController controller = (PurchaseOrderController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "purchaseOrderController");
            return controller.getPurchaseOrder(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PurchaseOrder) {
                PurchaseOrder o = (PurchaseOrder) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PurchaseOrder.class.getName()});
                return null;
            }
        }

    }

}
