package sanpablook.study.sanpablook;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.study.sanpablook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sanpablook.study.sanpablook.Adapter.RecyclerRatingsUser;
import sanpablook.study.sanpablook.Adapter.RecyclerViewAllRatingsHotel;

public class ViewAllRatingsHotel extends AppCompatActivity {

    ImageButton btnBackAH;
    RecyclerView recyclerViewAllRatingsHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_ratings_hotel);

        recyclerViewAllRatingsHotel = findViewById(R.id.recyclerViewAllRatingsHotel);
        recyclerViewAllRatingsHotel.setLayoutManager(new LinearLayoutManager(this));

        String establishmentID = getIntent().getStringExtra("establishmentID");

        btnBackAH = findViewById(R.id.buttonBackAH);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (currentUser != null) {
            db.collection("UserReview").whereEqualTo("establishmentID", establishmentID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Map<String, Object>> ratings = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ratings.add(document.getData());
                        }
                        RecyclerViewAllRatingsHotel adapter = new RecyclerViewAllRatingsHotel(ratings);
                        recyclerViewAllRatingsHotel.setAdapter(adapter);
                    } else {
                        Toast.makeText(ViewAllRatingsHotel.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        btnBackAH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}