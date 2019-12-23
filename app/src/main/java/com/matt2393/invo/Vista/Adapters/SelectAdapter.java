package com.matt2393.invo.Vista.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matt2393.invo.R;
import com.matt2393.invo.Vista.Listeners.OnSelectListener;
import com.matt2393.invo.Vista.ViewHolders.SelectViewHolder;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectViewHolder> {

    private ArrayList<String> selects;
    private OnSelectListener onSelectListener;

    public SelectAdapter(ArrayList<String> selects, OnSelectListener onSelectListener) {
        this.selects = selects;
        this.onSelectListener = onSelectListener;
    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alternative,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder holder, int position) {
        final int pos=position;
        holder.buttonSelect.setText(selects.get(pos));
        holder.buttonSelect.setOnClickListener(v -> {
            onSelectListener.onSelectItem(pos);
        });
    }

    @Override
    public int getItemCount() {
        return selects.size();
    }
}
