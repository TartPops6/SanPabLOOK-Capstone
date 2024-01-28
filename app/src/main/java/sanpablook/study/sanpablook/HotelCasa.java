package sanpablook.study.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
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
import sanpablook.study.sanpablook.Adapter.RecyclerHotelReviews;

public class HotelCasa extends AppCompatActivity implements OnMapReadyCallback {

    ImageButton btnShare, backBtn;
    Button buttonViewAll;

    GoogleMap hotelCasaMap;

    TextView reviewsHotel, stayReviews, txtReview2, ratingsHotel, stayProfileRate, txtRate2;

    String establishmentID = "casaHotel";

    //recycler view horizontal
    RecyclerView recyclerViewHotelReviewsCasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_casa);

        //recycler view horizontal
        recyclerViewHotelReviewsCasa = findViewById(R.id.recyclerViewHotelReviewsCasa);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHotelReviewsCasa.setLayoutManager(layoutManager);

        //Strings
        reviewsHotel = findViewById(R.id.reviews);
        stayReviews = findViewById(R.id.stayReview);
        txtReview2 = findViewById(R.id.txtReview2);
        ratingsHotel = findViewById(R.id.ratings);
        stayProfileRate = findViewById(R.id.stayProfileRate);
        txtRate2 = findViewById(R.id.txtRate2);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserReview")
                .whereEqualTo("establishmentID", "casaHotel")
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
                            RecyclerHotelReviews adapter = new RecyclerHotelReviews(reviews);
                            recyclerViewHotelReviewsCasa.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            // Set the review count text
                            String reviewCountText = reviews.size() > 0 ? reviews.size() + " Reviews" : "No reviews";
                            reviewsHotel.setText(reviewCountText);
                            stayReviews.setText(reviewCountText);
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
                                stayProfileRate.setText(String.format("%.1f", actualRatingScore));
                                txtRate2.setText(String.format("%.1f", actualRatingScore));
                            } else {
                                // If there are no reviews, set the text to "No rating"
                                ratingsHotel.setText("No rating");
                                stayProfileRate.setText("No rating");
                                txtRate2.setText("No rating");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Maps
        SupportMapFragment casaMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.casaGoogleMaps);

        if(casaMapFragment !=null){
            casaMapFragment.getMapAsync(this);
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
                Intent intent = new Intent(HotelCasa.this, HotelReservationActivity.class);
                intent.putExtra("documentId", "CasaSanPabloHotel");
                intent.putExtra("imagePath", "estabProfilePictures/casaSanPabloProfile.jpg");
                startActivity(intent);
            }
        });

        TextView stayPrice = (TextView) findViewById(R.id.stayPrice);
        String text = "<font color=#1A9AB7>₱ 3, 864</font> <font color=#000000>/ night</font>";
        stayPrice.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
    }

    private void goToViewAllReviews(View view) {
        Intent intent = new Intent(HotelCasa.this, ViewAllRatingsHotel.class);
        intent.putExtra("establishmentID", establishmentID);
        startActivity(intent);
    }

    //google Maps location
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        hotelCasaMap = googleMap;

        LatLng latlng = new LatLng(14.072728209107744, 121.317300798703);
        hotelCasaMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latlng, 12);
        hotelCasaMap.animateCamera(location);

        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        MarkerOptions options = new MarkerOptions().position(latlng).title("Hotel Casa");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        hotelCasaMap.addMarker(options);
    }
}