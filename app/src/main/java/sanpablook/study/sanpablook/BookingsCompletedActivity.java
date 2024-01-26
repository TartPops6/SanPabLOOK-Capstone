package sanpablook.study.sanpablook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.study.sanpablook.R;

import java.util.HashMap;
import java.util.Map;

public class BookingsCompletedActivity extends AppCompatActivity {

    ImageButton btnBackCompleted;
    AppCompatRatingBar ratingStars;
    Button buttonShare;
    EditText editTextRatings;
    ShapeableImageView addImageRatings;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_completed);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        btnBackCompleted = findViewById(R.id.buttonBackCompleted);
        ratingStars = findViewById(R.id.ratingStars);
        buttonShare = findViewById(R.id.buttonShare);
        editTextRatings = findViewById(R.id.editTextRatings);
        addImageRatings = findViewById(R.id.addImageRatings);

        addImageRatings.setOnClickListener(view1 -> {
            ImagePicker.Companion.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float userRating = ratingStars.getRating();
                String reviews = editTextRatings.getText().toString();

                Map<String, Object> userReview = new HashMap<>();
                userReview.put("userRating", userRating);
                userReview.put("reviews", reviews);

                db.collection("UserReviews")
                        .add(userReview)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(BookingsCompletedActivity.this, "Review saved successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookingsCompletedActivity.this, "Error saving review", Toast.LENGTH_SHORT).show();
                            }
                        });

                Uri imageUri = selectedImageUri;  // Use the field here
                if (imageUri != null) {
                    StorageReference storageRef = storage.getReference();
                    StorageReference userReviewImagesRef = storageRef.child("userReviewImages/" + imageUri.getLastPathSegment());

                    UploadTask uploadTask = userReviewImagesRef.putFile(imageUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(BookingsCompletedActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(BookingsCompletedActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnBackCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(BookingsCompletedActivity.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            selectedImageUri = data.getData();  // Store the URI here
            addImageRatings.setImageURI(selectedImageUri);
        }
    }
}