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

public class DistrictActivity extends AppCompatActivity {

    ArrayList<String> districtList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        final Bundle arguments = getIntent().getExtras();
        final int AreaId = arguments.getInt("AreaId");
        insertDistrictList(AreaId);
        //create input region
        final AutoCompleteTextView autoCompleteTextViewDistrict = findViewById(R.id.autocompleteDistrict);
        ArrayAdapter<String> adapterDistrict =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, districtList);
        autoCompleteTextViewDistrict.setAdapter(adapterDistrict);

        final Intent intent = new Intent(this, CityActivity.class);

        Button button = findViewById(R.id.buttonToCity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for(int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.size(); i++)
                {
                    if( SplashActivity.regionList.regionList.get(AreaId).districtList.get(i).districtName.equals(autoCompleteTextViewDistrict.getText().toString()))
                    {
                        intent.putExtra("DistrictId", i);
                        break;
                    }
                }
                intent.putExtra("AreaId", AreaId);
                startActivity(intent);

            }
        });
    }
    private void insertDistrictList(int id)
    {
        for(int j = 0; j < SplashActivity.regionList.regionList.get(id).districtList.size(); j++)
        {
            districtList.add(SplashActivity.regionList.regionList.get(id).districtList.get(j).districtName);
        }
    }
}
