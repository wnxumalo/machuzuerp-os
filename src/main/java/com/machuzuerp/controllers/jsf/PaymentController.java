package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.PaymentFacade;
import com.machuzuerp.entities.Payment;
import com.machuzuerp.controllers.jpa.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.entities.Supplier;
import com.machuzuerp.entities.dto.PurchaseOrderDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.servlet.http.HttpSession;

@Named("paymentController")
@SessionScoped
public class PaymentController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.PaymentFacade ejbFacade;
    private List<Payment> items = null;
    private Payment selected;
   
    public PaymentController() {
    }

    public Payment getSelected() {
        return selected;
    }

    public void setSelected(Payment selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PaymentFacade getFacade() {
        return ejbFacade;
    }

    public Payment prepareCreate() {
        selected = new Payment();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(HttpSession session, PurchaseOrderDTO purchaseOrderDTO, Supplier supplier) {
        selected.setPurchaseOrderId(purchaseOrderDTO.getId());
        selected.setSupplierId(supplier.getId());
        selected.setCreationDate(new Date());
        selected.setStatus("1");
    
        persist(PersistAction.CREATE, "Payment Created Successfully");
    }

    public void update() {
        persist(PersistAction.UPDATE, "Payment Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Payment Deleted Successfully");
    }

    public List<Payment> getItems() {
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

    public Payment getPayment(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Payment> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Payment> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Payment.class)
    public static class PaymentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PaymentController controller = (PaymentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "paymentController");
            return controller.getPayment(getKey(value));
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
            if (object instanceof Payment) {
                Payment o = (Payment) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Payment.class.getName()});
                return null;
            }
        }

    }

}
