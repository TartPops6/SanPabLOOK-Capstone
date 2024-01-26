package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class DineCasaActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageButton btnShare, btnBack;
    Button reserveNowBtn, buttonViewAll;

    GoogleMap map;

    //recycler view horizontal
    RecyclerView recyclerViewDineReviewsCasa;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_casa);

        //recycler view horizontal
        recyclerViewDineReviewsCasa = findViewById(R.id.recyclerViewDineReviewsCasa);
        recyclerViewDineReviewsCasa.setLayoutManager(new LinearLayoutManager(this));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDineReviewsCasa.setLayoutManager(linearLayoutManager);

        //Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsCasaDine);

        if(mapFragment !=null){
            mapFragment.getMapAsync(this);
        }

        //buttons
        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        reserveNowBtn = findViewById(R.id.reserveNowBtn1);
        buttonViewAll = findViewById(R.id.buttonViewAll);

        //back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToViewAllReviews(view);
            }
        });

        //share button
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Share this App";
                String Sub = "https://maps.app.goo.gl/1JvXSeQFJAa4QKjMA";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        Button reserveNowBtn1 = findViewById(R.id.reserveNowBtn1);
        reserveNowBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DineCasaActivity.this, DineReservationActivity.class);
                intent.putExtra("documentId", "CasaSanPabloDine");
                intent.putExtra("imagePath", "estabProfilePictures/casaSanPabloProfile.jpg");
                startActivity(intent);
            }
        });


        //reserve now button
//        reserveNowBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(DineCasaActivity.this, com.example.capstone.DineCasaReservationActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }
    //message button
    public void btnMessageCasa(View v) {
        String number = "09178126687";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

    private void goToViewAllReviews(View view) {
        Intent intent = new Intent(this, ViewAllRatingsDine.class);
        startActivity(intent);
    }

    //google Maps location
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng latlng = new LatLng(14.071994820738484, 121.31482390935017);
        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latlng, 13);
        map.animateCamera(location);

        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        MarkerOptions options = new MarkerOptions().position(latlng).title("Casa San Pablo Bed and Breakfast");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        map.addMarker(options);
    }
}