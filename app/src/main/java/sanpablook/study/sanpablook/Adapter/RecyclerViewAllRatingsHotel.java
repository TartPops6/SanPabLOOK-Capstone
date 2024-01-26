package sanpablook.study.sanpablook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

public class RecyclerViewAllRatingsHotel extends RecyclerView.Adapter<RecyclerViewAllRatingsHotel.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerViewAllRatingsHotel(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_all_ratings_hotel, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.reviewDestination.setText(booking.get("place").toString());
        holder.reviewContent.setText(booking.get("bookingID").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
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