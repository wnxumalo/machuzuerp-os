package com.machuzuerp.dashboards;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.inject.Named;

/**
 *
 * @author Administrator
 */
@Named
@RequestScoped
public class AccountingDashboard {

    Connection connection;
    Statement statement;
    ResultSet results;

    private LineChartModel totRevenueExpenditure;
    private PieChartModel gender;
    private PieChartModel type;
    private PieChartModel status;

    private Date dateFrom;
    private Date dateTo;
    String tempMonth;
    String tempYear;
    
    AccountingProcessing ap = new AccountingProcessing();
    
    private List<DashboardValues> totExpenditure = new ArrayList<DashboardValues>();
    private List<DashboardValues> totRevenue = new ArrayList<DashboardValues>();
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd");
    SimpleDateFormat smf = new SimpleDateFormat("MM");
    SimpleDateFormat syf = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdtf = new SimpleDateFormat("yyyy/MM/dd");
    
    public AccountingDashboard() {
        
        gender = new PieChartModel();         
        type = new PieChartModel();         
        status = new PieChartModel(); 

      /*  dateTo = new Date();
        tempMonth = smf.format(dateTo);
        tempMonth = ""+(Integer.parseInt(tempMonth)-1);
        tempMonth = syf.format(dateTo) + "-" + tempMonth + "-" + sdf.format(dateTo);

        try {
            dateFrom = sdtf.parse(tempMonth);
        } catch (ParseException ex) {
            Logger.getLogger(AccountingDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
       // createDateModel();

    }
    
     private static final long serialVersionUID = 1L;

    private String json;
    
    @PostConstruct
    public void init() { 
        createJsonModel();
        try {
            dateFrom = sdtf.parse("2022/01/01");
            dateTo = sdtf.parse("2022/12/31");
        } catch (ParseException ex) {
            Logger.getLogger(StakeholdersDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        createDateModel();
    }
    
    public void createJsonModel() {
        json = "{\n" +
                    "    \"title\": {\n" +
                    "        \"text\": \"Apache ECharts Line Chart\"\n" +
                    "    },\n" +
                    "    \"xAxis\": {\n" +
                    "        \"type\": \"category\",\n" +
                    "        \"data\": [\"Mon\", \"Tue\", \"Wed\", \"Thu\", \"Fri\", \"Sat\", \"Sun\"]\n" +
                    "    },\n" +
                    "    \"yAxis\": {\n" +
                    "        \"type\": \"value\"\n" +
                    "    },\n" +
                    "    \"series\": [\n" +
                    "        {\n" +
                    "            \"name\": \"Some data\",\n" +
                    "            \"data\": [150, 230, 224, 218, 135, 147, 260],\n" +
                    "            \"type\": \"line\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"Other data\",\n" +
                    "            \"data\": [110, 260, 124, 118, 235, 100, 200],\n" +
                    "            \"type\": \"line\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"legend\": {},\n" +
                    "    \"dataZoom\": [\n" +
                    "        {\n" +
                    "            \"type\": \"slider\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
    }

    /*public void itemSelect(EChartEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String message = "Clicked " + event.getName()
                    + ", series index " + event.getSeriesIndex()
                    + ", data index " + event.getDataIndex()
                    + ", value " + event.getData();
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        facesContext.addMessage(null, facesMessage);
    }*/

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
    
    public void filterDashboard() throws SQLException {
        createDateModel();        
    }
    
    private void createDateModel() {
        
        try {
            totRevenueExpenditure = new LineChartModel();
            
            totExpenditure = ap.CalculateExpenditure(dateFrom, dateTo);
            LineChartSeries series1 = new LineChartSeries();            
            series1.setLabel("Expenditure");
            totExpenditure.forEach((dvs) -> {
                series1.set(dvs.getDataDate(), dvs.getTotal());
            });
            totRevenueExpenditure.addSeries(series1); 
            
            /*totRevenue = ap.CalculateRevenue(dateFrom, dateTo);
            LineChartSeries series2 = new LineChartSeries();            
            series2.setLabel("Revenue");
            totRevenue.forEach((dvs) -> {
                series2.set(dvs.getDataDate(), dvs.getTotal());
            });
            totRevenueExpenditure.addSeries(series2); */
            
            totRevenueExpenditure.setTitle("Journal Entries");
            totRevenueExpenditure.setZoom(true);
            totRevenueExpenditure.getAxis(AxisType.Y).setLabel("Total");
            DateAxis axis = new DateAxis("Dates");
            axis.setTickAngle(-50);
            //axis.setMax("2014-02-01");
            axis.setTickFormat("%b %#d, %y");
            totRevenueExpenditure.getAxes().put(AxisType.X, axis);
        } catch (NullPointerException | SQLException ex) {
            System.out.println(ex);
        }
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

    public LineChartModel getTotRevenueExpenditure() {
        return totRevenueExpenditure;
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
