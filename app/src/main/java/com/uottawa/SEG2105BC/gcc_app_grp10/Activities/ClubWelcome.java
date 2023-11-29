package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.DatabaseHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAnEvent;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAnEventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.R;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Club;

public class ClubWelcome extends AppCompatActivity implements CanReceiveAnEvent {

    EditText addEventName;
    String clubName;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_welcome);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String role = intent.getStringExtra("role");
        clubName = intent.getStringExtra("clubName");

        addEventName = findViewById(R.id.EventEditText);

        // Select the text views on the screen
        TextView welcomeMessageTextView = findViewById(R.id.welcomeTextView3);
        TextView welcomeRoleTextView = findViewById(R.id.welcomeRoleTextView3);

        // Set the welcome messages with the username and role
        if (firstName != null) {
            welcomeMessageTextView.setText("Welcome " + firstName + "!");
        }

        if (role != null && !role.equals("unknown")) {
            welcomeRoleTextView.setText("You are logged in as " + role + ".");
        }

    }

    public void onAddEventButton(View view) {

        if (addEventName.getText().toString().equals("")) {
            Snackbar.make(findViewById(android.R.id.content), "You must enter an event name!", Snackbar.LENGTH_LONG).show();
        }
        else {
            DatabaseHandler databaseHandler=new DatabaseHandler();
            databaseHandler.loadEvent(this, addEventName.getText().toString(),clubName,  "addEvent");        }
    }

    public void onEditEventButton(View view) {

        if (addEventName.getText().toString().equals("")) {
            Snackbar.make(findViewById(android.R.id.content), "You must enter an event name!", Snackbar.LENGTH_LONG).show();
        }
        else {
            DatabaseHandler databaseHandler=new DatabaseHandler();
            databaseHandler.loadEvent(this, addEventName.getText().toString(), clubName, "editEvent");        }
    }

    public void onDeleteEventButton(View view) {
        if (addEventName.getText().toString().equals("")) {
            Snackbar.make(findViewById(android.R.id.content), "You must enter an event name!", Snackbar.LENGTH_LONG).show();
        }
        else {
            DatabaseHandler databaseHandler=new DatabaseHandler();
            databaseHandler.loadEvent(this, addEventName.getText().toString(), clubName,  "deleteEvent");        }
    }

    @Override
    public void onEventRetrieved(String retrievingFunctionName, Event event) {
        switch(retrievingFunctionName){
            case "addEvent":
                Snackbar.make(findViewById(android.R.id.content), "Event already exists!", Snackbar.LENGTH_LONG).show();
                break;
            case "editEvent":
                Intent intent = new Intent(getApplicationContext(), EditEventProperties.class);
                intent.putExtra("eventName", event.getName());
                intent.putExtra("eventTypeName", event.getEventTypeName());
                intent.putExtra("clubName", event.getClubName());
                intent.putExtra("callingClubName", clubName);
                startActivity(intent);
                break;
            case "deleteEvent":
                DatabaseHandler databaseHandler = new DatabaseHandler();
                databaseHandler.deleteEvent(event.getName());

                Snackbar.make(findViewById(android.R.id.content), "Deleted event!", Snackbar.LENGTH_LONG).show();
                break;
            default:
                Snackbar.make(findViewById(android.R.id.content), "invalid function name got passed", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onEventDatabaseFailure (String retrievingFunctionName) {
        switch(retrievingFunctionName){
            case "addEvent":
                Intent intent = new Intent(getApplicationContext(), AddEventProperties.class);
                intent.putExtra("eventName", addEventName.getText().toString());
                intent.putExtra("clubName", clubName);
                startActivity(intent);
                break;
            case "editEvent":
                Snackbar.make(findViewById(android.R.id.content), "No event exists with that name!", Snackbar.LENGTH_LONG).show();
                break;
            case "deleteEvent":
                Snackbar.make(findViewById(android.R.id.content),  "No event exists with that name!", Snackbar.LENGTH_LONG).show();
                break;
            case "callingClubNotAuthorized":
                Snackbar.make(findViewById(android.R.id.content), "You can only allowed to make changes to events your club created!", Snackbar.LENGTH_LONG).show();
                break;
            default:
                Snackbar.make(findViewById(android.R.id.content), "Invalid event type name (or other database failure)!", Snackbar.LENGTH_LONG).show();
        }

    }
}