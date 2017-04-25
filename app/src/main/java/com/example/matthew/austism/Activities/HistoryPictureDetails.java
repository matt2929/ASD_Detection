package com.example.matthew.austism.Activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.matthew.austism.R;
import com.example.matthew.austism.Utilities.GazeSession;
import com.example.matthew.austism.Utilities.SaveData;
import com.example.matthew.austism.Views.DrawPointTransparent;
import com.example.matthew.austism.Views.HeatView;
import com.example.matthew.austism.Views.HeatView2;

import java.util.ArrayList;

public class HistoryPictureDetails extends Activity {
    DrawPointTransparent drawPointTransparent;
    Button start, stop, restart;
    TextView textView;
    ProgressBar progressBar;
    boolean running = false;
    int position = 0;
    ArrayList<GazeSession> gazeSessions;
    DrawGazeRepeat drawGazeRepeat;
    SeekBar seekBar;
    private CheckBox checkBox;
    private HeatView2 heatView;
    int delay = 55;
    GazeSession.PictureInfo pictureInfo;
    int pictureID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        SaveData saveData = new SaveData(getApplicationContext());
        drawPointTransparent = (DrawPointTransparent) findViewById(R.id.DrawPointEye);
       // gazeSessions = saveData.getGazeSessions(getApplicationContext());
       // pictureInfo = gazeSessions.get(History.choice).getPictureInfo().get(HistoryPicturePicker.picChoice);
       // pictureID = pictureInfo.getPicID();
        ImageView imageView = (ImageView) findViewById(R.id.showwhereeyeare);
        checkBox = (CheckBox) findViewById(R.id.gazeCheckBox);
        start = (Button) findViewById(R.id.gazeStart);
        stop = (Button) findViewById(R.id.gazeStop);
        restart = (Button) findViewById(R.id.gazeRestart);
        textView = (TextView) findViewById(R.id.gazeText);
        checkBox = (CheckBox) findViewById(R.id.gazeCheckBox);
        heatView = (HeatView2) findViewById(R.id.HeatMap);
        progressBar = (ProgressBar) findViewById(R.id.gazeProgress);
        progressBar.setMax(100);
        seekBar = (SeekBar) findViewById(R.id.seekBarEye);
        Handler handler = new Handler();
        drawGazeRepeat = new DrawGazeRepeat(handler);
        drawGazeRepeat.run();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = true;

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 0;
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ArrayList<Integer> pictureInfo = new ArrayList<Integer>();
        pictureInfo.add(R.drawable.cat);
        pictureInfo.add(R.drawable.two_boys);
        pictureInfo.add(R.drawable.three_girls);
        pictureInfo.add(R.drawable.hummingbird);
        pictureInfo.add(R.drawable.dog);
        pictureInfo.add(R.drawable.deer);
        int choice =pictureInfo.get(HistoryPicturePicker.picChoice);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                delay = (seekBar.getMax() - seekBar.getProgress()) *5 ;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( b ) {
                    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    heatView.init(width, height);
                    heatView.turnOn();
                } else {
                    heatView.turnOff();
                }
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(choice);
    }

    public void drawPointTouch(float x, float y) {

        drawPointTransparent.setPoint(x, y);
    }

    public void updateView() {
        Log.e("data: ", " " + position);
        GazeSession.Touch touch = heatView.getTouches().get(position);
        drawPointTouch(touch.get_X(), touch.get_Y());
        float progress = (float) position / (float) heatView.getTouches().size();
        textView.setText("Coordinate:[" + touch.get_X() + "," + touch.get_Y() + "]");
        progressBar.setProgress((int) (progress * 100));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        heatView.init(width, height);
    }

    class DrawGazeRepeat implements Runnable {
        private Handler handler;
        public DrawGazeRepeat(Handler handler) {
            this.handler = handler;
        }
        public void run() {
            if ( running ) {
                if ( position + 1 == heatView.getTouches().size() ) {
                } else {
                    position++;
                }
                updateView();
            }
            handler.postDelayed(this, delay);
        }
    }
}
