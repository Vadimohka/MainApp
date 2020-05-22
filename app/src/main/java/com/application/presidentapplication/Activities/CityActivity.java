package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.application.presidentapplication.R;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {

    ArrayList<String> cityList = new ArrayList<String>();

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
        Button button = findViewById(R.id.buttonToStreet);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.size(); i++) {
                    if ((SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).cityName + " "
                            + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(i).categoryName).equals(autoCompleteTextViewCity.getText().toString()))
                    {
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
}