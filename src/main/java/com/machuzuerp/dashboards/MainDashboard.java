/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.dashboards;

import com.machuzuerp.controllers.datatableprocessing.jsf.RequisitionData;
import com.machuzuerp.controllers.jsf.Login;
import com.machuzuerp.controllers.jsf.Systems;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpSession;

@RequestScoped
@Named
public class MainDashboard {

    AccountingProcessing ap = new AccountingProcessing();
    
    int tot = 0;
    String finTot = "";
    private String color;
    private String openRequisitions;
    
    ResultSet results;
    
    public MainDashboard() throws SQLException {        

        openRequisitions = "0";
        color = "#FFFFFF";
        try {
            tot = ap.getOpenRequisitionCount();
            
            if (tot > 0) {
                setColor("#e00000");
                openRequisitions = ""+tot;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public MainDashboard(HttpSession session) {

        openRequisitions = "No Items";
        color = "#FFFFFF";
        try {
            tot = ap.getOpenRequisitionCount();
            
            if (tot > 0) {
                setColor("#e00000");
                openRequisitions = "View";
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRequisitions() throws SQLException {
        
        if (tot > 0)
            finTot = ""+tot;

        return finTot;
    }

    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getOpenRequisitions() {
        return openRequisitions;
    }
    
    public String openRequisitionCount() throws SQLException, IOException {

        this.openRequisitions = ""+ap.getOpenRequisitionCount();
        
        if (tot > 0) {
            setColor("#e00000");
//            openRequisitions = "View";
        }

        return openRequisitions;
    }

}
