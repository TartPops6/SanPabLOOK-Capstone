package sanpablook.study.sanpablook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

public class RecyclerRatingsUser extends RecyclerView.Adapter<RecyclerRatingsUser.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerRatingsUser(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_ratings_user, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> review = bookings.get(position);
        holder.reviewDestination.setText(review.get("place").toString());
        holder.reviewContent.setText(review.get("reviews").toString());

        String imageUrl = review.get("imageUrl").toString();
        Glide.with(holder.imageRatings.getContext())
                .load(imageUrl)
                .into(holder.imageRatings);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView reviewDestination, reviewContent;
        ShapeableImageView imageRatings;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            reviewDestination = itemView.findViewById(R.id.reviewDestination);
            reviewContent = itemView.findViewById(R.id.reviewContent);
            //image
            imageRatings = itemView.findViewById(R.id.imageRatings);
        }
    }
}