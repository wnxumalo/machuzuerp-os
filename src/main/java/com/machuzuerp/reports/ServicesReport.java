package com.machuzuerp.reports;

import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.controllers.jsf.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class ServicesReport implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;

    private String sRecNum;
    private String code;
    private String description;
    private double cost;
    private double totCost;
    private int quantity;

    private String byCode;
    int count = 9;
    private String byDescription;

    String serviceRecNum = "";

    private List<ServicesReport> services = new ArrayList<ServicesReport>();   
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";

    public ServicesReport() {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public ServicesReport(String sRecNum, String code, String description, double cost, int quantity, double totCost) {

        this.sRecNum = sRecNum;
        this.code = code;
        this.description = description;
        this.cost = cost;
        this.quantity = quantity;
        this.totCost = totCost;
    }

    public List<ServicesReport> getAllServices() {

        services.clear();

        try {

            connection = Systems.initConnection();
            query = "SELECT * FROM services order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                serviceRecNum = results.getString(1);
                services.add(new ServicesReport(results.getString(1),results.getString(2), results.getString(3), Float.parseFloat(results.getString(7)),1,0));
            }

            connection.close();

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }

        return services;
    }

    public List<ServicesReport> getServices() {
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public double getTotCost() {
        return totCost;
    }

    public void setTotCost(double totCost) {
        this.totCost = totCost;
    }
    
    public int getQty() {
        return quantity;
    }
    
    public void setQty(int quantity) {
        this.quantity = quantity;
    } 

}
