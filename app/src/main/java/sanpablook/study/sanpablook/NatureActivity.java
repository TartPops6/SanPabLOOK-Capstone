package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.study.sanpablook.R;

public class NatureActivity extends AppCompatActivity {

    private Button imagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

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

        View yamboPandinBtn = findViewById(R.id.yamboPandinPic);
        yamboPandinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NatureActivity.this, YamboPandinLakePage.class);
                startActivity(intent);

            }
        });
        return;
    }
}