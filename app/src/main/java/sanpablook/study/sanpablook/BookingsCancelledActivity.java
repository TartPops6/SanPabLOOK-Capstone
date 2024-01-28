package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.study.sanpablook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sanpablook.study.sanpablook.Adapter.BookingAdapter;
import sanpablook.study.sanpablook.Adapter.RecyclerBookingsCancelled;

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

        // Fetch the cancelled bookings from Firestore
        fetchCancelledBookings();
    }

    private void fetchCancelledBookings() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            db.collection("BookingPending")
                    .whereEqualTo("userID", currentUser.getUid())
                    .whereEqualTo("status", "Cancelled")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            List<Map<String, Object>> bookings = new ArrayList<>();
                            for (DocumentSnapshot document : documents) {
                                bookings.add(document.getData());
                            }

                            // Use the bookings to populate the RecyclerView
                            RecyclerBookingsCancelled adapter = new RecyclerBookingsCancelled(bookings);
                            recyclerViewCancelled.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("BookingsCancelledActivity", "Failed to fetch cancelled bookings", e);
                        }
                    });
        }
    }
}