package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class NatureActivity extends AppCompatActivity {

//    private Button imagebutton;

    ImageButton btnReturnNature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

        //back button
        btnReturnNature = findViewById(R.id.btnReturnNature);

        btnReturnNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NatureActivity.this, BottomNavBar.class);
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
        return;
    }
}