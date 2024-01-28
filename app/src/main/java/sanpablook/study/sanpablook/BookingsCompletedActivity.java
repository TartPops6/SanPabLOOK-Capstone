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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

    TextView establishmentNameToRate;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_completed);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        String userID = mAuth.getCurrentUser().getUid();

        establishmentNameToRate = findViewById(R.id.establishmentNameToRate);
        String place = getIntent().getStringExtra("place");
        String bookingID = getIntent().getStringExtra("bookingID");
        String establishmentID = getIntent().getStringExtra("establishmentID");
        String status = getIntent().getStringExtra("status");

        // Log the values
        Log.d("BookingsCompleted", "Place: " + place);
        Log.d("BookingsCompleted", "Booking ID: " + bookingID);
        Log.d("BookingsCompleted", "Establishment ID: " + establishmentID);
        Log.d("BookingsCompleted", "Status: " + status);
        establishmentNameToRate.setText(place);
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

                // Check if the EditText is empty, the rating is not set, or the image is not set
                if (reviews.isEmpty()) {
                    Toast.makeText(BookingsCompletedActivity.this, "Please enter a review", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userRating == 0) {
                    Toast.makeText(BookingsCompletedActivity.this, "Please set a rating", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedImageUri == null) {
                    Toast.makeText(BookingsCompletedActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Disable the button
                buttonShare.setEnabled(false);

                Map<String, Object> userReview = new HashMap<>();
                userReview.put("userRating", userRating);
                userReview.put("reviews", reviews);
                userReview.put("establishmentID", establishmentID);
                userReview.put("place", place);
                userReview.put("userID", userID);
                userReview.put("bookingID", bookingID);

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
                            userReviewImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    userReview.put("imageUrl", uri.toString()); // Store the image URL

                                    db.collection("UserReview")
                                            .add(userReview)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(BookingsCompletedActivity.this, "Review saved successfully", Toast.LENGTH_SHORT).show();

                                                    // Update the status to "Completed"
                                                    db.collection("BookingPending")
                                                            .document(bookingID)
                                                            .update("status", "Completed")
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(BookingsCompletedActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(BookingsCompletedActivity.this, ProfileFragment.class);
                                                                    startActivity(intent);

                                                                    // Enable the button
                                                                    buttonShare.setEnabled(true);
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(BookingsCompletedActivity.this, "Error updating status", Toast.LENGTH_SHORT).show();

                                                                    // Enable the button
                                                                    buttonShare.setEnabled(true);
                                                                }
                                                            });
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(BookingsCompletedActivity.this, "Error saving review", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
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