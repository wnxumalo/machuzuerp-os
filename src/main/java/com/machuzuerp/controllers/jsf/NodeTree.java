package com.machuzuerp.controllers.jsf;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ViewScoped
@Named("nodeTree")
public class NodeTree implements Serializable {

    private TreeNode root;
    
    private String name;
    private String uk;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);
        TreeNode invoice = new DefaultTreeNode("Invoice", root);
        TreeNode receipt = new DefaultTreeNode("Receipt", root);
        TreeNode statement = new DefaultTreeNode("Statement", root);
        TreeNode quotation = new DefaultTreeNode("Quotation", root);

        TreeNode invDash = new DefaultTreeNode("Dashboard", invoice);        
        TreeNode recDash = new DefaultTreeNode("Dashboard", receipt);        
        TreeNode staDash = new DefaultTreeNode("Dashboard", statement);        
        TreeNode quoDash = new DefaultTreeNode("Dashboard", quotation);        

    }

    public TreeNode getRoot() {
        return root;
    }
    
    public void onNodeSelect(NodeSelectEvent event) {

        uk = (String) event.getComponent().getAttributes().get("uk");
        name = (String) event.getComponent().getAttributes().get("name");
        
        try {
            if (event.getTreeNode().getParent().getData().equals("Invoice")) {                
                if (event.getTreeNode().getData().equals("Dashboard")) {               
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(),
                                "null", "/accounting/billing/invoice/dashboard.xhtml?uk="+ uk +"&name=" + name);
                }
           } else if (event.getTreeNode().getParent().getData().equals("Receipt")) {                
                if (event.getTreeNode().getData().equals("Dashboard")) {               
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(),
                                "null", "/accounting/billing/receipt/dashboard.xhtml?uk="+ uk +"&name=" + name);
                }
           } else if (event.getTreeNode().getParent().getData().equals("Statement")) {                
                if (event.getTreeNode().getData().equals("Dashboard")) {               
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(),
                                "null", "/accounting/billing/billingStatement/dashboard.xhtml?uk="+ uk +"&name=" + name);
                }
           } else if (event.getTreeNode().getParent().getData().equals("Quotation")) {                
                if (event.getTreeNode().getData().equals("Dashboard")) {               
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(),
                                "null", "/accounting/billing/quotation/dashboard.xhtml?uk="+ uk +"&name=" + name);
                }
           }

        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        //path = fc.getExternalContext().getApplicationContextPath();
        // path = fc.getExternalContext().getRealPath("\\");
        this.name = params.get("name");
        this.uk = params.get("uk");
    }
}
