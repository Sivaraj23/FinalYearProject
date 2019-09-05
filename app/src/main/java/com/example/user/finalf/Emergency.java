package com.example.user.finalf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Emergency extends AppCompatActivity {

    Button back;
    ImageView h,p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        back=(Button)findViewById(R.id.back);
        h=(ImageView)findViewById(R.id.hosp);
        p=(ImageView)findViewById(R.id.pet);

        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),hosp.class);
                startActivity(i);
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),petrol.class);
                startActivity(i);
            }
        });


    }

}
