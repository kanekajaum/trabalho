package com.example.pratica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    }
}
