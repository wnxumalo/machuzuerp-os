package com.machuzuerp.jdbc;

import com.machuzuerp.controllers.datatableprocessing.jsf.EmployeesData;
import com.machuzuerp.entities.dto.EmployeeDTO;
import com.machuzuerp.controllers.jsf.Systems;
import com.machuzuerp.controllers.jsf.Systems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */

public class EmployeesProcessing {

    Connection connection;
    Statement statement;
    ResultSet results;
    
    String INSERT_DATA = "";
    PreparedStatement ps = null;

    boolean edit = false;
    boolean proceed = true;
    
    public String recNum = "";
    public String idNumber = "";
    public String taxNumber = "";
    public String employeeNumber = "";
    public String employmentDate;
    public String empName = "";
    public String surname = "";
    public String level = "";
    public String gender = "";
    public String telephone = "";
    public String cellphone = "";
    public String email = "";
    public String postalAddress = "";
    public String physicalAddress = "";
    public float salary = 0;
    public String salaryInterval = "";
    public String status = "";
    public String position = "";
    public String approver = "";
    public String department = "";
    public int supervisorId = 0;
    
    // AssignedActivities
    public int activityRecNum;
    public int activityId;
    public String employeeId;
    public String employeeName;
    public String employeeSurname;
    public String role;    
    
    public String bankName = "";
    public String accountName = "";
    public String accountNumber = "";
    public String branchCode = "";
    
    String query = "";
    String msg = "";
    
    protected List<EmployeesData> selectedApprovers = new ArrayList<EmployeesData>(); 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sppf = new SimpleDateFormat("yyyy-MM");

    public EmployeesProcessing() {
                
    }

    public String editEmployees(String uk, String func, String mod, String clientRecNo, EmployeeDTO supervisor) {
        try {

            connection = Systems.initConnection();
           System.out.println("SAVE EMP: " + supervisor.getName());
            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "delete from Employee where recnum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();           
                    
                    msg = "Deleted";

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET idnumber  = '" + idNumber + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET taxnumber  = '" + taxNumber + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET employeenumber  = '" + employeeNumber + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET employmentdate  = '" + employmentDate + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET empname  = '" + empName + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET surname = '" + surname + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();    

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET emp_level = '" + level + "' Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close(); 

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET gender  = '" + gender + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET telephone  = '" + telephone + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET cellphone  = '" + cellphone + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET email  = '" + email + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET postaladdress  = '" + postalAddress + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET physicaladdress  = '" + physicalAddress + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET salary  = '" + salary + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET empbankname  = '" + bankName + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET branchCode  = '" + branchCode + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET empbankaccnum  = '" + accountNumber + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET empbankaccname  = '" + accountName + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET salaryinterval  = '" + salaryInterval + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET status  = '" + status + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    try {

                        added = 0;
                        statement = connection.createStatement();
                        query = "UPDATE Employee SET supervisor_id  = '" + supervisor.getId() + "'Where RecNum = '" + clientRecNo + "'";
                        added = statement.executeUpdate(query);
                        statement.close();

                    } catch (Exception e) {
                        System.out.println("333: " + e);
                    }
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET position  = '" + position + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET approver  = '" + approver + "' Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET department  = '" + department + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET department  = '" + department + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE Employee SET department  = '" + department + "'Where RecNum = '" + clientRecNo + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                    
                    msg = "Updated";                

                }


            } else {
System.out.println("SAVE EMP111: " + supervisor.getName() + ":" + idNumber);
                int i = 0;
                query = "SELECT * FROM Employee where idnumber = '" + idNumber + "'";
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    recNum = results.getString(1);
                }

                try {
System.out.println("SAVE EMP222: " + supervisor.getName() + ":" + proceed);
                    if ((proceed == true)) {
                        
                        connection.setAutoCommit(false);

                        INSERT_DATA = "INSERT INTO Employee(uuid, idnumber, taxnumber, employeenumber, employmentdate, empname, surname, position, approver, emp_level, gender, "
                                + "telephone, cellphone, email,postalAddress, physicalAddress, salary, salaryInterval, status, supervisor_id, department,password,"
                                + "empbankname,empbankaccname,empbankaccnum,branchcode) VALUES "
                                    + " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setString(1, uk);
                        ps.setString(2, idNumber);
                        ps.setString(3, taxNumber);                                       
                        ps.setString(4, employeeNumber);                                       
                        ps.setString(5, employmentDate);
                        ps.setString(6, empName);
                        ps.setString(7, surname);
                        ps.setString(8, position);
                        ps.setString(9, approver);
                        ps.setString(10, level);
                        ps.setString(11, gender);
                        ps.setString(12, telephone);
                        ps.setString(13, cellphone);
                        ps.setString(14, email);
                        ps.setString(15, postalAddress);
                        ps.setString(16, physicalAddress);
                        ps.setDouble(17, salary);
                        ps.setString(18, salaryInterval);
                        ps.setString(19, status);
                        ps.setInt(20, supervisorId);
                        ps.setString(21, department);
                        ps.setString(22, "temppass");
                        ps.setString(23, bankName);
                        ps.setString(24, accountName);
                        ps.setString(25, accountNumber);
                        ps.setString(26, branchCode);
                        
                        ps.executeUpdate();

                        connection.commit();

                        msg = "Saved";

                    } else {
                        
                        msg = "Exists";

                    }

                } catch (SQLException np) {
                        System.out.println(np);
                }

            }

        } catch (Exception sqle) {
                        System.out.println(sqle);

        }
        
        return msg;
    }

    public String editAssignedActivity(String uk, String func, List<EmployeesData> employees, int activityRecNum) {
        try {

            connection = Systems.initConnection();
           
            if (!func.equals("")) {

                edit = true;
                if (func.equals("delete")) {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "delete from activity_assigned where recnum = '" + activityRecNum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();           
                    
                    msg = "Deleted";

                } else {

                    int added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE activity_assigned SET idnumber  = '" + idNumber + "'Where RecNum = '" + activityRecNum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE activity_assigned SET taxnumber  = '" + taxNumber + "'Where RecNum = '" + activityRecNum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE activity_assigned SET employeenumber  = '" + employeeNumber + "'Where RecNum = '" + activityRecNum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();

                    added = 0;
                    statement = connection.createStatement();
                    query = "UPDATE activity_assigned SET employmentdate  = '" + employmentDate + "'Where RecNum = '" + activityRecNum + "'";
                    added = statement.executeUpdate(query);
                    statement.close();
                   
                    msg = "Updated";

                }

            } else {

                try {
                    
                    connection.setAutoCommit(false);

                    for (EmployeesData emp : employees) {
                        INSERT_DATA = "INSERT INTO activity_assigned(activity_id, employee_id, employee_name, employee_surname, role) VALUES "
                            + " (?,?,?,?,?)";
                        ps = connection.prepareStatement(INSERT_DATA);
                        ps.setInt(1, activityRecNum);
                        ps.setString(2, emp.getRecNum());
                        ps.setString(3, emp.getEmpName());                                       
                        ps.setString(4, emp.getEmpSurname());                                       
                        ps.setString(5, emp.getPosition());                                                

                        ps.executeUpdate();

                        connection.commit();

                    }
                    
                    msg = "Saved";

                } catch (SQLException np) {
                        System.out.println(np);
                }

            }

        } catch (Exception sqle) {

        }
        
        return msg;
    }
    
    public ResultSet getEmployeeByEmpNum(String employeeNumber) throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM Employee where employeenumber = '" + employeeNumber + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        return results;

    }
    
    public void getEmployee(String recnum) throws SQLException {

       // connection = Systems.initConnection();
       connection = Systems.connDatabase();
            
        int i = 0;
        query = "SELECT * FROM Employee where employee_id = '" + recnum + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) { 
            
            recNum = results.getString(1);
            idNumber = results.getString(3);
            taxNumber = results.getString(4);
            employeeNumber = results.getString(5);
            employmentDate = results.getString(6);
            empName = results.getString(7);
            surname = results.getString(8);
            level = results.getString(19);
            gender = results.getString(9);
            telephone = results.getString(10);
            cellphone = results.getString(11);
            email = results.getString(12);
            postalAddress = results.getString(13);
            physicalAddress = results.getString(14);
            salary = results.getFloat(15);
            salaryInterval = results.getString(16);
            status = results.getString(17);
            position = results.getString(21);
            approver = results.getString(22);

        }
        
    }
    
    public ResultSet getEmployeeProjectManager(String projectId, String empId) throws SQLException {

       // connection = Systems.initConnection();
       connection = Systems.connDatabase();
            
        int i = 0;
        query = "SELECT * FROM project_manager where employee_id = '" + empId + "' and project_id = '" + projectId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
            System.out.println("EMP: " + empId + ":" + projectId);
        return results;
    }
    
    public ResultSet getEmployeeStakeholder(String projectId, String empId) throws SQLException {

       // connection = Systems.initConnection();
       connection = Systems.connDatabase();
            
        int i = 0;
        query = "SELECT * FROM project_stakeholder where client_partner_id = '" + empId + "' and project_id = '" + projectId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
            
        return results;
    }
    
    public boolean checkEmployee(String userkey) throws SQLException {

        boolean exists = false;

        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM Employee where EMPLOYEE_ID = '" + userkey + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {             
            exists = true;
        }
        
        System.out.println(userkey + ":" + exists);
        return exists;
    }
    
    public void editEmployee(String activityId, int recNum, String empName, String empSurname) throws SQLException {
        
        boolean found = false;
      
        connection = Systems.initConnection();
        
        int i = 0;
        query = "SELECT * FROM task_assigned where activity_id = '" + activityId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        while (results.next()) {
            
            int added = 0;                        
            statement = connection.createStatement();
            query = "UPDATE task_assigned SET employee_id  = '" + recNum + "'Where activity_id = '" + activityId + "'";
            added = statement.executeUpdate(query);
            statement.close();

            added = 0;
            statement = connection.createStatement();
            query = "UPDATE task_assigned SET employee_name  = '" + empName + "' Where activity_id = '" + activityId + "'";
            added = statement.executeUpdate(query);
            statement.close(); 
            
            added = 0;
            statement = connection.createStatement();
            query = "UPDATE task_assigned SET employee_surname  = '" + empSurname + "' Where activity_id = '" + activityId + "'";
            added = statement.executeUpdate(query);
            statement.close(); 
            
            found = true;
            
        }
        
        if (found == false) {
            
            connection.setAutoCommit(false);
            
            INSERT_DATA = "INSERT INTO task_assigned(activity_id, employee_id, employee_name, employee_surname) VALUES "
                    + " (?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, activityId);
            ps.setInt(2, recNum);
            ps.setString(3, empName);                                       
            ps.setString(4, empSurname);                                                                                   

            ps.executeUpdate();

            connection.commit();

        }
                
    }
        
    public ResultSet getEmployeess() throws SQLException {

        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT recnum,name,surname FROM clients order by name, surname";
        statement = connection.createStatement();
        results = statement.executeQuery(query);

        return results;        
    }       
    
    public ResultSet getActivityAssignedEmployees(String activityId) throws SQLException {
        
        connection = Systems.initConnection();
            
        int i = 0;
        query = "SELECT * FROM activity_assigned where activity_id = '" + activityId + "'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);       
        
        return results;
    }

    public List<EmployeesData> getApprovers() throws SQLException {

        connection = Systems.initConnection();

        query = "SELECT * FROM Employee where approver = 'Yes'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
//int x =1;
        while (results.next()) {
            //System.out.println("SURNME: " + results.getString(26));
            selectedApprovers.add(new EmployeesData(results.getString(1), results.getString(16), results.getString(27), results.getString(13), 
                    results.getString(14), results.getString(9), results.getString(26), results.getString(20), results.getString(15), results.getString(28), results.getString(4), 
                    results.getString(7), results.getString(21), /*"physicaladdress"*/ "",results.getFloat(22), results.getString(23), results.getString(8), 
                    results.getString(24), results.getString(2), results.getString(6)));    
            //System.out.println("SURNME111: " + selectedApprovers.get(x).getEmpSurname() + ":" + results.getString(26));
            /*EmployeesData(String recNum, String idNumber, String taxNumber, String employeeNumber, String employmentDate, String empName,
            String surname, String position, String gender, String telephone, String cellphone, String email, String postalAddress, String physicalAddress,
            float salary, String salaryInterval, String level, String status, String approver, String department) {*/
//x++;
        }
        
        return selectedApprovers;
    }
        
    public ResultSet getPayroll(Date month) throws SQLException {
        
        connection = Systems.initConnection();

        int i = 0;
        query = "SELECT * FROM payroll where paydate like '%" + sppf.format(month) + "%'";
        statement = connection.createStatement();
        results = statement.executeQuery(query);       
        
        return results;
    }
    
    public void insertPayroll(String selectedAccount, java.util.Date payDate, double benefits, double deductions, double totPackage, String recNum, String idNumber, String taxNumber,
            String employeeNumber, String employmentDate, String empName, String surname, String position, 
            String gender, String telephone, String cellphone, String email, String postalAddress, String physicalAddress, String salary, String salaryInterval,
            String status, String level, String approver, String department, String EmpBankName, String EmpBankAccName, String EmpBankAccNum, String supervisor_id) throws SQLException {
                    
            String[] account = selectedAccount.split(",");
            connection = Systems.connDatabase();
            
            connection.setAutoCommit(false);
            System.out.println("EID: " + idNumber);
            INSERT_DATA = "INSERT INTO payroll(EmployeeID, PayDate, PaidFrom, Name,Surname,EmpBankName,EmpBankAccName,EmpBankAccNum,TotDeductions,TotBenefits,GrossPay,NettPay) VALUES "
                    + " (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, idNumber);
            ps.setString(2, sdf.format(payDate));
            ps.setString(3, account[0]);
            ps.setString(4, empName);
            ps.setString(5, surname);
            ps.setString(6, account[0]);
            ps.setString(7, EmpBankAccName);
            ps.setString(8, EmpBankAccNum);
            ps.setDouble(9, deductions);
            ps.setDouble(10, benefits);
            ps.setDouble(11, totPackage);
            ps.setDouble(12, (totPackage+benefits)-deductions);
            

            ps.executeUpdate();
                        
            connection.commit();
            
            
       /*
                
    CREATE TABLE `payroll` (
  `RecNum` int NOT NULL AUTO_INCREMENT,
  `EmployeeID` int DEFAULT NULL,
  `PayDate` date DEFAULT NULL,
  `PaidFrom` varchar(20) DEFAULT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Surname` varchar(20) DEFAULT NULL,
  `EmpBankName` varchar(20) DEFAULT NULL,
  `EmpBankAccName` varchar(20) DEFAULT NULL,
  `EmpBankAccNum` varchar(20) DEFAULT NULL,
  `TotDeductions` float DEFAULT NULL,
  `TotBenefits` float DEFAULT NULL,
  `GrossPay` float DEFAULT NULL,
  `NettPay` float DEFAULT NULL,
  `AnnualLeave` float DEFAULT NULL,
  `CompasionateLeave` float DEFAULT NULL,
  `SickLeave` float DEFAULT NULL,
  `StudyLeave` float DEFAULT NULL,
  PRIMARY KEY (`RecNum`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
    }
    */
       
    }
    
}
