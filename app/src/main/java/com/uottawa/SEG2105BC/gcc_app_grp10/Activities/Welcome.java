package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.uottawa.SEG2105BC.gcc_app_grp10.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get information from registration page
        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String role = intent.getStringExtra("role");

        // Select the text views on the screen
        TextView welcomeMessageTextView = findViewById(R.id.welcomeTextView);
        TextView welcomeRoleTextView = findViewById(R.id.welcomeRoleTextView);

        // Set the welcome messages with the username and role
        if (firstName != null) {
            welcomeMessageTextView.setText("Welcome " + firstName + "!");
        }

        if (role != null && !role.equals("unknown")) {
            welcomeRoleTextView.setText("You are logged in as " + role + ".");
        }
    }
}