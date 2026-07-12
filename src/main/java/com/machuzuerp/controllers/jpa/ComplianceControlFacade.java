/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.ComplianceControl;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ComplianceControlFacade extends AbstractFacade<ComplianceControl> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComplianceControlFacade() {
        super(ComplianceControl.class);
    }

}