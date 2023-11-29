package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.DatabaseHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAnEventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.Event;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.PropertyType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.SpecifiedProperty;
import com.uottawa.SEG2105BC.gcc_app_grp10.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AddEventPropertiesSecondPage extends AppCompatActivity implements CanReceiveAnEventType {
    String eventTypeName;
    String clubName;
    EventType eventType;
    ArrayList<Integer> fieldValueIDs;
    String eventName;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_properties_second_page);

        Intent intent = getIntent();
        DatabaseHandler databaseHandler = new DatabaseHandler();
        eventTypeName = intent.getStringExtra("eventTypeName");
        eventName = intent.getStringExtra("eventName");
        clubName = intent.getStringExtra("clubName");
        fieldValueIDs = new ArrayList<>();
        databaseHandler.loadEventType(this, eventTypeName, "addEvent");

    }

    public void onEventTypeRetrieved (String retrievingFunctionName, EventType eventType) {

        this.eventType = eventType;

        LinearLayout linearLayout = findViewById(R.id.fieldsLinearLayoutClubPage2);

        for (PropertyType property : eventType.getRequiredPropertyTypes()) {
            String propertyName = property.getName();
            String propertyType = property.getType().toString();

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
            newSpecifiedProperty.setHint("Enter Field Value");
            fieldLinearLayout.addView(newSpecifiedProperty);

            Space space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    24));
            fieldLinearLayout.addView(space);

            linearLayout.addView(fieldLinearLayout);
        }
    }

    public void onAddEventFieldsButton (View view) {

        int counter = 0;
        ArrayList<SpecifiedProperty> specifiedProperties = new ArrayList<>();

        for (PropertyType property : eventType.getRequiredPropertyTypes()) {
            String propertyName = property.getName();
            String propertyType = property.getType();

            // Get the corresponding filled in property field value
            EditText fieldValueEditText = findViewById(fieldValueIDs.get(counter));

            String fieldValue = fieldValueEditText.getText().toString();

            if (fieldValue.equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "You must fill in all fields!", Toast.LENGTH_LONG).show();
                return;
            }

            // Add the specified property
            SpecifiedProperty specifiedProperty = null;
            try {
                switch (propertyType) {
                    case "String":
                        specifiedProperty = new SpecifiedProperty(fieldValue, property);
                        break;
                    case "Integer":
                        specifiedProperty = new SpecifiedProperty(Integer.parseInt(fieldValue), property);
                        break;
                    case "Decimal":
                        specifiedProperty = new SpecifiedProperty(Double.parseDouble(fieldValue), property);
                        break;
                }
            } catch (Exception e) {
                Snackbar.make(findViewById(android.R.id.content), "For property " + propertyName + " you must give a " + propertyType +  " type variable!", Toast.LENGTH_LONG).show();
                return;
            }

            specifiedProperties.add(specifiedProperty);

            counter += 1;
        }

        Event event = new Event(eventName, eventType, specifiedProperties, clubName);

        System.out.println("Club name: " + clubName);

        DatabaseHandler databaseHandler = new DatabaseHandler();

        databaseHandler.addEvent(eventName, event);

        Toast.makeText(getApplicationContext(), "Added event!", Toast.LENGTH_SHORT).show();

        finish();

    }

    public void onEventTypeDatabaseFailure (String retrievingFunctionName) {
        System.out.println("Database Failure");
    }
}