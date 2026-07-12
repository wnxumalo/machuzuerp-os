package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.EmployeeBenefit;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.EmployeeBenefitFacade;
import com.machuzuerp.controllers.jpa.EmployeeFacade;
import com.machuzuerp.entities.Employee;

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

@Named("employeeBenefitController")
@SessionScoped
public class EmployeeBenefitController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeBenefitFacade ejbFacade;
    
    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeFacade ejbEmployeeFacade;
    
    private List<EmployeeBenefit> items = null;
    private EmployeeBenefit selected;
    
    Employee employee;

    public EmployeeBenefitController() {
    }

    public EmployeeBenefit getSelected() {
        return selected;
    }

    public void setSelected(EmployeeBenefit selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeBenefitFacade getFacade() {
        return ejbFacade;
    }

    private EmployeeFacade getEmployeeFacade() {
        return ejbEmployeeFacade;
    }

    public EmployeeBenefit prepareCreate() {
        selected = new EmployeeBenefit();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long empId) {
        selected.setEmployeeId(empId);
        selected.setCreationDate(LocalDate.now());
        persist(PersistAction.CREATE, "Employee Benefit Created Successfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Employee Benefit Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Employee Benefit Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EmployeeBenefit> getItems() {
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

    public EmployeeBenefit getEmployeeBenefit(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<EmployeeBenefit> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EmployeeBenefit> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EmployeeBenefit.class)
    public static class EmployeeBenefitControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeBenefitController controller = (EmployeeBenefitController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeBenefitController");
            return controller.getEmployeeBenefit(getKey(value));
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
            if (object instanceof EmployeeBenefit) {
                EmployeeBenefit o = (EmployeeBenefit) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EmployeeBenefit.class.getName()});
                return null;
            }
        }

    }

}
