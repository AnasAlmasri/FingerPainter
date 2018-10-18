package com.example.anas.fingerpainter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // declare global variables
    FingerPainterView myFingerPainterView; // FingerPainterView object
    String BRUSH_COLOR = "-16777216"; // set brush color to black RGB color-int type
    Uri inputImageURI; // URI of image when opened from gallery

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set initialization of activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize intent to receive images from os (gallery)
        Intent osIntent = getIntent();
        String osIntentAction = osIntent.getAction();
        String osIntentType = osIntent.getType();

        // initialize finger painter class
        myFingerPainterView = findViewById(R.id.myFingerPainterView);
        myFingerPainterView.setColour(Color.parseColor("#000000"));

        // handle image when opened from gallery
        if (Intent.ACTION_SEND.equals(osIntentAction) && osIntentType != null) {
            if (osIntentType.startsWith("image/")) { setCanvasImage(osIntent); }
        }
    } // end of onCreate()

    private void setCanvasImage(Intent inIntent) {
        // method to set canvas background image to an image from gallery
        inputImageURI = inIntent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (inputImageURI != null) { myFingerPainterView.load(inputImageURI); }
    } // end of setCanvasImage()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // intent result listener method
        if (requestCode == 0) { // request channel for result from BrushActivity.java
            if(resultCode == Activity.RESULT_OK){ // if activity returned success
                // retrieve data from BrushActivity.java
                String size = data.getStringExtra("BRUSH_SIZE");
                String shape = data.getStringExtra("BRUSH_SHAPE");

                // set brush type according to retrieved data
                if (shape.equals("SQR")) { myFingerPainterView.setBrush(Paint.Cap.SQUARE); }
                else if (shape.equals("RND")){ myFingerPainterView.setBrush(Paint.Cap.ROUND); }

                // set brush width according to retrieved data
                myFingerPainterView.setBrushWidth(Integer.parseInt(size));

            } else if (resultCode == Activity.RESULT_CANCELED) { // if activity returned failure
                // inform user that shape and size were set to default
                Toast.makeText(getApplicationContext(),
                        "Brush shape and size were set to default", Toast.LENGTH_LONG).show();
            } // end inner if-else
        } else if (requestCode == 1) { // request channel for result from ColorActivity.java
            if (resultCode == Activity.RESULT_OK) { // if activity returned success
                // retrieve data from ColorActivity.java
                String color = data.getStringExtra("BRUSH_COLOR");

                // set brush color according to retrieved data
                myFingerPainterView.setColour(Integer.parseInt(color));

                BRUSH_COLOR = color; // set global variable value
            } else if (resultCode == Activity.RESULT_CANCELED) { // if activity returned failure
                // inform user that color was set to default
                Toast.makeText(getApplicationContext(),
                        "Brush color was set to default", Toast.LENGTH_LONG).show();
            } // end inner if-else
        } else if (requestCode == 2) { // request channel for image import from OS (gallery)
            // THE FOLLOWING FEW LINES OF CODE WERE RETRIEVED FROM STACKOVERFLOW
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData(); // get image URI
                try {
                    // get bitmap version of image
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // set canvas image to new bitmap
                    myFingerPainterView.setCanvas(bitmap);
                } catch (IOException e) { e.printStackTrace(); }
            } // end inner if-else
            // END OF COPIED CODE
        } // end outer if-else
    } // end of onActivityResult()

    public void brushBtnOnClick(View v) { // onClick method for brush button
        // create explicit intent to create BrushActivity
        Intent brushActivityIntent = new Intent(getApplicationContext(), BrushActivity.class);
        // attach data to the explicit intent
        brushActivityIntent.putExtra("BRUSH_COLOR", BRUSH_COLOR);
        // open channel on requestCode=0 and wait for results from activity
        startActivityForResult(brushActivityIntent, 0);
    } // end of brushBtnOnClick()

    public void colorBtnOnClick(View v) { // onClick method for color button
        // create explicit intent to create ColorActivity
        Intent colorActivityIntent = new Intent(getApplicationContext(), ColorActivity.class);
        // attach data to explicit intent
        colorActivityIntent.putExtra("BRUSH_COLOR", BRUSH_COLOR);
        // open channel on requestCode=1 and wait for results from activity
        startActivityForResult(colorActivityIntent, 1);
    } // end of colorBtnOnClick()

    public void clearBtnOnClick(View v) { // onClick method for clear button
        myFingerPainterView.clearCanvas(); // call custom method in FingerPainterView.java
    } // end of clearBtnOnClick()

    public void loadBtnOnClick(View v) { // onClick method for load button
        Intent intent = new Intent(); // create an explicit intent
        // THE FOLLOWING FEW LINES WERE RETRIEVED FROM STACKOVERFLOW
        intent.setType("image/*"); // Show only images, no videos or anything else
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
        // END OF COPIED CODE
    } // end of loadBtnOnClick()

    public void saveBtnOnClick(View v) { // onClick method for save button
        // create an OUT stream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // create temporary bitmap and copy the current bitmap into it
        Bitmap tempBitmap = myFingerPainterView.getBitmap();
        // express bitmap in PNG form
        tempBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        // create a new bitmap with the same dimensions as the current one
        Bitmap bm = Bitmap.createBitmap(tempBitmap.getWidth(), tempBitmap.getHeight(), tempBitmap.getConfig());
        // create new canvas from new bitmap
        Canvas canvas = new Canvas(bm);
        // set canvas color to white
        canvas.drawColor(Color.WHITE);
        // draw bitmap onto white canvas
        canvas.drawBitmap(tempBitmap, 0, 0, null);
        // the previous steps were merely done to fix the transparent canvas background issue

        // THE FOLLOWING FEW LINES WERE RETRIEVED FROM STACKOVERFLOW
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "newImage.png");
        MediaStore.Images.Media.insertImage(getContentResolver(), bm,
                "Image", "Captured screenshot");
        try {
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(outStream.toByteArray());
            fo.flush();
            fo.close();
            Toast.makeText(getApplicationContext(), "Image was saved to gallery",
                    Toast.LENGTH_SHORT);
        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        }
        // END OF COPIED CODE
    } // end of saveBtnOnClick()
} // end of class MainActivity.java