package com.markokroselj.starshipx;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.markokroselj.starshipx.Tfr.Tfr;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ScheduledTfrsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lvRestriction;

    private SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduled_tfrs);
        lvRestriction = findViewById(R.id.lvRestriction);
        new DisplayTfrs(this).execute();
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        lvRestriction.setOnItemClickListener(this);
        mySwipeRefreshLayout.setOnRefreshListener(
                () -> new DisplayTfrs(this).execute()
        );
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(i);
        Intent intent = new Intent(getBaseContext(), TfrInfoActivity.class);
        intent.putExtra("tfrId", i);
        startActivity(intent);
    }

    private class DisplayTfrs extends AsyncTask<Void, Void, Void> {
        ArrayList<String> output = new ArrayList<>();

        private final WeakReference<Activity> weakActivity;

        DisplayTfrs(Activity myActivity) {
            this.weakActivity = new WeakReference<>(myActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                output = new Tfr().getDates();
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
            ArrayAdapter<String> tfrAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, output);
            lvRestriction.setAdapter(tfrAdapter);
            mySwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(aVoid);
        }
    }


}
