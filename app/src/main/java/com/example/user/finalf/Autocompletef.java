package com.example.user.finalf;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class Autocompletef extends AppCompatActivity {

    String name;
    double lat,lon;
    LatLng temp;
    public static Activity Autocompletef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocompletef);
        Autocompletef=this;



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
                Maphome.fname=name;
                Maphome.latitude=lat;
                Maphome.longitude=lon;

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
