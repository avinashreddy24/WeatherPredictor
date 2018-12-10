package com.jober.avinashchintareddy.weatherpredictor;

import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ForecastResponse;

/**
 * Interactions from Presenter to View using these methods
 */

public interface ViewWeather {
    // Display current weather
    void onFetchWeather();
    // Display 5days Forecast
    void onFetchForecast();
    //Display error messages for weather
    void onErrorWeather();
    //Display error messages for Forecast
    void onErrorForecast();
}
