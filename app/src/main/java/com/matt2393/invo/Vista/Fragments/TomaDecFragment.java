package com.matt2393.invo.Vista.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.matt2393.invo.R;

public class TomaDecFragment extends Fragment {
    public final static String TAG="TomaDecFragment";


    private TextInputEditText textInputEditTextAcciones,textInputEditTextEstados;
    private HorizontalScrollView coordinatorLayoutMatriz,coordinatorLayoutProb,coordinatorLayoutApriori;
    private Button buttonAceptar,buttonResolver;

    private LinearLayout contentData;

    private TextInputEditText matrizView[][],probView[],aprioriView[][];

    private int cantAcciones,cantEstados;

    private DisplayMetrics displayMetrics;

    public static TomaDecFragment newInstance(){
        return new TomaDecFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tomadec,container,false);
        textInputEditTextAcciones=view.findViewById(R.id.textinputedittext_acciones);
        textInputEditTextEstados=view.findViewById(R.id.textinputedittext_estados);
        buttonAceptar=view.findViewById(R.id.button_aceptar_tam);
        buttonResolver=view.findViewById(R.id.button_resolver_tomadec);

        coordinatorLayoutMatriz=view.findViewById(R.id.container_matriz_tomadec);
        coordinatorLayoutProb=view.findViewById(R.id.container_prob_tomadec);
        coordinatorLayoutApriori=view.findViewById(R.id.container_apriori_tomadec);

        contentData=view.findViewById(R.id.linearlayout_content_data);


        displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        buttonAceptar.setOnClickListener(v -> {
            cantAcciones=Integer.parseInt(textInputEditTextAcciones.getText().toString());
            cantEstados=Integer.parseInt(textInputEditTextEstados.getText().toString());
            matrizView=new TextInputEditText[cantAcciones][cantEstados];
            probView=new TextInputEditText[cantEstados];
            aprioriView=new TextInputEditText[cantAcciones][cantEstados];

            genMatriz();
            genProb();
            genApriori();

            contentData.setVisibility(View.VISIBLE);

        });





        return view;
    }

    private void genMatriz(){
        HorizontalScrollView.LayoutParams params1=new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout li=new LinearLayout(getActivity());
        li.setLayoutParams(params1);
        li.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3=new LinearLayout.LayoutParams(
                80*displayMetrics.densityDpi/160, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < cantAcciones; i++) {

            LinearLayout li2=new LinearLayout(getActivity());
            li2.setLayoutParams(params2);
            li2.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < cantEstados; j++) {
                TextInputEditText editText=new TextInputEditText(getActivity());
                editText.setLayoutParams(params3);
                matrizView[i][j]=editText;
                li2.addView(editText);
            }
            li.addView(li2);
        }

        coordinatorLayoutMatriz.addView(li);

    }

    private void genApriori(){
        HorizontalScrollView.LayoutParams params1=new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout li=new LinearLayout(getActivity());
        li.setLayoutParams(params1);
        li.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3=new LinearLayout.LayoutParams(
                80*displayMetrics.densityDpi/160, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < cantAcciones; i++) {

            LinearLayout li2=new LinearLayout(getActivity());
            li2.setLayoutParams(params2);
            li2.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < cantEstados; j++) {
                TextInputEditText editText=new TextInputEditText(getActivity());
                editText.setLayoutParams(params3);
                aprioriView[i][j]=editText;
                li2.addView(editText);
            }
            li.addView(li2);
        }

        coordinatorLayoutApriori.addView(li);

    }

    private void genProb(){
        HorizontalScrollView.LayoutParams params1=new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout li=new LinearLayout(getActivity());
        li.setLayoutParams(params1);
        li.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3=new LinearLayout.LayoutParams(
                80*displayMetrics.densityDpi/160, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < cantEstados; i++) {
            TextInputEditText editText=new TextInputEditText(getActivity());
            editText.setLayoutParams(params3);
            probView[i]=editText;
            li.addView(editText);
        }

        coordinatorLayoutProb.addView(li);

    }
}
