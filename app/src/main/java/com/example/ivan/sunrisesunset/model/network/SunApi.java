package com.example.ivan.sunrisesunset.model.network;


import com.example.ivan.sunrisesunset.entity.SunInfoResponse;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SunApi {

    @GET("json")
    Single<SunInfoResponse> getSunInfo(@QueryMap Map<String, String> parameters);
}
