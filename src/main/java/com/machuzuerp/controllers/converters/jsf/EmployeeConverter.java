package com.machuzuerp.controllers.converters.jsf;

import com.machuzuerp.entities.dto.EmployeeDTO;
import com.machuzuerp.controllers.jsf.EmployeesBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "employeeConverter")
public class EmployeeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String employeeId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{employeesBean}", EmployeesBean.class);

        EmployeesBean employeesBean = (EmployeesBean)vex.getValue(ctx.getELContext());
        return employeesBean.getEmployees(Integer.valueOf(employeeId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object employee) {
        return ((EmployeeDTO)employee).getId().toString();
    }

}