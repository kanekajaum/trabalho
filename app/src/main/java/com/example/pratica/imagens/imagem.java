package com.example.pratica.imagens;

import android.graphics.Bitmap;

public class imagem {
    private Integer id_img;
    private String email_user_img;
    private byte[] img;

    public Integer getId_img() {
        return id_img;
    }

    public void setId_img(Integer id_img) {
        this.id_img = id_img;
    }

    public String getEmail_user_img() {
        return email_user_img;
    }

    public void setEmail_user_img(String email_user_img) {
        this.email_user_img = email_user_img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
