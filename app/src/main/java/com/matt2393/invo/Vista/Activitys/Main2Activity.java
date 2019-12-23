package com.matt2393.invo.Vista.Activitys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Views vv=new Views(this);
        setContentView(vv);

      /*  Canvas canvas=new Canvas();
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10f);
        canvas.drawLine(0,200,200,0,paint);
        vv.draw(canvas);*/
    }

    private class Views extends View {

        public Views(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10f);
            canvas.drawLine(0,200,200,0,paint);

        }
    }
}
