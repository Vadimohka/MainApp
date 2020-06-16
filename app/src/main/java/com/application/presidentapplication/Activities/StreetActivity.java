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
import com.application.presidentapplication.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class StreetActivity extends AppCompatActivity {

    ArrayList<String> streetList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    int AreaId, DistrictId, CityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        AreaId = arguments.getInt("AreaId");
        DistrictId = arguments.getInt("DistrictId");
        CityId = arguments.getInt("CityId");
        insertStreetList(AreaId, DistrictId, CityId);

        // autocomplete street
        listView = findViewById(R.id.list_street);
        editText = findViewById(R.id.search_street);

        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem, streetList);
        listView.setAdapter(adapter);

        final Intent intent = new Intent(this, HouseActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String value = adapter.getItem(position);
                for (int i = 0; i < streetList.size(); i++) {
                    assert value != null;
                    if( value.equals(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetName + " "
                            + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetType)) {

                        intent.putExtra("StreetId", i);
                        intent.putExtra("CityId", CityId);
                        intent.putExtra("DistrictId", DistrictId);
                        intent.putExtra("AreaId", AreaId);
                        startActivity(intent);
                        finish();
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
                StreetActivity.this.adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (AreaId == 4)
        {
            Intent intent = new Intent(this, RegionActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, CityActivity.class);
            intent.putExtra("DistrictId", DistrictId);
            intent.putExtra("AreaId", AreaId);
            startActivity(intent);
            finish();
        }
    }

    private void insertStreetList(int AreaId, int DistrictId, int CityId)
    {
        for(int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.size(); i++)
        {
            streetList.add(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetName + " "
                    + SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetType );
        }
        Collections.sort(streetList);
    }
}
