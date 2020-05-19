package com.example.presidentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class SpotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        TextView textView = findViewById(R.id.text_spot);
        textView.setText(arguments.get("SpotId").toString());
        setContentView(R.layout.activity_spot);
    }
}
