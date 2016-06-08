package com.example.lawliet.taskbench;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
        boolean ifLogin = sharedPreferences.getBoolean("ifLogin", false);
        int userid = sharedPreferences.getInt("userid", -1);
        if(ifLogin && (userid != -1)){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);
    }
}
