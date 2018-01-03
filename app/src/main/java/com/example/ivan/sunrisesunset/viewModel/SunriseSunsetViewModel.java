package com.example.ivan.sunrisesunset.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ivan.sunrisesunset.SunApp;
import com.example.ivan.sunrisesunset.model.repository.SunriseSunsetRepository;
import com.example.ivan.sunrisesunset.utils.Resource;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class SunriseSunsetViewModel extends ViewModel {

    public MutableLiveData<Resource> sunInfo = new MutableLiveData<>();

    @Inject
    public SunriseSunsetRepository sunriseSunsetRepository;

    public void inject() {
        SunApp.getAppComponent().inject(this);
    }

    public void getSunInfo(double lat, double lng){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat", String.valueOf(lat));
        parameters.put("lng", String.valueOf(lng));
        parameters.put("formatted", "1");
        parameters.put("date", "today");
        sunInfo = sunriseSunsetRepository.getSunInfo(parameters);
    }

}
