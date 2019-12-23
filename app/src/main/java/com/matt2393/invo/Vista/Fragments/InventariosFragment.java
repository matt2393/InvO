package com.matt2393.invo.Vista.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.matt2393.invo.Modelo.TeoriaInventarios.DecInvDescuentos;
import com.matt2393.invo.Modelo.TeoriaInventarios.DecInvEOQ;
import com.matt2393.invo.R;
import com.matt2393.invo.Vista.Adapters.DataAdd1Adapter;
import com.matt2393.invo.Vista.Dialogs.ResultDialog;
import com.matt2393.invo.Vista.Dialogs.SelectDialog;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InventariosFragment extends Fragment {

    public static final String TAG="InventariosFragment";

    @BindView(R.id.edit_c1_inv)
    TextInputEditText editC1;
    @BindView(R.id.edit_c2_inv)
    TextInputEditText editc2;

    @BindView(R.id.edit_c3_inv)
    TextInputEditText editC3;
    @BindView(R.id.edit_r_inv)
    TextInputEditText editDemanda;
    @BindView(R.id.edit_k_inv)
    TextInputEditText editProd;

    @BindView(R.id.textinput_c1_inv)
    TextInputLayout textInputLayoutC1;
    @BindView(R.id.textinput_c2_inv)
    TextInputLayout textInputLayoutC2;
    @BindView(R.id.textinput_c3_inv)
    TextInputLayout textInputLayoutC3;
    @BindView(R.id.textinput_r_inv)
    TextInputLayout textInputLayoutDem;
    @BindView(R.id.textinput_k_inv)
    TextInputLayout textInputLayoutProd;

    @BindView(R.id.linearlayout_content_data_inv)
    LinearLayout linearLayoutData;

    @BindView(R.id.edit_descuento_inv)
    TextInputEditText editDesc;
    @BindView(R.id.recycler_datos_add_inv)
    RecyclerView recDatos1;


    private double c1,c2,c3,r,k;
    private int descInt;

    private int modelo=0;
    private String nomMod="";
    SelectDialog dialog;

    private DataAdd1Adapter adapterData1;

    public static InventariosFragment newInatance(){
        return new InventariosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inv,container,false);
        ButterKnife.bind(this,view);

        final ArrayList<String> selects=new ArrayList<>(Arrays.asList(
                "EOQ",
                "Descuentos"
        //        , "Almacenamiento"
        ));
        dialog= SelectDialog.newInstance("Seleccione un modelo de inventario",  selects);
        dialog.setCancelable(false);
        dialog.setOnSelectListener(pos -> {
            modelo=pos;
            nomMod=selects.get(pos);
           switch (pos){
                case 0: textInputLayoutC2.setVisibility(View.VISIBLE);
                    textInputLayoutProd.setVisibility(View.VISIBLE);
                    linearLayoutData.setVisibility(View.GONE);
                    break;
                case 1: textInputLayoutC2.setVisibility(View.GONE);
                    textInputLayoutProd.setVisibility(View.GONE);
                    linearLayoutData.setVisibility(View.VISIBLE);
                    recDatos1.setLayoutManager(new LinearLayoutManager(getActivity()));
                    break;
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(dialog!=null)
            dialog.show(getActivity().getSupportFragmentManager(),SelectDialog.TAG);
    }

    @OnClick(R.id.button_add_inv)
    public void buttonAdd(){
        String desc=editDesc.getText().toString();
        if(TextUtils.isEmpty(desc) || desc.equals("0")){
            Toast.makeText(getActivity(), "La cantidad de descuentos debe ser mayor a 0", Toast.LENGTH_LONG).show();
            return;
        }
        descInt=Integer.parseInt(desc);
        adapterData1=new DataAdd1Adapter(descInt);
        recDatos1.setAdapter(adapterData1);
    }

    @OnClick(R.id.button_resolver_inv)
    public void buttonResolver(){
        String sC1=editC1.getText().toString();
        String sC2=editc2.getText().toString();
        String sC3=editC3.getText().toString();
        String sR=editDemanda.getText().toString();
        String sK=editProd.getText().toString();

        if(modelo==0) {
            if (TextUtils.isEmpty(sC1) || TextUtils.isEmpty(sC2) || sC1.equals("0") || sC2.equals("0")
                    || TextUtils.isEmpty(sC3) || TextUtils.isEmpty(sR) || sC3.equals("0") || sR.equals("0")
                    || TextUtils.isEmpty(sK) || sK.equals("0")) {
                Toast.makeText(getActivity(), "Los datos deben ser mayores a 0", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if(modelo==1){
            if (TextUtils.isEmpty(sC1) || sC1.equals("0")
                    || TextUtils.isEmpty(sC3) || TextUtils.isEmpty(sR) || sC3.equals("0") || sR.equals("0")) {
                Toast.makeText(getActivity(), "Los datos deben ser mayores a 0", Toast.LENGTH_LONG).show();
                return;
            }
            for(Double d:adapterData1.getCantidad()){
                double dd=d==null ? 0:d;
                if(dd==0){
                    Toast.makeText(getActivity(), "Las cantidades deben ser distintas de 0", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            for(Double d:adapterData1.getPrecios()){
                double dd=d==null ? 0:d;
                if(dd==0){
                    Toast.makeText(getActivity(), "Las cantidades deben ser distintas de 0", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        c1=Double.parseDouble(sC1);
        c3=Double.parseDouble(sC3);
        r=Double.parseDouble(sR);
        if(modelo==0) {
            c2 = Double.parseDouble(sC2);
            k = Double.parseDouble(sK);
        }

        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<Object> datos = new ArrayList<>();

        if(modelo==0){
            DecInvEOQ invEOQ=new DecInvEOQ();
            invEOQ.modelo(c1,c2,c3,r,k);
            titulos.add("t1");
            datos.add(invEOQ.getT1());
            titulos.add("t2");
            datos.add(invEOQ.getT2());
            titulos.add("t3");
            datos.add(invEOQ.getT3());
            titulos.add("t4");
            datos.add(invEOQ.getT4());
            titulos.add("S");
            datos.add(invEOQ.getS());
            titulos.add("D");
            datos.add(invEOQ.getD());
            titulos.add("q");
            datos.add(invEOQ.getQ());
            titulos.add("CTS");
            datos.add(invEOQ.getCTS());
        }
        else{
            DecInvDescuentos descuentos=new DecInvDescuentos();
            descuentos.modelo(c1,c3,r,adapterData1.getCantidad(),adapterData1.getPrecios());
            titulos.add("q");
            datos.add(descuentos.getQ());
            titulos.add("CTS");
            datos.add(descuentos.getCTS());
        }
        showDialog(nomMod,titulos,datos);
    }

    private void showDialog(String titulo, ArrayList<String> tits, ArrayList<Object> datos){
        ResultDialog.newInstance(titulo,tits,datos)
                .show(getActivity().getSupportFragmentManager()
                        ,ResultDialog.TAG);
    }

}
