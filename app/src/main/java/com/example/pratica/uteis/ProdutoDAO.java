package com.example.pratica.uteis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public ProdutoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }
    public long inserir(Produto produto){

        ContentValues values = new ContentValues();


        values.put("nome", produto.getNome());
        values.put("email_usuario_lista", produto.getEmail_usuario_lista());

        return banco.insert("lista",null, values);

    }
    public List<Produto> ObterTodos(){
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = banco.query("lista", new String[]{"id","nome","email_usuario_lista"}, null, null, null, null, null);

        while (cursor.moveToNext()){
            Produto p = new Produto();

            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setEmail_usuario_lista(cursor.getString(2));

            produtos.add(p);

        }
        return produtos;
    }
    public List<Produto> getAllItens(String email) {
        List<Produto> produtos = new ArrayList<>();

        Cursor cursor = banco.rawQuery("SELECT * FROM lista WHERE email_usuario_lista LIKE'" + email + "'", null);

        while (cursor.moveToNext()) {
            Produto p = new Produto();

            p.setId(cursor.getInt(0));
            p.setEmail_usuario_lista(cursor.getString(1));
            p.setNome(cursor.getString(2));

            produtos.add(p);

        }
        return produtos;

    }


    public void excluir(Produto p, String usuario) {
        banco.delete("lista", "id = ?", new String[]{p.getId().toString()});
        banco.delete("itens", "email_usuario = ?", new String[]{usuario});

    }
}
