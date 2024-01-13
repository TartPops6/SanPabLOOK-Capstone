package sanpablook.study.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.study.sanpablook.R;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap map;
    View myFragment;
    TextView userName;
    //Firebase
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public void onResume() {
        super.onResume();

        checkUserStatus();

        // Get user's first name
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fireStore.collection("users").document(userID);

        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String firstName = document.getString("firstName");
                    userName.setText("Tara, " + firstName + "!");
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

        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        //Objects
       userName = myFragment.findViewById(R.id.userName);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        // Check if user is signed in (non-null) and update UI accordingly.
        if (user == null) {
            // User is not signed in
            Intent intent = new Intent(requireContext(), SignInActivity.class);
            startActivity(intent);
            requireActivity().finish();
            return myFragment;
        }

        // Get user's first name
       FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
       DocumentReference documentReference = fireStore.collection("users").document(userID);

       documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String firstName = document.getString("firstName");
                        userName.setText(firstName + "!");
                    } else {
                        Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
       });
        //Carousel
        ImageCarousel imageCarousel = (ImageCarousel) myFragment.findViewById(R.id.carousel);
        imageCarousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem("https://fastly.4sqi.net/img/general/width960/133621146_RQ5otMtUZup3VUJsKSFbU7qbtUbQY-QRCRvtc12snbU.jpg", "Welcome to San Pablo City, Laguna!"));
        list.add(new CarouselItem("https://virtual.reality.travel/wp-content/uploads/2021/07/cathedral-church-of-st-paul-the-first-hermit-san-pablo-city-1700x900.jpg", "San Pablo Church"));
        list.add(new CarouselItem("https://files01.pna.gov.ph/ograph/2018/08/01/san-pablo-city-marker.png", "Tara!"));

        imageCarousel.setData(list);


        //Maps

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("MyFragment", "Map fragment is null");
        }


        //button redirect to nature page
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        View button = myFragment.findViewById(R.id.natureBtn);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NatureActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
        return myFragment;
    }


    //google Maps location
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng latlng = new LatLng(14.0642, 121.3233);
        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latlng, 13);
        map.animateCamera(location);

        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
        MarkerOptions options = new MarkerOptions().position(latlng).title("San Pablo City");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        map.addMarker(options);

    }

    private void checkUserStatus() {
        if (auth.getCurrentUser() == null) {
            logout();
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(requireContext(), SignInActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}