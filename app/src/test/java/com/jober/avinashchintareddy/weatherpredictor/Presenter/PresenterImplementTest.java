package com.jober.avinashchintareddy.weatherpredictor.Presenter;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.jober.avinashchintareddy.weatherpredictor.Model.LocationValues;
import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ForecastResponse;
import com.jober.avinashchintareddy.weatherpredictor.Model.weatherModel.WeatherResponse;
import com.jober.avinashchintareddy.weatherpredictor.Network.NetworkCall;
import com.jober.avinashchintareddy.weatherpredictor.ViewWeather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import io.reactivex.Observable;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

/**
 * unit tested every
 */
public class PresenterImplementTest {



    @Test
    public void shouldNotLoadWeather() {
        //given
        ViewWeather viewWeather = Mockito.mock(ViewWeather.class);
        NetworkCall networkCall = Mockito.mock(NetworkCall.class);

        Exception exception = new Exception();

        //when
        when(networkCall.getCurrentWeather(anyDouble(),anyDouble())).thenReturn(Observable.<WeatherResponse>error(exception));


        //Then
        PresenterImplement presenterImplement = new PresenterImplement(viewWeather,networkCall);
        presenterImplement.getWeather(33,23);
        Mockito.verify(viewWeather,Mockito.times(1)).onErrorWeather();


    }
    @Test
    public void shouldLoadWeather(){
        //given
        WeatherResponse weatherResponse = new WeatherResponse();
        ViewWeather viewWeather = Mockito.mock(ViewWeather.class);
        NetworkCall networkCall = Mockito.mock(NetworkCall.class);

        //when
        when(networkCall.getCurrentWeather(anyDouble(),anyDouble())).thenReturn(Observable.just(weatherResponse));

        //Then
        PresenterImplement presenterImplement = new PresenterImplement(viewWeather,networkCall);
        presenterImplement.getWeather(33,23);
        Mockito.verify(viewWeather,Mockito.times(1)).onFetchWeather();

    }

    @Test
    public void shouldNotLoadForecast(){

        ViewWeather viewWeather = Mockito.mock(ViewWeather.class);
        LocationValues locationValues = Mockito.mock(LocationValues.class);
        NetworkCall networkCall = Mockito.mock(NetworkCall.class);
        Exception exception = new Exception();

        //when
        when(networkCall.getForecastWeather(anyDouble(),anyDouble())).thenReturn(Observable.<ForecastResponse>error(exception));

        //Then
        PresenterImplement presenterImplement = new PresenterImplement(viewWeather,networkCall);
        presenterImplement.getForecast(33,44);
        Mockito.verify(viewWeather,Mockito.times(1)).onErrorForecast();

    }

    @Test
    public void shouldLoadForecast(){
        ForecastResponse forecastResponse= new ForecastResponse();
        ViewWeather viewWeather = Mockito.mock(ViewWeather.class);
        NetworkCall networkCall = Mockito.mock(NetworkCall.class);

        //when
        when(networkCall.getForecastWeather(anyDouble(),anyDouble())).thenReturn(Observable.just(forecastResponse));

        //Then
        PresenterImplement presenterImplement = new PresenterImplement(viewWeather,networkCall);
        presenterImplement.getForecast(44,78);
        Mockito.verify(viewWeather,Mockito.times(1)).onFetchForecast();
    }


}