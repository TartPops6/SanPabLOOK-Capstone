package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.study.sanpablook.R;

public class SampalocLakePage extends AppCompatActivity {

    ImageButton btnShare, btnBack;

    Button sampalocYt;

    TextView sampalocLoc;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampaloc_lake);

        //buttons
        btnBack = findViewById(R.id.btnBackNature);
        btnShare = findViewById(R.id.btnShareNature);

        //back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShare.setOnClickListener(new View.OnClickListener() {
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

        //Sampaloc Lake location redirect maps
        sampalocLoc = findViewById(R.id.PostalAddressSampaloc);
        sampalocLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://maps.app.goo.gl/BU6SBMKeSa9x1PfX8");
            }
        });



        //Sampaloc Lake  redirect to youtube
        sampalocYt = findViewById(R.id.buttonSampalocYt);

        sampalocYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/eoGznaa70XI?si=xc1Ep_egopABws2r");
            }
        });
    }


    //Sampaloc Lake Image redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}