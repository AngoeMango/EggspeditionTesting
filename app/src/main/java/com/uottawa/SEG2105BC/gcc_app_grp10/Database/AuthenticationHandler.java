package com.uottawa.SEG2105BC.gcc_app_grp10.Database;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.SEG2105BC.gcc_app_grp10.Activities.MainActivity;
import com.uottawa.SEG2105BC.gcc_app_grp10.Database.Interfaces.CanRegister;
import com.uottawa.SEG2105BC.gcc_app_grp10.Users.User;

public class AuthenticationHandler {
    private FirebaseAuth mAuth;
    DatabaseHandler databaseHandler;

    public AuthenticationHandler() {
        // Initialize Firebase Auth and DatabaseHandler
        mAuth = FirebaseAuth.getInstance();
        databaseHandler=new DatabaseHandler();
    }

    /**
     * performs the first time authorisation and automatically calls the database handler to make a new user
     * @param main the class currently controlling the main thread, this method must be called from the registration screen
     * @param user the user to register
     * @param email
     * @param password
     * @param activity the current activity being displayed
     * @param context no clue honestly, might be the main thread
     */
    public void signUp(CanRegister main, User user, String email, String password, final Activity activity, final Context context) {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//
//        //sends a request ot the server for data
//        DatabaseReference userRef= ref.child("users/"+role+"/"+userId);
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    System.out.println("database works");
//                    // Retrieve data from the DataSnapshot
//                    User user=User.makeUser(role, dataSnapshot);
//                    main.onUserDataRetrieved(user);
//                }
//                else{
//                    main.onUserDatabaseFailure();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("uh oh");
//            }
//        });

//        if (user.getRole().equals("club")) {
//            String clubName = user.getUsername();
//            databaseHandler.loadUserDataFromBook(main, clubName, "signUp", user, email, password, activity, context);
//
//        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fUser = mAuth.getCurrentUser();
                        onSignUpAuthorised(fUser, user);
                        main.onRegistrationComplete(user);
                    } else {
                        main.onRegistrationAuthenticationFailure();
                    }
                });
    }



    private void onSignUpAuthorised(FirebaseUser fUser, User user){
        databaseHandler.addNewUserData(fUser.getUid(),user.getRole(),user);
    }

    /**
     * Signs in an existing user, and automatically called the database handler to load in the users data
     * @param main the class currently controlling the main thread, this method must be called from the login screen
     * @param email
     * @param password
     * @param role
     * @param activity the current activity being displayed
     * @param context no clue honestly, might be the main thread
     */
    public void signIn(MainActivity main, String email, String password, String role, final Activity activity, final Context context) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fUser = mAuth.getCurrentUser();
                        System.out.println(fUser.getUid());
                        onSignInAuthorised(main, fUser, role);
                    } else {
                        main.onLoginAuthorisationFailure();
                    }
                });
    }

    private void onSignInAuthorised(MainActivity main,FirebaseUser fUser, String role){
        //loads the users data from the database into a user instance
        databaseHandler.loadUserData(main,fUser.getUid(), role);
    }


    // Method to sign out
    public void signOut() {
        mAuth.signOut();
    }
}
