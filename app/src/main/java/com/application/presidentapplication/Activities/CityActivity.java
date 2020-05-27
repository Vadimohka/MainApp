package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class CityActivity extends AppCompatActivity {

    ArrayList<String> cityList = new ArrayList<String>();
    HashMap<String,Spot> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Bundle arguments = getIntent().getExtras();
        final int AreaId = arguments.getInt("AreaId");
        final int DistrictId = arguments.getInt("DistrictId");
        insertCityList(AreaId, DistrictId);

        // autocomlpete city
        final AutoCompleteTextView autoCompleteTextViewCity = findViewById(R.id.autocompleteCity);
        ArrayAdapter<String> adapterCity =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cityList);
        autoCompleteTextViewCity.setAdapter(adapterCity);


        final Intent intent = new Intent(this, StreetActivity.class);
        final Intent toMain = new Intent(this, MainActivity.class);
        Button button = findViewById(R.id.buttonToStreet);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.size(); i++) {
                    if ((SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).cityName + " "
                            + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).categoryName).equals(autoCompleteTextViewCity.getText().toString()))
                    {
                        if (!(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).citySpotId == null))
                        {
                            readSpotJson();
                            Spot spot = dictionary.get(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).citySpotId);
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
                            startActivity(toMain);
                            finish();
                        }
                        intent.putExtra("CityId", i);
                        break;
                    }

                }
                intent.putExtra("DistrictId", DistrictId);
                intent.putExtra("AreaId", AreaId);
                startActivity(intent);
            }
        });
    }

    private void insertCityList(int AreaId, int DistrictId)
    {
        for(int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.size(); i++)
        {
            cityList.add(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).cityName +  " " +
                    SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).categoryName);
        }
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
}