package com.matt2393.invo.Vista.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matt2393.invo.R;
import com.matt2393.invo.Vista.Adapters.ResulAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultDialog extends DialogFragment {
    public static final String TAG="ResultDialog";
    private static final String TITULO="TITULO";
    private static final String TITS="TITULOS_";
    private static final String DATA="DATA";

    private HashMap<String, Object> map;
    ArrayList<String> titulos=new ArrayList<>();
    ArrayList<Object> datos=new ArrayList<>();
    private String titulo;

    @BindView(R.id.text_titulo_resul)
    TextView textTitulo;
    @BindView(R.id.recycler_resul)
    RecyclerView recycler;

    public static ResultDialog newInstance(String titulo, ArrayList<String> tits, ArrayList<Object> data){
        Bundle bn=new Bundle();
        bn.putSerializable(DATA,data);
        bn.putStringArrayList(TITS,tits);
        bn.putString(TITULO,titulo);
        ResultDialog dialog=new ResultDialog();
        dialog.setArguments(bn);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        View view=getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_resul,null);

        ButterKnife.bind(this,view);
        titulo=getArguments().getString(TITULO);
        titulos=getArguments().getStringArrayList(TITS);
        datos= (ArrayList<Object>) getArguments().getSerializable(DATA);

        textTitulo.requestFocus();

        ResulAdapter adapter=new ResulAdapter(titulos,datos);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
        recycler.setNestedScrollingEnabled(false);
        recycler.setFocusable(false);
        textTitulo.setText(titulo);

        alert.setView(view);
        return alert.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getDialog()!=null && getDialog().getWindow()!=null){
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @OnClick(R.id.button_aceptar_resul)
    public void buttonAceptar(){
        dismiss();
    }
}
