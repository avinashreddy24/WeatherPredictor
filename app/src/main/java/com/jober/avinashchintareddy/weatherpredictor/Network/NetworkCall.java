package com.jober.avinashchintareddy.weatherpredictor.Network;

import android.util.Log;


import com.google.gson.Gson;
import com.jober.avinashchintareddy.weatherpredictor.Constants;
import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ForecastResponse;
import com.jober.avinashchintareddy.weatherpredictor.Model.weatherModel.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by avinashchintareddy on 11/19/18.
 */

public class NetworkCall {

    public Observable<WeatherResponse> getCurrentWeather(double lat, double longg){
        return  ApiClient.getClient().create(ApiInterface.class)
                .getRealWeather(String.valueOf(lat),String.valueOf(longg), Constants.unit,Constants.APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<ForecastResponse> getForecastWeather(double lat, double longg) {

        return  ApiClient.getClient().create(ApiInterface.class)
                .getForecast(String.valueOf(lat),String.valueOf(longg), Constants.unit,Constants.APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
