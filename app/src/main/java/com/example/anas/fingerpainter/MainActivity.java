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
    FingerPainterView myFingerPainterView;
    String BRUSH_COLOR = "-16777216"; // black RGB color-int type

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
                        "Brush shape and size were set to default", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String color = data.getStringExtra("BRUSH_COLOR");
                BRUSH_COLOR = color;
                myFingerPainterView.setColour(Integer.parseInt(color));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Brush color was set to default", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    myFingerPainterView.setCanvas(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void brushBtnOnClick(View v) {
        Intent brushActivityIntent = new Intent(getApplicationContext(), BrushActivity.class);
        brushActivityIntent.putExtra("BRUSH_COLOR", BRUSH_COLOR);
        startActivityForResult(brushActivityIntent, 0);
    }

    public void colorBtnOnClick(View v) {
        Intent colorActivityIntent = new Intent(getApplicationContext(), ColorActivity.class);
        colorActivityIntent.putExtra("BRUSH_COLOR", BRUSH_COLOR);
        startActivityForResult(colorActivityIntent, 1);
    }

    public void clearBtnOnClick(View v) {
        myFingerPainterView.clearCanvas();
    }

    public void rotateBtnOnClick(View v) {
        myFingerPainterView.rotateCanvas();
    }

    public void loadBtnOnClick(View v) {
        Intent intent = new Intent();

        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }

    public void saveBtnOnClick(View v) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Bitmap tempBitmap = myFingerPainterView.getBitmap();
        tempBitmap.compress(Bitmap.CompressFormat.PNG, 70, outStream);

        Bitmap bm = Bitmap.createBitmap(tempBitmap.getWidth(), tempBitmap.getHeight(), tempBitmap.getConfig());
        Canvas canvas = new Canvas(bm);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(tempBitmap, 0, 0, null);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "filename2.png");

        MediaStore.Images.Media.insertImage(getContentResolver(), bm,
                "Image", "Captured screenshot");

        try {
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(outStream.toByteArray());

            fo.flush();
            fo.close();
            Toast.makeText(getApplicationContext(), "Image was saved to gallery", Toast.LENGTH_SHORT);
        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        }
    }


}
