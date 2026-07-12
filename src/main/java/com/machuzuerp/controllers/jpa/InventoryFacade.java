/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.Inventory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.machuzuerp.entities.dto.InventoryDTO;

@Stateless
public class InventoryFacade extends AbstractFacade<Inventory> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InventoryFacade() {
        super(Inventory.class);
    }
    
    public Inventory getInventory(Long id) {

        Inventory inventory = null;
        
        try {
            inventory = em.createNamedQuery("Inventory.getInventory", 
                Inventory.class)                
                    .setParameter("id", id)            
                .getSingleResult();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return inventory;
    }
    
    public void returnInventory(Long id, int qty) {

        Inventory inventory = null;
        
        try {
            inventory = em.createNamedQuery("Inventory.returnInventory", 
                Inventory.class)                
                    .setParameter("id", id)            
                .getSingleResult();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

    }

    public List<Inventory> getInventoryFilter(String filter, int id) {

        List<Inventory> inventory = null;

        try {
            
            if (id == 1) {
                
                inventory = em.createNamedQuery("Inventory.getInventoryFilter", 
                Inventory.class)                
                    .setParameter("id", id)
                    .setParameter("", filter)            
                .getResultList();

            }
            
        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return inventory;
    }
    
}
