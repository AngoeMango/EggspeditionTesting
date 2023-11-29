package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.AuthenticationHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanRegister;
import com.uottawa.SEG2105BC.gcc_app_grp10.R;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Club;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.User;

public class ClubCanRegister extends AppCompatActivity implements CanRegister {

    EditText username ;
    EditText email;
    EditText bio;
    EditText password;
    EditText contactName;
    EditText phoneNumber;
    EditText socialMedia;
    AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_registration);

        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailEditText);
        bio = findViewById(R.id.bioEditText);
        password = findViewById(R.id.passwordEditText);
        contactName = findViewById(R.id.contactEditText);
        phoneNumber = findViewById(R.id.phoneEditText);
        socialMedia = findViewById(R.id.socialEditText);
        authenticationHandler=new AuthenticationHandler();
    }

    public void OnRegisterButton(View view) {
        if(!validateInputs()){return;}
        User user;//to hold new users

        user = new Club(username.getText().toString().trim(), password.getText().toString().trim(), email.getText().toString().trim(), bio.getText().toString().trim(), socialMedia.getText().toString().trim(), contactName.getText().toString().trim(), phoneNumber.getText().toString().trim());
        //makes the user with the info to be saved a little later
        authenticationHandler.signUp(this, user, email.getText().toString().trim(), password.getText().toString().trim(), this, this);
    }

    /**
     * called by the AuthenticationHandler once the new user is registered, note the database creates teh new user first
     * but there isn't a way to catch errors from it yet
     * @param user the user that was just registered
     */
    public void onRegistrationComplete(User user){
        Toast.makeText(ClubCanRegister.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

        //switches window to welcome window
        Intent intent = new Intent(getApplicationContext(), Welcome.class);
        // Adds information to the intent for the welcome page to access
        intent.putExtra("firstName", username.getText().toString());
        intent.putExtra("role", user.getRole().toString());

        startActivity (intent);
    }

    /**
     * called by the AuthenticationHandler if the authentication fails
     */
    public void onRegistrationAuthenticationFailure(){
        Snackbar.make(findViewById(android.R.id.content), "registration failed" , Toast.LENGTH_LONG).show();
    }

    /**
     * called by the DatabaseHandler if the creation fails
     */
    public void onDatabaseFailure(){
        Snackbar.make(findViewById(android.R.id.content), "registration failed" , Toast.LENGTH_LONG).show();
    }

    private boolean validateInputs(){
        //if username is left empty, that's a nono
        if (username.getText().length() == 0) {
            Toast.makeText(ClubCanRegister.this, "You need a username!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.getText().length() == 0) {
            Toast.makeText(ClubCanRegister.this, "You need a phone number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // checks that the phone number is valid according to https://ihateregex.io/expr/phone/
        if (!phoneNumber.getText().toString().matches("^^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")) {
            Toast.makeText(ClubCanRegister.this, "You need a valid phone number with 10 digits!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (socialMedia.getText().length() == 0) {
            Toast.makeText(ClubCanRegister.this, "You need a social media link!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // checks that the social media link is in the form https://name.com/name or http://name.org/name
        if (!socialMedia.getText().toString().matches("^(https?:\\/\\/)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$")) {
            Toast.makeText(ClubCanRegister.this, "Invalid social media link (need domain)!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.getText().length() == 0) {
            Toast.makeText(ClubCanRegister.this, "You need an email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!email.getText().toString().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            Toast.makeText(ClubCanRegister.this, "Invalid email address!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length() < 6) {
            Toast.makeText(ClubCanRegister.this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // checks that the contact name is a valid name, if there is any
        if (!contactName.getText().toString().matches("^[A-Za-z]*([ ][A-Za-z]+)?$|^$")) {
            Toast.makeText(ClubCanRegister.this, "You need a valid contact name formed of letters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}