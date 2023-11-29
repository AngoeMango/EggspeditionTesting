package com.uottawa.SEG2105BC.gcc_app_grp10.Events;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class EventType implements Serializable {
    private String name;
    private ArrayList<PropertyType> requiredPropertyTypes;

    public EventType(String name){
        this.name=name;
        requiredPropertyTypes = new ArrayList<>();
    }
    public EventType(String name, ArrayList<PropertyType> propertyTypeList){
        this.name=name;
        requiredPropertyTypes.addAll(propertyTypeList);
    }

    public EventType(EventType template){
        this.name= template.getName();
        requiredPropertyTypes =new ArrayList<>();
        requiredPropertyTypes.addAll(template.getRequiredPropertyTypes());
    }

    public EventType(DataSnapshot dataSnapshot){
        name=dataSnapshot.child("name").getValue(String.class);
        requiredPropertyTypes = new ArrayList<>();
        for (DataSnapshot propertySnapshot : dataSnapshot.child("requiredPropertyTypes").getChildren()) {
            requiredPropertyTypes.add(new PropertyType(propertySnapshot.child("name").getValue(String.class), propertySnapshot.child("type").getValue(String.class)));
        }

    }

    /*
    these ones should only be accessible by admin
     */

    public void addRequiredPropertyToType(String newProperty, String type){
        requiredPropertyTypes.add(new PropertyType(newProperty, type));
    }

    public void removeRequiredPropertyTypeFromEventType(String property){
        requiredPropertyTypes.remove(property);
    }

    public void setRequiredPropertyTypes(ArrayList<PropertyType> requiredPropertyTypes) {
        this.requiredPropertyTypes = requiredPropertyTypes;
    }

    public ArrayList<PropertyType> getRequiredPropertyTypes() {
        return requiredPropertyTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
