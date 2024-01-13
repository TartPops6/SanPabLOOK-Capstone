package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class ActivityRatings extends AppCompatActivity {

    ImageButton btnBackAH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        btnBackAH = findViewById(R.id.buttonBackAH);

        btnBackAH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}