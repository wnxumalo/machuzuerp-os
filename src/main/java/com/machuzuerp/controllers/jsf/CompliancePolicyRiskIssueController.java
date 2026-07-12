package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.ComplianceControlFacade;
import com.machuzuerp.controllers.jpa.CompliancePolicyRiskControlFacade;
import com.machuzuerp.controllers.jpa.CompliancePolicyRiskIssueFacade;
import com.machuzuerp.controllers.jpa.PolicyFacade;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.entities.ComplianceControl;
import com.machuzuerp.entities.CompliancePolicy;
import com.machuzuerp.entities.CompliancePolicyRisk;
import com.machuzuerp.entities.ComplianceRiskControl;
import com.machuzuerp.entities.ComplianceRiskIssue;

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

@Named("compliancePolicyRiskIssueController")
@SessionScoped
public class CompliancePolicyRiskIssueController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.CompliancePolicyRiskIssueFacade ejbFacade;
    private List<ComplianceRiskIssue> items = null;
    private ComplianceRiskIssue selected;

    public CompliancePolicyRiskIssueController() {
       
    }

    public ComplianceRiskIssue getSelected() {
        return selected;
    }

    public void setSelected(ComplianceRiskIssue selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompliancePolicyRiskIssueFacade getFacade() {
        return ejbFacade;
    }

    public ComplianceRiskIssue prepareCreate() {
        selected = new ComplianceRiskIssue();
        initializeEmbeddableKey();

        return selected;
    }

    public void create(Long compliancePolicyRisk) {                
        selected.setRisk(compliancePolicyRisk);
        persist(PersistAction.CREATE, "Policy Risk Issue Created Succesfully");
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

    public List<ComplianceRiskIssue> getItems() {
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

    public ComplianceRiskIssue getComplianceRiskIssue(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ComplianceRiskIssue> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ComplianceRiskIssue> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CompliancePolicy.class)
    public static class PolicyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompliancePolicyRiskControlController controller = (CompliancePolicyRiskControlController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compliancePolicyRiskControlController");
            return controller.getComplianceRiskControl(getKey(value));
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
            if (object instanceof CompliancePolicy) {
                CompliancePolicy o = (CompliancePolicy) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CompliancePolicy.class.getName()});
                return null;
            }
        }

    }

}