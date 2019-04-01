package com.example.pratica;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class item_da_lista extends BaseAdapter {

    private List<Itens> itens;
    private Activity activity;

    public item_da_lista(Activity activity, List<Itens> itens) {
        this.activity = activity;
        this.itens = itens;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = activity.getLayoutInflater().inflate(R.layout.activity_item_da_lista, parent, false);
        TextView nome = v.findViewById(R.id.id_nome_item);

        Itens p = itens.get(position);

        nome.setText(p.getNome_item());

        return v;
    }

}