package com.example.pratica;

import java.io.Serializable;

public class Produto implements Serializable {
    private Integer id;
    private String nome;
    private String email_usuario_lista;


    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }


    public String getEmail_usuario_lista() {
        return email_usuario_lista;
    }

    public void setEmail_usuario_lista(String email_usuario_lista) {
        this.email_usuario_lista = email_usuario_lista;
    }


    public  String getNome() {return nome;}

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String toString(){
        return nome+" || "+email_usuario_lista ;

    }
}
