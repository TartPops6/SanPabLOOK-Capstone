package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.study.sanpablook.R;

public class SanPablookActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pablook_activities);

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