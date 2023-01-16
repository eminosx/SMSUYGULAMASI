package com.example.smsuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class GirisEkrani extends AppCompatActivity {

    EditText emaileditText, sifreeditText;
    Button girisyap;
    Button kayitol;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_ekrani);

        emaileditText = findViewById(R.id.giris_emaileditText);
        sifreeditText = findViewById(R.id.giris_sifreeditText);
        girisyap = findViewById(R.id.giris_girisbuton);
        kayitol = findViewById(R.id.giris_kayitbuton);

        mAuth = FirebaseAuth.getInstance();

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GirisEkrani.this, KayitEkrani.class));
            }
        });

        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emaileditText.getText().toString();
                String password = sifreeditText.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password ).addOnCompleteListener(task -> {
                    startActivity(new Intent(GirisEkrani.this, MainActivity.class));
                  finish();
                });
            }
        });
    }
}