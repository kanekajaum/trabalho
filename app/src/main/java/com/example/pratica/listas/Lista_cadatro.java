package com.example.pratica.listas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pratica.R;
import com.example.pratica.UsuarioDAO;
import com.example.pratica.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Lista_cadatro extends AppCompatActivity {

    private ListView lista;
    private UsuarioDAO dao;
    private List<Usuario> usuarios;
    private List<Usuario> usariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cadatro);

        lista = findViewById(R.id.lista_de_usuarios);

        dao = new UsuarioDAO(this);

        usuarios = dao.ObterUsuarios();
        usariosFiltrados.addAll(usuarios);
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, usariosFiltrados);
        lista.setAdapter(adapter);

        registerForContextMenu(lista);

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public  void procura(String nome){
        usariosFiltrados.clear();
        for (Usuario p : usuarios){
            if (p.getNome().toLowerCase().contains(nome.toLowerCase())){
                usariosFiltrados.add(p);
            }
        }
        lista.invalidateViews();


    }

    public  void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Usuario produtoExcluir = usariosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usariosFiltrados.remove(produtoExcluir);
                        usuarios.remove(produtoExcluir);
                        dao.excluir(produtoExcluir);
                        lista.invalidateViews();
                    }
                }).create();
        dialog.show();
    }
}
