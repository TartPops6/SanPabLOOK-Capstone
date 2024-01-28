package sanpablook.study.sanpablook.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

public class RecyclerDineReviews extends RecyclerView.Adapter<RecyclerDineReviews.BookingViewHolder> {

    static FirebaseFirestore db;
    private List<Map<String, Object>> reviews;

    public RecyclerDineReviews(List<Map<String, Object>> bookings) {
        this.reviews = bookings;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dine_reviews, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> review = this.reviews.get(position);
        holder.reviewContent.setText(review.get("reviews").toString());

        // Log the data for each review
        Log.d("RecyclerDineReviews", "Review at position " + position + ": " + review.get("reviews").toString());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView reviewContent, showMore;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            reviewContent = itemView.findViewById(R.id.reviewContent);
        }
    }
}