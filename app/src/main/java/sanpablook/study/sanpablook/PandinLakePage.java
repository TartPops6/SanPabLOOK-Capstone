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

public class PandinLakePage extends AppCompatActivity {

    ImageButton btnSharePandin, btnBackPandin;

    Button pandinYt;

    TextView pandinLake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yambo_pandin_lake_page);

        //buttons
        btnSharePandin = findViewById(R.id.btnSharePandin);
        btnBackPandin = findViewById(R.id.btnBackPandin);

        //back button
        btnBackPandin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnSharePandin.setOnClickListener(new View.OnClickListener() {
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

        //Pandin Lake location redirect maps
        pandinLake = findViewById(R.id.PostalAddressYamboPandin);
        pandinLake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://maps.app.goo.gl/vVGGDzFaJ1LWHVtz5");
            }
        });

        //YamboPandin Lake Image redirect to youtube
        pandinYt = findViewById(R.id.buttonPandinYt);

        pandinYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/b5-caWYajaI?si=Ez4HgYyalUcsGbwF");
            }
        });
    }
    //Yambo Pandin Lake Image redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}