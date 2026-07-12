package com.machuzuerp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

@Entity
@NamedQueries({  
 @NamedQuery(name="EmployeeBenefit.getEmployeeBenefits", 
 query="SELECT b FROM EmployeeBenefit b WHERE b.employeeId = :id")})   
public class EmployeeBenefit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EMPLOYEE_BENEFIT_ID")
    Long id;

    private String description;
    private BigDecimal amount;

    @JoinColumn(name="EMPLOYEE", nullable=false)
    private Long employeeId;
    
    private LocalDate creationDate;

    public EmployeeBenefit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
 
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
        
    public LocalDate getCreationDate() {
        return creationDate;
    }
 
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeBenefit)) {
            return false;
        }
        EmployeeBenefit other = (EmployeeBenefit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "machuzu.entities.EmployeeBenefit[ id=" + id + " ]";
    }
    
}