package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.study.sanpablook.R;

public class UbeMariaAna extends AppCompatActivity {
    ImageView ArrowBack, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ube_maria_ana);

        ArrowBack = findViewById(R.id.ArrowBack);
        nextButton = findViewById(R.id.nextButton);

        ArrowBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UbeMariaAna.this, BottomNavBar.class);
                intent.putExtra("initialFragment", "ProductsFragment");
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UbeMariaAna.this,RomalynUbe.class);
                startActivity(intent);
            }
        });

    }

    public void sendSMS(View v) {
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("number","09988606422");
        smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(smsIntent);

        String number = "09988606422";  // The number on which you want to send SMS
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

    public void sendSMS2(View v) {
        String number = "09088859934";  // The number on which you want to send SMS
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }
}