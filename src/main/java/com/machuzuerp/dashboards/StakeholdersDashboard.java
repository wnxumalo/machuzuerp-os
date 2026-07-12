/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.dashboards;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Administrator
 */
@Named
@RequestScoped
public class StakeholdersDashboard {

    Connection connection;
    Statement statement;
    ResultSet results;

    private LineChartModel totByJoiningDate;
    private PieChartModel gender;
    private PieChartModel type;
    private PieChartModel status;

    private Date dateFrom;
    private Date dateTo;

    StakeholdersProcessing sp = new StakeholdersProcessing();
    
    private List<DashboardValues> totStakeholders = new ArrayList<DashboardValues>();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
    
    public StakeholdersDashboard() {
        
        gender = new PieChartModel();         
        type = new PieChartModel();         
        status = new PieChartModel();         
        
    }
    
    @PostConstruct
    public void init() {  
        try {
            dateFrom = sdf.parse("2022/01/01");
            dateTo = sdf.parse("2022/12/31");
        } catch (ParseException ex) {
            Logger.getLogger(StakeholdersDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {              
            createDateModel();
            CalculateGender();
            CalculateType();
            CalculateStatus();
        } catch (SQLException ex) {
            Logger.getLogger(StakeholdersDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void filterDashboard() throws SQLException {
        createDateModel();
        CalculateGender();
        CalculateType();
        CalculateStatus();
    }
    
    private void createDateModel() {
        
        try {
            totByJoiningDate = new LineChartModel();
            
            totStakeholders = sp.TotalByJoiningDate(dateFrom, dateTo);
            LineChartSeries series1 = new LineChartSeries();
            
            series1.setLabel("Series 1");
            for (DashboardValues dvs : totStakeholders) {
                
                series1.set(dvs.getDataDate(), dvs.getTotal());
                
            }

            totByJoiningDate.addSeries(series1); 
            totByJoiningDate.setTitle("Total By Joining Date");
            totByJoiningDate.setZoom(true);
            totByJoiningDate.getAxis(AxisType.Y).setLabel("Total");
            DateAxis axis = new DateAxis("Dates");
            axis.setTickAngle(-50);
            //axis.setMax("2014-02-01");
            axis.setTickFormat("%b %#d, %y");
            totByJoiningDate.getAxes().put(AxisType.X, axis);
        } catch (NullPointerException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void CalculateGender() throws SQLException {
        gender.set("Male", sp.CalculateMale(dateFrom, dateTo)); 
        gender.set("Female", sp.CalculateFemale(dateFrom, dateTo));         
        gender.setTitle("Gender");        
        gender.setLegendPosition("e");        
        gender.setFill(false);        
        gender.setShowDataLabels(true);        
        //model.setDiameter(150); 
    }
    
    public void CalculateType() throws SQLException {
        type.set("Client", sp.CalculateTypeClient(dateFrom, dateTo)); 
        type.set("Partner", sp.CalculateTypePartner(dateFrom, dateTo));         
        type.setTitle("Type");        
        type.setLegendPosition("e");        
        type.setFill(false);        
        type.setShowDataLabels(true);        
        //model.setDiameter(150); 
    }
    
    public void CalculateStatus() throws SQLException {
        status.set("Active", sp.CalculateActive(dateFrom, dateTo)); 
        status.set("Inactive", sp.CalculateInactive(dateFrom, dateTo));         
        status.setTitle("Status");        
        status.setLegendPosition("e");        
        status.setFill(false);        
        status.setShowDataLabels(true);        
        //model.setDiameter(150); 
    }

    public PieChartModel getGender() {
        return gender;
    }
    
    public PieChartModel getType() {
        return type;
    }
    
    public PieChartModel getStatus() {
        return status;
    }

    public LineChartModel getTotByJoiningDate() {
        return totByJoiningDate;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

}
