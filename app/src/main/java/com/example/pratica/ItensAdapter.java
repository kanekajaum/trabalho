package com.example.pratica;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItensAdapter extends BaseAdapter {
    private List<Itens> itens;
    private Activity activity;

    public ItensAdapter(Activity activity, List<Itens> itens) {
        this.activity = activity;
        this.itens = itens;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itens.get(position).getId_itens();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.itens_adapter, parent, false);

        TextView nome = view.findViewById(R.id.txtItenAdapter);

        Itens i = itens.get(position);

        nome.setText(i.getNome_item());

        return view;
    }
}
