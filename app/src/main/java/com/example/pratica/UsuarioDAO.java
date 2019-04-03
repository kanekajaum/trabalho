package com.example.pratica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pratica.usuarios.Usuario;
import com.example.pratica.uteis.Conexao;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;



    public UsuarioDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }
    public long inserir(Usuario usuario){

        ContentValues values = new ContentValues();

        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        return banco.insert("usuarios",null, values);

    }



    public List<Usuario> ObterUsuarios(){
        List<Usuario> usuario = new ArrayList<>();
//        Cursor cursor = banco.query("usuarios", new String[]{"id","nome", "email", "senha"}, null, null, null, null, null);

        Cursor cursor = banco.query("usuarios", new String[]{"id","nome", "email", "senha"}, null, null, null, null, null);


        while (cursor.moveToNext()){
            Usuario u = new Usuario();

            u.setId(cursor.getInt(0));
            u.setNome(cursor.getString(1));
            u.setEmail(cursor.getString(2));
            u.setSenha(cursor.getString(3));

            usuario.add(u);

        }
        return usuario;
    }
    String metodo(String parametro,String senha){


        Cursor cursor = banco.rawQuery("SELECT * FROM usuarios WHERE email LIKE '"+ parametro+"' AND senha LIKE'"+senha+"'", null);

        cursor.moveToFirst();

        String nomeString = cursor.getString(cursor.getColumnIndex("email"));

        StringBuilder conversor = new StringBuilder();
        conversor.append(nomeString);


        return conversor.toString();

    }

    String usuarioNome(String parametro){


        Cursor cursor = banco.rawQuery("SELECT * FROM usuarios WHERE email LIKE '"+ parametro+"'", null);

        cursor.moveToFirst();

        String nomeString = cursor.getString(cursor.getColumnIndex("nome"));

        StringBuilder conversor = new StringBuilder();
        conversor.append(nomeString);


        return conversor.toString();

    }


    public void excluir(Usuario produtoExcluir) {
        banco.delete("usuarios", "id = ?", new String[]{produtoExcluir.getId().toString()});

    }
}
