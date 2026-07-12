package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.dto.EmployeeDTO;
import static com.machuzuerp.controllers.jsf.Systems.connection;
import static com.machuzuerp.controllers.jsf.Systems.statement;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class EmployeesBean implements Serializable {

    private EmployeeDTO selectedEmployee;
    private List<EmployeeDTO> employees;
    ResultSet results;

    public EmployeesBean(){
        employees = new ArrayList<EmployeeDTO>();
        
        employees.clear();
        employees.add(new EmployeeDTO(Long.parseLong("0"), "N/A", "N/A", "N/A", "N/A", "N/A", new BigDecimal(0)));
        try {

            String query = "";
            String employeeRecNum = "";
            boolean proceed = false;         
              
                query = "SELECT employee_id, idnumber, empname, surname, position, cellphone, salary FROM employee order by empname, surname";                
                statement = connection.createStatement();
                results = statement.executeQuery(query);

                proceed = true;
                while (results.next()) {
                    proceed = false;
                    employeeRecNum = results.getString(1);
                    employees.add(new EmployeeDTO(Long.parseLong(results.getString(1)), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getBigDecimal(7)));

                    //public EmployeeDTO(Long id, String idNumber, String name, String surname, String position, String cellphone) {
                }

        } catch (Exception sqle) {
            System.out.println(sqle);
        }                          
    }

    public EmployeeDTO getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(EmployeeDTO selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public EmployeeDTO getEmployees(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (EmployeeDTO emps : employees){
            if (id.equals(emps.getId())){
                return emps;
            }
        }
        return null;
    }

    
}