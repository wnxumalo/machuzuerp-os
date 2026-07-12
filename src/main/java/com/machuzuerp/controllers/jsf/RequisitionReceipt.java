package com.machuzuerp.controllers.jsf;

import java.util.Date;

public class RequisitionReceipt {

    private Integer id;
    private Integer requisitionId;
    private String viewurl;
    private String filename;
    private Date uploaddate;

    public RequisitionReceipt(Integer id, Integer requisitionId, String viewurl, String filename, Date uploaddate) {
        this.id = id;
        this.requisitionId = requisitionId;
        this.viewurl = viewurl;
        this.filename = filename;
        this.uploaddate = uploaddate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getViewFile() {
        return viewurl;
    }

    public void setViewFile(String viewurl) {
        this.viewurl = viewurl;
    }

    public Integer getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Integer requisitionId) {
        this.requisitionId = requisitionId;
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public Date getUploaddate() {
        return uploaddate;
    }
    
    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }
}