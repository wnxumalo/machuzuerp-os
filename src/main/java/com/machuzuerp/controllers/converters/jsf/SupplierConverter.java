package com.machuzuerp.controllers.converters.jsf;

import com.machuzuerp.entities.Client;
import com.machuzuerp.controllers.jsf.ClientsBean;
import com.machuzuerp.controllers.jsf.SupplierList;
import com.machuzuerp.controllers.jsf.SupplierList;
import com.machuzuerp.controllers.jsf.SupplierListBean;
import com.machuzuerp.controllers.jsf.SupplierListBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "supplierConverter")
public class SupplierConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String supplierId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{supplierListBean}", SupplierListBean.class);

        SupplierListBean supplier = (SupplierListBean)vex.getValue(ctx.getELContext());
        return supplier.getSupplierList(Integer.valueOf(supplierId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object supplierList) {
        return ((SupplierList)supplierList).getId().toString();
    }

}