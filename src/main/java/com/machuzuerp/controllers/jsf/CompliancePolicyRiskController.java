package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.PolicyRiskFacade;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.entities.CompliancePolicy;
import com.machuzuerp.entities.CompliancePolicyRisk;

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

@Named("policyRiskController")
@SessionScoped
public class CompliancePolicyRiskController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.PolicyRiskFacade ejbFacade;
    private List<CompliancePolicyRisk> items = null;
    private CompliancePolicyRisk selected;

    public CompliancePolicyRiskController() {
       
    }

    public CompliancePolicyRisk getSelected() {
        return selected;
    }

    public void setSelected(CompliancePolicyRisk selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PolicyRiskFacade getFacade() {
        return ejbFacade;
    }

    public CompliancePolicyRisk prepareCreate() {
        selected = new CompliancePolicyRisk();
        initializeEmbeddableKey();

        return selected;
    }

    public void create(Long policy) {                
        selected.setCompliancePolicy(policy);
        persist(PersistAction.CREATE, "Policy Created Succesfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Policy Updated Succesfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Policy Deleted Succesfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CompliancePolicyRisk> getItems() {
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

    public CompliancePolicyRisk getRisk(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<CompliancePolicyRisk> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CompliancePolicyRisk> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CompliancePolicyRisk.class)
    public static class PolicyRiskControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompliancePolicyRiskController controller = (CompliancePolicyRiskController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "policyRiskController");
            return controller.getRisk(getKey(value));
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
            if (object instanceof CompliancePolicyRisk) {
                CompliancePolicyRisk o = (CompliancePolicyRisk) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CompliancePolicyRisk.class.getName()});
                return null;
            }
        }

    }

}