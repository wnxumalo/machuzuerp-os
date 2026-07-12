package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.EmployeeFacade;
import com.machuzuerp.controllers.jpa.EmployeeLeaveFacade;
import com.machuzuerp.entities.Organisation;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.OrganisationFacade;
import com.machuzuerp.entities.dto.EmployeeDTO;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.SelectEvent;

@Named("organisationController")
@SessionScoped
public class OrganisationController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.OrganisationFacade ejbFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeFacade ejbEmployeeFacade;

    private List<Organisation> items = null;
    private Organisation selected;

    @PostConstruct
    public void init() {
/*        employees = new ArrayList<EmployeeDTO>();

        employees.clear();
        employees.add(new EmployeeDTO(Long.parseLong("0"), "N/A", "N/A", "N/A", "N/A"));

        String decryptedCipherText = "";
        for (Employee emp : getFacade().findAll()) {

            decryptedCipherText = AESPKCS5Padding.decrypt(emp.getCellphone());
            employees.add(new EmployeeDTO(emp.getId(), emp.getEmpName(), emp.getSurname(), emp.getPosition(), decryptedCipherText));
        }
*/
    }


    public OrganisationController() {
    }

    public Organisation getSelected() {
        return selected;
    }

    public void setSelected(Organisation selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OrganisationFacade getFacade() {
        return ejbFacade;
    }
    
    private EmployeeFacade getEmployeeFacade() {
        return ejbEmployeeFacade;
    }

    public Organisation prepareCreate() {
        selected = new Organisation();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Organisation Created Successfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Organisation Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Organisation Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Organisation> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }            

    public void onOrganisationRowSelect(SelectEvent event) throws IOException {
        selected.setEmployees(getEmployeeFacade().getOrganisationEmployees(selected.getId()));
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

    public Organisation getOrganisation(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Organisation> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Organisation> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Organisation.class)
    public static class OrganisationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrganisationController controller = (OrganisationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "organisationController");
            return controller.getOrganisation(getKey(value));
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
            if (object instanceof Organisation) {
                Organisation o = (Organisation) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Organisation.class.getName()});
                return null;
            }
        }

    }

}
