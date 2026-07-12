/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.EmployeeBenefit;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class EmployeeBenefitFacade extends AbstractFacade<EmployeeBenefit> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeBenefitFacade() {
        super(EmployeeBenefit.class);
    }

    public List<EmployeeBenefit> getEmployeeBenefits(Long id) {

        List<EmployeeBenefit> employeeBenefits = null;
        
        try {
            employeeBenefits = em.createNamedQuery("EmployeeBenefit.getEmployeeBenefits", 
                EmployeeBenefit.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return employeeBenefits;
    }        
    
}
