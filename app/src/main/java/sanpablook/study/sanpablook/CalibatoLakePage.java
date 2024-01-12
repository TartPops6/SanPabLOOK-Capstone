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

public class CalibatoLakePage extends AppCompatActivity {

    ImageButton btnShareCalibato, btnBackCalibato;

    Button calibatoYt;

    TextView calibatoLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibato_lake_page);

        //buttons
        btnShareCalibato = findViewById(R.id.btnShareCalibato);
        btnBackCalibato = findViewById(R.id.btnBackCalibato);

        //back button
        btnBackCalibato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShareCalibato.setOnClickListener(new View.OnClickListener() {
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

        //Calibato Lake location redirect maps
        calibatoLoc = findViewById(R.id.PostalAddressCalibato);
        calibatoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://maps.app.goo.gl/MLGfhjzSbmLDCdyS8");
            }
        });

        //Calibato Lake redirect to youtube
        calibatoYt = findViewById(R.id.buttonCalibatoYt);

        calibatoYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://youtu.be/rchsMULj9yE?si=F9ir1v8wt8_6Tr0v");
            }
        });
    }
    //Calibato Lake redirect to youtube
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}