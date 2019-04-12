package com.example.sushant_bansde;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity  {
    APIInterface apiInterface;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    String currentLat,currentLong,destinationLat,destinationLong;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(checkPermission())
        {
           // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            currentLat=""+ myLocation.getLatitude();
            currentLong=""+ myLocation.getLongitude();

            getLatitudeLong();

        }
        else{
            requestPermission();
        }



    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted){
                       // Toast.makeText(MainActivity.this, "Permission Granted, Now you can access location data and camera.", Toast.LENGTH_LONG).show();
                        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                        currentLat=""+ myLocation.getLatitude();
                        currentLong=""+ myLocation.getLongitude();
                        getLatitudeLong();

                    }
                    else {


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
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


    public void getLatitudeLong()
    {

        apiInterface = APIClient.getClient().create(APIInterface.class);


        /**
         GET List Resources
         **/
        Call<GetLatLongResponse> call = apiInterface.getLatAndLong();
        call.enqueue(new Callback<GetLatLongResponse>() {
            @Override
            public void onResponse(Call<GetLatLongResponse> call, Response<GetLatLongResponse> response) {


                if(response!=null)
                {
                    destinationLat=response.body().getLatitude();
                    destinationLong=response.body().getLongitude();

                   Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                    intent.putExtra("destinationLat",response.body().getLatitude());
                    intent.putExtra("destinationLong",response.body().getLongitude());
                    intent.putExtra("currentLat",currentLat);
                    intent.putExtra("currentLong",currentLong);
                    startActivity(intent);

                }


            }

            @Override
            public void onFailure(Call<GetLatLongResponse> call, Throwable t) {

            }
        });



    }

}
