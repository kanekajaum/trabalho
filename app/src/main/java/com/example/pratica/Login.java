package com.example.pratica;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Login extends AppCompatActivity {


    private static Usuario User = null;
    private EditText email;
    private EditText senha;
    private UsuarioDAO dao;
    private SharedPreferences logado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.editEmail_login);
        senha = findViewById(R.id.editSenha_login);

        String txt_email = email.getText().toString();
        String txt_senha = senha.getText().toString();


        carregarPreferencias(txt_email);


    }


    public void Login(View view) {

        try {
            dao = new UsuarioDAO(this);

            final String txt_email = email.getText().toString();
            final String txt_senha = senha.getText().toString();

            String texto = dao.metodo(txt_email, txt_senha);

            carregarPreferencias(txt_email);

            Intent intent = new Intent(this, Lista.class);
            startActivity(intent);



            }catch (Exception e){
            Toast.makeText(this, "Erro: login esta incorreto", Toast.LENGTH_SHORT).show();
        }


    }

    private void carregarPreferencias(String string) {

        String username = email.getText().toString();


        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.apply();


    }





    public void cadas(View view) {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }


}
