package com.example.pratica.listas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.pratica.itens.ItemAdapterRiscadp;
import com.example.pratica.R;
import com.example.pratica.uteis.Lista_fechadaDAO;

import java.util.ArrayList;
import java.util.List;

public class Lista_itens_finalizados extends AppCompatActivity {

    private ListView listaViewItens;
    private Lista_fechadaDAO daoitenslistaFechada;
    private List<Lista_fechada> item;
    private List<Lista_fechada> itensFiltrados = new ArrayList<Lista_fechada>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens_finalizados);



        listaViewItens = findViewById(R.id.listaFechada);

        daoitenslistaFechada = new Lista_fechadaDAO(this);


        item = daoitenslistaFechada.ObterTodosF();


        itensFiltrados.addAll(item);

//        ArrayAdapter<Lista_fechada> adapter = new ArrayAdapter<Lista_fechada>(this, android.R.layout.simple_list_item_1, itensFiltrados);
//        listaViewItens.setAdapter(adapter);

        ItemAdapterRiscadp adapter1 = new ItemAdapterRiscadp(this, itensFiltrados);
        listaViewItens.setAdapter(adapter1);

    }
}
