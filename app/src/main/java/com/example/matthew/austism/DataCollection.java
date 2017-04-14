package com.example.matthew.austism;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.qualcomm.snapdragon.sdk.face.FaceData;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing;

import java.util.Calendar;
import java.util.EnumSet;


public class DataCollection extends Activity implements Camera.PreviewCallback {

    private TextView smileText;
    Button btnStart;
    dottimer _dottimer;
    int pictureIds[] = new int[]{
            R.drawable.threekids,
            R.drawable.animalsbackgroundpreview5,
            R.drawable.twogirls,
            R.drawable.b13,
            R.drawable.pexels_photo_58997,

            R.drawable.cutedeer,
            R.drawable.twoboys,
           };

    static boolean[] selectedPicture = new boolean[]{

            true,true,true,true,true,true,true};

    private ImageSwitcher simpleImageSwitcher;
    int currentIndex = -1;
    int delay = 5000;
    boolean pause = true;
    Long timerStart = System.currentTimeMillis();
    Calendar calendar;
    Handler handler2;
    /////////////////////////////////////////camera
    ///////////////////
    Camera cameraObj;
    FrameLayout preview;

    FacialProcessing faceProc;
    FaceData[] faceArray = null;// Array in which all the face data values will be returned for each face detected.
    View myView;
    //calibration////
    static Calibration9Point calibration9Point = CameraPreviewActivity.calibration9Point;
    NinePointCalibrationView ninePointCalibration;
    boolean calibrationAvailable = false;
    ////////////////
    boolean canRecord = false;
    Canvas canvas = new Canvas();
    private Clock2 clock2;
    private Handler handler;
    private CameraSurfacePreview mPreview;
    private DrawView drawView;
    private final int FRONT_CAMERA_INDEX = 1;
    private final int BACK_CAMERA_INDEX = 0;
    // boolean clicked = false;
    boolean fpFeatureSupported = false;
    static boolean cameraSwitch = false;    // Boolean to check if the camera is switched to back camera or no.
    boolean landScapeMode = false;      // Boolean to check if the phone orientation is in landscape mode or portrait mode.

    int cameraIndex;// Integer to keep track of which camera is open.
    int smileValue = 0;
    int leftEyeBlink = 0;
    int rightEyeBlink = 0;
    int faceRollValue = 0;
    private DrawBallView drawBallView;
    int pitch = 0;
    int yaw = 0;
    int horizontalGaze = 0;
    int verticalGaze = 0;
    PointF gazePointValue = null;
    private final String TAG = "CameraPreviewActivity";
    // TextView Variables
    int surfaceWidth = 0;
    int surfaceHeight = 0;
    static MovingAverage movingAverageX, movingAverageY;
    OrientationEventListener orientationEventListener;
    int deviceOrientation;
    int presentOrientation;
    float rounded;
    Display display;
    int displayAngle;
    private TextView coordinateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        smileText=(TextView) findViewById(R.id.smileText);
        smileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ReportAnalysis.class);
                startActivity(i);
            }
        });
        _dottimer = (dottimer) findViewById(R.id.timerData);
        handler2 = new Handler();
        btnStart = (Button) findViewById(R.id.startDataCollection);
        myView = new View(DataCollection.this);
        preview = (FrameLayout) findViewById(R.id.facialPreview);
        fpFeatureSupported = FacialProcessing
                .isFeatureSupported(FacialProcessing.FEATURE_LIST.FEATURE_FACIAL_PROCESSING);

        if ( fpFeatureSupported && faceProc == null ) {
            Log.e("TAG", "Feature is supported");
            faceProc = FacialProcessing.getInstance();  // Calling the Facial Processing Constructor.
            faceProc.setProcessingMode(FacialProcessing.FP_MODES.FP_MODE_VIDEO);
        } else {
            Log.e("TAG", "Feature is NOT supported");
            return;
        }
        cameraIndex = Camera.getNumberOfCameras() - 1;// Start with front Camera
        try {
            cameraObj = Camera.open(cameraIndex); // attempt to get a Camera instance
        } catch (Exception e) {
            Log.d("TAG", "Camera Does Not exist");// Camera is not available (in use or does not exist)
        }
        // Change the sizes according to phone's compatibility.
        mPreview = new CameraSurfacePreview(DataCollection.this, cameraObj, faceProc);
        preview = (FrameLayout) findViewById(R.id.facialPreview);
        preview.removeView(mPreview);
        preview.addView(mPreview);
        movingAverageX = CameraPreviewActivity.movingAverageX;
        movingAverageY = CameraPreviewActivity.movingAverageY;
        cameraObj.setPreviewCallback(DataCollection.this);
        orientationListener();
        display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Camera.FaceDetectionListener faceDetectionListener = new Camera.FaceDetectionListener() {

            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                Log.e(TAG, "Faces Detected through FaceDetectionListener = " + faces.length);
            }
        };
//////

        drawBallView = (DrawBallView) findViewById(R.id.drawBallView);
        clock2 = new Clock2(handler2);
        simpleImageSwitcher = (ImageSwitcher) findViewById(R.id.collectionImageSwitcher);
        simpleImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub

                // Create a new ImageView and set it's properties
                ImageView imageView = new ImageView(getApplicationContext());
                // set Scale type of ImageView to Fit Center
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                // set the Height And Width of ImageView To FIll PARENT
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                return imageView;
            }
        });
        simpleImageSwitcher.setImageResource(android.R.drawable.screen_background_dark_transparent);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setVisibility(View.GONE);
                clock2.run();
                timerStart = System.currentTimeMillis();
            }
        });
    }

    private void orientationListener() {
        orientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                deviceOrientation = orientation;
            }
        };

        if ( orientationEventListener.canDetectOrientation() ) {
            orientationEventListener.enable();
        }

        presentOrientation = 90 * (deviceOrientation / 360) % 360;
    }

    public void setUI(int numFaces, int smileValue, int leftEyeBlink, int rightEyeBlink, int faceRollValue,
                      int faceYawValue, int facePitchValue, PointF gazePointValue, int horizontalGazeAngle, int verticalGazeAngle) {
        smileText.setText(""+smileValue);
        if ( numFaces > 0 ) {
            canRecord = true;
        } else {
            canRecord = false;

        }
        if ( gazePointValue != null && canRecord ) {
            double x = Math.round(gazePointValue.x * 100.0) / 100.0;// Rounding the gaze point value.
            double y = Math.round(gazePointValue.y * 100.0) / 100.0;
            movingAverageX.update(x);
            movingAverageY.update(y);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( cameraObj != null ) {
            stopCamera();
        }

        if ( !cameraSwitch )
            startCamera(FRONT_CAMERA_INDEX);
        else
            startCamera(BACK_CAMERA_INDEX);
    }

    /*
     * This is a function to stop the camera preview. Release the appropriate objects for later use.
     */
    public void stopCamera() {
        if ( cameraObj != null ) {
            cameraObj.stopPreview();
            cameraObj.setPreviewCallback(null);
            preview.removeView(mPreview);
            cameraObj.release();
            faceProc.release();
            faceProc = null;
        }

        cameraObj = null;
    }

    /*
     * This is a function to start the camera preview. Call the appropriate constructors and objects.
     * @param-cameraIndex: Will specify which camera (front/back) to start.
     */
    public void startCamera(int cameraIndex) {

        if ( fpFeatureSupported && faceProc == null ) {

            Log.e("TAG", "Feature is supported");
            faceProc = FacialProcessing.getInstance();// Calling the Facial Processing Constructor.
        }

        try {
            cameraObj = Camera.open(cameraIndex);// attempt to get a Camera instance
        } catch (Exception e) {
            Log.d("TAG", "Camera Does Not exist");// Camera is not available (in use or does not exist)
        }

        mPreview = new CameraSurfacePreview(DataCollection.this, cameraObj, faceProc);
        preview.removeView(mPreview);
        preview = (FrameLayout) findViewById(R.id.facialPreview);
        preview.addView(mPreview);
        cameraObj.setPreviewCallback(DataCollection.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    /*
     * Detecting the face according to the new Snapdragon SDK. Face detection will now take place in this function.
     * 1) Set the Frame
     * 2) Detect the Number of faces.
     * 3) If(numFaces > 0) then do the necessary processing.
     */
    @Override
    public void onPreviewFrame(byte[] data, Camera arg1) {

        presentOrientation = (90 * Math.round(deviceOrientation / 90)) % 360 - 90;
        int dRotation = display.getRotation()-90;
        FacialProcessing.PREVIEW_ROTATION_ANGLE angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_0;

        switch (dRotation) {
            case 0:
                displayAngle = 90;
                angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_90;
                break;

            case 1:
                displayAngle = 0;
                angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_0;
                break;

            case 2:
                // This case is never reached.
                break;

            case 3:
                displayAngle = 180;
                angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_180;
                break;
        }

        if ( faceProc == null ) {
            faceProc = FacialProcessing.getInstance();
        }

        Camera.Parameters params = cameraObj.getParameters();
        Camera.Size previewSize = params.getPreviewSize();
        surfaceWidth = mPreview.getWidth();
        surfaceHeight = mPreview.getHeight();

        // Landscape mode - front camera
        if ( this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !cameraSwitch ) {
            faceProc.setFrame(data, previewSize.width, previewSize.height, true, angleEnum);
            cameraObj.setDisplayOrientation(displayAngle);
            landScapeMode = true;
        }
        // landscape mode - back camera
        else if ( this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && cameraSwitch ) {
            faceProc.setFrame(data, previewSize.width, previewSize.height, false, angleEnum);
            cameraObj.setDisplayOrientation(displayAngle);
            landScapeMode = true;
        }
        // Portrait mode - front camera
        else if ( this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                && !cameraSwitch ) {
            faceProc.setFrame(data, previewSize.width, previewSize.height, true, angleEnum);
            cameraObj.setDisplayOrientation(displayAngle);
            landScapeMode = false;
        }
        // Portrait mode - back camera
        else {
            faceProc.setFrame(data, previewSize.width, previewSize.height, false, angleEnum);
            cameraObj.setDisplayOrientation(displayAngle);
            landScapeMode = false;
        }

        int numFaces = faceProc.getNumFaces();

        if ( numFaces == 0 ) {
            Log.d("TAG", "No Face Detected");
            if ( drawView != null ) {
                preview.removeView(drawView);

                drawView = new DrawView(this, null, false, 0, 0, null, landScapeMode);
                preview.addView(drawView);
            }
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            setUI(0, 0, 0, 0, 0, 0, 0, null, 0, 0);
        } else {

            Log.d("TAG", "Face Detected");
            faceArray = faceProc.getFaceData(EnumSet.of(FacialProcessing.FP_DATA.FACE_RECT,
                    FacialProcessing.FP_DATA.FACE_COORDINATES, FacialProcessing.FP_DATA.FACE_CONTOUR,
                    FacialProcessing.FP_DATA.FACE_SMILE, FacialProcessing.FP_DATA.FACE_ORIENTATION,
                    FacialProcessing.FP_DATA.FACE_BLINK, FacialProcessing.FP_DATA.FACE_GAZE));
            // faceArray = faceProc.getFaceData(); // Calling getFaceData() alone will give you all facial data except the
            // face
            // contour. Face Contour might be a heavy operation, it is recommended that you use it only when you need it.
            if ( faceArray == null ) {
                Log.e("TAG", "Face array is null");
            } else {
                if ( faceArray[0].leftEyeObj == null ) {
                    Log.e(TAG, "Eye Object NULL");
                } else {
                    Log.e(TAG, "Eye Object not NULL");
                }

                faceProc.normalizeCoordinates(surfaceWidth, surfaceHeight);
                preview.removeView(drawView);// Remove the previously created view to avoid unnecessary stacking of Views.
                drawView = new DrawView(this, faceArray, true, surfaceWidth, surfaceHeight, cameraObj, landScapeMode);
                preview.addView(drawView);

                for ( int j = 0; j < numFaces; j++ ) {
                    smileValue = faceArray[j].getSmileValue();
                    leftEyeBlink = faceArray[j].getLeftEyeBlink();
                    rightEyeBlink = faceArray[j].getRightEyeBlink();
                    faceRollValue = faceArray[j].getRoll();
                    gazePointValue = faceArray[j].getEyeGazePoint();
                    pitch = faceArray[j].getPitch();
                    yaw = faceArray[j].getYaw();
                    horizontalGaze = faceArray[j].getEyeHorizontalGazeAngle();
                    verticalGaze = faceArray[j].getEyeVerticalGazeAngle();
                }
                setUI(numFaces, smileValue, leftEyeBlink, rightEyeBlink, faceRollValue, yaw, pitch, gazePointValue,
                        horizontalGaze, verticalGaze);
            }
        }
    }


    class Clock2 implements Runnable {
        private Handler handler;
        DisplayMetrics metrics;

        public Clock2(Handler handler) {
            this.handler = handler;
            metrics = getApplicationContext().getResources().getDisplayMetrics();
        }

        public void run() {
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
           if ( currentIndex < pictureIds.length ) {
                if ( Math.abs(timerStart - System.currentTimeMillis()) > delay ) {
                    if (pause) {
                        timerStart = System.currentTimeMillis();
                        currentIndex++;
                        while ( currentIndex != pictureIds.length && !selectedPicture[currentIndex] )
                            currentIndex++;
                        if ( currentIndex == pictureIds.length ) {
                            simpleImageSwitcher.setImageResource(android.R.drawable.screen_background_dark_transparent);
                        } else {
                            simpleImageSwitcher.setImageResource(pictureIds[currentIndex]);
                        }
                        pause=!pause;

                    }else{
                        pause=!pause;
                        simpleImageSwitcher.setImageResource(android.R.drawable.screen_background_dark_transparent);
                    }
                    drawBallView.reset();
                }
            }
            handler.postDelayed(this, 55);
            double[] coordinates = calibration9Point.getXYPoportional(movingAverageX.getCurrentNeg(), movingAverageY.getCurrentNeg(), width, height);
            _dottimer.setBalls(5,(int)((5-Math.abs(timerStart - (System.currentTimeMillis()))/1000)-.5));
            drawBallView.setBallPosition(coordinates[0], coordinates[1]);
        }
    }
}

