package sanpablook.study.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.study.sanpablook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sanpablook.study.sanpablook.Adapter.RecyclerDineReviews;

public class DineCasaActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageButton btnShare, btnBack;
    Button reserveNowBtn;

    GoogleMap map;

    //recycler view horizontal
    RecyclerView recyclerViewDineReviewsCasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_casa);

        //recycler view horizontal
        recyclerViewDineReviewsCasa = findViewById(R.id.recyclerViewDineReviewsCasa);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDineReviewsCasa.setLayoutManager(layoutManager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserReview")
                .whereEqualTo("establishmentID", "casaDine")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Map<String, Object>> reviews = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                reviews.add(document.getData());
                            }
                            Log.d(TAG, "Number of reviews fetched: " + reviews.size());
                            RecyclerDineReviews adapter = new RecyclerDineReviews(reviews);
                            recyclerViewDineReviewsCasa.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsCasaDine);

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