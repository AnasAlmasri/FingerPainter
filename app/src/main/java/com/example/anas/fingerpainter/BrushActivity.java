package com.example.anas.fingerpainter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class BrushActivity extends AppCompatActivity {

    String BRUSH_COLOR;
    private TextView brushShapeTextView;
    private ImageView brushImageView;
    private ImageView provSquareBrushImageView;
    private ImageView provRoundBrushImageView;
    private TextView brushSizeTextView;
    private SeekBar brushSizeSeekBar;
    private TextView orBrushSizeTextView;
    private EditText brushSizePixelEditText;
    private Button proceedBtn;
    private int previousProgress = 75;
    private int BRUSH_DIMENSION = 350;
    private String BRUSH_SHAPE = "SQR"; // SQR for square, RND for round

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        BRUSH_COLOR = getIntent().getStringExtra("BRUSH_COLOR");

        brushShapeTextView = findViewById(R.id.brushShapeTextView);
        brushImageView = findViewById(R.id.brushImageView);
        provSquareBrushImageView = findViewById(R.id.provSquareBrushImageView);
        provRoundBrushImageView = findViewById(R.id.provRoundBrushImageView);
        brushSizeTextView = findViewById(R.id.brushSizeTextView);
        brushSizeSeekBar = findViewById(R.id.brushSizeSeekBar);
        orBrushSizeTextView = findViewById(R.id.orBrushSizeTextView);
        brushSizePixelEditText = findViewById(R.id.brushSizePixelEditText);
        proceedBtn = findViewById(R.id.proceedBtn);

        brushImageView.setVisibility(View.INVISIBLE);
        brushSizeSeekBar.setProgress(75);

        hideSizeOptions();

        brushImageView.setColorFilter(Integer.parseInt(BRUSH_COLOR));
        provRoundBrushImageView.setColorFilter(Integer.parseInt(BRUSH_COLOR));
        provSquareBrushImageView.setColorFilter(Integer.parseInt(BRUSH_COLOR));

        brushSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentProgress, boolean b) {
                int diff = currentProgress - previousProgress;
                scaleImage(diff);
                previousProgress = currentProgress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        brushSizePixelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                brushSizeEditTextOnTextChanged();
            }
        });
    }

    public void squareBrushOnClick(View v) {
        hideShapeOptions();
        showSizeOptions();
        BRUSH_SHAPE = "SQR";
        brushImageView.setImageResource(R.drawable.square_brush);
        brushImageView.setVisibility(View.VISIBLE);
    }

    public void roundBrushOnClick(View v) {
        hideShapeOptions();
        showSizeOptions();
        BRUSH_SHAPE = "RND";
        brushImageView.setImageResource(R.drawable.round_brush);
        brushImageView.setVisibility(View.VISIBLE);
    }

    public void brushSizeEditTextOnTextChanged() {
        if (brushSizePixelEditText.getText().length() != 0) {
            int pixelInput = Integer.valueOf(brushSizePixelEditText.getText().toString());

            if (pixelInput > 400) {
                pixelInput = 400;
            } else if (pixelInput < 100) {
                pixelInput = 100;
            }
            android.view.ViewGroup.LayoutParams layoutParams = brushImageView.getLayoutParams();
            layoutParams.width = pixelInput;
            layoutParams.height = pixelInput;
            brushImageView.setLayoutParams(layoutParams);
            BRUSH_DIMENSION = pixelInput;
        }
    }

    public void proceedBtnOnClick(View v) {
        if (brushSizePixelEditText.getText().length() != 0) {
            brushSizeEditTextOnTextChanged();
        }
        String tempStr = Integer.toString(BRUSH_DIMENSION);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("BRUSH_SIZE", tempStr);
        returnIntent.putExtra("BRUSH_SHAPE", BRUSH_SHAPE);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void hideSizeOptions() {
        brushSizeTextView.setVisibility(View.INVISIBLE);
        brushSizeSeekBar.setVisibility(View.INVISIBLE);
        orBrushSizeTextView.setVisibility(View.INVISIBLE);
        brushSizePixelEditText.setVisibility(View.INVISIBLE);
        proceedBtn.setVisibility(View.INVISIBLE);
    }

    private void showSizeOptions() {
        brushSizeTextView.setVisibility(View.VISIBLE);
        brushSizeSeekBar.setVisibility(View.VISIBLE);
        orBrushSizeTextView.setVisibility(View.VISIBLE);
        brushSizePixelEditText.setVisibility(View.VISIBLE);
        proceedBtn.setVisibility(View.VISIBLE);
    }

    private void hideShapeOptions() {
        brushShapeTextView.setVisibility(View.INVISIBLE);
        provSquareBrushImageView.setVisibility(View.INVISIBLE);
        provRoundBrushImageView.setVisibility(View.INVISIBLE);
    }

    public void scaleImage(int scale) {
        android.view.ViewGroup.LayoutParams layoutParams = brushImageView.getLayoutParams();
        layoutParams.width += scale*3;
        layoutParams.height += scale*3;
        brushImageView.setLayoutParams(layoutParams);
        BRUSH_DIMENSION = layoutParams.height;
        brushSizePixelEditText.setHint(Integer.toString(BRUSH_DIMENSION));
    }
}
