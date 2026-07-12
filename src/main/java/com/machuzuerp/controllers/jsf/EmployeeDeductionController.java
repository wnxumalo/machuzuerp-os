package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.EmployeeDeduction;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.EmployeeDeductionFacade;

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

@Named("employeeDeductionController")
@SessionScoped
public class EmployeeDeductionController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeDeductionFacade ejbFacade;
    private List<EmployeeDeduction> items = null;
    private EmployeeDeduction selected;

    public EmployeeDeductionController() {
    }

    public EmployeeDeduction getSelected() {
        return selected;
    }

    public void setSelected(EmployeeDeduction selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeDeductionFacade getFacade() {
        return ejbFacade;
    }

    public EmployeeDeduction prepareCreate() {
        selected = new EmployeeDeduction();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long empId) {
        selected.setEmployeeId(empId);
        selected.setCreationDate(LocalDate.now());
        persist(PersistAction.CREATE, "Employee Deduction Created Successfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Employee Deduction Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Employee Deduction Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EmployeeDeduction> getItems() {
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

    public EmployeeDeduction getEmployeeDeduction(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<EmployeeDeduction> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EmployeeDeduction> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EmployeeDeduction.class)
    public static class EmployeeDeductionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeDeductionController controller = (EmployeeDeductionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeDeductionController");
            return controller.getEmployeeDeduction(getKey(value));
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
            if (object instanceof EmployeeDeduction) {
                EmployeeDeduction o = (EmployeeDeduction) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EmployeeDeduction.class.getName()});
                return null;
            }
        }

    }

}
