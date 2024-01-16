package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.study.sanpablook.R;

public class YamboLakePage extends AppCompatActivity {

    ImageButton btnShareYambo, btnBackYambo;

    TextView yamboLake;

    Button yamboYt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yambo_lake_page);

        //buttons
        btnShareYambo = findViewById(R.id.btnShareYambo);
        btnBackYambo = findViewById(R.id.btnBackYambo);

        //back button
        btnBackYambo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShareYambo.setOnClickListener(new View.OnClickListener() {
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

        //Yambo Lake Image redirect to youtube
        yamboYt = findViewById(R.id.buttonYamboYt);

        yamboYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/WZj2pPTa5LM?si=RttFFVAU2QDDhBGR");
            }
        });

        //Yambo Lake location redirect maps
        yamboLake = findViewById(R.id.PostalAddressYambo);
        yamboLake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://maps.app.goo.gl/ijX686E8D4g6AePs6");
            }
        });
    }

    //Yambo Pandin Lake Image redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}