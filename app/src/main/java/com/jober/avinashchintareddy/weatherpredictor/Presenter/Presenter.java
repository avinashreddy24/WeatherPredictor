package com.jober.avinashchintareddy.weatherpredictor.Presenter;



import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ForecastResponse;
import com.jober.avinashchintareddy.weatherpredictor.Model.weatherModel.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by avinashchintareddy on 11/17/18.
 */

public interface Presenter {
    void getWeather(double lat, double longg);
    void getForecast(double lat, double longg);
    DisposableObserver<WeatherResponse> getWeatherObs();
    DisposableObserver<ForecastResponse> getForecastObs();
    //Observable<ForecastResponse> getForecastWeather(double lat, double longg);
}
