package com.example.garyzhou.snaphistory;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    public final static String HISTORIC_FACT = "com.example.garyzhou.snaphistory";
    GoogleApiClient mGoogleApiClient;
    TextView loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = getIntent();
        loc = (TextView) findViewById(R.id.loc);
        loc.setText("LOCATION");
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */,
                            this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

    }

    protected void onResume() {
        mGoogleApiClient.connect();
        super.onResume();
    }

    protected void onPause() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Context context = this.getApplicationContext();
        CharSequence text = "Google Play Service Connection Failed";
        int duration = Toast.LENGTH_LONG;

        Toast.makeText(context, text, duration).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.))
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        loc.setText(location.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        loc.setText(location.toString());

                    }catch(SecurityException e){

                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    public void seeHistory(View view) {
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putExtra(HISTORIC_FACT, "Here are the facts!");
        startActivity(intent);
    }
}
