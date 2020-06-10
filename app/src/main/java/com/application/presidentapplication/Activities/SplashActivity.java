package com.application.presidentapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        ReadModelString();
    }


    private void ReadModelString() {

        try {
            InputStream f = getAssets().open("testAll.json");
            int size = f.available();
            byte[] buffer = new byte[size];
            f.read(buffer);
            f.close();
            String JsonString = new String(buffer, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            regionList = gson.fromJson(JsonString, RegionList.class);
            Toast.makeText(SplashActivity.this, "Secces", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(SplashActivity.this, "Error read file", Toast.LENGTH_SHORT).show();
        }
    }
}