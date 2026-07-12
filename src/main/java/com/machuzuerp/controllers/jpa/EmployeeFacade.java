/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
    
    public Employee getEmployee(Long id) {

        Employee employee = null;
        
        try {
            employee = em.createNamedQuery("Employee.getEmployee", 
                Employee.class)
                    .setParameter("id", id)            
                .getSingleResult();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return employee;
    }
    
    public List<Employee> getOrganisationEmployees(Long id) {

        List<Employee> employees = null;
        
        try {
            employees = em.createNamedQuery("Employee.getOrganisationEmployees", 
                Employee.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return employees;
    }
    

}
