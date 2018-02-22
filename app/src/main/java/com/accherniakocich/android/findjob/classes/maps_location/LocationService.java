package com.accherniakocich.android.findjob.classes.maps_location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocationService implements OnMapReadyCallback,LocationSource{

    private Context context;
    private FusedLocationProviderClient mFusedLocationClient;

    public String [] getLocate(String user, Context c) {
        context = c;
        String[] coordinates = new String[2];
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("coord", "2");
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    1);
                        } catch (IOException e) {

                        }
                        if (addresses != null) {
                            Log.d("locality", "loc = " + addresses.get(0).getLocality());
                            coordinates[0] = location.getLongitude() + "";
                            coordinates[1] = location.getLatitude() + "";
                        }
                    }
                }
            });
            return new String[0];
        }
        return coordinates;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
