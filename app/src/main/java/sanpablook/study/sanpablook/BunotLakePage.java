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

public class BunotLakePage extends AppCompatActivity {

    ImageButton btnShareBunot, btnBackBunot;

    TextView bunotLake;

    Button bunotYt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunot_lake_page);

        //buttons
        btnShareBunot = findViewById(R.id.btnShareBunot);
        btnBackBunot = findViewById(R.id.btnBackBunot);

        //back button
        btnBackBunot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShareBunot.setOnClickListener(new View.OnClickListener() {
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

        //Bunot Lake Image redirect to youtube
        bunotYt = findViewById(R.id.buttonBunotYt);

        bunotYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/EpvvfTvC1lE?si=qAxk-4QxAjy60gfZ");
            }
        });

        //Bunot Lake location redirect maps
        bunotLake = findViewById(R.id.PostalAddressBunot);
        bunotLake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://maps.app.goo.gl/3YQvt46SnBnP6Dei9");
            }
        });
    }

    //Bunot Lake redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}