/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.InventoryStorageSpace;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class InventoryStorageSpaceFacade extends AbstractFacade<InventoryStorageSpace> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InventoryStorageSpaceFacade() {
        super(InventoryStorageSpace.class);
    }
    
     public List<InventoryStorageSpace> getInventoryStorageSpaceList(Long id) {

        List<InventoryStorageSpace> inventoryStorageSpace = null;
        
        try {
            inventoryStorageSpace = em.createNamedQuery("InventoryStorageSpace.getInventoryStorageSpaceList", 
                InventoryStorageSpace.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return inventoryStorageSpace;
    }
    
}
