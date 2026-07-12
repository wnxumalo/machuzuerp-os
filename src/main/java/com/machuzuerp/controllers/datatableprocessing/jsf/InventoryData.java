/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.datatableprocessing.jsf;

import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.jdbc.InventoryProcessing;
import com.machuzuerp.jdbc.SupplierProcessing;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONException;
import org.primefaces.shaded.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.machuzu.printable.PrintPOS;
import com.machuzuerp.entities.Client;
import com.machuzuerp.jdbc.ReceiptProcessing;
import com.machuzuerp.jdbc.UserProcessing;
import java.text.SimpleDateFormat;
import org.primefaces.PrimeFaces;
import com.machuzuerp.jdbc.CustomerProcessing;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;
import java.util.HashMap;
import javax.faces.component.UIViewRoot;
import org.primefaces.component.commandbutton.CommandButton;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

@SessionScoped
@Named
public class InventoryData implements Serializable {

    /**
     * Creates a new instance of InventoryData
     */
    Connection connection;
    Statement statement;
    ResultSet results;

    private String iRecNum;
    private String code;
    private String description;
    private int supplierId;
    private String supplier;
    private int clientId;
    private String client;
    private BigDecimal cost;
    private BigDecimal sellingPrice;
    private int reorderLevel;
    private BigDecimal markup;
    private int atHand;
    private String size;
    private String color;
    private int quantity;
    private BigDecimal totCost = new BigDecimal("0");
    private BigDecimal total = new BigDecimal("0");
    BigDecimal temp = new BigDecimal("0");
    private String expiryDates;
    private LocalDate expiryDate;
    private String sKuNumber;
    private boolean printReceipt;

    private String orgNumber;
    private String orgName;
    private String orgTel;
    private String orgFax;
    private String orgEmail;
    private String orgPosAdd;
    private String orgPhysAdd;

    private String tel;
    private String cell;
    private String fax;
    private String email;
    private String vatNum;
    private String posAdd;
    private String physAdd;

    private String invDate;

    BigDecimal invCost = new BigDecimal("0");
    private BigDecimal totServCost = new BigDecimal("0");
    private BigDecimal totInvCost = new BigDecimal("0");
//    private BigDecimal totCost = new BigDecimal("0");
    private BigDecimal taxAmount = new BigDecimal("0");

    private BigDecimal amtPaid = new BigDecimal("0");
    private BigDecimal change = new BigDecimal("0");
    private BigDecimal showChange = new BigDecimal("0");
    BigDecimal outstandingAmount = new BigDecimal("0");

    private String cashPayment;

    private BigDecimal tax = new BigDecimal("0");

    private String byCode;
    int count = 9;
    private String byDescription;

    String inventoryRecNum = "";

    private List<InventoryData> inventory = new ArrayList<InventoryData>();
    private List<InventoryData> allInventory = new ArrayList<InventoryData>();
    private List<InventoryData> filteredInventory = new ArrayList<InventoryData>();
    private List<InventoryData> posInventory = new ArrayList<InventoryData>();
    private InventoryData selectedInventory;
    private InventoryData selectedPOSInventory;
    private List<InventoryData> selectedInv;
    private List<InventoryData> filteredInvs;

    private Map<String, Integer> allInventoryMap = new HashMap<String, Integer>();

    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;

    InventoryProcessing ip = new InventoryProcessing();
    SupplierProcessing sp = new SupplierProcessing();
    ReceiptProcessing rp = new ReceiptProcessing();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private int allInvRowIndex;

    PrintPOS printPOS = new PrintPOS();

    private String barCode;
    
    boolean proceed = true;
    boolean queryProceed = false;

    public InventoryData(String iRecNum, String code, String description, int supplierId, String supplier, BigDecimal cost, BigDecimal sellingPrice, int reorderLevel, BigDecimal markup, int atHand, String size, String color, int quantity, BigDecimal totCost, int clientId, String client, String expiryDates, int allInvRowIndex) {

        this.iRecNum = iRecNum;
        this.code = code;
        this.description = description;
        this.supplierId = supplierId;
        this.supplier = supplier;
        this.clientId = clientId;
        this.client = client;
        this.cost = cost;
        this.sellingPrice = sellingPrice;
        this.reorderLevel = reorderLevel;
        this.markup = markup;
        this.atHand = atHand;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.totCost = totCost;
        this.expiryDates = expiryDates;
        this.allInvRowIndex = allInvRowIndex;

    }

    public InventoryData() {

    }

    @PostConstruct
    public void init() {
        selectedInventory = new InventoryData();
        fetchAllInventory();
        this.quantity = 1;
    }

    public String findInventory(HttpSession session) {

        code = "";
        description = "";

        //return "/inventory/find-inventory";
        return "/v4/inventory/List";
    }

    public String searchInventory() {

        inventory.clear();
        try {

            String query = "";
            String inventoryRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            int i = 0;
            if (!code.equals("")) {
                query = "SELECT * FROM Inventory where name service_no = '" + code + "'";
            } else if (!description.equals("")) {
                query = "SELECT * FROM Inventory where description like '%" + description + "%'";
            }
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            int x = 0;
            while (results.next()) {
                proceed = false;
                inventoryRecNum = results.getString(1);
                //inventory.add(new InventoryData(results.getString(1), results.getString(2), results.getString(3), results.getInt(4), results.getString(5), results.getBigDecimal(6), results.getBigDecimal(7), results.getInt(8), results.getBigDecimal(9), results.getInt(10), results.getString(14), results.getString(15), 1, new BigDecimal("0"), results.getInt(20), results.getString(21), LocalDate.parse(results.getString(9))));
                try {
                    inventory.add(new InventoryData(results.getString(1), results.getString(4), results.getString(8), results.getInt(15), sp.supplierName, results.getBigDecimal(6), results.getBigDecimal(13), results.getInt(12), results.getBigDecimal(11), results.getInt(2), results.getString(10), results.getString(5), 1, new BigDecimal("0"), results.getInt(3), "", results.getString(9), x));
                } catch (Exception e) {
                    inventory.add(new InventoryData(results.getString(1), results.getString(4), results.getString(8), results.getInt(15), sp.supplierName, results.getBigDecimal(6), results.getBigDecimal(13), results.getInt(12), results.getBigDecimal(11), results.getInt(2), results.getString(10), results.getString(5), 1, new BigDecimal("0"), results.getInt(3), "", null, x));
                }

                x++;
            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return "/inventory/inventory-listing";

    }

    public List<InventoryData> getAllInventory() {
        return allInventory;
    }

    public List<InventoryData> fetchAllInventory() {
        allInventory.clear();

        try {

            String query = "";
            String inventoryRecNum = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            query = "SELECT * FROM Inventory order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            proceed = true;
            int x = 0;
            while (results.next()) {

                proceed = false;
                inventoryRecNum = results.getString(1);
                try {
                    expiryDates = results.getString(9);
                } catch (NullPointerException npe) {
                    expiryDates = null;
                }

                //sp.getSupplier(results.getString(15));
                allInventory.add(new InventoryData(results.getString(1), results.getString(4), results.getString(8), 0, "", results.getBigDecimal(6), results.getBigDecimal(13), results.getInt(12), results.getBigDecimal(11), results.getInt(2), results.getString(10), results.getString(5), 1, new BigDecimal("0"), results.getInt(3), "", expiryDates, x));

                x++;
            }
//public InventoryData(String iRecNum, String code, String description, int supplierId, String supplier, BigDecimal cost, BigDecimal sellingPrice, int reorderLevel, BigDecimal markup, int atHand, String size, String color, int quantity, BigDecimal totCost, int clientId, String client) {
            connection.close();

        } catch (Exception sqle) {
            System.out.println("ERR:" + sqle);
        }

        return allInventory;
    }

    public void filterInventory() {

        filteredInventory.clear();

        try {

            String query = "";
            boolean proceed = false;

            connection = Systems.initConnection();

            query = "SELECT * FROM Inventory where description like '%" + description + "%' order by description";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            int count = 0;
            proceed = true;
            while (results.next()) {
                proceed = false;
                inventoryRecNum = results.getString(1);
                filteredInventory.add(new InventoryData(results.getString(1), results.getString(2), results.getString(3), results.getInt(4), results.getString(5), results.getBigDecimal(6), results.getBigDecimal(7), Integer.parseInt(results.getString(8)), results.getBigDecimal(9), results.getInt(10), results.getString(14), results.getString(15), 1, new BigDecimal("0"), results.getInt(20), results.getString(21), results.getString(9), count));
                count++;
            }

            this.count = count;

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

    }

    public void clearAddedItems() {
        filteredInventory.clear();
    }

    public void posComplete(List<InventoryData> selectedPOSInvs, String client, String paymentMethod, HttpSession session) throws IOException, SQLException {

        //getAccParams();
        UserProcessing up = new UserProcessing();

        results = up.getOrganisation();
        while (results.next()) {

            orgNumber = results.getString(2);
            orgName = results.getString(3);
            orgTel = results.getString(4);
            orgFax = results.getString(5);
            orgEmail = results.getString(6);
            orgPosAdd = results.getString(7);
            orgPhysAdd = results.getString(8);

        }

        CustomerProcessing cp = new CustomerProcessing();

        results = cp.getCustomerResultset(client.split(",")[0]);
        while (results.next()) {

            tel = results.getString(8);
            cell = results.getString(9);
            fax = results.getString(10);
            email = results.getString(18);
            posAdd = results.getString(11);
            physAdd = results.getString(12);

        }

        invDate = sdf.format(new Date());

        int maxRec = rp.getMaxRec();
        maxRec++;

        iRecNum = "" + maxRec;

        /*totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");

        invCost = new BigDecimal("0");*/
        count = selectedPOSInvs.size();

        for (InventoryData inv : selectedPOSInvs) {

            //invCost = inv.getSellingPrice();
            //invCost = invCost.multiply(new BigDecimal(quantity));
            
            quantity = inv.getQuantity();

         //   this.totInvCost = this.totInvCost.add(invCost);

           // inv.setTotCost(this.totInvCost);
            
            //System.out.println("INV COSTS COMPLETE: " + quantity + ":" + invCost + ":" + this.totInvCost + ":" + inv.getTotCost());
            
//ip.updateInventory(inv.getRecNum(), (inv.getAtHand() - quantity));            
            this.atHand = (inv.getAtHand() - quantity);

            //this.totCost.add(this.totInvCost);
            //System.out.println("SAT 111: " + atHand);
            //***SEND RECEIPT PRINTING TO PRINTER********//        
            //PrimeFaces current = PrimeFaces.current();
            //current.executeScript("PF('dlgPrintReceipt'). .show();");
            //UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            //CommandButton printReceipt = (CommandButton) viewRoot.findComponent(":printReceiptForm:posPrinter");
            //printReceipt.
//            printReceipt.
            //System.out.println("SAT 222: " + atHand);
            rp.saveReceiptInventory(session.getAttribute("uk").toString(), "" + maxRec, inv.getDescription(), inv.getCost(), inv.getQuantity(), inv.getSellingPrice(), inv.getRecNum(), inv.getAtHand());
        }

        //this.totCost = totInvCost;//totInvCost.add(totServCost);
       // this.taxAmount = this.totInvCost.divide(new BigDecimal(100));
       // this.taxAmount = this.taxAmount.multiply(new BigDecimal(15));
       // this.totCost.add(this.taxAmount);        

        System.out.println("CHECK CHANGE: " + this.taxAmount + ":" + this.totInvCost + ":" + this.totCost + ":" + this.getAmountPaid() + ":" + this.getAmountPaid().subtract(this.totCost));
//13:11:27,794 INFO  [stdout] (default task-51) CHECK CHANGE: 6116.5695:40777.13:50529.3785:51000:470.6215        
        if (this.getAmountPaid().compareTo(this.totCost) == 1) 
            setChange(this.getAmountPaid().subtract(this.totCost));
        else
            setChange(this.change = new BigDecimal(0));        
        this.outstandingAmount = rp.outstandingAmount;                

        setChange(getChange());
        setShowChange(getChange());
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlgShowChange').show();");

        rp.saveReceipt(sdf.format(new Date()), client.split(",")[0], client.split(",")[1], "", tel, cell, fax, posAdd, physAdd, "", getTotCost(), getTax(), this.getAmountPaid(), getChange(), session.getAttribute("uk").toString(), "", email, paymentMethod);
//public BigDecimal saveReceipt(String invDate,String cID,String cName,String cSurname,String tel,String cell,String fax,String posAdd,String physAdd,String recurring, BigDecimal amtDue, BigDecimal taxAmount, BigDecimal received, BigDecimal change, String uk,String oid, String email, String cashPayment) throws SQLException {        

System.out.println("PRINT REC CHECK: " + printReceipt);
        if (printReceipt) 
            printPOS.printPOSReceipt(sdf.format(new Date()), orgNumber, orgName, orgTel, orgFax, orgEmail, orgPosAdd, orgPhysAdd, client.split(",")[0], client.split(",")[1], tel, cell, fax, email, posAdd, physAdd, getTotal(), getTotCost(), this.getAmountPaid(), getChange(), this.outstandingAmount, getTax(),selectedPOSInvs);
        
        clearPOSInventory();

        //printPOSReceipt();
        //session.removeAttribute("inventoryData.allInventory");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Receipt Saved Successfully"));
    }

    public void printPOSReceipt() throws SQLException {
        PrimeFaces primefaces = PrimeFaces.current();
        primefaces.executeScript("PF('dlgPrintReceipt').show();");

        clearPOSInventory();
    }

    public void clearPOSInventory() {

        this.tax = new BigDecimal("0");
        this.totCost = new BigDecimal("0");
        this.sellingPrice = new BigDecimal("0");
        this.amtPaid = new BigDecimal("0");
        this.outstandingAmount = new BigDecimal("0");
        this.total = new BigDecimal("0");

        totInvCost = new BigDecimal("0");
        cost = new BigDecimal("0");
        invCost = new BigDecimal("0");
        totCost = new BigDecimal("0");
        change = new BigDecimal("0");
        amtPaid = new BigDecimal("0");
        
        this.client = "";

        posInventory.clear();
        InventoryData updatedInvData = null;
        for (InventoryData inv : allInventory) {

            updatedInvData = new InventoryData();
            updatedInvData.setAmountPaid(inv.getAmountPaid());

            try {
                updatedInvData.setAtHand(allInventoryMap.get(inv.getRecNum()));
            } catch (NullPointerException npe) {
                updatedInvData.setAtHand(inv.getAtHand());
            }

            updatedInvData.setCell(inv.getCell());
            updatedInvData.setChange(inv.getChange());
            updatedInvData.setClient(inv.getClient());
            updatedInvData.setCode(inv.getCode());
            updatedInvData.setColor(inv.getColor());
            updatedInvData.setCost(inv.getCost());
            updatedInvData.setCount(inv.getCount());
            updatedInvData.setDescription(inv.getDescription());
            updatedInvData.setEmail(inv.getEmail());
            updatedInvData.setExpiryDate(inv.getExpiryDate());
            updatedInvData.setExpiryDates(inv.getExpiryDates());
            updatedInvData.setFax(inv.getFax());
            updatedInvData.setInvDate(inv.getInvDate());
            updatedInvData.setOrgEmail(inv.getOrgEmail());
            updatedInvData.setOrgFax(inv.getOrgFax());
            updatedInvData.setOrgName(inv.getOrgName());
            updatedInvData.setOrgNumber(inv.getOrgName());
            updatedInvData.setOrgPhysAdd(inv.getOrgPhysAdd());
            updatedInvData.setOrgPosAdd(inv.getOrgPosAdd());
            updatedInvData.setOrgTel(inv.getOrgTel());
            updatedInvData.setPhysAdd(inv.getPhysAdd());
            updatedInvData.setPosAdd(inv.getPosAdd());
            //updatedInvData.setQty(inv.getq());
            updatedInvData.setQuantity(inv.getQuantity());
            updatedInvData.setRecNum(inv.getRecNum());
            updatedInvData.setReorderLevel(inv.getReorderLevel());
            updatedInvData.setSKUNumber(inv.getSKUNumber());
            updatedInvData.setSellingPrice(inv.getSellingPrice());
            updatedInvData.setSize(inv.getSize());
            updatedInvData.setSupplier(inv.getSupplier());
            updatedInvData.setTax(inv.getTax());
            updatedInvData.setTel(inv.getTel());
            updatedInvData.setTotCost(inv.getTotCost());
            updatedInvData.setTotal(inv.getTotal());
            updatedInvData.setVatNum(inv.getVatNum());

            //allInventory.set(inv.getAllInvRowIndex(), updatedInvData);
            posInventory.add(updatedInvData);

        }

        allInventory.clear();
        for (InventoryData inv : posInventory) {
            allInventory.add(inv);
        }

        posInventory.clear();
        allInventoryMap.clear();
        
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('wvInventoryDT').clearFilters();");
    }

    /*    public void setAllInventoryMap(Map<InventoryData> allInventoryMap allInventoryMap) {
        this.allInvRowIndex = allInvRowIndex;
    }

    public Map<InventoryData> getAllInventoryMap getAllInventoryMap() {
        return allInvRowIndex;
    }
     */
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setAllInvRowIndex(int allInvRowIndex) {
        this.allInvRowIndex = allInvRowIndex;
    }

    public int getAllInvRowIndex() {
        return allInvRowIndex;
    }

    public void setQty(int quantity) {
        this.quantity = quantity;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPosAdd() {
        return orgPosAdd;
    }

    public void setOrgPosAdd(String orgPosAdd) {
        this.orgPosAdd = orgPosAdd;
    }

    public String getOrgPhysAdd() {
        return orgPhysAdd;
    }

    public void setOrgPhysAdd(String orgPhysAdd) {
        this.orgPhysAdd = orgPhysAdd;
    }

    public BigDecimal getAmountPaid() {
        return amtPaid;
    }

    public void setAmountPaid(BigDecimal amtPaid) {
        this.amtPaid = amtPaid;
    }
    
    public BigDecimal getShowChange() {
        return showChange;
    }

    public void setShowChange(BigDecimal showChange) {
        this.showChange = showChange;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getTotServCost() {
        return totServCost;
    }

    public void setTotServCost(BigDecimal totServCost) {
        this.totServCost = totServCost;
    }

    public BigDecimal getTotInvCost() {
        return totInvCost;
    }

    public void setTotInvCost(BigDecimal totInvCost) {
        this.totInvCost = totInvCost;
    }

    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVatNum() {
        return vatNum;
    }

    public void setVatNum(String vatNum) {
        this.vatNum = vatNum;
    }

    public void setPosAdd(String posAdd) {
        this.posAdd = posAdd;
    }

    public String getPosAdd() {
        return posAdd;
    }

    public String getPhysAdd() {
        return physAdd;
    }

    public void setPhysAdd(String physAdd) {
        this.physAdd = physAdd;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getInvDate() {
        return invDate;
    }

    public List<InventoryData> getFilteredInventoryData() {
        return filteredInvs;
    }

    public void setInventoryData(List<InventoryData> filteredInvs) {
        this.filteredInvs = filteredInvs;
    }

    public List<InventoryData> getFilteredInventory() {
        return filteredInventory;
    }

    public void setFilteredInventory(List<InventoryData> filteredInventory) {
        this.filteredInventory = filteredInventory;
    }

    public List<InventoryData> getPOSInventory() {
        return posInventory;
    }

    public void setPOSInventory(List<InventoryData> posInventory) {
        this.posInventory = posInventory;
    }

    public List<InventoryData> getSelectedInv() {
        return selectedInv;
    }

    public void setSelectedInv(List<InventoryData> selectedInv) {
        this.selectedInv = selectedInv;
    }

    public List<InventoryData> getFilteredInventorys() {

        filterInventory();

        return filteredInventory;
    }

    public List<InventoryData> getInventory() {
        return inventory;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRecNum() {
        return iRecNum;
    }

    public void setRecNum(String iRecNum) {
        this.iRecNum = iRecNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public void setAtHand(int atHand) {
        this.atHand = atHand;
    }

    public int getAtHand() {
        return atHand;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getExpiryDates() {
        return expiryDates;
    }

    public void setExpiryDates(String expiryDates) {
        this.expiryDates = expiryDates;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSKUNumber() {
        return sKuNumber;
    }

    public void setSKUNumber(String sKuNumber) {
        this.sKuNumber = sKuNumber;
    }
    
    public boolean getPrintReceipt() {
        return printReceipt;
    }

    public void setPrintReceipt(boolean printReceipt) {
        this.printReceipt = printReceipt;
    }
    
    public InventoryData getSelectedInventory() {
        return selectedInventory;
    }

    public void setSelectedInventory(InventoryData selectedInventory) {
        this.selectedInventory = selectedInventory;
    }

    public InventoryData getSelectedPOSInventory() {
        return selectedPOSInventory;
    }

    public void setSelectedPOSInventory(InventoryData selectedPOSInventory) {
        this.selectedPOSInventory = selectedPOSInventory;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        this.iRecNum = selectedInventory.iRecNum; //((InventoryData) event.getObject()).iRecNum;
        this.code = selectedInventory.code;
        this.description = selectedInventory.description;
        this.cost = selectedInventory.cost;
        this.supplierId = selectedInventory.supplierId;
        this.supplier = selectedInventory.supplier;
        this.clientId = selectedInventory.clientId;
        this.client = selectedInventory.client;
        this.sellingPrice = selectedInventory.sellingPrice;
        this.reorderLevel = selectedInventory.reorderLevel;
        this.markup = selectedInventory.markup;
        this.atHand = selectedInventory.atHand;
        this.size = selectedInventory.size;
        this.color = selectedInventory.color;

        try {
            this.description = URLEncoder.encode(this.description, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(InventoryData.class.getName()).log(Level.SEVERE, null, ex);
        }

        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String param = "code=" + code + "&uk=" + request.getSession().getAttribute("uk").toString() + "&name=" + request.getSession().getAttribute("username").toString() + "&recnum=" + iRecNum + "&description=" + description + "&cost=" + cost + "&supplierId=" + supplierId + "&supplier=" + supplier + "&clientId=" + clientId + "&client=" + client + "&sellingPrice=" + sellingPrice + "&reorderLevel=" + reorderLevel + "&markup=" + markup + "&atHand=" + atHand + "&size=" + size + "&color=" + color;

        url = request.getRequestURL().toString();
        uri = request.getRequestURI();

        endIndex = uri.lastIndexOf("/");

        if (endIndex != -1) {
            newstr = uri.substring(0, endIndex);
            FacesContext.getCurrentInstance().getExternalContext().redirect(newstr + "/view-inventory.xhtml?" + param);
        }

    }
    
    public void onClientSelect(String client) throws IOException, SQLException {
        this.client = client;
    }

    public void onBarcodeChange() throws IOException, SQLException {
System.out.println("CODE CHANGE 000");
        for (InventoryData invCode : allInventory) {
            if (invCode.getCode().equals(barCode)) {
                //System.out.println("GOT CODE");sd
                this.setSelectedPOSInventory(invCode);
                try {

                    for (InventoryData inv : posInventory) {
                        if (inv.getRecNum().equals(selectedPOSInventory.getRecNum())) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cannot Add Inventory", "Item is already added."));

                            proceed = false;
                        }
                    }

                    JSONArray jsonRules = new JSONArray(selectedPOSInventory.getExpiryDates());
                    JSONObject obj = null;

                    String month = "";
                    //String skuNumber = "";
                    for (int i = 0; i < jsonRules.length(); i++) {
                        obj = jsonRules.getJSONObject(i);
                        if (obj.getJSONObject("expiryDate").getInt("month") < 10) {
                            month = "0" + obj.getJSONObject("expiryDate").getInt("month");
                        }

                        this.expiryDate = LocalDate.parse(obj.getJSONObject("expiryDate").getInt("year") + "-" + month + "-" + obj.getJSONObject("expiryDate").getInt("day"));

                        this.sKuNumber = obj.getString("sKUNumber");
                    }

                    if ((expiryDate.isBefore(LocalDate.now())) && ((!expiryDate.equals("")) || (!expiryDate.toString().equals("null")))) {
                        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SKU Number " + skuNumber + " Expired", "Check this item before selling"));

                        // queryProceed = true;
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('AddExpiredInventoryForm').show();");
                        proceed = false;
                    }

                } catch (Exception e) {
                    System.out.println("POS ERROR: " + e);
                }

                /*if (queryProceed) {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('MedicationCreateDialog').show();");
            proceed = false;
        }*/
                if (proceed) {
                    //selectedPOSInventory = selectedInventory;

                    if (selectedPOSInventory.getAtHand() < 1) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", selectedPOSInventory.getDescription() + " is out of stock."));
                    } else if ((selectedPOSInventory.getAtHand() - quantity) < 0) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", "There are only " + selectedPOSInventory.getAtHand() + " " + selectedPOSInventory.getDescription() + " left."));
                    } else if (selectedPOSInventory.getReorderLevel() >= (selectedPOSInventory.getAtHand() - quantity)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Reorder Item Urgent Notice", selectedPOSInventory.getDescription() + " has reached the re-order level of " + selectedPOSInventory.getReorderLevel()));
                        deductStock();
                    } else if (selectedPOSInventory.getAtHand() <= (selectedPOSInventory.getAtHand() - quantity)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", "There are only " + selectedPOSInventory.getAtHand() + " " + selectedPOSInventory.getDescription() + " left."));
                    } else {
                        deductStock();
                    }
                }

                /////
            }
        }

    }
/*
    public void onBarcodeChange(SelectEvent event) throws IOException, SQLException {

    }
*/
    public void onRowSelectPOS(SelectEvent event) throws IOException, SQLException {

        //System.out.println("ALL ROW INDEX FIRST: " + ((InventoryData) event.getObject()).allInvRowIndex);
        //this.setAllInvRowIndex(selectedPOSInventory.getAllInvRowIndex());
        //this.setAllInvRowIndex(((InventoryData) event.getObject()).allInvRowIndex);
        //  sf
        //allInventoryMap
        //System.out.println("ALL ROW INDEX FIRST 000: " + this.getAllInvRowIndex() + ":" + selectedPOSInventory.atHand + ":" + rp.atHand);
        //temp = selectedPOSInventory.getSellingPrice();
//        total = total.add(temp);
        
        try {

            for (InventoryData inv : posInventory) {
                if (inv.getRecNum().equals(selectedPOSInventory.getRecNum())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cannot Add Inventory", "Item is already added."));

                    proceed = false;
                }
            }

            JSONArray jsonRules = new JSONArray(selectedPOSInventory.getExpiryDates());
            JSONObject obj = null;

            String month = "";
            //String skuNumber = "";
            for (int i = 0; i < jsonRules.length(); i++) {
                obj = jsonRules.getJSONObject(i);
                if (obj.getJSONObject("expiryDate").getInt("month") < 10) {
                    month = "0" + obj.getJSONObject("expiryDate").getInt("month");
                }

                this.expiryDate = LocalDate.parse(obj.getJSONObject("expiryDate").getInt("year") + "-" + month + "-" + obj.getJSONObject("expiryDate").getInt("day"));

                this.sKuNumber = obj.getString("sKUNumber");
            }

            if ((expiryDate.isBefore(LocalDate.now())) && ((!expiryDate.equals("")) || (!expiryDate.toString().equals("null")))) {
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SKU Number " + skuNumber + " Expired", "Check this item before selling"));

                // queryProceed = true;
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('AddExpiredInventoryForm').show();");
                proceed = false;
            }

        } catch (Exception e) {
            System.out.println("POS ERROR: " + e);
        }

        /*if (queryProceed) {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('MedicationCreateDialog').show();");
            proceed = false;
        }*/
        if (proceed) {
            //selectedPOSInventory = selectedInventory;

            if (selectedPOSInventory.getAtHand() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", selectedPOSInventory.getDescription() + " is out of stock."));
            } else if ((selectedPOSInventory.getAtHand() - getQuantity()) < 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", "There are only " + selectedPOSInventory.getAtHand() + " " + selectedPOSInventory.getDescription() + " left."));
            } else if (selectedPOSInventory.getReorderLevel() >= (selectedPOSInventory.getAtHand() - getQuantity())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Reorder Item Urgent Notice", selectedPOSInventory.getDescription() + " has reached the re-order level of " + selectedPOSInventory.getReorderLevel()));
                deductStock();
            } else if (selectedPOSInventory.getAtHand() <= (selectedPOSInventory.getAtHand() - getQuantity())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", "There are only " + selectedPOSInventory.getAtHand() + " " + selectedPOSInventory.getDescription() + " left."));
            } else {
                deductStock();
            }
        }

    }

    public void deductStock() throws SQLException {

        if (selectedPOSInventory.getAtHand() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", selectedPOSInventory.getDescription() + " is out of stock."));
        } else if ((selectedPOSInventory.getAtHand() - getQuantity()) < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", "There are only " + selectedPOSInventory.getAtHand() + " " + selectedPOSInventory.getDescription() + " left."));
        } else if (selectedPOSInventory.getReorderLevel() >= (selectedPOSInventory.getAtHand() - getQuantity())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Reorder Item Urgent Notice", selectedPOSInventory.getDescription() + " has reached the re-order level of " + selectedPOSInventory.getReorderLevel()));
        } else if (selectedPOSInventory.getAtHand() <= (selectedPOSInventory.getAtHand() - getQuantity())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Add Inventory", "There are only " + selectedPOSInventory.getAtHand() + " " + selectedPOSInventory.getDescription() + " left."));
        }

        temp = selectedPOSInventory.getSellingPrice().multiply(new BigDecimal(getQuantity()));

        total = total.add(temp);
        
        //tax = total.divide(new BigDecimal(100));
        //tax = tax.multiply(new BigDecimal(15));
        
        setTotal(total);
        setTax(new BigDecimal(1));
       // totCost = total.add(tax);
        //setTotCost(totCost);
        setTotCost(getTotal());
        selectedPOSInventory.setQuantity(quantity);
        
System.out.println("COUNT TOTAL: " + getTotal() + ":" + getTotCost() + ":" + getTax() + ":" + getQuantity());
        //totCost = totCost.add(tax);

        /* selectedInventory.setCost(selectedPOSInventory.getSellingPrice());
        selectedInventory.setSellingPrice(BigDecimal.valueOf(quantity).multiply(selectedPOSInventory.getSellingPrice()));
        selectedInventory.setQuantity(quantity);
        selectedInventory.setDescription(selectedPOSInventory.getDescription());
        selectedInventory.setCode(selectedPOSInventory.getCode());
        selectedInventory.setRecNum(selectedPOSInventory.iRecNum);*/
        //ip.updateInventory(selectedPOSInventory.iRecNum, atHand);
        // on select an inventory item
        // store values in a hashmap
        // store selectedPOSInventory.allInvRowIndex, selectedPOSInventory.atHand
        // at the end of printPOSReceipt(), fetch hashmap values
        // iterate hashmap
        // iterate allInventory records
        // set condition if  allInvRowIndex = (hashmap allInvRowIndex)
        // update that record eg: setInventoryData(hashmap.atHand);
        //System.out.println("DED STOCK: " + selectedPOSInventory.getAtHand() + ":" + this.atHand + ":" + rp.atHand);
        allInventoryMap.put(selectedPOSInventory.getRecNum(), selectedPOSInventory.getAtHand() - getQuantity());
        //   System.out.println("PUT MAP: " + selectedPOSInventory.getRecNum() + ":" + (selectedPOSInventory.getAtHand() - quantity));

        //selectedInventory.setAtHand(selectedPOSInventory.getAtHand());
        posInventory.add(selectedPOSInventory);

        this.quantity = 1;
        
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('wvInventoryDT').clearFilters();");
    }

    public void viewInventory() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/inventory/view-inventory.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(InventoryData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void projectSelectInventory(String recnum, String actualStart, HttpSession session) throws SQLException, IOException {

        /*   FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        projectId = session.getAttribute("projectId").toString();
        projectName = session.getAttribute("projectName").toString();
        actualStart = session.getAttribute("actualStart").toString();
        actualEnd = session.getAttribute("actualEnd").toString();
        
        ap.recNum = recnum;   */
        if (recnum.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cannot Add Inventory", "Select An Activity First."));
        } else if (actualStart.equals("1900-01-01")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cannot Add Inventory", "The Activity Has Not Begun Yet."));
        } else {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            url = request.getRequestURL().toString();
            uri = request.getRequestURI();

            endIndex = uri.lastIndexOf("/");

            if (endIndex != -1) {
                newstr = uri.substring(0, endIndex);

                FacesContext.getCurrentInstance().getExternalContext().redirect("../projects/select-inventory.xhtml");
            }

        }

    }

}
