package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.application.presidentapplication.R;
import java.util.ArrayList;

public class RegionActivity extends AppCompatActivity {

    ArrayList<String> areaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        setTitle(R.string.title_region);

        final Intent intent = new Intent(this,DistrictActivity.class);
        final Intent toStreet = new Intent(this, StreetActivity.class);
        insertAreaList();
        final ListView lv = findViewById(R.id.list_ragion);

        ArrayAdapter<String> adapterDistrict = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areaList);
        lv.setAdapter(adapterDistrict);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

                if( id == 4)
                {
                    toStreet.putExtra("DistrictId", 0);
                    toStreet.putExtra("CityId", 0);
                    toStreet.putExtra("AreaId", position);
                    startActivity(toStreet);
                    finish();
                }
                else
                {
                    intent.putExtra("AreaId", position);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void insertAreaList()
    {
        for (int i = 0; i < SplashActivity.regionList.regionList.size(); i++)
        {
            areaList.add(SplashActivity.regionList.regionList.get(i).regionName);
        }
    }
}
