package sanpablook.study.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class ProfileFragment extends Fragment {

    //Activity History
    Button btnViewAll;
    ImageButton btnImage1, btnImage2, btnImage3, btnSettings;

    //Profile
    TextView userBio, userFirstName;
    ImageView profilePicture;

    //Bookings badge count
    ImageButton btnPending, btnConfirmed, btnCancelled, btnRatings;
    TextView badgePendingCount, badgeConfirmedCount, badgeCancelledCount, badgeRatingCount;
    int intBadgePendingCount = 2, intBadgeConfirmedCount = 1, intBadgeCancelledCount = 1, intBadgeRatingsCount = 7;

    //Firebase
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    StorageReference storage;
    String userID;
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
        fStore.collection("BookingPending").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                intBadgePendingCount = task.getResult().size();
                setupPendingBadge();
            } else {
                Log.d(TAG, "Failed to fetch user data");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

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
        btnImage1 = view.findViewById(R.id.image1);
        btnImage2 = view.findViewById(R.id.image2);
        btnImage3 = view.findViewById(R.id.image3);

        //bookings
        btnPending = view.findViewById(R.id.buttonPending);
        btnConfirmed = view.findViewById(R.id.buttonConfirmed);
        btnCancelled = view.findViewById(R.id.buttonCancelled);
        btnRatings = view.findViewById(R.id.buttonRatings);

        //bookings notification badge
        badgePendingCount = view.findViewById(R.id.badgePending);
        badgeConfirmedCount = view.findViewById(R.id.badgeConfirmed);
        badgeCancelledCount = view.findViewById(R.id.badgeCancelled);
        badgeRatingCount = view.findViewById(R.id.badgeRatings);

        //Check if user is not signed in
        if (user != null) {
            //Check the count of pending bookings for this user
            fStore.collection("BookingPending").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    intBadgePendingCount = task.getResult().size();
                    setupPendingBadge();
                } else {
                    Log.d(TAG, "Failed to fetch user data");
                }
            });
            setupConfirmedBadge();
            setupCancelledBadge();
            setupRatingsBadge();
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

        //for card view in profile fragment
        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityRatings(view);
            }
        });
        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityRatings(view);
            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
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
        btnRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRatings(view);
            }
        });

        return view;
    }


    //onclick of activity history cards
    private void goToActivityRatings(View view) {
        Intent intent = new Intent(getActivity(), ActivityRatings.class);
        startActivity(intent);
    }

    private void goToSettings(View view) {
        Intent intent = new Intent(getActivity(), UserSettings.class);
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

    private void goToRatings(View view) {
        Intent intent = new Intent(getActivity(), BookingsCompletedActivity.class);
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
    private void setupRatingsBadge() {
        if (badgeRatingCount != null) {
            if (intBadgeRatingsCount == 0) {
                if (badgeRatingCount.getVisibility() != View.GONE) {
                    badgeRatingCount.setVisibility(View.GONE);
                }
            } else {
                badgeRatingCount.setText(String.valueOf(Math.min(intBadgeRatingsCount, 99)));
                if (badgeRatingCount.getVisibility() != View.VISIBLE) {
                    badgeRatingCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}