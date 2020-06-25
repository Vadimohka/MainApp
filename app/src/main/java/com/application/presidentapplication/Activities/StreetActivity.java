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

import com.application.presidentapplication.Adapters.StreetAdapter;
import com.application.presidentapplication.JSONClass.Street;
import com.application.presidentapplication.R;

public class StreetActivity extends AppCompatActivity {

    ArrayAdapter<Street> adapter;
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

        // autocomplete street
        listView = findViewById(R.id.list_street);
        editText = findViewById(R.id.search_street);

        adapter = new StreetAdapter(this, SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList);
        listView.setAdapter(adapter);

        final Intent intent = new Intent(this, HouseActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Street value = adapter.getItem(position);
                for (int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.size(); i++) {
                    assert value != null;
                    if( (value.streetName + value.streetType ).equals(SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetName
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

}
