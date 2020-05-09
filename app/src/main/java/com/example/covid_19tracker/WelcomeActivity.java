package com.example.covid_19tracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public static String  PREFS_NAME="mypre";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        onStart();


    }

    @Override
    public void onBackPressed() {
        //exit app to home screen
            Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
            homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
            homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeScreenIntent);
        }
    public void onStart(){
        super.onStart();
        getUser();
    }
    public void getUser(){
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            Intent intent = new Intent(WelcomeActivity.this, DataActivity.class);
            startActivity(intent);
        }
    }

    public void Login(View v){
        Intent intent= new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void Register(View v){
        Intent intent= new Intent(WelcomeActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

}