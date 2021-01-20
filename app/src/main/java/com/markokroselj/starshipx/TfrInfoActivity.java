package com.markokroselj.starshipx;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.markokroselj.starshipx.Tfr.Tfr;
import com.markokroselj.starshipx.Tfr.TfrInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class TfrInfoActivity extends AppCompatActivity {
    TextView dateOutput;
    TextView beginningDateAndTimeOutput;
    TextView endingDateAndTimeOutput;
    TextView typeOutput;
    TextView reasonOutput;
    TextView altitudeOutput;
    ImageView imageView;

    int intValue;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfr_info);
        intValue = getIntent().getIntExtra("tfrId", 0);
        dateOutput = findViewById(R.id.dateOutput);
        beginningDateAndTimeOutput = findViewById(R.id.beginningDateAndTimeOutput);
        endingDateAndTimeOutput = findViewById(R.id.endingDateAndTimeOutput);
        typeOutput = findViewById(R.id.typeOutput);
        reasonOutput = findViewById(R.id.reasonOutput);
        altitudeOutput = findViewById(R.id.altitudeOutput);
        imageView = findViewById(R.id.imageView);
        new DisplayTfrInformation(this).execute();

        System.out.println(imgUrl);
    }

    private class DisplayTfrInformation extends AsyncTask<Void, Void, Void> {
        TfrInfo tfrInfo = null;

        private final WeakReference<Activity> weakActivity;

        DisplayTfrInformation(Activity myActivity) {
            this.weakActivity = new WeakReference<>(myActivity);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                tfrInfo = new Tfr().getTFrInfo(intValue);
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

            dateOutput.setText(tfrInfo.getDate());
            beginningDateAndTimeOutput.setText(tfrInfo.getBeginningDateAndTime());
            endingDateAndTimeOutput.setText(tfrInfo.getEndDateAndTime());
            typeOutput.setText(tfrInfo.getType());
            reasonOutput.setText(tfrInfo.getReason());
            altitudeOutput.setText(tfrInfo.getAltitude());
            imgUrl = tfrInfo.getImgUrl();
            System.out.println(imgUrl);
            Glide
                    .with(activity) // replace with 'this' if it's in activity
                    .load(imgUrl)
                    .asGif()
                    .into(imageView);
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

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
