package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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

import sanpablook.study.sanpablook.Adapter.BookingAdapter;

public class BookingsPendingActivity extends AppCompatActivity {

    ImageButton btnBackPending;
    TextView txtPending, txtBookingID, txtCustomerName, txtBookingDate, txtBookingTime, txtGuestCount, txtPetCount;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_pending);

        refreshData();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnBackPending = findViewById(R.id.buttonBackPending);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userID = currentUser.getUid();

db.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Pending").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            List<Map<String, Object>> bookings = new ArrayList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                bookings.add(document.getData());
            }
            BookingAdapter adapter = new BookingAdapter(bookings);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(BookingsPendingActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
        }
    }
});

        btnBackPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userID = currentUser.getUid();

        db.collection("BookingPending").whereEqualTo("userID", userID).whereEqualTo("status", "Pending").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Map<String, Object>> bookings = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        bookings.add(document.getData());
                    }
                    BookingAdapter adapter = new BookingAdapter(bookings);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(BookingsPendingActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

