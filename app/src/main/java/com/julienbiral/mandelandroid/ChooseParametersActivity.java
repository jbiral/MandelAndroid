package com.julienbiral.mandelandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.julienbiral.mandelandroid.util.DrawingParameters;
import com.julienbiral.mandelandroid.util.JsonParser;

public class ChooseParametersActivity extends Activity {

    private final static double PROGRESS_TO_COLOR_COEFFICIENT = 0.01;

    Button btnDrawMandelbrot;
    EditText zoom, redValue, greenValue, blueValue;
    SeekBar redBar, greenBar, blueBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_parameter_layout);

        zoom = (EditText) findViewById(R.id.et_zoom);

        initColoredSeekBar();
        initButtonDrawMandelbrot();
    }

    private void initColoredSeekBar() {
        redBar = (SeekBar) findViewById(R.id.red_parameter_seekbar);
        redValue = (EditText) findViewById(R.id.red_parameter_value);
        redValue.setText(getString(R.string.color_default_progress) + "%");
        initColorBarListener(redBar, redValue);

        greenBar = (SeekBar) findViewById(R.id.green_parameter_seekbar);
        greenValue = (EditText) findViewById(R.id.green_parameter_value);
        greenValue.setText(getString(R.string.color_default_progress) + "%");
        initColorBarListener(greenBar, greenValue);

        blueBar = (SeekBar) findViewById(R.id.blue_parameter_seekbar);
        blueValue = (EditText) findViewById(R.id.blue_parameter_value);
        blueValue.setText(getString(R.string.color_default_progress) + "%");
        initColorBarListener(blueBar, blueValue);
    }

    private void initColorBarListener(SeekBar bar, final EditText text) {
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setText(parseSeekBarProgress(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private String parseSeekBarProgress(int progress) {
        return String.valueOf(progress) + "%";
    }

    private void initButtonDrawMandelbrot() {
        btnDrawMandelbrot = (Button) findViewById(R.id.btn_draw_mandelbrot);
        btnDrawMandelbrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String json = createJsonFromParameters();
                Intent intent = new Intent(ChooseParametersActivity.this, MandelbrotActivity.class);
                intent.putExtra(MandelbrotActivity.DRAWING_PARAMETERS, json);
                startActivity(intent);
            }
        });
    }

    private String createJsonFromParameters() {
        final DrawingParameters params = new DrawingParameters();
        params.zoom = Integer.parseInt(zoom.getText().toString());
        params.setRGBColor(redBar.getProgress() * PROGRESS_TO_COLOR_COEFFICIENT,
                           greenBar.getProgress() * PROGRESS_TO_COLOR_COEFFICIENT,
                           blueBar.getProgress() * PROGRESS_TO_COLOR_COEFFICIENT);
        Log.d("params", params.toString());
        return JsonParser.createJson(params);
    }
}
