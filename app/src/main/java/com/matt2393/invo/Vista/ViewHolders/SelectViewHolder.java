package com.matt2393.invo.Vista.ViewHolders;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matt2393.invo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.button_item_select)
    public Button buttonSelect;
    public SelectViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
