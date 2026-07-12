/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class TrainingsData implements Serializable {

    /**
     * Creates a new instance of TrainingsData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String recNum;
    private String employeeNumber;
    private Date startDate;
    private Date endDate;
    private String description;
    private String outputs;
    private String status;
    private String comments;   

    private List<TrainingsData> trainings = new ArrayList<TrainingsData>();
    private List<TrainingsData> allTrainings = new ArrayList<TrainingsData>();
    private List<TrainingsData> funcTrainings = new ArrayList<TrainingsData>();
    private List<TrainingsData> filteredTrainings = new ArrayList<TrainingsData>();
    private TrainingsData selectedTraining;            
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query;

    public TrainingsData() {
    }      
    
    public TrainingsData(String recNum, String employeeNumber, Date startDate, Date endDate, String description, String outputs, String status, String comments) {        
    
        this.recNum = recNum;
        this.employeeNumber = employeeNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.outputs = outputs;
        this.status = status;
        this.comments = comments;
        
    }

    public String searchTraining() {

        trainings.clear();
        try {
            
            connection = Systems.initConnection();
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            int i = 0;
            if (!employeeNumber.equals("")) {
                query = "SELECT * FROM employeetrainings where EmployeeRecNumber = '" + employeeNumber + "'";                
            } else if (!description.equals("")) {
                query = "SELECT * FROM employeetrainings where uuid = '" + request.getSession().getAttribute("uk").toString() + "' and empname like '%" + description + "%'";
            }

            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {         
                trainings.add(new TrainingsData(results.getString(1), results.getString(3), results.getDate(4), results.getDate(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9)));

            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "/hr/trainings/employee-listing";

    }
    
    public String getTraining(String recnum) {

        try {

            connection = Systems.initConnection();
    
            query = "SELECT * FROM employeetrainings where recnum = '" + recnum + "'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                //empDat = results.getString(1) + ',' + results.getString(3) + ',' + results.getString(4) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(7) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(11);
            }

            connection.close();


        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return "";

    }

    public List<TrainingsData> getAllTrainings() {

        allTrainings.clear();
        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM employeetrainings order by name, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {           
                //allTrainings.add(new TrainingsData(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(14), Float.parseFloat(results.getString(15)), results.getString(16), results.getString(17)));
            }

            connection.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return allTrainings;
    }

    /*public String filterTrainings() {

        getParams();       

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
    
    public List<TrainingsData> getFilteredServs() {
        return filteredTrainings;
    }
 
   /* public void setFilteredServs(List<TrainingsData> filteredTrainings) {
        this.filteredTrainings = filteredTrainings;
    }

    public List<TrainingsData> getFilteredServices() {

        filterServices();

        return filteredTrainings;
    }*/

    public List<TrainingsData> getTrainings() {
        return trainings;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getRecNum() {
        return recNum;
    }

    public String getEmployeeRecNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeRecNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }        
    
    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }       
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TrainingsData getSelectedTraining() {
        return selectedTraining;
    }

    public void setSelectedTraining(TrainingsData selectedEmp) {
        this.selectedTraining = selectedEmp;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

      /*  this.recNum = ((TrainingsData) event.getObject()).recNum;
        this.idNumber = ((TrainingsData) event.getObject()).idNumber;
        this.taxNumber = ((TrainingsData) event.getObject()).taxNumber;
        this.employeeNumber = ((TrainingsData) event.getObject()).employeeNumber;
        this.employmentDate = ((TrainingsData) event.getObject()).employmentDate;      
        this.empName = ((TrainingsData) event.getObject()).empName;
        this.surname = ((TrainingsData) event.getObject()).surname;    
        this.gender = ((TrainingsData) event.getObject()).gender;
        this.telephone = ((TrainingsData) event.getObject()).telephone;       
        this.cellphone = ((TrainingsData) event.getObject()).cellphone;
        this.email = ((TrainingsData) event.getObject()).email;
        this.postalAddress = ((TrainingsData) event.getObject()).postalAddress;
        this.physicalAddress = ((TrainingsData) event.getObject()).physicalAddress;
        this.salary = ((TrainingsData) event.getObject()).salary;
        this.salaryInterval = ((TrainingsData) event.getObject()).salaryInterval;
        this.status = ((TrainingsData) event.getObject()).status;
System.out.println("ID: "+idNumber);
        try {
            this.name = URLEncoder.encode(this.name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TrainingsData.class.getName()).log(Level.SEVERE, null, ex);
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
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }                         

    }

    public void viewService() {
        try {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/services/view-service.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/services/view-service.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(TrainingsData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
