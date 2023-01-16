package com.example.smsuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
   FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        findViewById(R.id.splashscreen_kayitbuton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashScreen.this, KayitEkrani.class));
            }
        });

        findViewById(R.id.splashscreen_girisbuton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashScreen.this, GirisEkrani.class));
            }
        });

       mauth = FirebaseAuth.getInstance();

        if(mauth.getCurrentUser() != null){
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
        }
    }
}