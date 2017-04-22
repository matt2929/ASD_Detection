package com.example.matthew.austism.Utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matthew on 1/9/2017.
 */

public class SaveData implements Serializable {

    private Context context;
    private File dir;
    private File file;
    ArrayList<GazeSession.PictureInfo> pictureInfos = new ArrayList<>();
    ArrayList<GazeSession.Touch> gazeCoordinates=new ArrayList<>();
    public SaveData(Context c) {
        dir = new File(c.getFilesDir() + "/ASDSessions");
        dir.mkdirs();
        file = new File(dir, "asd.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        context = c;
    }

    public void saveSession(Context context) {
        //UserList ul = new UserList(a);
        ArrayList<GazeSession> arrayWork = getGazeSessions(context);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(out);
            // write something in the file
            //String workoutname, int[] shakelist, String workoutinfo, int grade, boolean leftHand
            GazeSession readingSession = new GazeSession(pictureInfos);
            arrayWork.add(0, readingSession);
            oout.writeObject(arrayWork);
            oout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void savePage(int picID) {
        GazeSession.PictureInfo tempPageInfo = new GazeSession.PictureInfo(gazeCoordinates,picID);
        pictureInfos.add(tempPageInfo);
        gazeCoordinates.clear();
    }

    public void saveGazePoint(float x, float y){
        GazeSession.Touch touch = new GazeSession.Touch(x,y);
        gazeCoordinates.add(touch);
    }

    public ArrayList<GazeSession> getGazeSessions(Context c) {

        ObjectInputStream ois =
                null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("HEY!","This is broken input stream");
        }


        // UserList ul = null;
        ArrayList<GazeSession> ul = null;

        try {
            if (ois == null) {
                ul = new ArrayList<GazeSession>();

            } else {
                ul = (ArrayList<GazeSession>) ois.readObject();
            }
        } catch (ClassNotFoundException e) {
            ul = new ArrayList<GazeSession>();
            e.printStackTrace();
        } catch (IOException e) {
            ul = new ArrayList<GazeSession>();
            e.printStackTrace();
        }
        if (ul != null) {
            return ul;
        } else {
            Toast.makeText(c, "null", Toast.LENGTH_LONG).show();
            return new ArrayList<GazeSession>();
        }
    }

}
