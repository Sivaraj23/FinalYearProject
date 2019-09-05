package com.example.user.finalf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

public class Autocompletet extends AppCompatActivity {

    String name;
    double lat,lon;
    LatLng temp;
    public static Activity Autocompletet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocompletef);
        Autocompletet=this;



        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("pleaseseehere", "Place: " + place.getLatLng());
                name=place.getName().toString();
                lat=place.getLatLng().latitude;
                lon=place.getLatLng().longitude;

                Intent i=new Intent(getApplicationContext(), Maphome.class);
                Maphome.tname=name;
                Maphome.tlat=lat;
                Maphome.tlon=lon;

                startActivity(i);


            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("pleaseseehere", "An error occurred: " + status);
            }
        });



    }

}
