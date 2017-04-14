package com.example.matthew.austism;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    ListView listView;
    ImageView imageView;
    HeatView heatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.listOfPics);
        imageView= (ImageView) findViewById(R.id.picViewer);
        heatView = (HeatView) findViewById(R.id.heatView);

        ArrayList<String> stringList = new ArrayList<>();

        final int pictureIds[] = new int[]{
                R.drawable.threekids,
                R.drawable.animalsbackgroundpreview5,
                R.drawable.twogirls,
                R.drawable.b13,
                R.drawable.pexels_photo_58997,
                R.drawable.cutedeer,
                R.drawable.twoboys,
        };
        for(int i=0;i<pictureIds.length;i++){
            int a=1;
            stringList.add((""+getResources().getResourceName(pictureIds[i])).substring(37));
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource(pictureIds[position]);
                heatView.turnOn();
                heatView.init(1920,1080);
            }
        });
    }
}
