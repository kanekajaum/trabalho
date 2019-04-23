package com.example.pratica.listas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratica.Login;
import com.example.pratica.uteis.ItensDAO;
import com.example.pratica.uteis.MainActivity;
import com.example.pratica.Perfil;
import com.example.pratica.uteis.Produto;
import com.example.pratica.R;
import com.example.pratica.itens.ItemAdapter;
import com.example.pratica.uteis.ProdutoDAO;

import java.util.ArrayList;
import java.util.List;

public class ListaP extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private com.example.pratica.UsuarioDAO UsuarioDAO;
    private ProdutoDAO dao;
    private List<Produto> produtos;
    private GridView gv;
    private int res;

    private List<Produto> produtosFiltrados = new ArrayList<>();

    private ListView listaView;
    private TextView qtdItens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_p);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListaP.this, MainActivity.class);
                startActivity(it);
            }
        });
        String username =  getFromSharedPreferences("username");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gv = (GridView) findViewById(R.id.listaGrid);
        qtdItens = (TextView) findViewById(R.id.qtdItens);

        dao = new ProdutoDAO(this);

        ItensDAO daoItens = new ItensDAO(this);
//        String aqui = daoItens.contar("Casa", username);
//        Toast.makeText(this,aqui, Toast.LENGTH_SHORT).show();





//            produtos = dao.ObterTodos();
        produtos = dao.getAllItens(username);

        produtosFiltrados.addAll(produtos);
//        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtosFiltrados);
//
        ItemAdapter adapter = new ItemAdapter(this, produtosFiltrados);
        gv.setAdapter(adapter);
    //------------------------------------------------------------------------------

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//


                Intent it = new Intent(ListaP.this, Lista_de_itens.class);
                it.putExtra("item", produtosFiltrados.get(position).getNome());
                startActivity(it);


                String username =  getFromSharedPreferences("username");

                String nome =  produtosFiltrados.get(position).getNome();

                ItensDAO itn = new ItensDAO(ListaP.this);

                 res = itn.getProfilesCount("itens");


            }
        });



        registerForContextMenu(gv);

        TextView nome = (TextView) findViewById(R.id.navNome);

        if(username != "stive"){
            nome = (TextView) findViewById(R.id.navNome);
        }else{
            nome.setText("Você precisa se logar");

        }


        int countGrid = gv.getAdapter().getCount(); //contar itens da lista

//        Toast.makeText(this, "Registros = "+countGrid, Toast.LENGTH_SHORT).show();

        if(countGrid == 0){
            String[] itens = { "Bem Vindo "+username+"","Crie uma lista  no botão (+)." };

            gv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, itens));
        }


    }
    private String getFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getSharedPreferences("login" , Context.MODE_PRIVATE);
        return sharedPref.getString(key, "stive");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i  = getMenuInflater();
        i.inflate(R.menu.lista, menu);

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


    //===============================================================


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
        gv.invalidateViews();
    }


    //===============================================================


    public  void excluir(MenuItem item){
        final String username =  getFromSharedPreferences("username");
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
                        dao.excluir(produtoExcluir, username);
                        gv.invalidateViews();
                    }
                }).create();
        dialog.show();
    }


    //===============================================================


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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater i  = getMenuInflater();
//        i.inflate(R.menu.lista, menu);
//
//
//        return true;
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navPerfil) {
            String username = getFromSharedPreferences("username");



            Intent inte = new Intent(this, Perfil.class);
            inte.putExtra("usuario", username);
            startActivity(inte);
        } else if (id == R.id.nav_listaUsuarios) {
            Intent it = new Intent(this, Lista_cadatro.class);
            startActivity(it);
        } else if (id == R.id.nav_finalizados) {
            Intent its = new Intent(this, Lista_itens_finalizados.class);
            startActivity(its);
        } else if (id == R.id.nav_exit) {
            Intent it = new Intent(this, Login.class);
            startActivity(it);

            SharedPreferences settings = getSharedPreferences("username", Context.MODE_PRIVATE);
            settings.edit().clear().commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
