package com.machuzuerp.controllers.converters.jsf;

import com.machuzuerp.entities.Account;
import com.machuzuerp.entities.Account;
import com.machuzuerp.controllers.jsf.AccountBean;
import com.machuzuerp.controllers.jsf.AccountBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "accountConverter")
public class AccountConverterMain implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String accountId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{accountBean}", AccountBean.class);

        AccountBean accounts = (AccountBean)vex.getValue(ctx.getELContext());
        return accounts.getAccount(Integer.valueOf(accountId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object account) {
        return ((Account)account).getId().toString();
    }

}