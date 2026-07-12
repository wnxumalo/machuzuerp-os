/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.Cities;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Wandile
 */
@Stateless
public class CitiesFacade extends AbstractFacade<Cities> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitiesFacade() {
        super(Cities.class);
    }
    
    public List<Cities> getCities(String country) {

        List<Cities> cities = null;
        
        try {
            cities = em.createNamedQuery("Cities.getCities", 
                Cities.class)                
                    .setParameter("country", country)            
                .getResultList();

        } catch (Exception nre) {
        System.out.println("ERROR: "+nre);
        }

        return cities;
    }    
    
    
}
