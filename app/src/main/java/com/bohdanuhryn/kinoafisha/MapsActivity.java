package com.bohdanuhryn.kinoafisha;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.bohdanuhryn.kinoafisha.model.data.Cinema;
import com.bohdanuhryn.kinoafisha.model.data.CinemaMapMarker;
import com.bohdanuhryn.kinoafisha.model.converters.CinemaMapMarkerConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String CINEMA_ARG = "cinema_arg";

    private GoogleMap googleMap;

    private Cinema cinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        readArguments();
        setupMapFragment();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setupMapMarker();
    }

    private void readArguments() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            cinema = (Cinema)args.getSerializable(CINEMA_ARG);
        }
    }

    private void setupMapFragment() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupMapMarker() {
        CinemaMapMarker mapMarker = CinemaMapMarkerConverter.convert(cinema);
        googleMap.addMarker(new MarkerOptions().position(mapMarker.position).title(mapMarker.name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mapMarker.position));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }
}
