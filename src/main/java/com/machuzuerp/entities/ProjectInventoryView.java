package com.machuzuerp.entities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@ViewScoped
@Named
public class ProjectInventoryView implements Serializable {

    Connection connection;
    Statement statement;

    private String recNum;
    private String projectId;
    private String projectDescription;
    private String activityId;
    private String activityDescription;
    private String inventoryId;
    private String inventoryDescription;
    private int quantity;
    private BigDecimal price;
    private BigDecimal amount;

    private List<ProjectInventoryView> projectInventoryView = new ArrayList<ProjectInventoryView>();    
    private ProjectInventoryView selectedProjectInventoryView;        

    public ProjectInventoryView() throws ParseException {

    }

    public ProjectInventoryView(String projectId, String projectDescription, String activityId, String activityDescription, String inventoryId, String inventoryDescription, int quantity, BigDecimal price, BigDecimal amount) {

        this.projectId = projectId;
        this.projectDescription = projectDescription;
        this.activityId = activityId;
        this.activityDescription = activityDescription;
        this.inventoryId = inventoryId;
        this.inventoryDescription = inventoryDescription;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    
    }   

    public List<ProjectInventoryView> getProjectInventory() {
        return projectInventoryView;
    }
 
    public void setProjectInventory(List<ProjectInventoryView> projectInventoryView) {
        this.projectInventoryView = projectInventoryView;
    }
   
    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getProjectId() {
        return projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getProjectDescription() {
        return projectDescription;
    }
    
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    
    public String getActivityId() {
        return activityId;
    }
    
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    
    public String getActivityDescription() {
        return activityDescription;
    }
    
    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }
       
    public String getInventoryId() {
        return inventoryId;
    }        
    
    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    public String getInventoryDescription() {
        return inventoryDescription;
    }
    
    public void setInventoryDescription(String inventoryDescription) {
        this.inventoryDescription = inventoryDescription;
    }

    public int getQuantity() {
        return quantity;
    }      
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }      
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public BigDecimal getPrice() {
        return price;
    }      
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProjectInventoryView getSelectedProjectInventory() {
        return selectedProjectInventoryView;
    }

    public void setSelectedProjectInventoryView(ProjectInventoryView selectedProjectInventoryView) {
        this.selectedProjectInventoryView = selectedProjectInventoryView;
    }       

    public void onRowSelect(SelectEvent event) throws IOException {

    }

}
