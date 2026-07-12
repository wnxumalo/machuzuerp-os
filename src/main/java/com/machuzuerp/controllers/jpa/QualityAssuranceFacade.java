/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.QualityAssurance;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class QualityAssuranceFacade extends AbstractFacade<QualityAssurance> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QualityAssuranceFacade() {
        super(QualityAssurance.class);
    }
    
    public List<QualityAssurance> getQualityAssuranceList(Long id) {       

        List<QualityAssurance> qualityAssurance = null;
        
        try {
            qualityAssurance = em.createNamedQuery("QualityAssurance.getQualityAssuranceList", 
                QualityAssurance.class)                
                    .setParameter("id", id)            
                .getResultList();

        } catch (Exception nre) {
            System.out.println("ERROR: "+nre);
        }
System.out.println("QUALITYASSURANCE22222");
        return qualityAssurance;
    }
    
    
}
