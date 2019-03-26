package com.example.pratica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {
    private static final String name = "tb_lista1.db";
    private static final int version = 2;
    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String lista = "create table lista("+
                "id integer primary key autoincrement," +
                "email_usuario_lista varchar(50)," +
                "nome varchar(50))";

        String itens = "create table itens("+
                "id_itens integer primary key autoincrement," +
                "nome_item varchar(50)," +
                "nome_tabela varchar(50)," +
                "email_usuario varchar(50))";

        String usuarios = "create table usuarios(" +
                "id integer primary key autoincrement," +
                "nome varchar(50)," +
                "email varchar(50)," +
                "senha varchar(12))";

        String lista_fechada = "create table lista_fechada("+
                "id integer primary key autoincrement," +
                "nome varchar(50))";




        db.execSQL(lista_fechada);
        db.execSQL(itens);
        db.execSQL(lista);
        db.execSQL(usuarios);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
