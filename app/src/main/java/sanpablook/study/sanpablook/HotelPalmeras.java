package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.study.sanpablook.R;

public class HotelPalmeras extends AppCompatActivity {

    ImageButton btnShare, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_palmeras);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //buttons
        backBtn= findViewById(R.id.backBtn);
        btnShare = findViewById(R.id.btnShare);

        //back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //share button
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Share this App";
                String Sub = "http://meding_location.google.com";
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

        Button reserveNowBtn1 = findViewById(R.id.reserveNowBtn1);
        reserveNowBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelPalmeras.this, HotelReservationActivity.class);
                intent.putExtra("documentId", "CasaPalmerasHotel");
                intent.putExtra("imagePath", "estabProfilePictures/palmerasHotelProfile.jpg");
                startActivity(intent);
            }
        });

        TextView stayPrice = (TextView) findViewById(R.id.stayPrice);
        String text = "<font color=#1A9AB7>₱ 3, 864</font> <font color=#000000>/ night</font>";
        stayPrice.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
    }
}