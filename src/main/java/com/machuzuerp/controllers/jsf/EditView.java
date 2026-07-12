package com.machuzuerp.controllers.jsf;
 
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import com.machuzuerp.controllers.datatableprocessing.jsf.ServiceData;
 
@ViewScoped
@Named("dtEditView")
public class EditView implements Serializable {
     
    private List<ServiceData> services;
         
    @ManagedProperty("#{carService}")
    private ServiceData service;
     
    @PostConstruct
    public void init() {
        services = service.getAllServices();
    }
 
    public List<ServiceData> getServices() {
        return services;
    } 

    public void setService(ServiceData service) {
        this.service = service;
    }
     
   /* public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }*/
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}