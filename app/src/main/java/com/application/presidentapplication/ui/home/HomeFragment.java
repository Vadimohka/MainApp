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
import com.google.gson.Gson;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;


public class HomeFragment extends Fragment {

    Spot spot = new Spot();
    boolean flag = false;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_begin, container, false);

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getActivity().openFileInput("spot.json")));
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
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //DO NOT TOUCH
        //MAP
        Button map = root.findViewById(R.id.button_map);
        if (flag)
            map.setVisibility(View.VISIBLE);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View veiw) {
                String geoUriString = "geo:"+ spot.X + ", " + spot.Y + "?z=7";
                Uri geoUri = Uri.parse(geoUriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
        //END MAP

        Button find = root.findViewById(R.id.button_spot);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegionActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }
}