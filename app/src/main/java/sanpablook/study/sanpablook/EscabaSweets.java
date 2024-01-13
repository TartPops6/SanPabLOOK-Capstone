package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.study.sanpablook.R;

public class EscabaSweets extends AppCompatActivity {

    ImageView ArrowBack, previousButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaba_sweets);

        ArrowBack = findViewById(R.id.ArrowBack);
        previousButton = findViewById(R.id.previousButton);

        ArrowBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EscabaSweets.this, BottomNavBar.class);
                intent.putExtra("initialFragment", "ProductsFragment");
                startActivity(intent);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void sendSMS(View v) {
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("number","09173262738");
        smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(smsIntent);

        String number = "09173262738";  // The number on which you want to send SMS
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }
}