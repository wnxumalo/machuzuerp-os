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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.PieChartModel;
import com.machuzuerp.dashboards.DashboardValues;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Administrator
 */
@ManagedBean
@RequestScoped
public class CollaborationDashboard {

    Connection connection;
    Statement statement;
    ResultSet results;

    private BarChartModel totFilesTeamname;
    private PieChartModel type;

    private Date dateFrom;
    private Date dateTo;

    CollaborationProcessing cp = new CollaborationProcessing();
    
    private List<DashboardValues> totFiles = new ArrayList<DashboardValues>();
    private List<DashboardValues> totRevenue = new ArrayList<DashboardValues>();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    public CollaborationDashboard() {
        
        type = new PieChartModel();
        
    }
    
    @PostConstruct
    public void init() {        
        try {
            createBarModel();
            CalculateType();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborationDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void filterDashboard() throws SQLException {
        createBarModel();       
        CalculateType();
    }
    
    /*private void createDateModel() {
        
        try {
            totFilesTeamname = new BarChartModel();
            System.out.println("111");
            totFiles = cp.CalculateFilesByTeam(dateFrom, dateTo);
            System.out.println("222");
            BarChartSeries series1 = new BarChartSeries();            
            series1.setLabel("Team Files");
            System.out.println("333");
            totFiles.forEach((dvs) -> {
                series1.set(dvs.getDataDate(), dvs.getTotal());
            });
            System.out.println("444");
            totFilesTeamname.addSeries(series1); 
            
            /*totRevenue = ap.CalculateRevenue(dateFrom, dateTo);
            BarChartSeries series2 = new BarChartSeries();            
            series2.setLabel("Revenue");
            totRevenue.forEach((dvs) -> {
                series2.set(dvs.getDataDate(), dvs.getTotal());
            });
            totFilesTeamname.addSeries(series2); */
            
           /* totFilesTeamname.setZoom(true);
            
            totFilesTeamname.setTitle("Horizontal and Stacked");        
            totFilesTeamname.setLegendPosition("e");        
            totFilesTeamname.setStacked(true); 
           
            Axis xAxis = totFilesTeamname.getAxis(AxisType.X);        
            xAxis.setLabel("Teams");        
            xAxis.setMin(0);        
            //xAxis.setMax(200);                 
            Axis yAxis = totFilesTeamname.getAxis(AxisType.Y);        
            yAxis.setLabel("Total");         
        } catch (NullPointerException | SQLException ex) {
            System.out.println(ex);
        }
    }  */  
    
      private BarChartModel initBarModel() throws SQLException {
        BarChartModel model = new BarChartModel();

        //ChartSeries series1 = new ChartSeries();
        //series1.setLabel("Teams");
        ChartSeries series2 = new ChartSeries();
        series2.setLabel("Types");

        try {
        /*    for (DashboardValues dvs : cp.CalculateFilesByTeam(dateFrom, dateTo)) {
                series1.set(dvs.getName(), dvs.getTotal());
                
            };*/
            
            //for (DashboardValues dvs : cp.CalculateFilesByType(dateFrom, dateTo)) {
            for (DashboardValues dvs : cp.CalculateFilesByType(sdf.parse("2022/01/01"), sdf.parse("2022/12/31"))) {
                series2.set(dvs.getName(), dvs.getTotal());
                
            };
        } catch (NullPointerException | ParseException ne) {
            System.out.println("ERROR: " + ne);
        }       

       // model.addSeries(series1);
        model.addSeries(series2);
 
        return model;
    }
 
    private void createBarModel() throws SQLException {
        totFilesTeamname = initBarModel();
 
        totFilesTeamname.setTitle("Uploaded Files");
        totFilesTeamname.setLegendPosition("Total");
 
        Axis xAxis = totFilesTeamname.getAxis(AxisType.X);
        xAxis.setLabel("Month");
 
        Axis yAxis = totFilesTeamname.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        //yAxis.setMax(maxVal);
    }
    
    public void CalculateType() throws SQLException {
        type.set("Associates", cp.CalculateAssociates()); 
        type.set("Board", cp.CalculateBoard());
        type.set("Executive", cp.CalculateExecutive());
        type.set("Friends", cp.CalculateFriends());
        type.set("General", cp.CalculateGeneral());
        type.set("Management", cp.CalculateManagement());
        type.set("Staff", cp.CalculateStaff());
        type.setTitle("Uploaded Files By Team Type");        
        type.setLegendPosition("e");        
        type.setFill(false);        
        type.setShowDataLabels(true);        
        //model.setDiameter(150); 
    }

    public PieChartModel getType() {
        return type;
    }

    public BarChartModel getTotFilesTeamname() {
        return totFilesTeamname;
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
