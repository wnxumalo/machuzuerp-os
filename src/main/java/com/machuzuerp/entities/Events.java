package com.machuzuerp.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;

/**
 *
 * @author Wandile
 */
@Entity
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventsId;

    private String eventId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String customers;

    public Events() {
    }

    public Events(Long id, String eventId, String title, Date startDate, Date endDate, String customers) {

        this.eventsId = eventsId;
        this.eventId = eventId;
        this.title = title;
        this.endDate = endDate;
        this.customers = customers;

    }

    public Long getEventsId() {
        return eventsId;
    }

    public void setEventsId(Long eventsId) {
        this.eventsId = eventsId;
    }
    
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTitle(String title) {
        this.title = title;
    }  
    
    public String getTitle() {
        return title;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }  
    
    public Date getStartDate() {
        return startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }  

    public String getCustomers() {
        return customers;
    }
    
    public void setCustomers(String customers) {
        this.customers = customers;
    }  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Events)) {
            return false;
        }
        Events other = (Events) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.machuzuerp.entities.Events[ eventId=" + eventId + " ]";
    }        
    
}
