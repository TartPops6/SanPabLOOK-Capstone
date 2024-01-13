package com.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.study.sanpablook.databinding.ActivityBookingsPendingBinding;

import sanpablook.study.sanpablook.BookingsPendingActivity;
import sanpablook.study.sanpablook.BottomNavBar;

public class ConfirmedBookingActivity extends AppCompatActivity {

    Button btnContinueBooking;
    TextView btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_booking);

        //BUTTON CONTINUE BOOKING
        btnContinueBooking = findViewById(R.id.btnContinueBooking);
        btnContinueBooking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmedBookingActivity.this, BookingsPendingActivity.class);
                startActivity(intent);
            }
        });

        //BUTTON HOME
        btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmedBookingActivity.this, BottomNavBar.class);
                startActivity(intent);
            }
        });
    }
}