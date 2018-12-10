package com.jober.avinashchintareddy.weatherpredictor;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.jober.avinashchintareddy.weatherpredictor.Model.LocationValues;
import com.jober.avinashchintareddy.weatherpredictor.Network.NetworkCall;
import com.jober.avinashchintareddy.weatherpredictor.Presenter.PresenterImplement;

import java.util.ArrayList;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
/*
* This is the activity to load multiple views Google api is used to locate the values
* Current weather is named as Weather variables
* and 5 day forecast is named as Forecast variables
*
*
*
* */

public class MainActivity extends Activity implements ViewWeather,GoogleApiClient.ConnectionCallbacks{
    //UI Elements
    TextView txlocat,txrgname,
            txtemperature, txtempmax,
            txtempmin,txdescription,
            txweathermain,txweathertemp;
    LocationValues mLocationValues;
    // google location apis
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    //Listview and adapter
    ListView listView;
    CustomAdapter customAdapter;

    // permissions
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private ArrayList<String> permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;

    //presenter and netweork calls
    PresenterImplement presenterImplement;
    NetworkCall networkCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addResources();
        networkCall = new NetworkCall();
        presenterImplement = new PresenterImplement(this, networkCall);
        //txlocat = (TextView) findViewById(R.id.txtlocation);
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }


    // added all the resources over here
    private void addResources() {
        txrgname =(TextView) findViewById(R.id.tx_name);
        txdescription=(TextView) findViewById(R.id.tx_weather_desc);
        txtemperature=(TextView) findViewById(R.id.tx_weather_temp);
        txtempmax=(TextView) findViewById(R.id.tx_weather_temp_max);
        txtempmin=(TextView) findViewById(R.id.tx_weather_temp_min);
        txweathermain=(TextView) findViewById(R.id.tx_weathermain);
        txweathertemp=(TextView) findViewById(R.id.tx_weather_temp);
        listView = (ListView) findViewById(R.id.lst_view);
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

   @Override
    protected void onStart() {
        super.onStart();
                callConnect();

    }
    protected void callConnect(){

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //callConnect();
        if (!checkPlayServices()) {
            txlocat.setText("Please install Google Play services.");
        }
    }

    // To verify the location open any of the location service maps before turning on this app

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLocation = (Location) LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if(mLocation!=null)
        {


                // Location values given to Model class LocationValues

            try {
                mLocationValues =new LocationValues(mLocation.getLatitude(),mLocation.getLongitude());
                //gets current weather
                presenterImplement.getWeather(mLocationValues.getLatitude(),mLocationValues.getLongitude());
                //gets forecast weather
                presenterImplement.getForecast(mLocationValues.getLatitude(),mLocationValues.getLongitude());

            }
            catch (Exception e){
                Log.i("Exception",""+e);
            }
        }
        else{
            Log.i("NoLocation","NO location available "+mGoogleApiClient.isConnected());


        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }





    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else
                finish();

            return false;
        }
        return true;
    }



    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mGoogleApiClient.isConnected()) {

            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onFetchWeather() {
        try {
            if (presenterImplement.mWeatherResponse != null) {

                txrgname.setText(presenterImplement.mWeatherResponse.getName());
                txdescription.setText(presenterImplement.mWeatherResponse.getWeather().get(0).getDescription());
                txtempmin.setText(String.valueOf(presenterImplement.mWeatherResponse.getMain().getTempMin()));
                txtempmax.setText(String.valueOf(presenterImplement.mWeatherResponse.getMain().getTempMax()));

                txrgname.setText(String.valueOf(presenterImplement.mWeatherResponse.getName()));
                txweathermain.setText(presenterImplement.mWeatherResponse.getWeather().get(0).getMain());
                txweathertemp.setText(String.valueOf(presenterImplement.mWeatherResponse.getMain().getTemp()));

            }
        }
        catch (Exception e){
            Log.i("Fetchweather",""+e);
        }
        Log.i("OnFetch","from fetch weather");

    }

    @Override
    public void onFetchForecast() {
        try{
        if(presenterImplement.mForecastResponse!=null){
            customAdapter = new CustomAdapter(getApplicationContext(),presenterImplement.mForecastResponse.getList());
            listView.setAdapter(customAdapter);
        }
    }
        catch (Exception e){
        Log.i("Fetchweather",""+e);
    }

        Log.i("OnFetch","from fetch cast");
    }

    @Override
    public void onErrorWeather() {

        Toast.makeText(this,"Error in fetching Weather",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorForecast() {

        Toast.makeText(this,"Error in fetching response",Toast.LENGTH_LONG).show();
    }
}
