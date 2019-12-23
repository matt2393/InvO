package com.matt2393.invo.Vista.Adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matt2393.invo.R;
import com.matt2393.invo.Vista.ViewHolders.ResulViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

public class ResulAdapter extends RecyclerView.Adapter<ResulViewHolder> {

    private ArrayList<String> titulos;
    private ArrayList<Object> datos;


    public ResulAdapter(ArrayList<String> titulos, ArrayList<Object> datos) {
        this.titulos = titulos;
        this.datos = datos;
    }

    @NonNull
    @Override
    public ResulViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResulViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resul,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ResulViewHolder holder, int position) {
        holder.textTitulo.setText(titulos.get(position));
        String val=String.valueOf(datos.get(position));
        if(TextUtils.isEmpty(val)){
            holder.textTitulo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.textResul.setVisibility(View.GONE);
        }else {
            holder.textResul.setVisibility(View.VISIBLE);
            try{
                double dat=Double.parseDouble(val);
                String v;
                if(dat- (int)dat > 0) {
                    v = String.valueOf(Math.rint(dat * 1000) / 1000);
                }
                else
                    v= String.valueOf((int)dat);
                holder.textResul.setText(v);
            }catch (NumberFormatException e){

                holder.textResul.setText(val);
            }
        }
    }

    @Override
    public int getItemCount() {
        return titulos.size();
    }

}
