/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jpa;

import com.machuzuerp.entities.ComplianceRiskIssue;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CompliancePolicyRiskIssueFacade extends AbstractFacade<ComplianceRiskIssue> {

    @PersistenceContext(unitName = "MachuzuERPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompliancePolicyRiskIssueFacade() {
        super(ComplianceRiskIssue.class);
    }

}