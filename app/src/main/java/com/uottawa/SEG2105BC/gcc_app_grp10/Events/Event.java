package com.uottawa.SEG2105BC.gcc_app_grp10.Events;

import com.google.firebase.database.DataSnapshot;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.DatabaseHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Club;

import java.util.ArrayList;
import java.util.Objects;

public class Event {
    private String name;
    private ArrayList<SpecifiedProperty> specifiedProperties;

    private String eventTypeName;
    private EventType eventType;
    private String clubName;

    /**
     * simplest implementation of an event
     *
     * @param name
     * @param eventType
     * @param clubName
     * @param specifiedProperties
     */
    public Event(String name, EventType eventType, ArrayList<SpecifiedProperty> specifiedProperties, String clubName) {
        this.name = name;
        this.eventType = eventType;
        this.eventTypeName = eventType.getName();
        this.specifiedProperties = specifiedProperties;
        this.clubName = clubName;
    }

    public Event(String name, String eventTypeName, ArrayList<SpecifiedProperty> specifiedProperties, String clubName) {
        this.name = name;
        this.eventTypeName = eventTypeName;
        this.specifiedProperties = specifiedProperties;
        this.clubName = clubName;
    }

    /**
     * used when loading an event Type from the database
     * @param dataSnapshot this is the form data takes when loaded from the database
     */
    public Event(DataSnapshot dataSnapshot) {
        name = dataSnapshot.child("name").getValue(String.class);
        eventTypeName = dataSnapshot.child("eventTypeName").getValue(String.class);
        clubName = dataSnapshot.child("clubName").getValue(String.class);
        specifiedProperties = new ArrayList<>();
        for (DataSnapshot propertySnapshot : dataSnapshot.child("specifiedProperties").getChildren()) {
            specifiedProperties.add(new SpecifiedProperty(propertySnapshot.child("value").getValue(Object.class), propertySnapshot.child("propertyType").getValue(PropertyType.class)));
        }
    }


    /**
     * The idea here is the event is valid if it has a value for every property type
     * it can have other propertyTypes and values if it wants to though
     * @return whether or not the event is valide
     */
    public boolean validate(){
        for (PropertyType requiredType:eventType.getRequiredPropertyTypes()) {
            if(!specifiedProperties.contains(requiredType))return false;
        }
        return true;
    }

    public void addProperty(String name, SpecifiedProperty property){
        specifiedProperties.add(property);
    }

    public void removeProperty(String propertyName){
//        for (SpecifiedProperty specifiedProperty:specifiedProperties) {
//            if(specifiedProperty.propertyType.getName().equals(propertyName))properties.remove(property);
//        }
    }

    public String getName() {
        return name;
    }


    public ArrayList<SpecifiedProperty> getSpecifiedProperties() {
        return specifiedProperties;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public String getClubName() {
        return clubName;
    }


    public void setName(String name) {
        this.name = name;
    }

}
