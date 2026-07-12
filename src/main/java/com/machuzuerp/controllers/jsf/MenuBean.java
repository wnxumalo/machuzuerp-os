/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@RequestScoped
@Named
public class MenuBean {
   
private MenuModel model;
public MenuBean() {
  
model = new DefaultMenuModel();

//First submenu
//DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");
//****DefaultMenuItem item = new DefaultMenuItem("External");
//item.setUrl("http://www.primefaces.org");
//item.setIcon("ui-icon-home");
//firstSubmenu.addElement(item);
//model.addElement(firstSubmenu);
//Second submenu
//DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");
//******item = new DefaultMenuItem("Save");
//item.setIcon("ui-icon-disk");
//item.setCommand("Save");

//secondSubmenu.addElement(item);
//*****DefaultMenuItem item1 = new DefaultMenuItem("Delete");
//item.setIcon("ui-icon-close");
//item.setCommand("#{menuBean.delete}");
//item.setAjax(false);
//secondSubmenu.addElement(item);
//******DefaultMenuItem item2 = new DefaultMenuItem("Redirect");
//item.setIcon("ui-icon-search");
//item.setCommand("#{menuBean.redirect}");
//secondSubmenu.addElement(item);
/*model.addElement(item);
model.addElement(item1);
model.addElement(item2);*/
}
public MenuModel getModel() { return model; }
}
    

