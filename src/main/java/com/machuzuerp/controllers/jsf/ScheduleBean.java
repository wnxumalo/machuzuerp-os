
package com.machuzuerp.controllers.jsf;

import com.machuzuerp.jdbc.CustomerProcessing;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Administrator
 */

@ViewScoped
@Named
public class ScheduleBean implements Serializable {

    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private LocalDateTime start;
    private LocalDateTime end;

    private String uk;
    private String name;
    String eventID = "";

    private String[] selectedCustomers;
    private String[] selectedCustomers2;
    private List<String> customers;

    Connection connection;
    Statement statement;
    ResultSet events = null;

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

        String sDate = "";
        String eDate = "";
        String event = "";
        String evt = "";
        boolean getEvt = false;
        boolean getStart = false;
        boolean getEnd = false;
        boolean startGet = false;
        int count = 0;
        int count1 = 0;
        
        getParams();
        EventProc ep = new EventProc();    
        
/*        try {
            events = ep.getEvents(session.getAttribute("uk").toString());
        } catch (SQLException ex) {           
            Logger.getLogger(ScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
      */  
        try {
            events.first();
            while (events.next()) {

                evt = events.getString(4);

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

                if (event.length() > 0)  {
                    //eventModel.addEvent(new DefaultScheduleEvent(event, new Date(sDate), new Date(eDate)));                    
                    
                    //eventModel.addEvent(new DefaultScheduleEvent(event, sDate, eDate));                    
                }

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
                count1++;

            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        
    }
    
    public String[] getSelectedCustomers() {
        return selectedCustomers;
    }
 
    public void setSelectedCustomers(String[] selectedCustomers) {
        this.selectedCustomers = selectedCustomers;
    }
 
    public String[] getSelectedCustomers2() {
        return selectedCustomers2;
    }
 
    public void setSelectedCustomers2(String[] selectedCustomers2) {
        this.selectedCustomers2 = selectedCustomers2;
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

        eventModel = new DefaultScheduleModel();

        String sDate = "";
        String eDate = "";
        String event = "";
        String evt = "";
        boolean getEvt = false;
        boolean getStart = false;
        boolean getEnd = false;
        boolean startGet = false;
        int count = 0;
        int count1 = 0;
        
        getParams();
        EventProc ep = new EventProc();    
        
/*        try {
            events = ep.getEvents(session.getAttribute("uk").toString());
        } catch (SQLException ex) {           
            Logger.getLogger(ScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
  */      
        try {
            events.first();
            while (events.next()) {

                evt = events.getString(4);

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

                if (event.length() > 0)  {
//                    eventModel.ad
  //                  eventModel.addEvent(new DefaultScheduleEvent(event, new LocalDate(sDate), new LocalTime(eDate)));                    
                }

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
                count1++;

            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        
        return eventModel;
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
        System.out.println("111");
        if (event.getId() == null) {
            eventModel.addEvent(event);
System.out.println("222");
            EventProc ep = new EventProc();

            getParams();
         //   ep.editEvent("", "", uk, eventModel.getEvents().get(eventModel.getEvents().size() - 1), event.getId(), selectedCustomers);
        } else {
            EventProc ep = new EventProc();

            getParams();            
           // ep.editEvent("edit", "", uk, event, eventID, selectedCustomers);

            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    public String deleteEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
                        
        } else {
            EventProc ep = new EventProc();

            getParams();
            ep.deleteEvent("delete", uk, event);

            eventModel.updateEvent(null);
        }

        event = new DefaultScheduleEvent();
        
        return "/oc/calendar";
    }

    public void onEventSelect(SelectEvent selectEvent) throws SQLException {
        event = (ScheduleEvent) selectEvent.getObject(); 
        EventProc ep = new EventProc();
        this.eventID = ep.getEvent(event, uk);
        this.selectedCustomers = ep.getEventAttendees(event, uk);
    }

    public void onDateSelect(SelectEvent selectEvent) {
//        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());        
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

      //  addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void getParams() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.name = params.get("name");
        this.uk = params.get("uk");
    }
}
