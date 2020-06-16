package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.application.presidentapplication.JSONClass.District;
import com.application.presidentapplication.JSONClass.Region;
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

public class CityActivity extends AppCompatActivity {

    ArrayList<String> cityList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    HashMap<String,Spot> dictionary;
    int AreaId, DistrictId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Bundle arguments = getIntent().getExtras();
        AreaId = arguments.getInt("AreaId");
        DistrictId = arguments.getInt("DistrictId");
        insertCityList(AreaId, DistrictId);

        listView = findViewById(R.id.list_city);
        editText = findViewById(R.id.search_city);

        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem, cityList);
        listView.setAdapter(adapter);

        final Intent intent = new Intent(this, StreetActivity.class);
        final Intent toMain = new Intent(this, MainActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String value = adapter.getItem(position);
                for (int i = 0; i < cityList.size(); i++) {
                    assert value != null;
                    if( value.equals(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).cityName + " "
                            + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).categoryName + " "
                            + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).cityCategory)) {
                        if (!(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).citySpotId == null)) {
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
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            startActivity(toMain);
                            finish();
                        }
                        else {
                            intent.putExtra("CityId", i);
                            intent.putExtra("DistrictId", DistrictId);
                            intent.putExtra("AreaId", AreaId);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CityActivity.this.adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
            Intent city = new Intent(this, DistrictActivity.class);
            city.putExtra("AreaId", AreaId);
            startActivity(city);
            finish();
    }

    private void insertCityList(int AreaId, int DistrictId) {
        for(int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.size(); i++)
        {
            cityList.add(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).cityName + " "
                   + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).categoryName + " "
                    + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).cityCategory);
        }
        Collections.sort(cityList);
    }

    public void readSpotJson(){
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
}