/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.Supplier;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class SupplierFacade extends AbstractFacade<Supplier> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupplierFacade() {
        super(Supplier.class);
    }
    
    public Supplier getSupplier(Long id) {

        Supplier supplier = null;
        
        try {
            supplier = em.createNamedQuery("Supplier.getSupplier", 
                Supplier.class)                
                    .setParameter("id", id)            
                .getSingleResult();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return supplier;
    }
    
}
