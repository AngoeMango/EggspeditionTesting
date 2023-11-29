package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.DatabaseHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAnEvent;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveEventTypes;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.PropertyType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.SpecifiedProperty;
import com.uottawa.SEG2105BC.gcc_app_grp10.R;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditEventProperties extends AppCompatActivity implements CanReceiveAnEvent {

    String eventName;
    String eventTypeName;
    String clubName;
    ArrayList<SpecifiedProperty> eventSpecifiedProperties;
    ArrayList<Integer> fieldValueIDs;
    String callingClubName;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_properties);

        fieldValueIDs = new ArrayList<>();

        DatabaseHandler databaseHandler = new DatabaseHandler();

        Intent intent = getIntent();
        clubName = intent.getStringExtra("clubName");
        eventName = intent.getStringExtra("eventName");
        eventTypeName = intent.getStringExtra("eventTypeName");
        callingClubName = intent.getStringExtra("callingClubName");

        databaseHandler.loadEvent(this, getIntent().getStringExtra("eventName"), callingClubName, "editEvent");
    }

    public void onEventRetrieved (String retrievingFunctionName, Event event) {
        eventName = event.getName();
        clubName = event.getClubName();
        eventTypeName = event.getEventTypeName();
        eventSpecifiedProperties = event.getSpecifiedProperties();

        System.out.println("EVENT SPECIFIED PROPERTIES");
        System.out.println(eventSpecifiedProperties);

        LinearLayout linearLayout = findViewById(R.id.fieldsLinearLayoutClubEditPage);

        for (SpecifiedProperty specifiedProperty : eventSpecifiedProperties) {
            String propertyName = specifiedProperty.getPropertyType().getName();
            String propertyType = specifiedProperty.getPropertyType().getType();

            LinearLayout fieldLinearLayout = new LinearLayout(this);
            int fieldGroupId = View.generateViewId();
            fieldLinearLayout.setId(fieldGroupId);
            fieldLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            fieldLinearLayout.setOrientation(LinearLayout.VERTICAL);

            TextView newProperty = new TextView(this);
            newProperty.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            newProperty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            newProperty.setTypeface(null, Typeface.BOLD);

            int newPropertyID = View.generateViewId();
            newProperty.setId(newPropertyID);
            newProperty.setText("Field Name: " + propertyName + " \nField Type: " + propertyType);
            fieldLinearLayout.addView(newProperty);

            EditText newSpecifiedProperty = new EditText(this);
            newProperty.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            int newSpecifiedPropertyID = View.generateViewId();
            fieldValueIDs.add(newSpecifiedPropertyID);
            newSpecifiedProperty.setId(newSpecifiedPropertyID);
            newSpecifiedProperty.setText(specifiedProperty.getValue().toString());
            fieldLinearLayout.addView(newSpecifiedProperty);

            Space space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    24));
            fieldLinearLayout.addView(space);

            linearLayout.addView(fieldLinearLayout);
        }

    }

    public void onEditEventFieldsButton (View view) {

        int counter = 0;
        ArrayList<SpecifiedProperty> newSpecifiedProperties = new ArrayList<>();

        for (SpecifiedProperty specifiedProperty : eventSpecifiedProperties) {
            String propertyName = specifiedProperty.getPropertyType().getName();
            String propertyType = specifiedProperty.getPropertyType().getType();

            // Get the corresponding filled in property field value
            EditText fieldValueEditText = findViewById(fieldValueIDs.get(counter));

            String fieldValue = fieldValueEditText.getText().toString();

            if (fieldValue.equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "You must fill in all fields!", Toast.LENGTH_LONG).show();
                return;
            }

            // Add the specified property
            SpecifiedProperty newSpecifiedProperty = null;
            try {
                switch (propertyType) {
                    case "String":
                        newSpecifiedProperty = new SpecifiedProperty(fieldValue, specifiedProperty.getPropertyType());
                        break;
                    case "Integer":
                        newSpecifiedProperty = new SpecifiedProperty(Integer.parseInt(fieldValue), specifiedProperty.getPropertyType());
                        break;
                    case "Decimal":
                        newSpecifiedProperty = new SpecifiedProperty(Double.parseDouble(fieldValue), specifiedProperty.getPropertyType());
                        break;
                }
            } catch (Exception e) {
                Snackbar.make(findViewById(android.R.id.content), "For property " + propertyName + " you must give a " + propertyType +  " type variable!", Toast.LENGTH_LONG).show();
                return;
            }

            newSpecifiedProperties.add(newSpecifiedProperty);

            counter += 1;
        }

        Event event = new Event(eventName, eventTypeName, newSpecifiedProperties, clubName);

        DatabaseHandler databaseHandler = new DatabaseHandler();

        databaseHandler.addEvent(eventName, event);

        Toast.makeText(getApplicationContext(), "Edited event!", Toast.LENGTH_SHORT).show();

        finish();

    }

    public void onEventDatabaseFailure (String retrievingFunctionName) {
        System.out.println("Database Failure");
    }
}