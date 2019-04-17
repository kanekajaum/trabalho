package com.example.pratica.uteis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.pratica.itens.Itens;

import java.util.ArrayList;
import java.util.List;

public class ItensDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public ItensDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }
    public long inserir(Itens itens){

        ContentValues values = new ContentValues();

        values.put("nome_item", itens.getNome_item());
        values.put("nome_tabela", itens.getNome_tabela());
        values.put("email_usuario", itens.getEmail_usuario());
        return banco.insert("itens",null, values);

    }

    public List<Itens> getAllItens(String tabela, String email) {
        List<Itens> itens = new ArrayList<Itens>();

        Cursor cursor = banco.rawQuery("SELECT * FROM itens WHERE nome_tabela LIKE '"+tabela+"' AND email_usuario LIKE'"+email+"'", null);

        while (cursor.moveToNext()){
            Itens it = new Itens();

            it.setId_itens(cursor.getInt(0));
            it.setNome_item(cursor.getString(1));
            it.setNome_tabela(cursor.getString(2));
            it.setEmail_usuario(cursor.getString(3));

            itens.add(it);

        }
        return itens;
    }
    public int getProfilesCount(String itens) {

        String countQuery = "SELECT  * FROM " + itens;
        SQLiteDatabase db = banco;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void excluir(Itens Excluir) {
        banco.delete("itens", "id_itens = ?", new String[]{Excluir.getId_itens().toString()});
    }
}
