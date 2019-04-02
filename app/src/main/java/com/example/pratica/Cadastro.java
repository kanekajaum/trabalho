package com.example.pratica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;


    private UsuarioDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);


        dao = new UsuarioDAO(this);




    }

    public void cadastro(View view) {


            Usuario u = new Usuario();

            u.setNome(nome.getText().toString());
            u.setEmail(email.getText().toString());
            u.setSenha(senha.getText().toString());

                 dao.inserir(u);

                 Toast.makeText(this, "Usuario " + nome.getText().toString() + " cadastrado.", Toast.LENGTH_SHORT).show();

                 Intent intent = new Intent(this, Login.class);
                 startActivity(intent);



    }
}
