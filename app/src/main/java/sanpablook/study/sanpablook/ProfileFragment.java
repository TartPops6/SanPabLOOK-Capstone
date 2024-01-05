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
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.study.sanpablook.R;

public class ProfileFragment extends Fragment {

    Button btnViewAll;
    ImageButton btnImage1, btnImage2, btnImage3, btnSettings;
    TextView userBio, userFirstName;
    ImageView profilePicture;

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

        //Check if user is not signed in
        if (user != null) {
            //User is signed in

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
                Glide.with(ProfileFragment.this)
                        .load(uri)
                        .into(profilePicture);
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
                goToActivityHistory(view);
            }
        });

        //for card view in profile fragment
        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityHistory(view);
            }
        });
        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityHistory(view);
            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityHistory(view);
            }
        });

        return view;
    }


    //onclick of activity history cards
    private void goToActivityHistory(View view) {
        Intent intent = new Intent(getActivity(), ActivityHistory.class);
        startActivity(intent);
    }

    private void goToSettings(View view) {
        Intent intent = new Intent(getActivity(), UserSettings.class);
        startActivity(intent);
    }


}