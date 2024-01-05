package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class BikeRentalPage extends AppCompatActivity {

    ImageButton btnShareBikeRental, btnBackBikeRental;

    Button bikeRentalYt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_rental_page);

        //buttons
        btnShareBikeRental = findViewById(R.id.btnShareBikeRental);
        btnBackBikeRental = findViewById(R.id.btnBackBikeRental);

        //back button
        btnBackBikeRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShareBikeRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Share this App";
                String Sub = "https://maps.app.goo.gl/F2psPJtXwuQzKTYk6";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        //Bike Rental redirect to youtube
        bikeRentalYt = findViewById(R.id.buttonBikeRentalYt);

        bikeRentalYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/EtP6wHxBMUU?si=A0otu83qpu2fvspg");
            }
        });
    }

    //Bike Rental redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}