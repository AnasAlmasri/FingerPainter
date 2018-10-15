package com.example.anas.fingerpainter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FingerPainterView myFingerPainterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFingerPainterView = findViewById(R.id.myFingerPainterView);
        myFingerPainterView.setColour(Color.parseColor("#000000"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                String size = data.getStringExtra("BRUSH_SIZE");
                String shape = data.getStringExtra("BRUSH_SHAPE");

                if (shape.equals("SQR")) {
                    myFingerPainterView.setBrush(Paint.Cap.SQUARE);
                } else {
                    myFingerPainterView.setBrush(Paint.Cap.ROUND);
                }

                myFingerPainterView.setBrushWidth(Integer.parseInt(size));

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Failed to receive data from Activity", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String color = data.getStringExtra("BRUSH_COLOR");
                myFingerPainterView.setColour(Integer.parseInt(color));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Failed to receive data from Activity", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void brushBtnOnClick(View v) {
        Intent brushActivityIntent = new Intent(getApplicationContext(), BrushActivity.class);
        startActivityForResult(brushActivityIntent, 0);
    }

    public void colorBtnOnClick(View v) {
        Intent colorActivityIntent = new Intent(getApplicationContext(), ColorActivity.class);
        startActivityForResult(colorActivityIntent, 1);
    }
}
