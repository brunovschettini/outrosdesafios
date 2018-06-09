package br.com.xptosystems.converter;

import br.com.xptosystems.utils.Types;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
public class ThisNumber implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return (String) value; // Or (value != null) ? value.toString().toUpperCase() : null;            
        } catch (Exception e) {
            if (Types.isInteger(value)) {
                return value + "";
            }
            return "";
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        value = (value != null) ? value.replaceAll("[^0-9]", "") : null;
        if (value == null || value.isEmpty()) {
            value = "0";
        }
        return value;
    }

}
