package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.InventoryStorageSpace;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.InventoryStorageSpaceFacade;

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

@Named("inventoryStorageSpaceController")
@SessionScoped
public class InventoryStorageSpaceController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.InventoryStorageSpaceFacade ejbFacade;
    private List<InventoryStorageSpace> items = null;
    private InventoryStorageSpace selected;    
    private String[] accessibilityScore;
    private String[] temperatureRequirements;
    private String[] securityLevel;

    public InventoryStorageSpaceController() {
    }

    public InventoryStorageSpace getSelected() {
        return selected;
    }

    public void setSelected(InventoryStorageSpace selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InventoryStorageSpaceFacade getFacade() {
        return ejbFacade;
    }
    
    public String[] getAccessibilityScore() {
        return accessibilityScore;
    }
    
    public void setAccessibilityScore(String[] accessibilityScore) {
        this.accessibilityScore = accessibilityScore;
    }
    
    public String[] getTemperatureRequirements() {
        return temperatureRequirements;
    }
    
    public void setTemperatureRequirements(String[] temperatureRequirements) {
        this.temperatureRequirements = temperatureRequirements;
    }
    
    public String[] getSecurityLevel() {
        return securityLevel;
    }
    
    public void setSecurityLevel(String[] securityLevel) {
        this.securityLevel = securityLevel;
    }   

    public InventoryStorageSpace prepareCreate() {
        selected = new InventoryStorageSpace();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long inventoryStorageId) {
        selected.setInventoryStorageId(inventoryStorageId);
        String aScores = "";
        
        for (String aScore : getAccessibilityScore()) {
            aScores += "," + aScore;
        }
        selected.setAccessibilityScore(aScores);
        //selected.setTemperatureRequirements(getTemperatureRequirements().toString());
        //selected.setSecurityLevel(getSecurityLevel().toString());

        persist(PersistAction.CREATE, "Inventory Storage Space Created Successfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Inventory Storage Space Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Inventory Storage Space Deleted  Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<InventoryStorageSpace> getItems() {
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

    public InventoryStorageSpace getInventoryStorageSpace(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<InventoryStorageSpace> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InventoryStorageSpace> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InventoryStorageSpace.class)
    public static class InventoryStorageSpaceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InventoryStorageSpaceController controller = (InventoryStorageSpaceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inventoryStorageSpaceController");
            return controller.getInventoryStorageSpace(getKey(value));
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
            if (object instanceof InventoryStorageSpace) {
                InventoryStorageSpace o = (InventoryStorageSpace) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InventoryStorageSpace.class.getName()});
                return null;
            }
        }

    }

}
