package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.study.sanpablook.R;

public class ColettesBukoPie extends AppCompatActivity {
    ImageView ArrowBack, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colettes_buko_pie);

        ArrowBack = findViewById(R.id.ArrowBack);
        nextButton = findViewById(R.id.nextButton);

        ArrowBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ColettesBukoPie.this, ProductMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ColettesBukoPie.this,CocoVinegar.class);
                startActivity(intent);
            }
        });

        }

        public void sendSMS(View v) {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("number","09283578000");
            smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(smsIntent);

            String number = "09283578000";  // The number on which you want to send SMS
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        }

}