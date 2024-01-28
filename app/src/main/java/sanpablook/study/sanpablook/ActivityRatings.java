package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class ActivityRatings extends AppCompatActivity {

    ImageButton btnBackAH;
    RecyclerView recyclerViewRatingsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        recyclerViewRatingsUser = findViewById(R.id.recyclerViewRatingsUser);
        recyclerViewRatingsUser.setLayoutManager(new LinearLayoutManager(this));

        btnBackAH = findViewById(R.id.buttonBackAH);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (currentUser != null) {
            db.collection("UserReview").whereEqualTo("userID", currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Map<String, Object>> reviews = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            reviews.add(document.getData());
                        }
                        RecyclerRatingsUser adapter = new RecyclerRatingsUser(reviews);
                        recyclerViewRatingsUser.setAdapter(adapter);
                    } else {
                        Toast.makeText(ActivityRatings.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
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