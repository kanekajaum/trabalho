package com.example.pratica.imagens;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import com.example.pratica.uteis.Conexao;

import java.io.ByteArrayOutputStream;


public class ImagemDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public ImagemDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public void guardarImagen( String email, Bitmap bitmap){
        // tamaño del baos depende del tamaño de tus imagenes en promedio
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        String sql = "INSERT INTO imagenes (email, img) VALUES(?,?)";
        SQLiteStatement insert = banco.compileStatement(sql);
        insert.clearBindings();
        insert.bindString(1, email);
        insert.bindBlob(2, blob);
        insert.executeInsert();
        banco.close();
    }


}