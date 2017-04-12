package com.example.matthew.austism;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Matthew on 4/11/2017.
 */

public class dottimer extends RelativeLayout {
    private Paint paintGray, paintGreen;
    int numBalls=0,ballsLit=0;
    private float radius = 25;



    public dottimer(Context context) {
        super(context);
        setUp();
    }

    public dottimer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public dottimer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paintGray = new Paint();
        paintGreen = new Paint();
        paintGray.setColor(Color.GRAY);
        paintGreen.setColor(Color.GREEN);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int[] ballsXCoordinate= new int[numBalls];
        for(int i=1;i<numBalls+1;i++){
            ballsXCoordinate[i-1]=(i*(getWidth()/(numBalls+1)));
        }
        for(int i=0;i<ballsXCoordinate.length;i++){
            if(i<=ballsLit) {
                canvas.drawCircle(ballsXCoordinate[i],getHeight()/2,radius,paintGreen);
            }else{
                canvas.drawCircle(ballsXCoordinate[i],getHeight()/2,radius,paintGray);

            }
        }
    }

    public void setBalls(int numBalls, int ballsLit) {
        this.numBalls=numBalls;
        this.ballsLit=ballsLit;
        invalidate();
    }

}
