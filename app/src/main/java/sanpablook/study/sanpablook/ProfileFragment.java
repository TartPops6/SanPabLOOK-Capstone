package sanpablook.study.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.study.sanpablook.R;

public class ProfileFragment extends Fragment {

    Button btnViewAll;
    ImageButton btnImage1, btnImage2, btnImage3, btnSettings;
    TextView userBio, userFirstName;

    //Firebase
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
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