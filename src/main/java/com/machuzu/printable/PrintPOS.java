package com.machuzu.printable;
        
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import com.machuzuerp.controllers.datatableprocessing.jsf.InventoryData;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrintPOS implements Printable {
    
    private static BigDecimal totCost;
    private static BigDecimal total;    
    private static BigDecimal amtPaid;    
    private static BigDecimal change;
    private static BigDecimal outstandingAmount;
    private static BigDecimal taxAmount;    
    private List<InventoryData> printInventory = new ArrayList<InventoryData>();
    private static String orgNumber;
    private static String orgName;
    private static String orgTel;
    private static String orgEmail;
    private static String clientId;
    private static String clientName;
    private static String tel;
    private static String cell;
    private static String rDate;    

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        g.drawString("TAX RECEIPT " + getRDate() , 0, 12);
        g.drawString(getOrgNumber() + " : " + getOrgName() + " : " + getOrgTel(), 0, 37);
        g.drawString("CUSTOMER: " + getClientName() + " : " + getCell(), 0, 57);
        
        int x = 87; 
        BigDecimal tempTotCost = null;
        for (InventoryData inv : this.getPrintInventory()) {
                
             g.drawString(inv.getDescription() + " / " + inv.getQuantity() + " / " + inv.getSellingPrice(), 0, x);
             
            /* tempTotCost = inv.getSellingPrice().multiply(new BigDecimal(inv.getQuantity()));
             totCost = totCost.add(tempTotCost);
             setTotCost(totCost);
              */ 
             x += 20;
        }
        
       /* taxAmount = totCost.divide(new BigDecimal(100));
        taxAmount = taxAmount.multiply(new BigDecimal(15));
        taxAmount = taxAmount.add(totCost);
        setTaxAmount(taxAmount);*/
        
        g.drawString("SUB TOTAL: " + this.getTotal()+"", 0, x+20);
        g.drawString("TAX: " + getTaxAmount()+"", 0, x+40);  
        g.drawString("TOTAL: " + this.getTotCost()+"", 0, x+60);        
        g.drawString("PAID: " + this.getAmtPaid()+"", 0, x+80);        
        g.drawString("Change: " + this.getChange()+"", 0, x+100);
        g.drawString("Outstanding: " + this.getOutstandingAmount() +"", 0, x+120);        
        
        /*private static BigDecimal totCost;
    private static BigDecimal change;
    private static BigDecimal outstandingAmount;
    private static BigDecimal taxAmount;    
    private String printInventory = "";
    private static String orgNumber;
    private static String orgName;
    private static String orgTel;
    private static String orgEmail;
    private static String clientId;
    private static String clientName;
    private static String tel;
    private static String cell;*/

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void printPOSReceipt(String rDate, String orgNumber, String orgName, String orgTel, String orgFax, String orgEmail, String orgPosAdd, String orgPhysAdd, String clientId, String clientName, String tel, String cell, 
            String fax, String email, String posAdd, String physAdd, BigDecimal total, BigDecimal totCost, BigDecimal amtPaid, BigDecimal change, BigDecimal outstandingAmount, BigDecimal taxAmount, List<InventoryData> selectedPOSInvs) {
        
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);                  
         
         this.setPrintInventory(selectedPOSInvs);
         this.setRDate(rDate);
         
         if (orgNumber !=null)
            this.setOrgNumber(orgNumber); 
         else
            this.setOrgNumber(""); 
         
         if (orgName != null)
            this.setOrgName(orgName); 
         else
            this.setOrgName(""); 
         
         if (orgTel != null)
            this.setOrgTel(orgTel);
         else
            this.setOrgTel(""); 
         
         if (orgEmail != null)
             this.setOrgEmail(orgEmail); 
         else
            this.setOrgEmail(""); 
    
         if (clientId != null)
             this.setClientId(clientId); 
         else
            this.setClientId(""); 
    
         if (clientName != null)
             this.setClientName(clientName); 
         else
            this.setClientName(""); 
    
         if (tel != null)
             this.setTel(tel); 
         else
            this.setTel(""); 
    
         if (cell != null)
             this.setCell(cell);   
         else
            this.setCell(""); 
         
         if (outstandingAmount != null)
             this.setOutstandingAmount(outstandingAmount);
         else
             this.setOutstandingAmount(new BigDecimal(0));
         
         if (change != null)
             this.setChange(change);
         else
             this.setChange(new BigDecimal(0));

         this.setPrintInventory(printInventory);
         this.setTotCost(totCost);
         this.setTotal(total);
         this.setAmtPaid(amtPaid);
         this.setOutstandingAmount(outstandingAmount);
         this.setChange(change); 
         this.setTaxAmount(taxAmount);
                  
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         
    }

    public BigDecimal getTotCost() {
        return totCost;
    }
    
    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public BigDecimal getAmtPaid() {
        return amtPaid;
    }
    
    public void setAmtPaid(BigDecimal amtPaid) {
        this.amtPaid = amtPaid;
    }
    
    public BigDecimal getChange() {
        return change;
    }
    
    public void setChange(BigDecimal change) {
        this.change = change;
    }
    
    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }
    
    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public List<InventoryData> getPrintInventory() {
        return printInventory;
    }
    
    public void setPrintInventory(List<InventoryData> printInventory) {
        this.printInventory = printInventory;
    }
    
    public String getOrgNumber() {
        return orgNumber;
    }
    
    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }
    
    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getOrgTel() {
        return orgTel;
    }
    
    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }
    
    public String getOrgEmail() {
        return orgEmail;
    }
    
    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public String getCell() {
        return cell;
    }
    
    public void setCell(String cell) {
        this.cell = cell;
    }
    
    public String getRDate() {
        return rDate;
    }
    
    public void setRDate(String rDate) {
        this.rDate = rDate;
    }
    
        
}