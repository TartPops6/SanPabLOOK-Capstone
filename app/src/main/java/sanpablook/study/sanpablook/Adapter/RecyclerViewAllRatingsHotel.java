package sanpablook.study.sanpablook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

public class RecyclerViewAllRatingsHotel extends RecyclerView.Adapter<RecyclerViewAllRatingsHotel.BookingViewHolder> {

    private List<Map<String, Object>> ratings;

    public RecyclerViewAllRatingsHotel(List<Map<String, Object>> ratings) {
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_all_ratings_hotel, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> reviews = ratings.get(position);
        holder.reviewDestination.setText(reviews.get("place").toString());
        holder.reviewContent.setText(reviews.get("reviews").toString());

        String imageUrl = reviews.get("imageUrl").toString();
        Glide.with(holder.imageRatings.getContext())
                .load(imageUrl)
                .into(holder.imageRatings);
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView reviewDestination, reviewContent;
        ImageView imageRatings;

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