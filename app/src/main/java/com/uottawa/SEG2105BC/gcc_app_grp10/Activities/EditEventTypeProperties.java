package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.EventType;
import com.uottawa.SEG2105BC.gcc_app_grp10.Events.PropertyType;
import com.uottawa.SEG2105BC.gcc_app_grp10.R;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EditEventTypeProperties extends AppCompatActivity {

    Admin admin;
    String eventTypeName;
    ArrayList<PropertyType> eventTypeProperties;
    HashMap<Integer, Integer[]> fields;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_type_properties);

        Intent intent = getIntent();
        eventTypeName = intent.getStringExtra("eventTypeName");
        eventTypeProperties = (ArrayList<PropertyType>) intent.getSerializableExtra("eventTypeProperties");
        System.out.println(eventTypeProperties);
        fields = new HashMap<>();

        admin = new Admin("admin", "admin1", "admin@admin.com");

        LinearLayout linearLayout = findViewById(R.id.fieldsLinearLayout);

        for (PropertyType property : eventTypeProperties) {
            String propertyName = property.getName();
            String propertyType = property.getType().toString();

            LinearLayout fieldLinearLayout = new LinearLayout(this);
            int fieldGroupId = View.generateViewId();
            fieldLinearLayout.setId(fieldGroupId);
            fieldLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            fieldLinearLayout.setOrientation(LinearLayout.VERTICAL);

            EditText newProperty = new EditText(this);
            newProperty.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            int newPropertyID = View.generateViewId();
            System.out.println(newPropertyID);
            newProperty.setId(newPropertyID);
            newProperty.setText(propertyName);
            fieldLinearLayout.addView(newProperty);

            RadioGroup radioGroup = new RadioGroup(this);
            int radioGroupID = View.generateViewId();
            radioGroup.setId(radioGroupID);
            radioGroup.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT));
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
            RadioButton stringButton = new RadioButton(this);
            int stringButtonID = View.generateViewId();
            stringButton.setId(stringButtonID);
            stringButton.setText("String");
            radioGroup.addView(stringButton);

            RadioButton integerButton = new RadioButton(this);
            int integerButtonID = View.generateViewId();
            integerButton.setId(integerButtonID);
            integerButton.setText("Integer");
            radioGroup.addView(integerButton);

            RadioButton decimalButton = new RadioButton(this);
            int decimalButtonID = View.generateViewId();
            decimalButton.setId(decimalButtonID);
            decimalButton.setText("Decimal");
            radioGroup.addView(decimalButton);

            if ("Integer".equals(propertyType)) {
                integerButton.setChecked(true);
            } else if ("Decimal".equals(propertyType)) {
                decimalButton.setChecked(true);
            } else if ("String".equals(propertyType)) {
                stringButton.setChecked(true);
            }

            fieldLinearLayout.addView(radioGroup);

            Button removeButton = new Button(this);
            removeButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            int removeButtonId = View.generateViewId();
            removeButton.setId(removeButtonId);
            removeButton.setText("Remove field");
            removeButton.setOnClickListener(view -> {
                int removeButtonID = view.getId();
                onFieldRemoveButton(removeButtonID);
            });

            fieldLinearLayout.addView(removeButton);

            fields.put(removeButtonId, new Integer[] {fieldGroupId, radioGroupID, newPropertyID});

            Space space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    24));
            fieldLinearLayout.addView(space);

            linearLayout.addView(fieldLinearLayout);
        }

    }

    private void onFieldRemoveButton (int removeButtonID) {
        LinearLayout linearLayout = findViewById(R.id.fieldsLinearLayout);
        View fieldGroup = linearLayout.findViewById(fields.get(removeButtonID)[0]);

        if (fieldGroup != null) {
            linearLayout.removeView(fieldGroup);
            fields.remove(removeButtonID);
        }
    }

    public void onAddIndividualFieldButton(View view) {
        LinearLayout linearLayout = findViewById(R.id.fieldsLinearLayout);

        LinearLayout fieldLinearLayout = new LinearLayout(this);
        int fieldGroupId = View.generateViewId();
        fieldLinearLayout.setId(fieldGroupId);
        fieldLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        fieldLinearLayout.setOrientation(LinearLayout.VERTICAL);

        EditText newField = new EditText(this);
        newField.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        int newFieldID = View.generateViewId();
        System.out.println(newFieldID);
        newField.setId(newFieldID);
        newField.setHint("Enter field name");
        fieldLinearLayout.addView(newField);

        // Create a RadioGroup
        RadioGroup radioGroup = new RadioGroup(this);
        Integer radioGroupID = View.generateViewId();
        radioGroup.setId(radioGroupID);
        radioGroup.setLayoutParams(new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);

        RadioButton stringButton = new RadioButton(this);
        stringButton.setId(View.generateViewId());
        stringButton.setText("String");
        radioGroup.addView(stringButton);

        RadioButton integerButton = new RadioButton(this);
        integerButton.setId(View.generateViewId());
        integerButton.setText("Integer");
        radioGroup.addView(integerButton);

        RadioButton decimalButton = new RadioButton(this);
        decimalButton.setId(View.generateViewId());
        decimalButton.setText("Decimal");
        radioGroup.addView(decimalButton);

        Button removeButton = new Button(this);
        removeButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        int removeButtonId = View.generateViewId();
        removeButton.setId(removeButtonId);
        removeButton.setText("Remove field");
        removeButton.setOnClickListener(removeView -> {
            int removeButtonID = removeView.getId();
            onFieldRemoveButton(removeButtonID);
        });

        fieldLinearLayout.addView(radioGroup);

        fieldLinearLayout.addView(removeButton);

        fields.put(removeButtonId, new Integer[] {fieldGroupId, radioGroupID, newFieldID});

        Space space = new Space(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                24));
        fieldLinearLayout.addView(space);

        linearLayout.addView(fieldLinearLayout);

    }

    public void onEditFieldsButton(View view) {
        ArrayList<String> propertyNames = new ArrayList<>();

        EventType newEventType = new EventType(eventTypeName);

        for (Map.Entry<Integer, Integer[]> entry : fields.entrySet()) {
            Integer fieldNameID = entry.getValue()[2];
            Integer fieldTypeID = entry.getValue()[1];

            EditText currentFieldName = findViewById(fieldNameID);
            RadioGroup currentFieldType = findViewById(fieldTypeID);

            int selectedRadioButtonId = currentFieldType.getCheckedRadioButtonId();

            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

            String selectedText;

            try {
                selectedText = selectedRadioButton.getText().toString();
            }
            catch (Exception e) {
                Snackbar.make(findViewById(android.R.id.content), "You must select a type for all properties!", Toast.LENGTH_LONG).show();
                return;
            }

            System.out.println(selectedRadioButtonId);

            if (currentFieldName.getText().toString().equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "You cannot have an empty field", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (propertyNames.contains(currentFieldName.getText().toString())) {
                    Snackbar.make(findViewById(android.R.id.content), "You cannot give a duplicate field name", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    propertyNames.add(currentFieldName.getText().toString());
                    newEventType.addRequiredPropertyToType(currentFieldName.getText().toString(), selectedText);
                }
            }
        }

        if (fields.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "You cannot have an event with no properties!", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            admin.createEventType(newEventType);
        }

        Toast.makeText(getApplicationContext(), "Edited event type!", Toast.LENGTH_SHORT).show();

        finish();
    }
}