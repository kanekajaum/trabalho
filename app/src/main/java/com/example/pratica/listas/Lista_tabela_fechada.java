package com.example.pratica.listas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.pratica.R;
import com.example.pratica.uteis.Lista_fechada;
import com.example.pratica.uteis.Lista_fechadaDAO;

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

        GridView gv = (GridView) findViewById(R.id.listaGrid);

        dao = new Lista_fechadaDAO(this);

        produtosFechados = dao.ObterTodosF();
        produtosFiltrados.addAll(produtosFechados);
            ArrayAdapter<Lista_fechada> adapter = new ArrayAdapter<Lista_fechada>(this, android.R.layout.simple_list_item_1, produtosFiltrados);

//        ItemAdapter adapter = new ItemAdapter(this, produtosFiltrados);
        gv.setAdapter(adapter);
    }
}
