package com.knaas.thetravelmate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.knaas.thetravelmate.Activity.EditProfile;
import com.knaas.thetravelmate.Activity.FrontActivity;
import com.knaas.thetravelmate.Activity.LoginActivity;
import com.knaas.thetravelmate.Activity.ProfileActivity;
import com.knaas.thetravelmate.Activity.SearchActivity;
import com.knaas.thetravelmate.R;
import com.knaas.thetravelmate.SearchPlaces;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

     ImageView logOutB;
     TextView currentLoc;
     CardView locationCard, tourismCard, searchCard, interestCard, websiteCard, profileCard;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logOutB = findViewById(R.id.logOutB);
        currentLoc = findViewById(R.id.currentLoc);

        locationCard = findViewById(R.id.locationCard);
        tourismCard = findViewById(R.id.tourismCard);
        searchCard = findViewById(R.id.searchCard);
        interestCard = findViewById(R.id.interestsCard);
        websiteCard = findViewById(R.id.websiteCard);
        profileCard = findViewById(R.id.profileCard);


        logOutB.setOnClickListener(this);

        locationCard.setOnClickListener(this);
        tourismCard.setOnClickListener(this);
        searchCard.setOnClickListener(this);
        interestCard.setOnClickListener(this);
        websiteCard.setOnClickListener(this);
        profileCard.setOnClickListener(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(Dashboard.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // when permission granted
            getLocation();
        } else {
            // when permission denied
            ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
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
                        Geocoder geocoder = new Geocoder(Dashboard.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        currentLoc.setText(
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logOutB:
            startActivity(new Intent(this, LoginActivity.class));
            break;

            case R.id.locationCard:
                startActivity(new Intent(this,SearchActivity.class));
                break;

            case R.id.tourismCard:
                startActivity(new Intent(this,FrontActivity.class));
                break;

            case R.id.searchCard:
                startActivity(new Intent(this, SearchPlaces.class));
                break;

            case R.id.interestsCard:
//                startActivity(new Intent(this,FrontActivity.class));
                break;

            case R.id.websiteCard:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.google.com")));
                break;

            case R.id.profileCard:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }
}