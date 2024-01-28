package sanpablook.study.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.study.sanpablook.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sanpablook.study.sanpablook.Adapter.RecyclerCardRatings;

public class ProfileFragment extends Fragment {

    //recycler view horizontal
    RecyclerView recyclerViewCardRatings;
    LinearLayoutManager linearLayoutManager;

    //Activity History
    Button btnViewAll;
    ImageButton btnSettings;

    //Profile
    TextView userBio, userFirstName;
    ImageView profilePicture;

    //Bookings badge count
    ImageButton btnPending, btnConfirmed, btnCancelled;
    TextView badgePendingCount, badgeConfirmedCount, badgeCancelledCount, badgeRatingCount;
    int intBadgePendingCount = 0, intBadgeConfirmedCount = 0, intBadgeCancelledCount = 0, intBadgeRatingsCount = 0;

    private List<String> items = new ArrayList<>();

    //Firebase
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    StorageReference storage;
    String userID;

    private RecyclerCardRatings adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Get user's first name
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fireStore.collection("users").document(userID);

        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String firstName = document.getString("firstName");
                    userFirstName.setText(firstName);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        // Get user's bio
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String bio = document.getString("bio");
                    userBio.setText(bio);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        // Check the count of pending bookings for this user
        fStore.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Pending").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                intBadgePendingCount = task.getResult().size();
                setupPendingBadge();
            } else {
                Log.d(TAG, "Failed to fetch user data");
            }
        });

        // Check the count of confirmed bookings for this user
        fStore.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Confirmed").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                intBadgeConfirmedCount = task.getResult().size();
                setupConfirmedBadge();
            } else {
                Log.d(TAG, "Failed to fetch user data");
            }
        });

        // Check the count of cancelled bookings for this user
        fStore.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Cancelled").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                intBadgeCancelledCount = task.getResult().size();
                setupCancelledBadge();
            } else {
                Log.d(TAG, "Failed to fetch user data");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //recycler view horizontal
        recyclerViewCardRatings = view.findViewById(R.id.recyclerViewCardRatings);
        recyclerViewCardRatings.setLayoutManager(new LinearLayoutManager(requireContext()));
        linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCardRatings.setLayoutManager(linearLayoutManager);

        //Objects
        userFirstName = view.findViewById(R.id.userFirstName);
        userBio = view.findViewById(R.id.userBio);
        profilePicture = view.findViewById(R.id.profilePicture);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        //buttons
        btnSettings = view.findViewById(R.id.buttonSettings);
        btnViewAll = view.findViewById(R.id.buttonViewAll);

        //bookings
        btnPending = view.findViewById(R.id.buttonPending);
        btnConfirmed = view.findViewById(R.id.buttonConfirmed);
        btnCancelled = view.findViewById(R.id.buttonCancelled);

        //bookings notification badge
        badgePendingCount = view.findViewById(R.id.badgePending);
        badgeConfirmedCount = view.findViewById(R.id.badgeConfirmed);
        badgeCancelledCount = view.findViewById(R.id.badgeCancelled);

        //Check if user is not signed in
        if (user != null) {
            //Check the count of pending bookings for this user
            fStore.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Pending").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    intBadgePendingCount = task.getResult().size();
                    setupPendingBadge();
                } else {
                    Log.d(TAG, "Failed to fetch user data");
                }
            });
            //Check count for cancelled bookings for this user
            fStore.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Cancelled").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    intBadgeCancelledCount = task.getResult().size();
                    setupCancelledBadge();
                } else {
                    Log.d(TAG, "Failed to fetch user data");
                }
            });
            // Check the count of confirmed bookings for this user
            fStore.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Confirmed").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    intBadgeConfirmedCount = task.getResult().size();
                    setupConfirmedBadge();
                } else {
                    Log.d(TAG, "Failed to fetch user data");
                }
            });
            setupConfirmedBadge();
            setupCancelledBadge();
        } else {
            //User is not signed in
            Intent intent = new Intent(requireContext(), SignInActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        DocumentReference documentReference = fireStore.collection("users").document(userID);
        StorageReference profilePicRef = storage.getReference().child("profilePictures/" + userID + ".jpg");

        // Get user's first name
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String firstName = document.getString("firstName");
                    userFirstName.setText(firstName);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        // Get user's bio
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String bio = document.getString("bio");
                    userBio.setText(bio);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        //Get users profile picture
        profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (isAdded() && getActivity() != null) {
                    Glide.with(getActivity())
                            .load(uri)
                            .into(profilePicture);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "Failed to load profile picture");
            }
        });

        fStore.collection("UserReview").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    String imageUrl = document.getString("imageUrl");

                    // Add the imageUrl to the items list
                    items.add(imageUrl);
                }
                // Initialize the adapter
                adapter = new RecyclerCardRatings(getActivity(), items);
                recyclerViewCardRatings.setAdapter(adapter);

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "Failed to fetch user data");
            }
        });

        //for settings button
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettings(view);
            }
        });

        //for view all button
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityRatings(view);
            }
        });


        //bookings pages
        btnPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPending(view);
            }
        });
        btnConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToConfirmed(view);
            }
        });
        btnCancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCancelled(view);
            }
        });

        return view;
    }


    private void goToSettings(View view) {
        Intent intent = new Intent(getActivity(), UserSettings.class);
        startActivity(intent);
    }

    private void goToActivityRatings(View view) {
        Intent intent = new Intent(getActivity(), ActivityRatings.class);
        startActivity(intent);
    }

    private void goToPending(View view) {
        Intent intent = new Intent(getActivity(), BookingsPendingActivity.class);
        startActivity(intent);
    }

    private void goToConfirmed(View view) {
        Intent intent = new Intent(getActivity(), BookingsConfirmedActivity.class);
        startActivity(intent);
    }

    private void goToCancelled(View view) {
        Intent intent = new Intent(getActivity(), BookingsCancelledActivity.class);
        startActivity(intent);
    }

    //bookings badge
    private void setupPendingBadge() {
        if (badgePendingCount != null) {
            if (intBadgePendingCount == 0) {
                if (badgePendingCount.getVisibility() != View.GONE) {
                    badgePendingCount.setVisibility(View.GONE);
                }
            } else {
                badgePendingCount.setText(String.valueOf(Math.min(intBadgePendingCount, 99)));
                if (badgePendingCount.getVisibility() != View.VISIBLE) {
                    badgePendingCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private void setupConfirmedBadge() {
        if (badgeConfirmedCount != null) {
            if (intBadgeConfirmedCount == 0) {
                if (badgeConfirmedCount.getVisibility() != View.GONE) {
                    badgeConfirmedCount.setVisibility(View.GONE);
                }
            } else {
                badgeConfirmedCount.setText(String.valueOf(Math.min(intBadgeConfirmedCount, 99)));
                if (badgeConfirmedCount.getVisibility() != View.VISIBLE) {
                    badgeConfirmedCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private void setupCancelledBadge() {
        if (badgeCancelledCount != null) {
            if (intBadgeCancelledCount == 0) {
                if (badgeCancelledCount.getVisibility() != View.GONE) {
                    badgeCancelledCount.setVisibility(View.GONE);
                }
            } else {
                badgeCancelledCount.setText(String.valueOf(Math.min(intBadgeCancelledCount, 99)));
                if (badgeCancelledCount.getVisibility() != View.VISIBLE) {
                    badgeCancelledCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}