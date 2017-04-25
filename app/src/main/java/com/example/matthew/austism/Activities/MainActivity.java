package com.example.matthew.austism.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.matthew.austism.R;

public class MainActivity extends AppCompatActivity {
    Button button, historyButt;
    public static Toast transitionToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(0, 0);
        transitionToast = new Toast(getApplicationContext());
        setContentView(R.layout.activity_main);
        historyButt = (Button) findViewById(R.id.goToHistory);
        button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToast = Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_LONG);
                transitionToast.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getApplicationContext(), CameraPreviewActivity.class);
                        startActivity(i);
                    }
                }, 10);

            }
        });
        historyButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        transitionToast = Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_LONG);
                        transitionToast.show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(getApplicationContext(), History.class);
                                startActivity(i);
                            }
                        }, 10);

                    }
                });
            }
        });
    }
}
