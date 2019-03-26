package com.example.pratica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    TextView perfilNome;
    TextView perfilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        Intent it = getIntent();


        String item = it.getStringExtra("usuario");

        final TextView textViewToChange = (TextView) findViewById(R.id.textPerfilEmail);
        textViewToChange.setText(item);
    }
}
