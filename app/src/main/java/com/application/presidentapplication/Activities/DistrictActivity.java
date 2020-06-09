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

public class DistrictActivity extends AppCompatActivity {

    ArrayList<String> listItems;
    String[] districts;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        final Bundle arguments = getIntent().getExtras();
        final int AreaId = arguments.getInt("AreaId");
        insertDistrictList(AreaId);

        listView = findViewById(R.id.list_district);
        editText = findViewById(R.id.search_district);
        initlist();

        final Intent intent = new Intent(this, CityActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String value = adapter.getItem(position);
                for (int i = 0; i < districts.length; i++)
                    if( value.equals(districts[i]))
                    {
                        intent.putExtra("DistrictId", i);
                        intent.putExtra("AreaId",AreaId);
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
        for(String item: districts){
            if(!item.contains(textToSearch)){
                listItems.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initlist(){
        listItems = new ArrayList<>(Arrays.asList(districts));
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
    }

    private void insertDistrictList(int id) {
        districts = new String[SplashActivity.regionList.regionList.get(id).districtList.size()];
        for (int j = 0; j < SplashActivity.regionList.regionList.get(id).districtList.size(); j++) {
            districts[j] = SplashActivity.regionList.regionList.get(id).districtList.get(j).districtName.toLowerCase();
        }
    }
}
