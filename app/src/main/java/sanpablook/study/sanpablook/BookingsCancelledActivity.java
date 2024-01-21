package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class BookingsCancelledActivity extends AppCompatActivity {

    ImageButton btnBackCancelled;
    RecyclerView recyclerViewCancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_cancelled);

        btnBackCancelled = findViewById(R.id.buttonBackCancelled);

        recyclerViewCancelled = findViewById(R.id.recyclerViewCancelled);
        recyclerViewCancelled.setLayoutManager(new LinearLayoutManager(this));

        btnBackCancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}