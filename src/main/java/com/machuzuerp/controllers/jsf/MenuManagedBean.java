package com.machuzuerp.controllers.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

@SessionScoped
@Named
public class MenuManagedBean implements Serializable {
	private MenuModel menu = new DefaultMenuModel();
        DefaultSubMenu file = new DefaultSubMenu();

	public MenuManagedBean(){
		// Create submenu
		
                file.setLabel("File");
                //file.s
		// Create submenu
		DefaultSubMenu help = new DefaultSubMenu();
                help.setLabel("Open");
		// Create menuitem
		DefaultMenuItem open = new DefaultMenuItem();
                open.setTitle("Open");
		// Create menuitem
		/*DefaultMenuItem edit = new DefaultMenuItem("Edit");
		// Create menuitem
		DefaultMenuItem exit = new DefaultMenuItem("Exit");

		// Create menuitem
		DefaultMenuItem about = new DefaultMenuItem("About Primefaces");
		// Create menuitem
		DefaultMenuItem contact = new DefaultMenuItem("Contact Us");
		// Create menuitem
		DefaultMenuItem helpMenuItem = new DefaultMenuItem("Help");*/	
                
                List<MenuElement> list = new ArrayList<MenuElement>();
                list.add(file);
                list.add(help);

		// Determine menuitem action
		open.setCommand("#{menuManagedBean.openAction}");

		// Associate menuitem with submenu
		file.setElements(list);
		/*file.addElement(edit);
		file.addElement(new DefaultSeparator());
		file.addElement(exit);

		help.addElement(about);
		help.addElement(contact);
		help.addElement(new DefaultSeparator());
		help.addElement(helpMenuItem);*/

		// Associate submenu with menu
		menu.equals(list);
                
		//menu.addElement(help);
	}

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public String openAction(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Open action has activiated asynchrounsly !"));
		return "";
	}
}
