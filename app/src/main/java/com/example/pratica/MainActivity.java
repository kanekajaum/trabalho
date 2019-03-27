package com.example.pratica;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nome;


    private ProdutoDAO dao;
    private Produto produto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.editNome);
        dao = new ProdutoDAO(this);



    }
    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getSharedPreferences("login" , Context.MODE_PRIVATE);
        return sharedPref.getString(key, "stive");

    }

    public void inserir(View view) {

        String usuario = getFromSharedPreferences("username");
        Produto p = new Produto();

        p.setNome(nome.getText().toString());
        p.setEmail_usuario_lista(usuario);

        if (nome.getText().toString() == " " || nome.getText().toString() == ""){
            Toast.makeText(this, "Por favor Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }else{
            dao.inserir(p);

            Toast.makeText(MainActivity.this, "usuario: "+usuario+ " || "+nome.getText().toString()+" inserido com sucesso!!!",Toast.LENGTH_LONG).show();

            Intent it = new Intent(MainActivity.this, Lista.class);
            startActivity(it);

            finish();
        }



    }

    public void Lista(View view) {
        Intent it = new Intent(this, Lista.class);
        startActivity(it);
    }

}
