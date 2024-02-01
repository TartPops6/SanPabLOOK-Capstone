package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.study.sanpablook.R;

import java.util.Arrays;
import java.util.List;

public class NatureActivity extends AppCompatActivity {

    private Button imagebutton;

    ImageButton btnReturnNature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

        //back button
        btnReturnNature = findViewById(R.id.btnReturnNature);

        btnReturnNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, BottomNavBar.class);
                startActivity(intent);
            }
        });

        View sampalocBtn = findViewById(R.id.sampaloclakePic);
        sampalocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, SampalocLakePage.class);
                startActivity(intent);
            }
        });

        View mohicapBtn = findViewById(R.id.mohicapLakePic);
        mohicapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, MohicapLakePage.class);
                startActivity(intent);
            }
        });

        View calibatoBtn = findViewById(R.id.calibatoLakePic);
        calibatoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, CalibatoLakePage.class);
                startActivity(intent);
            }
        });

        View palakpakinBtn = findViewById(R.id.palakpakinLakePic);
        palakpakinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, PalakpakinLakePage.class);
                startActivity(intent);
            }
        });

        View pandinBtn = findViewById(R.id.pandinPic);
        pandinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, PandinLakePage.class);
                startActivity(intent);
            }
        });

        View yamboBtn = findViewById(R.id.yamboPic);
        yamboBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, YamboLakePage.class);
                startActivity(intent);
            }
        });

        View bunotBtn = findViewById(R.id.bunotPic);
        bunotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, BunotLakePage.class);
                startActivity(intent);
            }
        });

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.searchBar);

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
        return;
    }
}