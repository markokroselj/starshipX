package com.markokroselj.starshipx.labPadreCams;

import com.google.gson.Gson;
import com.markokroselj.starshipx.ApiKeys;
import com.markokroselj.starshipx.labPadreCams.response.YtVideo;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LabPadreCams {


    private final String URL = "https://www.googleapis.com/youtube/v3/videos?part=id%2C+snippet&id=sTA0GTgFn5E&key=" + ApiKeys.getYT_API_KEY();


    private YtVideo getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new Gson().fromJson(response.peekBody(9048).string(), YtVideo.class);
        }
    }

    public ArrayList<String[]> getCamLinks() throws IOException {
        ArrayList<String[]> links = new ArrayList<>();

        String needleCamDescription = getResponse(URL).getItems().get(0).getSnippet().getDescription();
        String linksAndTitles = needleCamDescription.split("Subscribe for more SpaceX live coverage.")[1].split("Onsite Weather")[0];
        String[] linksTmp = linksAndTitles.split("Live");

        for (int i = 1; i < linksTmp.length; i++) {
            links.add(linksTmp[i].split("Link"));
        }
        return links;
    }

}
