package com.example.matthew.austism.Utilities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matthew on 1/12/2017.
 */

public class GazeSession implements Serializable {
    Calendar StartTime;
    ArrayList<PictureInfo> pictureInfo = new ArrayList<>();

    public GazeSession(ArrayList<PictureInfo> pictureInfos) {
        StartTime = Calendar.getInstance();
        this.pictureInfo = pictureInfos;
    }

    public ArrayList<PictureInfo> getPictureInfo() {
        return pictureInfo;
    }


    public Calendar getStartTime() {
        return StartTime;
    }


    public void setPictureInfo(ArrayList<PictureInfo> pictureInfos) {
        pictureInfo = pictureInfos;
    }

    public void setStartTime(Calendar startTime) {
        StartTime = startTime;
    }


    public static class PictureInfo implements Serializable {
        private ArrayList<Touch> eye = new ArrayList<>();
        private int picID;

        public PictureInfo(ArrayList<Touch> allEye, int picID) {
            eye = new ArrayList<>(allEye);
            this.picID = picID;
        }

        public ArrayList<Touch> getEye() {
            return eye;
        }

        public void setEye(ArrayList<Touch> eye) {
            this.eye = eye;
        }

        public int getPicID() {
            return picID;
        }

        public void setPicID(int picID) {
            this.picID = picID;
        }

    }

    public static class Touch implements Serializable {
        private float _X, _Y;
        private Calendar _Time;

        public Touch(float x, float y) {
            _Time = Calendar.getInstance();
            _X = x;
            _Y = y;
        }

        public float get_X() {
            return _X;
        }

        public float get_Y() {
            return _Y;
        }

        public Calendar get_Time() {
            return _Time;
        }

        public void set_Time(Calendar _Time) {
            this._Time = _Time;
        }

        public void set_X(float _X) {
            this._X = _X;
        }

        public void set_Y(float _Y) {
            this._Y = _Y;
        }
    }
}

