package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Events;
import com.machuzuerp.controllers.jpa.util.JsfUtil;
import com.machuzuerp.controllers.jpa.util.PaginationHelper;
import com.machuzuerp.controllers.jpa.EventsFacade;
import com.machuzuerp.jdbc.CustomerProcessing;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named("eventsController")
@SessionScoped
public class EventsController implements Serializable {

    private Events selected;
    private DataModel items = null;
    @EJB
    private com.machuzuerp.controllers.jpa.EventsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
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

    private String[] selectedCustomers;
    private List<String> customers;
    
    public EventsController() {
    }
    
    //Calendar
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }
    
    public String[] getSelectedCustomers() {
        return selectedCustomers;
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
 
    public void setSelectedCustomers(String[] selectedCustomers) {
        this.selectedCustomers = selectedCustomers;
    }    

    public LocalDateTime getRandomDateTime(LocalDateTime base) {
        LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
        return dateTime.plusDays(((int) (Math.random() * 30)));
    }

    public ScheduleModel getEventModel() { 
        fetchAllEvents();
        return eventModel;
    }
    
    public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
        event = selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event.getDeltaAsDuration());
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

    }

    public ScheduleModel fetchAllEvents() {

        eventModel.clear();

        List<Events> events = getFacade().findAll();

        for (Events evt: events) {

            try {

                defaultEvent = new DefaultScheduleEvent();
                defaultEvent.setDescription("");
                defaultEvent.setId(evt.getEventId());                

                Instant from = evt.getStartDate().toInstant();
                ZonedDateTime zdt = from.atZone(ZoneId.systemDefault());
                LocalDateTime fromDate = zdt.toLocalDateTime();

                Instant to = evt.getEndDate().toInstant();
                ZonedDateTime zdt1 = to.atZone(ZoneId.systemDefault());
                LocalDateTime toDate = zdt1.toLocalDateTime();
                
                defaultEvent.setStartDate(fromDate);
                defaultEvent.setEndDate(toDate);                
                
                //defaultEvent.setStartDate(event.getStartDate().toLocalDate().atStartOfDay());
                //defaultEvent.setEndDate(event.getEndDate().toLocalDate().atStartOfDay());
              //  defaultEvent.setStartDate(Date.from(event.getStartDate().toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC)));
                //defaultEvent.setEndDate(LocalDateTime.parse((CharSequence) encounter.getPeriodTo()));
                defaultEvent.setTitle(evt.getTitle() + " - " + evt.getCustomers());

                if (defaultEvent.isAllDay()) {
                    //see https://github.com/primefaces/primefaces/issues/1164
                    if (defaultEvent.getStartDate().toLocalDate().equals(defaultEvent.getEndDate().toLocalDate())) {
                        defaultEvent.setEndDate(defaultEvent.getEndDate().plusDays(1));
                    }
                }

                eventModel.addEvent(defaultEvent);
            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e);
            }

        }
        
        return eventModel;
    }
    
    public void addEvent() {

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
        
        prepareCreate();
        
        selected.setEventId(event.getId());
        selected.setTitle(event.getTitle());
        selected.setStartDate(Date.from(event.getStartDate().toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC)));
        selected.setEndDate(Date.from(event.getEndDate().toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC)));
        selected.setCustomers(Arrays.toString(selectedCustomers));
                
        create();
        
        event = new DefaultScheduleEvent();

        
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
    
    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
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

    public Events getSelected() {
        if (selected == null) {
            selected = new Events();
            selectedItemIndex = -1;
        }
        return selected;
    }

    private EventsFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        selected = (Events) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        selected = new Events();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        selected = (Events) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        selected = (Events) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventsDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            selected = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Events getEvents(Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Events.class)
    public static class EncounterControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EventsController controller = (EventsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eventsController");
            return controller.getEvents(getKey(value));
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
            if (object instanceof Events) {
                Events o = (Events) object;
                return getStringKey(o.getEventsId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Events.class.getName()});
                return null;
            }
        }
    }

}
