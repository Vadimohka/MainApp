package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.application.presidentapplication.JSONClass.RegionList;
import com.application.presidentapplication.R;
import com.application.presidentapplication.SpinnerAdapter1;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;

public class RegionActivity extends AppCompatActivity {

    ArrayList<String> areaList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        final Intent intent = new Intent(this,DistrictActivity.class);

        ReadModelString();

        // create spinner Area
        insertAreaList();
        final Spinner spinner1 = findViewById(R.id.spinner1);
        SpinnerAdapter adapter1 = new SpinnerAdapter1(areaList, this);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != 0) {
                    intent.putExtra("AreaId", spinner1.getSelectedItemPosition() - 1);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void ReadModelString() {

        try {
            InputStream f = getAssets().open("testAll.json");
            int size = f.available();
            byte[] buffer = new byte[size];
            f.read(buffer);
            f.close();
            String JsonString = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            MainActivity.regionList = gson.fromJson(JsonString, RegionList.class);
            Toast.makeText(RegionActivity.this, "Secces", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(RegionActivity.this, "Error read file", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertAreaList()
    {
        areaList.add("Выберите область");
        for (int i = 0; i < MainActivity.regionList.regionList.size(); i++)
        {
            areaList.add(MainActivity.regionList.regionList.get(i).regionName);
        }
    }
}
