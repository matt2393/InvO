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
import com.matt2393.invo.Vista.Adapters.SelectAdapter;
import com.matt2393.invo.Vista.Listeners.OnSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectDialog extends DialogFragment implements OnSelectListener {
    public final static String TAG="SelectDialog";
    private final static String SELECTS="SELECTS";
    private final static String TITULO="TITULO";

    @BindView(R.id.text_titulo_select)
    TextView textTitulo;
    @BindView(R.id.recycler_select)
    RecyclerView recyclerView;
    private ArrayList<String> selects;
    private String titulo;
    private OnSelectListener onSelectListener;

    public static SelectDialog newInstance(String titulo,ArrayList<String> selects){
        Bundle bn=new Bundle();
        bn.putStringArrayList(SELECTS, selects);
        bn.putString(TITULO, titulo);
        SelectDialog dialog=new SelectDialog();
        dialog.setArguments(bn);
        return dialog;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        View view= getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_select_model,null);
        ButterKnife.bind(this,view);
        titulo=getArguments().getString(TITULO);
        selects=getArguments().getStringArrayList(SELECTS);
        textTitulo.setText(titulo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SelectAdapter(selects,this));

        alert.setView(view);
        return alert.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getDialog()!=null && getDialog().getWindow()!=null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSelectItem(int pos) {
        if(onSelectListener!=null)
            onSelectListener.onSelectItem(pos);
        dismiss();
    }
}
