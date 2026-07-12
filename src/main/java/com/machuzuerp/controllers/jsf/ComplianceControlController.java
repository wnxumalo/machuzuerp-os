package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.ComplianceControlFacade;
import com.machuzuerp.controllers.jpa.PolicyFacade;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.entities.ComplianceControl;
import com.machuzuerp.entities.CompliancePolicy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

@Named("complianceControlController")
@SessionScoped
public class ComplianceControlController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.ComplianceControlFacade ejbFacade;
    private List<ComplianceControl> items = null;
    private ComplianceControl selected;
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    private String filesURL;
    
        SimpleDateFormat stf1 = new SimpleDateFormat("hh:mm:ss");


    public ComplianceControlController() {
       
    }

    public ComplianceControl getSelected() {
        return selected;
    }

    public void setSelected(ComplianceControl selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ComplianceControlFacade getFacade() {
        return ejbFacade;
    }

    public ComplianceControl prepareCreate() {
        selected = new ComplianceControl();
        initializeEmbeddableKey();

        return selected;
    }
    
    public String getFilesURL() {
        return filesURL;
    }

    public void setFilesURL(String filesURL) {
        this.filesURL = filesURL;
    }

    public void create(Long policyId) {                
        selected.setCompliancePolicy(policyId);
        persist(PersistAction.CREATE, "Control Created Succesfully");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        try {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('ComplianceControlUploadDialog').show();");     
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
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

    public List<ComplianceControl> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException, SQLException {

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    
            url = request.getRequestURL().toString();
            endIndex = url.lastIndexOf("faces");
            if (endIndex != -1) {
                newstr = url.substring(0, endIndex);
            }        

            filesURL = newstr + "machuzuerpfiles/";

            copyFile(event.getFile().getFileName(), event.getFile().getInputStream(), (String) event.getComponent().getAttributes().get("folder"), request.getSession());
       
    }
    
    public void copyFile(String fileName, InputStream in, String path, HttpSession session) {
        try {

            String directoryName = new File("../docroot").getAbsolutePath() + "/MachuzuERP/files/";

            String fTime = stf1.format(new Date()).replace(":", "");
            
            endIndex = fileName.lastIndexOf(".");
            String fileType = "";
            
            for (int x=endIndex;x<fileName.length();x++) {
                fileType += fileName.charAt(x);
            }

            String fName = "";
            for (int x=0;x<fileName.length();x++) {
                if (fileName.charAt(x) == '.') {
                    x=fileName.length();                    
                } else {
                    fName += fileName.charAt(x);                    
                }
            }

            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            } 

            Path fullPath = Paths.get(new File("../docroot").getAbsolutePath() + "/MachuzuERP/files/" + fName + "_" + fTime + fileType);
            //Path fullPath = Paths.get(new File("/Wandile").getAbsolutePath() + "/Wandile/Java 14/wildfly-24.0.1.Final/wildfly-24.0.1.Final/docroot/MaestroIT/files/" + fName + "_" + fTime + fileType);

            try {
                Files.createFile(fullPath);
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File("../docroot").getAbsolutePath() + "/MachuzuERP/files/" + fName + "_" + fTime + fileType);
            //OutputStream out = new FileOutputStream(new File("/Wandile").getAbsolutePath() + "/Wandile/Java 14/wildfly-24.0.1.Final/wildfly-24.0.1.Final/docroot/MaestroIT/files/" + fName + "_" + fTime + fileType);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
                       
            fName += "_" + fTime + fileType;
            
            //String ret = fp.saveCollaborationFile(filesURL, fullPath.toString(), fName, fileType);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "SAVED"));           
                    
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);
                //FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/file-upload-details.xhtml?max=" + fp.getMaxFiles() + "&fName=" + fName);
            }


        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
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

    public ComplianceControl getPolicy(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ComplianceControl> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ComplianceControl> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CompliancePolicy.class)
    public static class PolicyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompliancePolicyController controller = (CompliancePolicyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "policyController");
            return controller.getPolicy(getKey(value));
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