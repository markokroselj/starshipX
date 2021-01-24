package com.markokroselj.starshipx.weather;

import com.google.gson.Gson;
import com.markokroselj.starshipx.ApiKeys;
import com.markokroselj.starshipx.weather.response.WeatherApiResponse;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherInBocaChica {

    private final String URL = "https://api.openweathermap.org/data/2.5/weather?lat=25.99753385726347&lon=-97.15934229636552&appid=" + ApiKeys.getOpenWeatherApiKey() + "&units=metric";

    public WeatherApiResponse getResponse() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            //  System.out.println(response.body().string());
            return new Gson().fromJson(response.peekBody(8048).string(), WeatherApiResponse.class);
        }
    }

}
