package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.AuthenticationHandler;
import com.uottawa.SEG2105BC.gcc_app_grp10.R;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanReceiveAUser;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Admin;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.Club;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.User;

import static java.util.Objects.requireNonNull;

public class MainActivity extends AppCompatActivity implements CanReceiveAUser {
    EditText email;
    EditText password;
    RadioButton roleParticipant;
    RadioButton roleClub;
    RadioButton roleAdmin;
    AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        roleParticipant = findViewById(R.id.roleParticipant);
        roleClub = findViewById(R.id.roleClub);
        roleAdmin = findViewById(R.id.roleAdmin);
        authenticationHandler=new AuthenticationHandler();
    }

    public void toRegisterParticipantPageButton(View view){
        //when go to register gets pressed, bring you to register page
        Intent intent = new Intent(getApplicationContext(), ParticipantCanRegister.class);
        startActivity(intent);
    }

    public void toRegisterClubPageButton(View view){
        //when go to register gets pressed, bring you to register page
        Intent intent = new Intent(getApplicationContext(), ClubCanRegister.class);
        startActivity (intent);
    }

    public void toRegisterAdminPageButton(View view){
        //when go to register gets pressed, bring you to register page
        Intent intent = new Intent(getApplicationContext(), AdminCanRegister.class);
        startActivity (intent);
    }

    public void OnLoginButton(View view) {
        System.out.println("button");
        if(!validateInputs()){return;}
        String role=checkRole();
        //attempts to sign in to the users account
        authenticationHandler.signIn(this,email.getText().toString().trim(),password.getText().toString().trim(),role,this,this);
    }

    /**
     * Called by the DatabaseHandler once the users data has been loaded
     * @param user the user data that was retrieved
     */
    @Override
    public void onUserDataRetrieved(User user){
        //notifies the user that the loading was successful
        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
        if (user.getClass().equals(Admin.class)) {
            Intent intent = new Intent(getApplicationContext(), AdminWelcome.class);
            startActivity(intent);
        }
        else if (user.getClass().equals(Club.class)) {

            Intent intent = new Intent(getApplicationContext(), ClubWelcome.class);
            intent.putExtra("firstName", user.getUsername());
            intent.putExtra("role", user.getRole());
            intent.putExtra("clubName", user.getUsername());
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), Welcome.class);
            // Adds information to the intent for the welcome page to access
            intent.putExtra("firstName", user.getUsername());
            intent.putExtra("role", user.getRole());

            System.out.println("going to next activity");
            startActivity(intent);
        }
    }

    /**
     * called by the AuthenticationHandler if the authentication fails
     */
    public void onLoginAuthorisationFailure(){
        Snackbar.make(findViewById(android.R.id.content), "No user exists with given role (or password is wrong)!", Snackbar.LENGTH_LONG).show();
    }



    /**
     * called by the DatabaseHandler is the retrieval fails
     */
    @Override
    public void onUserDatabaseFailure(){
        Snackbar.make(findViewById(android.R.id.content), "Signing in user failed!", Snackbar.LENGTH_LONG).show();
    }

    private String checkRole(){
        if (roleParticipant.isChecked()){
            return "participant";
        } else if (roleClub.isChecked()) {
            return "club";
        } else if (roleAdmin.isChecked()) {
            return "admin";
        }
        else {
            Toast.makeText(MainActivity.this, "Select a Role!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private boolean validateInputs(){
        if (email.getText().length() == 0){
            Toast.makeText(MainActivity.this, "Enter an email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().length() == 0){
            Toast.makeText(MainActivity.this, "Enter a password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!roleParticipant.isChecked()&&!roleClub.isChecked()&&!roleAdmin.isChecked()) {
            Toast.makeText(MainActivity.this, "Select a role!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}