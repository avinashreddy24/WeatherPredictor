package com.jober.avinashchintareddy.weatherpredictor.Model;

/**
 * Created by avinashchintareddy on 11/28/18.
 */

public class LocationValues {
    private double longitude;
    private double latitude;

    public LocationValues(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LocationValues{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
