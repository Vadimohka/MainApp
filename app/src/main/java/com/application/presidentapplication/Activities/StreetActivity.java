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
import java.util.Arrays;

public class StreetActivity extends AppCompatActivity {

    ArrayList<String> streetList;
    String[] streets;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

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
        listView = findViewById(R.id.list_street);
        editText = findViewById(R.id.search_street);
        initlist();

        final Intent intent = new Intent(this, HouseActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String value = adapter.getItem(position);
                for (int i = 0; i < streets.length; i++)
                    if( value.equals(streets[i])) {

                        intent.putExtra("StreetId", i);
                        intent.putExtra("CityId", CityId);
                        intent.putExtra("DistrictId", DistrictId);
                        intent.putExtra("AreaId", AreaId);
                        startActivity(intent);
                    }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    initlist();
                } else {
                    searchItem(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void searchItem(String textToSearch){
        for(String item: streets){
            if(!item.contains(textToSearch)){
                streetList.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initlist(){
        streetList = new ArrayList<>(Arrays.asList(streets));
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem, streetList);
        listView.setAdapter(adapter);
    }

    private void insertStreetList(int AreaId, int DistrictId, int CityId)
    {
        streets = new String[SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.size()];
        for(int i = 0; i < SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.size(); i++)
        {
            streets[i] = SplashActivity.regionList.regionList.get(AreaId).districtList.get(DistrictId).cityList.get(CityId).streetList.get(i).streetName.toLowerCase();
        }
    }
}
