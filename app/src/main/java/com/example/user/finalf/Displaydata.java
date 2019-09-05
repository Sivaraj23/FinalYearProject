package com.example.user.finalf;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Displaydata extends AppCompatActivity {

    public static TextView co,nox,hc,highornorm;
    Button b;
    public static Activity displaydata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydata);
        displaydata=this;

        b=(Button)findViewById(R.id.back);
        co=(TextView)findViewById(R.id.co);
        hc=(TextView)findViewById(R.id.HC);
        nox=(TextView)findViewById(R.id.Nox);
        highornorm=(TextView)findViewById(R.id.highornorm);


       b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),HomeAct.class);
                startActivity(i);
            }
        });



    }
}
