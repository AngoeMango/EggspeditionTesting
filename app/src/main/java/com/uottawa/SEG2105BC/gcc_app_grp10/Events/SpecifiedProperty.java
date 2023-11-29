package com.uottawa.SEG2105BC.gcc_app_grp10.Events;

import java.io.Serializable;
import java.lang.reflect.Type;

public class SpecifiedProperty implements Serializable {

    Object value;
    PropertyType propertyType;


    public SpecifiedProperty(Object value, PropertyType propertyType) {
        this.propertyType = new PropertyType(propertyType);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setValue(Object value) throws PropertyTypeMismatchException {
        if(validateType(value))this.value = value;
        else throw new PropertyTypeMismatchException();
    }

    /**
     * checks if value's type matches the eventProperties type, will return true if value is null
     * @return whether or not the type is invalid
     */
    public boolean validateType(){
        return value==null||propertyType.equals(value.getClass());
    }

    /**
     * checks if value's type matches the eventProperties type, will return true if value is null
     * @return whether or not the type is invalid
     */
    public boolean validateType(Object value){
        return value==null||propertyType.getType().equals(value.getClass());
    }

    public boolean validateType(Type type){
        return value==null||type.equals(value.getClass());
    }

    public void setType(String type) {
//        if(type==null||!validateType(type)){
//            throw new PropertyTypeMismatchException();
//        }
        propertyType.setType(type);
    }

//    public void setType(String type) throws PropertyTypeMismatchException {
//        try {
//            setType(Class.forName(type));
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }



}
