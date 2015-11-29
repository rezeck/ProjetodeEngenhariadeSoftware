package com.example.mateuspalhares.projetodeengenhariadesoftware;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mateuspalhares on 25/11/15.
 */
public class RankAdapter extends ArrayAdapter<Rank> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Rank> list = new ArrayList<Rank>();

    public RankAdapter(Context context,int layoutResourceId, ArrayList<Rank> list){
        super(context,layoutResourceId,list);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RankHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RankHolder();
            holder.posicao = (TextView)row.findViewById(R.id.posicao);
            holder.nome = (TextView)row.findViewById(R.id.rank_nome);
            holder.rank = (TextView)row.findViewById(R.id.rank);
            holder.imgIcon = (ImageView)row.findViewById(R.id.coroa);
            row.setTag(holder);
        }
        else{
            holder = (RankHolder)row.getTag();
        }

        Rank rank = list.get(position);
        holder.posicao.setText(""+position);
        holder.nome.setText(rank.getNome());
        holder.rank.setText(""+rank.getRank());
        if(position == 0)
            holder.imgIcon.setImageResource(R.drawable.winner);

        return row;
    }

    static class RankHolder{
        TextView posicao;
        TextView nome;
        TextView rank;
        ImageView imgIcon;
    }
}
