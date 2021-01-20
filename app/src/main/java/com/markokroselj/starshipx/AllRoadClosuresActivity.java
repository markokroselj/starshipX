package com.markokroselj.starshipx;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.markokroselj.starshipx.roadClosure.RoadClosures;

import java.io.IOException;
import java.util.ArrayList;

public class AllRoadClosuresActivity extends AppCompatActivity {


    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_road_closures);
        textView2 = findViewById(R.id.textView2);
        new DisplayRoadClosures().execute();
    }


    private class DisplayRoadClosures extends AsyncTask<Void, Void, Void> {
        String output = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ArrayList<String> closures = new RoadClosures().getScheduledClosuresDates();
                for (int i = 0; i < closures.size(); i++) {
                    output += closures.get(i) + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textView2.setText(output);
            super.onPostExecute(aVoid);
        }
    }

}

