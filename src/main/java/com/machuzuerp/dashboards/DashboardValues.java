package com.machuzuerp.dashboards;

import java.util.Date;

class DashboardValues {
    
    private Date dataDate;
    private int total;
    private String name;
    
    public DashboardValues(Date dataDate, int total) {
        this.dataDate = dataDate;
        this.total = total;
    }
    
    public DashboardValues(String name, int total) {
        this.name = name;
        this.total = total;
    }
    
    public Date getDataDate() {
        return dataDate;
    }
    
    public String getName() {
        return name;
    }

    public void setDataDate(Date dataDate) {
        this.dataDate = dataDate;
    }
    
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}