package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.study.sanpablook.R;

public class DineSulyapActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageButton btnShare, btnBack;
    Button reserveNowBtn;

    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_sulyap);

        //Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsSulyapDine);

        if(mapFragment !=null){
            mapFragment.getMapAsync(this);
        }


        //buttons
        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        reserveNowBtn = findViewById(R.id.reserveNowBtn1);

        //back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Share this App";
                String Sub = "https://maps.app.goo.gl/iwvYd52dxCLYYhtd7";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });


        //reserve now button
        reserveNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DineSulyapActivity.this, DineReservationActivity.class);
                intent.putExtra("documentId", "SulyapGalleryCafe");
                intent.putExtra("imagePath", "estabProfilePictures/sulyapProfile.jpg");
                startActivity(intent);
            }
        });
    }
    //message button
    public void btnMessageSulyap(View v) {
        String number = "09171821483";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

    //google Maps location
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng latlng = new LatLng(14.076609886358636, 121.31264046702333);
        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latlng, 13);
        map.animateCamera(location);

        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        MarkerOptions options = new MarkerOptions().position(latlng).title("Sulyap Gallery and Cafe");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        map.addMarker(options);
    }
}