/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.InvoiceData;
import com.machuzuerp.jdbc.InvoiceProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class CreditNote implements Serializable {

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

    private String recnum;   
    private Date cnDate;
    private String cid;
    private String invId;
    private String cName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String email;
    private String posAdd;
    private String physAdd;   
    private String vatNum;
    private Double amount;
    private String total;
    private String outstanding;
    private String notes;
    
    //invoices
    private String iRecNum;
    private String orderNum;
    private String iCheck;
    private String iDate;
   // private String cName;
    //private String cSurname;
    //private String tel;
    //private String cell;
    //private String fax;
    //private String email;
   // private String posAdd;
   // private String physAdd;
    //private String country;
    //private String recurring;
    //private String cid;
    //private String vatNum;
    //private String notes;

    private Date dFrom;
    private Date dTo;    

    private String to;

    private List<CreditNote> creditNotes = new ArrayList<CreditNote>();
    private CreditNote selectedCreditNote;
   private List<InvoiceData> filteredInvoices = new ArrayList<InvoiceData>();
    private InvoiceData selectedInvoice;
    
    InvoiceProcessing ip = new InvoiceProcessing();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";

    public CreditNote() {                                                      
    }
       
    public CreditNote(String recnum, Date cnDate, String cid, String invId, String cName, String cSurname, String tel, String cell, String fax, String email, String posAdd, String physAdd, String vatNum, String total, String outstanding, String notes, double amount) {
          
            this.recnum = recnum;
            this.cnDate = cnDate;
            this.cid = cid;
            this.invId = invId;
            this.cName = cName;
            this.cSurname = cSurname;
            this.tel = tel;
            this.cell = cell;
            this.fax = fax;
            this.email = email;
            this.posAdd = posAdd;
            this.physAdd = physAdd;            
            this.vatNum = vatNum;
            this.notes = notes;
            this.total = total;
            this.outstanding = outstanding;
            this.amount = amount;            
            
            this.dFrom = null;
            this.dTo = null;
            
        
    }  
    
    public Date getDate() {
        return cnDate;
    }

    public void setDate(Date cnDate) {
        this.cnDate = cnDate;
    }
    
    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCSurname() {
        return cSurname;
    }

    public void setCSurname(String cSurname) {
        this.cSurname = cSurname;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosAdd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPosAdd() {
        return posAdd;
    }

    public String getPhysAdd() {
        return physAdd;
    }

    public void setPhysAdd(String physAdd) {
        this.physAdd = physAdd;
    }
    
    public String getVatNumber() {
        return vatNum;
    }

    public void setVatNumber(String vatNum) {
        this.vatNum = vatNum;
    }
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    
    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
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
    
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<CreditNote> getCreditNotes() {
        return creditNotes;
    }
    
    public void setSelectedCreditNote(List<CreditNote> creditNotes) {
        this.creditNotes = creditNotes;
    }
    
    public CreditNote getSelectedCreditNote() {
        return selectedCreditNote;
    }

    public void setSelectedCreditNote(CreditNote selectedCreditNote) {
        this.selectedCreditNote = selectedCreditNote;
    }
    
    public String getRecNum() {
        return recnum;
    }

    public void setRecNum(String recnum) {
        this.recnum = recnum;
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

    public void creditNoteComplete() throws IOException, SQLException {

        try {   
         
            /*this.cid = params.get("cid");
            this.invId = params.get("invId");
            this.cName = params.get("cName");
            this.cSurname = params.get("cSurname");
            this.tel = params.get("tel");
            this.cell = params.get("cell");
            this.fax = params.get("fax");
            this.email = params.get("email");
            this.posAdd = params.get("posAdd");
            this.physAdd = params.get("physAdd");
            this.vatNum = params.get("vatNum");
            this.notes = params.get("notes");
            this.total = params.get("total");
            this.outstanding = params.get("outstanding");   
            */
            if (amount > Double.parseDouble(outstanding)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Credit Note Value Cannot Be Greater Than Outstanding. "));
            } else {

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
               /* amount = "0";
                outstanding = "0";
                cid = */

                if (amount > Double.parseDouble(outstanding)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Amount cannot be greater than Invoice Outstanding Amount."));
                } else {
                    
                    request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    
                    connection = Systems.initConnection(); 
                    String maxRec = ip.saveCreditNote(cnDate, cid, cName, cSurname, tel, cell, fax, posAdd, physAdd, request.getSession().getAttribute("uk").toString(), vatNum, notes, total, outstanding, invId, ""+amount);
                    recnum = maxRec; 

                    url = request.getRequestURL().toString();
                    uri = request.getRequestURI();

                    endIndex = uri.lastIndexOf("/");

                    if (endIndex != -1) {
                        newstr = uri.substring(0, endIndex);
                        FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/credit-note-complete.xhtml");
                    }
                }
            
            }
        } catch (IOException | NumberFormatException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Not Saved. " + e));
        }

    }

    public void creditNoteEditComplete() throws IOException, SQLException {

        try {   
           
            /*this.cid = params.get("cid");
            this.invId = params.get("invId");
            this.cName = params.get("cName");
            this.cSurname = params.get("cSurname");
            this.tel = params.get("tel");
            this.cell = params.get("cell");
            this.fax = params.get("fax");
            this.email = params.get("email");
            this.posAdd = params.get("posAdd");
            this.physAdd = params.get("physAdd");
            this.vatNum = params.get("vatNum");
            this.notes = params.get("notes");
            this.total = params.get("total");
            this.outstanding = params.get("outstanding");   */
            
            if (amount > Double.parseDouble(outstanding)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Credit Note Value Cannot Be Greater Than Invoice Total. "));
            } else {

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

                if (amount > Double.parseDouble(outstanding)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Amount cannot be greater than Invoice Outstanding Amount."));
                } else {
  
                    request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    
                    connection = Systems.initConnection();

                    String maxRec = ip.saveEditCreditNote(recnum, cnDate, cid, cName, cSurname, tel, cell, fax, posAdd, physAdd, request.getSession().getAttribute("uk").toString(), vatNum, notes, total, outstanding, invId, ""+amount);
                    recnum = maxRec; 

                    url = request.getRequestURL().toString();
                    uri = request.getRequestURI();

                    endIndex = uri.lastIndexOf("/");

                    if (endIndex != -1) {
                        newstr = uri.substring(0, endIndex);
                        FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/credit-note-complete.xhtml");
                    }
                }
            
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credit Note", "Not Saved. " + e));
        }
            
            
    }
    
    public String searchInvoiceCreditNote(Date dFrom, Date dTo) {

        creditNotes.clear();

        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM invoices where invoiceDate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {     
                creditNotes.add(new CreditNote(results.getString(1), results.getDate(4), results.getString(15), results.getString(1), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(18), results.getString(19), results.getString(20),results.getString(14), results.getDouble(15)));                
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/receivable/creditnote/creditnote-listing";

    }
    
    public String searchCreditNote(Date dFrom, Date dTo) {

        creditNotes.clear();

        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM creditnotes where notedate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {     
                creditNotes.add(new CreditNote(results.getString(1), results.getDate(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(13), results.getString(16), results.getString(17), results.getString(14), results.getDouble(15)));                
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/receivable/creditnote/creditnote-listing";

    }
    
    public void findInvoice(Date from, Date to) throws SQLException {        
        filteredInvoices = ip.findInvoice(from, to);        
    }
    
    public List<InvoiceData> getInvoices() throws SQLException {
        return filteredInvoices;
    }

    public void onRowSelect(SelectEvent event) throws IOException, SQLException {

        this.invId = ((InvoiceData) event.getObject()).getRecNum();
        this.cid = ((InvoiceData) event.getObject()).getCustId();
        this.cName = ((InvoiceData) event.getObject()).getCName();
        this.cSurname = ((InvoiceData) event.getObject()).getCSurname();
        this.tel = ((InvoiceData) event.getObject()).getTel();
        this.cell = ((InvoiceData) event.getObject()).getCell();
        this.fax = ((InvoiceData) event.getObject()).getFax();
        this.email = ((InvoiceData) event.getObject()).getFax();
        this.posAdd = ((InvoiceData) event.getObject()).getPosAdd();
        this.physAdd = ((InvoiceData) event.getObject()).getPhysAdd();
        this.vatNum = ((InvoiceData) event.getObject()).getVatNumber();        
        this.notes = ((InvoiceData) event.getObject()).getNotes();
        this.total = ""+((InvoiceData) event.getObject()).getTotCost();
        this.outstanding = ""+((InvoiceData) event.getObject()).getOutstanding();

        this.iRecNum = ((InvoiceData) event.getObject()).getRecNum();
        this.iCheck = ((InvoiceData) event.getObject()).getInvCheck();
        this.iDate = ((InvoiceData) event.getObject()).getIDate();
                
        try {
            this.cSurname = ((InvoiceData) event.getObject()).getCSurname();
        } catch (Exception e) {
            this.cSurname = "";
        }


        try {
            this.tel = ((InvoiceData) event.getObject()).getTel();
        } catch (Exception e) {
            this.tel = "";
        }

        try {
            this.cell = ((InvoiceData) event.getObject()).getCell();
        } catch (Exception e) {
            this.cell = "";
        }

        try {
            this.fax = ((InvoiceData) event.getObject()).getFax();
        } catch (Exception e) {
            this.fax = "";
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String param = "uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + invId + "&iDate=" + cnDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&cid=" + cid + "&notes=" + notes;

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
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/new-credit-note.xhtml");
        }

    }
    
    public void onRowSelectMain(SelectEvent event) throws IOException, SQLException {

        this.recnum = ((CreditNote) event.getObject()).getRecNum();
        this.invId = ((CreditNote) event.getObject()).getInvId();
        this.cnDate = ((CreditNote) event.getObject()).cnDate;
        this.cid = ((CreditNote) event.getObject()).cid;
        this.cName = ((CreditNote) event.getObject()).cName;
        this.cSurname = ((CreditNote) event.getObject()).cSurname;
        this.tel = ((CreditNote) event.getObject()).tel;
        this.cell = ((CreditNote) event.getObject()).cell;
        this.fax = ((CreditNote) event.getObject()).fax;
        this.email = ((CreditNote) event.getObject()).email;
        this.posAdd = ((CreditNote) event.getObject()).posAdd;        
        this.physAdd = ((CreditNote) event.getObject()).physAdd;
        this.vatNum = ((CreditNote) event.getObject()).vatNum;
        this.notes = ((CreditNote) event.getObject()).notes;
        this.total = ((CreditNote) event.getObject()).total;
        this.amount = ((CreditNote) event.getObject()).amount;
        this.outstanding = ((CreditNote) event.getObject()).outstanding;

        try {
            this.cName = URLEncoder.encode(this.cName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreditNote.class.getName()).log(Level.SEVERE, null, ex);
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String param = "recnum=" + recnum + "&invId=" + invId + "&cnDate=" + cnDate + "&cName=" + cName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&vatNum=" + vatNum + "&notes=" + notes + "&total=" + total + "&outstanding=" + outstanding + "&amount=" + amount;

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
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-creditnote.xhtml?" + param);
        }

    }

}