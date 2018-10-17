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

    int redValue = 0;
    int greenValue = 0;
    int blueValue = 0;
    String BRUSH_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        BRUSH_COLOR = getIntent().getStringExtra("BRUSH_COLOR");

        final View colorPreviewView = findViewById(R.id.colorPreviewView);
        final SeekBar redSeekbar = findViewById(R.id.redSeekbar);
        final SeekBar greenSeekbar = findViewById(R.id.greenSeekbar);
        final SeekBar blueSeekbar = findViewById(R.id.blueSeekbar);

        redSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setRedValue((i * 255) / 100);
                BRUSH_COLOR = Integer.toString(Color.rgb(redValue, greenValue, blueValue));
                Log.d("INFO", BRUSH_COLOR);
                colorPreviewView.setBackgroundColor(Integer.parseInt(BRUSH_COLOR));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        greenSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setGreenValue((i * 255) / 100);
                BRUSH_COLOR = Integer.toString(Color.rgb(redValue, greenValue, blueValue));
                colorPreviewView.setBackgroundColor(Integer.parseInt(BRUSH_COLOR));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        blueSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setBlueValue((i * 255) / 100);
                BRUSH_COLOR = Integer.toString(Color.rgb(redValue, greenValue, blueValue));
                colorPreviewView.setBackgroundColor(Integer.parseInt(BRUSH_COLOR));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    public void proceedBtnOnClick(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("BRUSH_COLOR", BRUSH_COLOR);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void setRedValue(int rval) { redValue = rval; }
    private void setGreenValue(int gval) { greenValue = gval; }
    private void setBlueValue(int bval) { blueValue = bval; }
}
