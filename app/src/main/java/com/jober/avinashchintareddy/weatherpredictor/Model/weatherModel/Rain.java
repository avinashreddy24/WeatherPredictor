package com.jober.avinashchintareddy.weatherpredictor.Model.weatherModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by avinashchintareddy on 12/3/18.
 */

public class Rain {
    @SerializedName("3")
    private double jsonMember3h;

    public void setJsonMember3h(double jsonMember3h){
        this.jsonMember3h = jsonMember3h;
    }

    public double getJsonMember3h(){
        return jsonMember3h;
    }

    @Override
    public String toString(){
        return
                "Rain{" +
                        "3h = '" + jsonMember3h + '\'' +
                        "}";
    }
}
