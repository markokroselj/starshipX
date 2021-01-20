package com.markokroselj.starshipx;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.markokroselj.starshipx.labPadreCams.LabPadreCams;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class LabPadreCamsListActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    ArrayList<Button> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_padre_cams_list);
        linearLayout = findViewById(R.id.linearLayout);
        new DisplayCamsList(this).execute();

    }

    public void openCam(String link) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + link.split("be/")[1]));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + link.split("be/")[1]));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    private class DisplayCamsList extends AsyncTask<Void, Void, Void> {

        ArrayList<String[]> cams = new ArrayList<>();


        private final WeakReference<Activity> weakActivity;

        DisplayCamsList(Activity myActivity) {
            this.weakActivity = new WeakReference<>(myActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                cams = new LabPadreCams().getCamLinks();
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
            System.out.println(cams.size());
            runOnUiThread(() -> {
                for (int i = 0; i < cams.size(); i++) {
                    ContextThemeWrapper newContext = new ContextThemeWrapper(activity, R.style.Theme_AppCompat);
                    Button button = new Button(newContext);
                    buttons.add(button);
                    buttons.get(i);
                    buttons.get(i).setText(cams.get(i)[0]);
                    int finalI = i;
                    buttons.get(i).setOnClickListener(v -> openCam(cams.get(finalI)[1]));
                    linearLayout.addView(buttons.get(i));
                }
            });
            super.onPostExecute(aVoid);
        }
    }
}
