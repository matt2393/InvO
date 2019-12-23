package com.matt2393.invo.Vista.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matt2393.invo.R;
import com.matt2393.invo.Vista.ViewHolders.DataAdd1ViewHolder;

import java.util.ArrayList;

public class DataAdd1Adapter extends RecyclerView.Adapter<DataAdd1ViewHolder> {

    private ArrayList<Double> precios;
    private ArrayList<Double> cantidad;
    private int size;

    public DataAdd1Adapter(int size) {
        this.size = size;
        precios=new ArrayList<>(size);
        cantidad=new ArrayList<>(size);
    }

    public ArrayList<Double> getPrecios() {
        return precios;
    }

    public ArrayList<Double> getCantidad() {
        return cantidad;
    }

    @NonNull
    @Override
    public DataAdd1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataAdd1ViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data_add1,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdd1ViewHolder holder, int position) {
        final int pos=position;
        holder.textInputLayoutCantAdd.setHint("k"+(position+1));
        holder.textInputLayoutPrecioAdd.setHint("p"+(position+1));
        holder.editCantAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    cantidad.add(pos,Double.parseDouble(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.editPrecioAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    precios.add(pos,Double.parseDouble(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
