package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.application.presidentapplication.R;
import com.application.presidentapplication.SpinnerAdapter1;
import java.util.ArrayList;

public class RegionActivity extends AppCompatActivity {

    ArrayList<String> areaList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        final Intent intent = new Intent(this,DistrictActivity.class);

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


    private void insertAreaList()
    {
        areaList.add("Выберите область");
        for (int i = 0; i < SplashActivity.regionList.regionList.size(); i++)
        {
            areaList.add(SplashActivity.regionList.regionList.get(i).regionName);
        }
    }
}
