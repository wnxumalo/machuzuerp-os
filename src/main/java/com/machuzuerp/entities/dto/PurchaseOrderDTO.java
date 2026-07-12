package com.machuzuerp.entities.dto;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;

public class PurchaseOrderDTO {

    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date poDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date deliveryDate;
    private String shippingMethod;
    private String shippingTerms;
    private String paymentTerms;   
    
    private long deliverToId;        
    private String deliverToName;        
    
    private long requestorId;
    private String requestorName;        
    
    private long accountsManagerId;
    private String accountsManagerName;        
    
    private long approverId;
    private String approverName;        

    private Long supplierId;
    private String supplierName;        

    private Long inventoryId;
    private String inventoryDescription;        
    
    private Long requisitionId;    

    private BigDecimal total = new BigDecimal("0");
    private BigDecimal subTotal = new BigDecimal("0");
    private BigDecimal shippingCharges = new BigDecimal("0");
    private BigDecimal taxAmount = new BigDecimal("0");
    private BigDecimal totalAmount = new BigDecimal("0");

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    private String status;
    private String notes;
    
    private Date requestDate;
    private String itemNo;
    private String itemService;
    private int quantity;
    private int unit;
    private String estUnitPrice;
    private String estAmount;

    public PurchaseOrderDTO(Long id, Date poDate, Date deliveryDate, String shippingMethod, String shippingTerms, String paymentTerms, Long deliverToId, String deliverToName, long requestorId, String requestorName, long accountsManagerId, 
            String accountsManagerName, Long approverId, String approverName, Long supplierId, String supplierName, Long inventoryId, String inventoryDescription, Long requisitionId, 
            String requisitionName, BigDecimal total, BigDecimal subTotal, BigDecimal shippingCharges, BigDecimal taxAmount, BigDecimal totalAmount) {
        this.id = id;
        this.poDate = poDate;
        this.deliveryDate = deliveryDate;
        this.shippingMethod = shippingMethod;
        this.shippingTerms = shippingTerms;
        this.paymentTerms = paymentTerms;
        this.deliverToId = deliverToId;
        this.deliverToName = deliverToName;
        this.requestorId = requestorId;
        this.requestorName = requestorName;
        this.accountsManagerId = accountsManagerId;
        this.accountsManagerName = accountsManagerName;
        this.approverId = approverId;
        this.approverName = approverName;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.inventoryId = inventoryId;
        this.inventoryDescription = inventoryDescription;
        this.requisitionId = requisitionId;
        this.total = total;
        this.subTotal = subTotal;
        this.shippingCharges = shippingCharges;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        
    }
    
    public PurchaseOrderDTO(String itemNo, String itemService, int quantity, String estUnitPrice, String estAmount) {

        this.itemNo = itemNo;
        this.itemService = itemService;
        this.quantity = quantity;
        this.unit = unit;
        this.estUnitPrice = estUnitPrice;
        this.estAmount = estAmount;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPODate() {
        return poDate;
    }

    public void setPODate(Date poDate) {
        this.poDate = poDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public String getShippingTerms() {
        return shippingTerms;
    }

    public void setShippingTerms(String shippingTerms) {
        this.shippingTerms = shippingTerms;
    }
    
    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }
 
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public String getSupplierName() {
        return supplierName;
    }
 
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    public Long getDeliverToId() {
        return deliverToId;
    }
 
    public void setDeliverToId(Long deliverToId) {
        this.deliverToId = deliverToId;
    }
    
    public String getDeliverToName() {
        return deliverToName;
    }
 
    public void setDeliverToName(String deliverToName) {
        this.deliverToName = deliverToName;
    }        
    
    public Long getRequestorId() {
        return requestorId;
    }
 
    public void setRequestorId(Long requestorId) {
        this.requestorId = requestorId;
    }
    
    public String getRequestorName() {
        return requestorName;
    }
 
    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }
    
    public Long getAccountsManagerId() {
        return accountsManagerId;
    }
 
    public void setAccountsManagerId(Long accountsManagerId) {
        this.accountsManagerId = accountsManagerId;
    }
    
    public String getAccountsManagerName() {
        return accountsManagerName;
    }
 
    public void setAccountsManagerName(String accountsManagerName) {
        this.accountsManagerName = accountsManagerName;
    }
    
    public Long getApproverId() {
        return approverId;
    }
 
    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }
    
    public String getApproverName() {
        return approverName;
    }
 
    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }
            
    public Long getInventoryId() {
        return inventoryId;
    }
 
    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    public String getInventoryDescription() {
        return inventoryDescription;
    }
 
    public void setInventoryDescription(String inventoryDescription) {
        this.inventoryDescription = inventoryDescription;
    }
    
    public Long getRequisitionId() {
        return requisitionId;
    }
 
    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }
        
    public BigDecimal getTotal() {
        return total;
    }
 
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public BigDecimal getSubTotal() {
        return subTotal;
    }
 
    public void setubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
    public BigDecimal getShippingCharges() {
        return shippingCharges;
    }
 
    public void setShippingCharges(BigDecimal shippingCharges) {
        this.shippingCharges = shippingCharges;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
 
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public BigDecimal getProductsServices() {
        return totalAmount;
    }
 
    public void setProductsServices(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
 
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
 
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
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

}