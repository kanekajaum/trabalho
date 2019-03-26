package com.example.pratica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Lista_fechadaDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public Lista_fechadaDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }
    public long inserir(Lista_fechada lista){

        ContentValues values = new ContentValues();

        values.put("nome", lista.getNome());
        return banco.insert("lista_fechada",null, values);

    }
    public List<Lista_fechada> ObterTodosF(){
        List<Lista_fechada> Lista_fechada = new ArrayList<>();
        Cursor cursor = banco.query("lista_fechada", new String[]{"id","nome"}, null, null, null, null, null);

        while (cursor.moveToNext()){
            Lista_fechada p = new Lista_fechada();

            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));

            Lista_fechada.add(p);

        }
        return Lista_fechada;
    }
}
