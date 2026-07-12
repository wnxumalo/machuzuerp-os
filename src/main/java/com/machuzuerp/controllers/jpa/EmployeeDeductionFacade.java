/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.EmployeeDeduction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class EmployeeDeductionFacade extends AbstractFacade<EmployeeDeduction> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeDeductionFacade() {
        super(EmployeeDeduction.class);
    }

    public List<EmployeeDeduction> getEmployeeDeduction(Long id) {

        List<EmployeeDeduction> employeeDeduction = null;
        
        try {
            employeeDeduction = em.createNamedQuery("EmployeeDeduction.getEmployeeDeductions", 
                EmployeeDeduction.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return employeeDeduction;
    }

}
