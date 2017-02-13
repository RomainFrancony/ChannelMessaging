package com.francony.romain.channelmessaging;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

@SuppressWarnings("MissingPermission")
public class GPSActivity extends ActionBarActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private GoogleApiClient mGoogleApiClient;
    protected Location mCurrentLocation;
    private LocationListener listener;
    private boolean requesting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ActivityCompat.requestPermissions(GPSActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);




        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        //Correspond à l’intervalle moyen de temps entre chaque mise à jour descoordonnées
        mLocationRequest.setFastestInterval(5000);
        //Correspond à l’intervalle le plus rapide entre chaque mise à jour descoordonnées
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //Définit la demande de mise à jour avec un niveau de précision maximal
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Toast.makeText(getApplicationContext(),"Latitude"+ Double.toString(location.getLatitude()), Toast.LENGTH_LONG).show();
                mCurrentLocation = location;
            }
        };
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onStop(){
        super.onStop();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, listener);
        requesting = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                } else {
                    finish();
                }
                return;
            }
        }
    }



    @Override
    public void onResume(){
        super.onResume();
        if (requesting == false && mGoogleApiClient.isConnected()){
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            //Correspond à l’intervalle moyen de temps entre chaque mise à jour descoordonnées
            mLocationRequest.setFastestInterval(5000);
            //Correspond à l’intervalle le plus rapide entre chaque mise à jour descoordonnées
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //Définit la demande de mise à jour avec un niveau de précision maximal
            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    requesting = true;
                    Toast.makeText(getApplicationContext(),"Latitude"+ Double.toString(location.getLatitude()), Toast.LENGTH_LONG).show();
                    mCurrentLocation = location;
                }
            };
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
        }
    }
}
