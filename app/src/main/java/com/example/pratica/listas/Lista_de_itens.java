package com.example.pratica.listas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratica.itens.ItemAdapterRiscadp;
import com.example.pratica.itens.Itens;
import com.example.pratica.itens.ItensAdapter;
import com.example.pratica.R;
import com.example.pratica.usuarios.Add_item_lista;
import com.example.pratica.uteis.ItensDAO;
import com.example.pratica.uteis.Lista_fechadaDAO;

import java.util.ArrayList;
import java.util.List;

public class Lista_de_itens extends AppCompatActivity {

    private ListView listaViewItensF;
    private Lista_fechadaDAO daoitenslistaFechadaf;
    private List<Lista_fechada> itemf;
    private List<Lista_fechada> itensFiltradosF = new ArrayList<Lista_fechada>();

    private ListView listaViewItens;
    private ItensDAO daoitenslista;
    private Lista_fechadaDAO daoitenslistaFechada;
    private List<Itens> itens;
    private TextView text;
    private List<Itens> itensFiltrados = new ArrayList<>();


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

        setTitle(item);

        text = findViewById(R.id.editTextTitle_pag);
        text.setText(item);

        text = findViewById(R.id.editTextFinal);
        text.setText(item+"s finalizado");


        listaViewItens = findViewById(R.id.list_itens);

        daoitenslista = new ItensDAO(this);
        daoitenslistaFechada = new Lista_fechadaDAO(this);

 //       itens = daoitenslista.ObterTodosItensDaLista("usuario","Item");
        itens = daoitenslista.getAllItens(item ,usuario);


        itensFiltrados.addAll(itens);

//       ArrayAdapter<Itens> adapter = new ArrayAdapter<Itens>(this, android.R.layout.simple_list_item_1, itensFiltrados);
//
        ItensAdapter adapter = new ItensAdapter(this, itensFiltrados);
        listaViewItens.setAdapter(adapter);

        //-------------------------------------------------------------

        listaViewItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             String baixar =  itensFiltrados.get(position).getNome_item();

             //===========================================
             Intent it = getIntent();
             String item = it.getStringExtra("item");
             String usuario = getFromSharedPreferences("username");
             //===========================================


                final Itens produtoExcluir = itensFiltrados.get(position);
                final Itens ItemP = itensFiltrados.get(position);

                Lista_fechada l = new Lista_fechada();
                l.setNome_item(ItemP.toString());
                l.setNome_tabela(item);
                l.setEmail_usuario(usuario);

                daoitenslistaFechada.inserir(l);

                AlertDialog dialog = new AlertDialog.Builder(Lista_de_itens.this)
                        .setTitle("Atenção")
                        .setMessage("Realmente deseja finalizar ''"+produtoExcluir+"''? ")
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

                Intent intent = new Intent(Lista_de_itens.this, Lista_de_itens.class);
                startActivity(intent);

            }
        });

        registerForContextMenu(listaViewItens);


        //-------------------------------------------------------------

        listaViewItensF = findViewById(R.id.listaF);

        daoitenslistaFechadaf = new Lista_fechadaDAO(this);


        itemf = daoitenslistaFechadaf.getAllItens(item, usuario);


        itensFiltradosF.addAll(itemf);

        ItemAdapterRiscadp adp = new ItemAdapterRiscadp(this, itensFiltradosF);
        listaViewItensF.setAdapter(adp);
        //-------------------------------------------------------------
        int countGrid = listaViewItens.getAdapter().getCount(); //contar itens da lista

//        Toast.makeText(this, "Registros = "+countGrid, Toast.LENGTH_SHORT).show();

        if(countGrid == 0){
            String[] itens = { "Adicione um item a sua tabela com o Botão(+)." };

            listaViewItens.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, itens));
        }
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

            Intent it = getIntent();
            String s = it.getStringExtra("item");
            String usuario = getFromSharedPreferences("username");

            Lista_fechada l = new Lista_fechada();
            l.setNome_item(ItemP.toString());
            l.setNome_tabela(s);
            l.setEmail_usuario(usuario);

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

    @Override
    protected void onResume() {
        super.onResume();

        String usuario = getFromSharedPreferences("username");
        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        String item = it.getStringExtra("item");




        itens = daoitenslista.getAllItens(item ,usuario);
        itensFiltrados.clear();
        itensFiltrados.addAll(itens);
        listaViewItens.invalidateViews();




        itemf = daoitenslistaFechadaf.getAllItens(item ,usuario);
        itensFiltradosF.clear();
        itensFiltradosF.addAll(itemf);
        listaViewItensF.invalidateViews();

    }
}