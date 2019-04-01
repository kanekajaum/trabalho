package com.example.pratica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    TextView perfilNome;
    TextView perfilEmail;
    private UsuarioDAO UsuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        UsuarioDAO UsuarioDAO = new UsuarioDAO(this);

        Intent it = getIntent();


        String item = it.getStringExtra("usuario");
        String nome = UsuarioDAO.usuarioNome(item);

        final TextView textViewToChang = (TextView) findViewById(R.id.textPerfilNome);
        textViewToChang.setText("Nome: "+nome);

        final TextView textViewToChange = (TextView) findViewById(R.id.textPerfilEmail);
        textViewToChange.setText("Email: "+item);
    }

    public void deslogar(View view){
        Intent it = new Intent(this, Login.class);
        startActivity(it);

        SharedPreferences settings = getSharedPreferences("username", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

}
