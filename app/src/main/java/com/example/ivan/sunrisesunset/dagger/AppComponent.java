package com.example.ivan.sunrisesunset.dagger;


import com.example.ivan.sunrisesunset.viewModel.SunriseSunsetViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {SunriseSunsetModule.class})
@Singleton
public interface AppComponent {

    void inject(SunriseSunsetViewModel sunriseSunsetViewModel);
}
