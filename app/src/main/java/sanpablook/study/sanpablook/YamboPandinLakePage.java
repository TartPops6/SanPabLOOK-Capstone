package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class YamboPandinLakePage extends AppCompatActivity {

    ImageButton btnShareYamboPandin, btnBackYamboPandin;

    Button YamboPandinYt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yambo_pandin_lake_page);

        //buttons
        btnShareYamboPandin = findViewById(R.id.btnShareYamboPandin);
        btnBackYamboPandin = findViewById(R.id.btnBackYamboPandin);

        //back button
        btnBackYamboPandin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShareYamboPandin.setOnClickListener(new View.OnClickListener() {
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

        //YamboPandin Lake Image redirect to youtube
        YamboPandinYt = findViewById(R.id.buttonYamboYt);

        YamboPandinYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/EtP6wHxBMUU?si=A0otu83qpu2fvspg");
            }
        });
    }
    //Yambo Pandin Lake Image redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}