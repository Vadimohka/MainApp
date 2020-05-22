package com.application.presidentapplication.Activities;

import android.os.Bundle;


import com.application.presidentapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_candidat, R.id.navigation_news)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



//        Button spot = findViewById(R.id.button_spot);
//        spot.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                BufferedReader br = null;
//                try {
//                    br = new BufferedReader(new InputStreamReader( openFileInput("spot.json")));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                if(br != null)
//                {
//                    Intent intent = new Intent(this, SpotActivity.class);
//                    startActivity(intent);
//                }
//                else
//                {
//                    Intent intent = new Intent(this, RegionActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }
}