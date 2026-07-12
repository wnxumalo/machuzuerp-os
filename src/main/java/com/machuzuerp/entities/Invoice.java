package com.machuzuerp.entities;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;

@Entity
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVOICE_ID")
    Long id;
    
    private String invoiceCheck;
    private Date invoiceDate;
    private String recurring;
    private File iFile;
    private String orderNum;
    private String vatnum;
    private String total;
    private String outstanding;
    private String notes;
    private String quoteId;
    private String projectId;   
  
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    private String status;
    
   /*
  `Name` varchar(150) DEFAULT NULL,
  `Surname` varchar(20) DEFAULT NULL,
  `Telephone` varchar(20) DEFAULT NULL,
  `Cellphone` varchar(20) DEFAULT NULL,
  `Fax` varchar(20) DEFAULT NULL,
  `PostalAddress` varchar(100) DEFAULT NULL,
  `PhysicalAddress` varchar(100) DEFAULT NULL,
  `notes` varchar(30) DEFAULT NULL,*/

    @JoinColumn(name="STAKEHOLDER_ID", nullable=false)
    private Long stakeholderId;
    
    @JoinColumn(name="ORGANISATION_ID", nullable=false)
    private Long organisationId;
    
    public Invoice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getInvoiceCheck() {
        return invoiceCheck;
    }

    public void setInvoiceCheck(String invoiceCheck) {
        this.invoiceCheck = invoiceCheck;
    }        

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }
    
    public File getIFile() {
        return iFile;
    }

    public void setIFile(File iFile) {
        this.iFile = iFile;
    }
    
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    
    public String getVatNum() {
        return vatnum;
    }

    public void setVatNum(String vatnum) {
        this.vatnum = vatnum;
    }
        
    public String getTotal() {
        return total;
    }
 
    public void setTotal(String total) {
        this.total = total;
    }
    
    public String getOutstanding() {
        return outstanding;
    }
 
    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }
    
    public String getQuoteId() {
        return quoteId;
    }
 
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }
    
    public String getProjectId() {
        return projectId;
    }
 
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }    
    
    public String getNotes() {
        return notes;
    }
 
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.Invoice[ id=" + id + " ]";
    }
    
}