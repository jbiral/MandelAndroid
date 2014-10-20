package com.julienbiral.mandelandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseParametersActivity extends Activity {

    Button btnDrawMandelbrot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_parameter_layout);

        btnDrawMandelbrot = (Button) findViewById(R.id.btn_draw_mandelbrot);
        btnDrawMandelbrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseParametersActivity.this, MandelbrotActivity.class);
                startActivity(intent);
            }
        });
    }
}
