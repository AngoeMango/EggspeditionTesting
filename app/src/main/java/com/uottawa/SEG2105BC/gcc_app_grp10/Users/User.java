package com.uottawa.SEG2105BC.gcc_app_grp10.Users;

import com.google.firebase.database.DataSnapshot;

import java.security.InvalidParameterException;
import java.util.HashMap;

public abstract class User {

    private String username;
    private String password;
    private String bio;
    private String email;

    public static User makeUser(String role, DataSnapshot dataSnapshot) {
        switch (role) {
            case "club":
                return new Club(dataSnapshot);
            case "participant":
                return new Participant(dataSnapshot);
            case "admin":
                return new Admin("admin", "admin1", "admin@admin");
            default:
                throw new InvalidParameterException();
        }
    }


    protected User(String username, String password, String email, String bio){
        this.username=username;
        this.password=password;
        this.email=email;
        this.bio=bio;
    }

    protected User(DataSnapshot dataSnapshot){
        username=dataSnapshot.child("username").getValue(String.class);
        password=dataSnapshot.child("password").getValue(String.class);
        email=dataSnapshot.child("email").getValue(String.class);
        bio=dataSnapshot.child("bio").getValue(String.class);
    }


    public boolean isPasswordCorrect(String passwordAttempt) {
        return password.equals(passwordAttempt);
    }

    public boolean setPassword(String passwordAttempt, String newPassword){
        if (isPasswordCorrect(passwordAttempt)){password=newPassword;return true;}
        else{return false;}
    }


    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getBio(){
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public abstract String getRole();

}
