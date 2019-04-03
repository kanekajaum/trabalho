package com.example.pratica.itens;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pratica.uteis.Produto;
import com.example.pratica.R;
import com.example.pratica.listas.Lista_de_itens;
import com.example.pratica.uteis.ItensDAO;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private List<Produto> produto;
    private Activity activity;
    private ItensDAO DAOitens;

    public ItemAdapter(Activity activity, List<Produto> produto){
        this.activity = activity;
        this.produto = produto;
        this.DAOitens = DAOitens;


    }

    public ItemAdapter(Lista_de_itens activity, List<Itens> strings) {
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
        return produto.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.activity_item, parent, false);
        TextView nome = view.findViewById(R.id.textNome_item);
        TextView valor = view.findViewById(R.id.textNome_item);



        Produto p = produto.get(position);

        nome.setText(p.getNome());

        return view;
    }
}
