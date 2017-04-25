package com.example.matthew.austism.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matthew.austism.R;
import com.example.matthew.austism.Views.DrawLevelOfRisk;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ReportAnalysis extends AppCompatActivity {
    DrawLevelOfRisk drawLevelOfRisk;
    TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
    double risk = 0;
    TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.transitionToast.cancel();
        setContentView(R.layout.activity_report_analysis);
        textView = (TextView) findViewById(R.id.riskfactortext);
        textView2 = (TextView) findViewById(R.id.textView4);
        textView3 = (TextView) findViewById(R.id.textView5);
        textView4 = (TextView) findViewById(R.id.textView6);
        textView5 = (TextView) findViewById(R.id.textView2);
        textView6 = (TextView) findViewById(R.id.textView8);
        textView7 = (TextView) findViewById(R.id.textView9);
        textView8 = (TextView) findViewById(R.id.textView10);
        textView9 = (TextView) findViewById(R.id.textView11);
        textView10 = (TextView) findViewById(R.id.textView12);

        textViews = new TextView[]{ textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10 };
        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, 6),
                new DataPoint(2, 2),
                new DataPoint(3, 9),
                new DataPoint(4, 5),
        });

        series.setSpacing(55);
        series.setValuesOnTopSize(45f);
        graph.setTitleTextSize(45f);
        graph.addSeries(series);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);


        graph.getViewport().setMinY(0);

        graph.getViewport().setMinX(0);

        graph.getViewport().setMaxX(6);
        graph.getGridLabelRenderer().setTextSize(40f);
        graph.getGridLabelRenderer().reloadStyles();
        graph.getViewport().setMaxY(10);
        int count = 0;
        final Button button1, button2, button3, button4;
        button1 = (Button) findViewById(R.id.colorSet1);

        button2 = (Button) findViewById(R.id.colorSet2);

        button3 = (Button) findViewById(R.id.colorSet3);

        button4 = (Button) findViewById(R.id.colorSet4);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if ( data.getX() == 1 ) {
                    button1.setBackgroundColor(Color.rgb((int) data.getX() * 144 / 4, (int) Math.abs(data.getY() * 44 / 6), 100));
                } else if ( data.getX() == 2 ) {
                    button2.setBackgroundColor(Color.rgb((int) data.getX() * 144 / 4, (int) Math.abs(data.getY() * 44 / 6), 100));
                } else if ( data.getX() == 3 ) {
                    button3.setBackgroundColor(Color.rgb((int) data.getX() * 144 / 4, (int) Math.abs(data.getY() * 44 / 6), 100));
                } else if ( data.getX() == 4 ) {
                    button4.setBackgroundColor(Color.rgb((int) data.getX() * 144 / 4, (int) Math.abs(data.getY() * 44 / 6), 100));
                }
                return Color.rgb((int) data.getX() * 144 / 4, (int) Math.abs(data.getY() * 44 / 6), 100);
            }
        });

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risk += .1;
                drawLevelOfRisk.reset(risk);
                textView.setText("Risk Rate: " + (risk * 100) + "%");

            }
        });
        drawLevelOfRisk = (DrawLevelOfRisk) findViewById(R.id.riskreport);
        drawLevelOfRisk.reset(.1);
        drawLevelOfRisk.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1 = 0, x2 = 0, y1 = 0, y2 = 1;
        if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
            if ( event.getPointerCount() == 2 ) {
                x1 = event.getX(0);
                x2 = event.getX(1);
                y1 = event.getY(0);
                y2 = event.getY(1);

                double value = Math.pow(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2), .5) / 20;
                for ( TextView t : textViews ) {

                    t.setTextSize((float) value);

                }
            }
        }
        return super.onTouchEvent(event);
    }
}
