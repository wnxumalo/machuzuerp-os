package com.machuzuerp.controllers.converters.jsf;

import com.machuzuerp.entities.Client;
import com.machuzuerp.controllers.jsf.ClientsBean;
import com.machuzuerp.controllers.jsf.ClientsBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "clientConverter")
public class ClientConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String clientId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{clientsBean}", ClientsBean.class);

        ClientsBean clients = (ClientsBean)vex.getValue(ctx.getELContext());
        return clients.getClient(clientId);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object client) {
        return ((Client)client).getId();
    }

}