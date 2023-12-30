package sanpablook.study.sanpablook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.sanpablook.R;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseAuth auth;
    private View myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String fragmentName = getIntent().getStringExtra("fragment");
        if ("HomeFragment".equals(fragmentName)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new HomeFragment())
                    .commit();
        }

        setupNatureButton();
        setupCarousel();
        setupMap();
    }

    private void setupNatureButton() {
        ImageButton natureBtn = findViewById(R.id.natureBtn);
        natureBtn.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });
    }

    private void setupCarousel() {
        ImageCarousel imageCarousel = findViewById(R.id.carousel);
        imageCarousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem("https://fastly.4sqi.net/img/general/width960/133621146_RQ5otMtUZup3VUJsKSFbU7qbtUbQY-QRCRvtc12snbU.jpg", "Welcome to San Pablo City, Laguna!"));
        list.add(new CarouselItem("https://virtual.reality.travel/wp-content/uploads/2021/07/cathedral-church-of-st-paul-the-first-hermit-san-pablo-city-1700x900.jpg", "San Pablo Church"));
        list.add(new CarouselItem("https://files01.pna.gov.ph/ograph/2018/08/01/san-pablo-city-marker.png", "Tara!"));

        imageCarousel.setData(list);
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latlng = new LatLng(14.0642, 121.3233);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));

        MarkerOptions options = new MarkerOptions().position(latlng).title("San Pablo City");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        googleMap.addMarker(options);
    }
}