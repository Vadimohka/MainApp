package com.application.presidentapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.application.presidentapplication.Activities.RegionActivity;
import com.application.presidentapplication.Activities.SpotActivity;
import com.application.presidentapplication.R;

import java.io.File;
import java.io.FileInputStream;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button spot = root.findViewById(R.id.button_spot);
        spot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File f = new File("spot.json");
                if (f.exists())
                {
                    Intent intent = new Intent(getActivity(), SpotActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getActivity(), RegionActivity.class);
                    startActivity(intent);
                }
            }
        });
        Button find = root.findViewById(R.id.button_find);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegionActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}