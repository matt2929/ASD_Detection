/*
package com.example.matthew.austism;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;
*//*
public class PicturePicker extends Activity {
    ImageSwitcher imageSwitcher;
    ImageView alertBox;

    int index = -1;
    // to keep current Index of ImageID array
    int currentIndex = 0;
    private ImageSwitcher simpleImageSwitcher;
    Button btnNext,btnSave,btnBack,btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_picture_picker);
        // get The references of Button and ImageSwitcher

        btnNext = (Button) findViewById(R.id.ContinuePicutre);
        btnSave = (Button) findViewById(R.id.SavePictue);
        btnBack = (Button) findViewById(R.id.ReturnPicture);
        btnStart = (Button) findViewById(R.id.starttracking);
        simpleImageSwitcher = (ImageSwitcher) findViewById(R.id.gall);
        // Set the ViewFactory of the ImageSwitcher that will create ImageView object when asked
        alertBox=(ImageView) findViewById(R.id.imageView);

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
        simpleImageSwitcher.setImageResource(imageIds[currentIndex]); // set the image in ImageSwitcher

        // Declare in and out animations and load them using AnimationUtils class

        // set the animation type to ImageSwitcher
        // ClickListener for NEXT button
        // When clicked on Button ImageSwitcher will switch between Images
        // The current Image will go OUT and next Image  will come in with specified animation
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                Animation out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                simpleImageSwitcher.setInAnimation(in);
                simpleImageSwitcher.setOutAnimation(out);
                currentIndex++;
                //  Check If index reaches maximum then reset it
                if ( currentIndex == count )
                    currentIndex = 0;
                simpleImageSwitcher.setImageResource(imageIds[currentIndex]); // set the image in ImageSwitcher
                if(pictureSelection[currentIndex]){
                    alertBox.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_on));
                }else{
                    alertBox.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_off));
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                Animation out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                simpleImageSwitcher.setInAnimation(in);
                simpleImageSwitcher.setOutAnimation(out);

                currentIndex--;
                //  Check If index reaches maximum then reset it
                if ( currentIndex == -1 )
                    currentIndex = imageIds.length-1;
                simpleImageSwitcher.setImageResource(imageIds[currentIndex]); // set the image in ImageSwitcher
                if(pictureSelection[currentIndex]){
                    alertBox.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_on));
                }else{
                    alertBox.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_off));
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex!=-1) {
                    pictureSelection[currentIndex] = !pictureSelection[currentIndex];
                    if ( pictureSelection[currentIndex] ) {
                        alertBox.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_on));
                    } else {
                        alertBox.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_off));
                    }
                }
            }}
        );
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DataCollection.class);
                startActivity(i);

            }
        });
    }
}*/
