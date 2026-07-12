package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jpa.CitiesFacade;
import com.machuzuerp.entities.Cities;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

@Named("citiesController")
@SessionScoped
public class CitiesController implements Serializable {

    private Cities current;
    private DataModel items = null;
    @EJB
    private com.machuzuerp.controllers.jpa.CitiesFacade ejbFacade;
    private int selectedItemIndex;
    
    private List<Cities> countries;
    private String country;
    
    TreeSet<String> citiesFinal;

    public CitiesController() {
    }

    public Cities getSelected() {
        if (current == null) {
            current = new Cities();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CitiesFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareCreate() {
        current = new Cities();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
          //  JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitiesCreated"));
            return prepareCreate();
        } catch (Exception e) {
           // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cities) getItems().getRowData();
     
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitiesUpdated"));
            return "View";
        } catch (Exception e) {
  //          JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cities) getItems().getRowData();
    
        performDestroy();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitiesDeleted"));
        } catch (Exception e) {
  //          JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
                
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public Cities getCity(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    public TreeSet<String> getCountries() {
        countries = ejbFacade.findAll();  
        HashSet<Cities> hset = new HashSet<Cities>(countries); 
        HashSet<String> countries = new HashSet<String>();         
        
        hset.forEach(country -> {
            countries.add(country.getCountry());
        });
        
        TreeSet<String> countriesFinal = new TreeSet<String>(countries); 

        return countriesFinal;
    }

    public void onCountryChange(String country) {
        getCities(country);
    }

    public TreeSet<String> getCities(String country) {
        countries = ejbFacade.getCities(country);
        HashSet<Cities> hset = new HashSet<Cities>(countries); 
        HashSet<String> cities = new HashSet<String>();         
        
        hset.forEach(city -> {
            cities.add(city.getCity());
        });       

        citiesFinal = new TreeSet<String>(cities); 

        return citiesFinal;
    }
    
    public TreeSet<String> getCitiesList() {
        return citiesFinal;
    }
    
    public void setCities(TreeSet<String> citiesFinal) {
        this.citiesFinal = citiesFinal;
    }

    public void setCountries(List<Cities> countries) {
        this.countries = countries;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    @FacesConverter(forClass = Cities.class)
    public static class CitiesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CitiesController controller = (CitiesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "citiesController");
            return controller.getCity(getKey(value));
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
            if (object instanceof Cities) {
                Cities o = (Cities) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cities.class.getName());
            }
        }

    }

}
