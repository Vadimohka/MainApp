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

import com.application.presidentapplication.JSONClass.Region;
import com.application.presidentapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DistrictActivity extends AppCompatActivity {

    ArrayList<String> districtList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    int AreaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        setTitle(R.string.title_district);
        final Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        AreaId = arguments.getInt("AreaId");
        insertDistrictList(AreaId);

        listView = findViewById(R.id.list_district);
        editText = findViewById(R.id.search_district);

        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem, districtList);
        listView.setAdapter(adapter);

        final Intent intent = new Intent(this, CityActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String value = adapter.getItem(position);
                for (int i = 0; i < districtList.size(); i++) {
                    assert value != null;
                    if( value.equals(SplashActivity.regionList.regionList.get(AreaId).districtList.get(i).districtName))
                    {
                        intent.putExtra("DistrictId", i);
                        intent.putExtra("AreaId",AreaId);
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
                DistrictActivity.this.adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RegionActivity.class);
        startActivity(intent);
        finish();
    }

    private void insertDistrictList(int AreaId) {
        for (int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.size(); i++) {
            districtList.add(SplashActivity.regionList.regionList.get(AreaId).districtList.get(i).districtName);
        }
        Collections.sort(districtList);
    }
}
