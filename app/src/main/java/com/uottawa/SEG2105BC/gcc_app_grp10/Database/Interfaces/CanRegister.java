package com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces;


import android.view.View;

import com.uottawa.SEG2105BC.gcc_app_grp10.Users.User;


public interface CanRegister {



    public void OnRegisterButton(View view);

    /**
     * called by the AuthenticationHandler once the new user is registered, note the database creates teh new user first
     * but there isn't a way to catch errors from it yet
     * @param user the user that was just registered
     */
    public void onRegistrationComplete(User user);

    /**
     * called by the AuthenticationHandler if the authentication fails
     */
    public void onRegistrationAuthenticationFailure();

    /**
     * called by the DatabaseHandler if the creation fails
     */
    public void onDatabaseFailure();





        /*
        * this is the thing that makes the user account in the Authentication thing in firebase
        * it's pretty nuts and i don't totally get how it works
        */
        /*
        String finalPath = path;

        fAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fUser = fAuth.getCurrentUser();//current user
                            String IDstring = fUser.getUid();//current user id

                            //uses current user id as primary key to save account as under
                            DatabaseReference ref = fDB.getReference(finalPath + IDstring);
                            ref.setValue(user);//this saves all the info really easy-like

                            Toast.makeText(Registration.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                            //switches window to welcome window
                            Intent intent = new Intent(getApplicationContext(), Welcome.class);
                            // Adds information to the intent for the welcome page to access
                            intent.putExtra("firstName", firstName.getText().toString());
                            if (roleParticipant.isChecked()) {
                                intent.putExtra("role", roleParticipant.getText().toString());
                            } else if (roleClub.isChecked()) {
                                intent.putExtra("role", roleClub.getText().toString());
                            }
                            else {
                                intent.putExtra("role", "unknown");
                            }
                            startActivity (intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(findViewById(android.R.id.content), "" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                */



}