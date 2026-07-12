/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.InventoryAddition;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class InventoryAdditionFacade extends AbstractFacade<InventoryAddition> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InventoryAdditionFacade() {
        super(InventoryAddition.class);
    }
    
    public List<InventoryAddition> getInventoryAdditions(Long inventoryId) {

        List<InventoryAddition> inventoryAddition = null;
        
        try {
            inventoryAddition = em.createNamedQuery("InventoryAddition.getInventoryAdditionList", 
                InventoryAddition.class)                
                    .setParameter("id", inventoryId)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return inventoryAddition;
    }
    
    
}
