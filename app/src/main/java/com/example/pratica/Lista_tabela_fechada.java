package com.example.pratica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Lista_tabela_fechada extends AppCompatActivity {

    private ListView listaView;
    private Lista_fechadaDAO dao;
    private List<Lista_fechada> produtosFechados;

    private List<Lista_fechada> produtosFiltrados = new ArrayList<Lista_fechada>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tabela_fechada);

        listaView = findViewById(R.id.lista_produtos);

        dao = new Lista_fechadaDAO(this);

        produtosFechados = dao.ObterTodosF();
        produtosFiltrados.addAll(produtosFechados);
            ArrayAdapter<Lista_fechada> adapter = new ArrayAdapter<Lista_fechada>(this, android.R.layout.simple_list_item_1, produtosFiltrados);

//        ItemAdapter adapter = new ItemAdapter(this, produtosFiltrados);
        listaView.setAdapter(adapter);
    }
}
