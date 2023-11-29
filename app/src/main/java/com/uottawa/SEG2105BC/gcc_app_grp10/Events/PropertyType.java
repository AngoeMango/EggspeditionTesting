package com.uottawa.SEG2105BC.gcc_app_grp10.Events;

import java.io.Serializable;
import java.lang.reflect.Type;

public class PropertyType implements Serializable {

    private String type;
    private String name;

    public PropertyType(){

    }

    public PropertyType(String name){
        this.name=name;
    }

    public PropertyType(PropertyType propertyType){
        this.type=propertyType.getType();
        this.name= propertyType.getName();
    }

    public PropertyType(String name, String type){
        this.name=name;
        this.type = type;
    }


    /**
     * like all the other methods, SpecifiedProperty inherits this
     * and it still overrides it's equal method
     * @param o
     * @return
     */
//    @Override
//    public boolean equals(Object o){
//        if(o instanceof PropertyType){
//            if(((PropertyType) o).getName().equals(name)){
//                return true;
//            }
//        }
//        return false;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public PropertyType clone(){
        return new PropertyType(this);
    }

}
