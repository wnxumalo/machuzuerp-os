package com.machuzuerp.controllers.converters.jsf;

import com.machuzuerp.entities.Inv;
import com.machuzuerp.controllers.jsf.InventoryBean;
import com.machuzuerp.controllers.jsf.InventoryBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "inventoryConverter")
public class InventoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String employeeId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{inventoryBean}", InventoryBean.class);

        InventoryBean inventory = (InventoryBean)vex.getValue(ctx.getELContext());
        return inventory.getInventory(Integer.valueOf(employeeId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object inventory) {
        return ((Inv)inventory).getId().toString();
    }

}