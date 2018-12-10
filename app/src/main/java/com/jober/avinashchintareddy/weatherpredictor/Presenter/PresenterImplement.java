package com.jober.avinashchintareddy.weatherpredictor.Presenter;


import android.util.Log;

import com.jober.avinashchintareddy.weatherpredictor.Model.LocationValues;
import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ForecastResponse;
import com.jober.avinashchintareddy.weatherpredictor.Model.weatherModel.WeatherResponse;
import com.jober.avinashchintareddy.weatherpredictor.Network.NetworkCall;
import com.jober.avinashchintareddy.weatherpredictor.ViewWeather;


import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by avinashchintareddy on 11/17/18.
 */

public class PresenterImplement implements Presenter {
    ViewWeather viewWeather;
    //LocationValues locationValues;
    NetworkCall networkCall;
    public WeatherResponse mWeatherResponse;
    public ForecastResponse mForecastResponse;


    public PresenterImplement(ViewWeather viewWeather,  NetworkCall networkCall){
        this.viewWeather=viewWeather;
        this.networkCall= networkCall;
    }

    @Override
    public void getWeather(double lat, double longg) {
        networkCall.getCurrentWeather(lat,longg).subscribeWith(getWeatherObs());
    }


    public DisposableObserver<WeatherResponse> getWeatherObs() {

        return new DisposableObserver<WeatherResponse>(){

            @Override
            public void onNext(@NonNull WeatherResponse weatherResponse) {
                mWeatherResponse =weatherResponse;
                //Log.i("networkcall","onNext");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                viewWeather.onErrorWeather();
            }

            @Override
            public void onComplete() {

                viewWeather.onFetchWeather();
            }
        };
        
    }



    public DisposableObserver<ForecastResponse> getForecastObs(){
        return new DisposableObserver<ForecastResponse>(){

            @Override
            public void onNext(@NonNull ForecastResponse forecastResponse) {
                //Log.i("Forecast",""+forecastResponse);
                mForecastResponse=forecastResponse;

            }

            @Override
            public void onError(@NonNull Throwable e) {
                //Log.i("Forecast",""+e);
                viewWeather.onErrorForecast();
            }

            @Override
            public void onComplete() {
                viewWeather.onFetchForecast();
            }
        };
    }

    @Override
    public void getForecast(double lat, double longg){
        networkCall.getForecastWeather(lat,longg).subscribeWith(getForecastObs());

    }
}
