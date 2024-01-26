package sanpablook.study.sanpablook;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.sanpablook.R;

public class ViewAllRatingsDine extends AppCompatActivity {

    ImageButton btnBackAH;
    RecyclerView recyclerViewAllRatingsDine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_ratings_dine);

        recyclerViewAllRatingsDine = findViewById(R.id.recyclerViewAllRatingsDine);
        recyclerViewAllRatingsDine.setLayoutManager(new LinearLayoutManager(this));

        btnBackAH = findViewById(R.id.buttonBackAH);

        btnBackAH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}