package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;


@SessionScoped
@Named("dtInvEditView")
public class InventoryEditView implements Serializable {
     
    private List<InventoryData> inventory = new ArrayList<InventoryData>();
         
    private InventoryService inv = new InventoryService();

    @PostConstruct
    public void init() {
        inventory = inv.createServices();
    }
 
    public List<InventoryData> getInventoryList() {
        return inventory;
    }
    
    public InventoryService getInventory() {
        return inv;
    }
 
    public void setInventory(InventoryService inv) {
        this.inv = inv;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((ServiceData) event.getObject()).getRecNum());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((ServiceData) event.getObject()).getRecNum());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
