package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.application.presidentapplication.JSONClass.Spot;
import com.application.presidentapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class HouseActivity extends AppCompatActivity {

    ArrayList<String> houseList = new ArrayList<>();
    HashMap<String,Spot> dictionary;
    int AreaId, DistrictId, CityId, StreetId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        Bundle arguments = getIntent().getExtras();
        AreaId = arguments.getInt("AreaId");
        DistrictId = arguments.getInt("DistrictId");
        CityId = arguments.getInt("CityId");
        StreetId = arguments.getInt("StreetId");

        insertHouseList(AreaId, DistrictId, CityId, StreetId);
        Collections.sort(houseList);
        GridView GList = findViewById(R.id.gridview_house);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.grid_item, houseList);
        GList.setAdapter(adapter);

        final Intent intent = new Intent(this, MainActivity.class);

        GList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                readSpotJson();
                Spot spot = dictionary.get(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(StreetId).houseList.get(position).spotId);
                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(spot);
                try {
                    deleteFile("spot.json");
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            openFileOutput("spot.json", MODE_PRIVATE)));
                    // пишем данные
                    bw.write(json);
                    // закрываем поток
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StreetActivity.class);
        intent.putExtra("CityId", CityId);
        intent.putExtra("DistrictId", DistrictId);
        intent.putExtra("AreaId", AreaId);
        startActivity(intent);
        finish();
    }


    public void readSpotJson() {
        try {
            InputStream f = getAssets().open("SpotList.json");
            int size = f.available();
            byte[] buffer = new byte[size];
            f.read(buffer);
            f.close();
            String JsonString = new String(buffer, StandardCharsets.UTF_8);
            dictionary = new Gson().fromJson(JsonString, new TypeToken<HashMap<String,Spot>>(){}.getType());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    private void insertHouseList(int AreaId, int DistrictId, int CityId, int StreetId)
    {
        for(int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(StreetId).houseList.size(); i++)
        {
            houseList.add(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(StreetId).houseList.get(i).houseNumber);
        }
    }
}
