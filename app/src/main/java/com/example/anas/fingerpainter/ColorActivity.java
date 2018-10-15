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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        final View colorPreviewView = findViewById(R.id.colorPreviewView);
        final SeekBar redSeekbar = findViewById(R.id.redSeekbar);
        final SeekBar greenSeekbar = findViewById(R.id.greenSeekbar);
        final SeekBar blueSeekbar = findViewById(R.id.blueSeekbar);

        redSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setRedValue((i * 255) / 100);
                colorPreviewView.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));
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
                colorPreviewView.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));
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
                colorPreviewView.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    public void proceedBtnOnClick(View v) {
        Intent returnIntent = new Intent();

        int color = Color.rgb(redValue, greenValue, blueValue);
        String tempStr = Integer.toString(color);
        returnIntent.putExtra("BRUSH_COLOR", tempStr);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void setRedValue(int rval) { redValue = rval; }
    private void setGreenValue(int gval) { greenValue = gval; }
    private void setBlueValue(int bval) { blueValue = bval; }
}
