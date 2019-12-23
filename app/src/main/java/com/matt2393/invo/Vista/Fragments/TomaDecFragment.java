package com.matt2393.invo.Vista.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.matt2393.invo.Modelo.TomaDecision.Decision;
import com.matt2393.invo.Modelo.TomaDecision.TDModelos;
import com.matt2393.invo.R;
import com.matt2393.invo.Tools.Tools;
import com.matt2393.invo.Vista.Dialogs.InputAuxDialog;
import com.matt2393.invo.Vista.Dialogs.ResultDialog;
import com.matt2393.invo.Vista.Dialogs.SelectDialog;
import com.matt2393.invo.Vista.Listeners.OnSelectListener;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.HashMap;

public class TomaDecFragment extends Fragment implements OnSelectListener {
    public final static String TAG="TomaDecFragment";


    private TextInputEditText textInputEditTextAcciones,textInputEditTextEstados,editEstudio;
    private HorizontalScrollView horizontalScrollMatriz, horizontalScrollProb, horizontalScrollApriori;
    private Button buttonAceptar,buttonResolver,buttonAceptarEstudio;
    private BoomMenuButton boomMenuButton;

    private LinearLayout contentData;

    private TextInputEditText matrizView[][],probView[],aprioriView[][];

    private int cantAcciones,cantEstados,cantEstudios;

    private DisplayMetrics displayMetrics;
    private boolean conApriori;

    private TDModelos decisiones;

    public static TomaDecFragment newInstance(){
        return new TomaDecFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tomadec,container,false);
        textInputEditTextAcciones=view.findViewById(R.id.textinputedittext_acciones);
        textInputEditTextEstados=view.findViewById(R.id.textinputedittext_estados);
        editEstudio=view.findViewById(R.id.textinputedittext_estudio);
        buttonAceptar=view.findViewById(R.id.button_aceptar_tam);
        buttonResolver=view.findViewById(R.id.button_resolver_tomadec);
        buttonAceptarEstudio=view.findViewById(R.id.button_aceptar_tam_estudio);

        horizontalScrollMatriz =view.findViewById(R.id.container_matriz_tomadec);
        horizontalScrollProb =view.findViewById(R.id.container_prob_tomadec);
        horizontalScrollApriori =view.findViewById(R.id.container_apriori_tomadec);

        conApriori=false;
        contentData=view.findViewById(R.id.linearlayout_content_data);

        //boomMenuButton=view.findViewById(R.id.bmb_desicion);


        displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        buttonAceptar.setOnClickListener(v -> {
            String cantA,cantE;
            cantA=textInputEditTextAcciones.getText().toString();
            cantE=textInputEditTextEstados.getText().toString();
            if(TextUtils.isEmpty(cantA) || TextUtils.isEmpty(cantE) || cantA.equals("0") || cantE.equals("0")){
                Toast.makeText(getActivity(),"Debe colocar un número distinto de 0 en las cantidades",Toast.LENGTH_LONG).show();
                return;
            }
            cantAcciones=Integer.parseInt(cantA);
            cantEstados=Integer.parseInt(cantE);
            matrizView=new TextInputEditText[cantAcciones][cantEstados];
            probView=new TextInputEditText[cantEstados];
            aprioriView=new TextInputEditText[cantAcciones][cantEstados];

            horizontalScrollMatriz.removeAllViews();
            horizontalScrollProb.removeAllViews();
            horizontalScrollApriori.setVisibility(View.GONE);
            matrizView= Tools.genMatriz(getActivity(), horizontalScrollMatriz,
                    displayMetrics,cantAcciones,cantEstados,"A","E");
            probView= Tools.genVector(getActivity(), horizontalScrollProb,displayMetrics,cantEstados);
            //aprioriView=Tools.genMatriz(getActivity(), horizontalScrollApriori,displayMetrics,cantAcciones,cantEstados);//genApriori();

            contentData.setVisibility(View.VISIBLE);

            for(TextInputEditText edit: probView){
                edit.setText(String.valueOf(Math.rint(1d/(double)probView.length * 1000)/1000 ));
            }

        });
        buttonAceptarEstudio.setOnClickListener(v -> {
            horizontalScrollApriori.removeAllViews();
            String cantEstu=editEstudio.getText().toString();
            if(TextUtils.isEmpty(cantEstu)){
                Toast.makeText(getActivity(),"Debe colocar un número distinto de 0 en la cantidad de resultados de estudio",Toast.LENGTH_LONG).show();
                return;
            }
            conApriori=true;
            horizontalScrollApriori.setVisibility(View.VISIBLE);
            cantEstudios=Integer.parseInt(cantEstu);
            aprioriView=Tools.genMatriz(getActivity(), horizontalScrollApriori,displayMetrics,cantEstudios,cantEstados,
                    "Y","E");//genApriori();
        });

        buttonResolver.setOnClickListener(v -> {
            if(!Tools.validateSumProb(probView)){
                Toast.makeText(getActivity(),"La suma de probabilidades debe ser 1",Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            if(conApriori){
                if(!Tools.validateSumProbMatrizCol(aprioriView)){
                    Toast.makeText(getActivity(),"La suma de probabilidades de las columnas de la matriz apriori deben ser 1",Toast.LENGTH_LONG)
                            .show();
                    return;
                }
            }
            decisiones=new TDModelos();
            decisiones.setMatriz_pagos(Tools.getDataMatriz(matrizView));
            if(conApriori)
                decisiones.setApriori(Tools.getDataMatriz(aprioriView));
            decisiones.setProb(Tools.getDataVector(probView));
            decisiones.setN(cantAcciones);
            decisiones.setM(cantEstados);

            ArrayList<String> selects=new ArrayList<>();
            selects.add("MaxiMax");
            selects.add("MaxiMin");
            selects.add("Max. Prob. Pesimista");
            selects.add("Hurwicz");
            selects.add("MiniMax");
            selects.add("Bayes sin Exper.");
            if(conApriori)
                selects.add("Bayes con Exper.");

            SelectDialog selectDialog=SelectDialog.newInstance("Selecciona un modelo",selects);
            selectDialog.setOnSelectListener(this);
            selectDialog.show(getActivity().getSupportFragmentManager(),SelectDialog.TAG);

            /*
            Decision dec=decisiones.MaxProbPesimista();
            HashMap<String,Object> map=new HashMap<>();
            map.put("Valor",dec.getValor());
            map.put("Probabilidad",dec.getProbabilidad());

            ResultDialog.newInstance("Maxima Probabilidad Pesimista",map).show(getActivity().getSupportFragmentManager(),ResultDialog.TAG);
*/

        });



        return view;
    }


    @Override
    public void onSelectItem(int pos) {
        ArrayList<String> titulos=new ArrayList<>();
        ArrayList<Object> datos=new ArrayList<>();
        Decision dec;

        switch (pos){
            case 0:
                dec=decisiones.MaxiMax();
                titulos.add("Valor");
                datos.add(dec.getValor());
                titulos.add("Alternativa");
                datos.add(dec.getI()+1);
                showDialog("MaxiMax",titulos,datos);
                break;
            case 1:
                dec=decisiones.MaxiMin();
                titulos.add("Valor");
                datos.add(dec.getValor());
                titulos.add("Alternativa");
                datos.add(dec.getI()+1);
                showDialog("MaxiMin",titulos,datos);

                break;
            case 2:
                dec=decisiones.MaxProbPesimista();
                titulos.add("Valor");
                datos.add(dec.getValor());
                titulos.add("Alternativa");
                datos.add(dec.getI()+1);
                titulos.add("Prob");
                datos.add(dec.getProbabilidad());
                showDialog("Máxima Probabilidad Pesimista",titulos,datos);
                break;
            case 3:
                InputAuxDialog dialog=InputAuxDialog.newInstance("Valor de Alfa", "Alfa");
                dialog.setOnInputData(data -> {
                    if(data>1){
                        Toast.makeText(getActivity(),"El valor de alfa no debe ser mayor a 1",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(data<0){
                        Toast.makeText(getActivity(),"El valor de alfa no debe ser negativo",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    Decision decA=decisiones.Hurwicz(data);
                    titulos.add("Valor");
                    datos.add(decA.getValor());
                    titulos.add("Alternativa");
                    datos.add(decA.getI()+1);
                    showDialog("Hurwicz", titulos,datos);
                });
                dialog.show(getActivity().getSupportFragmentManager(),InputAuxDialog.TAG);
                break;
            case 4:
                dec=decisiones.MiniMax();
                titulos.add("Valor");
                datos.add(dec.getValor());
                titulos.add("Alternativa");
                datos.add(dec.getI()+1);
                titulos.add("Prob");
                datos.add(dec.getProbabilidad());
                showDialog("MiniMax",titulos,datos);
                break;
            case 5:
                dec=decisiones.BayesSinExp();
                titulos.add("Valor");
                datos.add(dec.getValor());
                titulos.add("Alternativa");
                datos.add(dec.getI()+1);
                showDialog("Bayes sin Experimentación",titulos,datos);
                break;
            case 6:
                InputAuxDialog dialog2=InputAuxDialog.newInstance("Valor de la Experimentación", "Valor");
                dialog2.setOnInputData(data -> {

                    if(data<0){
                        Toast.makeText(getActivity(),"El valor de la experimentación no debe ser negativo",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    Decision decSinEx=decisiones.BayesSinExp();
                    Decision[] decConEx=decisiones.BayesConExp(Tools.getDataMatriz(aprioriView),data);
                    double VEIP=decisiones.VEIP(decSinEx);
                    double VEE = decisiones.VEE(decSinEx,decConEx);
                    double valor = VEE + decSinEx.getValor();

                    titulos.add("Valor sin Exp");
                    datos.add(decSinEx.getValor());

                    titulos.add("Alternativa sin Exp");
                    datos.add(decSinEx.getI()+1);

                    titulos.add("Valores con experimentación");
                    datos.add("");
                    for(int i=0;i<decConEx.length;i++){
                        titulos.add("Valor Y-"+(i+1));
                        datos.add(decConEx[i].getValor());
                        titulos.add("Alternativa "+(i+1));
                        datos.add((decConEx[i].getI()+1));
                    }
                    titulos.add("Pago esperado con Exp");
                    datos.add(valor);
                    titulos.add("VEIP");
                    datos.add(VEIP);
                    titulos.add("VEE");
                    datos.add(VEE);
                    titulos.add("Valor del EXP");
                    datos.add(data);
                    titulos.add("Conclusión");
                    datos.add(VEE>data?"Vale la pena realizar la experimentación":"No vale la pena realizar la experimentación");
                    //map.put("Valor",decA.getValor());
                    //map.put("Alternativa",dec.getI()+1);
                    showDialog("Bayes con Experimentación", titulos,datos);
                });
                dialog2.show(getActivity().getSupportFragmentManager(),InputAuxDialog.TAG);
                break;
        }
    }

    private void showDialog(String titulo, ArrayList<String> tits, ArrayList<Object> datos){
        ResultDialog.newInstance(titulo,tits,datos)
                .show(getActivity().getSupportFragmentManager()
                        ,ResultDialog.TAG);
    }
}
