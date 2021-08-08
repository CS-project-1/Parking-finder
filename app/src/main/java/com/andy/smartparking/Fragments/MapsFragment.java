package com.andy.smartparking.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.andy.smartparking.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;


public class MapsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull @NotNull LatLng latLng) {
//                        initialize marker options
                        MarkerOptions markerOptions = new MarkerOptions();
//                        set position of marker
                        markerOptions.position(latLng);
//                        Set title of marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

//                        Remove all marker
                        googleMap.clear();
//                        Animating to zoom marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//Add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return view;
    }
}