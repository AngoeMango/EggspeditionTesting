package com.uottawa.SEG2105BC.gcc_app_grp10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.View;
import android.view.WindowManager;
import android.content.Intent;
import android.view.animation.AnimationUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity{

    private static int SPLASH_SCREEN = 4000;

    //Variables
    Animation topAnimation, bottomAnimation;
    ImageView image, logo;
    TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animations
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.eggLogo);
        logo = findViewById(R.id.eggspeditionLogo);
        slogan = findViewById(R.id.sloganLogo);

        image.setAnimation(topAnimation);
        logo.setAnimation(topAnimation);
        slogan.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }

}