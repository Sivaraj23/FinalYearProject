package com.example.user.finalf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class petrol extends AppCompatActivity {

    Button b;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=(Button)findViewById(R.id.back);
        setContentView(R.layout.activity_petrol);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.co.in/maps/search/petrol+bunks");

    }

}
