package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.InventoryStorage;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.InventoryStorageFacade;
import com.machuzuerp.controllers.jpa.InventoryStorageSpaceFacade;
import com.machuzuerp.entities.InventoryStorageSpace;
import java.io.IOException;

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
import org.primefaces.event.SelectEvent;

@Named("inventoryStorageController")
@SessionScoped
public class InventoryStorageController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.InventoryStorageFacade ejbFacade;
    @EJB
    private com.machuzuerp.controllers.jpa.InventoryStorageSpaceFacade ejbStorageSpaceFacade;
    private List<InventoryStorage> items = null;
    private InventoryStorage selected = new InventoryStorage();
    
    private List<InventoryStorageSpace> inventoryStorageSpaceList = null;
    private InventoryStorageSpace selectedInventoryStorageSpace;

    public InventoryStorageController() {
    }

    public InventoryStorage getSelected() {
        return selected;
    }

    public void setSelected(InventoryStorage selected) {
        this.selected = selected;
    }

    public void onRowSelect(SelectEvent event) throws IOException {
        selected.setInventoryStorageSpaceList(getStorageSpaceFacade().getInventoryStorageSpaceList(selected.getId()));

        System.out.println("INVSTORe: " + selected.getId() + ":" + selected.getInventoryStorageSpaceList().size());
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InventoryStorageFacade getFacade() {
        return ejbFacade;
    }

    private InventoryStorageSpaceFacade getStorageSpaceFacade() {
        return ejbStorageSpaceFacade;
    }

    public InventoryStorage prepareCreate(int atHand) {
        selected = new InventoryStorage();
        selected.setQuantity(atHand);
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long inventoryId) {
        selected.setInventoryId(inventoryId);                
        persist(PersistAction.CREATE, "Inventory Storage Created Successfully");
    }

    public void update() {
        persist(PersistAction.UPDATE, "Inventory Storage Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Inventory Storage Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<InventoryStorage> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<InventoryStorageSpace> getInventoryStorageSpaceList() {     
        return inventoryStorageSpaceList;
    }
    
    public void setInventoryStorageSpaceList(List<InventoryStorageSpace> inventoryStorageSpaceList) {
        this.inventoryStorageSpaceList = inventoryStorageSpaceList;
    }
    
    public InventoryStorageSpace getSelectedInventoryStorageSpace() {     
        return selectedInventoryStorageSpace;
    }
    
    public void setSelectedInventoryStorageSpace(InventoryStorageSpace selectedInventoryStorageSpace) {
        this.selectedInventoryStorageSpace = selectedInventoryStorageSpace;
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

    public InventoryStorage getInventoryStorage(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<InventoryStorage> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InventoryStorage> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InventoryStorage.class)
    public static class InventoryStorageControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InventoryStorageController controller = (InventoryStorageController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inventoryStorageController");
            return controller.getInventoryStorage(getKey(value));
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
            if (object instanceof InventoryStorage) {
                InventoryStorage o = (InventoryStorage) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InventoryStorage.class.getName()});
                return null;
            }
        }

    }

}
