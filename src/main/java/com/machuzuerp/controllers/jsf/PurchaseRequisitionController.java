package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.PurchaseRequisitionFacade;
import com.machuzuerp.entities.PurchaseRequisition;
import com.machuzuerp.controllers.jpa.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;

import java.io.Serializable;
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

@Named("purchaseRequisitionController")
@SessionScoped
public class PurchaseRequisitionController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.PurchaseRequisitionFacade ejbFacade;
    private List<PurchaseRequisition> items = null;
    private PurchaseRequisition selected;

    public PurchaseRequisitionController() {
    }

    public PurchaseRequisition getSelected() {
        return selected;
    }

    public void setSelected(PurchaseRequisition selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PurchaseRequisitionFacade getFacade() {
        return ejbFacade;
    }

    public PurchaseRequisition prepareCreate() {
        selected = new PurchaseRequisition();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Purchase Requisition Created");
    }

    public void update() {
        persist(PersistAction.UPDATE, "Purchase Requisition Updated");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Purchase Requisition Deleted");
    }

    public List<PurchaseRequisition> getItems() {
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

    public PurchaseRequisition getPurchaseRequisition(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<PurchaseRequisition> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PurchaseRequisition> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PurchaseRequisition.class)
    public static class PurchaseRequisitionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PurchaseRequisitionController controller = (PurchaseRequisitionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "purchaseRequisitionController");
            return controller.getPurchaseRequisition(getKey(value));
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
            if (object instanceof PurchaseRequisition) {
                PurchaseRequisition o = (PurchaseRequisition) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PurchaseRequisition.class.getName()});
                return null;
            }
        }

    }

}
