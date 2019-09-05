package com.example.user.finalf;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class HomeAct extends AppCompatActivity {

    ImageView car,health,path;
    Button back;

    //buffer read
    static byte[] buffer = new byte[1024];
    static int bytes;
    static String strReceived = "null";
    String address = null;
    static ProgressDialog progress;
    static BluetoothAdapter myBluetooth = null;
    static BluetoothSocket btSocket = null;
    static  boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static Activity HomeAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        back=(Button)findViewById(R.id.back);
        HomeAct=this;


        path=(ImageView)findViewById(R.id.path);
        car=(ImageView)findViewById(R.id.car);
        health=(ImageView)findViewById(R.id.health);



        Intent newint = getIntent();
        address = newint.getStringExtra(Bluetooth.EXTRA_ADDRESS); //receive the address of the bluetooth device
        new HomeAct.ConnectBT().execute();//Call the class to connect




        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Maphome.class);
                startActivity(i);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Emergency.class);
                startActivity(i);
            }
        });
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Displaydata.class);
                startActivity(i);
            }
        });


    }
    public static String readData() {
        if (btSocket!=null) {
            try {

                bytes = btSocket.getInputStream().read(buffer);
                strReceived = new String(buffer, 0, bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strReceived;
    }
    public void logout_click(View view)
    {
        Toast.makeText(getApplicationContext(),"Service stopped",Toast.LENGTH_SHORT).show();
        stopService(new Intent(getBaseContext(), Backgroud.class));
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        Intent in=new Intent(this,LoginActivity.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(HomeAct.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                    startService(new Intent(getBaseContext(), Backgroud.class));
                   // Toast.makeText(getApplicationContext(),"Service started",Toast.LENGTH_SHORT).show();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                Toast.makeText(HomeAct.this,"Connection Failed. Is it a SPP bluetooth? Try again.", Toast.LENGTH_SHORT).show();
                Intent i=new Intent (HomeAct.this,Bluetooth.class);
                //startActivity(i);
                startService(new Intent(getBaseContext(), Backgroud.class));



            }
            else
            {
                Toast.makeText(HomeAct.this,"Connected.", Toast.LENGTH_SHORT).show();
                startService(new Intent(getBaseContext(), Backgroud.class));

            }
            progress.dismiss();
        }

    }

}
