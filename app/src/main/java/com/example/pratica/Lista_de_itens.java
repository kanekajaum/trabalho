package com.example.pratica;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lista_de_itens extends AppCompatActivity {

    private ListView listaViewItens;
    private ItensDAO daoitenslista;
    private Lista_fechadaDAO daoitenslistaFechada;
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
        daoitenslistaFechada = new Lista_fechadaDAO(this);

 //       itens = daoitenslista.ObterTodosItensDaLista("usuario","Item");
        itens = daoitenslista.getAllItens(item ,usuario);


        itensFiltrados.addAll(itens);
        ArrayAdapter<Itens> adapter = new ArrayAdapter<Itens>(this, android.R.layout.simple_list_item_1, itensFiltrados);
        listaViewItens.setAdapter(adapter);

//        Toast.makeText(this, "usuario: "+usuario+" lista: "+item, Toast.LENGTH_LONG).show();
//

        //-------------------------------------------------------------

        listaViewItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),"Você Clicou em: " +itensFiltrados
                        .get(position).getNome_item(), Toast.LENGTH_SHORT).show();


//                Intent it = new Intent(Lista_de_itens.this, Lista_de_itens.class);
//                it.putExtra("item", itensFiltrados.get(position).getNome_tabela());
//                startActivity(it);

            }
        });

        registerForContextMenu(listaViewItens);


    }

    //-----------------------------------------------------------------

    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getSharedPreferences("login" , Context.MODE_PRIVATE);
        return sharedPref.getString(key, "stive");

    }

    //-----------------------------------------------------------------

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto_itens, menu);
    }

    //-----------------------------------------------------------------

    public  void procura(String nome){
        itensFiltrados.clear();
        for (Itens p : itens){
            if (p.getNome_item().toLowerCase().contains(nome.toLowerCase())){
                itensFiltrados.add(p);
            }
        }
        listaViewItens.invalidateViews();
    }

    //-----------------------------------------------------------------

    public  void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Itens produtoExcluir = itensFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir "+menuInfo.position+"?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itensFiltrados.remove(produtoExcluir);
                        itens.remove(produtoExcluir);
                        daoitenslista.excluir(produtoExcluir);
                        listaViewItens.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

//-----------------------------------------------------------------

    public void baixa(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        try {

//

            final Itens produtoExcluir = itensFiltrados.get(menuInfo.position);
            final Itens ItemP = itensFiltrados.get(menuInfo.position);

                Lista_fechada l = new Lista_fechada();
                l.setNome(ItemP.toString());

            daoitenslistaFechada.inserir(l);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Realmente finalizar  ´´"+produtoExcluir+"``? ")
                    .setNegativeButton("Não", null)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            itensFiltrados.remove(produtoExcluir);
                            itens.remove(produtoExcluir);
                            daoitenslista.excluir(produtoExcluir);
                            listaViewItens.invalidateViews();
                        }
                    }).create();
            dialog.show();


            Toast.makeText(this, produtoExcluir+" inseridoo la lista de finalizados.", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


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
