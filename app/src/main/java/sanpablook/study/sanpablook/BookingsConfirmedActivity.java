package sanpablook.study.sanpablook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.study.sanpablook.R;

public class BookingsConfirmedActivity extends AppCompatActivity {

    ImageButton btnBackConfirmed;
    RecyclerView recyclerViewConfirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_confirmed);

        btnBackConfirmed = findViewById(R.id.buttonBackConfirmed);

        recyclerViewConfirmed = findViewById(R.id.recyclerViewConfirmed);
        recyclerViewConfirmed.setLayoutManager(new LinearLayoutManager(this));

        btnBackConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}