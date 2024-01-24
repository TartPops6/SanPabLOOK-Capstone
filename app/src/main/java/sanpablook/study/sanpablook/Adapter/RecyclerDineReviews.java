package sanpablook.study.sanpablook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

public class RecyclerDineReviews extends RecyclerView.Adapter<RecyclerDineReviews.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerDineReviews(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dine_reviews, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.reviewContent.setText(booking.get("place").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView reviewContent, showMore;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            reviewContent = itemView.findViewById(R.id.reviewContent);
            //clickable text
            showMore = itemView.findViewById(R.id.showMore);
        }
    }
}