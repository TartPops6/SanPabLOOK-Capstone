package sanpablook.study.sanpablook;

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

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.study.sanpablook.R;

public class BookingsCompletedActivity extends AppCompatActivity {

    ImageButton btnBackCompleted;
    AppCompatRatingBar ratingStars;
    Button buttonShare;
    EditText editTextRatings;

    //for add image
    ShapeableImageView addImageRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_completed);

        btnBackCompleted = findViewById(R.id.buttonBackCompleted);
        ratingStars = findViewById(R.id.ratingStars);
        buttonShare = findViewById(R.id.buttonShare);
        editTextRatings = findViewById(R.id.editTextRatings);
        //image
        addImageRatings = findViewById(R.id.addImageRatings);

        addImageRatings.setOnClickListener(view1 -> {
            ImagePicker.Companion.with(this)
                    .crop()                 // Crop image(Optional), Check Customization for more option
                    .compress(1024)         // Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)   // Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
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
                // Handle the rating change here
                // You can use the 'rating' variable to get the new rating value
                // 'fromUser' indicates whether the change was initiated by the user
                Toast.makeText(BookingsCompletedActivity.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Get the image URI
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            addImageRatings.setImageURI(imageUri);
        }
    }
}