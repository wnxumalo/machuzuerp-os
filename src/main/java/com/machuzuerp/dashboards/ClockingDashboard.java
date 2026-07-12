/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.dashboards;

import com.machuzuerp.jdbc.ClockingProcessing;
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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Administrator
 */
@ManagedBean
@RequestScoped
public class ClockingDashboard {

    Connection connection;
    Statement statement;
    ResultSet results;

    private LineChartModel totByJoiningDate;
    private PieChartModel gender;
    private PieChartModel type;
    private PieChartModel status;
    private BarChartModel totFilesTeamname;

    private Date dateFrom;
    private Date dateTo;

    ClockingProcessing sp = new ClockingProcessing();
    
    private List<DashboardValues> totClocking = new ArrayList<DashboardValues>();

    
    public ClockingDashboard() {
        
        gender = new PieChartModel();         
        type = new PieChartModel();         
        status = new PieChartModel();         
        
    }
    
    @PostConstruct
    public void init() {        
        createDateModel();
        try {
            createBarModel();
        } catch (SQLException ex) {
            Logger.getLogger(CollaborationDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private BarChartModel initBarModel() throws SQLException {
        BarChartModel model = new BarChartModel();

        //ChartSeries series1 = new ChartSeries();
        //series1.setLabel("Teams");
        ChartSeries series2 = new ChartSeries();
        series2.setLabel("Logs");

        try {
        /*    for (DashboardValues dvs : cp.CalculateFilesByTeam(dateFrom, dateTo)) {
                series1.set(dvs.getName(), dvs.getTotal());
                
            };*/
            
//            for (DashboardValues dvs : cp.CalculateFilesByType(dateFrom, dateTo)) {
                //series2.set(dvs.getName(), dvs.getTotal());
                
                series2.set("Jan", 300);
                series2.set("Feb", 400);
                series2.set("Mar", 900);
                series2.set("Apr", 50);
                series2.set("May", 100);
                series2.set("Jun", 1500);
                series2.set("July", 1200);
                series2.set("Aug", 120);
                series2.set("Sep", 11);
                series2.set("Oct", 30);
                series2.set("Nov", 99);
                series2.set("Dec", 5);
                
  //          };
        } catch (NullPointerException npe) {}       

       // model.addSeries(series1);
        model.addSeries(series2);
 
        return model;
    }
    
    private void createBarModel() throws SQLException {
        totFilesTeamname = initBarModel();
 
        totFilesTeamname.setTitle("Clocking by Month");
        totFilesTeamname.setLegendPosition("Total");
 
        Axis xAxis = totFilesTeamname.getAxis(AxisType.X);
        xAxis.setLabel("Month");
 
        Axis yAxis = totFilesTeamname.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        //yAxis.setMax(maxVal);
    }
    
    public void filterDashboard() throws SQLException {
        createDateModel();
//        CalculateGender();
  //      CalculateType();
    //    CalculateStatus();
    }
    
    private void createDateModel() {
        
        try {
            totByJoiningDate = new LineChartModel();
            
            //totClocking = sp.TotalByJoiningDate(dateFrom, dateTo);
            LineChartSeries series1 = new LineChartSeries();
            
            series1.setLabel("Series 1");
            //for (DashboardValues dvs : totClocking) {
                
                //series1.set(dvs.getDataDate(), dvs.getTotal());
                
                series1.set("2019-01-01", 50);
                series1.set("2019-02-01", 100);
                series1.set("2019-03-01", 200);
                series1.set("2019-04-01", 300);
                series1.set("2019-05-01", 10);
                series1.set("2019-06-01", 20);
                series1.set("2019-07-01", 400);
                series1.set("2019-08-01", 500);
                
            //}

            totByJoiningDate.addSeries(series1); 
            totByJoiningDate.setTitle("Clocking");
            totByJoiningDate.setZoom(true);
            totByJoiningDate.getAxis(AxisType.Y).setLabel("Total");
            DateAxis axis = new DateAxis("Dates");
            axis.setTickAngle(-50);
            //axis.setMax("2014-02-01");
            axis.setTickFormat("%b %#d, %y");
            totByJoiningDate.getAxes().put(AxisType.X, axis);
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
    }
    
    public BarChartModel getTotFiles() {
        return totFilesTeamname;
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
