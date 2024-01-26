package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.study.sanpablook.R;

import java.util.Arrays;
import java.util.List;

public class SanPablookActivities extends AppCompatActivity {

    ImageButton btnReturnActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pablook_activities);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.searchBarActivities);

        List<String> items = Arrays.asList("Tahanan ni Aling Meding", "Casa San Pablo (Hotel)", "Casa Palmera", "Sulyap Gallery Cafe", "Casa San Pablo (Dine)", "Palmeras Garden Restaurant");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);

        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            if (selectedItem.isEmpty()) {
                Toast.makeText(this, "No results", Toast.LENGTH_SHORT).show();
            } else {
                switch (selectedItem) {
                    case "Casa San Pablo (Hotel)":
                        startActivity(new Intent(this, HotelCasa.class));
                        break;
                    case "Tahanan ni Aling Meding":
                        startActivity(new Intent(this, HotelMeding.class));
                        break;
                    case "Casa Palmera":
                        startActivity(new Intent(this, HotelPalmeras.class));
                        break;
                    case "Sulyap Gallery Cafe":
                        startActivity(new Intent(this, DineSulyapActivity.class));
                        break;
                    case "Casa San Pablo (Dine)":
                        startActivity(new Intent(this, DineCasaActivity.class));
                        break;
                    case "Palmeras Garden Restaurant":
                        startActivity(new Intent(this, DinePalmerasActivity.class));
                        break;
                    default:
                        Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
                        break;
                    // Add more cases as needed
                }
            }
        });

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