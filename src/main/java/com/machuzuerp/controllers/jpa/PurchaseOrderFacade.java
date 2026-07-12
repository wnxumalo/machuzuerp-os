/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.PurchaseOrder;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class PurchaseOrderFacade extends AbstractFacade<PurchaseOrder> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseOrderFacade() {
        super(PurchaseOrder.class);
    }
    
    public List<PurchaseOrder> getSupplierPurchaseOrders(Long supplierId) {

        List<PurchaseOrder> pos = null;
        
        try {
            pos = em.createNamedQuery("PurchaseOrder.getSupplierPurchaseOrders", 
                PurchaseOrder.class)                
                    .setParameter("supplierId", supplierId)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return pos;
    }
    
    public List<PurchaseOrder> getPurchaseOrdersByDate(Date dateFrom, Date dateTo) {

        List<PurchaseOrder> pos = null;
        
        try {
            pos = em.createNamedQuery("PurchaseOrder.getPurchaseOrdersByDate", 
                PurchaseOrder.class)                
                    .setParameter("dateFrom", dateFrom)            
                    .setParameter("dateTo", dateTo)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }

        return pos;
    }
    
    /*public Long getMaxPurchaseOrder() {

        PurchaseOrder pos = null;
        
        try {
            pos = em.createNamedQuery("PurchaseOrder.getMaxPurchaseOrder", 
                PurchaseOrder.class)                
                .getSingleResult();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }
System.out.println("EMPID: " + pos.getId());
        return pos.getId();
    }*/
    
}
