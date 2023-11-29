package com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces;

import com.uottawa.SEG2105BC.gcc_app_grp10.Users.User;

/**
 * lowkey I'm kinda cooking with this one
 * The idea is that since data is retrieved asynchronously any class that wants to
 * load a user needs a function that can be called to pass it the user from the thread listening to the database
 * We have to pass that class and call that function in the database loadUserData function
 * this interface means we can guarantee that any class in=mplementing it has the required function
 * so we don't have to overload the loadUserData function we just pass it a class that CanReceiveAUser lol
 */
public interface CanReceiveAUser {
    void onUserDataRetrieved(User user);
    void onUserDatabaseFailure();
}
