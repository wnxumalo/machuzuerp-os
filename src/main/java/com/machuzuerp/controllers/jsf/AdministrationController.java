package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Administration;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.AdministrationFacade;
import com.machuzuerp.controllers.jpa.AuditLoggingFacade;
import com.machuzuerp.controllers.jpa.EmployeeFacade;
import com.machuzuerp.entities.AuditLogging;
import com.machuzuerp.entities.Employee;
import java.io.IOException;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.wildfly.security.auth.server.IdentityCredentials;
import org.wildfly.security.credential.Credential;
import org.wildfly.security.credential.PasswordCredential;
import org.wildfly.security.credential.store.CredentialStore;
import org.wildfly.security.credential.store.CredentialStore.CredentialSourceProtectionParameter;
import org.wildfly.security.credential.store.CredentialStore.ProtectionParameter;
import org.wildfly.security.credential.store.CredentialStoreException;
import org.wildfly.security.credential.store.WildFlyElytronCredentialStoreProvider;
import org.wildfly.security.evidence.PasswordGuessEvidence;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.WildFlyElytronPasswordProvider;
import org.wildfly.security.password.interfaces.ClearPassword;
import com.machuzu.cryptography.CredentialStoreMain;

@Named("administrationController")
@SessionScoped
public class AdministrationController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.AdministrationFacade ejbFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeFacade ejbEmployeeFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.AuditLoggingFacade auditFacade;

    private List<Administration> items = null;
    private Administration selected;

    private String fullname;
    private String cellphone;
    private String email;
    private String accessEmail;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private String uMsg;
    
    private String copyright;

    Systems systems = new Systems();

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    CredentialStoreMain csmain = new CredentialStoreMain();

    // private static final ServiceName SERVICE_NAME_CRED_STORE = ServiceName.of("org", "wildfly", "security", "credential-store");
    public AdministrationController() {
        setEmail("main@machuzu.com");
        setPassword("12345");
    }

    public Administration getSelected() {
        return selected;
    }

    public void setSelected(Administration selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AuditLoggingFacade getAuditFacade() {
        return auditFacade;
    }

    private AdministrationFacade getFacade() {
        return ejbFacade;
    }

    private EmployeeFacade getEmployeeFacade() {
        return ejbEmployeeFacade;
    }

    public String loginUser(HttpSession session) throws NoSuchAlgorithmException {

        String clearTextPswd = null;
        Provider CREDENTIAL_STORE_PROVIDER = new WildFlyElytronCredentialStoreProvider();
        Provider PASSWORD_PROVIDER = new WildFlyElytronPasswordProvider();
        Security.addProvider(PASSWORD_PROVIDER);
        Password wfcPassword = null;
        final String ALIAS = "dbPassword";

        Password storePassword = ClearPassword.createRaw(ClearPassword.ALGORITHM_CLEAR, "StorePassword".toCharArray());
        ProtectionParameter protectionParameter = new CredentialSourceProtectionParameter(IdentityCredentials.NONE.withCredential(new PasswordCredential(storePassword)));

        CredentialStore credentialStore = CredentialStore.getInstance("KeyStoreCredentialStore", CREDENTIAL_STORE_PROVIDER);

        Map<String, String> configuration = new HashMap<>();
        configuration.put("location", "c:/docroot/mystore.cs");
        // configuration.put("create", "true");

        try {
            credentialStore.initialize(configuration, protectionParameter);

            try {

                //csmain.populateCredentialStore(credentialStore);
            } catch (Exception ex) {
                Logger.getLogger(AdministrationController.class.getName()).log(Level.SEVERE, null, ex);
            }

            String aliasPwd = "";

            System.out.println(credentialStore.isInitialized());

            System.out.println("************************************");
            System.out.println("Current Aliases: -");
            for (String aliasExist : credentialStore.getAliases()) {
                System.out.print(" - ");
                System.out.println("ALIAS: " + aliasExist);
                if (aliasExist.equals(ALIAS)) {
                    aliasPwd = aliasExist;
                }
            }
            System.out.println("************************************");

            if (aliasPwd.equals(ALIAS)) {
                wfcPassword = credentialStore.retrieve(ALIAS, PasswordCredential.class).getPassword();
            }
        } catch (CredentialStoreException ex) {
            Logger.getLogger(AdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String page = "/index";
        try {

            if (systems.Login(email, password) == true) {

                if (password.equals("temppass")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Change Password", "Kindly create a new password."));
                    page = "/v4/hr/employee/employee";
                } else {

                    selected = getFacade().getLoginEmployeeAdministration(Long.parseLong(systems.uk));

                    session.setAttribute("username", systems.uName);
                    session.setAttribute("uk", systems.uk);

                    AuditLogging audit = new AuditLogging();
                    audit.setActionResponse("Successful");
                    audit.setAppModule("index.xhtml");
                    audit.setEmployeeId(Long.parseLong(systems.uk));
                    audit.setUserAction("Login");
                    audit.setDateCreated(LocalDate.now());
                    audit.setTimeCreated(LocalTime.now());
                    getAuditFacade().create(audit);

                    if (email.equals("administrator@machuzu.com")) {
                        page = "/v4/administration/administrator";
                    } else {
                        page = "maindashboard";
                    }
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Login", "Incorrect username and/or password."));
                uMsg = "Incorrect username and/or password.";

                page = "/index";
            }

        } catch (SQLException | ParseException ex) {
            uMsg = "" + ex;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Login", uMsg));
            System.out.println("ERROR: " + ex + ":" + uMsg);
        }

        return page;
    }

    public void logOff(HttpSession session) throws IOException {

        session.invalidate();

        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = request.getRequestURL().toString();
        uri = request.getRequestURI();
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
    }

    public void setMsg(String uMsg) {
        this.uMsg = uMsg;
    }

    public String getMsg() {
        return uMsg;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setAccessEmail(String accessEmail) {
        this.accessEmail = accessEmail;
    }

    public String getAccessEmail() {
        return accessEmail;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Administration prepareCreate() {
        selected = new Administration();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Employee employee) {
        try {

            getFacade().find(employee.getId());

            selected.setEmployeeId(employee.getId());
            persist(PersistAction.CREATE, "Administration Created Successfully");
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Saved", "First Select an Employee"));
        }

    }

    public void update() {
        persist(PersistAction.UPDATE, "Administration Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Administration Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Administration> getItems() {
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

    public Administration getAdministration(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Administration> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Administration> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Administration.class)
    public static class AdministrationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AdministrationController controller = (AdministrationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "administrationController");
            return controller.getAdministration(getKey(value));
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
            if (object instanceof Administration) {
                Administration o = (Administration) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Administration.class.getName()});
                return null;
            }
        }

    }

}