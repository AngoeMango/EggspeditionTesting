package com.uottawa.SEG2105BC.gcc_app_grp10.Users;

import com.uottawa.SEG2105BC.gcc_app_grp10.Database.DatabaseHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAnEventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.PropertyType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanDeleteAUser;

import java.util.ArrayList;

public class Admin extends User{

    DatabaseHandler databaseHandler;

    public Admin(String username, String password, String email){
        super(username, password, email, "Admin account");
        databaseHandler=new DatabaseHandler();
    }

    public void deleteAccount(CanDeleteAUser main, String userName, String role){
        if(role.equals("admin")){
            return;
        }
        databaseHandler.deleteUserData(main, userName, role);
    }

    public EventType createEventType(String name, ArrayList<PropertyType> properties){
        EventType eventType=new EventType(name, properties);
        databaseHandler.addEventType(eventType);
        return eventType;
    }


    public EventType createEventType(String name){
        EventType eventType=new EventType(name);
        databaseHandler.addEventType(eventType);
        return eventType;
    }

    public EventType createEventType(EventType eventType){
        databaseHandler.addEventType(eventType);
        return eventType;
    }

    public void deleteEventType(String name){
        databaseHandler.deleteEventType(name);
    }

    public void loadEventType(CanReceiveAnEventType main, String name, String receivingFunctionName ){
        databaseHandler.loadEventType(main, name, receivingFunctionName);
    }

    public void deleteEvent(String name){
        databaseHandler.deleteEvent(name);
    }

    /**
     * Admins can modify events made by clubs for the purposes of moderation
     */
    public void editEvent(){

    }

    /**
     * Admins can delete events made by clubs for the purposes of moderation
     */
    public void deleteEvent(){

    }

    @Override
    public String getRole(){
        return "admin";
    }
}
