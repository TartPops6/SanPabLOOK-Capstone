package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.study.sanpablook.databinding.ActivityBottomNavBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.study.sanpablook.R;

public class BottomNavBar extends AppCompatActivity {

    ActivityBottomNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        //for the unclickable middle navigation button
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu bottom_nav_menu = bottomNavigationView.getMenu();
        MenuItem menuItem = bottom_nav_menu.findItem(R.id.unclickableBooking);
        menuItem.setEnabled(false);


        //Navigation if-else
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
                resetFabColors();
            } else if (itemId == R.id.dine) {
                replaceFragment(new DineFragment());
                resetFabColors();
            } else if (itemId == R.id.products) {
                replaceFragment(new ProductsFragment());
                resetFabColors();
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
                resetFabColors();
            }

            return true;
        });

        // FAB Booking for Hotel
        binding.floatingBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomNavigationView.setSelectedItemId(R.id.unclickableBooking);
                replaceFragment(new HotelFragment());

                // Change FAB background & icon color when clicked
                binding.floatingBook.setBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(BottomNavBar.this, R.color.blue))
                );
                binding.floatingBook.setImageTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(BottomNavBar.this, R.color.light))
                );
            }
        });

        // PRODUCTS
        if (getIntent().hasExtra("initialFragment")) {
            String initialFragment = getIntent().getStringExtra("initialFragment");
            if ("ProductsFragment".equals(initialFragment)) {
                replaceFragment(new ProductsFragment());
                binding.bottomNavigationView.setSelectedItemId(R.id.products);
            }
        } else {
            //default fragment (HomeFragment)
            replaceFragment(new HomeFragment());
        }

        // PRODUCTS
        if (getIntent().hasExtra("initialFragment")) {
            String initialFragment = getIntent().getStringExtra("initialFragment");
            if ("HotelFragment".equals(initialFragment)) {
                replaceFragment(new HotelFragment());
                binding.bottomNavigationView.setSelectedItemId(R.id.unclickableBooking);
                // Change FAB background & icon color when clicked
                binding.floatingBook.setBackgroundTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(BottomNavBar.this, R.color.blue))
                );
                binding.floatingBook.setImageTintList(
                        ColorStateList.valueOf(ContextCompat.getColor(BottomNavBar.this, R.color.light))
                );
            }
        } else {
            //default fragment (HomeFragment)
            replaceFragment(new HomeFragment());
        }
    }

    //Fragments for Navigation
    private void replaceFragment(Fragment fragment) {

        FragmentManager fmProfileFragment = getSupportFragmentManager();
        FragmentTransaction ftProfileFragment = fmProfileFragment.beginTransaction();
        ftProfileFragment.replace(R.id.frameLayoutNavigation,fragment);
        ftProfileFragment.commit();
    }

    //FAB reset to initial colors
    private void resetFabColors() {
        binding.floatingBook.setBackgroundTintList(
                ColorStateList.valueOf(ContextCompat.getColor(BottomNavBar.this, R.color.lightBlue))
        );
        binding.floatingBook.setImageTintList(
                ColorStateList.valueOf(ContextCompat.getColor(BottomNavBar.this, R.color.blue))
        );
    }
}