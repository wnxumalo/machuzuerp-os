/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class BenefitsData implements Serializable {

    /**
     * Creates a new instance of BenefitsData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String recNum;
    private String employeeNumber = "";   
    private String description = "";
    private double amount = 0;    

    private List<BenefitsData> benefits = new ArrayList<BenefitsData>();
    private List<BenefitsData> allBenefits = new ArrayList<BenefitsData>();
    private List<BenefitsData> funcBenefits = new ArrayList<BenefitsData>();
    private List<BenefitsData> filteredBenefits = new ArrayList<BenefitsData>();
    private BenefitsData selectedBenefit;            
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query;

    public BenefitsData() {        
    }
  
    public BenefitsData(String recNum, String employeeNumber, String description, double amount) {

        this.recNum = recNum;
        this.employeeNumber = employeeNumber;
        this.description = description;
        this.amount = amount;
        
    }

    public String searchBenefit() {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        benefits.clear();
        try {

            connection = Systems.initConnection();

            if (!employeeNumber.equals("")) {
                query = "SELECT * FROM benefits where EmployeeNumber = '" + employeeNumber + "'";                
            } else if (!description.equals("")) {
                query = "SELECT * FROM benefits where uuid = '" + request.getSession().getAttribute("uk").toString() + "' and empname like '%" + description + "%'";
            }

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                benefits.add(new BenefitsData(results.getString(1), results.getString(2), results.getString(3), results.getDouble(4)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/hr/benefits/employee-listing";

    }
    
    public String getBenefit(String recnum) {

        try {
            
            connection = Systems.initConnection();

            int i = 0;               
            query = "SELECT * FROM benefits where recnum = '" + recnum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                //empDat = results.getString(1) + ',' + results.getString(3) + ',' + results.getString(4) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(7) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(11);
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "";//empDat;

    }

    public List<BenefitsData> getAllBenefits() {

        allBenefits.clear();
        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM benefits order by name, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                //allBenefits.add(new BenefitsData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14), Float.parseFloat(results.getString(15)), results.getString(16), results.getString(17)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return allBenefits;
    }

    /*public String filterBenefits() {

        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());       

        try {

            String query = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();  
            
            boolean done = false;
            
            try {
                byName.equals("");
                bySurname.equals("");
                
                if (byVatNum.equals("")) {
                    throw new NullPointerException();
                }

                done = true; 
                System.out.println("ONE:"+byVatNum);
                query = "SELECT * FROM clients where name like '%" + byName + "%' and surname like '%" + bySurname + "%'  and vatnum like '%" + byVatNum + "%' order by surname, name";            
            } catch (NullPointerException npe5) {
                
                try {
                    byName.equals("");
                    bySurname.equals(""); 
                    
                    if (bySurname.equals("")) {
                        throw new NullPointerException();
                    }

                    if (done == false) {
                        done = true;
    
                        query = "SELECT * FROM clients where name like '%" + byName + "%' and surname like '%" + bySurname + "%' order by surname, name";            
                    }
                } catch (Exception npe2) {
                
                    try {
                        byName.equals("");

                        if (byName.equals("")) {
                            throw new NullPointerException();
                        }

                        if (done == false) {
                            done = true;

                            query = "SELECT * FROM clients where name like '%" + byName + "%' order by surname, name";            
                        }
                     } catch (Exception npe3) {}
                
                }
            
            
            }                                                                    

            filteredCustomers.clear();

            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            int count = 0;
            proceed = true;
            while (results.next()) {                
                proceed = false;
                clientRecNum = results.getString(1);              
                filteredCustomers.add(new CustomerData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(18), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14)));                
                //setPicPath(clientRecNum);
                count++;
            }

           // connection.close();
            this.count = count;
            
           

        } catch (Exception sqle) {
            System.out.println(sqle);
        }               
        
                
        return "/customers/customer-detailed-list.xhtml";

    }*/
    
    public List<BenefitsData> getFilteredServs() {
        return filteredBenefits;
    }
 
   /* public void setFilteredServs(List<BenefitsData> filteredBenefits) {
        this.filteredBenefits = filteredBenefits;
    }

    public List<BenefitsData> getFilteredServices() {

        filterServices();

        return filteredBenefits;
    }*/

    public List<BenefitsData> getBenefits() {
        return benefits;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getRecNum() {
        return recNum;
    }
  
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }   
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }              
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }                                        
    
    public BenefitsData getSelectedBenefit() {
        return selectedBenefit;
    }

    public void setSelectedBenefit(BenefitsData selectedEmp) {
        this.selectedBenefit = selectedEmp;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

      /*  this.recNum = ((BenefitsData) event.getObject()).recNum;
        this.idNumber = ((BenefitsData) event.getObject()).idNumber;
        this.taxNumber = ((BenefitsData) event.getObject()).taxNumber;
        this.employeeNumber = ((BenefitsData) event.getObject()).employeeNumber;
        this.employmentDate = ((BenefitsData) event.getObject()).employmentDate;      
        this.empName = ((BenefitsData) event.getObject()).empName;
        this.surname = ((BenefitsData) event.getObject()).surname;    
        this.gender = ((BenefitsData) event.getObject()).gender;
        this.telephone = ((BenefitsData) event.getObject()).telephone;       
        this.cellphone = ((BenefitsData) event.getObject()).cellphone;
        this.email = ((BenefitsData) event.getObject()).email;
        this.postalAddress = ((BenefitsData) event.getObject()).postalAddress;
        this.physicalAddress = ((BenefitsData) event.getObject()).physicalAddress;
        this.salary = ((BenefitsData) event.getObject()).salary;
        this.salaryInterval = ((BenefitsData) event.getObject()).salaryInterval;
        this.status = ((BenefitsData) event.getObject()).status;
System.out.println("ID: "+idNumber);
        try {
            this.name = URLEncoder.encode(this.name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BenefitsData.class.getName()).log(Level.SEVERE, null, ex);
        }        

        String param = "uk=" + uk + "&name=" + name + "&recnum=" + recNum + "&idnumber=" + idNumber + "&taxnumber=" + taxNumber + "&employeenumber=" + employeeNumber + "&employmentdate=" + this.employmentDate + "&empname=" + empName + "&surname=" + surname + "&gender=" + gender + "&telephone=" + telephone + "&cellphone=" + cellphone + "&email=" + email + "&postaladdress=" + postalAddress + "&physicaladdress=" + physicalAddress + "&salary=" + salary + "&salaryinterval=" + salaryInterval + "&status=" + status;

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-employee.xhtml?" + param);
        }        */   

    }
    
    public void onCellEdit(CellEditEvent event) {
        System.out.println("SERVS11");
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println("SERVS:"+""+oldValue + ", New:" + newValue);
        }                         

    }

    public void viewService() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/services/view-service.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/services/view-service.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(BenefitsData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
