package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.study.sanpablook.R;

public class SampalocLakeSouvenir extends AppCompatActivity {

    ImageView ArrowBack, previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampaloc_lake_souvenir);

        ArrowBack = findViewById(R.id.ArrowBack);
        previousButton = findViewById(R.id.previousButton);

        ArrowBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SampalocLakeSouvenir.this, ProductMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }

    public void sendSMS(View v) {
        String number = "09275171472";  // The number on which you want to send SMS
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

}