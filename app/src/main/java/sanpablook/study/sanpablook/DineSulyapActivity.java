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
import android.widget.TextView;

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

public class DineSulyapActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageButton btnShare, btnBack;
    Button reserveNowBtn, buttonViewAll;

    GoogleMap map;

    //recycler view horizontal
    RecyclerView recyclerViewDineReviewsSulyap;

    TextView reviewsHotel, stayReviews, txtReview2, ratingsHotel, stayProfileRate, txtRate2;

    String establishmentID = "sulyapDine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_sulyap);

        //Strings
        reviewsHotel = findViewById(R.id.txtReview);
        txtReview2 = findViewById(R.id.txtReview2);
        ratingsHotel = findViewById(R.id.txtRate);
        txtRate2 = findViewById(R.id.txtRate2);

        //recycler view horizontal
        recyclerViewDineReviewsSulyap = findViewById(R.id.recyclerViewDineReviewsSulyap);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDineReviewsSulyap.setLayoutManager(layoutManager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserReview")
                .whereEqualTo("establishmentID", "sulyapDine")
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
                            recyclerViewDineReviewsSulyap.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            // Set the review count text
                            String reviewCountText = reviews.size() > 0 ? reviews.size() + " Reviews" : "No reviews";
                            reviewsHotel.setText(reviewCountText);
                            txtReview2.setText(reviewCountText);

                            // Disable the button if there are no reviews
                            if (reviews.size() == 0) {
                                buttonViewAll.setEnabled(false);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("UserReview")
                .whereEqualTo("establishmentID", establishmentID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Map<String, Object>> reviews = new ArrayList<>();
                            int totalRatings = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> review = document.getData();
                                reviews.add(review);
                                Object ratingObj = review.get("userRating");
                                if (ratingObj != null) {
                                    float rating = Float.parseFloat(ratingObj.toString());
                                    if (rating >= 1 && rating <= 5) {
                                        totalRatings += rating;
                                    }
                                }
                            }
                            Log.d(TAG, "Number of reviews fetched: " + reviews.size());
                            Log.d(TAG, "Total ratings: " + totalRatings);

                            if (reviews.size() > 0) {
                                // Calculate the actual rating score
                                float actualRatingScore = (float) totalRatings / reviews.size();
                                Log.d(TAG, "Actual rating score: " + actualRatingScore);

                                // Display the total ratings and the actual rating score
                                ratingsHotel.setText(String.format("%.1f", actualRatingScore));
                                txtRate2.setText(String.format("%.1f", actualRatingScore));
                            } else {
                                // If there are no reviews, set the text to "No rating"
                                ratingsHotel.setText("No rating");
                                txtRate2.setText("No rating");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        //Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsSulyapDine);

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

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToViewAllReviews(view);
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

    private void goToViewAllReviews(View view) {
        Intent intent = new Intent(DineSulyapActivity.this, ViewAllRatingsDine.class);
        intent.putExtra("establishmentID", establishmentID);
        startActivity(intent);
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