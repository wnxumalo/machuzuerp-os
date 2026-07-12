/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.InvoiceData;
import com.machuzuerp.controllers.datatableprocessing.jsf.RequisitionData;
import com.machuzuerp.jdbc.RequisitionProcessing;
import com.machuzuerp.jdbc.InvoiceProcessing;
import com.machuzuerp.jdbc.SupplierProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;

@SessionScoped
@Named
public class MenuNavigation implements Serializable {   
    
    ResultSet results;
    
    private Date dateFrom;
    private Date dateTo;
    private String navOption;
    
    InvoiceProcessing ip = new InvoiceProcessing();
    RequisitionProcessing rp = new RequisitionProcessing();
    SupplierProcessing sp = new SupplierProcessing();
    
    private List<InvoiceData> filteredInvoices = new ArrayList<InvoiceData>();
    private InvoiceData selectedInvoice;
    
    private List<PurchaseOrderOld> purchaseOrders = new ArrayList<PurchaseOrderOld>();
    private List<RequisitionData> filteredRequisitions = new ArrayList<RequisitionData>();
    private RequisitionData selectedRequisition;
    private List<PurchaseOrderOld> filteredPurchaseOrderOlds = new ArrayList<PurchaseOrderOld>();
    private PurchaseOrderOld selectedPurchaseOrderOld;
    private SupplierControllerOld selectedSupplier;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String ret = "";
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;        
  
    public MenuNavigation() throws SQLException {
        filteredPurchaseOrderOlds = rp.getPurchaseOrders();
    }  
    
       
    public java.util.Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(java.util.Date dateFrom) {

        try {
            this.dateFrom = sdf.parse(sdf.format(dateFrom));
        } catch (ParseException e) {
            this.dateFrom = new Date();
        }  
    }
    
    public java.util.Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(java.util.Date dateTo) {
        try {
            this.dateTo = sdf.parse(sdf.format(dateTo));
        } catch (ParseException e) {
            this.dateTo = new Date();
        }         
    }
    
    public String getNavOption() {
        return navOption;
    }

    public void setNavOption(String navOption) {         
            this.navOption = navOption;        
    }
    
    public void navMainDashboard() throws IOException {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("faces");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "faces/maindashboard.xhtml");
        }
        
        System.out.println(url);
        System.out.println(uri);
        System.out.println(newstr);
    }

    public String navReceivable(HttpSession session) {

        if (navOption.equals("Requisition")) {
            session.setAttribute("requisition", null);

            ret = "/accounting/receivable/requisition/new-requisition";
        } else if (navOption.equals("Quotation")) {
           // session.setAttribute("quotation", null);
        
            ret = "/accounting/billing/quotation/customer-listing";
        } else if (navOption.equals("Sales Order")) {
           // session.setAttribute("quotation", null);
        
            ret = "/accounting/receivable/salesorder/customer-listing";
        } else if (navOption.equals("Sales Invoice")) {
           // session.setAttribute("quotation", null);
        
            ret = "/accounting/billing/invoice/customer-listing";
        } else if (navOption.equals("Credit Note")) {
           // session.setAttribute("quotation", null);
        
            ret = "/accounting/receivable/creditnote/invoice-listing";
        } else if (navOption.equals("Receipt")) {
           // session.setAttribute("quotation", null);
        
            ret = "/accounting/billing/receipt/customer-listing";
        } else if (navOption.equals("Point of Sale")) {
           // session.setAttribute("quotation", null);
        
            ret = "/accounting/billing/posreceipt/inventory-listing";
        }        

        return ret;
    }
    
    public void findInvoice(Date from, Date to) throws SQLException {        
        filteredInvoices = ip.findInvoice(from, to);        
    }
    
    public List<InvoiceData> getInvoices() throws SQLException {
        return filteredInvoices;
    }
    
    public InvoiceData getSelectedInvoice() {
        return selectedInvoice;
    }

    public void setSelectedInvoice(InvoiceData selectedInvoice) {
        this.selectedInvoice = selectedInvoice;
    }
    
    public RequisitionData getSelectedRequisition() {
        return selectedRequisition;
    }

    public void setSelectedRequisition(RequisitionData selectedRequisition) {
        this.selectedRequisition = selectedRequisition;
    }
    
    public List<RequisitionData> getRequisitions() {
        return filteredRequisitions;
    }

    public void setRequisitions(List<RequisitionData> filteredRequisitions) {
        this.filteredRequisitions = filteredRequisitions;
    }       
    
    public PurchaseOrderOld getSelectedPurchaseOrderOld() {
        return selectedPurchaseOrderOld;
    }

    public void setSelectedPurchaseOrderOld(PurchaseOrderOld selectedPurchaseOrderOld) {
        this.selectedPurchaseOrderOld = selectedPurchaseOrderOld;
    }
    
    public SupplierControllerOld getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(SupplierControllerOld selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }
    
    public List<PurchaseOrderOld> getFilteredPurchaseOrderOlds() throws SQLException {
        //if (filteredPurchaseOrderOlds == null) {
            filteredPurchaseOrderOlds = rp.getPurchaseOrders();
        //}
        
        System.out.println("222: " + filteredPurchaseOrderOlds.size());
        return filteredPurchaseOrderOlds;
    }

    public void setFilteredPurchaseOrderOlds(List<PurchaseOrderOld> filteredPurchaseOrderOlds) {
        this.filteredPurchaseOrderOlds = filteredPurchaseOrderOlds;
    }

    public void getPIPurchaseOrderOlds() throws SQLException {

        filteredPurchaseOrderOlds = rp.getPIPurchaseOrders(dateFrom, dateTo);
        
        System.out.println("333: " + filteredPurchaseOrderOlds.size());
        
    }

    public String navPayable(HttpSession session) throws SQLException {

        if (navOption.equals("ReorderRequisition")) {
            session.setAttribute("reorderRequisition", null);

            ret = "/accounting/payable/requisition/reorder-requisition";
        } else if (navOption.equals("Requisition")) {
            session.setAttribute("requisition", null);

            ret = "/accounting/payable/requisition/new-requisition";
        } else if (navOption.equals("Purchase Order")) {
            session.setAttribute("purchaseOrder", null);

            filteredRequisitions = rp.getRequisitions();

            ret = "/accounting/payable/purchaseorder/requisition-listing";
        
        } else if (navOption.equals("Purchase Invoice")) {
            session.setAttribute("purchaseOrder", null);

            //filteredPurchaseOrderOlds = rp.getPurchaseOrderDates();

            ret = "/accounting/payable/purchaseinvoice/purchaseorder-listing";
        
        } else if (navOption.equals("Sales Order")) {
           // session.setAttribute("quotation", null);

            ret = "/accounting/payable/salesorder/customer-listing";
        } else if (navOption.equals("Delivery Note")) {
           // session.setAttribute("quotation", null);

            ret = "/accounting/payable/deliverynote/supplier-listing";
        } else if (navOption.equals("Remittance Advice")) {
           // session.setAttribute("quotation", null);

            ret = "/accounting/payable/remittanceadvice/new-remittance";
        }

        return ret;
    }
    
    public List<PurchaseOrderOld> getPurchaseOrderOldRequisition(String requisitionId) throws SQLException {
        
        purchaseOrders = new ArrayList<PurchaseOrderOld>();
        
        results = rp.getRequisitionItems(requisitionId);

        while(results.next()) {
            purchaseOrders.add(new PurchaseOrderOld(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }

        /*FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.name = params.get("name");
        this.uk = params.get("uk");
        this.recnum = params.get("recnum");

        requestorName = name;
        */
        return purchaseOrders;
    }

    public List<PurchaseOrderOld> getPurchaseOrderOlds() {
        return purchaseOrders;
    }

    public void setSelectedPurchaseOrderOlds(List<PurchaseOrderOld> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }    
    
    public void findInvoicePurchaseOrderOld(Date dateFrom, Date dateTo) throws SQLException {

        filteredPurchaseOrderOlds = rp.findInvoicePurchaseOrder(dateFrom, dateTo);

    }
    

}
