package com.example.ivan.sunrisesunset.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.sunrisesunset.R;
import com.example.ivan.sunrisesunset.entity.SunInfoResponse;
import com.example.ivan.sunrisesunset.utils.Resource;
import com.example.ivan.sunrisesunset.utils.StatusType;
import com.example.ivan.sunrisesunset.viewModel.SunriseSunsetViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SunriseSunsetActivity extends AppCompatActivity {

    private SunriseSunsetViewModel viewModel;
    private ProgressBar mProgressBar;
    private TextView mTextViewSunrise, mTextViewSunset;
    private FusedLocationProviderClient mFusedLocationClient;
    private PlaceAutocompleteFragment mAutocompleteFragment;
    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunrise_sunset);
        initPlaceAutocomplete();
        initUi();
        getCurrentLocation();
        viewModel = ViewModelProviders.of(this).get(SunriseSunsetViewModel.class);
        viewModel.inject();
    }

    private void initPlaceAutocomplete() {
        mAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        mAutocompleteFragment.setFilter(typeFilter);
        mAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                viewModel.getSunInfo(place.getLatLng().latitude, place.getLatLng().longitude);
                viewModel.sunInfo.observe(SunriseSunsetActivity.this, resource -> setSunInfo(resource));
            }

            @Override
            public void onError(Status status) {
            }
        });
    }


    private void initUi() {
        mProgressBar = findViewById(R.id.pb_sun);
        mTextViewSunrise = findViewById(R.id.tv_time_sunrise);
        mTextViewSunset = findViewById(R.id.tv_time_sunset);
    }


    private void getCurrentLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkPermission()) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            viewModel.getSunInfo(location.getLatitude(), location.getLongitude());
                            Geocoder gcd = new Geocoder(this, Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                viewModel.sunInfo.observe(SunriseSunsetActivity.this, this::setSunInfo);
                                if (addresses.size() > 0) {
                                    mAutocompleteFragment.setText(addresses.get(0).getLocality());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void setSunInfo(Resource resource) {
        if (resource.statusType == StatusType.LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else if (resource.statusType == StatusType.SUCCESS) {
            mProgressBar.setVisibility(View.GONE);
            SunInfoResponse sunInfoResponse = (SunInfoResponse) resource.data;
            if (sunInfoResponse != null) {
                mTextViewSunrise.setText(sunInfoResponse.getResults().getSunrise());
                mTextViewSunset.setText(sunInfoResponse.getResults().getSunset());
            }
        } else if (resource.statusType == StatusType.ERROR) {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) {
            return;
        }
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
                break;
        }
    }
}
