package com.matt2393.invo.Vista.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.matt2393.invo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputAuxDialog extends DialogFragment {
    public static final String TAG="InputAuxDialog";
    private final static String TIT="Titulo";
    private final static String HINT="Hint";
    private String titulo;
    private String hint;

    @BindView(R.id.text_titulo_input_aux)
    TextView textTitulo;
    @BindView(R.id.edit_input_aux)
    TextInputEditText editInput;

    private OnInputDataListener onInputDataListener;

    public static InputAuxDialog newInstance(String titulo,String hint){
        Bundle bn=new Bundle();
        bn.putString(TIT,titulo);
        bn.putString(HINT,hint);
        InputAuxDialog dialog=new InputAuxDialog();
        dialog.setArguments(bn);
        return dialog;
    }

    public interface OnInputDataListener{
        void onInput(double data);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        View view=getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_input_aux,null);
        ButterKnife.bind(this,view);
        hint=getArguments().getString(HINT);
        titulo=getArguments().getString(TIT);
        textTitulo.setText(titulo);
        editInput.setHint(hint);
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

    public void setOnInputData(OnInputDataListener onInputDataListener) {
        this.onInputDataListener= onInputDataListener;
    }

    @OnClick(R.id.button_aceptar_input_aux)
    public void buttonAceptar(){
        if(onInputDataListener!=null){
            String val = editInput.getText().toString();
            double dat = TextUtils.isEmpty(val)? 0d : Double.parseDouble(val);
            onInputDataListener.onInput(dat);
            dismiss();
        }
    }
}
