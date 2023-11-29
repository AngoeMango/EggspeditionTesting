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
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Admin;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.User;

public class AdminCanRegister extends AppCompatActivity implements CanRegister {
    EditText username ;
    EditText email;
    EditText password;
    EditText firstName;
    AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        authenticationHandler=new AuthenticationHandler();
    }

    public void OnRegisterButton(View view) {
        if(!validateInputs()){return;}
        User user;//to hold new users

        user = new Admin(username.getText().toString().trim(), password.getText().toString().trim(), email.getText().toString().trim());
        //makes the user with the info to be saved a little later
        authenticationHandler.signUp(this, user, email.getText().toString().trim(), password.getText().toString().trim(), this, this);
    }

    /**
     * called by the AuthenticationHandler once the new user is registered, note the database creates teh new user first
     * but there isn't a way to catch errors from it yet
     * @param user the user that was just registered
     */
    public void onRegistrationComplete(User user){
        Toast.makeText(AdminCanRegister.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(AdminCanRegister.this, "You need a username!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.getText().length() == 0) {
            Toast.makeText(AdminCanRegister.this, "You need an email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!email.getText().toString().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            Toast.makeText(AdminCanRegister.this, "Invalid email address!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length() < 6) {
            Toast.makeText(AdminCanRegister.this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
