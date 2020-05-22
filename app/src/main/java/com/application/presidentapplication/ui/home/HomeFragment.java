package com.application.presidentapplication.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.application.presidentapplication.Activities.RegionActivity;
import com.application.presidentapplication.JSONClass.Spot;
import com.application.presidentapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;


public class HomeFragment extends Fragment {

    Spot spot = new Spot();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getActivity().openFileInput("spot.json")));
            if (br != null) {
                String jsonString = br.readLine();
                Gson gson = new Gson();
                spot = gson.fromJson(jsonString, Spot.class);
                br.close();
                TextView textViewAddress = root.findViewById(R.id.spotAddress);
                textViewAddress.setText(spot.address);
                TextView textViewInfo = root.findViewById(R.id.spotInfo);
                textViewInfo.setText(spot.spotInfo);
                TextView textViewName = root.findViewById(R.id.spotName);
                textViewName.setText(spot.spotName);
                TextView textViewPhoneNumber = root.findViewById(R.id.spotPhoneNumber);
                textViewPhoneNumber.setText(spot.phoneNumber);
                ImageView imageView = root.findViewById(R.id.homespot);
                imageView.setVisibility(View.GONE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Button find = root.findViewById(R.id.button_spot);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegionActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoUriString = "geo:52.459343, 31.004553?z=2";
                Uri geoUri = Uri.parse(geoUriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);

                }
            }
        });
        return root;
    }
}