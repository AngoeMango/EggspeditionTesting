package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.DatabaseHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveEventTypes;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveEvents;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AddEventProperties extends AppCompatActivity implements CanReceiveEventTypes {

    String eventName;
    String clubName;
    HashMap<Integer, Integer[]> fields;

    DatabaseHandler databaseHandler;
    int radioGroupID;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_properties);

        Intent intent = getIntent();
        eventName = intent.getStringExtra("eventName");
        clubName = intent.getStringExtra("clubName");

        databaseHandler = new DatabaseHandler();

        databaseHandler.loadAllEventTypes(this);
    }

    public void onEventTypesRetrieved (ArrayList<EventType> eventTypes) {
        LinearLayout linearLayout = findViewById(R.id.fieldsLinearLayoutClub);

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroupID = View.generateViewId();
        radioGroup.setId(radioGroupID);
        radioGroup.setLayoutParams(new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        for (EventType eventType : eventTypes) {
            String eventTypeName = eventType.getName();

            RadioButton eventTypeButton = new RadioButton(this);
            eventTypeButton.setId(View.generateViewId());
            eventTypeButton.setText(eventTypeName);
            radioGroup.addView(eventTypeButton);
        }

        linearLayout.addView(radioGroup);

        Space space = new Space(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                24));
        linearLayout.addView(space);

    }

    public void onEventTypesDatabaseFailure () {
        System.out.println("Database Failure");
    }

    public void onSelectEventTypeButton (View view) {
        RadioGroup radioGroup = findViewById(radioGroupID);
        int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);

        String selectedEventTypeName;

        try {
            selectedEventTypeName = selectedRadioButton.getText().toString();
        }
        catch (Exception e) {
            Snackbar.make(findViewById(android.R.id.content), "You must select a type for all properties!", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, AddEventPropertiesSecondPage.class);
        intent.putExtra("eventTypeName", selectedEventTypeName);
        intent.putExtra("eventName", eventName);
        intent.putExtra("clubName", clubName);

        startActivity(intent);
        finish();
    }


}