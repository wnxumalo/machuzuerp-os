/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.datatableprocessing.jsf.EmployeesData;
import com.machuzuerp.controllers.jsf.PurchaseOrderOld;
import com.machuzuerp.controllers.jsf.Requisition;
import com.machuzuerp.controllers.datatableprocessing.jsf.RequisitionData;
import com.machuzuerp.controllers.datatableprocessing.jsf.SupplierData;
import com.machuzuerp.controllers.jsf.SupplierList;
import com.machuzuerp.controllers.jsf.SupplierListBean;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.entities.dto.EmployeeDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class GISProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;

    boolean edit = false;
    boolean proceed = true;

    public String rRecNum;
    public Date uuid;
    public int organisation;
    public String telephone;
    public String cellphone;
    public String emailaddress;
    public String coordinates;   

    int maxRec = 0;

    String query = "";

    String ret = "";
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
    
    SupplierProcessing sp = new SupplierProcessing();   

    public GISProcessing() {

    }
    
    public ResultSet getGISClient() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        //query = "SELECT * FROM demogis where uuid = '" + uuid + "'";
        query = "SELECT * FROM demogis";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

    public int editGISProcessing(String uk, String func, String mod, String requisitionRecNo) throws SQLException {
        
        int x = 1;
        
//        try {

            String cDate = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cDate = sdf.format(new java.util.Date());            
            
            connection = Systems.initConnection();

            if (!func.equals("")) {
                edit = true;
                if (func.equals("delete")) {

                    if (mod.equals("yes")) {

                        int added = 0;
                        statement = connection.createStatement();
                        query = "delete from requisition where recnum = '" + requisitionRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } else {

                    }

                } else {

                   /* int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET requestDate  = '" + sdf.format(requestDate) + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET requestedDeliveryDate  = '" + sdf.format(requestedDeliveryDate) + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET deliveryEmployeeId  = '" + deliveryEmployeeId + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET deliveryEmployeeName  = '" + deliveryEmployeeName + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET requestorName  = '" + requestorName + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET accountsManagerName  = '" + accountsManagerName + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET accountsManagerId  = '" + accountsManagerId + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET availableFunds  = '" + availableFunds + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET accountingData  = '" + accountingData + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET approverName  = '" + approverName + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET approverJobTitle  = '" + approverJobTitle + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET approvalDate  = '" + sdf.format(approvalDate) + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET remarks  = '" + remarks + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE requisition SET status  = '" + status + "'Where RecNum = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "delete from requisitionitems where requisitionid = '" + requisitionRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();                 
                    
*/
                }

            } else {

                connection.setAutoCommit(false);

               /* INSERT_DATA = "INSERT INTO requisition(requestDate, requestedDeliveryDate, deliveryEmployeeId, "
                + "deliveryEmployeeName,soleSourceJustificationAttached, requestorName, accountsManagerId, accountsManagerName, "
                + "remarks, supplierid, suppliername, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, sdf.format(requestDate));
                ps.setString(2, sdf.format(requestedDeliveryDate));
                ps.setInt(3, deliveryEmployeeId);
                ps.setString(4, deliveryEmployeeName);
                ps.setString(5, soleSourceJustificationAttached);
                ps.setString(6, requestorName);
                ps.setInt(7, accountsManagerId);
                ps.setString(8, accountsManagerName);
                ps.setString(9, remarks);
                ps.setInt(10, supplier.getId());
                ps.setString(11, supplier.getName());
                ps.setString(12, status);
*/
                ps.executeUpdate();

                connection.commit();

                query = "SELECT max(recnum) FROM requisition";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                while (results.next()) {
                    maxRec = results.getInt(1);
                }
                
                connection.setAutoCommit(false);
                
/*                for (Requisition item : filteredRequisitions) {

                    try {                                              

                        INSERT_DATA = "insert into requisitionitems(RequisitionId, ItemNo, ItemService, Quantity, "
                                + "EstUnitPrice, EstAmount) VALUES (?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setInt(1, maxRec);
                        ps.setInt(2, x);
                        ps.setString(3, item.getItemService());
                        ps.setInt(4, item.getQuantity());
                        ps.setString(5, item.getEstUnitPrice());
                        ps.setFloat(6, (item.getQuantity() * Float.parseFloat(item.getEstUnitPrice())) );
                        ps.executeUpdate();

                        connection.commit();

                        x++;

                    } catch (java.sql.SQLException | java.lang.NumberFormatException sqle) {
                        System.out.println("Save Requisition Items: " + sqle);
                    }

                }

                for (EmployeesData item : approvers) {

                    try {
                     
                        INSERT_DATA = "insert into requisitionapprovals(requisitionid, employeeid, empname, surname, department, position, status) VALUES (?,?,?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setInt(1, maxRec);
                        ps.setString(2, item.getIDNumber());
                        ps.setString(3, item.getEmpName());
                        ps.setString(4, item.getSurname());
                        ps.setString(5, item.getDepartment());
                        ps.setString(6, item.getPosition());
                        ps.setString(7, "Unapproved");
                        ps.executeUpdate();

                        connection.commit();

                    } catch (java.sql.SQLException | java.lang.NumberFormatException sqle) {
                        System.out.println("Save Requisition Approval: " + sqle + "-" + item.getIDNumber());
                    }

                }
                statement.close();

            }
*/
            statement.close();
            connection.close();

        } /*catch (Exception sqle) {
            System.out.println(sqle);
        }*/

        return maxRec;

    }

   /* public String updateRequisitionApproval(int approvalId, String employeeId, float availableFunds, String approvalStatus, String comments) throws SQLException {

        String ret = "";
        
        boolean exists = getRequisitionApproval(approvalId, employeeId);

        if (approvalStatus.equals("")) {
            
            ret = "Blank";

        } else {
            
            if (exists) {

                int added = 0;
                statement = connection.createStatement();
                query = "UPDATE requisitionapprovals SET availableFunds  = '" + availableFunds + "'Where RecNum = '" + approvalId + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE requisitionapprovals SET status  = '" + approvalStatus + "'Where RecNum = '" + approvalId + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE requisitionapprovals SET comment  = '" + comments + "'Where RecNum = '" + approvalId + "'";
                added = statement.executeUpdate(query);
                statement.close();

                ret = "Successful";

            } else {

                ret = "Incorrect";

            }
        } 

        return ret;
    }

    public void UpdateRequisitionUpload(int rId) throws SQLException {
        
        connection = Systems.initConnection();
        
        int added = 0;
        statement = connection.createStatement();
        query = "UPDATE requisition SET SoleSourceJustificationAttached  = 'Yes' where recnum = '" + rId + "'";
        added = statement.executeUpdate(query);
        statement.close();

        connection.close();

    }

    public String saveRequisitionJustification(String requisitionId, String url, String fullPath, String fileName, String filetype) throws SQLException {        

        try {

            connection = Systems.initConnection();

            connection.setAutoCommit(false);

            INSERT_DATA = "insert into requisitionjustification(requisitionId, viewurl, fullPath, filename, "
                    + "filetype, uploaddate) VALUES (?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, requisitionId);
            ps.setString(2, url+fileName);
            ps.setString(3, fullPath);
            ps.setString(4, fileName);
            ps.setString(5, filetype);
            ps.setString(6, sdf.format(new Date()));
            ps.executeUpdate();

            connection.commit();

            query = "SELECT max(recnum) FROM requisitionjustification";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                maxRec = results.getInt(1);
            }

            ret = "Requisition Justification Uploaded Successfully";

        } catch (Exception e) {
            ret = "Error: " + e;
        }

        return ret;
    }

    public String saveRequisitionReceipt(String requisitionId, String url, String fullPath, String fileName, String filetype) throws SQLException {        

        try {

            connection.setAutoCommit(false);

            INSERT_DATA = "insert into requisitionreceipts(requisitionId, viewurl, fullPath, filename, "
                    + "filetype, uploaddate) VALUES (?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, requisitionId);
            ps.setString(2, url+fileName);
            ps.setString(3, fullPath);
            ps.setString(4, fileName);
            ps.setString(5, filetype);
            ps.setString(6, sdf.format(new Date()));

             ps.executeUpdate();

            connection.commit();

            query = "SELECT max(recnum) FROM requisitionreceipts";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                maxRec = results.getInt(1);
            }

            ret = "Requisition Receipt Uploaded Successfully";

        } catch (Exception e) {
            ret = "Error: " + e;
        }

        return ret;
    }

    public int editPurchaseOrder(String uk, String func, String mod, String poRecnum, String requisitionId, String supplierId, String poDate, String requestorName, String remarks, List<PurchaseOrder> purchaseOrders) throws SQLException {

        String cDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cDate = sdf.format(new java.util.Date());

        int added = 0;

        connection = Systems.initConnection();

        if (!func.equals("")) {
            edit = true;
            if (func.equals("delete")) {

                if (mod.equals("yes")) {

                    added = 0;
                    statement = connection.createStatement();
                    query = "delete from PurchaseOrderOld where PURCHASE_ORDER_ID = '" + poRecnum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                } else {

                }

            } else {

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + requisitionId + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + supplierId + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + requestorName + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + remarks + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "delete from purchaseorderitems where requisitionid = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                int x = 1;
                statement = connection.createStatement();
                for (PurchaseOrderOld item : purchaseOrders) {

                    try {

                        INSERT_DATA = "INSERT INTO purchaseorderitems(RequisitionId, ItemNo, ItemService, Quantity, "
                                + "EstUnitPrice, EstAmount) VALUES (?,?,?,?,?,?)";
                            ps = connection.prepareStatement(INSERT_DATA);
                            ps.setString(1, poRecnum);
                            ps.setInt(2, x);
                            ps.setString(3, item.getItemService());
                            ps.setInt(4, item.getQuantity());
                            ps.setString(5, item.getEstUnitPrice());
                            ps.setString(6, item.getEstAmount());                           

                            ps.executeUpdate();

                            connection.commit();

                        x++;

                    } catch (java.sql.SQLException sqle) {
                        System.out.println("Save Requisition: " + sqle);
                    }

                }
                statement.close();

            }

        } else {

            String ret = sp.getSupplierObject(supplierId);
            String[] retArr = ret.split(",");

            connection = Systems.initConnection();
            connection.setAutoCommit(false);
            try {               
                
                INSERT_DATA = "INSERT INTO PurchaseOrderOld(requisitionId, poDate, notes, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + "cid, vatNum) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setString(1, requisitionId);
                    ps.setString(2, poDate);
                    ps.setString(3, remarks);
                    ps.setString(4, retArr[0]);
                    ps.setString(5, retArr[1]);
                    ps.setString(6, retArr[2]);                           
                    ps.setString(7, retArr[3]);                           
                    ps.setString(8, retArr[4]);                           
                    ps.setString(9, retArr[5]);                           
                    ps.setString(10, retArr[6]);                           
                    ps.setString(11, retArr[7]);                           
                    ps.setString(12, retArr[8]);                           

                    ps.executeUpdate();

                    connection.commit();
            } catch (Exception e) {               
                
                INSERT_DATA = "INSERT INTO PurchaseOrderOld(requisitionId, poDate, notes, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + "cid, vatNum) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setString(1, requisitionId);
                    ps.setString(2, poDate);
                    ps.setString(3, remarks);
                    ps.setString(4, retArr[0]);
                    ps.setString(5, retArr[1]);
                    ps.setString(6, retArr[2]);                           
                    ps.setString(7, retArr[3]);                           
                    ps.setString(8, retArr[4]);                           
                    ps.setString(9, retArr[5]);                           
                    ps.setString(10, retArr[6]);                           
                    ps.setString(11, retArr[7]);                           
                    ps.setString(12, "0");                           

                    ps.executeUpdate();

                    connection.commit();
                
            }

            added = statement.executeUpdate(query);

            query = "SELECT max(recnum) FROM PurchaseOrderOld";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                maxRec = results.getInt(1);
            }

        }

        connection.setAutoCommit(false);
        for (PurchaseOrderOld item : purchaseOrders) {

            if (item.getItemService().length() > 0) {

                try {                  
                    
                    INSERT_DATA = "INSERT INTO purchaseorderitems(purchaseorderid, itemservice, quantity, "
                            + "estunitprice, estamount) VALUES (?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setInt(1, maxRec);
                    ps.setString(2, item.getItemService());
                    ps.setInt(3, item.getQuantity());
                    ps.setString(4, item.getEstUnitPrice());
                    ps.setString(5, item.getEstAmount());                          

                    ps.executeUpdate();

                    connection.commit();

                } catch (Exception e) {
                    System.out.println("ERR: " + e);
                }

            }

        }

        statement.close();
        connection.close();

        return maxRec;

    }

    public ResultSet getRequisitions(Date from, Date to) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisition where requestDate between '" + sdf.format(from) + "' and '" + sdf.format(to) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
    }

    public List<RequisitionData> getRequisitions() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisition order by requestDate";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        requisitions.clear();
        while (results.next()) {
            requisitions.add(new RequisitionData(results.getString(1),results.getDate(2),results.getString(14),results.getString(5),results.getString(12),results.getDate(3), results.getString(13)));
        }

        return requisitions;
    }

    public ResultSet getRequisitionItems(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisitionitems where requisitionid = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;

    }

    public void getRequisition(String recnum) throws SQLException {
        
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisition where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {

            requestDate = results.getDate(2);
            requestedDeliveryDate = results.getDate(3);
            requestorName = results.getString(5);
            accountsManagerName = results.getString(10);
           // availableFunds = results.getString(6);
            //accountingData = results.getString(7);
            //approverName = results.getString(10);
            //approverJobTitle = results.getString(11);
            supplierId = results.getString(7);
            /*try {
                approvalDate = results.getDate(12);
            } catch (NullPointerException | java.sql.SQLException npe) {
                approvalDate = null;
            }
            remarks = results.getString(6);

        }      

    }

    
    public boolean getRequisitionApproval(int recnum, String employeeId) throws SQLException {

        boolean exists = false;

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT employeeid FROM requisitionapprovals where recnum = '" + recnum + "' and employeeid = '" + employeeId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        while (results.next()) {
            exists = true;
        }

        return exists;
    }

    /*public String saveRequisition(String invDate,String cID,String cName,String cSurname,String tel,String cell,String fax,String posAdd,String physAdd,String recurring,String uk,String oid) throws SQLException {
        
        Systems.closeConnection();
        connection = Systems.initConnection();
         
        statement = connection.createStatement();
        query = "INSERT INTO quotations(quotationdate, cid, name, surname, telephone, cellphone, fax, postaladdress, physicaladdress, recurring, uk, orgid) VALUES ('" + invDate + "','" + cID + "','" + cName + "','" + cSurname + "','" + tel + "','" + cell + "','" + fax + "','" + posAdd + "','" + physAdd + "','" + recurring + "','" + uk + "','" + oid + "')";
        int added = statement.executeUpdate(query);
        statement.close();   
        
        int i = 0;
        query = "SELECT max(recnum) FROM quotations";
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);
        
        String maxRec = "";
        while(results.next()) {
            maxRec = results.getString(1);
        }
        
        return maxRec;

    }
    
    public void saveRequisitionServices(String uk, String maxRec, String description, double cost, int qty, double amount, String recNum, String orgid) throws SQLException {
        
        Systems.closeConnection();
        connection = Systems.initConnection();
         
        statement = connection.createStatement();
        query = "INSERT INTO quotationitems(uk, quotationCheck, description, price, quantity, amount, servid, orgid) VALUES ('" + uk +"','" + maxRec +"','" + description + "','" + cost + "','" + qty + "','" + amount + "','" + recNum + "','" + orgid + "')";
        int added = statement.executeUpdate(query);
        statement.close();           
    }
    
    public void saveRequisitionInventory(String uk, String maxRec, String description, double cost, int qty, double amount, String recNum, String orgid) throws SQLException {
        
        Systems.closeConnection();
        connection = Systems.initConnection();
         
        statement = connection.createStatement();
        query = "INSERT INTO quotationitems(uk, quotationCheck, description, price, quantity, amount, invid, orgid) VALUES ('" + uk +"','" + maxRec +"','" + description + "','" + cost + "','" + qty + "','" + amount + "','" + recNum + "','" + orgid + "')";
        int added = statement.executeUpdate(query);
        statement.close();   
        
    }*/
    
/*    public String savePurchaseOrder(String recnum, String requisitionId, Date poDate, String supplierName, String tel, String cell, String fax, String email, String posAdd, String physAdd, String supplierId, String vatNum, String notes) throws SQLException {

        connection = Systems.initConnection();

        INSERT_DATA = "INSERT INTO PurchaseOrderOld(requisitionId, poDate, name, telephone, cellphone, fax, email, postaladdress, physicaladdress, cid, vatnum, notes)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        ps = connection.prepareStatement(INSERT_DATA);
        ps.setString(1, requisitionId);
        ps.setString(2, sdf.format(poDate));
        ps.setString(3, supplierName);
        ps.setString(4, tel);
        ps.setString(5, cell);                          
        ps.setString(6, fax);                          
        ps.setString(7, email);                          
        ps.setString(8, posAdd);                          
        ps.setString(9, physAdd);                          
        ps.setString(10, supplierId);                          
        ps.setString(11, vatNum);                          
        ps.setString(12, notes);                          

        ps.executeUpdate();

        connection.commit();

        int i = 0;
        query = "SELECT max(recnum) FROM PurchaseOrderOld";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        String maxRec = "";
        while (results.next()) {
            maxRec = results.getString(1);
        }

        return maxRec;

    }

    public List<Requisition> findRequisition(Date from, Date to) throws SQLException {

        filteredRequisitions.clear();

        Systems.closeConnection();
        connection = Systems.initConnection();

        String data = "";

        query = "SELECT * FROM invoices where InvoiceDate between '" + sdf.format(from) + "' and '" + sdf.format(to) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            //   filteredRequisitions.add(new Requisition(results.getString(1), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7), results.getString(8), results.getString(9), results.getString(21), results.getString(10), results.getString(11), results.getString(12), results.getString(13), results.getString(15), results.getDouble(20), results.getDouble(19), results.getString(22))); 

        }

        return filteredRequisitions;
    }
    
    public String getPurchaseOrder(String recnum) throws SQLException {
                
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM PurchaseOrderOld where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            ret = results.getString(1) + ',' + results.getString(2) + ',' + results.getDate(3) + ',' + results.getString(4) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(7) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(11) + ',' + results.getString(12) + ',' + results.getString(13);
        }

        return ret;
    }
    
    public List<PurchaseOrder> getPurchaseOrderItems(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM purchaseorderitems where purchaseorderid = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }

        return filteredPurchaseOrders;
    }
    
    public int deletePurchaseOrder(String poRecnum) throws SQLException {
        connection = Systems.initConnection();

        int added = 0;
        statement = connection.createStatement();
        query = "delete from PurchaseOrderOld where PURCHASE_ORDER_ID = '" + poRecnum + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        added = 0;
        statement = connection.createStatement();
        query = "delete from purchaseorderitems where purchaseorderid = '" + poRecnum + "'";
        added = statement.executeUpdate(query);
        statement.close();
        
        query = "SELECT max(recnum) FROM purchaseorderitems";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        while (results.next()) {
            maxRec = results.getInt(1);
        }

        query = "SELECT max(recnum) FROM PurchaseOrderOld";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        while (results.next()) {
            maxRec = results.getInt(1);
        }
        
        return maxRec;
    }

    public int editPurchaseInvoice(String uk, String func, String mod, String poRecnum, Date poDate, String requisitionId, String supplierId, List<PurchaseOrder> purchaseOrders) throws SQLException {

        String cDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cDate = sdf.format(new java.util.Date());

        int added = 0;

        connection = Systems.initConnection();

        if (!func.equals("")) {
            edit = true;
            if (func.equals("delete")) {

                if (mod.equals("yes")) {

                    added = 0;
                    statement = connection.createStatement();
                    query = "delete from PurchaseOrderOld where PURCHASE_ORDER_ID = '" + poRecnum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                } else {

                }

            } else {

               /* added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + requisitionId + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + supplierId + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + requestorName + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrderOld SET cid  = '" + remarks + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "delete from purchaseorderitems where requisitionid = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                int x = 1;
                statement = connection.createStatement();
                for (PurchaseOrderOld item : purchaseOrders) {

                    try {

                        query = "INSERT INTO purchaseorderitems(RequisitionId, ItemNo, ItemService, Quantity, "
                                + "EstUnitPrice, EstAmount) VALUES ('" + poRecnum + "','" + x + "','" + item.getItemService() + "','" + item.getQuantity() + "','"
                                + item.getEstUnitPrice() + "','" + item.getEstAmount() + "')";
                        added = statement.executeUpdate(query);

                        x++;

                    } catch (java.sql.SQLException sqle) {
                        System.out.println("Save Requisition: " + sqle);
                    }

                }
                statement.close();
*/
  /*          }

        } else {

            String ret = sp.getSupplierObject(supplierId);
            String[] retArr = ret.split(",");

            connection = Systems.initConnection();
            statement = connection.createStatement();

            try {
              
                INSERT_DATA = "INSERT INTO purchaseinvoice(requisitionId, piDate, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + "cid, vatNum) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, requisitionId);
                ps.setString(2, sdf.format(poDate));
                ps.setString(3, retArr[0]);
                ps.setString(4, retArr[1]);
                ps.setString(5, retArr[2]);                          
                ps.setString(6, retArr[3]);                          
                ps.setString(7, retArr[4]);                          
                ps.setString(8, retArr[5]);                          
                ps.setString(9, retArr[6]);                          
                ps.setString(10, retArr[7]);                          
                ps.setString(11, retArr[8]);

                ps.executeUpdate();

                connection.commit();
            } catch (Exception e) {

                INSERT_DATA = "INSERT INTO purchaseinvoice(requisitionId, piDate, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + "cid, vatNum) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, requisitionId);
                ps.setString(2, sdf.format(poDate));
                ps.setString(3, retArr[0]);
                ps.setString(4, retArr[1]);
                ps.setString(5, retArr[2]);                          
                ps.setString(6, retArr[3]);                          
                ps.setString(7, retArr[4]);                          
                ps.setString(8, retArr[5]);                          
                ps.setString(9, retArr[6]);                          
                ps.setString(10, retArr[7]);                          
                ps.setString(11, "0");

                ps.executeUpdate();

                connection.commit();

            }

            added = statement.executeUpdate(query);

            query = "SELECT max(recnum) FROM purchaseinvoice";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                maxRec = results.getInt(1);
            }

        }

        statement = connection.createStatement();
        for (PurchaseOrderOld item : purchaseOrders) {

            if (item.getItemService().length() > 0) {

                try {
                  
                    INSERT_DATA = "INSERT INTO purchaseinvoiceitems(purchaseinvoiceid, itemservice, quantity, "
                            + "estunitprice, estamount) VALUES (?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setInt(1, maxRec);
                    ps.setString(2, item.getItemService());
                    ps.setInt(3, item.getQuantity());
                    ps.setString(4, item.getEstUnitPrice());
                    ps.setString(5, item.getEstAmount());                          
                    
                    ps.executeUpdate();

                    connection.commit();

                } catch (Exception e) {
                    System.out.println("ERR: " + e);
                }

            }

        }

        statement.close();
        connection.close();

        return maxRec;

    }

    public List<PurchaseOrder> findInvoicePurchaseOrder(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM PurchaseOrderOld where poDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        filteredPurchaseOrders.clear();
        while (results.next()) {
            filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1),results.getString(2),results.getDate(3),results.getString(4),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getString(9),results.getString(10),results.getString(11),results.getString(12),results.getString(13)));
        }

        connection.close();

        return filteredPurchaseOrders;
    }
    
    public ResultSet getUploadedJustifications(int rId) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisitionjustification where requisitionId = '" + rId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);        

        return results;
    }
    
    public ResultSet getUploadedReceipts(int rId) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisitionreceipts where requisitionId = '" + rId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);        

        return results;
    }
    
    public String getPurchaseInvoice(String recnum) throws SQLException {
                
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM purchaseinvoice where recnum = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            ret = results.getString(1) + ',' + results.getString(2) + ',' + results.getString(3) + ',' + results.getDate(4) + ',' + results.getString(5) + ',' + results.getString(6) + ',' + results.getString(7) + ',' + results.getString(8) + ',' + results.getString(9) + ',' + results.getString(10) + ',' + results.getString(11) + ',' + results.getString(12) + ',' + results.getString(13) + ',' + results.getString(14);
        }

        return ret;
    }
    
    public List<PurchaseOrder> getPurchaseInvoiceItems(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM purchaseinvoiceitems where purchaseinvoiceid = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }

        return filteredPurchaseOrders;
    }

    public List<PurchaseOrder> getPurchaseOrders() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM PurchaseOrderOld order by poDate";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        filteredPurchaseOrders.clear();
        while (results.next()) {
            try {
                filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
            } catch (Exception e) {}
        }

        return filteredPurchaseOrders;
    }*/
}