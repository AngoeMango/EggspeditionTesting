package com.uottawa.SEG2105BC.gcc_app_grp10.Database;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAnEvent;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAnEventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveEventTypes;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveEvents;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanDeleteAUser;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAUser;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.*;

import java.util.ArrayList;


public class DatabaseHandler {
    DatabaseReference ref;


    public DatabaseHandler(){
        ref = FirebaseDatabase.getInstance().getReference();
    }
    // Method to write data to the database
    public void writeData(String userId, String role, String data) {
        ref.child("users/"+role+"/"+userId).setValue(data);
    }



    /*
    EventType Methods
     */


    public void addEventType(EventType eventType){
        ref.child("eventTypes/"+eventType.getName()).setValue(eventType);
    }

    public void addEvent(String eventName, Event event){
        System.out.println(event.getClubName());

        ref.child("events/"+eventName).setValue(event);
    }

    public void deleteEventType(String eventTypeName){
        ref.child("eventTypes/"+eventTypeName).removeValue();
    }


    /**
     * used to load an EventType from the database
     * @param main the class currently controlling the main thread
     * @param eventTypeName the name of the EventType your looking for
     * @param retrievingFunctionName the name of the type of operation being performed on the event type (add, edit, delete)
     */
    public void loadEventType(CanReceiveAnEventType main, String eventTypeName, String retrievingFunctionName){
        DatabaseReference userRef;

        try {
            userRef = ref.child("eventTypes/" + eventTypeName);
        }
        catch (Exception e) {
            main.onEventTypeDatabaseFailure("invalidName");
            return;
        }
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("database works");
                    // Retrieve data from the DataSnapshot
                    EventType eventType=new EventType(dataSnapshot);
                    main.onEventTypeRetrieved(retrievingFunctionName, eventType);
                }
                else{
                    main.onEventTypeDatabaseFailure(retrievingFunctionName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
            }
        });
    }


    /*
    Event Methods
     */

    /**
     * used to load an Event from the database
     *
     * @param main                  the class currently controlling the main thread
     * @param eventName             the name of the event your looking for
     * @param retrievingFunctionName
     */
    public void loadEvent(CanReceiveAnEvent main, String eventName, String callingClubName, String retrievingFunctionName){
        DatabaseReference userRef= ref.child("events/"+eventName);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("database works");
                    // Retrieve data from the DataSnapshot
                    Event event=new Event(dataSnapshot);



                    if (!event.getClubName().equals(callingClubName)) {
                        if (retrievingFunctionName.equals("addEvent")) {
                            main.onEventRetrieved("addEvent", event);
                        }
                        else {
                            main.onEventDatabaseFailure("callingClubNotAuthorized");
                        }
                    }
                    else {
                        main.onEventRetrieved(retrievingFunctionName, event);
                    }

                }
                else{
                    main.onEventDatabaseFailure(retrievingFunctionName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
            }
        });
    }

    /**
     * used to load an Event from the database
     *
     * @param main                  the class currently controlling the main thread
     */
    public void loadAllEvents(CanReceiveEvents main){
        DatabaseReference userRef= ref.child("events");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("database works");
                    // Retrieve data from the DataSnapshot
                    ArrayList<Event> events=new ArrayList<>();
                    for (DataSnapshot folderSnapshot : dataSnapshot.getChildren()) {
                        Event event = folderSnapshot.getValue(Event.class);
                        events.add(event);

                    }

                    main.onEventsRetrieved(events);
                }
                else{
                    main.onEventsDatabaseFailure();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
            }
        });
    }


    /**
     * used to load an Event from the database
     *
     * @param main                  the class currently controlling the main thread
     */
    public void loadAllEventTypes(CanReceiveEventTypes main){
        DatabaseReference userRef= ref.child("eventTypes");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("database works");
                    // Retrieve data from the DataSnapshot
                    ArrayList<EventType> eventTypes=new ArrayList<>();
                    for (DataSnapshot folderSnapshot : dataSnapshot.getChildren()) {
                        System.out.println(folderSnapshot);
                        EventType eventType=new EventType(folderSnapshot);
                        eventTypes.add(eventType);

                    }

                    main.onEventTypesRetrieved(eventTypes);
                }
                else{
                    main.onEventTypesDatabaseFailure();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
            }
        });
    }

    public void deleteEvent(String eventName){
        System.out.println("Event name" + eventName);
        ref.child("events/"+eventName).removeValue();
    }




    /*
    User Methods
     */




    public void addNewUserData(String userId, String role, User user){
        ref.child("users/theAdminsLittleBlackBook/"+user.getUsername()).setValue(userId);
        ref.child("users/"+role+"/"+userId).setValue(user);
    }

    //method for updating data in the database
    public void updateData(String userId, String role, String newData) {
        ref.child("users/"+role+"/"+userId).setValue(newData);
    }


    /**
     * Loads the data of an existing user, and passes it back to the main thread via the onUserDataRetrieved() method
     * @param main the class currently controlling the main thread, has to have the right functions, i.e. implement the right interface
     * @param userId
     * @param role
     */
    public void loadUserData(CanReceiveAUser main, String userId, String role){
        System.out.println("trying to read");
        //sends a request ot the server for data
        DatabaseReference userRef= ref.child("users/"+role+"/"+userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("database works");
                    // Retrieve data from the DataSnapshot
                    User user=User.makeUser(role, dataSnapshot);
                    main.onUserDataRetrieved(user);
                }
                else{
                    main.onUserDatabaseFailure();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
            }
        });
    }

    /**
     * Loads the data of an existing user from the admins book, and passes it back to the main thread via the onUserDataRetrieved() method     *
     * @param main the class currently controlling the main thread
     * @param userName
     * @param role
     */
    public void loadUserDataFromBook(CanReceiveAUser main, String userName, String role){
        System.out.println("trying to read");
        DatabaseReference userRef;
        //sends a request to the server for data
        userRef = ref.child("users/theAdminsLittleBlackBook/" + userName);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        System.out.println("database works");
                        // Retrieve data from the DataSnapshot
                        String userId = dataSnapshot.getValue(String.class);
                        loadUserData(main, userId, role);
                    }
                    catch (Exception e) {
                        main.onUserDatabaseFailure();
                    }
                }
                else{
                    main.onUserDatabaseFailure();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
                main.onUserDatabaseFailure();
            }
        });
    }



    /*
    methods for deleting data from the database
    */


    /**
     *  method used in Admin to delete a user given their username and role
     * @param main the instance currently controlling the main thread
     *            has to implement CanDeleteAUser so that it can be notified when/if the database succeeds or fails
     * @param userName the username of the user you're looking for
     * @param role the role of the user you want to delete
     */
    public void deleteUserData(CanDeleteAUser main, String userName, String role) {
        loadUserDataFromBookForDeletion(main, userName,role);
    }

    /**
     * helper method for deleting a user using their username, find their user id, then calls finishDeleting with that id
     * @param userName the username of the user you're looking for
     * @param role the role of the user you want to delete
     */
    private void loadUserDataFromBookForDeletion(CanDeleteAUser main, String userName, String role){
        DatabaseReference userRef;
        //sends a request to the server for data
        userRef = ref.child("users/theAdminsLittleBlackBook/" + userName);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        System.out.println("database works");
                        // Retrieve data from the DataSnapshot
                        String userId = dataSnapshot.getValue(String.class);
                        finishDeletion(main, userId, role, userName);
                    }
                    catch (Exception e) {
                        main.onUserDeleteAccountFailed();
                    }
                }
                else{
                    main.onUserDeleteAccountFailed();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
                main.onUserDeleteAccountFailed();
            }
        });
    }

    private void finishDeletion(CanDeleteAUser main, String userId, String role, String userName){
        //sends a request to the server for data
        DatabaseReference userRef= ref.child("users/"+role+"/"+userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ref.child("users/"+role+"/"+userId).removeValue();
                    ref.child("users/theAdminsLittleBlackBook/"+userName).removeValue();
                    main.onUserDeleteAccountSuccess();
                }
                else{
                    main.onUserDeleteAccountFailed();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("uh oh");
                main.onUserDeleteAccountFailed();
            }
        });
    }



  }




