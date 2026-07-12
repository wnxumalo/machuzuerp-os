package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.PasswordManagement;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.PasswordManagementFacade;
import com.machuzuerp.entities.Employee;

import java.io.Serializable;
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

@Named("passwordManagementController")
@SessionScoped
public class PasswordManagementController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.PasswordManagementFacade ejbFacade;
    private List<PasswordManagement> items = null;
    private PasswordManagement selected;

    public PasswordManagementController() {
    }

    public PasswordManagement getSelected() {
        return selected;
    }

    public void setSelected(PasswordManagement selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PasswordManagementFacade getFacade() {
        return ejbFacade;
    }

    public PasswordManagement prepareCreate() {
        selected = new PasswordManagement();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Employee employee) {
        
        try {
            
            getFacade().find(employee.getId());
            
            selected.setEmployeeId(employee.getId());
            persist(PersistAction.CREATE, "Password Management Created Successfully");
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }


        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "First Select an Employee"));
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Password Management Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Password Management Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PasswordManagement> getItems() {
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

    public PasswordManagement getPasswordManagement(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<PasswordManagement> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PasswordManagement> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PasswordManagement.class)
    public static class PasswordManagementControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PasswordManagementController controller = (PasswordManagementController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "passwordManagementController");
            return controller.getPasswordManagement(getKey(value));
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
            if (object instanceof PasswordManagement) {
                PasswordManagement o = (PasswordManagement) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PasswordManagement.class.getName()});
                return null;
            }
        }

    }

}
