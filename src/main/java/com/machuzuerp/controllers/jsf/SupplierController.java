package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.LocationFacade;
import com.machuzuerp.controllers.jpa.PaymentFacade;
import com.machuzuerp.controllers.jpa.ShipmentFacade;
import com.machuzuerp.controllers.jpa.SupplierFacade;
import com.machuzuerp.entities.Supplier;
import com.machuzuerp.controllers.jpa.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.entities.Location;
import com.machuzuerp.entities.Payment;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.SelectEvent;

@Named("supplierController")
@SessionScoped
public class SupplierController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.SupplierFacade ejbFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.LocationFacade locationFacade;    

    @EJB
    private com.machuzuerp.controllers.jpa.PaymentFacade paymentFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.ShipmentFacade shipmentFacade;

    private List<Supplier> items = null;
    private Supplier selected;

    private List<Payment> payments = new ArrayList<Payment>();

    public SupplierController() {
    }
    
    @PostConstruct 
    public void init() {
       //this.selected.getLocations();
    }

    public Supplier getSelected() {
        return selected;
    }

    public void setSelected(Supplier selected) {
        this.selected = selected;
    }
    
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SupplierFacade getFacade() {
        return ejbFacade;
    }
    
    private LocationFacade getLocationFacade() {
        return locationFacade;
    }
    
    private PaymentFacade getPaymentFacade() {
        return paymentFacade;
    }
 
    private ShipmentFacade getShipmentFacade() {
        return shipmentFacade;
    }
 
    public Supplier prepareCreate() {
        selected = new Supplier();
        initializeEmbeddableKey();
        return selected;
    }

    public void onRowSelect(SelectEvent event) throws IOException {
        selected.setLocations(getLocationFacade().getSupplierLocations(selected.getId()));
        selected.setPayments(getPaymentFacade().getSupplierPayments(selected.getId()));
        selected.setShipments(getShipmentFacade().getSupplierShipments(selected.getId()));
    }

    public void create() {
        items = getFacade().findAll();
        
        persist(PersistAction.CREATE, "Supplier Created");                
    }

    public void update() {
        items = getFacade().findAll();
        persist(PersistAction.UPDATE, "Supplier Updated");                
    }

    public void destroy() {
        items = getFacade().findAll();
        persist(PersistAction.DELETE, "Supplier Deleted");                
    }

    public List<Supplier> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Supplier getSupplier(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Supplier> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Supplier> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Supplier.class)
    public static class SupplierControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SupplierController controller = (SupplierController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "supplierController");
            return controller.getSupplier(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Supplier) {
                Supplier o = (Supplier) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Supplier.class.getName()});
                return null;
            }
        }

    }

}
