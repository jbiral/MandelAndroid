package com.julienbiral.mandelandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MandelbrotActivity extends ActionBarActivity {

    TextView tvLoadingScreen;
    ProgressBar pbLoadingScreen;
    ImageView ivMandelbrot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mandelbrot_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvLoadingScreen = (TextView) findViewById(R.id.tv_loading_screen);
        pbLoadingScreen = (ProgressBar) findViewById(R.id.pb_loading_screen);
        ivMandelbrot = (ImageView) findViewById(R.id.iv_mandelbrot);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Start the drawing of the Mandelbrot set in background
        new ComputeMandelbrotSet().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mandelbrot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, upIntent);
                break;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ComputeMandelbrotSet extends AsyncTask<Void, Integer, Bitmap> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            pbLoadingScreen.setProgress(values[0]);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            final double ZOOM = 300;
            final int MAX_ITER = 570;
            Bitmap mBitmap = null;

            if (mBitmap == null) {
                int width = ivMandelbrot.getWidth();
                int height = ivMandelbrot.getHeight();
                mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                double zx, zy, cX, cY, tmp;
                int previousPercent = 0;
                int currentPercent = 0;
                int nbIter = width * height;
                int[] pixels = new int[nbIter];

                mBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        zx = zy = 0;
                        // x-y are taken centered
                        cX = (x - width / 2) / ZOOM;
                        cY = (y - height / 2) / ZOOM;
                        int iter = MAX_ITER;

                        // To have a bounded serie, the module should be lower than 2:
                        while (zx * zx + zy * zy < 4 && iter > 0) {
                            tmp = zx * zx - zy * zy + cX;
                            zy = 2.0 * zx * zy + cY;
                            zx = tmp;
                            iter--;
                        }

                        pixels[(y * width + x)] = Color.rgb(30, iter << 8, 30);

                        currentPercent = (y * width + x) * 100 / nbIter;
                        if (previousPercent < currentPercent) {
                            previousPercent = currentPercent;
                            publishProgress(currentPercent);
                            Log.d("Progression", currentPercent + " percents.");
                        }
                    }
                }
                mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            }

            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            tvLoadingScreen.setVisibility(View.GONE);
            pbLoadingScreen.setVisibility(View.GONE);

            ivMandelbrot.setImageBitmap(bitmap);
        }
    }
}
