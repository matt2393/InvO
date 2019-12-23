package com.matt2393.invo.Vista.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.matt2393.invo.Modelo.TeoriaColas.Colas;
import com.matt2393.invo.R;
import com.matt2393.invo.Vista.Dialogs.ResultDialog;
import com.matt2393.invo.Vista.Dialogs.SelectDialog;
import com.matt2393.invo.Vista.Listeners.OnSelectListener;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColasFragment extends Fragment {
    public final static String TAG="ColasFragment";

    @BindView(R.id.edit_nac_colas)
    TextInputEditText editNac;
    @BindView(R.id.edit_muerte_colas)
    TextInputEditText editMuerte;

    @BindView(R.id.edit_servidor_colas)
    TextInputEditText editServ;
    @BindView(R.id.edit_capacidad_colas)
    TextInputEditText editCant;

    @BindView(R.id.textinput_muertes_colas)
    TextInputLayout textInputLayoutMuer;
    @BindView(R.id.textinput_nac_colas)
    TextInputLayout textInputLayoutNac;
    @BindView(R.id.textinput_servidor_colas)
    TextInputLayout textInputLayoutServ;
    @BindView(R.id.textinput_cantidad_colas)
    TextInputLayout textInputLayoutCant;

    private double nac,muer,ser,cant;

    private int modelo=0;
    private String nomMod="";
    SelectDialog dialog;


    public static ColasFragment newInstance(){
        return new ColasFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_colas,container,false);
        ButterKnife.bind(this,view);


        String infinity=DecimalFormatSymbols.getInstance().getInfinity();
        final ArrayList<String> selects=new ArrayList<>(Arrays.asList(
                "(M,M,1):(FIFO,"+ infinity +","+infinity+")",
                "(M,M,1):(DG,"+ "N,"+infinity +")",
                "(M,M,S):(DG,"+ infinity +",+"+infinity+")",
                "(M,M,S):(DG,"+ "N,"+infinity +")"));
        dialog=SelectDialog.newInstance("Seleccione un modelo de colas",  selects);
        dialog.setCancelable(false);
        dialog.setOnSelectListener(pos -> {
            modelo=pos;
            nomMod=selects.get(pos);
            switch (pos){
                case 0: textInputLayoutServ.setVisibility(View.GONE);
                        textInputLayoutCant.setVisibility(View.GONE);
                    break;
                case 1: textInputLayoutServ.setVisibility(View.GONE);
                        textInputLayoutCant.setVisibility(View.VISIBLE);
                    break;
                case 2: textInputLayoutServ.setVisibility(View.VISIBLE);
                        textInputLayoutCant.setVisibility(View.GONE);
                    break;
                case 3: textInputLayoutServ.setVisibility(View.VISIBLE);
                        textInputLayoutCant.setVisibility(View.VISIBLE);
                    break;
            }
        });

        String lampda="";
        String mu="";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            lampda = Html.fromHtml("&lambda", Html.FROM_HTML_MODE_LEGACY).toString();
            mu = Html.fromHtml("&mu", Html.FROM_HTML_MODE_LEGACY).toString();
        }
        else {
            lampda = Html.fromHtml("&lambda").toString();
            mu = Html.fromHtml("&mu").toString();
        }
        //editNac.setHint(lampda);
        //editMuerte.setHint(mu);
        textInputLayoutMuer.setHint(mu);
        textInputLayoutNac.setHint(lampda);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(dialog!=null)
            dialog.show(getActivity().getSupportFragmentManager(),SelectDialog.TAG);
    }

    @OnClick(R.id.button_resolver_cola)
    public void buttonResolver(){
        String sNac=editNac.getText().toString();
        String sMue=editMuerte.getText().toString();
        String sSer=editServ.getText().toString();
        String sCant=editCant.getText().toString();

        if(TextUtils.isEmpty(sNac) || TextUtils.isEmpty(sMue) || sNac.equals("0") || sMue.equals("0")){
            String mess="";
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                mess=Html.fromHtml("&lambda y &mu deben ser distintos de 0 ",Html.FROM_HTML_MODE_LEGACY).toString();
            else
                mess=Html.fromHtml("&lambda y &mu deben ser distintos de 0 ").toString();
            Toast.makeText(getActivity(),mess,Toast.LENGTH_LONG).show();
            return;
        }
        if(modelo==1 && TextUtils.isEmpty(sCant)){
            Toast.makeText(getActivity(),"Debe ingresar una capacidad del sistema ",Toast.LENGTH_LONG).show();
            return;
        }
        if(modelo==2 && TextUtils.isEmpty(sSer)){
            Toast.makeText(getActivity(),"Debe ingresar una cantidad de servidores",Toast.LENGTH_LONG).show();
            return;
        }
        if(modelo==3 && (TextUtils.isEmpty(sCant) || TextUtils.isEmpty(sSer))){
            Toast.makeText(getActivity(),"Debe ingresar una capacidad del sistema y cantidad de servidores",Toast.LENGTH_LONG).show();
            return;
        }
        nac=Double.parseDouble(sNac);
        muer=Double.parseDouble(sMue);

        if(modelo==1) {
            cant=Double.parseDouble(sCant);
        }
        if(modelo==2)
            ser=Double.parseDouble(sSer);
        if(modelo==3){
            ser=Double.parseDouble(sSer);
            cant=Double.parseDouble(sCant);
        }
        if(nac>=muer && modelo!=2 && modelo!=3){
            String mess="";
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                mess=Html.fromHtml("&mu debe ser mayor a &lambda",Html.FROM_HTML_MODE_LEGACY).toString();
            else
                mess=Html.fromHtml("&mu debe ser mayor a &lambda").toString();
            Toast.makeText(getActivity(),mess,Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<Object> datos = new ArrayList<>();

        Colas colas=new Colas();
        switch (modelo){
            case 0:
                colas.modeloMM1_FIFOinfinf(muer,nac);
                break;
            case 1:
                colas.modeloMM1_DGNinf(muer,nac,cant);
                break;
            case 2:
                colas.modeloMMS_DGinfinf(muer,nac,ser);
                break;
            case 3:
                colas.modeloMMS_DGNinf(muer,nac,ser,nac);
                break;
        }
        titulos.add("p");
        datos.add(colas.getP());
        titulos.add("P0");
        datos.add(colas.getP0());
        titulos.add("Lq");
        datos.add(colas.getLq());
        titulos.add("L2");
        datos.add(colas.getLs());
        titulos.add("Wq");
        datos.add(colas.getWq());
        titulos.add("Ws");
        datos.add(colas.getWs());
        showDialog(nomMod,titulos,datos);


    }
    private void showDialog(String titulo, ArrayList<String> tits, ArrayList<Object> datos){
        ResultDialog.newInstance(titulo,tits,datos)
                .show(getActivity().getSupportFragmentManager()
                        ,ResultDialog.TAG);
    }
}
