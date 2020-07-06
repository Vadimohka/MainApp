package com.application.presidentapplication.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.application.presidentapplication.Activities.RegionActivity;
import com.application.presidentapplication.JSONClass.Spot;
import com.application.presidentapplication.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class HomeFragment extends Fragment {

    Spot spot = new Spot();
    boolean flag = false;
    MapView mMapView;
    private GoogleMap googleMap;
    Button find;
    ImageView homespot;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getActivity().openFileInput("spot.json")));
            String jsonString = br.readLine();
            Gson gson = new Gson();
            spot = gson.fromJson(jsonString, Spot.class);
            br.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView textViewAddress = root.findViewById(R.id.spotAddress);
        TextView textViewInfo = root.findViewById(R.id.spotInfo);
        mMapView = root.findViewById(R.id.gmap);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        find = root.findViewById(R.id.button_spot);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegionActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        if (flag) {
            textViewAddress.setText(spot.address);
            textViewInfo.setText(spot.spotInfo);

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //DO NOT TOUCH
            //MAP
            Button BigMap = root.findViewById(R.id.button_map);
            BigMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View veiw) {
                    Uri uri = Uri.parse("yandexnavi://build_route_on_map?lat_to=" + spot.X +"&lon_to=" + spot.Y);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("ru.yandex.yandexnavi");

                    PackageManager packageManager = getActivity().getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe)
                    {
                        startActivity(intent);
                    }
                    else
                    {
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + spot.X + ", " + spot.Y));
                        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    }
                }
            });
            //END MAP

        }
        else
        {
            homespot = root.findViewById(R.id.homespot);
            homespot.setVisibility(View.VISIBLE);

            CardView cardView = root.findViewById(R.id.cardView);
            cardView.setVisibility(View.GONE);
            mMapView.setVisibility(View.GONE);
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                try {
                    // For dropping a marker at a point on the Map
                    LatLng spotCords = new LatLng(Double.parseDouble(spot.X), Double.parseDouble(spot.Y));
                    googleMap.addMarker(new MarkerOptions().position(spotCords).title(spot.spotName));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(spotCords).zoom(17).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}