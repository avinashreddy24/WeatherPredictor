package com.jober.avinashchintareddy.weatherpredictor.Network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by avinashchintareddy on 11/18/18.
 */

public class ApiClient {
    public static final String BASE_URL_DEV ="http://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;

    // retrofit instantiation
    public static Retrofit getClient() {
        if (retrofit==null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_DEV)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //.client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
