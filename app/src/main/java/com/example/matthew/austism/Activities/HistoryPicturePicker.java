package com.example.matthew.austism.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.matthew.austism.R;
import com.example.matthew.austism.Utilities.GazeSession;
import com.example.matthew.austism.Utilities.SaveData;

import java.util.ArrayList;

public class HistoryPicturePicker extends Activity {
    ListView listView;
    SaveData saveData;
    static int picChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_picture_picker);
        listView = (ListView) findViewById(R.id.pictureChoiceList);
        listView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ArrayList<String> stringList = new ArrayList<>();
        saveData = new SaveData(getApplicationContext());
        ArrayList<GazeSession> gazeSessions = saveData.getGazeSessions(getApplicationContext());
        ArrayList<GazeSession.PictureInfo> pictureInfo = gazeSessions.get(History.choice).getPictureInfo();

        for ( int i = 0; i < pictureInfo.size(); i++ ) {
            stringList.add("" + (i + 1) + ") " + getResources().getResourceName(pictureInfo.get(i).getPicID()).substring(37));
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                picChoice = position;
                Intent i = new Intent(getApplicationContext(), HistoryPictureDetails.class);
                startActivity(i);

            }
        });
    }
}
