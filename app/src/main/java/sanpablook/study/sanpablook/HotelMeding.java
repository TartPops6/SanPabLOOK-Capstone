package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.study.sanpablook.R;

public class HotelMeding extends AppCompatActivity implements OnMapReadyCallback {

    ImageButton btnShare, backBtn;
    Button buttonViewAll;

    GoogleMap hotelMedingMap;

    //recycler view horizontal
    RecyclerView recyclerViewHotelReviewsMeding;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_meding);

        //recycler view horizontal
        recyclerViewHotelReviewsMeding = findViewById(R.id.recyclerViewHotelReviewsMeding);
        recyclerViewHotelReviewsMeding.setLayoutManager(new LinearLayoutManager(this));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHotelReviewsMeding.setLayoutManager(linearLayoutManager);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Maps
        SupportMapFragment medingMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.HotelMedingGoogleMaps);

        if(medingMapFragment !=null){
            medingMapFragment.getMapAsync(this);
        }

        //buttons
        backBtn= findViewById(R.id.backBtn);
        btnShare = findViewById(R.id.btnShare);
        buttonViewAll = findViewById(R.id.buttonViewAll);

        //back button
        backBtn.setOnClickListener(new View.OnClickListener() {
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
                String Sub = "http://meding_location.google.com";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        Button reserveNowBtn1 = findViewById(R.id.reserveNowBtn1);
        reserveNowBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelMeding.this, HotelReservationActivity.class);
                intent.putExtra("documentId", "TahananNiAlingMeding");
                intent.putExtra("imagePath", "estabProfilePictures/medingProfile.jpg");
                startActivity(intent);
            }
        });
        TextView stayPrice = (TextView) findViewById(R.id.stayPrice);
        String text = "<font color=#1A9AB7>₱ 3, 864</font> <font color=#000000>/ night</font>";
        stayPrice.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
    }

    private void goToViewAllReviews(View view) {
        Intent intent = new Intent(this, ViewAllRatingsHotel.class);
        startActivity(intent);
    }

    //google Maps location
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        hotelMedingMap = googleMap;

        LatLng latlng = new LatLng(14.077910773278965, 121.32622524727054);
        hotelMedingMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latlng, 12);
        hotelMedingMap.animateCamera(location);

        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        MarkerOptions options = new MarkerOptions().position(latlng).title("Tahanan ni Aling Meding");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        hotelMedingMap.addMarker(options);
    }
}