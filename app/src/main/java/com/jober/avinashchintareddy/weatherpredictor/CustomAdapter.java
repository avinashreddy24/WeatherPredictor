package com.jober.avinashchintareddy.weatherpredictor;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ForecastResponse;
import com.jober.avinashchintareddy.weatherpredictor.Model.forecastModel.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display Forecast over here with view holder pattern
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<ListItem> arrayList;

    public CustomAdapter(Context context, List<ListItem> arrayList) {

        this.context=context;
        this.arrayList=arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
            if(convertView==null){
                viewHolder= new ViewHolder();
                LayoutInflater layoutInflater =LayoutInflater.from(context);
                convertView =layoutInflater.inflate(R.layout.list_elements,parent,false);
                viewHolder.txfomain= convertView.findViewById(R.id.tx_forecast_main);
                viewHolder.txfotime=convertView.findViewById(R.id.tx_forecast_time);
                viewHolder.txfotemp=convertView.findViewById(R.id.tx_Forecast_temp);
                viewHolder.txfotempmax=convertView.findViewById(R.id.tx_forecast_tempmax);
                viewHolder.txfotempmin=convertView.findViewById(R.id.tx_forecast_tempmin);

                convertView.setTag(viewHolder);

            }
            else {
                viewHolder =(ViewHolder) convertView.getTag();
            }

            viewHolder.txfomain.setText(arrayList.get(position).getWeather().get(0).getMain());
            viewHolder.txfotemp.setText(""+arrayList.get(position).getMain().getTemp());
            viewHolder.txfotime.setText(arrayList.get(position).getDtTxt());
            viewHolder.txfotempmax.setText(""+arrayList.get(position).getMain().getTempMax());
            viewHolder.txfotempmin.setText(""+arrayList.get(position).getMain().getTempMin());
        return convertView;
    }
    private static class ViewHolder{
        TextView txfomain,txfotime,txfotemp,txfotempmax,txfotempmin;
    }

}
