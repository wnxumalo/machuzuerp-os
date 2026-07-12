package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.WorkflowProcessing;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.diagram.Diagram;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

@Named("viewFlowChart")
@RequestScoped
public class ViewFlowChart implements Serializable {

    private DefaultDiagramModel model;
    final WorkflowProcessing wp = new WorkflowProcessing();
    
    private List<Element> element = new ArrayList<Element>();

    private Element val = new Element();
    
    public ViewFlowChart() {
        
        List<UIComponent> viewRootChildren = FacesContext.getCurrentInstance().getViewRoot().getChildren();
            for (UIComponent child : viewRootChildren) {
                List<UIComponent> viewChildChildren = child.getChildren();
                for (UIComponent child1 : viewChildChildren) {
                    List<UIComponent> viewChildChildren1 = child1.getChildren();
                    System.out.println("111: " + child1.getClientId());
                    if (child1.getClientId().equals("wfForm:diagWorkflow")) {
                                        System.out.println("WORKFLOW: " + child1.getClientId());
                                        
                                        Diagram diag = new Diagram();
                                        diag.setId("diagWorkflow");
                                        diag.setValue(this.model);
                                        diag.setStyleClass("ui-widget-content");
                                        
                                        child1.getChildren().add(diag);
                                        //<!--p:diagram id="diagWorkflow" value="#{workflowFlowChartView.model}" style="height:600px" styleClass="ui-widget-content"/-->
                                    }
                    for (UIComponent child2 : viewChildChildren1) {
                        List<UIComponent> viewChildChildren2 = child2.getChildren();
                                
                    System.out.println("222: " + child2.getClientId());
                        }
                    }
                }

    }

    public DefaultDiagramModel getModel() {                 
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
    }

    public void setVal(Element val) {
        this.val = val;
    }
    
    public Element getVat() {
        return val;
    }
}
