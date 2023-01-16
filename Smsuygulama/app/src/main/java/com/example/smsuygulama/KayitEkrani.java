package com.example.smsuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class KayitEkrani extends AppCompatActivity {
    EditText emailedittext, sifreedittext;
    Button girisyap;
    Button kayitol;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        emailedittext = findViewById(R.id.kayit_emaileditText);
        sifreedittext = findViewById(R.id.kayit_sifreeditText);

        girisyap = findViewById(R.id.kayit_girisbuton);
        kayitol = findViewById(R.id.kayit_kayitbuton);

        mAuth = FirebaseAuth.getInstance();
        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KayitEkrani.this, GirisEkrani.class));
                finish();
            }
        });
        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailedittext.getText().toString();
                String password = sifreedittext.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    startActivity(new Intent(KayitEkrani.this, GirisEkrani.class));
                    finish();
                });
            }
        });
    }
}