package com.uottawa.SEG2105BC.gcc_app_grp10.Users;

import com.google.firebase.database.DataSnapshot;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.DatabaseHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;

import java.util.HashMap;


public class Club extends User{
    private HashMap<String ,Event> events;
    private String socialMediaLink;
    private String phoneNumber;
    private String mainContactName;

    public Club(String username, String password, String email, String bio, String socialMediaLink, String mainContactName, String phoneNumber){
        super(username, password, email, bio);
        events=new HashMap<>();
        this.mainContactName=mainContactName;
        this.phoneNumber=phoneNumber;
        this.socialMediaLink=socialMediaLink;
    }

    public Club(DataSnapshot dataSnapshot){
        super(dataSnapshot);
        events=new HashMap<>();
    }

    @Override
    public String getRole(){
        return "club";
    }

    public Event getEvent(String name){
        return events.get(name);
    }

    public void addEvent(Event event){
        events.put(event.getName(), event);
    }

    public HashMap<String, Event> getEvents(){
        return events;
    }


    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        this.socialMediaLink = socialMediaLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public void setMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
    }
}
