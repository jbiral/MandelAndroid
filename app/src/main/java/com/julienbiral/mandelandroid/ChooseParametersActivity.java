package com.julienbiral.mandelandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.julienbiral.mandelandroid.util.DrawingParameters;
import com.julienbiral.mandelandroid.util.JsonParser;

public class ChooseParametersActivity extends Activity {

    Button btnDrawMandelbrot;
    EditText etZoom, etMagicColor;
    DrawingParameters params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_parameter_layout);

        etZoom = (EditText) findViewById(R.id.et_zoom);
        etMagicColor = (EditText) findViewById(R.id.et_magic_color);
        btnDrawMandelbrot = (Button) findViewById(R.id.btn_draw_mandelbrot);
        params = new DrawingParameters();

        btnDrawMandelbrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.zoom = Integer.parseInt(etZoom.getText().toString());
                params.color = Integer.parseInt(etMagicColor.getText().toString());
                String json = JsonParser.createJson(params);
                Intent intent = new Intent(ChooseParametersActivity.this, MandelbrotActivity.class);
                intent.putExtra(MandelbrotActivity.DRAWING_PARAMETERS, json);
                startActivity(intent);
            }
        });




    }
}
