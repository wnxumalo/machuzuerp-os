/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Project;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class EmployeesProjects implements Serializable {

    Connection connection;
    Statement statement;
    ResultSet results;
    ResultSet results1;
    ResultSet results2;

    private String recNum;
    private String idNumber;
    private String taxNumber;
    private String employeeNumber;
    private String employmentDate;
    private String empName;
    private String surname;    
    private String project;
    private String description;
    private java.util.Date plannedStart;    
    private java.util.Date plannedEnd;        
    private java.util.Date actualStart;    
    private java.util.Date actualEnd;        
    private String status;
    private String progress;

    private String byIDNumber;
    int count = 9;
    private String byName;
    private String bySurname;

    private List<EmployeesProjects> employees = new ArrayList<EmployeesProjects>();   
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
    
    String query = "";
    
    public EmployeesProjects() {
    }

    public EmployeesProjects(String idNumber,String employeeNumber, String empName, String surname, String project, String description, java.util.Date plannedStart, java.util.Date plannedEnd, java.util.Date actualStart, java.util.Date actualEnd, String status, String progress) {

        this.idNumber = idNumber;
        this.employeeNumber = employeeNumber;
        this.empName = empName;
        this.surname = surname;
        this.project = project;
        this.description = description;
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.actualStart = actualStart;
        this.actualEnd = actualEnd;
        this.status = status;
        this.progress = progress;
                
    }
    
    public List<EmployeesProjects> searchProjects(Project project) throws SQLException {

        employees.clear();
        try {

            connection = Systems.initConnection();
            
            query = "SELECT * FROM employees order by empname, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {   

                query = "SELECT * FROM project_manager where employee_id = '" + results.getString(1) + "'";
                statement = connection.createStatement();
                results1 = statement.executeQuery(query);

                while (results1.next()) {

                    query = "SELECT * FROM project where recnum = '" + results1.getString(2) + "'";
                    statement = connection.createStatement();
                    results2 = statement.executeQuery(query);

                    while (results2.next()) {                        
                        employees.add(new EmployeesProjects(results.getString(1), results.getString(5), results.getString(7), results.getString(8), results2.getString(2), results2.getString(7), results2.getDate(3), results2.getDate(4), results2.getDate(5), results2.getDate(6), results2.getString(10), results2.getString(11)));
                    }
                }

            }

            connection.close();

        } catch (Exception sqle) {
            System.out.println(sqle);
        }

        return employees;
    }

    public List<EmployeesProjects> getEmployees() {
        return employees;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getIDNumber() {
        return idNumber;
    }
    
    public void setIDNumber(String idNumber) {
        this.idNumber = idNumber;
    }   
    
    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }              
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }           
    
    public String getEmploymentDate() {
        return employmentDate;
    }
    
    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }    
    
    public String getEmpName() {
        return empName;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }               
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }
    
    public void setProject(String project) {
        this.project = project;
    }

    public String getProject() {
        return project;
    } 
                      
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }        
    
    public void setPlannedStart(java.util.Date plannedStart) {
        this.plannedStart = plannedStart;
    }

    public java.util.Date getPlannedStart() {
        return plannedStart;
    }
    
    public void setPlannedEnd(java.util.Date plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public java.util.Date getPlannedEnd() {
        return plannedEnd;
    }  
    
    public void setActualStart(java.util.Date actualStart) {
        this.actualStart = actualStart;
    }

    public java.util.Date getActualStart() {
        return actualStart;
    }
    
    public void setActualEnd(java.util.Date actualEnd) {
        this.actualEnd = actualEnd;
    }

    public java.util.Date getActualEnd() {
        return actualEnd;
    }           
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }       
    
    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return progress;
    }    

}
