package com.machuzuerp.controllers.jsf;

import com.machuzuerp.controllers.jsf.EventProc;
import com.machuzuerp.jdbc.CustomerProcessing;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named("scheduleController")
@SessionScoped
public class ScheduleController implements Serializable {
    
    //Calendar
    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();
    private DefaultScheduleEvent defaultEvent;

    private boolean showWeekends = true;
    private boolean tooltip = true;
    private boolean allDaySlot = true;

    private String timeFormat;
    private String slotDuration = "00:30:00";
    private String slotLabelInterval;
    private String scrollTime = "06:00:00";
    private String minTime = "04:00:00";
    private String maxTime = "20:00:00";
    private String locale = "en";
    private String timeZone = "";
    private String clientTimeZone = "local";
    private String columnHeaderFormat = "";

    EventProc ep = new EventProc();
    
    private String[] selectedCustomers;
    private List<String> customers;
    
    Connection connection;
    Statement statement;
    ResultSet events = null;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ScheduleController() {
    }
    
    @PostConstruct
    public void init() {

        customers = new ArrayList<String>();

        connection = Systems.initConnection();

        try {
            CustomerProcessing cp = new CustomerProcessing();        
            ResultSet custs = cp.getCustomers();

            while (custs.next()) {
                customers.add(custs.getString(2) + " " + custs.getString(3) + ":" + custs.getString(1));
            }
        } catch(SQLException sqle) {
            System.out.println(sqle);
        }

        eventModel = new DefaultScheduleModel();
    }

    //Calendar    
    public LocalDateTime getRandomDateTime(LocalDateTime base) {
        LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
        return dateTime.plusDays(((int) (Math.random() * 30)));
    }

    public ScheduleModel getEventModel() throws SQLException { 
        fetchAllAppointments();
        return eventModel;
    }
    
    public ScheduleModel fetchAllAppointments() throws SQLException {

        eventModel.clear();
System.out.println("111");
        events = ep.getEvents();
System.out.println("222");
        while (events.next()) {
            try {
            
                defaultEvent = new DefaultScheduleEvent();
                defaultEvent.setDescription("Meeting");
                defaultEvent.setId(events.getString(4));
System.out.println("333");
                
                java.sql.Date sqlDate = java.sql.Date.valueOf(sdf.format(events.getDate(6)));
                java.util.Date utilDate = sqlDate;
                LocalDateTime localDate2 = LocalDateTime.parse((CharSequence) utilDate);

               // Instant from = events.getDate(6).toInstant();
                System.out.println("444: " + localDate2);
                /*ZonedDateTime zdt = from.atZone(ZoneId.systemDefault());
                System.out.println("555");
                LocalDateTime fromDate = zdt.toLocalDateTime();
                System.out.println("777");
                Instant to = events.getDate(7).toInstant();
                ZonedDateTime zdt1 = to.atZone(ZoneId.systemDefault());
                LocalDateTime toDate = zdt1.toLocalDateTime();*/
                
               /* defaultEvent.setStartDate(fromDate);
                defaultEvent.setEndDate(toDate);

                System.out.println("TEST: " + fromDate + ":" + toDate);*/

                //defaultEvent.setStartDate(event.getStartDate().toLocalDate().atStartOfDay());
                //defaultEvent.setEndDate(event.getEndDate().toLocalDate().atStartOfDay());
              //  defaultEvent.setStartDate(Date.from(event.getStartDate().toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC)));
                //defaultEvent.setEndDate(LocalDateTime.parse((CharSequence) encounter.getPeriodTo()));
                defaultEvent.setTitle("Meeting");
                defaultEvent.setDescription("Meeting");


                if (defaultEvent.isAllDay()) {
                    //see https://github.com/primefaces/primefaces/issues/1164
                    if (defaultEvent.getStartDate().toLocalDate().equals(defaultEvent.getEndDate().toLocalDate())) {
                        defaultEvent.setEndDate(defaultEvent.getEndDate().plusDays(1));
                    }
                }
                System.out.println("999");
                eventModel.addEvent(defaultEvent);
            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e);
            }

        }

        return eventModel;
    }
    
    public List<String> getCustomers() {
        customers = new ArrayList<String>();

        try {
            CustomerProcessing cp = new CustomerProcessing();        
            ResultSet custs = cp.getCustomers();

            while (custs.next()) {
                customers.add(custs.getString(2) + " " + custs.getString(3) + ":" + custs.getString(1));
            }
        } catch(SQLException sqle) {
            System.out.println(sqle);
        }

        return customers;
    }
    
    public String[] getSelectedCustomers() {
        return selectedCustomers;
    }
 
    public void setSelectedCustomers(String[] selectedCustomers) {
        this.selectedCustomers = selectedCustomers;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    private LocalDateTime previousDay8Pm() {
        return LocalDateTime.now().minusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime previousDay11Pm() {
        return LocalDateTime.now().minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today1Pm() {
        return LocalDateTime.now().withHour(13).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime theDayAfter3Pm() {
        return LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today6Pm() {
        return LocalDateTime.now().withHour(18).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay9Am() {
        return LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay11Am() {
        return LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime fourDaysLater3pm() {
        return LocalDateTime.now().plusDays(4).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime sevenDaysLater0am() {
        return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime eightDaysLater0am() {
        return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDate getInitialDate() {
        return LocalDate.now().plusDays(1);
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
    public void TestBean() {
        System.out.println("111");
    }

    public void addEvent(String temp) {

        if (event.isAllDay()) {
            //see https://github.com/primefaces/primefaces/issues/1164
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }                       

        ep.editEvent("", "", event, this.selectedCustomers);

        event = new DefaultScheduleEvent();

    }

    public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
        event = selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event.getDeltaAsDuration());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean isShowWeekends() {
        return showWeekends;
    }

    public void setShowWeekends(boolean showWeekends) {
        this.showWeekends = showWeekends;
    }

    public boolean isTooltip() {
        return tooltip;
    }

    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isAllDaySlot() {
        return allDaySlot;
    }

    public void setAllDaySlot(boolean allDaySlot) {
        this.allDaySlot = allDaySlot;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getSlotDuration() {
        return slotDuration;
    }

    public void setSlotDuration(String slotDuration) {
        this.slotDuration = slotDuration;
    }

    public String getSlotLabelInterval() {
        return slotLabelInterval;
    }

    public void setSlotLabelInterval(String slotLabelInterval) {
        this.slotLabelInterval = slotLabelInterval;
    }

    public String getScrollTime() {
        return scrollTime;
    }

    public void setScrollTime(String scrollTime) {
        this.scrollTime = scrollTime;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getClientTimeZone() {
        return clientTimeZone;
    }

    public void setClientTimeZone(String clientTimeZone) {
        this.clientTimeZone = clientTimeZone;
    }

    public String getColumnHeaderFormat() {
        return columnHeaderFormat;
    }

    public void setColumnHeaderFormat(String columnHeaderFormat) {
        this.columnHeaderFormat = columnHeaderFormat;
    }

    // End Calendar
}
