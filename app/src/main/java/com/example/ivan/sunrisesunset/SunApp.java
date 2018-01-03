package com.example.ivan.sunrisesunset;

import android.app.Application;

import com.example.ivan.sunrisesunset.dagger.AppComponent;
import com.example.ivan.sunrisesunset.dagger.DaggerAppComponent;
import com.example.ivan.sunrisesunset.dagger.SunriseSunsetModule;


public class SunApp extends Application {
    private static AppComponent appComponent;
    public static AppComponent getAppComponent(){
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .sunriseSunsetModule(new SunriseSunsetModule())
                .build();
    }
}
