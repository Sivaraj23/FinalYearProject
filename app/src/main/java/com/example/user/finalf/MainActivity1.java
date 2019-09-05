package com.example.user.finalf;



        import android.app.Activity;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class MainActivity1 extends AppCompatActivity {
    TextView range,imp;
    String str;
    Button back;
    public static Activity Main1actt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        range=(TextView)findViewById(R.id.range);
        imp=(TextView)findViewById(R.id.imp);

        back=(Button)findViewById(R.id.back);
        Main1actt=this;

        String uris = "https://api.breezometer.com/baqi/?lat="+Asyncmapact.s.latitude+"&lon="+Asyncmapact.s.longitude+"&key=e6b9a46f05d34446931c803af4710a91";
        String urid = "https://api.breezometer.com/baqi/?lat="+Asyncmapact.d.latitude+"&lon="+Asyncmapact.d.longitude+"&key=e6b9a46f05d34446931c803af4710a91";
        try {

            JSONObject jsonObjects = getJSONObjectFromURL(uris);
            JSONObject jsonObjectd= getJSONObjectFromURL(urid);
            str=jsonObjects.getString("country_description")+" to \n"+jsonObjectd.getString("country_description");
            if(jsonObjects.getString("country_description").equalsIgnoreCase(jsonObjectd.getString("country_description")))
                imp.setText("No health impacts");
            else if(jsonObjects.getString("country_description").trim().equalsIgnoreCase("Moderate air quality")&&jsonObjectd.getString("country_description").trim().equalsIgnoreCase("Poor air quality"))
                imp.setText("Take care of your health");
            else if(jsonObjects.getString("country_description").trim().equalsIgnoreCase("Satisfactory air quality")&&jsonObjectd.getString("country_description").trim().equalsIgnoreCase("Moderate air quality"))
                imp.setText("Take care of your health");
            else if(jsonObjects.getString("country_description").trim().equalsIgnoreCase("Satisfactory air quality")&&jsonObjectd.getString("country_description").trim().equalsIgnoreCase("Poor air quality"))
                imp.setText("Take care of your health");
            else if(jsonObjects.getString("country_description").trim().equalsIgnoreCase("Good air quality")&&jsonObjectd.getString("country_description").trim().equalsIgnoreCase("Moderate air quality"))
                imp.setText("Take care of your health");
            else if(jsonObjects.getString("country_description").trim().equalsIgnoreCase("Good air quality")&&jsonObjectd.getString("country_description").trim().equalsIgnoreCase("Satisfactory air quality"))
                imp.setText("Take care of your health");
            else if(jsonObjects.getString("country_description").trim().equalsIgnoreCase("Good air quality")&&jsonObjectd.getString("country_description").trim().equalsIgnoreCase("Poor air quality"))
                imp.setText("Take care of your health");

            else
                imp.setText("You are doing good");
            range.setText(str);


        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),HomeAct.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed() {

    }
    public JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        int byteData;
        while ((byteData = br.read()) != -1) {
            stringBuilder.append((char) byteData);

        }
        br.close();

        String jsonString = sb.toString();

        return new JSONObject(stringBuilder.toString());
    }

}

