/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Administrator
 */
@SessionScoped
@Named
public class ServiceData implements Serializable {

    /**
     * Creates a new instance of ServiceData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String sRecNum;
    private String code;
    private String description;
    private BigDecimal cost;
    private BigDecimal totCost;
    private int quantity;

    private String byCode;
    int count = 9;
    private String byDescription;

    String serviceRecNum = "";

    private List<ServiceData> services = new ArrayList<ServiceData>();
    private List<ServiceData> allServices = new ArrayList<ServiceData>();
    private List<ServiceData> funcServices = new ArrayList<ServiceData>();
    private List<ServiceData> filteredServices = new ArrayList<ServiceData>();
    private ServiceData selectedServices;        
    private List<ServiceData> filteredServs; 
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";

    public ServiceData() {
        
    }

    public ServiceData(String sRecNum, String code, String description, BigDecimal cost, int quantity, BigDecimal totCost) {

        this.sRecNum = sRecNum;
        this.code = code;
        this.description = description;
        this.cost = cost;
        this.quantity = quantity;
        this.totCost = totCost;
    }
    
    public String findService(HttpSession session) {
       code = "";
       description = "";
       return "/services/find-service";
    }
    

    public String searchService() {

        services.clear();
        try {

            connection = Systems.initConnection();

            int i = 0;
            if (!code.equals("")) {
                query = "SELECT * FROM services where service_no = '" + code + "'";
            } else if (!description.equals("")) {
                query = "SELECT * FROM services where description like '%" + description + "%'";
            } else {
                query = "SELECT * FROM services where description like '%" + description + "' and service_no = '" + code + "'";
            }
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                serviceRecNum = results.getString(1);
                services.add(new ServiceData(results.getString(1),results.getString(2), results.getString(3), results.getBigDecimal(7),1,new BigDecimal("0")));

            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/services/services-listing";

    }

    public List<ServiceData> getAllServices() {

        allServices.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM services order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                serviceRecNum = results.getString(1);
                allServices.add(new ServiceData(results.getString(1),results.getString(2), results.getString(3), results.getBigDecimal(7),1, new BigDecimal("0")));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return allServices;
    }

    

    public void filterServices() {

        filteredServices.clear();

        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM services where description like '%" + description + "%' order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                serviceRecNum = results.getString(1);
                filteredServices.add(new ServiceData(results.getString(1), results.getString(2), results.getString(3), results.getBigDecimal(7),1,new BigDecimal("0")));
                count++;
            }

            this.count = count;

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

    }
    
    public List<ServiceData> getFilteredServs() {
        return filteredServs;
    }
 
    public void setFilteredServs(List<ServiceData> filteredServs) {
        this.filteredServs = filteredServs;
    }

    public List<ServiceData> getFilteredServices() {

        filterServices();

        return filteredServices;
    }

    public List<ServiceData> getServices() {

        return services;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public String getRecNum() {
        return sRecNum;
    }

    public void setRecNum(String sRecNum) {
        this.sRecNum = sRecNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }
    
    public int getQty() {
        return quantity;
    }
    
    public void setQty(int quantity) {
        this.quantity = quantity;
    } 

    public ServiceData getSelectedService() {
        return selectedServices;
    }

    public void setSelectedService(ServiceData selectedServ) {
        this.selectedServices = selectedServ;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.sRecNum = ((ServiceData) event.getObject()).sRecNum;
        this.code = ((ServiceData) event.getObject()).code;
        this.description = ((ServiceData) event.getObject()).description;
        this.cost = ((ServiceData) event.getObject()).cost;       

        try {
            this.description = URLEncoder.encode(this.description, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServiceData.class.getName()).log(Level.SEVERE, null, ex);
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
        String param = "code=" + code + "&uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + sRecNum + "&description=" + description + "&cost=" + cost;
        
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-service.xhtml?" + param);
        }             

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
            Logger.getLogger(ServiceData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
