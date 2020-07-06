package com.application.presidentapplication.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.application.presidentapplication.JSONClass.RegionList;
import com.application.presidentapplication.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SplashActivity extends AppCompatActivity {

    public static RegionList regionList = new RegionList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        ReadModelString();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }


    private void ReadModelString() {

        try {
            InputStream f = getAssets().open("NewJson.json");
            int size = f.available();
            byte[] buffer = new byte[size];
            f.read(buffer);
            f.close();
            String JsonString = new String(buffer, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            regionList = gson.fromJson(JsonString, RegionList.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}