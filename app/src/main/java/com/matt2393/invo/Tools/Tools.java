package com.matt2393.invo.Tools;

import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

public class Tools {

    private static DisplayMetrics displayMetrics;

    public static void init(FragmentActivity fragmentActivity){
        displayMetrics=new DisplayMetrics();
        fragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    public static int dpToPx(int dp){
        return dp * displayMetrics.densityDpi/160;
    }
}
