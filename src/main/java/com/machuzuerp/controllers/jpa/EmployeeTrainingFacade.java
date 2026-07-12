/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.EmployeeTraining;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class EmployeeTrainingFacade extends AbstractFacade<EmployeeTraining> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeTrainingFacade() {
        super(EmployeeTraining.class);
    }
    
    public List<EmployeeTraining> getEmployeeTraining(Long id) {

        List<EmployeeTraining> employeeTraining = null;
        
        try {
            employeeTraining = em.createNamedQuery("EmployeeTraining.getEmployeeTraining", 
                EmployeeTraining.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return employeeTraining;
    }

}
