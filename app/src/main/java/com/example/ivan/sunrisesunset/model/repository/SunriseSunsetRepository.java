package com.example.ivan.sunrisesunset.model.repository;



import android.arch.lifecycle.MutableLiveData;

import com.example.ivan.sunrisesunset.entity.SunInfoResponse;
import com.example.ivan.sunrisesunset.model.network.RetrofiteBuilder;
import com.example.ivan.sunrisesunset.utils.Resource;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SunriseSunsetRepository {

    public MutableLiveData<Resource> getSunInfo(Map<String, String> parameters) {
        final MutableLiveData<Resource> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        RetrofiteBuilder
                .getSunApi()
                .getSunInfo(parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SunInfoResponse>() {
                    @Override
                    public void onSuccess(SunInfoResponse sunInfoResponse) {
                        result.setValue(Resource.success(sunInfoResponse));
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getLocalizedMessage(), e));
                    }
                });
        return result;
    }
}
