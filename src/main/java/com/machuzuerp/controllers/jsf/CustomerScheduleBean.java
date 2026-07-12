/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.CustomerEventProcessing;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ViewScoped
@Named
public class CustomerScheduleBean implements Serializable {

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    String eventID = "";
    String custName = "";
    String custSurname = "";
    String custRecNum = "";
    
    HttpServletRequest request;

    @PostConstruct
    public void init() {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        eventModel = new DefaultScheduleModel();

        getParams();
        EventProc ep = new EventProc();

        ResultSet events = null;
 /*       try {
            events = ep.getEvents(request.getSession().getAttribute("uk").toString());
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        String sDate = "";
        String eDate = "";
        String event = "";
        String evt = "";
        boolean getEvt = false;
        boolean getStart = false;
        boolean getEnd = false;
        boolean startGet = false;
        int count = 0;
        try {

            while (events.next()) {

                evt = events.getString(2);

                for (int y = 0; y < evt.length(); y++) {

                    if (evt.charAt(y) == '{') {
                        startGet = false;
                        count++;
                    }

                    if (evt.charAt(y) == ',') {
                        startGet = false;
                        count++;
                    }

                    if ((getStart == true) & (evt.charAt(y) == ',')) {
                        getEvt = false;
                        getStart = false;
                        getEnd = true;
                        startGet = false;
                    }

                    if (evt.charAt(y) == '=') {
                        startGet = true;
                    }

                    if (count == 1) {
                        getEvt = true;
                    }

                    if ((evt.charAt(y) == '=') & (count == 2)) {
                        getStart = true;
                        startGet = true;
                    }

                    if ((evt.charAt(y) == '=') & (count == 3)) {
                        getEnd = true;
                        startGet = true;
                    }

                    if ((getEvt == true) & (startGet == true) & (evt.charAt(y) != '=') & (count == 1)) {
                        event = event + evt.charAt(y);
                    }

                    if ((getStart == true) & (startGet == true) & (evt.charAt(y) != '=')) {
                        sDate = sDate + evt.charAt(y);
                    }

                    if ((getEnd == true) & (evt.charAt(y) != '}') & (startGet == true) & (evt.charAt(y) != '=')) {
                        eDate = eDate + evt.charAt(y);
                    }
                }

                DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
//                eventModel.addEvent(new DefaultScheduleEvent(event, LocalDateTime.parse(sDate,formatter), LocalDateTime.parse(eDate,formatter)));                

                evt = "";
                event = "";
                sDate = "";
                eDate = "";
                getStart = false;
                getEvt = false;
                getStart = false;
                getEnd = false;
                startGet = false;
                count = 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }      
    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1);    //set random day of month

        return date.getTime();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);

        return t.getTime();
    }

    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    private Date today6Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);

        return t.getTime();
    }

    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);

        return t.getTime();
    }

    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println("EVT0");
        if (event.getId() == null) {
            eventModel.addEvent(event);
System.out.println("EVT-:"+event);
            CustomerEventProcessing ep = new CustomerEventProcessing();

            getParams();
            ep.editEvent("", "", request.getSession().getAttribute("uk").toString(), eventModel.getEvents().get(eventModel.getEvents().size() - 1), event.getId(), custName, custSurname, custRecNum);
        } else {
            CustomerEventProcessing ep = new CustomerEventProcessing();
System.out.println("EVT:"+event);
            getParams();            
            ep.editEvent("edit", "", request.getSession().getAttribute("uk").toString(), event, eventID, custName, custSurname, custRecNum);

            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();        
    }

    public String deleteEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
                        
        } else {
            CustomerEventProcessing ep = new CustomerEventProcessing();

            getParams();
            ep.deleteEvent("delete", request.getSession().getAttribute("uk").toString(), event);

            eventModel.updateEvent(null);
        }

        event = new DefaultScheduleEvent();
        
        return "/oc/calendar";
    }

    public void onEventSelect(SelectEvent selectEvent) throws SQLException {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println("EVTSELECT0");
        event = (ScheduleEvent) selectEvent.getObject(); 
        CustomerEventProcessing ep = new CustomerEventProcessing();
        this.eventID = ep.getEvent(event, request.getSession().getAttribute("uk").toString());
        System.out.println("EVTSELECT1");
    }

    //public void onDateSelect(SelectEvent selectEvent, AjaxBehaviorEvent event) {
    public void onDateSelect(SelectEvent selectEvent) {      
//        event = new DefaultScheduleEvent("", (LocalDateTime) selectEvent.getObject(), (LocalDateTime) selectEvent.getObject());              
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDeltaEnd() + ", Minute delta:" + event.getMinuteDeltaEnd());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.custName = params.get("custName");
        this.custSurname = params.get("custSurname");
        this.custRecNum = params.get("custRecNum");
    }
}
