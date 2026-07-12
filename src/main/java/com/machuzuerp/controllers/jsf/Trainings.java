
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.TrainingsData;
import com.machuzuerp.controllers.datatableprocessing.jsf.BenefitsData;
import com.machuzuerp.controllers.datatableprocessing.jsf.DeductionsData;
import com.machuzuerp.jdbc.TrainingsProcessing;
import com.machuzuerp.jdbc.BenefitsProcessing;
import com.machuzuerp.jdbc.DeductionsProcessing;
import com.machuzuerp.jdbc.EmployeesProcessing;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class Trainings implements Serializable {

    private String recnum;
       
    private String employeeNumber;
    private Date startDate;
    private Date endDate;
    private String description;
    private String outputs;
    private String status;
    private String comments;
    
    private String ret = "";
    
    TrainingsProcessing tp = new TrainingsProcessing();
    DeductionsProcessing dp = new DeductionsProcessing();
    BenefitsProcessing bp = new BenefitsProcessing();
    EmployeesProcessing ep = new EmployeesProcessing();
    
    private List<BenefitsData> benefits = new ArrayList<BenefitsData>();
    private List<DeductionsData> deductions = new ArrayList<DeductionsData>();
    private List<TrainingsData> trainings = new ArrayList<TrainingsData>();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String newstr;
    int endIndex;
    HttpServletRequest request;
    String url;
    String uri;
        
    public Trainings() {                       
        
    }
    
    public void onDateSelect(SelectEvent event) {
     //   FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");               
        //setJDate(format.format(event.getObject()));
       // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    public void setRecNum(String recNum) {
        this.recnum = recNum;
    }

    public String getRecNum() {
        return recnum;
    }
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }   
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }         
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }                                     
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }     
    
    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    } 
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    } 
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    } 
   
    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();        

        this.recnum = params.get("recnum");   

        try {
            tp.employeeNumber = this.employeeNumber;
            tp.startDate = this.startDate;
            tp.endDate = this.endDate;
            tp.description = this.description;
            tp.outputs = this.outputs;
            tp.status = this.status;
            tp.comments = this.comments;

        } catch (NullPointerException npe) {}
    }

    public void getURLParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.recnum = params.get("recNum"); 
        
        try {
            this.recnum = params.get("empRecNum");   
        } catch(NullPointerException npe) {}
        
        this.employeeNumber = params.get("employeeNumber");           
        this.description = params.get("description");           
        try {
           // this.amount = Double.parseDouble(params.get("amount"));
        } catch (NullPointerException npe) {
            //this.amount = 0;
        }

       /* ep.recNum= params.get("recnum");
        ep.customerType = this.idNumber;        
        ep.customerStat = this.taxNumber;
        ep.customerName = params.get("cname");
        ep.customerSurname = this.cSurname;
        ep.customerGender = this.cGender;
        ep.customerTelephone = this.cTelephone;
        ep.customerCellphone = this.cCellphone;
        ep.customerFax = this.cFax;
        ep.customerPostalAddress = this.cPostalAddress;
        ep.customerPhysicalAddress = this.cPhysicalAddress;
        ep.customerCountry = this.cCountry;
        ep.customerJDate = ""+sqlDate(this.cJDate);*/
    }
    
    public java.sql.Date sqlDate(java.util.Date calendarDate) {        
        return new java.sql.Date(calendarDate.getTime());
    }

    public void saveTraining() {
        getParams();          

        try {

            tp.recNum = this.recnum;
            tp.employeeNumber = this.employeeNumber;
            tp.startDate = this.startDate;
            tp.endDate = this.endDate;
            tp.description = this.description;
            tp.outputs = this.outputs;
            tp.status = this.status;
            tp.comments = this.comments;
            
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            tp.editTraining(request.getSession().getAttribute("uk").toString(), "", "", "");            
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Training Added Successfully."));
            
        } catch (Exception npe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Saved", "Training Not Added. " + npe));
        }
        
    }
    
    //public String saveEditTrainings(String recnum, String ctype, String cStat, String cName, String cSurname, String cGender, String cTel, String cCell, String cFax, String cPosAd, String cPhysAd, String cCountry,String cJDate) throws SQLException {
    public String saveEditTrainings() throws SQLException {

        getParams(); 

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            tp.editTraining(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/hr/saved";
        } catch (NullPointerException npe) {
            ret = "/hr/notsaved";
        }

        return ret;
    }

    public String mainEditTrainings() {
        getParams(); 
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        try {
            tp.editTraining(request.getSession().getAttribute("uk").toString(), "edit", "", recnum);
            ret = "/employees/saved";
        } catch (NullPointerException npe) {
            ret = "/employees/notsaved";
        }
        
        return ret;        
    }
    
    public String editEmployee() {        
        
        return "/hr/employees/edit-employee";        
    }
       
    public String editEmployee_1(String recnum, String idNumber, String taxNumber, String empDate, String employeeNumber, String empName, String surname, String level, String gender, String telephone, String cellphone, String email, String postalAddress, String physicalAddress, float salary, String salaryInterval, String status) throws SQLException, ParseException {        
        
        this.recnum = recnum;
        this.employeeNumber = employeeNumber;           
        this.startDate = startDate;           
        this.endDate = endDate;
        this.description = description;
        this.outputs = outputs;
        this.status = status;
        this.comments = comments;
        
        return "edit-training";        
    }    

    public void getTrainings() throws SQLException {        
        tp.getTrainings(recnum);                      
    
        employeeNumber = tp.employeeNumber;
        startDate = tp.startDate;
        endDate = tp.endDate;
        description = tp.description;
        outputs = tp.outputs;
        status = tp.status;
        comments = tp.comments;

    }        
        
    public void showError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Incorrect username and/or password."));
    }
    
}
