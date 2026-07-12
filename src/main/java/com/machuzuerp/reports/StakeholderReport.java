
package com.machuzuerp.reports;

import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.jsf.Systems;
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
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class StakeholderReport implements Serializable {
  
    Connection connection;
    Statement statement;

    private int recNum;
    private String cType;
    private String cStatus;
    protected String cName;
    protected String cSurname;
    private String cGender;
    private String cTelephone;
    private String cCellphone;
    private String email;
    private String cFax;
    private String cPostalAddress;
    private String cPhysicalAddress;
    private String cCountry;
    private String cJDate;
    private String vatNum;

    private String byName;
    private String bySurname;
    private String byVatNum;
    private String byCellphone;  

    protected List<StakeholderReport> stakeholders = new ArrayList<StakeholderReport>();
    private List<StakeholderReport> allCustomers = new ArrayList<StakeholderReport>();
    private List<StakeholderReport> filteredCustomers = new ArrayList<StakeholderReport>();
    private StakeholderReport selectedCustomer;

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";
    
    public StakeholderReport() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public StakeholderReport(int recNum, String vatNum, String cType, String cStat, String cName, String cSurname, String cGender, String cTelephone, String cCellphone, String email, String cFax, String cPosAddress, String cPhysAddress, String cCountry, String cJoinDate) {

        this.recNum = recNum;
        this.vatNum = vatNum;
        this.cType = cType;
        this.cStatus = cStat;
        this.cName = cName;
        this.cSurname = cSurname;
        this.cGender = cGender;
        this.cTelephone = cTelephone;
        this.cCellphone = cCellphone;
        this.email = email;
        this.cFax = cFax;
        this.cPostalAddress = cPosAddress;
        this.cPhysicalAddress = cPhysAddress;
        this.cCountry = cCountry;
        this.cJDate = cJoinDate;
    }
    
    public void getReportGeneral(String type, String status) throws SQLException {

        stakeholders.clear();
        try {

            connection = Systems.initConnection();

            if ((!type.equals("")) & (status.equals("")) ){
                query = "SELECT * FROM clients where clienttype = '" + type + "' order by name, surname";
            } else if ((type.equals("")) & (!status.equals("")) ){
                query = "SELECT * FROM clients where clientstatus = '" + status + "' order by name, surname";                
            } else {
                query = "SELECT * FROM clients where clientstatus = '" + status + "' and clienttype = '" + type + "' order by name, surname";                
            }
            statement = connection.createStatement();
            ResultSet cfs = statement.executeQuery(query);

            while (cfs.next()) {                   
                stakeholders.add(new StakeholderReport(cfs.getInt(1), cfs.getString(17), cfs.getString(3), cfs.getString(4), cfs.getString(5), cfs.getString(6), cfs.getString(7), cfs.getString(8), cfs.getString(9), cfs.getString(18), cfs.getString(10), cfs.getString(11), cfs.getString(12), cfs.getString(13), cfs.getString(14)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

    }

    public List<StakeholderReport> getAllStakeholders() {
        return stakeholders;
    }

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    public String getBySurname() {
        return bySurname;
    }

    public void setBySurname(String bySurname) {
        this.bySurname = bySurname;
    }
    
    public String getByVatNum() {
        return byVatNum;
    }

    public void setByVatNum(String byVatNum) {
        this.byVatNum = byVatNum;
    }
    
    public String getByCellphone() {
        return byCellphone;
    }

    public void setByCellphone(String byCellphone) {
        this.byCellphone = byCellphone;
    }

    public int getRecordNum() {
        return recNum;
    }

    public void setRecordNum(int recNum) {
        this.recNum = recNum;
    }

    public String getType() {
        return cType;
    }

    public void setType(String cType) {
        this.cType = cType;
    }

    public void setStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getStatus() {
        return cStatus;
    }

    public void setCustomerName(String cName) {
        this.cName = cName;
    }

    public String getCustomerName() {
        return cName;
    }

    public void setSurname(String cSurname) {
        this.cSurname = cSurname;
    }

    public String getSurname() {
        return cSurname;
    }

    public void setGender(String cGender) {
        this.cGender = cGender;
    }

    public String getGender() {
        return cGender;
    }

    public void setTelephone(String cTelephone) {
        this.cTelephone = cTelephone;
    }

    public String getTelephone() {
        return cTelephone;
    }

    public void setCellphone(String cCellphone) {
        this.cCellphone = cCellphone;
    }

    public String getCellphone() {
        return cCellphone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFax(String cFax) {
        this.cFax = cFax;
    }

    public String getFax() {
        return cFax;
    }

    public void setPostalAddress(String cPostalAddress) {
        this.cPostalAddress = cPostalAddress;
    }

    public String getPostalAddress() {
        return cPostalAddress;
    }

    public void setPhysicalAddress(String cPhysicalAddress) {
        this.cPhysicalAddress = cPhysicalAddress;
    }

    public String getPhysicalAddress() {
        return cPhysicalAddress;
    }

    public void setCountry(String cCountry) {
        this.cCountry = cCountry;
    }

    public String getCountry() {
        return cCountry;
    }

    public void setJDate(String cJDate) {
        this.cJDate = cJDate;
    }

    public String getJDate() {
        return cJDate;
    }
    
    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }

    public StakeholderReport getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(StakeholderReport selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.recNum = ((StakeholderReport) event.getObject()).recNum;
        this.vatNum = ((StakeholderReport) event.getObject()).vatNum;
        this.cType = ((StakeholderReport) event.getObject()).cType;
        this.cStatus = ((StakeholderReport) event.getObject()).cStatus;
        this.cName = ((StakeholderReport) event.getObject()).cName;
        this.cSurname = ((StakeholderReport) event.getObject()).cSurname;
        this.cGender = ((StakeholderReport) event.getObject()).cGender;
        this.cTelephone = ((StakeholderReport) event.getObject()).cTelephone;
        this.cCellphone = ((StakeholderReport) event.getObject()).cCellphone;
        this.email = ((StakeholderReport) event.getObject()).email;
        this.cFax = ((StakeholderReport) event.getObject()).cFax;
        this.cPostalAddress = ((StakeholderReport) event.getObject()).cPostalAddress;
        this.cPhysicalAddress = ((StakeholderReport) event.getObject()).cPhysicalAddress;
        this.cCountry = ((StakeholderReport) event.getObject()).cCountry;
        this.cJDate = ((StakeholderReport) event.getObject()).cJDate;


        String param = "name=" + request.getSession().getAttribute("username").toString() + "&uk=" + request.getSession().getAttribute("uk").toString() + "&recnum=" + this.recNum + "&type=" + cType + "&status=" + cStatus + "&cname=" + cName + "&cSurname=" + cSurname + "&gender=" + cGender + "&telephone=" + cTelephone + "&cellphone=" + cCellphone + "&fax=" + cFax + "&email=" + email + "&posaddress=" + cPostalAddress + "&physaddress=" + cPhysicalAddress + "&country=" + cCountry + "&jdate=" + cJDate + "&vatNum=" + vatNum;
        
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-customer.xhtml?" + param);
        }                

    }

    public void viewCustomer() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/stakeholders/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(StakeholderReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}