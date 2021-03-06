package com.example.matthew.austism.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.matthew.austism.Utilities.GazeSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Matthew on 3/19/2017.
 */

public class HeatView2 extends RelativeLayout {
    private Paint paint;
    ArrayList<GazeSession.Touch> touches = new ArrayList<>();

    private int widthTile = 15, heightTile = 15;
    private HashMap<String, Integer> trackHeat = new HashMap<>();
    private HashMap<String, Integer> trackHeatColor = new HashMap<>();
    Integer[] colors = new Integer[]{ Color.TRANSPARENT, Color.argb(200,205,237,26), Color.argb(200,237,181,26), Color.argb(200,255,130,26), Color.argb(200,255,49,26) };
    public boolean ON = false;
    float width,height;
    public HeatView2(Context context) {
        super(context);
        setUp();
    }

    public HeatView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public HeatView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAlpha(30);
        Random generator = new Random();

        for(int i=0;i<75;i++){
            Calendar calendar = Calendar.getInstance();
            touches.add(new GazeSession.Touch((float)generator.nextGaussian()*550+960,(float)generator.nextGaussian()*150+540));

        }

        this.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( ON ) {
            for ( int i = 0; i < heightTile; i++ ) {
                for ( int j = 0; j < widthTile; j++ ) {
                    int index=trackHeatColor.get("" + i + "" + j);
                    paint.setColor(colors[index]);
                    float left = (getWidth() * ((float) i / (float) widthTile));
                    float top = (getHeight() * ((float) j / (float) heightTile));
                    float right = left + (getWidth()/(float)widthTile);
                    float bottom = top + (getHeight()/ heightTile);
                    canvas.drawRect(left, top, right, bottom, paint);
                }
            }
        }
    }

    public void turnOff() {
        ON = false;
        invalidate();
    }

    public void turnOn() {
        ON = true;
        invalidate();
    }

    public void init(float width, float height) {
        touches.clear();
        Random generator = new Random();
        for(int i=0;i<75;i++){
            Calendar calendar = Calendar.getInstance();
            touches.add(new GazeSession.Touch((float)generator.nextGaussian()*550+960,(float)generator.nextGaussian()*150+540));

        }
        this.width=width;
        this.height=height;

        for ( int i = 0; i < heightTile; i++ ) {
            for ( int j = 0; j < widthTile; j++ ) {
                trackHeat.put(("" + i + "" + j), 0);
                trackHeatColor.put(("" + i + "" + j), 0);
            }
            this.invalidate();
        }

        int x = 0, y = 0;
        for ( GazeSession.Touch t : touches ) {
            if ( t.get_X() >= 0 && t.get_X() < this.width && t.get_Y() >= 0 && t.get_Y() < this.height ) {
                x = (int) (((t.get_X()) *(widthTile)) /(((float) this.width)));
                y = (int) (((t.get_Y()* (heightTile)) / (((float) this.height) )));
                trackHeat.put(("" + x + "" + y), trackHeat.get(("" + x + "" + y))+1);
            }
        }
        int maxEntry = -1;
        int minEntry = Integer.MAX_VALUE;
        for ( Map.Entry<String, Integer> entry : trackHeat.entrySet()) {
            int entryVal = entry.getValue();
            if ( maxEntry == -1 || Integer.compare(entryVal, maxEntry) > 0 ) {
                maxEntry = entryVal;
            }
            if ( Integer.compare(entryVal, maxEntry) < 0 ) {
                minEntry = entryVal;
            }
        }

        int diff = Math.abs(maxEntry - minEntry);
        int[] heatCheck = new int[5];
        if ( diff >= 4 ) {
            for ( int i = 0; i < 5; i++ ) {
                heatCheck[i] = (int) ((float) i / (float) maxEntry);
            }
        }

        for ( Map.Entry<String, Integer> entry : trackHeat.entrySet() ) {
            int value=entry.getValue();
            int a =(int) (((((float)value/ ((float) maxEntry)) * 5f))+.5);
            if(a==5){
                a=4;
            }
            trackHeatColor.put(entry.getKey(),a);
        }
    }

    public HashMap<String, Integer> getTrackHeat() {
        return trackHeat;
    }

    public ArrayList<GazeSession.Touch> getTouches() {
        return touches;
    }
}
