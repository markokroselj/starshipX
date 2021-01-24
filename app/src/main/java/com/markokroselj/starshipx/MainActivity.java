package com.markokroselj.starshipx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.markokroselj.starshipx.Tfr.Tfr;
import com.markokroselj.starshipx.roadClosure.RoadClosures;
import com.markokroselj.starshipx.weather.WeatherInBocaChica;
import com.markokroselj.starshipx.weather.response.WeatherApiResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView roadClosuresOutput;
    private TextView tfrsOutput;
    private ProgressBar closuresProgressBar;
    private ProgressBar tfrProgressBar;
    private ImageView weatherIconOutput;
    private TextView weatherDescriptionOutput;
    private TextView windOutput;
    private TextView temperatureOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roadClosuresOutput = findViewById(R.id.roadClosuresOutput);
        closuresProgressBar = findViewById(R.id.closuresProgressBar);
        tfrProgressBar = findViewById(R.id.tfrProgressBar);
        tfrsOutput = findViewById(R.id.tfrsOutput);
        weatherIconOutput = findViewById(R.id.weatherIconOutput);
        weatherDescriptionOutput = findViewById(R.id.weatherDescriptionOutput);
        windOutput = findViewById(R.id.windOutput);
        temperatureOutput = findViewById(R.id.temperatureOutput);
        new DisplayIsRoadClosed().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new DisplayIsTfrToday().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new DisplayWeather().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void openAllRoadClosuresActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AllRoadClosuresActivity.class);
        startActivity(intent);
    }

    public void openScheduledTfrsActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ScheduledTfrsActivity.class);
        startActivity(intent);
    }

    public void openLabPadreCamsListActivity(View view) {
        Intent intent = new Intent(MainActivity.this, LabPadreCamsListActivity.class);
        startActivity(intent);
    }

    private class DisplayIsRoadClosed extends AsyncTask<Void, Void, Void> {
        String output = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                output = new RoadClosures().isClosureScheduledForToday() ? "Road is closed" : "Road is opened :(";
                runOnUiThread(() -> closuresProgressBar.setVisibility(View.INVISIBLE));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            roadClosuresOutput.setText(output);
            super.onPostExecute(aVoid);
        }
    }

    private class DisplayIsTfrToday extends AsyncTask<Void, Void, Void> {
        String output = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                output = new Tfr().isToday() ? "TFR is scheduled for Today!" : "No TFR today :(";
                runOnUiThread(() -> tfrProgressBar.setVisibility(View.INVISIBLE));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            tfrsOutput.setText(output);
            super.onPostExecute(aVoid);
        }
    }

    private class DisplayWeather extends AsyncTask<Void, Void, Void> {
        WeatherApiResponse weatherApiResponse;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                weatherApiResponse = new WeatherInBocaChica().getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String imgUrl = "https://openweathermap.org/img/wn/" + weatherApiResponse.getWeather().get(0).getIcon() + "@2x.png";
            new DownLoadImageTask(weatherIconOutput).execute(imgUrl);
            String descTmp = weatherApiResponse.getWeather().get(0).getDescription();
            String weatherDescription = descTmp.substring(0, 1).toUpperCase() + descTmp.substring(1);
            weatherDescriptionOutput.setText(weatherDescription);
            windOutput.setText("Wind: " + weatherApiResponse.getWind().getSpeed() + " m/s");
            temperatureOutput.setText(weatherApiResponse.getMain().getTemp() + "\u00B0C");
            super.onPostExecute(aVoid);
        }
    }


    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }


        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}