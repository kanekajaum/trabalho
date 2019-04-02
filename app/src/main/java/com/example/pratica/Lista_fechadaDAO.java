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
    public long inserir(Lista_fechada itens){

        ContentValues values = new ContentValues();

        values.put("nome_item", itens.getNome_item());
        values.put("nome_tabela", itens.getNome_tabela());
        values.put("email_usuario", itens.getEmail_usuario());
        return banco.insert("lista_fechada",null, values);

    }

    public List<Lista_fechada> ObterTodosF(){
        List<Lista_fechada> Lista_fechada = new ArrayList<>();
        Cursor cursor = banco.query("lista_fechada", new String[]{"id_itens","nome_item", "nome_tabela", "email_usuario" }, null, null, null, null, null);

        while (cursor.moveToNext()){
            Lista_fechada p = new Lista_fechada();

            p.setId_itens(cursor.getInt(0));
            p.setNome_item(cursor.getString(1));
            p.setNome_tabela(cursor.getString(2));
            p.setEmail_usuario(cursor.getString(3));

            Lista_fechada.add(p);

        }
        return Lista_fechada;
    }

    public List<Lista_fechada> getAllItens(String tabela, String email) {
        List<Lista_fechada> lista = new ArrayList<Lista_fechada>();

        Cursor cursor = banco.rawQuery("SELECT * FROM lista_fechada WHERE nome_tabela LIKE '"+tabela+"' AND email_usuario LIKE '"+email+"'", null);

        while (cursor.moveToNext()){
            Lista_fechada it = new Lista_fechada();

            it.setId_itens(cursor.getInt(0));
            it.setNome_item(cursor.getString(1));
            it.setNome_tabela(cursor.getString(2));
            it.setEmail_usuario(cursor.getString(3));

            lista.add(it);

        }
        return lista;
    }

    public void excluir(Lista_fechada produtoExcluir) {
        banco.delete("lista_fechada", "id_itens = ?", new String[]{produtoExcluir.getId_itens().toString()});
    }
}
