package com.markokroselj.starshipx;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.markokroselj.starshipx.Tfr.Tfr;
import com.markokroselj.starshipx.roadClosure.RoadClosures;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView roadClosuresOutput;
    private TextView tfrsOutput;
    private ProgressBar closuresProgressBar;
    private ProgressBar tfrProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roadClosuresOutput = findViewById(R.id.roadClosuresOutput);
        closuresProgressBar = findViewById(R.id.closuresProgressBar);
        tfrProgressBar = findViewById(R.id.tfrProgressBar);
        tfrsOutput = findViewById(R.id.tfrsOutput);
        new DisplayIsRoadClosed().execute();
        new DisplayIsTfrToday().execute();
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
}