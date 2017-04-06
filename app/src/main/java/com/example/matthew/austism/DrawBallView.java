package com.example.matthew.austism;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/16/2017.
 */

public class DrawBallView extends RelativeLayout {
    private Paint paintYellow, paintRed;

    private float positionX = -45, positionY = -45;
    private float radius = 25;
    ArrayList<Double> historyX = new ArrayList<>();
    ArrayList<Double> historyY = new ArrayList<>();
    private int lengthTail = 100;
    public DrawBallView(Context context) {
        super(context);
        setUp();
    }

    public DrawBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public DrawBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paintYellow = new Paint();
        paintRed = new Paint();
        paintRed.setStyle(Paint.Style.STROKE);
        paintYellow.setColor(Color.RED);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(10f);
        this.setBackgroundColor(Color.rgb(131, 131, 255));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            setBackgroundColor(Color.TRANSPARENT);
            Log.e("draw","x"+positionX+"y"+positionY);
            canvas.drawCircle(positionX, positionY, radius, paintYellow);
        int alphaScale = 255-100;
        for(int i=0;i<historyX.size()-1;i++){
            paintRed.setAlpha(alphaScale);
            double lastX = historyX.get(i);
            double currentX = historyX.get(i+1);
            double lastY = historyY.get(i);
            double currentY = historyY.get(i+1);
            canvas.drawCircle((float)lastX,(float)lastY,25,paintRed);
          //  canvas.drawOval((float)lastX,(float)lastY,(float)currentX,(float)currentY,paintRed);
            alphaScale++;
        }
    }
    public void reset(){
        historyX.clear();
        historyY.clear();
    }
    public void setBallPosition(double x, double y) {
        radius=60;
        positionX = (float) x;
        positionY = (float) y;
        if(historyX.size()==lengthTail){
            historyX.remove(0);
            historyY.remove(0);
        }
        historyX.add(x);
        historyY.add(y);
        invalidate();
    }

}
