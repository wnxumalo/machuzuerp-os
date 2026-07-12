package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.ShipmentFacade;
import com.machuzuerp.entities.Shipment;
import com.machuzuerp.controllers.jpa.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.entities.dto.PurchaseOrderDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;

@Named("shipmentController")
@SessionScoped
public class ShipmentController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.ShipmentFacade ejbFacade;
    private List<Shipment> items = null;
    private Shipment selected;

    public ShipmentController() {
    }

    public Shipment getSelected() {
        return selected;
    }

    public void setSelected(Shipment selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ShipmentFacade getFacade() {
        return ejbFacade;
    }

    public Shipment prepareCreate() {
        selected = new Shipment();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long sId) {
        selected.setSupplierId(sId);
        selected.setCreationDate(new Date());
        selected.setStatus("1");
        
        persist(PersistAction.CREATE, "Shipment Created");
    }

    public void update() {
        persist(PersistAction.UPDATE, "Shipment Updated");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Shipment Deleted");
    }

    public List<Shipment> getItems() {
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

    public Shipment getShipment(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Shipment> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Shipment> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Shipment.class)
    public static class ShipmentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ShipmentController controller = (ShipmentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "shipmentController");
            return controller.getShipment(getKey(value));
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
            if (object instanceof Shipment) {
                Shipment o = (Shipment) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Shipment.class.getName()});
                return null;
            }
        }

    }

}
