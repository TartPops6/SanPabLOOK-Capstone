package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.study.sanpablook.R;

public class Pinayte extends AppCompatActivity {

    ImageView ArrowBack, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinayte);

        ArrowBack = findViewById(R.id.ArrowBack);
        nextButton = findViewById(R.id.nextButton);

        ArrowBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Pinayte.this, ProductMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pinayte.this,PandinFIsherFolk.class);
                startActivity(intent);
            }
        });

    }

    public void sendSMS(View v) {
        String number = "09454926988";  // The number on which you want to send SMS
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }
}