package com.jober.avinashchintareddy.weatherpredictor.Network;



import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ForecastResponse;
import com.jober.avinashchintareddy.weatherpredictor.Model.weatherModel.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by avinashchintareddy on 11/18/18.
 */

public interface ApiInterface {

    //api.openweathermap.org/data/2.5/weather?lat=35&lon=139
    @GET("weather")
    Observable<WeatherResponse> getRealWeather(@Query("lat") String latitude,
                                               @Query("lon") String longitude,
                                               @Query("units") String metric,
                                               @Query("appid") String Appid
                                         );

    //api.openweathermap.org/data/2.5/forecast?lat=35&lon=139
    @GET("forecast")
    Observable<ForecastResponse> getForecast(@Query("lat") String latitude,
                                       @Query("lon") String longitude,
                                       @Query("units") String metric,
                                       @Query("appid") String Appid);
}
