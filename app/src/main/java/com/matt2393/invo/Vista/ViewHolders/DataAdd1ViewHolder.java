package com.matt2393.invo.Vista.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.matt2393.invo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataAdd1ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.textinput_cant_add_inv)
    public TextInputLayout textInputLayoutCantAdd;

    @BindView(R.id.textinput_precio_add_inv)
    public TextInputLayout textInputLayoutPrecioAdd;

    @BindView(R.id.edit_cant_add_inv)
    public TextInputEditText editCantAdd;

    @BindView(R.id.edit_precio_add_inv)
    public TextInputEditText editPrecioAdd;


    public DataAdd1ViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
