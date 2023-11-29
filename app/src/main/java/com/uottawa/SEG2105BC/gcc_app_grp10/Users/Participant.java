package com.uottawa.SEG2105BC.gcc_app_grp10.Users;

import com.google.firebase.database.DataSnapshot;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class Participant extends User{
    private String firstName;

    public Participant(String username, String password, String email, String bio){
        super(username, password, email, bio);
    }

    @Override
    public String getRole(){
        return "participant";
    }

    //To be implemented
    public void joinEvent() {

    }

    public void leaveEvent() {

    }
    public Participant(DataSnapshot dataSnapshot) {
        super(dataSnapshot);
        firstName=dataSnapshot.child("firstname").getValue(String.class);
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
