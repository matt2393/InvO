package com.matt2393.invo.Vista.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.matt2393.invo.Modelo.TeoriaJuegos.DecisionJuego;
import com.matt2393.invo.Modelo.TeoriaJuegos.TDModelos;
import com.matt2393.invo.R;
import com.matt2393.invo.Tools.Tools;
import com.matt2393.invo.Vista.Dialogs.ResultDialog;
import com.matt2393.invo.Vista.Dialogs.SelectDialog;
import com.matt2393.invo.Vista.Listeners.OnSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JuegosFragment extends Fragment implements OnSelectListener {

    @BindView(R.id.edit_cant_jugA)
    TextInputEditText editJA;
    @BindView(R.id.edit_cant_jugB)
    TextInputEditText editJB;

    @BindView(R.id.container_matriz_jueg)
    HorizontalScrollView horizontalScrollMatriz;
    @BindView(R.id.button_aceptar_tam_jueg)
    Button buttonAceptar;
    @BindView(R.id.button_resolver_jueg)
    Button buttonResolver;

    @BindView(R.id.linearlayout_content_data_jueg)
    LinearLayout contentData;

    private TextInputEditText matrizView[][],probPView[],probQView[];

    private int cantJugA,cantJugB;

    private DisplayMetrics displayMetrics;
    private TDModelos decisiones;


    public static JuegosFragment newInstance(){
        return new JuegosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_juegos,container,false);
        ButterKnife.bind(this,view);
        displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        buttonAceptar.setOnClickListener(v -> {
            String cantA,cantB;
            cantA=editJA.getText().toString();
            cantB=editJB.getText().toString();
            if(TextUtils.isEmpty(cantA) || TextUtils.isEmpty(cantB) || cantA.equals("0") || cantB.equals("0")){
                Toast.makeText(getActivity(),"Debe colocar un número distinto de 0 en las cantidades",Toast.LENGTH_LONG).show();
                return;
            }
            cantJugA=Integer.parseInt(cantA);
            cantJugB=Integer.parseInt(cantB);
            matrizView=new TextInputEditText[cantJugA][cantJugB];
            probPView=new TextInputEditText[cantJugA];
            probQView=new TextInputEditText[cantJugB];

            horizontalScrollMatriz.removeAllViews();
            matrizView= Tools.genMatriz(getActivity(), horizontalScrollMatriz,
                    displayMetrics,cantJugA,cantJugB,"A","B");

            contentData.setVisibility(View.VISIBLE);


        });

        buttonResolver.setOnClickListener(v -> {

            decisiones=new TDModelos();
            decisiones.setMatrizPagos(Tools.getDataMatriz(matrizView));
            decisiones.setN(cantJugA);
            decisiones.setM(cantJugB);

            ArrayList<String> selects=new ArrayList<>();
            selects.add("Punto silla");
            selects.add("Algebraico");

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
        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<Object> datos = new ArrayList<>();


        switch (pos) {
            case 0:
                DecisionJuego dec = decisiones.puntoSilla();
                if(dec!=null) {
                    titulos.add("Valor Juego");
                    datos.add(dec.getValorJuego());
                    titulos.add("Estrategia J-A");
                    datos.add(dec.getjA() + 1);
                    titulos.add("Estrategia J-B");
                    datos.add(dec.getjB() + 1);
                }
                else{
                    titulos.add("No existe punto silla para el juego");
                    datos.add("");
                }
                showDialog("Punto silla", titulos, datos);
                break;
            case 1:
                DecisionJuego[] decAl = decisiones.algebraico();
                if(decAl[0]!=null && decAl[1]!=null) {
                    //titulos.add("Valor Jugador A");
                    //datos.add(decAl[0].getValorJuego());
                    titulos.add("Estrategia Jug A");
                    datos.add(decAl[0].getjA() + 1);
                    titulos.add("Prob P, Jug A");
                    datos.add(decAl[0].getProb());
                    //titulos.add("Valor Jugador B");
                    //datos.add(decAl[1].getValorJuego());
                    titulos.add("Estrategia Jug B");
                    datos.add(decAl[1].getjA() + 1);
                    titulos.add("Prob Q, Jug B");
                    datos.add(decAl[1].getProb());
                }
                else{
                    titulos.add("No se puede resolver, la matriz debe ser 2x2 y la dominación no puede reducir mas la matriz");
                    datos.add("");
                }
                showDialog("MaxiMin", titulos, datos);
                break;

        }
    }

    private void showDialog(String titulo, ArrayList<String> tits, ArrayList<Object> datos){
        ResultDialog.newInstance(titulo,tits,datos)
                .show(getActivity().getSupportFragmentManager()
                        ,ResultDialog.TAG);
    }
}
