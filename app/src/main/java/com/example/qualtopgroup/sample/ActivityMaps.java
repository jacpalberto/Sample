package com.example.qualtopgroup.sample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback {
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_LOCATION_PERMISSIONS = 2244;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMap;
    private GoogleMap googleMap;
    private Marker myMarker;
    private TextView tvAddress;

    public static Intent newIntent(Context context) {
        return new Intent(context, ActivityMaps.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvAddress = (TextView) findViewById(R.id.address);
        setupMap(savedInstanceState);
    }

    private void setupMap(Bundle savedInstanceState) {
        mMap = (MapView) findViewById(R.id.map);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMap.onCreate(mapViewBundle);

        mMap.getMapAsync(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 225);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMap.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMap.onStop();
    }

    @Override
    public void onLowMemory() {
        mMap.onLowMemory();
        super.onLowMemory();
    }

    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, LOCATION_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED) {
                findMyPosition();
            }
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(20.65, -103.34)));
        googleMap.animateCamera(zoom);
        findMyPosition();
        googleMap.setOnMapClickListener(this::drawMarker);
    }

    private void drawMarker(LatLng latLng) {
        if (myMarker != null) myMarker.remove();
        myMarker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("marker")
                .snippet("MARKER"));
        showAddress();
        //Toast.makeText(this, myMarker.get, Toast.LENGTH_SHORT).show();
    }

    private void showAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        double latitude = myMarker.getPosition().latitude;
        double longitude = myMarker.getPosition().longitude;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            String data = "";
            if (address != null) data += address;
            if (city != null) data += " City: " + city;
            if (state != null) data += " State: " + state;
            if (country != null) data += " Country: " + country;
            if (postalCode != null) data += " postalCode: " + postalCode;
            if (knownName != null) data += " knownName: " + knownName;
            tvAddress.setText("Address: " + data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findMyPosition() {
        if (ActivityCompat.checkSelfPermission(this, LOCATION_PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
            }
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }
}