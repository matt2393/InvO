package com.matt2393.invo.Vista.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matt2393.invo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResulViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_tit_item_resul)
    public TextView textTitulo;
    @BindView(R.id.text_result_item_resul)
    public TextView textResul;

    public ResulViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
