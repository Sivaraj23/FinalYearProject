package com.example.user.finalf;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.user.finalf.LoginActivity.MyPREFERENCES;


/**
 * Created by SivarajP on 2/15/2018.
 */

public class Backgroud extends Service {
    @Nullable
    static int count=0;
    int num=0,newdt=0;
    static String value = "2222";
    long  avgco,avghc,avgnox,countlong=0;
    public Context context = this;
    String email;
    public Handler handler = null;
    public static Runnable runnable = null;
    /** indicates how to behave if the service is killed */
            int mStartMode;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    /** Called when the service is being created. */
    @Override
    public void onCreate() {



        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if(count==0){
                    count=1;
                    HomeAct.readData().trim();
                    //senddt("0");
                    sendNotification("TRAVELGREEN");
                }
                else if(count == 1){

                    String s= HomeAct.readData().trim();
                    senddt(s);
                    printtext(s);
                    count=2;
                }
                else {
                    try {
                        String str =HomeAct.readData().trim();
                         printtext(str);
                         int val[]=new int[3];

                        List<String> mylist= Arrays.asList(str.split(","));
                        int i=0;
                        for (String num : mylist) {
                            //System. out. println(num);
                            //Toast.makeText(getApplicationContext(),num.trim().substring(0, num.length() - 3),Toast.LENGTH_SHORT).show();
                            val[i]=Integer.parseInt(num.trim().substring(0, num.length() - 3));
                            Toast.makeText(getApplicationContext(),val[i]+"",Toast.LENGTH_SHORT).show();
                            i++;
                            //Toast.makeText(getApplicationContext(),val[i-1],Toast.LENGTH_SHORT).show();
                        }

                        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                        String restoredText = prefs.getString("namekey", null);
                        if (restoredText != null) {
                             email = prefs.getString("namekey", "No name defined");//"No name defined" is the default value.
                            //int idName = prefs.getInt("idName", 0); //0 is the default value.
                        }
                       // Toast.makeText(getApplicationContext(),email+"  "+restoredText,Toast.LENGTH_SHORT).show();

                        String disp="";
                        if(val[0]>700||val[1]>1000||val[2]>1000) {
                            disp = "YOU ARE POLLUTING HIGH";

                            countlong++;
                            avgnox=(avgnox+val[0])/countlong;
                            avgco=(avgco+val[1])/countlong;
                            avghc=(avghc+val[2])/countlong;
                            sendNotification(disp);
                            String high="high";
                            Displaydata.highornorm.setText(high);
                            Displaydata.co.setText(avgco+"");//7   HC
                            Displaydata.hc.setText(avghc+"");//2   CO
                            Displaydata.nox.setText(avgnox+"");//135    NOX
                            String url="http://ezeelearn.com/alteruser.php?value="+val[0]+"&value1="+val[1]+"&value2="+val[2]+"&level="+high+"&username="+email;

                            HTTPDataHandler hh = new HTTPDataHandler(getApplicationContext());
                            String  stream = hh.GetHTTPData(url);
                            String status="";
                            try{

                                JSONObject reader= new JSONObject(stream);
                                JSONArray jsonArray = reader.optJSONArray("store");

                                if(jsonArray.length()>0) // check for data from remote server
                                {
                                    for(int y=0; y < jsonArray.length(); y++)
                                    {
                                        JSONObject jsonObject = jsonArray.getJSONObject(y);
                                        status = jsonObject.optString("status").toString();
                                    }
                                }
                                Toast.makeText(getBaseContext(), "Sever Response :"+status , Toast.LENGTH_LONG).show();
                            }catch(JSONException ee)
                            {
                                Toast.makeText(getBaseContext(), "Error GetResponse "+ee.getMessage() , Toast.LENGTH_LONG).show();
                            }
                            //tv1.setText(status); // display server response
                        }
                        else {
                            disp = "Normal";

                            countlong++;
                            avgnox=(avgnox+val[0])/countlong;
                            avgco=(avgco+val[1])/countlong;
                            avghc=(avghc+val[2])/countlong;

                            Displaydata.highornorm.setText(disp);
                            Displaydata.co.setText(avgco+"");
                            Displaydata.hc.setText(avghc+"");
                            Displaydata.nox.setText(avgnox+"");
                            String url="http://ezeelearn.com/alteruser.php?value="+val[0]+"&value1="+val[1]+"&value2="+val[2]+"&level="+disp+"&username="+email;
                            //Toast.makeText(getBaseContext(), "http://ezeelearn.com/alteruser.php?value="+val[0]+"&value1="+val[1]+"&value2="+val[2]+"&level="+disp+"&username="+email , Toast.LENGTH_LONG).show();
                            HTTPDataHandler hh = new HTTPDataHandler(getApplicationContext());
                            String  stream = hh.GetHTTPData(url);
                            //Toast.makeText(getBaseContext(), stream , Toast.LENGTH_LONG).show();
                            String status="";
                            try{

                                JSONObject reader= new JSONObject(stream);
                                JSONArray jsonArray = reader.optJSONArray("store");

                                if(jsonArray.length()>0) // check for data from remote server
                                {
                                    for(int h=0; h < jsonArray.length(); h++)
                                    {
                                        JSONObject jsonObject = jsonArray.getJSONObject(h);
                                        status = jsonObject.optString("status");
                                    }
                                }
                                Toast.makeText(getBaseContext(), "Sever Response :"+status , Toast.LENGTH_LONG).show();
                            }catch(JSONException ee)
                            {
                                Toast.makeText(getBaseContext(), "Error GetResponse "+ee.getMessage() , Toast.LENGTH_LONG).show();
                            }
                            //tv1.setText(status); // display server response
                            sendNotification(disp);
                        }

                    }
                    catch (Exception e)
                    {
                        printtext("error"+e);
                    }
                }
                    handler.postDelayed(runnable, 10000);

            }
        };

        handler.postDelayed(runnable, 10000);
    }

    public  void sendNotification(String text) {

        //Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.finallogo)
                        .setContentTitle("Pollution status: ")
                        .setOngoing(true)
                        .setContentText(text);



        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//

        mNotificationManager.notify(001, mBuilder.build());
    }

    public void senddt(String st){

        Bluetooth.printmsg(st,this);
    }

    public String recvdt(){

       return "siva";
    }

    public int avg(int a, int b){
        return ((a+b)/2);
    }

    void printtext(String p)
    {
        Toast.makeText(this, p, Toast.LENGTH_LONG).show();
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return mStartMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

    }
}
