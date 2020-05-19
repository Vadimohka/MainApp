package com.example.presidentapplication;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ReadMainString();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_candidat, R.id.navigation_news)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        GetJsonCity();
        GetJsonStreet();
    }


    public static ArrayList<String> citylist = new ArrayList<>();
    public static ArrayList<String> streetlist = new ArrayList<>();
    public static String mainString;
    public static String spotId = "";

    private void ReadMainString()
    {
        try {
            InputStream fis = getAssets().open(".json");
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            mainString = new String(buffer, "UTF-8");
            Toast.makeText(MainActivity.this, "Succes", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error read file", Toast.LENGTH_SHORT).show();
        }
    }



    private void GetJsonCity()
    {
        String jsonCity;
        try
        {
            InputStream isC = getAssets().open("Cities.json");
            int sizeC = isC.available();
            byte[] bufferC = new byte[sizeC];
            isC.read(bufferC);
            isC.close();

            jsonCity = new String(bufferC, "UTF-8");
            JSONArray jsonCityArray = new JSONArray(jsonCity);

            for (int i = 0; i < jsonCityArray.length(); i++)
            {
                JSONObject obj = jsonCityArray.getJSONObject(i);
                citylist.add(obj.getString("CityName"));
            }

        }catch(IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void GetJsonStreet()
    {
        String jsonStreet;
        try
        {
            InputStream isS = getAssets().open("Streets.json");
            int sizeS = isS.available();
            byte[] bufferS = new byte[sizeS];
            isS.read(bufferS);
            isS.close();

            jsonStreet = new String(bufferS, "UTF-8");
            JSONArray jsonStreetArray = new JSONArray(jsonStreet);

            for (int i = 0; i < jsonStreetArray.length(); i++)
            {
                JSONObject obj = jsonStreetArray.getJSONObject(i);
                streetlist.add(obj.getString("StreetName"));
            }


        }catch(IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }
}