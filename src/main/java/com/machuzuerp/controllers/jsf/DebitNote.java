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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class DebitNote implements Serializable {

    /**
     * Creates a new instance of DebitNote
     */
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
    private Date dnDate;
    private String sId;
    private String invId;
    private String sName;
    private String cSurname;
    private String tel;
    private String cell;
    private String fax;
    private String email;
    private String posAdd;
    private String physAdd;   
    private String vatNum;
    private BigDecimal amount;
    private String total;
    private String outstanding;
    private String notes;
    
    //invoices
    private String iRecNum;
    private String orderNum;
    private String iCheck;
    private String iDate;
   // private String sName;
    //private String cSurname;
    //private String tel;
    //private String cell;
    //private String fax;
    //private String email;
   // private String posAdd;
   // private String physAdd;
    //private String country;
    //private String recurring;
    //private String sId;
    //private String vatNum;
    //private String notes;

    private Date dFrom;
    private Date dTo;    

    private String to;

    private List<DebitNote> debitNotes = new ArrayList<DebitNote>();
    private DebitNote selectedDebitNote;
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

    public DebitNote() {                                              
        
    }
       
    public DebitNote(String recnum, Date dnDate, String invId, String sId, String sName, String tel, String cell, String fax, String email, String posAdd, String physAdd, String vatNum, String total, String notes, BigDecimal amount) {
          
            this.recnum = recnum;
            this.dnDate = dnDate;
            this.invId = invId;
            this.sId = sId;
            this.sName = sName;
            this.tel = tel;
            this.cell = cell;
            this.fax = fax;
            this.email = email;
            this.posAdd = posAdd;
            this.physAdd = physAdd;            
            this.vatNum = vatNum;
            this.notes = notes;
            this.total = total;
            this.amount = amount;            
            
            this.dFrom = null;
            this.dTo = null;            
    }  
    
    public Date getDate() {
        return dnDate;
    }

    public void setDate(Date dnDate) {
        this.dnDate = dnDate;
    }
    
    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
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
    
    public String getSid() {
        return sId;
    }

    public void setSid(String sId) {
        this.sId = sId;
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
    
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<DebitNote> getDebitNotes() {
        return debitNotes;
    }
    
    public void setSelectedDebitNote(List<DebitNote> debitNotes) {
        this.debitNotes = debitNotes;
    }
    
    public DebitNote getSelectedDebitNote() {
        return selectedDebitNote;
    }

    public void setSelectedDebitNote(DebitNote selectedDebitNote) {
        this.selectedDebitNote = selectedDebitNote;
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

    public void debitNoteComplete() throws IOException, SQLException, ParseException {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setCharacterEncoding("UTF-8");

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

            this.invId = params.get("poId");
            this.sId = params.get("sid");
            this.vatNum = params.get("vatNum");
            this.sName = params.get("SName");
            this.tel = params.get("tel");
            this.cell = params.get("cell");
            this.fax = params.get("fax");
            this.email = params.get("email");
            this.posAdd = params.get("posAdd");
            this.physAdd = params.get("physAdd");                        
                        

           {

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

                String maxRec = ip.saveDebitNote(invId, sId, request.getSession().getAttribute("username").toString(), tel, cell, fax, posAdd, physAdd, vatNum, dnDate, amount, notes, request.getSession().getAttribute("uk").toString());
                recnum = maxRec;                     

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Debit Note", "Saved Successfully. "));
            
            }
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debit Note", "Not Saved. " + e));
        }

    }

    public void debitNoteEditComplete() throws IOException, SQLException {

        try {   
            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
           
            /*this.sId = params.get("sId");
            this.invId = params.get("invId");
            this.sName = params.get("sName");
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
            
            //if (amount > BigDecimal.parseBigDecimal(outstanding)) {
              //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debit Note", "Debit Note Value Cannot Be Greater Than Purchase Invoice Total. "));
            //} else {

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
                
                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

                    String maxRec = ip.saveEditDebitNote(recnum, invId, sId, request.getSession().getAttribute("username").toString(), tel, cell, fax, posAdd, physAdd, vatNum, dnDate, amount, notes, request.getSession().getAttribute("uk").toString());
                    recnum = maxRec;                     

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Debit Note", "Saved Successfully. "));
                    
                    url = request.getRequestURL().toString();
                    uri = request.getRequestURI();

                    endIndex = uri.lastIndexOf("/");

                    if (endIndex != -1) {
                        newstr = uri.substring(0, endIndex);
                        FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/debit-note-complete.xhtml");
                    }
                //}
            
       //     }
        } catch (IOException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debit Note", "Not Saved. " + e));
        }

    }

    public String searchInvoiceDebitNote(Date dFrom, Date dTo) {

        debitNotes.clear();

        try {

            connection = Systems.initConnection();

            int i = 0;
            query = "SELECT * FROM invoices where invoiceDate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {     
//                debitNotes.add(new DebitNote(results.getString(1), results.getDate(4), results.getString(15), results.getString(1), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(18), results.getString(19), results.getString(20),results.getString(14), results.getBigDecimal(15)));                
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/receivable/creditnote/creditnote-listing";

    }
    
    public String searchDebitNote(Date dFrom, Date dTo) {

        debitNotes.clear();

        try {

            connection = Systems.initConnection();

            query = "SELECT * FROM debitnotes where notedate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {     
                debitNotes.add(new DebitNote(results.getString(1), results.getDate(11), results.getString(3), results.getString(2), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(12), results.getString(12), results.getString(13), results.getBigDecimal(12)));
            }
            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/accounting/payable/debitnote/debitnote-listing";
    }

    public void findInvoice(Date from, Date to) throws SQLException {        
        filteredInvoices = ip.findInvoice(from, to);        
    }
    
    public List<InvoiceData> getInvoices() throws SQLException {
        return filteredInvoices;
    }

    public void onRowSelect(SelectEvent event) throws IOException, SQLException {

        this.invId = ((InvoiceData) event.getObject()).getRecNum();
        this.sId = ((InvoiceData) event.getObject()).getCustId();
        this.sName = ((InvoiceData) event.getObject()).getCName();
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
                
        if (((InvoiceData) event.getObject()).getCSurname().equals("null")) {
            this.cSurname = "";
        } else {
            this.cSurname = ((InvoiceData) event.getObject()).getCSurname();
        }

        if (((InvoiceData) event.getObject()).getTel().equals("null")) {
            this.tel = "";
        } else {
            this.tel = ((InvoiceData) event.getObject()).getTel();
        }

        if (((InvoiceData) event.getObject()).getCell().equals("null")) {
            this.cell = "";
        } else {
            this.cell = ((InvoiceData) event.getObject()).getCell();
        }

        if (((InvoiceData) event.getObject()).getFax().equals("null")) {
            this.fax = "";
        } else {
            this.fax = ((InvoiceData) event.getObject()).getFax();
        }

        try {
            this.sName = URLEncoder.encode(this.sName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DebitNote.class.getName()).log(Level.SEVERE, null, ex);
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String param = "uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + invId + "&iDate=" + dnDate + "&sName=" + sName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&sId=" + sId + "&notes=" + notes;

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

        if (up.orgPosAdd.equals("")) {
            orgPosAdd = "-";
        } else {
            orgPosAdd = up.orgPosAdd;
        }

        if (up.orgPhysAdd.equals("")) {
            orgPhysAdd = "-";
        } else {
            orgPhysAdd = up.orgPhysAdd;
        }

        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String mod = params.get("CR");

        if (endIndex != -1) {
//            newstr = uri.substring(0, endIndex);
  //          FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/new-credit-note.xhtml?" + param);
        }

    }
    
    public void onRowSelectMain(SelectEvent event) throws IOException, SQLException {

        this.recnum = ((DebitNote) event.getObject()).getRecNum();
        this.invId = ((DebitNote) event.getObject()).getInvId();
        this.dnDate = ((DebitNote) event.getObject()).dnDate;
        this.sId = ((DebitNote) event.getObject()).sId;
        this.sName = ((DebitNote) event.getObject()).sName;
        this.cSurname = ((DebitNote) event.getObject()).cSurname;
        this.tel = ((DebitNote) event.getObject()).tel;
        this.cell = ((DebitNote) event.getObject()).cell;
        this.fax = ((DebitNote) event.getObject()).fax;
        this.email = ((DebitNote) event.getObject()).email;
        this.posAdd = ((DebitNote) event.getObject()).posAdd;        
        this.physAdd = ((DebitNote) event.getObject()).physAdd;
        this.vatNum = ((DebitNote) event.getObject()).vatNum;
        this.notes = ((DebitNote) event.getObject()).notes;
        this.total = ((DebitNote) event.getObject()).total;
        this.amount = ((DebitNote) event.getObject()).amount;
        this.outstanding = ((DebitNote) event.getObject()).outstanding;

        try {
            this.sName = URLEncoder.encode(this.sName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DebitNote.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String param = "uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + recnum + "&invId=" + invId + "&dnDate=" + dnDate + "&sName=" + sName + "&cSurname=" + cSurname + "&tel=" + tel + "&cell=" + cell + "&fax=" + fax + "&email=" + email + "&posAdd=" + posAdd + "&physAdd=" + physAdd + "&vatNum=" + vatNum + "&notes=" + notes + "&total=" + total + "&outstanding=" + outstanding + "&amount=" + amount;

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
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-debitnote.xhtml?" + param);
        }

    }

}