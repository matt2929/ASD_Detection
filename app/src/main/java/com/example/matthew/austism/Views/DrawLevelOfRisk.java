package com.example.matthew.austism.Views;

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

public class DrawLevelOfRisk extends RelativeLayout {
    private Paint paintGreen, paintYellow, paintRed,paintBlack;
    private float spot = .1f;
    public DrawLevelOfRisk(Context context) {
        super(context);
        setUp();
    }

    public DrawLevelOfRisk(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public DrawLevelOfRisk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paintGreen= new Paint();
        paintYellow = new Paint();
        paintRed = new Paint();
        paintYellow.setColor(Color.YELLOW);
        paintRed.setColor(Color.RED);
        paintGreen.setColor(Color.GREEN);
        paintBlack= new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(25f);
        paintBlack.setStyle(Paint.Style.STROKE);

        this.setBackgroundColor(Color.rgb(131, 131, 255));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.TRANSPARENT);
        float width = this.getWidth();
        float x1 = 0f, x2 = width / 3, x3 = width * (2f / 3f), x4 = width;
        canvas.drawRect(x1, 0, x2, this.getHeight(), paintGreen);
        canvas.drawRect(x2, 0, x3, this.getHeight(), paintYellow);
        canvas.drawRect(x3, 0, x4, this.getHeight(), paintRed);
        canvas.drawRect(spot - 20, 0, spot + 20, getHeight(), paintBlack);
    }
    public void reset(double v){
        spot=(getWidth()*(float)v);
        this.invalidate();
    }
}
