# StarshipX
 ![Starship](Starship.jpg "Starhip img:Spacex Flicker")

Android application that provides useful information about Spacex's Starship development in Boca Chica, Texas

### Version: 0.1 beta

## Feauters 
* is road closed
* is TFR today
* list of scheduled road closures
* list of scheduled Temporary Flight Restrictions - TFR
* list LabPadre Live streams 


## Build
* Clone the repository 
```
git clone https://github.com/markokroselj/starshipX
```

* Open project in Android studio
* Get API Key  
    Some app functionalities use YouTube API. In order to build the project you need to include it in the project. You can get it from [here](https://developers.google.com/youtube/v3/getting-started). 
 * Include API Key  
    In the direcotry
    ```
    /app/src/main/java/com/markokroselj/starshipx/labPadreCams
    ```
    create file ApiKeys.java and write inside: 

    ```java
    package com.markokroselj.starshipx.labPadreCams;

    public class ApiKeys {
        private final String YT_API_KEY = "YOUR KEY";

        protected String getYT_API_KEY() {
            return YT_API_KEY;
        }
    }
    ```
    Place your API key as the value of YT_API_KEY variable.
* Application uses this Gradle dependencies  
    ```Gradle
    implementation 'org.jsoup:jsoup:1.13.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.github.bumptech.glide:glide:3.7.0'
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation 'com.google.code.gson:gson:2.8.6'
    ```
    When running the project for the first time you will have to download them. If it does not do it automathicliy, click sync or Reload Gradle Project in the Gradle tab. 
* To build the apk click Build --> Build bundle(s) - APK(s) --> Build APK(s)

Application requires internet permission 
```xml
 <uses-permission android:name="android.permission.INTERNET" />
 ```
