# StarshipX
 ![Starship](Starship.jpg "Starship img:Spacex Flickr")

Android application that provides useful information about Spacex's Starship development in Boca Chica, Texas

### Version: 0.1 beta

## Features 
* is road closed
* is TFR today
* weather in Boca Chica
* list of scheduled road closures
* list of scheduled Temporary Flight Restrictions - TFR
* list of LabPadre Live streams 


## Build
* Clone the repository 
```
git clone https://github.com/markokroselj/starshipX
```

* Open project in Android studio
* Get API Keys 
    Application uses Youtube and OpenWeather API. In order to build the project you need to include API Key in the project. You can get Yt key from [here](https://developers.google.com/youtube/v3/getting-started) and OpenWeather key from  [here](https://home.openweathermap.org/api_keys). 
 * Include API Keys  
    In the directory 
    ```
    /app/src/main/java/com/markokroselj/starshipx/
    ```
    create file ApiKeys.java and write inside: 

    ```java
    private static final String YT_API_KEY = "YOUR YOUTUBE KEY";
    private static final String OPEN_WEATHER_API_KEY = "YOUR OPEN WEATHER KEY";

    public static String getYT_API_KEY() {
        return YT_API_KEY;
    }

    public static String getOpenWeatherApiKey() {
        return OPEN_WEATHER_API_KEY;
    }
    ```
    Place your YT API key as the value of YT_API_KEY variable and OpenWeather key as the value of OPEN_WEATHER_API_KEY variable.
* Application uses this Gradle dependencies  
    ```Gradle
    implementation 'org.jsoup:jsoup:1.13.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.github.bumptech.glide:glide:3.7.0'
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation 'com.google.code.gson:gson:2.8.6'
    ```
    When running the project for the first time you will have to download them. If it does not do it automatically, click sync or Reload Gradle Project in the Gradle tab. 
* To build the apk click Build --> Build bundle(s) - APK(s) --> Build APK(s)

Application requires internet permission 
```xml
 <uses-permission android:name="android.permission.INTERNET" />
 ```
