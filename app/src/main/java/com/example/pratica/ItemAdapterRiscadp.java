package com.example.pratica;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapterRiscadp extends BaseAdapter {

    private List<Lista_fechada> produto;
    private Activity activity;

    public ItemAdapterRiscadp(Activity activity, List<Lista_fechada> produto){
        this.activity = activity;
        this.produto = produto;
    }
    @Override
    public int getCount() {
        return produto.size();
    }

    @Override
    public Object getItem(int position) {
        return produto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produto.get(position).getId_itens();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.activity_item_riscado, parent, false);
        TextView nome = view.findViewById(R.id.id_nome_item_riscado);

        Lista_fechada p = produto.get(position);

        nome.setText(p.getNome_item());
        return view;
    }
}
