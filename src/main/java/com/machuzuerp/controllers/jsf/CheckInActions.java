/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.CheckInProcessing;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;

@SessionScoped
@Named
public class CheckInActions implements Serializable {

    Connection connection;
    Statement statement;

    private String recNum;

    private String jobId;
    private String checkInId;
    private String plannedAction;
    private String actualAction;
    private String plannedTargetDate;
    private String actualTargetDate;
    private String plannedResourcesBudget;
    private String actualResourcesBudget;
    private String responsiblePerson;
    private String responsiblePersonSignature;
   
    private List<CheckInActions> checkInActions = new ArrayList<CheckInActions>();    
    private CheckInActions selectedCheckInActions;
    CheckInProcessing cip = new CheckInProcessing();

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    public CheckInActions() {           

        checkInActions.clear();

        for (int x=0;x<15;x++) {

            //checkInActions.add(new CheckInActions(""+x, jobId, cip.checkInId, "", "", "", "", "", "", ""));
            checkInActions.add(new CheckInActions("", "", "", "", "", "", ""));

        }
                
    }

    public CheckInActions(String plannedAction, String actualAction, String plannedTargetDate, String actualTargetDate, String plannedResourcesBudget, String actualResourcesBudget, String responsiblePerson) {

        this.plannedAction = plannedAction;
        this.actualAction = actualAction;
        this.plannedTargetDate = plannedTargetDate;
        this.actualTargetDate = actualTargetDate;
        this.plannedResourcesBudget = plannedResourcesBudget;
        this.actualResourcesBudget = actualResourcesBudget;
        this.responsiblePerson = responsiblePerson;        

    }

    /*public String searchCustomer() {
        getParams();

        customers.clear();
        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();

            if (logged == true) {

                int i = 0;
                if (!cName.equals("")) {
                    query = "SELECT * FROM clients where uk = '" + uk + "' and name like '%" + cName + "%'";
                } else if (!cSurname.equals("")) {
                    query = "SELECT * FROM clients where uk = '" + uk + "' and surname like '%" + cSurname + "%'";
                } else if (!cSurname.equals("") & (!cName.equals(""))) {
                    query = "SELECT * FROM clients where uk = '" + uk + "' and name like '%" + cName + "%' and surname like '%" + cSurname + "%'";
                }
                statement = connection.createStatement();
                ResultSet cfs = statement.executeQuery(query);

                proceed = true;
                while (cfs.next()) {
                    proceed = false;
                    clientRecNum = cfs.getString(1);
                    customers.add(new CheckInActions(cfs.getString(1), cfs.getString(17), cfs.getString(3), cfs.getString(4), cfs.getString(5), cfs.getString(6), cfs.getString(7), cfs.getString(8), cfs.getString(9), cfs.getString(18), cfs.getString(10), cfs.getString(11), cfs.getString(12), cfs.getString(13), cfs.getString(14)));

                }

                connection.close();

            } else {

            }

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/customers/customer-listing";

    }
    
    public String getCustomer(String recnum) {
        getParams();

        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();

            if (logged == true) {

                int i = 0;               
                query = "SELECT * FROM clients where recnum = '" + recnum + "'";
                statement = connection.createStatement();
                ResultSet cfs = statement.executeQuery(query);

                while (cfs.next()) {
                    clientDat = cfs.getString(1) + ',' + cfs.getString(5) + ',' + cfs.getString(6) + ',' + cfs.getString(8) + ',' + cfs.getString(9) + ',' + cfs.getString(10) + ',' + cfs.getString(18) + ',' + cfs.getString(11) + ',' + cfs.getString(12) + ',' + cfs.getString(17);
                }

                connection.close();

            } else {

            }

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return clientDat;

    }

    public List<CheckInActions> getAllCustomers() {

        getParams();
        allCustomers.clear();
        try {

            String query = "";
            String clientRecNum = "";
            boolean proceed = false;

            Systems.closeConnection();
            boolean logged = Systems.checkLogin(uk);
            String oid = Systems.getOID();
            connection = Systems.initConnection();
            query = "SELECT * FROM clients order by name";
            statement = connection.createStatement();
            ResultSet cfs = statement.executeQuery(query);

            proceed = true;
            while (cfs.next()) {
                proceed = false;
                clientRecNum = cfs.getString(1);
                allCustomers.add(new CheckInActions(cfs.getString(1), cfs.getString(17), cfs.getString(3), cfs.getString(4), cfs.getString(5), cfs.getString(6), cfs.getString(7), cfs.getString(8), cfs.getString(9), cfs.getString(18), cfs.getString(10), cfs.getString(11), cfs.getString(12), cfs.getString(13), cfs.getString(14)));                
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return allCustomers;
    }

    public String filterCustomers() {

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
            ResultSet cfs = statement.executeQuery(query);

            int count = 0;
            proceed = true;
            while (cfs.next()) {                
                proceed = false;
                clientRecNum = cfs.getString(1);              
                filteredCustomers.add(new CheckInActions(cfs.getString(1), cfs.getString(17), cfs.getString(3), cfs.getString(4), cfs.getString(5), cfs.getString(6), cfs.getString(7), cfs.getString(8), cfs.getString(9), cfs.getString(18), cfs.getString(10), cfs.getString(11), cfs.getString(12), cfs.getString(13), cfs.getString(14)));                
                //setPicPath(clientRecNum);
                count++;
            }

           // connection.close();
            this.count = count;
            
           
        } catch (Exception sqle) {
            System.out.println(sqle);
        }               
        
                
        return "/customers/customer-detailed-list.xhtml";

    }   

    public List<CheckInActions> getFilteredCustomers() {

        filterCustomers();
        //customers.add(new CustomerList("0000","Wandile","Nxum","dwed","sdfvsdf","sdfsd","Sdfsdf","ergergt","bergfergf","wfwrewer","2432edwqed","sdfwefrw","wsfwerw"));

        return filteredCustomers;
    }

    public List<CheckInActions> getCustomers() {
        getParams();
        searchCustomer();
        //customers.add(new CustomerList("0000","Wandile","Nxum","dwed","sdfvsdf","sdfsd","Sdfsdf","ergergt","bergfergf","wfwrewer","2432edwqed","sdfwefrw","wsfwerw"));

        return customers;
    }*/

    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }
        
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setCheckInId(String checkInId) {
        this.checkInId = checkInId;
    }

    public String getCheckInId() {
        return checkInId;
    }

    public void setPlannedAction(String plannedAction) {
        this.plannedAction = plannedAction;
    }

    public String getPlannedAction() {
        return plannedAction;
    }            

    public void setActualAction(String actualAction) {
        this.actualAction = actualAction;
    }

    public String getActualAction() {
        return actualAction;
    }

    public void setPlannedTargetDate(String plannedTargetDate) {
        this.plannedTargetDate = plannedTargetDate;
    }

    public String getPlannedTargetDate() {
        return plannedTargetDate;
    }

    public void setActualTargetDate(String actualTargetDate) {
        this.actualTargetDate = actualTargetDate;
    }

    public String getActualTargetDate() {
        return actualTargetDate;
    }

    public void setPlannedResourcesBudget(String plannedResourcesBudget) {
        this.plannedResourcesBudget = plannedResourcesBudget;
    }

    public String getPlannedResourcesBudget() {
        return plannedResourcesBudget;
    }   
    
    public void setActualResourcesBudget(String actualResourcesBudget) {
        this.actualResourcesBudget = actualResourcesBudget;
    }

    public String getActualResourcesBudget() {
        return actualResourcesBudget;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePersonSignature(String responsiblePersonSignature) {
        this.responsiblePersonSignature = responsiblePersonSignature;
    }

    public String getResponsiblePersonSignature() {
        return responsiblePersonSignature;
    }

    public List getCheckInActions() {        
        return checkInActions;
    }

    public void setCheckInActions(List checkInActions) {
        this.checkInActions = checkInActions;
    } 
        
    
    public CheckInActions getSelectedSelectedCheckInActions() {
        return selectedCheckInActions;
    }

    public void setSelectedSelectedCheckInActions(CheckInActions selectedCheckInActions) {
        this.selectedCheckInActions = selectedCheckInActions;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.plannedAction = ((CheckInActions) event.getObject()).plannedAction;
        this.actualAction = ((CheckInActions) event.getObject()).actualAction;
        this.plannedTargetDate = ((CheckInActions) event.getObject()).plannedTargetDate;
        this.actualTargetDate = ((CheckInActions) event.getObject()).actualTargetDate;
        this.plannedResourcesBudget = ((CheckInActions) event.getObject()).plannedResourcesBudget;
        this.actualResourcesBudget = ((CheckInActions) event.getObject()).actualResourcesBudget;
        this.responsiblePerson = ((CheckInActions) event.getObject()).responsiblePerson;      

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        
        String param = "name=" + request.getSession().getAttribute("username").toString() + "&uk=" + request.getSession().getAttribute("uk").toString() + "&recnum=" + this.recNum + "&jobId=" + jobId + "&checkInId=" + checkInId 
                + "&plannedAction=" + plannedAction + "&actualAction=" + actualAction + "&plannedTargetDate=" + plannedTargetDate 
                + "&actualTargetDate=" + actualTargetDate + "&plannedResourcesBudget=" + plannedResourcesBudget + "&actualResourcesBudget=" + actualResourcesBudget
                + "&responsiblePerson=" + responsiblePerson;
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/add-aspects.xhtml?" + param);
        }                

    }

    public void viewCustomer() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/customers/view-customer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CheckInActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        
//        this.jobId = selectedCheckInActions.jobId;  
        System.out.println(((CheckInActions) event.getObject()).getActualAction());
                
        
        //FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
       // FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        
    }
    
    public String checkInAspects(String jobId, String checkInId, List<CheckInActions> checkInActions) {

        this.jobId = jobId;   
        this.checkInId = checkInId;                      
        
        return "/jobs/check-in-aspects";
    }
    
}