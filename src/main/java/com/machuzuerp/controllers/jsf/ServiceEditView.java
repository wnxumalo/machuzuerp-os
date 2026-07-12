package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@SessionScoped
@Named("dtServEditView")
public class ServiceEditView implements Serializable {
     
    private List<ServiceData> services = new ArrayList<ServiceData>();

    private ServService service = new ServService();
     
    @PostConstruct
    public void init() {
        services = service.createServices();
    }
 
    public List<ServiceData> getServices() {
        return services;
    }
    
    public ServService getService() {
        return service;
    }
 
    public void setService(ServService service) {
        this.service = service;
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
