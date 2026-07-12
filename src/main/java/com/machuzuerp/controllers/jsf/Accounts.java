/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.AccountData;
import com.machuzuerp.jdbc.AccountProcessing;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/*
 * @author Administrator
 */
@RequestScoped
@Named
public class Accounts {

    private String recnum;

    private String aRecNum;
    private String accountNumber;
    private String accType;
    private String description;
    private float balance;
    private String comments;
    private java.util.Date pDate;
    String cDate;

    String ret = "";

    private List<SelectItem> categories;
    private List<SelectItem> payables;
    private List<SelectItem> receivables;

    private String selectedAccount1;
    private String[] selectedAccount;
    private List<String> accounts;
    private Accounts selectedAcc;

    private String option;
    private AccountData accData;
    private List<SelectItem> account;
    private List<String> options;

    Connection connection;
    Statement statement;
    ResultSet results;

    AccountProcessing ap = new AccountProcessing();    

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    HttpServletRequest request;

    public Accounts() {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
        
        categories = new ArrayList<SelectItem>();
        payables = new ArrayList<SelectItem>();
        receivables = new ArrayList<SelectItem>();

        SelectItemGroup group1 = new SelectItemGroup("1000 Assets");
        SelectItemGroup group2 = new SelectItemGroup("1200 Receivables");
        SelectItemGroup group3 = new SelectItemGroup("1300 Inventories");
        SelectItemGroup group4 = new SelectItemGroup("1400 Prepaid Expenses and Other Current Assets");
        SelectItemGroup group5 = new SelectItemGroup("1500 Property Plant and Equipment");
        SelectItemGroup group6 = new SelectItemGroup("1600 Accumulated Depreciation and Amortization");
        SelectItemGroup group7 = new SelectItemGroup("1700 None-Current Receivables");
        SelectItemGroup group8 = new SelectItemGroup("1800 Intercompany Receivables");
        SelectItemGroup group9 = new SelectItemGroup("1900 Other None-Current Assets");
        SelectItemGroup group10 = new SelectItemGroup("2000 Liablities");
        SelectItemGroup group11 = new SelectItemGroup("2100 Payables");
        SelectItemGroup group12 = new SelectItemGroup("2200 Accrued Compensation and Related Items");
        SelectItemGroup group13 = new SelectItemGroup("2300 Other Accrued Expenses");
        SelectItemGroup group14 = new SelectItemGroup("2500 Accrued Taxes");
        SelectItemGroup group15 = new SelectItemGroup("2600 Deferred Taxes");
        SelectItemGroup group16 = new SelectItemGroup("2700 Long-Term Debt");
        SelectItemGroup group17 = new SelectItemGroup("2800 Intercompany Payables");
        SelectItemGroup group18 = new SelectItemGroup("2900 Other Non Current Liabilities");
        SelectItemGroup group19 = new SelectItemGroup("3000 Owners Equities");
        SelectItemGroup group20 = new SelectItemGroup("4000 Revenue");
        SelectItemGroup group21 = new SelectItemGroup("5000 Cost of Goods Sold");
        SelectItemGroup group22 = new SelectItemGroup("6000 – 7000 Operating Expenses");

        /*SelectItem option11;
        SelectItem option12;
        SelectItem option13;

        int count =0;
        ResultSet subAccs;
        subAccs = (ap.getSubAccounts(1000, 1999));
        while(subAccs.next()) {
            switch(count) {
                case 1: 
                    option11 = new SelectItem(subAccs.getString(4) + " - " + subAccs.getString(5));
                    break;
                case 2: 
                    option12 = new SelectItem(subAccs.getString(4) + " - " + subAccs.getString(5));
                    break;
                case 3: 
                    option13 = new SelectItem(subAccs.getString(4) + " - " + subAccs.getString(5));
                    break;
            }                       
            count++;
        }*/
        SelectItem option11 = new SelectItem("1010 CASH Operating Account");
        SelectItem option12 = new SelectItem("1020 CASH Debitors");
        SelectItem option13 = new SelectItem("1030 CASH Petty Cash");

        SelectItem option21 = new SelectItem("1210 A/REC Trade");
        SelectItem option22 = new SelectItem("1220 A/REC Trade Notes Receivable");
        SelectItem option23 = new SelectItem("1230 A/REC Installment Receivables");
        SelectItem option24 = new SelectItem("1240 A/REC Retainage Withheld");
        SelectItem option25 = new SelectItem("1290 A/REC Allowance for Uncollectable Accounts");

        SelectItem option31 = new SelectItem("1310 INV – Reserved");
        SelectItem option32 = new SelectItem("1320 INV – Work-in-Progress");
        SelectItem option33 = new SelectItem("1330 INV – Finished Goods");
        SelectItem option34 = new SelectItem("1340 INV – Reserved");
        SelectItem option35 = new SelectItem("1350 INV – Unbilled Cost and Fees");
        SelectItem option36 = new SelectItem("1390 INV – Reserve for Obsolescence");

        SelectItem option41 = new SelectItem("1410 PREPAID – Insurance");
        SelectItem option42 = new SelectItem("1420 PREPAID – Real Estate Taxes");
        SelectItem option43 = new SelectItem("1430 PREPAID – Repairs and Maintenance");
        SelectItem option44 = new SelectItem("1440 PREPAID – Rent");
        SelectItem option45 = new SelectItem("1450 PREPAID – Deposits");

        SelectItem option51 = new SelectItem("1510 PPE – Buildings");
        SelectItem option52 = new SelectItem("1520 PPE – Machinery and Equipment");
        SelectItem option53 = new SelectItem("1530 PPE – Vehicles");
        SelectItem option54 = new SelectItem("1540 PPE – Computer Equipment");
        SelectItem option55 = new SelectItem("1550 PPE – Furniture and Fixtures");
        SelectItem option56 = new SelectItem("1560 PPE – Leasehold Improvements");

        SelectItem option61 = new SelectItem("1610 ACCUM DEPR Buildings");
        SelectItem option62 = new SelectItem("1620 ACCUM DEPR Machinery and Equipment");
        SelectItem option63 = new SelectItem("1630 ACCUM DEPR Vehicles");
        SelectItem option64 = new SelectItem("1640 ACCUM DEPR Computer Equipment");
        SelectItem option65 = new SelectItem("1650 ACCUM DEPR Furniture and Fixtures");
        SelectItem option66 = new SelectItem("1660 ACCUM DEPR Leasehold Improvements");

        SelectItem option71 = new SelectItem("1710 NCA – Notes Receivable");
        SelectItem option72 = new SelectItem("1720 NCA – Installment Receivables");
        SelectItem option73 = new SelectItem("1730 NCA – Retainage Withheld");

        SelectItem option81 = new SelectItem("1810 LIA - ");

        SelectItem option101 = new SelectItem("2005 Accounts payable");
        SelectItem option102 = new SelectItem("2010 Payroll taxes payable");
        SelectItem option103 = new SelectItem("2015 Income taxes payable");
        SelectItem option104 = new SelectItem("2020 Interest payable");
        SelectItem option105 = new SelectItem("2025 Bank account overdrafts");
        SelectItem option106 = new SelectItem("2030 Accrued expenses");
        SelectItem option107 = new SelectItem("2035 Stakeholder deposits");
        SelectItem option108 = new SelectItem("2040 Dividends declared");
        SelectItem option109 = new SelectItem("2045 Short-term loans");
        SelectItem option1010 = new SelectItem("2050 Current maturities of long-term debt");
        SelectItem option1011 = new SelectItem("2055 Organisation Costs");
        SelectItem option1012 = new SelectItem("2060 Patents and Licenses");
        SelectItem option1013 = new SelectItem("2065 Intangible Assets – Capitalised Software Costs");

        SelectItem option111 = new SelectItem("2105 Accounts Payable");
        SelectItem option112 = new SelectItem("2110 A/P Insurance");
        SelectItem option113 = new SelectItem("2115 A/P Fees");
        SelectItem option114 = new SelectItem("2120 A/P Wages");
        SelectItem option115 = new SelectItem("2125 A/P Taxes");
        SelectItem option116 = new SelectItem("2130 A/P Interest");
        SelectItem option117 = new SelectItem("2135 A/P Supplies");
        SelectItem option118 = new SelectItem("2140 A/P Depreciation");
        SelectItem option119 = new SelectItem("2145 A/P Maintenance");
        SelectItem option120 = new SelectItem("2150 A/P Travel");
        SelectItem option121 = new SelectItem("2155 A/P Supplies");
        SelectItem option122 = new SelectItem("2160 A/P Meals and Entertainment");
        SelectItem option123 = new SelectItem("2165 A/P Training");

        SelectItem option124 = new SelectItem("2210 Accrued – Payroll");
        SelectItem option125 = new SelectItem("2220 Accrued – Commissions");
        SelectItem option126 = new SelectItem("2230 Accrued – Unemployment Taxes");
        SelectItem option127 = new SelectItem("2240 Accrued – Workmen’s Comp");
        SelectItem option128 = new SelectItem("2250 Accrued – Medical Benefits");
        SelectItem option129 = new SelectItem("2260 Accrued – Company Match");
        SelectItem option130 = new SelectItem("2270 W/H – Medical Benefits");
        SelectItem option131 = new SelectItem("2280 W/H – Employee Contribution");

        SelectItem option132 = new SelectItem("2310 Accrued – Rent");
        SelectItem option133 = new SelectItem("2320 Accrued – Interest");
        SelectItem option134 = new SelectItem("2330 Accrued – Property Taxes");
        SelectItem option135 = new SelectItem("2340 Accrued – Warranty Expense");

        SelectItem option141 = new SelectItem("2510 Accrued – Income Taxes");
        SelectItem option142 = new SelectItem("2520 Accrued – State Income Taxes");
        SelectItem option143 = new SelectItem("2530 Accrued – Franchise Taxes");
        SelectItem option145 = new SelectItem("2550 Deferred – State Income Taxes");

        SelectItem option151 = new SelectItem("2610 D/T – FIT – NON CURRENT");
        SelectItem option152 = new SelectItem("2620 D/T – SIT – NON CURRENT");

        SelectItem option161 = new SelectItem("2710 LTD – Notes Payable");
        SelectItem option162 = new SelectItem("2720 LTD – Mortgages Payable");
        SelectItem option163 = new SelectItem("2730 LTD – Installment Notes Payable");

        SelectItem option171 = new SelectItem("2810 ICP – ");

        SelectItem option181 = new SelectItem("2910 ONCL - ");

        SelectItem option191 = new SelectItem("3010 Common Stock");
        SelectItem option192 = new SelectItem("3020 Preferred Stock");
        SelectItem option193 = new SelectItem("3030 Paid in Capital");
        SelectItem option194 = new SelectItem("3040 Partners Capital");
        SelectItem option195 = new SelectItem("3050 Member Contributions");
        SelectItem option196 = new SelectItem("3060 Retained Earnings");

        SelectItem option202 = new SelectItem("4010 Interest Income");
        SelectItem option203 = new SelectItem("4020 Other Income");
        SelectItem option204 = new SelectItem("4030 Finance Charge Income");
        SelectItem option205 = new SelectItem("4040 Sales Returns and Allowances");
        SelectItem option206 = new SelectItem("4050 Sales Discounts");

        SelectItem option212 = new SelectItem("5010 Freight");
        SelectItem option213 = new SelectItem("5020 Inventory Adjustments");
        SelectItem option214 = new SelectItem("5030 Purchase Returns and Allowances");
        SelectItem option215 = new SelectItem("5040 Reserved");

        SelectItem option221 = new SelectItem("6010 Advertising Expense");
        SelectItem option222 = new SelectItem("6020 Amortisation Expense");
        SelectItem option223 = new SelectItem("6030 Auto Expense");
        SelectItem option224 = new SelectItem("6040 Bad Debt Expense");
        SelectItem option225 = new SelectItem("6050 Bank Charges");
        SelectItem option226 = new SelectItem("6060 Commission Expense");
        SelectItem option227 = new SelectItem("6070 Depreciation Expense");
        SelectItem option228 = new SelectItem("6080 Employee Benefit Program");
        SelectItem option229 = new SelectItem("6090 Freight Expense");
        SelectItem option2210 = new SelectItem("6100 Gifts Expense");
        SelectItem option2211 = new SelectItem("6110 Insurance – General");
        SelectItem option2212 = new SelectItem("6120 Interest Expense");
        SelectItem option2213 = new SelectItem("6130 Professional Fees");
        SelectItem option2214 = new SelectItem("6140 License Expense");
        SelectItem option2215 = new SelectItem("6150 Maintenance Expense");
        SelectItem option2216 = new SelectItem("6160 Meals and Entertainment");
        SelectItem option2217 = new SelectItem("6170 Office Expense");
        SelectItem option2218 = new SelectItem("6180 Payroll Taxes");
        SelectItem option2219 = new SelectItem("6190 Printing");
        SelectItem option2220 = new SelectItem("6200 Postage");
        SelectItem option2221 = new SelectItem("6210 Rent");
        SelectItem option2222 = new SelectItem("6220 Repairs Expense");
        SelectItem option2223 = new SelectItem("6230 Salaries Expense");
        SelectItem option2224 = new SelectItem("6240 Supplies Expense");
        SelectItem option2225 = new SelectItem("6250 Taxes – FIT Expense");
        SelectItem option2226 = new SelectItem("6260 Utilities Expense");
        SelectItem option2227 = new SelectItem("6270 Gain/Loss on Sale of Assets");

        group1.setSelectItems(new SelectItem[]{option11, option12, option13});
        group2.setSelectItems(new SelectItem[]{option21, option22, option23, option24, option25});
        group3.setSelectItems(new SelectItem[]{option31, option32, option33, option34, option35, option36});
        group4.setSelectItems(new SelectItem[]{option41, option42, option43, option44, option45});
        group5.setSelectItems(new SelectItem[]{option51, option52, option53, option54, option55, option56});
        group6.setSelectItems(new SelectItem[]{option61, option62, option63, option64, option65, option66});
        group7.setSelectItems(new SelectItem[]{option71, option72, option73});
//        group9.setSelectItems(new SelectItem[]{option91,option92,option93});
        group10.setSelectItems(new SelectItem[]{option101, option102, option103, option104, option105, option106, option107, option108, option109, option1010, option1011, option1012, option1013});
        group11.setSelectItems(new SelectItem[]{option111, option112, option113, option114, option115, option116, option117, option118, option119, option120, option123, option122, option123});
        group12.setSelectItems(new SelectItem[]{option121, option122, option123, option124, option125, option126, option127, option128, option129});
        group13.setSelectItems(new SelectItem[]{option131, option132, option133, option134});
        group14.setSelectItems(new SelectItem[]{option141, option142, option143, option145});
        group15.setSelectItems(new SelectItem[]{option151, option152});
        group16.setSelectItems(new SelectItem[]{option161, option162, option163});
        group19.setSelectItems(new SelectItem[]{option191, option192, option193, option194, option195, option196});
        group20.setSelectItems(new SelectItem[]{option202, option203, option204, option205, option206});
        group21.setSelectItems(new SelectItem[]{option212, option213, option214, option215});
        group22.setSelectItems(new SelectItem[]{option221, option222, option223, option224, option225, option226, option227, option228, option229, option2210, option2211, option2212, option2213, option2214, option2215, option2216, option2217, option2218, option2219, option2220, option2221, option2222, option2223, option2224, option2225, option2226, option2227});

        categories.add(group1);
        categories.add(group2);
        categories.add(group3);
        categories.add(group4);
        categories.add(group5);
        categories.add(group6);
        categories.add(group7);
        categories.add(group8);
        categories.add(group9);
        categories.add(group10);
        categories.add(group11);
        payables.add(group11);
        categories.add(group12);
        categories.add(group13);
        categories.add(group14);
        categories.add(group15);
        categories.add(group16);
        categories.add(group17);
        categories.add(group18);
        categories.add(group19);
        categories.add(group20);
        categories.add(group21);
        categories.add(group22);

        accounts = new ArrayList<String>();

    }

    public Accounts(String description) {

        this.description = description;

    }
   
    public List<String> getAcc() {
        return accounts;
    }

    public List<SelectItem> getAccounts() {                        
        return account;
    }
    
    public String getSelectedAccount1() {
        return selectedAccount1;
    }

    public void setSelectedAccount1(String selectedAccount1) {
        this.selectedAccount1 = selectedAccount1;
    }

    public String[] getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(String[] selectedAccount) {
        this.selectedAccount = selectedAccount;
    }    

    public List<SelectItem> getCategories() {
        return categories;
    }

    public void setCategories(List<SelectItem> categories) {
        this.categories = categories;
    }

    public List<SelectItem> getPayables() {
        return payables;
    }

    public void setPayables(List<SelectItem> payables) {
        this.payables = payables;
    }

    public List<SelectItem> getReceivables() {
        return receivables;
    }

    public void setReceivables(List<SelectItem> receivables) {
        this.receivables = receivables;
    }

    public String getRecNum() {
        return aRecNum;
    }

    public void setRecNum(String aRecNum) {
        this.aRecNum = aRecNum;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accType;
    }

    public void setAccountType(String accType) {
        this.accType = accType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setPDate(java.util.Date pDate) {
        this.pDate = pDate;
    }

    public java.util.Date getPDate() {
        return pDate;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");

        ap.aRecNum = params.get("recnum");
        ap.accountNumber = this.accountNumber;
        ap.accType = this.accType;
        ap.description = this.description;
        ap.balance = this.balance;
        ap.comments = this.comments;
        ap.pDate = "" + this.pDate;

    }

    public void getPayableParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recnum");

        ap.aRecNum = params.get("recnum");
        ap.accType = this.accType;
        ap.accountType = "Accounts Payable";
        ap.accountNumber = "2100 Payables";

        ap.description = description;
        ap.balance = this.balance;
        ap.comments = this.comments;
        ap.pDate = "" + this.pDate;

    }

    public void getReceivableParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
       
        this.recnum = params.get("recnum");
        this.description = params.get("accName");

        ap.aRecNum = params.get("recnum");
        ap.accType = this.accType;
        ap.accountType = "Accounts Receivable";
        ap.accountNumber = "1200 Receivables";
        //ap.description = Arrays.toString(this.selectedAccount);        
        ap.description = description;
        ap.balance = this.balance;
        ap.comments = this.comments;
        ap.pDate = "" + this.pDate;

    }

    public void getURLParams() throws ParseException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
    
        this.recnum = params.get("recnum");
        this.accountNumber = params.get("accountNumber");
        this.accType = params.get("accType");
        this.description = params.get("description");
        this.balance = Float.parseFloat(params.get("balance"));
        this.comments = params.get("comments");
        ap.pDate = params.get("pDate");

        ap.aRecNum = params.get("recnum");
        ap.accountNumber = this.accountNumber;
        ap.accType = this.accType;
        ap.description = this.description;
        ap.balance = this.balance;
        ap.comments = this.comments;
        ap.pDate = "" + this.pDate;

    }

    public java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

    public String saveAccount() {
        getParams();

        try {
            ap.accountNumber = this.accountNumber;
            ap.accType = this.accType;
            ap.description = this.description;
            ap.balance = this.balance;
            ap.comments = this.comments;
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();     

            ap.editAccount(request.getSession().getAttribute("uk").toString(), "", "", "");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "Record Saved Succssfully"));
        } catch (SQLException npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "This module module does not process change. Enter the exact outstanding amount in Amount Paid."));
        }

        return ret;
    }
   
    public String saveAccountReceivable(String accDesc) {
        getReceivableParams();

        try {
            if (accDesc.length() > 0) {
                this.description = accDesc;
                ap.description = accDesc;
                ap.pDate = sdf.format(pDate);

                try {
                    ap.editAccountReceivable(request.getSession().getAttribute("uk").toString(), "", "", "");
                    ret = "/accounting/saved";
                } catch (SQLException npe) {
                    ret = "/accounting/notsaved";
                }
            }
        } catch (NullPointerException npe) {
            //System.out.println(npe);
        }

        return ret;
    }

    public String saveEditAccount() throws SQLException {

        getParams();

        try {

            ap.aRecNum = recnum;

            ap.editAccount(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/accounting/saved";
        } catch (NullPointerException npe) {
            ret = "/accounting/notsaved";
        }

        return ret;
    }

    public String mainEditAccount() throws SQLException {

        try {
            ap.editAccount(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/account/saved";
        } catch (NullPointerException npe) {
            ret = "/account/notsaved";
        }

        return ret;
    }

    public String editAccount_1(String aRecNum, String accountNumber, String description, float balance, String comments) throws SQLException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        this.recnum = aRecNum;
        this.accountNumber = accountNumber;
        this.accType = accType;
        this.description = description;
        this.balance = balance;
        this.comments = comments;
        // this.pDate = pDate;

        return "/accounting/edit-account";
    }
    
    public List<String> getLoadAccounts() throws SQLException {
       
        results = ap.getAccounts();
        
        while(results.next()) {
            try {
                accounts.add(results.getString(1) + "," + results.getString(4));
            } catch (NullPointerException npe) {}
        }
        
        return accounts;
    }    

    public void getAccount() throws SQLException {

        ap.getAccount(recnum);

        this.recnum = ap.aRecNum;
        this.accountNumber = ap.accountNumber;
        setAccountType(accType); //= ap.accType;
        this.description = ap.description;
        this.balance = ap.balance;
        this.comments = ap.comments;

    }

    public String deleteAccount() throws SQLException {

        try {
            ap.editAccount(request.getSession().getAttribute("uk").toString(), "delete", "yes", recnum);
            ret = "/accounting/saved";
        } catch (NullPointerException npe) {
            ret = "/accounting/notsaved";
        }

        return ret;
    }

    public String findAccount() {
        getParams();
       
        return "/accounting/payable/find-account";
    }  

    public String findAccountReceivable() {
        getParams();
       
        return "/accounting/receivable/find-account";
    } 

    public void onDateSelect(SelectEvent event) {
        //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        //setJDate(format.format(event.getObject()));
        System.out.println("ONDATESELECT:" + pDate);
        // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

}
