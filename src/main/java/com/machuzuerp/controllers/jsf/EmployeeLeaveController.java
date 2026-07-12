package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.EmployeeLeave;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.EmployeeLeaveFacade;

import java.io.Serializable;
import java.time.LocalDate;
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

@Named("employeeLeaveController")
@SessionScoped
public class EmployeeLeaveController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeLeaveFacade ejbFacade;
    private List<EmployeeLeave> items = null;
    private EmployeeLeave selected;

    public EmployeeLeaveController() {
    }

    public EmployeeLeave getSelected() {
        return selected;
    }

    public void setSelected(EmployeeLeave selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeLeaveFacade getFacade() {
        return ejbFacade;
    }

    public EmployeeLeave prepareCreate() {
        selected = new EmployeeLeave();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long empId) {
        selected.setEmployeeId(empId);
        selected.setCreationDate(LocalDate.now());
        persist(PersistAction.CREATE, "Employee Leave Created Successfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Employee Leave Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Employee Leave Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EmployeeLeave> getItems() {
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

    public EmployeeLeave getEmployeeLeave(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<EmployeeLeave> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EmployeeLeave> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EmployeeLeave.class)
    public static class EmployeeLeaveControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeLeaveController controller = (EmployeeLeaveController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeLeaveController");
            return controller.getEmployeeLeave(getKey(value));
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
            if (object instanceof EmployeeLeave) {
                EmployeeLeave o = (EmployeeLeave) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EmployeeLeave.class.getName()});
                return null;
            }
        }

    }

}
