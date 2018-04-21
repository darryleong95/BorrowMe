/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.enumeration.CategoryEnum;

/**
 *
 * @author katrina
 */
@FacesConverter(value = "categoryConverter", forClass = CategoryEnum.class)
public class CategoryConverter implements Converter {

    public CategoryConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.err.println("********* getAsObject");
        if (value == null || value.trim().length() == 0 || value.equals("null")) {
            return null;
        }

        String category = (String) value;
        if (category.equalsIgnoreCase("PARTY")) {
            return CategoryEnum.PARTY;
        } else if(category.equalsIgnoreCase("ELECTRONICS")){
            return CategoryEnum.ELECTRONICS;
        } else if(category.equalsIgnoreCase("SPORTS")){
            return CategoryEnum.SPORTS;
        }else if(category.equalsIgnoreCase("VEHICLES")){
            return CategoryEnum.VEHICLES;
        } else {
            return CategoryEnum.OTHERS;
        }
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.err.println("********* getAsString");
        if (value == null) {
            return "";
        }

        if (value instanceof String) {
            return "";
        }
        if (value instanceof CategoryEnum) {
            CategoryEnum categoryEnum = (CategoryEnum) value;
            try {
                return categoryEnum.toString();
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid value");
            }
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
