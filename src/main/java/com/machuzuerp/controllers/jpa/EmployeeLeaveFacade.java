/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.EmployeeLeave;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class EmployeeLeaveFacade extends AbstractFacade<EmployeeLeave> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeLeaveFacade() {
        super(EmployeeLeave.class);
    }
    
    public List<EmployeeLeave> getEmployeeLeave(Long id) {

        List<EmployeeLeave> employeeLeave = null;
        
        try {
            employeeLeave = em.createNamedQuery("EmployeeLeave.getEmployeeLeave", 
                EmployeeLeave.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return employeeLeave;
    }
    
}
