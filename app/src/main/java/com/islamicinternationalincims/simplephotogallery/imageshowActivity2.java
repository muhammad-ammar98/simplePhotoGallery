package com.islamicinternationalincims.simplephotogallery;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class imageshowActivity2 extends AppCompatActivity {
    String imagePath;
    private ImageView imageView,backBtn, shareBtn, menuBtn;
    private ScaleGestureDetector scaleGestureDetector;

    // on below line we are defining our scale factor.
    private float mScaleFactor = 1.0f;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow2);




        imageView = findViewById(R.id.imageID);
        backBtn=findViewById(R.id.floatingActionButton2);
        shareBtn=findViewById(R.id.floatingActionButton);
        menuBtn=findViewById(R.id.floatingActionButton3);

        // get the image path from MainActivity
        imagePath = getIntent().getStringExtra("image");



        //menu button for open bottom sheet navigation
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetFragment bottomSheetFragment = new bottomsheetFragment();

                Bundle bundle = new Bundle();

                bundle.putString("message", imagePath );

                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(imagePath);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
               // shareIntent.putExtra(Intent.EXTRA_TEXT, quoteModal.getText());
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }
        });
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        File imgFile = new File(imagePath);

        // if the file exists then we are loading that image in our image view.
        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.photo).into(imageView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {


            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // on below line we are setting
            // scale x and scale y to our image view.
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}