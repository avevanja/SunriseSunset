package com.example.ivan.sunrisesunset.dagger;

import com.example.ivan.sunrisesunset.model.repository.SunriseSunsetRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SunriseSunsetModule {

    @Provides
    @Singleton
    public SunriseSunsetRepository provideSunriseSunsetRepository(){
        return new SunriseSunsetRepository();
    }
}
