/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.Administration;
import com.machuzuerp.entities.PasswordManagement;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class AdministrationFacade extends AbstractFacade<Administration> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministrationFacade() {
        super(Administration.class);
    }
    
    public Administration getLoginEmployeeAdministration(Long id) {

        Administration administration = null;
        
        try {
            administration = em.createNamedQuery("Administration.getEmployeeAdministration", 
                Administration.class)                
                    .setParameter("id", id)            
                .getSingleResult();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return administration;
    }
    
    public List<Administration> getEmployeeAdministration(Long id) {

        List<Administration> administration = null;
        
        try {
            administration = em.createNamedQuery("Administration.getEmployeeAdministration", 
                Administration.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return administration;
    }

    public List<PasswordManagement> getPasswordManagement(Long id) {

        List<PasswordManagement> passwordManagement = null;
        
        try {
            passwordManagement = em.createNamedQuery("PasswordManagement.getPasswordManagement", 
                PasswordManagement.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return passwordManagement;
    }

}
