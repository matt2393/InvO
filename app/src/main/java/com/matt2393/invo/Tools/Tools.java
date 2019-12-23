package com.matt2393.invo.Tools;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Tools {

    private static DisplayMetrics displayMetrics;

    public static void init(FragmentActivity fragmentActivity) {
        displayMetrics = new DisplayMetrics();
        fragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    public static int dpToPx(int dp) {
        return dp * displayMetrics.densityDpi / 160;
    }


    public static TextInputEditText[][] genMatriz(Context context, ViewGroup parent, DisplayMetrics displayMetrics, int cantAcciones, int cantEstados,
                                                  String txt1, String txt2) {
        HorizontalScrollView.LayoutParams params1 = new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout li = new LinearLayout(context);
        li.setLayoutParams(params1);
        li.setOrientation(LinearLayout.VERTICAL);

        TextInputEditText[][] matrizView = new TextInputEditText[cantAcciones][cantEstados];

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                80 * displayMetrics.densityDpi / 160, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextInputLayout.LayoutParams paramText = new TextInputLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < cantAcciones; i++) {

            LinearLayout li2 = new LinearLayout(context);
            li2.setLayoutParams(params2);
            li2.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < cantEstados; j++) {
                TextInputLayout textInputLayout = new TextInputLayout(context);
                textInputLayout.setLayoutParams(params3);
                textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
                TextInputEditText editText = new TextInputEditText(context);
                editText.setLayoutParams(paramText);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setHint(txt1+"-" + (i + 1) + ","+txt2+"-" + (j + 1));
                editText.setText("0");
                editText.setSelectAllOnFocus(true);
                matrizView[i][j] = editText;
                textInputLayout.addView(editText);
                li2.addView(textInputLayout);
            }
            li.addView(li2);
        }

        parent.addView(li);
        return matrizView;

    }

    public static TextInputEditText[] genVector(Context context, ViewGroup parent, DisplayMetrics displayMetrics, int cantEstados) {
        HorizontalScrollView.LayoutParams params1 = new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout li = new LinearLayout(context);
        li.setLayoutParams(params1);
        li.setOrientation(LinearLayout.HORIZONTAL);

        TextInputEditText[] probView = new TextInputEditText[cantEstados];

        TextInputLayout.LayoutParams paramText = new TextInputLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                80 * displayMetrics.densityDpi / 160, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < cantEstados; i++) {
            TextInputLayout tl = new TextInputLayout(context);
            tl.setLayoutParams(params3);
            tl.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
            TextInputEditText editText = new TextInputEditText(context);
            editText.setLayoutParams(paramText);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setHint("P-" + (i + 1));
            probView[i] = editText;
            tl.addView(editText);
            li.addView(tl);
        }

        parent.addView(li);

        return probView;
    }

    public static double[][] getDataMatriz(TextInputEditText[][] matriz) {
        double[][] mat = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                mat[i][j] = Double.parseDouble(matriz[i][j].getText().toString());
            }
        }
        return mat;
    }

    public static double[] getDataVector(TextInputEditText[] vector) {
        double[] vec = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            vec[i] = Double.parseDouble(vector[i].getText().toString());
        }
        return vec;
    }

    public static boolean validateMatriz(TextInputEditText[][] matriz) {
        for (TextInputEditText[] textInputEditTexts : matriz) {
            for (TextInputEditText textInputEditText : textInputEditTexts) {
                if (TextUtils.isEmpty(textInputEditText.getText().toString())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validateVector(TextInputEditText[] vector) {
        for (TextInputEditText textInputEditText : vector) {
            if (TextUtils.isEmpty(textInputEditText.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateSumProb(TextInputEditText[] prob) {
        double sum = 0;
        for (TextInputEditText edit : prob) {
            String val = edit.getText().toString();
            sum += TextUtils.isEmpty(val) ? 0 : Double.parseDouble(val);
        }
        return sum == 1d;
    }

    public static boolean validateSumProbMatrizCol(TextInputEditText[][] prob) {
        double sum;
        for(int i=0;i<prob[0].length;i++){
            sum=0;
            for(int j=0;j<prob.length;j++){
                String val = prob[j][i].getText().toString();
                Log.e("PROB "+i+"-"+j,val);
                sum += TextUtils.isEmpty(val) ? 0 : Double.parseDouble(val);
            }
            Log.e("SUM",sum+"");
            if(sum!=1d)
                return false;
        }
        return true;
    }

    public static boolean validateSumProbMatrizFil(TextInputEditText[][] prob) {
        double sum;
        for(int i=0;i<prob.length;i++){
            sum=0;
            for(int j=0;j<prob[0].length;j++){
                String val = prob[i][j].getText().toString();
                sum += TextUtils.isEmpty(val) ? 0 : Double.parseDouble(val);
            }
            if(sum!=1d)
                return false;
        }
        return true;
    }


}
