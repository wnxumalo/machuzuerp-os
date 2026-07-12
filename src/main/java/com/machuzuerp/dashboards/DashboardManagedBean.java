package com.machuzuerp.dashboards;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@SessionScoped
@Named
public class DashboardManagedBean implements Serializable {
	private DashboardModel model;

	public DashboardManagedBean() {
		// Initialize the dashboard model
		this.model = new DefaultDashboardModel();
		// Initialize the dashboard column #1
		DashboardColumn column1 = new DefaultDashboardColumn();
		// Initialize the dashboard column #2
		DashboardColumn column2 = new DefaultDashboardColumn();
		// Initialize the dashboard column #3
		DashboardColumn column3 = new DefaultDashboardColumn();

		// Add widgets into column1
		column3.addWidget("Stakeholders");
		column3.addWidget("Inventory");
                column1.addWidget("Projects");
		column2.addWidget("Services");
		column2.addWidget("Suppliers");		
                column2.addWidget("HR");		
		column1.addWidget("AP");
                column1.addWidget("AR");
                column3.addWidget("Collaboration");
                column3.addWidget("Access");

		// Add columns into your model
		this.model.addColumn(column1);
		this.model.addColumn(column2);
		this.model.addColumn(column3);

	}

	public DashboardModel getModel() {
		return model;
	}

	public void setModel(DashboardModel model) {
		this.model = model;
	}

	public void handleReorder(DashboardReorderEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Reordered: " + event.getWidgetId());
		message.setDetail("Item index: " + event.getItemIndex()+ ", Column index: " + event.getColumnIndex() 
				+ ", Sender index: " + event.getSenderColumnIndex());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
