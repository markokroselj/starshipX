package com.markokroselj.starshipx;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.markokroselj.starshipx.roadClosure.RoadClosures;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AllRoadClosuresActivity extends AppCompatActivity {


    ListView lvClosures;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_road_closures);
        lvClosures = findViewById(R.id.lvClosures);
        lvClosures.setAdapter(new ArrayAdapter<>(this, R.layout.closures_list_view, new String[]{"Loading..."}));
        new DisplayClosures(this).execute();
        swipeRefreshLayout = findViewById(R.id.swiperefreshClosures);
        swipeRefreshLayout.setOnRefreshListener(
                () -> new DisplayClosures(this).execute()
        );
    }

    private class DisplayClosures extends AsyncTask<Void, Void, Void> {
        ArrayList<String> output = new ArrayList<>();

        private final WeakReference<Activity> weakActivity;

        DisplayClosures(Activity myActivity) {
            this.weakActivity = new WeakReference<>(myActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                output = new RoadClosures().getScheduledClosuresDates();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Activity activity = weakActivity.get();
            if (activity == null
                    || activity.isFinishing()
                    || activity.isDestroyed()) {
                // activity is no longer valid, don't do anything!
                return;
            }
            if (output.size() < 1) {
                String[] noClosuresOutput = {"There are no road closures scheduled"};
                lvClosures.setAdapter(new ArrayAdapter<>(activity, R.layout.closures_list_view, noClosuresOutput));
                lvClosures.setDivider(null);
                lvClosures.setDividerHeight(0);
            } else {
                ArrayAdapter<String> closuresAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, output);
                lvClosures.setAdapter(closuresAdapter);
            }
            swipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(aVoid);
        }
    }

}

