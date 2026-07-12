/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wandile
 */
@Entity
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c"),
    @NamedQuery(name = "Clients.findByRecNum", query = "SELECT c FROM Clients c WHERE c.recNum = :recNum"),
    @NamedQuery(name = "Clients.findByUserRecNum", query = "SELECT c FROM Clients c WHERE c.userRecNum = :userRecNum"),
    @NamedQuery(name = "Clients.findByClientType", query = "SELECT c FROM Clients c WHERE c.clientType = :clientType"),
    @NamedQuery(name = "Clients.findByClientStatus", query = "SELECT c FROM Clients c WHERE c.clientStatus = :clientStatus"),
    @NamedQuery(name = "Clients.findByName", query = "SELECT c FROM Clients c WHERE c.name = :name"),
    @NamedQuery(name = "Clients.findBySurname", query = "SELECT c FROM Clients c WHERE c.surname = :surname"),
    @NamedQuery(name = "Clients.findByGender", query = "SELECT c FROM Clients c WHERE c.gender = :gender"),
    @NamedQuery(name = "Clients.findByTelephone", query = "SELECT c FROM Clients c WHERE c.telephone = :telephone"),
    @NamedQuery(name = "Clients.findByCellphone", query = "SELECT c FROM Clients c WHERE c.cellphone = :cellphone"),
    @NamedQuery(name = "Clients.findByFax", query = "SELECT c FROM Clients c WHERE c.fax = :fax"),
    @NamedQuery(name = "Clients.findByPostalAddress", query = "SELECT c FROM Clients c WHERE c.postalAddress = :postalAddress"),
    @NamedQuery(name = "Clients.findByPhysicalAddress", query = "SELECT c FROM Clients c WHERE c.physicalAddress = :physicalAddress"),
    @NamedQuery(name = "Clients.findByCountry", query = "SELECT c FROM Clients c WHERE c.country = :country"),
    @NamedQuery(name = "Clients.findByJoiningDate", query = "SELECT c FROM Clients c WHERE c.joiningDate = :joiningDate"),
    @NamedQuery(name = "Clients.findByUk", query = "SELECT c FROM Clients c WHERE c.uk = :uk"),
    @NamedQuery(name = "Clients.findByEmail", query = "SELECT c FROM Clients c WHERE c.email = :email"),
    @NamedQuery(name = "Clients.findByIdnumber", query = "SELECT c FROM Clients c WHERE c.idnumber = :idnumber"),
    @NamedQuery(name = "Clients.findByEmployer", query = "SELECT c FROM Clients c WHERE c.employer = :employer")})
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RecNum")
    private Integer recNum;
    @Size(max = 10)
    @Column(name = "UserRecNum")
    private String userRecNum;
    @Size(max = 20)
    @Column(name = "ClientType")
    private String clientType;
    @Size(max = 20)
    @Column(name = "ClientStatus")
    private String clientStatus;
    @Size(max = 150)
    @Column(name = "Name")
    private String name;
    @Size(max = 20)
    @Column(name = "Surname")
    private String surname;
    @Size(max = 11)
    @Column(name = "Gender")
    private String gender;
    @Size(max = 20)
    @Column(name = "Telephone")
    private String telephone;
    @Size(max = 20)
    @Column(name = "Cellphone")
    private String cellphone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "Fax")
    private String fax;
    @Size(max = 100)
    @Column(name = "PostalAddress")
    private String postalAddress;
    @Size(max = 100)
    @Column(name = "PhysicalAddress")
    private String physicalAddress;
    @Size(max = 150)
    @Column(name = "country")
    private String country;
    @Column(name = "JoiningDate")
    @Temporal(TemporalType.DATE)
    private Date joiningDate;
    @Size(max = 50)
    @Column(name = "uk")
    private String uk;
    @Lob
    @Size(max = 16777215)
    @Column(name = "oid")
    private String oid;
    @Lob
    @Size(max = 16777215)
    @Column(name = "vatnum")
    private String vatnum;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 155)
    @Column(name = "email")
    private String email;
    @Size(max = 45)
    @Column(name = "idnumber")
    private String idnumber;
    @Size(max = 145)
    @Column(name = "employer")
    private String employer;

    public Clients() {
    }

    public Clients(Integer recNum) {
        this.recNum = recNum;
    }

    public Integer getRecNum() {
        return recNum;
    }

    public void setRecNum(Integer recNum) {
        this.recNum = recNum;
    }

    public String getUserRecNum() {
        return userRecNum;
    }

    public void setUserRecNum(String userRecNum) {
        this.userRecNum = userRecNum;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getUk() {
        return uk;
    }

    public void setUk(String uk) {
        this.uk = uk;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getVatnum() {
        return vatnum;
    }

    public void setVatnum(String vatnum) {
        this.vatnum = vatnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recNum != null ? recNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.recNum == null && other.recNum != null) || (this.recNum != null && !this.recNum.equals(other.recNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.machuzuerp.entities.Clients[ recNum=" + recNum + " ]";
    }
    
}
