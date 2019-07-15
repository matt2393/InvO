package com.matt2393.invo.Vista.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.matt2393.invo.Modelo.NodoDT;
import com.matt2393.invo.R;
import com.matt2393.invo.TreeDesicion;

import java.util.ArrayList;

public class DesicionTreeFragment extends Fragment {
    public final static String TAG="DesicionTreeFragment";

    private ConstraintLayout constraintLayoutContainer;
    private LinearLayout.LayoutParams layoutParams;
    private ConstraintLayout.LayoutParams layoutParamsConstraint;

    private LinearLayout linearLayoutCanvas;
    private CoordinatorLayout coordinatorLayoutCanvas;

    private ArrayList<View> views;
    private TreeDesicion treeDesicion;

    private ArrayList<View> containers;

    private ViewCanvas viewCanvas;


    public static DesicionTreeFragment newInstance(){
        return new DesicionTreeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_desicion_tree,container,false);
        constraintLayoutContainer=view.findViewById(R.id.constraintlayout_content_desicion_tree);

        views=new ArrayList<>();
        containers=new ArrayList<>();

        treeDesicion=new TreeDesicion();

        layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        LinearLayout linearLayout=new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);

        layoutParamsConstraint=new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsConstraint.bottomToBottom=ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsConstraint.topToTop=ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsConstraint.startToStart=ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsConstraint.endToEnd=ConstraintLayout.LayoutParams.PARENT_ID;

       /* viewCanvas=new View(getActivity());
        viewCanvas.setLayoutParams(layoutParamsConstraint);
        viewCanvas.setBackgroundColor(Color.GREEN);
        constraintLayoutContainer.addView(viewCanvas);*/

        /*coordinatorLayoutCanvas=new CoordinatorLayout(getActivity());
        coordinatorLayoutCanvas.setLayoutParams(layoutParamsConstraint);
        constraintLayoutContainer.addView(coordinatorLayoutCanvas);
*/

        linearLayoutCanvas=new LinearLayout(getActivity());
        linearLayoutCanvas.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutCanvas.setLayoutParams(layoutParamsConstraint);
        linearLayoutCanvas.setBackgroundColor(Color.TRANSPARENT);
        linearLayoutCanvas.setGravity(Gravity.CENTER);

        constraintLayoutContainer.addView(linearLayoutCanvas);




        View itemIni=getActivity().getLayoutInflater()
                .inflate(R.layout.item_alternative,linearLayoutCanvas,false);

        linearLayoutCanvas.addView(itemIni);
        //linearLayoutCanvas.addView(linearLayout);
        NodoDT raiz=new NodoDT();
        raiz.setView(itemIni);
        raiz.setLevel(0);
        containers.add(itemIni);
        treeDesicion.setRaiz(raiz);
        itemIni.setOnClickListener(v -> {
            addAlternative(containers.size()>1 ? (LinearLayout) containers.get(1):null,raiz);
        });


        return view;
    }

    private void addAlternative(LinearLayout parent,NodoDT nodoDT){

        if(nodoDT.getChilds().size()==0 && parent==null) {
            parent = new LinearLayout(getContext());
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setLayoutParams(layoutParams);
            containers.add(parent);

        }
        View view=getActivity().getLayoutInflater()
                .inflate(R.layout.item_alternative,  parent,false);

        parent.addView(view);

        NodoDT child=new NodoDT();
        child.setView(view);
        child.setLevel(nodoDT.getLevel()+1);
        treeDesicion.add(nodoDT,child);


        view.setOnClickListener(v -> {
            addAlternative( containers.size()>child.getLevel()+1?(LinearLayout) containers.get(child.getLevel()+1):null,child);
        });
        views.add(view);

        viewCanvas=new ViewCanvas(getActivity());
        viewCanvas.setLayoutParams(layoutParamsConstraint);
        viewCanvas.setTreeDesicion(treeDesicion);
        linearLayoutCanvas.removeAllViews();
        constraintLayoutContainer.removeAllViews();
        constraintLayoutContainer.addView(viewCanvas);
        Log.e("Matt","creado: "+viewCanvas.getHeight()+"  "+viewCanvas.getWidth());
        for(View vi: containers)
            linearLayoutCanvas.addView(vi);
        constraintLayoutContainer.addView(linearLayoutCanvas);

      /*  Bitmap bitmap=Bitmap.createBitmap(linearLayoutCanvas.getWidth(),linearLayoutCanvas.getHeight(), Bitmap.Config.ARGB_8888);

        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10f);
        Canvas canvas=new Canvas(bitmap);


        for (View v: views) {
            Log.e("Matt","view lista");
            Log.e("Matt","view lenght: "+views.size());

            int positionStart[]=new int[2];
            int positionEnd[]=new int[2];
            start.getLocationInWindow(positionStart);
            v.getLocationInWindow(positionEnd);
            canvas.drawLine(positionStart[0],positionStart[1],positionEnd[0],positionEnd[1],paint);
        }
        Log.e("Canvas","w: "+canvas.getWidth()+"  h: "+canvas.getHeight());

        viewCanvas.draw(canvas);*/
    }

    private class ViewCanvas extends View {
        private TreeDesicion treeDesicion;

        public ViewCanvas(Context context) {
            super(context);


        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.e("Matt","lineas");

            Paint paint=new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10f);
            drawLine(treeDesicion.getRaiz(), canvas, paint);
         /*   int positionStart[]=new int[2];
            start.getLocationInWindow(positionStart);
            positionStart[0]=positionStart[0]+start.getWidth();
            positionStart[1]=positionStart[1]-start.getHeight()/2;
            for (View v: views) {
                Log.e("Matt","view lista");
                Log.e("Matt","view lenght: "+views.size());


                int positionEnd[]=new int[2];
                v.getLocationInWindow(positionEnd);
                positionEnd[0]=positionEnd[0]+v.getWidth()/2;
                positionEnd[1]=positionEnd[1]-v.getHeight()/2;
                canvas.drawLine(positionStart[0],positionStart[1],positionEnd[0],positionEnd[1],paint);
            }*/
        }

        private void drawLine(NodoDT nodoDT, Canvas canvas, Paint paint){
            int positionStart[]=new int[2];
            nodoDT.getView().getLocationInWindow(positionStart);
            positionStart[0]=positionStart[0]+nodoDT.getView().getWidth();
            positionStart[1]=positionStart[1]-nodoDT.getView().getHeight()/2;
            for (NodoDT nn: nodoDT.getChilds()){
                int positionEnd[]=new int[2];
                nn.getView().getLocationInWindow(positionEnd);
                positionEnd[0]=positionEnd[0]+nn.getView().getWidth()/2;
                positionEnd[1]=positionEnd[1]-nn.getView().getHeight()/2;
                canvas.drawLine(positionStart[0],positionStart[1],positionEnd[0],positionEnd[1],paint);

                drawLine(nn,canvas,paint);
            }

        }

        public TreeDesicion getTreeDesicion() {
            return treeDesicion;
        }

        public void setTreeDesicion(TreeDesicion treeDesicion) {
            this.treeDesicion = treeDesicion;
        }
    }
}
