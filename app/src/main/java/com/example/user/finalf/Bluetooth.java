package com.example.user.finalf;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth extends AppCompatActivity {

    Button btnPaired;
    ListView devicelist;
    //bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);

        //Calling widgets
        btnPaired = (Button)findViewById(R.id.button);
        devicelist = (ListView)findViewById(R.id.listView);

        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null)
        {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            //finish();
        }
        else if(!myBluetooth.isEnabled())
        {

            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }
        SharedPreferences prefs = getSharedPreferences("siva", MODE_PRIVATE);
        String restoredText = prefs.getString("blue", null);
        if (restoredText != null) {
            String name = prefs.getString("blue", null);//"No name defined" is the default value.
            //int idName = prefs.getInt("idName", 0); //0 is the default value.
            Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Bluetooth.this,HomeAct.class);

            startService(new Intent(getBaseContext(), Backgroud.class));
            //Change the activity.
            //Toast.makeText(this, "lalalal", Toast.LENGTH_LONG).show();
            i.putExtra(EXTRA_ADDRESS, name); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pairedDevicesList();
            }
        });
    }
    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity.
            SharedPreferences.Editor editor = getSharedPreferences("siva", MODE_PRIVATE).edit();
            editor.putString("blue", address);
            editor.apply();
          Intent i = new Intent(getApplicationContext(),HomeAct.class);

            //Change the activity.
            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
    };
    public static void printmsg(String a, Context th){
        Toast.makeText(th,a, Toast.LENGTH_SHORT).show();
    }


}
