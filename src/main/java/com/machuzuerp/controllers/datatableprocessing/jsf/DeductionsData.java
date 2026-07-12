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
public class DeductionsData implements Serializable {

    /**
     * Creates a new instance of DeductionsData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String name;
    private String uk;

    private String recNum;
    private String employeeNumber = "";   
    private String description = "";
    private double amount = 0;    

    private List<DeductionsData> deductions = new ArrayList<DeductionsData>();
    private List<DeductionsData> allDeductions = new ArrayList<DeductionsData>();
    private List<DeductionsData> funcDeductions = new ArrayList<DeductionsData>();
    private List<DeductionsData> filteredDeductions = new ArrayList<DeductionsData>();
    private DeductionsData selectedDeduction;            
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query;

    public DeductionsData() {
    }
  
    public DeductionsData(String recNum, String employeeNumber, String description, double amount) {

        this.recNum = recNum;
        this.employeeNumber = employeeNumber;
        this.description = description;
        this.amount = amount;
        
    }

    public String searchDeduction() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        deductions.clear();
        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            int i = 0;
            if (!employeeNumber.equals("")) {
                query = "SELECT * FROM deductions where EmployeeNumber = '" + employeeNumber + "'";                
            } else if (!description.equals("")) {
                query = "SELECT * FROM deductions where uuid = '" + uk + "' and empname like '%" + description + "%'";
            }

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                clientRecNum = results.getString(1);
                deductions.add(new DeductionsData(results.getString(1), results.getString(2), results.getString(3), results.getDouble(4)));

            }

            connection.close();


        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/hr/deductions/employee-listing";

    }
    
    public String getDeduction(String recnum) {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        try {

            String query = "";
            String empRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            int i = 0;               
            query = "SELECT * FROM deductions where recnum = '" + recnum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                //empDat = results.getString(1) + ',' + results.getString(3) + ',' + results.getString(4) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(7) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(11);
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "";//empDat;

    }

    public List<DeductionsData> getAllDeductions() {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());
        allDeductions.clear();
        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            query = "SELECT * FROM deductions order by name, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            while (results.next()) {
                proceed = false;
                clientRecNum = results.getString(1);
                //allDeductions.add(new DeductionsData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14), Float.parseFloat(results.getString(15)), results.getString(16), results.getString(17)));
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return allDeductions;
    }

    /*public String filterDeductions() {
    
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
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
    
    public List<DeductionsData> getFilteredServs() {
        return filteredDeductions;
    }
 
   /* public void setFilteredServs(List<DeductionsData> filteredDeductions) {
        this.filteredDeductions = filteredDeductions;
    }

    public List<DeductionsData> getFilteredServices() {

        filterServices();

        return filteredDeductions;
    }*/

    public List<DeductionsData> getDeductions() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

        return deductions;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getRecNum() {
        return recNum;
    }
    
    public String getUk() {
        return uk;
    }
    
    public void setUk(String uk) {
        this.uk = uk;
    } 
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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

    public void getParams(String username, String uk) {       
        this.name = username;
        this.uk = uk;
    }

    public DeductionsData getSelectedDeduction() {
        return selectedDeduction;
    }

    public void setSelectedDeduction(DeductionsData selectedEmp) {
        this.selectedDeduction = selectedEmp;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();       
        getParams(request.getSession().getAttribute("username").toString(), request.getSession().getAttribute("uk").toString());

      /*  this.recNum = ((DeductionsData) event.getObject()).recNum;
        this.idNumber = ((DeductionsData) event.getObject()).idNumber;
        this.taxNumber = ((DeductionsData) event.getObject()).taxNumber;
        this.employeeNumber = ((DeductionsData) event.getObject()).employeeNumber;
        this.employmentDate = ((DeductionsData) event.getObject()).employmentDate;      
        this.empName = ((DeductionsData) event.getObject()).empName;
        this.surname = ((DeductionsData) event.getObject()).surname;    
        this.gender = ((DeductionsData) event.getObject()).gender;
        this.telephone = ((DeductionsData) event.getObject()).telephone;       
        this.cellphone = ((DeductionsData) event.getObject()).cellphone;
        this.email = ((DeductionsData) event.getObject()).email;
        this.postalAddress = ((DeductionsData) event.getObject()).postalAddress;
        this.physicalAddress = ((DeductionsData) event.getObject()).physicalAddress;
        this.salary = ((DeductionsData) event.getObject()).salary;
        this.salaryInterval = ((DeductionsData) event.getObject()).salaryInterval;
        this.status = ((DeductionsData) event.getObject()).status;
System.out.println("ID: "+idNumber);
        try {
            this.name = URLEncoder.encode(this.name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DeductionsData.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DeductionsData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
