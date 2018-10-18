package com.example.anas.fingerpainter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class ColorActivity extends AppCompatActivity {
    // declare global variables
    int redValue = 0;
    int greenValue = 0;
    int blueValue = 0;
    String BRUSH_COLOR; // to save the color-int as a string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set initialization of activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        // set global BRUSH_COLOR to color received from MainActivity
        BRUSH_COLOR = getIntent().getStringExtra("BRUSH_COLOR");

        // declare and initialize widget controllers
        final View colorPreviewView = findViewById(R.id.colorPreviewView);
        final SeekBar redSeekbar = findViewById(R.id.redSeekbar);
        final SeekBar greenSeekbar = findViewById(R.id.greenSeekbar);
        final SeekBar blueSeekbar = findViewById(R.id.blueSeekbar);

        // start redSeekbar listener
        redSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // if (20/100)=(x/255), then x=(20*255)/100 -------- (where i=20)
                setRedValue((i * 255) / 100); // set red value according to previous example
                // update global BRUSH_COLOR according to new red value
                BRUSH_COLOR = Integer.toString(Color.rgb(redValue, greenValue, blueValue));
                // update color in rectangular box-shaped view
                colorPreviewView.setBackgroundColor(Integer.parseInt(BRUSH_COLOR));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        }); // end of redSeekbar listener

        // start greenSeekbar listener
        greenSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // if (20/100)=(x/255), then x=(20*255)/100 -------- (where i=20)
                setGreenValue((i * 255) / 100); // set green value according to previous example
                // update global BRUSH_COLOR according to new green value
                BRUSH_COLOR = Integer.toString(Color.rgb(redValue, greenValue, blueValue));
                // update color in rectangular box-shaped view
                colorPreviewView.setBackgroundColor(Integer.parseInt(BRUSH_COLOR));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        }); // end of greenSeekbar listener

        // start blueSeekbar listener
        blueSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // if (20/100)=(x/255), then x=(20*255)/100 -------- (where i=20)
                setBlueValue((i * 255) / 100); // set blue value according to previous example
                // update global BRUSH_COLOR according to new blue value
                BRUSH_COLOR = Integer.toString(Color.rgb(redValue, greenValue, blueValue));
                // update color in rectangular box-shaped view
                colorPreviewView.setBackgroundColor(Integer.parseInt(BRUSH_COLOR));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        }); // end of blueSeekbar listener
    } // end of onCreate()

    public void proceedBtnOnClick(View v) { // onClick method for proceed button
        // create explicit intent to return result to MainActivity
        Intent returnIntent = new Intent();
        // attach brush color to intent
        returnIntent.putExtra("BRUSH_COLOR", BRUSH_COLOR);
        // send result on requestCode=1
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    } // end of proceedBtnOnClick()

    // color value setter methods
    private void setRedValue(int rval) { redValue = rval; }
    private void setGreenValue(int gval) { greenValue = gval; }
    private void setBlueValue(int bval) { blueValue = bval; }
} // end of class ColorActivity.java