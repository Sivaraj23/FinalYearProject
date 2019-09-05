package com.example.user.finalf;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maphome extends AppCompatActivity {

    Button b;
    EditText f, t;
    static  double flat,flon,tlat,tlon;
    String result,fro,tow;
    static String fname,tname;




public static Activity maphome;
    static double latitude,longitude;
    static int flag=1;
Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maphome);

        maphome=this;
        b = (Button) findViewById(R.id.search);
        f = (EditText) findViewById(R.id.fe);
        t = (EditText) findViewById(R.id.te);

back=(Button)findViewById(R.id.back);



            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            try {
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                String result = null;
            } catch (Exception e) {
                //e.printStackTrace();
                //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append("\n");
                    }
                    //Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
                    //sb.append(address.getLocality()).append("\n");
                    //sb.append(address.getPostalCode()).append("\n");
                    sb.append(address.getLocality());
                    result = sb.toString();
                    //Toast.makeText(getApplicationContext(), "Your current adrress:" + result, Toast.LENGTH_LONG).show();
                    fname=result;
                 }
                } catch (Exception e) {
                //e.printStackTrace();
            }



            //t.requestFocus();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //---------------------------------------------------------------------------------------------
                Intent i = new Intent(getApplicationContext(), Autocompletef.class);
                startActivity(i);
                flag = 0;
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),HomeAct.class);
                startActivity(i);
            }
        });

        t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //---------------------------------------------------------------------------------------------
                Intent i = new Intent(getApplicationContext(), Autocompletet.class);
                startActivity(i);
                flag = 0;
                return false;
            }
        });


        t.setText(tname);
        f.setText(fname);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(f.getText().toString().equals("Yourlocation"))
                    {




                    MapsActivity m = new MapsActivity();
                    Asyncmapact asy = new Asyncmapact();


                        LatLng n = new LatLng(latitude,longitude);
                        LatLng d = new LatLng(tlat,tlon);

                    asy.setD(d);
                    asy.setS(n);



                }else{



                        MapsActivity m = new MapsActivity();
                        Asyncmapact asy = new Asyncmapact();

                        LatLng n = new LatLng(latitude,longitude);
                        LatLng d = new LatLng(tlat,tlon);

                        asy.setD(d);
                        asy.setS(n);





                    }
                    Intent i = new Intent(Maphome.this, Asyncmapact.class);
                    //i.putExtra("str",str);
                    startActivity(i);

                }


                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter valid details",Toast.LENGTH_SHORT).show();
                }
            }

        });




    } @Override
    public void onBackPressed() {

    }



}