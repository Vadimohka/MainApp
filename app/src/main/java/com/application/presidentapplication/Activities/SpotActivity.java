package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.application.presidentapplication.JSONClass.RegionList;
import com.application.presidentapplication.JSONClass.Spot;
import com.application.presidentapplication.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class SpotActivity extends AppCompatActivity {

    Spot spot = new Spot();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput("spot.json")));
            String jsonString = br.readLine();
            Gson gson = new Gson();
            spot = gson.fromJson(jsonString, Spot.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView textViewAddress = findViewById(R.id.spotAddress);
        textViewAddress.setText(spot.address);
        TextView textViewInfo = findViewById(R.id.spotInfo);
        textViewInfo.setText(spot.spotInfo);
        TextView textViewName = findViewById(R.id.spotName);
        textViewName.setText(spot.spotName);
        TextView textViewPhoneNumber = findViewById(R.id.spotPhoneNumber);
        textViewPhoneNumber.setText(spot.phoneNumber);
        final Intent intent = new Intent(this, MainActivity.class);
        Button btnToMain = findViewById(R.id.buttonToMain);
        btnToMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        Button btnToMap = findViewById(R.id.buttonToMap);
        btnToMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String geoUriString = "geo:52.459343, 31.004553?z=2";
                Uri geoUri = Uri.parse(geoUriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }


}
