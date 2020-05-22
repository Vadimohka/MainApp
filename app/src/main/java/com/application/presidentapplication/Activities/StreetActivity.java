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

public class StreetActivity extends AppCompatActivity {

    ArrayList<String> streetList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street);

        Bundle arguments = getIntent().getExtras();
        final int AreaId = arguments.getInt("AreaId");
        final int DistrictId = arguments.getInt("DistrictId");
        final int CityId = arguments.getInt("CityId");
        insertStreetList(AreaId, DistrictId, CityId);

        // autocomplete street
        final AutoCompleteTextView autoCompleteTextViewStreet = findViewById(R.id.autocompleteStreet);
        ArrayAdapter<String> adapterStreet =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, streetList);
        autoCompleteTextViewStreet.setAdapter(adapterStreet);

        final Intent intent = new Intent(this, HouseActivity.class);
        Button button = findViewById(R.id.buttonToHouse);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < MainActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.size(); i++)
                {
                    if ( MainActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetName.equals(autoCompleteTextViewStreet.getText().toString()))

                    {
                        intent.putExtra("StreetId", i);
                        break;
                    }
                }
                intent.putExtra("CityId", CityId);
                intent.putExtra("DistrictId", DistrictId);
                intent.putExtra("AreaId", AreaId);
                startActivity(intent);
            }
        });
    }

    private void insertStreetList(int AreaId, int DistrictId, int CityId)
    {
        for(int i = 0; i < MainActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.size(); i++)
        {
            streetList.add(MainActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetName);
        }
    }
}
