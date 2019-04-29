package com.example.pratica;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {

    TextView perfilNome;
    TextView perfilEmail;
    ImageView imageView;
    private UsuarioDAO UsuarioDAO;
    private String nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        UsuarioDAO UsuarioDAO = new UsuarioDAO(this);

        Intent it = getIntent();


        String item = it.getStringExtra("usuario");
        nome = UsuarioDAO.usuarioNome(item);
        setTitle(nome);

        imageView = findViewById(R.id.img_perfil);



        final TextView textViewToChang = (TextView) findViewById(R.id.textPerfilNome);
        textViewToChang.setText(nome);

        final TextView textViewToChange = (TextView) findViewById(R.id.textPerfilEmail);
        textViewToChange.setText(item);


//        ======================================================================

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA},0);
        }


    }

    public void deslogar(View view){
        Intent it = new Intent(this, Login.class);
        startActivity(it);
        Toast.makeText(this, "Até mais "+nome, Toast.LENGTH_SHORT).show();
        SharedPreferences settings = getSharedPreferences("username", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }


    public void saldacao(View view) {
        Toast.makeText(this, "Olá "+nome, Toast.LENGTH_SHORT).show();
    }

    public void trocar_foto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
