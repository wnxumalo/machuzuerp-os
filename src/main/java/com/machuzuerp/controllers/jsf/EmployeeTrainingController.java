package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.EmployeeTraining;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.EmployeeTrainingFacade;

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

@Named("employeeTrainingController")
@SessionScoped
public class EmployeeTrainingController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeTrainingFacade ejbFacade;
    private List<EmployeeTraining> items = null;
    private EmployeeTraining selected;

    public EmployeeTrainingController() {
    }

    public EmployeeTraining getSelected() {
        return selected;
    }

    public void setSelected(EmployeeTraining selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeTrainingFacade getFacade() {
        return ejbFacade;
    }

    public EmployeeTraining prepareCreate() {
        selected = new EmployeeTraining();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long empId) {
        selected.setEmployeeId(empId);
        selected.setCreationDate(LocalDate.now());
        persist(PersistAction.CREATE, "Employee Training Created Successfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Employee Training Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Employee Training Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EmployeeTraining> getItems() {
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

    public EmployeeTraining getEmployeeTraining(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<EmployeeTraining> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EmployeeTraining> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EmployeeTraining.class)
    public static class EmployeeTrainingControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeTrainingController controller = (EmployeeTrainingController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeTrainingController");
            return controller.getEmployeeTraining(getKey(value));
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
            if (object instanceof EmployeeTraining) {
                EmployeeTraining o = (EmployeeTraining) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EmployeeTraining.class.getName()});
                return null;
            }
        }

    }

}
