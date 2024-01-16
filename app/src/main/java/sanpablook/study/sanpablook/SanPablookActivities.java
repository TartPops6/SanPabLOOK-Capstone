package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class SanPablookActivities extends AppCompatActivity {

    ImageButton btnReturnActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pablook_activities);

        //back button
        btnReturnActivities = findViewById(R.id.btnReturnActivities);

        btnReturnActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SanPablookActivities.this, BottomNavBar.class);
                startActivity(intent);
            }
        });

        View bikeRentalBtn = findViewById(R.id.bikeRentalPic);
        bikeRentalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SanPablookActivities.this, BikeRentalPage.class);
                startActivity(intent);
            }
        });

        View campingBtn = findViewById(R.id.CampingPic);
        campingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SanPablookActivities.this, CampingPage.class);
                startActivity(intent);
            }
        });

        View fishingBtn = findViewById(R.id.fishingPic);
        fishingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SanPablookActivities.this, FishingPage.class);
                startActivity(intent);
            }
        });
    }
}