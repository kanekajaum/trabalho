package com.example.pratica.listas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.pratica.uteis.Produto;
import com.example.pratica.R;
import com.example.pratica.itens.ItemAdapter;
import com.example.pratica.uteis.ProdutoDAO;

import java.util.ArrayList;
import java.util.List;

public class listaGrid extends AppCompatActivity {

    private ProdutoDAO dao;
    private List<Produto> produtos;

    private List<Produto> produtosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_grid);

        GridView gv = (GridView) findViewById(R.id.listaGrid);
        String username =  getFromSharedPreferences("username");

        dao = new ProdutoDAO(this);

//            produtos = dao.ObterTodos();
        produtos = dao.getAllItens(username);

        produtosFiltrados.addAll(produtos);
//        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtosFiltrados);
//
        ItemAdapter adapter = new ItemAdapter(this, produtosFiltrados);
        gv.setAdapter(adapter);
    }
    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getSharedPreferences("login" , Context.MODE_PRIVATE);
        return sharedPref.getString(key, "stive");

    }
}
