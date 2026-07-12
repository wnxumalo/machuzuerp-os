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
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.entities.dto.PurchaseOrderDTO;
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
public class RequisitionProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    ResultSet results2;
    ResultSet results3;

    boolean edit = false;
    boolean proceed = true;
    public Date requestDate;
    public String itemNo;
    public String itemService;
    public int quantity;
    public int unit;
    public String estUnitPrice;
    public String estAmount;

    public String rRecNum;
    public Date requestedDeliveryDate;
    public Long deliveryEmployeeId;
    public String deliveryEmployeeName;
    public String soleSourceJustificationAttached;
    public String requestorName;
    public String accountsManagerName;
    public Long accountsManagerId;
    public String availableFunds;
    public String accountingData;
    public String approverName;
    public String approverJobTitle;
    public Date approvalDate;
    public Long supplierId;
    public String remarks;
    public String status;

    int maxRec = 0;

    String query = "";

    String ret = "";
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    public List<Requisition> filteredRequisitions = new ArrayList<Requisition>();
    List<RequisitionData> requisitions = new ArrayList<RequisitionData>();
    List<PurchaseOrderOld> filteredPurchaseOrders = new ArrayList<PurchaseOrderOld>();  
    List<SupplierData> filteredSuppliers = new ArrayList<SupplierData>();  
    public List<EmployeesData> approvers = new ArrayList<EmployeesData>();  
    public SupplierList supplier;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
    
    SupplierProcessing sp = new SupplierProcessing();   
    
    String approver;

    public RequisitionProcessing() {

    }

    public int editRequisition(String uk, String func, String mod, String requisitionRecNo) throws SQLException {
        
        int x = 1;
        
        try {

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

                    int added = 0;
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
                    

                }

            } else {

                connection.setAutoCommit(false);

                INSERT_DATA = "INSERT INTO requisition(requestDate, requestedDeliveryDate, deliveryEmployeeId, "
                + "deliveryEmployeeName,soleSourceJustificationAttached, requestorName, accountsManagerId, accountsManagerName, "
                + "remarks, supplierid, suppliername, status, uk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, sdf.format(requestDate));
                ps.setString(2, sdf.format(requestedDeliveryDate));
                ps.setLong(3, deliveryEmployeeId);
                ps.setString(4, null);
                ps.setString(5, soleSourceJustificationAttached);
                ps.setString(6, requestorName);
                ps.setLong(7, accountsManagerId);
                ps.setString(8, null);
                ps.setString(9, remarks);
                ps.setLong(10, supplierId);
                ps.setString(11, null);
                ps.setString(12, status);
                ps.setString(13, null);                                

                ps.executeUpdate();

                connection.commit();

                query = "SELECT max(recnum) FROM requisition";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                while (results.next()) {
                    maxRec = results.getInt(1);
                }
                
                connection.setAutoCommit(false);
                
                for (Requisition item : filteredRequisitions) {
System.out.println("REQS: " + item.getItemService() + ":" + item.getQuantity());
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
                        ps.setString(2, item.getRecNum());
                        ps.setString(3, item.getEmpName());
                        ps.setString(4, item.getEmpSurname());
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

            statement.close();
            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return maxRec;

    }

    public String updateRequisitionApproval(int approvalId, String employeeId, float availableFunds, String approvalStatus, String comments) throws SQLException {

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
                        
                added = 0;
                statement = connection.createStatement();
                query = "UPDATE requisition SET status  = '" + approvalStatus + "'Where RecNum = '" + getRequisitionByApproval(approvalId) + "'";
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

    public int editPurchaseOrder(String uk, String func, String mod, String poRecnum, String requisitionId, String supplierId, Date poDate, String requestorName, String remarks, List<PurchaseOrderOld> purchaseOrders) throws SQLException {

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
                    query = "delete from PurchaseOrder where PURCHASE_ORDER_ID = '" + poRecnum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                } else {

                }

            } else {

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrder SET cid  = '" + requisitionId + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrder SET cid  = '" + supplierId + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrder SET cid  = '" + requestorName + "' Where RecNum = '" + poRecnum + "'";
                added = statement.executeUpdate(query);
                statement.close();

                added = 0;
                statement = connection.createStatement();
                query = "UPDATE PurchaseOrder SET cid  = '" + remarks + "' Where RecNum = '" + poRecnum + "'";
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
            String[] retArr = ret.split("~");

            connection = Systems.initConnection();
            connection.setAutoCommit(false);
            try {               
                
                INSERT_DATA = "INSERT INTO PurchaseOrder(requisitionId, poDate, notes, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + "vatNum, requestor, uk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setString(1, requisitionId);
                    ps.setString(2, sdf.format(poDate));
                    ps.setString(3, remarks);
                    ps.setString(4, retArr[0]);
                    ps.setString(5, retArr[1]);
                    ps.setString(6, retArr[2]);                           
                    ps.setString(7, retArr[3]);                           
                    ps.setString(8, retArr[4]);                           
                    ps.setString(9, retArr[5]);                           
                    ps.setString(10, retArr[6]);                           
                    ps.setString(11, retArr[7]);
                    ps.setString(12, requestorName);
                    ps.setString(13, uk);

                    ps.executeUpdate();

                    connection.commit();
            } catch (Exception e) {               
                
                connection = Systems.initConnection();
                connection.setAutoCommit(false);
                
                INSERT_DATA = "INSERT INTO PurchaseOrder(requisitionId, poDate, notes, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + "vatNum, requestor, uk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(INSERT_DATA);
                    ps.setString(1, requisitionId);
                    ps.setString(2, sdf.format(poDate));
                    ps.setString(3, remarks);
                    ps.setString(4, retArr[0]);
                    ps.setString(5, retArr[1]);
                    ps.setString(6, retArr[2]);                           
                    ps.setString(7, retArr[3]);                           
                    ps.setString(8, retArr[4]);                           
                    ps.setString(9, retArr[5]);                           
                    ps.setString(10, retArr[6]);                           
                    ps.setString(11, retArr[7]);                           
                    ps.setString(12, requestorName);
                    ps.setString(13, uk);        

                    ps.executeUpdate();

                    connection.commit();
                
            }

            //added = statement.executeUpdate(query);

            query = "SELECT max(recnum) FROM PurchaseOrder";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                maxRec = results.getInt(1);
            }

        }

        connection = Systems.initConnection();
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

                } catch (Exception e) {
                    System.out.println("ERR: " + e);
                }

            }

        }

        connection.commit();
      //  statement.close();
    //    connection.close();

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
    
    public ResultSet getOpenRequisitions() throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisition where status <> 'Closed'";
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

            query = "SELECT * FROM requisitionapprovals where requisitionid = '" + results.getString(1) + "'";
            statement = connection.createStatement();
            results1 = statement.executeQuery(query);

            approver = "";
            while (results1.next()) {
                if (approver.equals(""))
                    approver = results1.getString(4) + " " + results1.getString(5);
                else
                    approver += "," + results1.getString(4) + " " + results1.getString(5);
            }

            query = "SELECT empName, surname FROM Employee where employee_id = '" + results.getString(11) + "'";
            statement = connection.createStatement();
            results2 = statement.executeQuery(query);

            while (results2.next()) {
                this.deliveryEmployeeName = results2.getString(1) + " " + results2.getString(2);
            }
            
            query = "SELECT empName, surname FROM Employee where employee_id = '" + results.getString(9) + "'";
            statement = connection.createStatement();
            results3 = statement.executeQuery(query);

            while (results3.next()) {
                this.accountsManagerName = results3.getString(1) + " " + results3.getString(2);
            }

            requisitions.add(new RequisitionData(results.getString(1),results.getDate(2),deliveryEmployeeName,results.getString(5),accountsManagerName,results.getDate(3),results.getString(13), approver, results.getLong(7)));
            
//public RequisitionData(String recnum, Date requestDate, String deliverTo, String requestorName, String accountsManagerName, Date requestedDeliveryDate, String status, String approverName, Long supplierId) {            
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
            supplierId = results.getLong(7);
            /*try {
                approvalDate = results.getDate(12);
            } catch (NullPointerException | java.sql.SQLException npe) {
                approvalDate = null;
            }*/
            remarks = results.getString(6);

        }       

    }

    public ResultSet getRequisitionApprovals(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM requisitionapprovals where requisitionid = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;
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
    
    public int getRequisitionByApproval(int approvalId) throws SQLException {

       int reqId = 0;

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT requisitionid FROM requisitionapprovals where recnum = '" + approvalId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        while (results.next()) {
            reqId = results.getInt(1);
        }

        return reqId;
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
    
    public String savePurchaseOrder(String recnum, String requisitionId, Date poDate, String supplierName, String tel, String cell, String fax, String email, String posAdd, String physAdd, String supplierId, String vatNum, String notes) throws SQLException {

        connection = Systems.initConnection();

        INSERT_DATA = "INSERT INTO PurchaseOrder(requisitionId, poDate, name, telephone, cellphone, fax, email, postaladdress, physicaladdress, cid, vatnum, notes)"
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
        query = "SELECT max(recnum) FROM PurchaseOrder";
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
        query = "SELECT * FROM PurchaseOrder where PURCHASE_ORDER_ID = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            sp.getSupplier(results.getString(18));
            ret = results.getString(1) + ',' + results.getString(12) + ',' + results.getDate(10) + ',' + sp.supplierName + ',' + sp.telephone + ',' + sp.cellphone + ',' + 
                    sp.fax + ',' + sp.email + ',' + sp.postalAddress + ',' + sp.physicalAddress + ',' + sp.supplierRecNum + ',' + sp.vatNum + ',' + "";
        }

        return ret;
    }
    
    public String getPurchaseOrderDates(Date dFrom, Date dTo) throws SQLException {
                
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM PurchaseOrder where poDate between '" + dFrom + "' and '" + dTo + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            sp.getSupplier(results.getString(18));
            ret = results.getString(1) + ',' + results.getString(12) + ',' + results.getDate(10) + ',' + sp.supplierName + ',' + sp.telephone + ',' + sp.cellphone + ',' + 
                    sp.fax + ',' + sp.email + ',' + sp.postalAddress + ',' + sp.physicalAddress + ',' + sp.supplierRecNum + ',' + sp.vatNum + ',' + "";
        }

        return ret;
    }
    
    public List<PurchaseOrderOld> getPurchaseOrderItems(String recnum) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM purchaseorderitems where purchaseorderid = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(3), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
        }

        return filteredPurchaseOrders;
    }
    
    public int deletePurchaseOrder(String poRecnum) throws SQLException {
        connection = Systems.initConnection();

        int added = 0;
        statement = connection.createStatement();
        query = "delete from PurchaseOrder where PURCHASE_ORDER_ID = '" + poRecnum + "'";
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

        query = "SELECT max(recnum) FROM PurchaseOrder";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        while (results.next()) {
            maxRec = results.getInt(1);
        }
        
        return maxRec;
    }

    public int editPurchaseInvoice(String uk, String func, String mod, String poRecnum, PurchaseOrderDTO purchaseOrder, String poId) throws SQLException {
    //public int editPurchaseInvoice(String uk, String func, String mod, String piRecnum, String requisitionId, String supplierId, String poDate, String requestorName, String remarks, List<PurchaseOrder> purchaseOrders) throws SQLException {

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
                    query = "delete from purchaseinvoice where recnum = '" + poRecnum + "'";
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
            }

        } else {


            connection = Systems.initConnection();
            //statement = connection.createStatement();
            connection.setAutoCommit(false);
            
            try {                              
                
                INSERT_DATA = "INSERT INTO purchaseinvoice(requisitionId, piDate, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + "vatNum) VALUES (?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, purchaseOrder.getRequisitionId().toString());
                ps.setString(2, sdf.format(purchaseOrder.getPODate()));
                ps.setString(3, purchaseOrder.getSupplierName());
                /*ps.setString(4, purchaseOrder.getTel());
                ps.setString(5, purchaseOrder.getCell());                          
                ps.setString(6, purchaseOrder.getFax());                          
                ps.setString(7, purchaseOrder.getEmail());                          
                ps.setString(8, purchaseOrder.getPosAdd());                          
                ps.setString(9, purchaseOrder.getPhysAdd());                          
                ps.setString(10, purchaseOrder.getVatNum());                          */
                ps.setString(4, "");
                ps.setString(5, "");                          
                ps.setString(6, "");                          
                ps.setString(7, "");                          
                ps.setString(8, "");                          
                ps.setString(9, "");                          
                ps.setString(10, "");                          

                ps.executeUpdate();

                connection.commit();
                
            } catch (Exception e) {

                INSERT_DATA = "INSERT INTO purchaseinvoice(requisitionId, piDate, name, "
                        + "telephone, cellphone, fax, email, postaladdress, physicaladdress, "
                        + " vatNum) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(INSERT_DATA);
                ps.setString(1, purchaseOrder.getRequisitionId().toString());
                ps.setString(2, sdf.format(purchaseOrder.getPODate()));
                ps.setString(3, purchaseOrder.getSupplierName());
                /*ps.setString(4, purchaseOrder.getTel());
                ps.setString(5, purchaseOrder.getCell());                          
                ps.setString(6, purchaseOrder.getFax());                          
                ps.setString(7, purchaseOrder.getEmail());                          
                ps.setString(8, purchaseOrder.getPosAdd());                          
                ps.setString(9, purchaseOrder.getPhysAdd());                          
                ps.setString(10, purchaseOrder.getVatNum());                          */
                ps.setString(4, "");
                ps.setString(5, "");                          
                ps.setString(6, "");                          
                ps.setString(7, "");                          
                ps.setString(8, "");                          
                ps.setString(9, "");                          
                ps.setString(10, "");                          

                ps.executeUpdate();

                connection.commit();

            }

        }

        query = "SELECT max(recnum) FROM purchaseinvoice";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            maxRec = results.getInt(1);
        }
        
        filteredPurchaseOrders = findInvoicePurchaseOrderItems(purchaseOrder.getId().toString());

        //statement = connection.createStatement();
        connection = Systems.initConnection();
        connection.setAutoCommit(false);
        for (PurchaseOrderOld item : filteredPurchaseOrders) {

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

        connection.close();

        return maxRec;

    }    

    public List<PurchaseOrderOld> findInvoicePurchaseOrder(Date dateFrom, Date dateTo) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        String query = "SELECT * FROM PurchaseOrder where poDate between '" + sdf.format(dateFrom) + "' and '" + sdf.format(dateTo) + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        filteredPurchaseOrders.clear();
        while (results.next()) {
            filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1),results.getString(2),results.getDate(3),results.getString(4),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getString(9),results.getString(10),results.getString(11),results.getString(12),results.getString(13)));
        }

        connection.close();

        return filteredPurchaseOrders;
    }
    
    public List<PurchaseOrderOld> findInvoicePurchaseOrderItems(String poId) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM purchaseorderitems where purchaseorderid = '" + poId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        filteredPurchaseOrders.clear();
        while (results.next()) {
            filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
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
    
    public List<PurchaseOrderOld> getPurchaseInvoiceItems(String recnum) throws SQLException {

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

    public List<PurchaseOrderOld> getPurchaseOrders() throws SQLException {

        connection = Systems.initConnection();

        int i = 1;
        query = "SELECT * FROM PurchaseOrder order by poDate";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        filteredPurchaseOrders.clear();
        while (results.next()) {
            
            try {
                System.out.println("111: " + results.getString(1) + "," + results.getString(4) + "," + results.getInt(5) + "," + results.getString(6) + "," + results.getString(7));
                filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(4), results.getInt(5), results.getString(6), ""));
            } catch (Exception e) {
            System.out.println("ERR: " + e.getMessage());
            }
            i++;
        }

        //public PurchaseOrderOld(String itemNo, String itemService, int quantity, String estUnitPrice, String estAmount) {

        return filteredPurchaseOrders;
    }

    public List<PurchaseOrderOld> getPIPurchaseOrders(Date dFrom, Date dTo) throws SQLException {

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM PurchaseOrder where poDate between '" + sdf.format(dFrom) + "' and '" + sdf.format(dTo) + "' order by poDate";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        filteredPurchaseOrders.clear();
        while (results.next()) {
            try {
                System.out.println("000222: " + results.getString(6));
                //filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1),results.getString(2),results.getDate(3),results.getString(4),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getString(9),results.getString(10),results.getString(11),results.getString(12),results.getString(13)));
                filteredPurchaseOrders.add(new PurchaseOrderOld(results.getString(1), results.getString(4), results.getInt(5), results.getString(6), results.getString(7)));
            } catch (Exception e) {
                System.out.println("ERR: " + e);
            }
        }

        return filteredPurchaseOrders;
    }
}