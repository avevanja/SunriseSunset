package com.example.ivan.sunrisesunset.model.network;

import com.example.ivan.sunrisesunset.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofiteBuilder {

    private static Retrofit retrofit;
    private static SunApi sunApi = getRetrofitBuilderWithRx().create(SunApi.class);

    private static Retrofit getRetrofitBuilderWithRx() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }



    public static SunApi getSunApi() {
        return sunApi;
    }
}
