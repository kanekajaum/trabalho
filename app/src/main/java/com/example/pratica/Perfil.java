package com.example.pratica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {

    TextView perfilNome;
    TextView perfilEmail;
    ImageView imageView;
    private UsuarioDAO UsuarioDAO;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        UsuarioDAO UsuarioDAO = new UsuarioDAO(this);

        Intent it = getIntent();


        String item = it.getStringExtra("usuario");
        nome = UsuarioDAO.usuarioNome(item);
        setTitle(nome);

        imageView = findViewById(R.id.img_perfil);



        final TextView textViewToChang = (TextView) findViewById(R.id.textPerfilNome);
        textViewToChang.setText("Nome: "+nome);

        final TextView textViewToChange = (TextView) findViewById(R.id.textPerfilEmail);
        textViewToChange.setText("Email:"+item);
    }

    public void deslogar(View view){
        Intent it = new Intent(this, Login.class);
        startActivity(it);
        Toast.makeText(this, "Até mais "+nome, Toast.LENGTH_SHORT).show();
        SharedPreferences settings = getSharedPreferences("username", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }


    public void saldacao(View view) {
        Toast.makeText(this, "Olá "+nome, Toast.LENGTH_SHORT).show();
    }
}
