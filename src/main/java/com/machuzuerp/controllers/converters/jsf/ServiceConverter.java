package com.machuzuerp.controllers.converters.jsf;

import com.machuzuerp.controllers.jsf.StakeholderServices;
import com.machuzuerp.controllers.jsf.ServicesBean;
import com.machuzuerp.controllers.jsf.ServicesBean;
import com.machuzuerp.controllers.jsf.StakeholderServices;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "serviceConverter")
public class ServiceConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String serviceId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{servicesBean}", ServicesBean.class);

        ServicesBean services = (ServicesBean)vex.getValue(ctx.getELContext());
        return services.getServices(Integer.valueOf(serviceId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object service) {
        return ((StakeholderServices)service).getId().toString();
    }

}