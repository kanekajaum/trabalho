package com.example.pratica;

import java.io.Serializable;

public class Lista_fechada implements Serializable {
    private Integer id_itens;
    private String nome_item;
    private String nome_tabela;
    private String email_usuario;

    public Integer getId_itens() {
        return id_itens;
    }

    public void setId_itens(Integer id_itens) {
        this.id_itens = id_itens;
    }

    public String getNome_item() {
        return nome_item;
    }

    public void setNome_item(String nome_item) {
        this.nome_item = nome_item;
    }

    public String getNome_tabela() {
        return nome_tabela;
    }

    public void setNome_tabela(String nome_tabela) {
        this.nome_tabela = nome_tabela;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

}
