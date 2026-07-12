package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Inventory;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.PaginationHelper;
import com.machuzuerp.controllers.jpa.InventoryFacade;
import com.machuzuerp.controllers.jpa.InventoryStorageFacade;
import com.machuzuerp.controllers.jpa.QualityAssuranceFacade;
import com.machuzuerp.entities.ReceiptItems;
import com.machuzuerp.entities.dto.InventoryDTO;
import com.machuzuerp.jdbc.CustomerProcessing;
import com.machuzuerp.jdbc.ReceiptProcessing;
import com.machuzuerp.jdbc.SupplierProcessing;
import java.awt.Color;
import java.io.IOException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONException;
import org.primefaces.shaded.json.JSONObject;

@Named("inventoryController")
@SessionScoped
public class InventoryController implements Serializable {

    private Inventory current;
    private List<Inventory> items = null;
    @EJB
    private com.machuzuerp.controllers.jpa.InventoryFacade ejbFacade;
    
    @EJB
    private com.machuzuerp.controllers.jpa.QualityAssuranceFacade ejbQualityAssuranceFacade;
    
    @EJB
    private com.machuzuerp.controllers.jpa.InventoryStorageFacade ejbStorageFacade;
    
    private String filterInventory;
    
    private List<Inventory> inventory = new ArrayList<Inventory>();
    private List<InventoryDTO> inventoryList = new ArrayList<InventoryDTO>(); 
    private InventoryDTO inventoryDTO = new InventoryDTO();
    
    private List<Inventory> filteredInventory = new ArrayList<Inventory>();

    CustomerProcessing cp = new CustomerProcessing();
    SupplierProcessing sp = new SupplierProcessing();
    ReceiptProcessing rp = new ReceiptProcessing();
    
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
    
    private String selectedTitle;   
        
    public InventoryController() {
    }
    
    @PostConstruct
    public void init() {
        populateInventoryDTO();
    }
    
    public void returnGoods(List<ReceiptItems> receiptItems, int recnum) throws SQLException {
        
        Long iId = null;
        for (ReceiptItems item : receiptItems) {
            iId = Long.parseLong(item.getInvId()+"");
            current  = getFacade().find(iId);
            current.setId(current.getId());
            current.setCode(current.getCode());
            current.setDescription(current.getDescription());
            current.setClientId(current.getClientId());
            current.setCost(current.getCost());
            current.setMarkup(current.getMarkup());
            current.setSellingPrice(current.getSellingPrice());
            current.setReorderLevel(current.getReorderLevel());
            current.setMarkup(current.getMarkup());
            current.setAtHand(current.getAtHand() + item.getQuantity());
            current.setSize(current.getSize());
            current.setColor(current.getColor());
            current.setStatus(current.getStatus());   
            
            getFacade().edit(current);            
            
        }
        
        rp.setGoodsReturned(recnum);
        
        JsfUtil.addSuccessMessage("Goods Returned Successfully");
    }

    public void populateInventoryDTO() {
    
        inventory = getFacade().findAll();
        
        inventory.forEach(item -> {
            
            try {
                cp.getCustomer(item.getClientId().toString());

                if (item.getReorderLevel() > item.getAtHand()) {
                    inventoryList.add(new InventoryDTO(item.getId(), item.getCode(), item.getDescription(), item.getClientId(), cp.customerName + " " + cp.customerSurname, item.getCost(),
                    item.getSellingPrice(), item.getReorderLevel(), item.getMarkup(), item.getAtHand(), item.getSize(), item.getColor(), null, sp.description, "red", "white", item.getExpiryDates()));
                } else {
                    inventoryList.add(new InventoryDTO(item.getId(), item.getCode(), item.getDescription(), item.getClientId(), cp.customerName + " " + cp.customerSurname, item.getCost(),
                    item.getSellingPrice(), item.getReorderLevel(), item.getMarkup(), item.getAtHand(), item.getSize(), item.getColor(), null, sp.description, "", "black", item.getExpiryDates()));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
                                
        });

    }

    public String getSelectedTitle() {        
        return selectedTitle;
    }
    
    public void setSelectedTitle(String selectedTitle) {
        this.selectedTitle = selectedTitle;
    }        
    
    public Inventory getCurrent() {        
        return current;
    }
    
    public void setCurrent(Inventory current) {
        this.current = current;
    }

    
    public InventoryDTO getInventoryDTO() {        
        return inventoryDTO;
    }
    
    public void setInventoryDTO(InventoryDTO inventoryDTO) {
        this.inventoryDTO = inventoryDTO;
    }

    private InventoryFacade getFacade() {
        return ejbFacade;
    }
    
    private InventoryStorageFacade getStorageFacade() {
        return ejbStorageFacade;
    }
    
    private QualityAssuranceFacade getQualityAssuranceFacade() {
        return ejbQualityAssuranceFacade;
    }
    
    public List<InventoryDTO> getInventoryList() {                        
        return inventoryList;
    }
    
    public void setInventoryList(List<InventoryDTO> inventoryList) {
        this.inventoryList = inventoryList;
    }
    
    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }
    
    public List<Inventory> getFilteredInventory() {
        return filteredInventory;
    }

    public void setFilteredInventory(List<Inventory> inventory) {
        this.filteredInventory = filteredInventory;
    }

    public String getFilterInventory() {
        return filterInventory;
    }

    public void setFilterInventory(String filterInventory) {
        this.filterInventory = filterInventory;
    }

    public String filterDates(String expiryDate) {
System.out.println("DATE000: " + expiryDate);
        // get existing expiry data, add new and save json
        try {

            JSONArray jsonRules = new JSONArray(expiryDate);
            JSONObject obj = null;
            JSONObject obj1 = null;
            String year = "";
            String month = "";
            String day = "";
            for (int i=0; i<jsonRules.length();i++) {
                obj = jsonRules.getJSONObject(i);
     
                year = ""+obj.getJSONObject("expiryDate").get("year");                
                month = ""+obj.getJSONObject("expiryDate").get("month");                
                day = ""+obj.getJSONObject("expiryDate").get("day");                

                if (Integer.parseInt(month) < 10)
                    month = "0" + month;
                
                expiryDate = year + "-" + month + "-" + day;                
            }     

        } catch (NumberFormatException | JSONException | IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("JSON ERR INVENTORY ADD: " + e);
            expiryDate = sdf.format(new Date());
        }

        return expiryDate;
    }
    
    public void onBarcodeChange(String barcode) {
        
        for (InventoryDTO code : inventoryList) {
            if (code.getCode().equals(barcode)) {
                
            }
        }        

    }

    public void onInventoryFilter(String filterInventory) throws ParseException {

        inventoryList.clear();
        switch (filterInventory) {
            case "1":
            //inventory = getFacade().getInventoryFilter("1", 1);
                inventory.stream().filter(inv -> ((inv.getReorderLevel() > inv.getAtHand()))).forEachOrdered(inv -> {
                    inventoryList.add(new InventoryDTO(inv.getId(), inv.getCode(), inv.getDescription(), inv.getClientId(), "", inv.getCost(), inv.getSellingPrice(), inv.getReorderLevel(), inv.getMarkup(), inv.getAtHand(), inv.getSize(), inv.getColor(), null,"","","", inv.getDescription()));
                    
                    setSelectedTitle("Inventory Reorder Level");
            });
            break;

            case "2":
                for (Inventory inv : inventory) {
                    
                    if ((sdf.parse(filterDates(inv.getExpiryDates())).after(new Date()))) {
                        inventoryList.add(new InventoryDTO(inv.getId(), inv.getCode(), inv.getDescription(), inv.getClientId(), "", inv.getCost(), inv.getSellingPrice(), inv.getReorderLevel(), inv.getMarkup(), inv.getAtHand(), inv.getSize(), inv.getColor(), null,"","","",inv.getExpiryDates()));
                    }                    
                } 
                setSelectedTitle("Expired Inventory");
            break;
            case "3":
                for (Inventory inv : inventory) {
                    
                    if ((inv.getStatus().equals("Active"))) {
                        inventoryList.add(new InventoryDTO(inv.getId(), inv.getCode(), inv.getDescription(), inv.getClientId(), "", inv.getCost(), inv.getSellingPrice(), inv.getReorderLevel(), inv.getMarkup(), inv.getAtHand(), inv.getSize(), inv.getColor(), null,"","","",inv.getExpiryDates()));
                    }
                    
                }   
                setSelectedTitle("Active Inventory");
            break;
            case "4":
                for (Inventory inv : inventory) {
                    
                    if ((inv.getStatus().equals("Inactive"))) {
                        inventoryList.add(new InventoryDTO(inv.getId(), inv.getCode(), inv.getDescription(), inv.getClientId(), "", inv.getCost(), inv.getSellingPrice(), inv.getReorderLevel(), inv.getMarkup(), inv.getAtHand(), inv.getSize(), inv.getColor(), null,"","","",inv.getExpiryDates()));
                    }
                    
                }   
                
                setSelectedTitle("Inactive Inventory");
            break;
            default:
                inventory.forEach(item -> {
                    
                    try {
                        cp.getCustomer(item.getClientId().toString());
                        
                        if (item.getReorderLevel() > item.getAtHand()) {
                            inventoryList.add(new InventoryDTO(item.getId(), item.getCode(), item.getDescription(), item.getClientId(), cp.customerName + " " + cp.customerSurname, item.getCost(),
                                    item.getSellingPrice(), item.getReorderLevel(), item.getMarkup(), item.getAtHand(), item.getSize(), item.getColor(), null, sp.description, "red", "white",item.getExpiryDates()));
                        } else {
                            inventoryList.add(new InventoryDTO(item.getId(), item.getCode(), item.getDescription(), item.getClientId(), cp.customerName + " " + cp.customerSurname, item.getCost(),
                                    item.getSellingPrice(), item.getReorderLevel(), item.getMarkup(), item.getAtHand(), item.getSize(), item.getColor(), null, sp.description, "", "black",item.getExpiryDates()));
                        }
                    } catch (SQLException ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                    
                }); 
                
                setSelectedTitle("All Inventory");
                
                break;
        }               

        
        
    }

    public void onRowSelect(SelectEvent event) throws IOException {
System.out.println("111");
        if(current == null) {
            current = new Inventory();
        }
        System.out.println("222");
        current.setQualityAssuranceList(getQualityAssuranceFacade().getQualityAssuranceList(inventoryDTO.getId()));        
        System.out.println("333");
        current.setInventoryStorageList(getStorageFacade().getInventoryStorageList(inventoryDTO.getId()));        
        System.out.println("444");
    }

    public String prepareCreate() {
        current = new Inventory();
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);  
            
            JsfUtil.addSuccessMessage("Inventory Created Succesfully");
            
            populateInventoryDTO();
            
            //PrimeFaces current = PrimeFaces.current();
            //current.executeScript("PF('dlInventory').clearFilters();");
            
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "PersistenceErrorOccured");
            return null;
        }
    }

    public String update() {
        try {
            
            current  = getFacade().find(inventoryDTO.getId());
            current.setId(inventoryDTO.getId());
            current.setCode(inventoryDTO.getCode());
            current.setDescription(inventoryDTO.getDescription());
            current.setClientId(inventoryDTO.getClientId());
            current.setCost(inventoryDTO.getCost());
            current.setMarkup(inventoryDTO.getMarkup());
            current.setSellingPrice(inventoryDTO.getSellingPrice());
            current.setReorderLevel(inventoryDTO.getReorderLevel());
            current.setMarkup(inventoryDTO.getMarkup());
            current.setAtHand(inventoryDTO.getAtHand());
            current.setSize(inventoryDTO.getSize());
            current.setColor(inventoryDTO.getColor());
            current.setStatus(inventoryDTO.getStatus());   
            
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Inventory Updated Successfully");
            
            //PrimeFaces current = PrimeFaces.current();
            //current.executeScript("PF('dlInventory').clearFilters();");
        
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Persistence Error Occured");
            return null;
        }
    }

    public String destroy() {
        performDestroy();
        return "List";
    }

    private void performDestroy() {
        try {
            current = getFacade().find(inventoryDTO.getId());
            getFacade().remove(current);            
            
            populateInventoryDTO();
            
            //PrimeFaces current = PrimeFaces.current();
            //current.executeScript("PF('dlInventory').clearFilters();");
            
            JsfUtil.addSuccessMessage("Inventory Deleted Successfully");
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Persistence Error Occured");
        }
    }

    public List<Inventory> getItems() {
        return getFacade().findAll();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Inventory getInventory(java.lang.Long id) {
        return ejbFacade.find(id);
    }
    
    public void onCostExit(BigDecimal cost) {
        this.current.setCost(cost);
    }
    
    public void onSellingPriceEnter(BigDecimal markup) {

        BigDecimal divideCost = this.current.getCost().divide(new BigDecimal(100));
        BigDecimal multiple = divideCost.multiply(markup);
        BigDecimal finalAmount = multiple.add(this.current.getCost());

        current.setSellingPrice(finalAmount);
    }

    @FacesConverter(forClass = Inventory.class)
    public static class InventoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InventoryController controller = (InventoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inventoryController");
            return controller.getInventory(getKey(value));
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
            if (object instanceof Inventory) {
                Inventory o = (Inventory) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Inventory.class.getName());
            }
        }

    }

}
