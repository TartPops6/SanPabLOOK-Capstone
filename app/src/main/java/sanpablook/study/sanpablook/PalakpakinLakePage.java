package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class PalakpakinLakePage extends AppCompatActivity {

    ImageButton btnSharePalakpakin, btnBackPalakpakin;

    Button palakpakinYt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palakpakin_lake_page);

        //buttons
        btnSharePalakpakin = findViewById(R.id.btnSharePalakpakin);
        btnBackPalakpakin = findViewById(R.id.btnBackPalakpakin);

        //back button
        btnBackPalakpakin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnSharePalakpakin.setOnClickListener(new View.OnClickListener() {
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

        //Palakpakin Lake redirect to youtube
        palakpakinYt = findViewById(R.id.buttonPalakpakinYt);

        palakpakinYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/EtP6wHxBMUU?si=A0otu83qpu2fvspg");
            }
        });
    }
    //Palakpakin Lake redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}