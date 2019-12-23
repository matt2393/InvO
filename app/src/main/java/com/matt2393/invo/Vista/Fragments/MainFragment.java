package com.matt2393.invo.Vista.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.matt2393.invo.R;

public class MainFragment extends Fragment {
    public final static String TAG="MainFragment";

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);

        Button  tomaDec,juegos,inv,colas;

        tomaDec=view.findViewById(R.id.button_toma_dec_main);
        juegos=view.findViewById(R.id.button_juegos_main);
        inv=view.findViewById(R.id.button_inv_main);
        colas=view.findViewById(R.id.button_colas_main);

        tomaDec.setOnClickListener(v ->
            changeFragment(TomaDecFragment.newInstance())
        );
        juegos.setOnClickListener(v ->
            changeFragment(JuegosFragment.newInstance())
        );
        inv.setOnClickListener(v ->
            changeFragment(InventariosFragment.newInatance())
        );
        colas.setOnClickListener(v ->
            changeFragment(ColasFragment.newInstance())
        );

        return view;
    }

    private void changeFragment(Fragment fr){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main,fr)
                .addToBackStack(null)
                .commit();
    }
}
