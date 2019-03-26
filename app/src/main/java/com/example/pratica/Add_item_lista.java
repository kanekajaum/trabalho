package com.example.pratica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add_item_lista extends AppCompatActivity {

    EditText textNome_item;
    ItensDAO daoItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_lista);

        textNome_item = findViewById(R.id.editNome_item_adicionar);
        daoItens = new ItensDAO(this);
    }

    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getSharedPreferences("login" , Context.MODE_PRIVATE);
        return sharedPref.getString(key, "stive");

    }

    public void ad_item_add(View view) {
        //Pega a intent de outra activity
        Intent item_passado = getIntent();

        //Recuperei a string da outra activity
        String item = item_passado.getStringExtra("item");

        String usuario = getFromSharedPreferences("username");

//        Toast.makeText(this, "Usuario: "+usuario+" || Lista: "+item+" || add: "+textNome_item.getText().toString(), Toast.LENGTH_LONG).show();
//

        Itens itens = new Itens();

        itens.setNome_item(textNome_item.getText().toString());
        itens.setNome_tabela(item);
        itens.setEmail_usuario(usuario);

        long result = daoItens.inserir(itens);

        Toast.makeText(this,textNome_item.getText().toString()+" inserido na tabela"+ item+".", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(this, Lista_de_itens.class);
        it.putExtra("item", item);
        startActivity(it);
        finish();

    }
}
