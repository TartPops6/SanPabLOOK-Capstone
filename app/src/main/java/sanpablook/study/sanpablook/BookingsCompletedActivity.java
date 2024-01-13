package sanpablook.study.sanpablook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.study.sanpablook.R;

public class BookingsCompletedActivity extends AppCompatActivity {

    ImageButton btnBackCompleted;
    AppCompatRatingBar ratingBar;

    //for add image
    ImageView btnAddImageRatings;
    CardView addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_completed);

        btnBackCompleted = findViewById(R.id.buttonBackCompleted);
        ratingBar = findViewById(R.id.ratingStars);

        //image
        btnAddImageRatings = findViewById(R.id.addImageRatings);
        addImage = findViewById(R.id.cardviewAddImage);

        btnAddImageRatings.setOnClickListener(view1 -> {
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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
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
            btnAddImageRatings.setImageURI(imageUri);
        }
    }
}