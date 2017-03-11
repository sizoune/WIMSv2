package com.example.pattimura.wims;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private static boolean splashLoaded = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (mAuth.getCurrentUser() != null) {
            Intent i = new Intent(SplashScreen.this, HalamanAwal.class);
            startActivity(i);
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreen.this, HalamanAwal.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }
}
