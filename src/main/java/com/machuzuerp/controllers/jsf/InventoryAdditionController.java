package com.machuzuerp.controllers.jsf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.machuzuerp.controllers.jpa.AuditLoggingFacade;
import com.machuzuerp.entities.InventoryAddition;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.InventoryAdditionFacade;
import com.machuzuerp.controllers.jpa.InventoryFacade;
import com.machuzuerp.controllers.jpa.SupplierFacade;
import com.machuzuerp.entities.AuditLogging;
import com.machuzuerp.entities.Inventory;
import com.machuzuerp.entities.Supplier;
import com.machuzuerp.entities.dto.InventoryAdditionDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONException;
import org.primefaces.shaded.json.JSONObject;

@Named("inventoryAdditionController")
@SessionScoped
public class InventoryAdditionController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.InventoryAdditionFacade ejbFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.InventoryFacade ejbInventoryFacade;
    
    @EJB
    private com.machuzuerp.controllers.jpa.InventoryAdditionFacade ejbInventoryAdditionFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.AuditLoggingFacade auditFacade;
    
    @EJB
    private com.machuzuerp.controllers.jpa.SupplierFacade supplierFacade;

    private List<InventoryAddition> items = null;
    private InventoryAddition selected = new InventoryAddition();
    
    private List<InventoryAdditionDTO> itemsDTO = new ArrayList<InventoryAdditionDTO>();
    private Inventory inventoryItem = null;
    private Supplier supplier = null;
    
    private Long supplierId;
    
    Systems systems = new Systems();

    public InventoryAdditionController() {
    }

    public InventoryAddition getSelected() {
        return selected;
    }

    public void setSelected(InventoryAddition selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InventoryAdditionFacade getFacade() {
        return ejbFacade;
    }

    private InventoryFacade getInventoryFacade() {
        return ejbInventoryFacade;
    }
    
    private AuditLoggingFacade getAuditFacade() {
        return auditFacade;
    }
    
    private SupplierFacade getSupplierFacade() {
        return supplierFacade;
    }
    
    private InventoryAdditionFacade getInventoryAdditionFacade() {
        return ejbInventoryAdditionFacade;
    }    

    public InventoryAddition prepareCreate() {
        selected = new InventoryAddition();
        initializeEmbeddableKey();
        return selected;
    }
    
    public List<InventoryAdditionDTO> inventoryList(Long id) {
        
        itemsDTO.clear();
        try {
            items = getInventoryAdditionFacade().getInventoryAdditions(id);        

            for (InventoryAddition item: items) {

                inventoryItem = getInventoryFacade().find(item.getInventoryId());
                supplier = getSupplierFacade().find(item.getSupplierId());

                itemsDTO.add(new InventoryAdditionDTO(item.getId(), item.getDeliveryDate(), item.getComments(), item.getQuantity(), item.getInventoryId(), inventoryItem.getDescription(), item.getSupplierId(), supplier.getDescription(), item.getDeliveryNoteId()));
            }

    //        public InventoryAdditionDTO(Long id, LocalDate deliveryDate, String comments, int quantity, Long inventoryId, String inventoryDescription, Long supplierId, String supplierDescription, Long deliveryNoteId) {
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Inventory Additions", "No Additions Yet"));           
        }
        
        return itemsDTO;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public void create(Long inventoryId, HttpSession session) {

        selected.setInventoryId(inventoryId);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();        

        List<InventoryAdditionDTO> inventoryAdditionDTO = new ArrayList<InventoryAdditionDTO>();
        Inventory inventory = getInventoryFacade().find(selected.getInventoryId());

        // get existing expiry data, add new and save json
        try {

            JSONArray jsonRules = new JSONArray(inventory.getExpiryDates());
            JSONObject obj = null;
            for (int i=0; i<jsonRules.length();i++) {
                obj = jsonRules.getJSONObject(i);

                inventoryAdditionDTO.add(new InventoryAdditionDTO(obj.getString("sKUNumber"),LocalDate.parse(obj.getString("expiryDate"))));
            }                        

        } catch (NumberFormatException | JSONException | IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("JSON ERR INVENTORY ADD: " + e);
        }
                
        inventoryAdditionDTO.add(new InventoryAdditionDTO(selected.getSKUNumber(), selected.getExpiryDate()));

        String newJsonObject = gson.toJson(inventoryAdditionDTO);  
        inventory.setExpiryDates(newJsonObject);
        // end
        
        inventory.setAtHand(inventory.getAtHand() + selected.getQuantity());
        
        getInventoryFacade().edit(inventory);

        AuditLogging audit = new AuditLogging();
        audit.setActionResponse("Successful");
        audit.setAppModule("Create.xhtml");
        audit.setEmployeeId(Long.parseLong(session.getAttribute("uk").toString()));
        audit.setUserAction("InventoryAddition");
        audit.setDateCreated(LocalDate.now());
        audit.setTimeCreated(LocalTime.now());
        getAuditFacade().create(audit);

        persist(PersistAction.CREATE, "Inventory Added Successfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Inventory Addition Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Inventory Addition Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<InventoryAddition> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<InventoryAdditionDTO> getItemsDTO() {
        return itemsDTO;
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

    public InventoryAddition getInventoryAddition(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<InventoryAddition> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InventoryAddition> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InventoryAddition.class)
    public static class InventoryAdditionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InventoryAdditionController controller = (InventoryAdditionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inventoryAdditionController");
            return controller.getInventoryAddition(getKey(value));
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
            if (object instanceof InventoryAddition) {
                InventoryAddition o = (InventoryAddition) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InventoryAddition.class.getName()});
                return null;
            }
        }

    }

}
