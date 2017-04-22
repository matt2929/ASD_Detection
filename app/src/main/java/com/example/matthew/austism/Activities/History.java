package com.example.matthew.austism.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matthew.austism.R;
import com.example.matthew.austism.Utilities.GazeSession;
import com.example.matthew.austism.Utilities.SaveData;

import java.util.ArrayList;

public class History extends Activity {
    ListView listView;
    SaveData saveData;
    static  int choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.listOfPics);

        ArrayList<String> stringList = new ArrayList<>();
        saveData = new SaveData(getApplicationContext());
        ArrayList<GazeSession> gazeSessions= saveData.getGazeSessions(getApplicationContext());

        for(int i=0;i<gazeSessions.size();i++){
            stringList.add(""+(i+1)+") "+gazeSessions.get(i).getStartTime().getTime().toString());
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        listView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choice=position;
                Intent i = new Intent(getApplicationContext(), HistoryPicturePicker.class);
                startActivity(i);
            }
        });
    }
}
