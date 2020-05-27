package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.application.presidentapplication.JSONClass.Spot;
import com.application.presidentapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class HouseActivity extends AppCompatActivity {

    ArrayList<String> houseList = new ArrayList<String>();
    HashMap<String,Spot> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        Bundle arguments = getIntent().getExtras();
        final int AreaId = arguments.getInt("AreaId");
        final int DistrictId = arguments.getInt("DistrictId");
        final int CityId = arguments.getInt("CityId");
        final int StreetId = arguments.getInt("StreetId");

        insertHouseList(AreaId, DistrictId, CityId, StreetId);

        final AutoCompleteTextView autoCompleteTextViewHouse = findViewById(R.id.autocompleteHouse);
        ArrayAdapter<String> adapterHouse =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, houseList);
        autoCompleteTextViewHouse.setAdapter(adapterHouse);

        readSpotJson();
        final Intent intent = new Intent(this, MainActivity.class);
        Button button = findViewById(R.id.buttonToSpot);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String SpotId = null;
                for (int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(StreetId).houseList.size(); i++) {
                    if ((SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(StreetId).houseList.get(i).houseNumber).equals(autoCompleteTextViewHouse.getText().toString()))
                    {
                        SpotId = SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(StreetId).houseList.get(i).spotId;
                        break;
                    }
                }
                Spot spot = dictionary.get(SpotId);
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
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                startActivity(intent);
                finish();
            }
        });
    }


    public void readSpotJson() {
        try {
            InputStream f = getAssets().open("SpotList.json");
            int size = f.available();
            byte[] buffer = new byte[size];
            f.read(buffer);
            f.close();
            String JsonString = new String(buffer, "UTF-8");
            dictionary = new Gson().fromJson(JsonString, new TypeToken<HashMap<String,Spot>>(){}.getType());
            Toast.makeText(this,"Succes read Spots", Toast.LENGTH_SHORT).show();
        }catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this,"Error read Spots", Toast.LENGTH_SHORT).show();
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
