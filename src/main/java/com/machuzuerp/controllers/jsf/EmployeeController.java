package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.datatableprocessing.jsf.BenefitsData;
import com.machuzuerp.controllers.datatableprocessing.jsf.DeductionsData;
import com.machuzuerp.controllers.jpa.AdministrationFacade;
import com.machuzuerp.controllers.jpa.EmployeeBenefitFacade;
import com.machuzuerp.controllers.jpa.EmployeeDeductionFacade;
import com.machuzuerp.entities.Employee;
import com.machuzuerp.controllers.jsf.util.JsfUtil;
import com.machuzuerp.controllers.jsf.util.JsfUtil.PersistAction;
import com.machuzuerp.controllers.jpa.EmployeeFacade;
import com.machuzuerp.controllers.jpa.EmployeeLeaveFacade;
import com.machuzuerp.controllers.jpa.EmployeeTrainingFacade;
import com.machuzuerp.entities.dto.EmployeeDTO;
import com.machuzuerp.jdbc.BenefitsProcessing;
import com.machuzuerp.jdbc.DeductionsProcessing;
import java.io.IOException;
import com.machuzu.cryptography.AESPKCS5Padding;
import com.machuzuerp.controllers.datatableprocessing.jsf.TrainingsData;
import com.machuzuerp.jdbc.TrainingsProcessing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import static org.primefaces.component.keyboard.KeyboardBase.PropertyKeys.password;
import org.primefaces.event.SelectEvent;

@Named("employeeController")
@SessionScoped
public class EmployeeController implements Serializable {

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeFacade ejbFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeBenefitFacade ejbBenefitFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeDeductionFacade ejbDeductionFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeLeaveFacade ejbLeaveFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.EmployeeTrainingFacade ejbTrainingFacade;

    @EJB
    private com.machuzuerp.controllers.jpa.AdministrationFacade ejbAdminFacade;

    private List<Employee> employees = null;
    private Employee selected = new Employee();
    private EmployeeDTO employeeDTO;
    private List<EmployeeDTO> employeesDTO = null;

    private BigDecimal salary;
    private BigDecimal totalBenefits;
    private BigDecimal totalDeductions;
    private BigDecimal totalPackage;

    private List<BenefitsData> benefits = new ArrayList<BenefitsData>();
    private List<DeductionsData> deductions = new ArrayList<DeductionsData>();
    private List<TrainingsData> trainings = new ArrayList<TrainingsData>();

    BenefitsProcessing bp = new BenefitsProcessing();
    DeductionsProcessing dp = new DeductionsProcessing();
    TrainingsProcessing tp = new TrainingsProcessing();

    AESPKCS5Padding aesPKCS5Padding = new AESPKCS5Padding();

    public EmployeeController() {
    }

    @PostConstruct
    public void init() {
/*        employeesDTO = new ArrayList<EmployeeDTO>();

        employeesDTO.clear();
        employeesDTO.add(new EmployeeDTO(Long.parseLong("0"), "N/A", "N/A", "N/A", "N/A", "N/A"));

        String decryptedCipherText = "";
        for (Employee emp : getFacade().findAll()) {

            emp.setCellphone(AESPKCS5Padding.decrypt(selected.getCellphone()));
            emp.setIdNumber(AESPKCS5Padding.decrypt(selected.getIdNumber()));
            emp.setTaxNumber(AESPKCS5Padding.decrypt(selected.getTaxNumber()));
            emp.setEmployeeNumber(AESPKCS5Padding.decrypt(selected.getEmployeeNumber()));
            emp.setTelephone(AESPKCS5Padding.decrypt(selected.getTelephone()));
            emp.setEmail(AESPKCS5Padding.decrypt(selected.getEmail()));
            emp.setPostalAddress(AESPKCS5Padding.decrypt(selected.getPostalAddress()));
            emp.setEmpBankAccName(AESPKCS5Padding.decrypt(selected.getEmpBankAccName()));
            emp.setEmpBankAccNum(AESPKCS5Padding.decrypt(selected.getEmpBankAccNum()));

            employeesDTO.add(new EmployeeDTO(emp.getId(), emp.getIdNumber(), emp.getTaxNumber(), emp.getEmployeeNumber(), emp.getEmploymentDate(), emp.getEmpName(),emp.getSurname(), emp.getGender(), emp.getTelephone(), emp.getPosition(), emp.getCellphone(), emp.getEmail(), emp.getPostalAddress(), emp.getSalary(), emp.getSalaryInterval(), emp.getEmpLevel(), emp.getApprover(), emp.getDepartment(), emp.getPassword(), emp.getEmpBankAccNum(), emp.getEmpBankName(), emp.getEmpBankName(), emp.getBranchCode(), emp.getUserRole(), emp.getOrganisationId(), emp.getSupervisorId(), emp.getCreationDate(), emp.getStatus(), emp.getNotes()));

        }
*/
    }

    public Employee getSelected() {
        return selected;
    }

    public void setSelected(Employee selected) {
        this.selected = selected;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public List<EmployeeDTO> getEmployeesDTO() {

        if (employeesDTO == null) {
            employeesDTO = new ArrayList<EmployeeDTO>();

            employeesDTO.clear();
            employeesDTO.add(new EmployeeDTO(Long.parseLong("0"), "N/A", "N/A", "N/A", "N/A", "N/A", new BigDecimal(0)));

            String decryptedCipherText = "";
            for (Employee emp : getFacade().findAll()) {

                emp.setCellphone(AESPKCS5Padding.decrypt(emp.getCellphone()));
                emp.setIdNumber(AESPKCS5Padding.decrypt(emp.getIdNumber()));
                emp.setTaxNumber(AESPKCS5Padding.decrypt(emp.getTaxNumber()));
                emp.setEmployeeNumber(AESPKCS5Padding.decrypt(emp.getEmployeeNumber()));
                emp.setTelephone(AESPKCS5Padding.decrypt(emp.getTelephone()));
                emp.setEmail(AESPKCS5Padding.decrypt(emp.getEmail()));
                emp.setPostalAddress(AESPKCS5Padding.decrypt(emp.getPostalAddress()));
                emp.setEmpBankAccName(AESPKCS5Padding.decrypt(emp.getEmpBankAccName()));
                emp.setEmpBankAccNum(AESPKCS5Padding.decrypt(emp.getEmpBankAccNum()));
                emp.setSalary(emp.getSalary());

                employeesDTO.add(new EmployeeDTO(emp.getId(), emp.getIdNumber(), emp.getEmpName(), emp.getSurname(), emp.getPosition(), emp.getCellphone(), emp.getSalary()));
            }
        }
        return employeesDTO;
    }

    public void setEmployeesDTO(List<EmployeeDTO> employeesDTO) {
        this.employeesDTO = employeesDTO;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        /*employeesDTO.forEach(emp -> {
            employees.add(new Employee(emp.getId(), emp.getIdNumber(), emp.getName(), emp.getSurname(), emp.getPosition(), emp.getCellphone()));
        });*/                
        //((QuotationData) event.getObject()).iRecNum;
        this.selected.setId(employeeDTO.getId());
        this.selected.setIdNumber(employeeDTO.getIdNumber());
        this.selected.setEmpName(employeeDTO.getName());
        this.selected.setSurname(employeeDTO.getSurname());
        this.selected.setPosition(employeeDTO.getPosition());
        this.selected.setCellphone(employeeDTO.getCellphone());
        this.selected.setSalary(employeeDTO.getSalary());
        this.selected.setEmployeeBenefits(getBenefitFacade().getEmployeeBenefits(selected.getId()));
        this.selected.setEmployeeDeduction(getDeductionFacade().getEmployeeDeduction(selected.getId()));
        this.selected.setEmployeeTraining(getTrainingFacade().getEmployeeTraining(selected.getId()));
        this.selected.setEmployeeLeave(getLeaveFacade().getEmployeeLeave(selected.getId()));
        
        System.out.println("SELECT: " + ((EmployeeDTO) event.getObject()).getSalary());
System.out.println("SELECT111: " + selected.getSalary() + ":" + employeeDTO.getSalary());
    }

    public void onAdminRowSelect(SelectEvent event) throws IOException {

        selected = new Employee();
        
        selected.setId(employeeDTO.getId());
        selected.setIdNumber(employeeDTO.getIdNumber());
        selected.setEmpName(employeeDTO.getName());
        selected.setSurname(employeeDTO.getSurname());
        selected.setPosition(employeeDTO.getPosition());
        selected.setCellphone(employeeDTO.getCellphone());

        try {
            selected.setEmployeeAdministration(getAdminFacade().getEmployeeAdministration(selected.getId()));
        } catch (NullPointerException npe) {
        }

        try {
            selected.setPasswordManagement(getAdminFacade().getPasswordManagement(selected.getId()));
        } catch (NullPointerException npe) {
        }
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeFacade getFacade() {
        return ejbFacade;
    }

    private EmployeeBenefitFacade getBenefitFacade() {
        return ejbBenefitFacade;
    }

    private EmployeeDeductionFacade getDeductionFacade() {
        return ejbDeductionFacade;
    }

    private EmployeeLeaveFacade getLeaveFacade() {
        return ejbLeaveFacade;
    }

    private EmployeeTrainingFacade getTrainingFacade() {
        return ejbTrainingFacade;
    }

    private AdministrationFacade getAdminFacade() {
        return ejbAdminFacade;
    }

    public Employee prepareCreate() {
        selected = new Employee();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(Long orgId) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        selected.setOrganisationId(orgId);

        String cipherText = "";
        cipherText = AESPKCS5Padding.encrypt(selected.getCellphone());
        selected.setCellphone(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getIdNumber());
        selected.setIdNumber(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getTaxNumber());
        selected.setTaxNumber(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmployeeNumber());
        selected.setEmployeeNumber(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getTelephone());
        selected.setTelephone(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmail());
        selected.setEmail(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getPostalAddress());
        selected.setPostalAddress(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmpBankAccName());
        selected.setEmpBankAccName(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmpBankAccNum());
        selected.setEmpBankAccNum(cipherText);
        
        selected.setPassword("12345");

        persist(PersistAction.CREATE, "Employee Created Successfully");
        if (!JsfUtil.isValidationFailed()) {
            employees = null;    // Invalidate list of employees to trigger re-query.
        }
    }

    public void update() {
        
        String cipherText = "";
        cipherText = AESPKCS5Padding.encrypt(selected.getCellphone());
        selected.setCellphone(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getIdNumber());
        selected.setIdNumber(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getTaxNumber());
        selected.setTaxNumber(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmployeeNumber());
        selected.setEmployeeNumber(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getTelephone());
        selected.setTelephone(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmail());
        selected.setEmail(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getPostalAddress());
        selected.setPostalAddress(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmpBankAccName());
        selected.setEmpBankAccName(cipherText);
        cipherText = AESPKCS5Padding.encrypt(selected.getEmpBankAccNum());
        selected.setEmpBankAccNum(cipherText);
        
        selected.setPassword("12345");

        persist(PersistAction.UPDATE, "Employee Updated Successfully");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Employee Deleted Successfully");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            employees = null;    // Invalidate list of employees to trigger re-query.
        }
    }

    public List<Employee> getItems() {
        if (employees == null) {
            employees = getFacade().findAll();
        }

        return employees;
    }

    public String setPayslip() throws SQLException {
        salary = new BigDecimal(0);
        salary = selected.getSalary();

        //salary = selected.getSalary();
        totalBenefits = new BigDecimal(0);
        ResultSet bens = bp.getBenefits(selected.getId().toString());
        benefits.clear();
        while (bens.next()) {
            benefits.add(new BenefitsData(bens.getString(1), bens.getString(5), bens.getString(4), bens.getDouble(2)));
            totalBenefits = totalBenefits.add(bens.getBigDecimal(2));
        }

        totalDeductions = new BigDecimal(0);
        bens = dp.getDeductions(selected.getId().toString());
        deductions.clear();
        while (bens.next()) {
            deductions.add(new DeductionsData(bens.getString(1), bens.getString(5), bens.getString(4), bens.getDouble(2)));
            totalDeductions = totalDeductions.add(bens.getBigDecimal(2));
        }

        ResultSet trains = tp.getTrainings(selected.getId().toString());
        trainings.clear();
        while (trains.next()) {
            trainings.add(new TrainingsData(trains.getString(1), trains.getString(5), trains.getDate(8), trains.getDate(6), trains.getString(9), trains.getString(2),"",""));
            //totalDeductions = totalDeductions.add(bens.getBigDecimal(2));
        }

        totalPackage = new BigDecimal(0);
        totalPackage = salary.add(totalBenefits).subtract(totalDeductions);

        return "view-payslip";
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Employee getEmployee(java.lang.Long id) {
        return getFacade().find(id);
    }

    public String getEmployeeNavigation(String id) {
        selected = getFacade().find(Long.parseLong(id));

        return "/v4/hr/employee/employee";
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setTotalBenefits(BigDecimal totalBenefits) {
        this.totalBenefits = totalBenefits;
    }

    public BigDecimal getTotalBenefits() {
        return totalBenefits;
    }

    public void setTotalDeductions(BigDecimal totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public BigDecimal getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalPackage(BigDecimal totalPackage) {
        this.totalPackage = totalPackage;
    }

    public BigDecimal getTotalPackage() {
        return totalPackage;
    }

    public List<Employee> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Employee> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Employee.class)
    public static class EmployeeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeController controller = (EmployeeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeController");
            return controller.getEmployee(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Employee) {
                Employee o = (Employee) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Employee.class.getName()});
                return null;
            }
        }

    }

}
