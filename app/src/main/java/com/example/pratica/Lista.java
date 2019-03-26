package com.example.pratica;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity {

    private ListView listaView;
    private ProdutoDAO dao;
    private List<Produto> produtos;

    private List<Produto> produtosFiltrados = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent it = new Intent(Lista.this, MainActivity.class);
                startActivity(it);
            }
        });

//===============================================================
        String username = getFromSharedPreferences("username");

            listaView = findViewById(R.id.lista_produtos);

            dao = new ProdutoDAO(this);

//            produtos = dao.ObterTodos();
            produtos = dao.getAllItens(username);

            produtosFiltrados.addAll(produtos);
//            ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtosFiltrados);
////
            ItemAdapter adapter = new ItemAdapter(this, produtosFiltrados);
            listaView.setAdapter(adapter);





        if (username == "stive" || username == "" ){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }


//===============================================================

        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getBaseContext(),"Você Clicou em: " +produtosFiltrados.get(position).getNome(), Toast.LENGTH_SHORT).show();


                Intent it = new Intent(Lista.this, Lista_de_itens.class);
                it.putExtra("item", produtosFiltrados.get(position).getNome());
                startActivity(it);

            }
        });

        registerForContextMenu(listaView);

    }

    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getSharedPreferences("login" ,Context.MODE_PRIVATE);
        return sharedPref.getString(key, "stive");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i  = getMenuInflater();
        i.inflate(R.menu.menu_cadastro, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procura(newText);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public  void procura(String nome){
        produtosFiltrados.clear();
        for (Produto p : produtos){
            if (p.getNome().toLowerCase().contains(nome.toLowerCase())){
                produtosFiltrados.add(p);
            }
        }
        listaView.invalidateViews();
    }

    public  void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Produto produtoExcluir = produtosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produtosFiltrados.remove(produtoExcluir);
                        produtos.remove(produtoExcluir);
                        dao.excluir(produtoExcluir);
                        listaView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void baixa(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        try {
            Toast.makeText(this, "funcionalidade ainda não disponivel", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        String username = getFromSharedPreferences("username");

        listaView = findViewById(R.id.lista_produtos);

        dao = new ProdutoDAO(this);
//
//            produtos = dao.ObterTodos();
        produtos = dao.getAllItens(username);

        produtosFiltrados.clear();
        produtosFiltrados.addAll(produtos);
        listaView.invalidateViews();




    }

    public void lista(MenuItem item) {



        Intent it = new Intent(Lista.this, Login.class);
        startActivity(it);

        SharedPreferences settings = getSharedPreferences("username", Context.MODE_PRIVATE);
        settings.edit().clear().commit();


    }

    public void cadastrar(MenuItem item) {
        Intent it = new Intent(Lista.this, Cadastro.class);
        startActivity(it);
    }

    public void logar(MenuItem item) {
        Intent it = new Intent(Lista.this, Login.class);
        startActivity(it);
    }

    public void lista_cadastrados(MenuItem item) {
        Intent it = new Intent(Lista.this, Lista_cadatro.class);
        startActivity(it);
    }

    public void pref(MenuItem item) {
        try {



        }catch (Exception e){
            Toast.makeText(this, "Erro:"+e, Toast.LENGTH_SHORT).show();
        }

    }

    public void add(MenuItem item) {
        Intent it = new Intent(Lista.this, MainActivity.class);
        startActivity(it);
    }

    public void usuario(MenuItem item) {
        String username = getFromSharedPreferences("username");

        Toast.makeText(this, "usuario: "+username, Toast.LENGTH_SHORT).show();
    }

    public void lista_finalizados(MenuItem item) {
        Intent it = new Intent(Lista.this, Lista_cadatro.class);
        startActivity(it);
    }
}
