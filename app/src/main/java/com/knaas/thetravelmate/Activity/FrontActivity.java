package com.knaas.thetravelmate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.knaas.thetravelmate.R;
import com.knaas.thetravelmate.SearchPlaces;
import com.knaas.thetravelmate.adapter.RecentsAdapter;
import com.knaas.thetravelmate.adapter.TopPlacesAdapter;
import com.knaas.thetravelmate.model.RecentsData;
import com.knaas.thetravelmate.model.TopPlacesData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FrontActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView upcoming,tvCurrentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        tvCurrentLocation=findViewById(R.id.tvCrrentLocation);
        upcoming = findViewById(R.id.upcoming);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontActivity.this, SearchActivity.class));
            }
        });

        List<RecentsData> recentsDataList = new ArrayList<>();
        recentsDataList.add(new RecentsData("Manali","India","From 4000 INR",R.drawable.manali));
        recentsDataList.add(new RecentsData("Udaipur","India","From 3000 INR",R.drawable.udaipur));
        recentsDataList.add(new RecentsData("Rishikesh","India","From 2000 INR",R.drawable.rishikesh));
        recentsDataList.add(new RecentsData("Andaman-Nikobar","India","From 8000 INR",R.drawable.andaman));
        recentsDataList.add(new RecentsData("Goa","India","From 10000 INR",R.drawable.goa));
        recentsDataList.add(new RecentsData("Ladakh","India","From 3000 INR",R.drawable.ladakh));

        setRecentRecycler(recentsDataList);

        List<TopPlacesData> topPlacesDataList = new ArrayList<>();
        topPlacesDataList.add(new TopPlacesData("Agra","India","700 INR - 2000 INR",R.drawable.agra));
        topPlacesDataList.add(new TopPlacesData("New Delhi","India","1000 INR - 5000 INR",R.drawable.delhi));
        topPlacesDataList.add(new TopPlacesData("Mumbai","India","4000 INR - 1000 INR",R.drawable.mumbaitwo));
        topPlacesDataList.add(new TopPlacesData("Rajasthan","India","3000 INR - 5000 INR",R.drawable.rajasthan2));
        topPlacesDataList.add(new TopPlacesData("Varanasi","India","1000 INR - 5000 INR",R.drawable.varanashi));
        topPlacesDataList.add(new TopPlacesData("Amritsar","India","2000 INR - 5000 INR",R.drawable.amritsar));
        topPlacesDataList.add(new TopPlacesData("Kerala","India","5000 INR - 7000 INR",R.drawable.kerala));
        topPlacesDataList.add(new TopPlacesData("Darjeeling","India","5000 INR - 9000 INR",R.drawable.darjeeling));
        topPlacesDataList.add(new TopPlacesData("Kolkata","India","5000 INR - 7000 INR",R.drawable.kolkata));
        topPlacesDataList.add(new TopPlacesData("Mysore","India","6000 INR - 10000 INR",R.drawable.mysore));

        setTopPlacesRecycler(topPlacesDataList);



        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        finish();
                        startActivity(getIntent());
                        finish();
                        break;

                    case R.id.search:
                        startActivity(new Intent(FrontActivity.this, SearchPlaces.class));
                        break;

                    case R.id.chat:
                        startActivity(new Intent(FrontActivity.this, ChatActivity.class));
                        break;

                    case R.id.profile:
                        startActivity(new Intent(FrontActivity.this, ProfileActivity.class));
                        break;
                }
                return true;
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(FrontActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // when permission granted
            getLocation();
        } else {
            // when permission denied
            ActivityCompat.requestPermissions(FrontActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList) {
        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlacesAdapter(this, topPlacesDataList);
        topPlacesRecycler.setAdapter(topPlacesAdapter);
    }

    private void setRecentRecycler(List<RecentsData> recentsDataList) {
        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycler.setAdapter(recentsAdapter);
    }

    // get location method will start here
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){
                    try {
                        Geocoder geocoder = new Geocoder(FrontActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        tvCurrentLocation.setText(
                                addresses.get(0).getLocality()
                        );

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.three_dot_menu, menu);
        return true;

    }
}