package com.knaas.thetravelmate.PlaceDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.knaas.thetravelmate.R;

public class NewDelhi extends AppCompatActivity {
    TextView searchFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delhi);

        searchFlight = findViewById(R.id.flightCheck);
        searchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.makemytrip.com/flights/")));
            }
        });
    }
}