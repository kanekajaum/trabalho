package com.example.pratica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lista_de_itens extends AppCompatActivity {

    private ListView listaViewItens;
    private ItensDAO daoitenslista;
    private List<Itens> itens;
    TextView text;
    private List<Itens> itensFiltrados = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_itens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pega a intent de outra activity
                Intent it = getIntent();

                //Recuperei a string da outra activity
                String item = it.getStringExtra("item");

                Intent item_passado = new Intent(Lista_de_itens.this, Add_item_lista.class);
                item_passado.putExtra("item", item);
                startActivity(item_passado);
            }
        });
        String usuario = getFromSharedPreferences("username");

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        String item = it.getStringExtra("item");

//        Toast.makeText(this, item, Toast.LENGTH_SHORT ).show();

        setTitle("Lista "+item);

        final TextView textViewToChange = (TextView) findViewById(R.id.textDinamico);
        textViewToChange.setText(item);

        listaViewItens = findViewById(R.id.list_itens);

        daoitenslista = new ItensDAO(this);

 //       itens = daoitenslista.ObterTodosItensDaLista("usuario","Item");
        itens = daoitenslista.getAllItens(item ,usuario);


        itensFiltrados.addAll(itens);
        ArrayAdapter<Itens> adapter = new ArrayAdapter<Itens>(this, android.R.layout.simple_list_item_1, itensFiltrados);
        listaViewItens.setAdapter(adapter);

//        Toast.makeText(this, "usuario: "+usuario+" lista: "+item, Toast.LENGTH_LONG).show();
//
    }
    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getSharedPreferences("login" , Context.MODE_PRIVATE);
        return sharedPref.getString(key, "stive");

    }

    public void add_item(View view) {
        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        String item = it.getStringExtra("item");

        Intent item_passado = new Intent(this, Add_item_lista.class);
        item_passado.putExtra("item", item);
        startActivity(item_passado);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        String usuario = getFromSharedPreferences("username");
//
//        //Pega a intent de outra activity
//        Intent it = getIntent();
//
//        //Recuperei a string da outra activity
//        String item = it.getStringExtra("item");
//
//
//        listaViewItens = findViewById(R.id.list_itens);
//
//        daoitenslista = new ItensDAO(this);
//
//        //       itens = daoitenslista.ObterTodosItensDaLista("usuario","Item");
//        itens = daoitenslista.getAllItens(item ,usuario);
//
//
//        itensFiltrados.addAll(itens);
//        ArrayAdapter<Itens> adapter = new ArrayAdapter<Itens>(this, android.R.layout.simple_list_item_1, itensFiltrados);
//        listaViewItens.setAdapter(adapter);
//    }
}
