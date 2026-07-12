package com.machuzuerp.selectOne;

import com.machuzuerp.entities.Account;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/*
@FacesConverter(value = "accountConverter")
public class AccountConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String clientId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{clientsBean}", AccountsBean.class);

        AccountsBean clients = (AccountsBean)vex.getValue(ctx.getELContext());
        return clients.getAccount(Integer.valueOf(clientId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object client) {
        return ((Account)client).getId().toString();
    }

}*/